package mylogic.generatefile;

import calculate.Edge;
import calculate.KochFractal;
import mylogic.FractalBinaryFileMappingObserver;
import mylogic.FractalEdgeListObserver;
import timeutil.TimeStamp;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class GenerateBufferedBinaryFileMapping
{
    public GenerateBufferedBinaryFileMapping(int level)
    {
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin();

        try(RandomAccessFile memoryMappedFile = new RandomAccessFile(String.format("%sMapEdges.bin", String.valueOf(level)), "rw"))
        {
            KochFractal kochFractal = new KochFractal();
            kochFractal.setLevel(level);
            FractalEdgeListObserver fractalEdgeListObserver = new FractalEdgeListObserver();
            //new FractalBinaryFileMappingObserver(memoryMappedFile.getChannel()));
            kochFractal.addObserver(fractalEdgeListObserver);

            kochFractal.generateLeftEdge();
            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();

            List<Edge> edges = fractalEdgeListObserver.getEdges();


            File file = new File(String.format("%sMapEdges.bin", String.valueOf(level)));

            file.delete();

            FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();

            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024*1024);//(7*8)*edges.size());
            

            buffer.put(FractalBinaryFileMappingObserver.serialize(edges));


            System.out.print("mapped done!");
            timeStamp.setEnd();
            System.out.printf("Generating binary filemapping: %s", timeStamp);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
