package mylogic.generatefile;

import calculate.Edge;
import calculate.KochFractal;
import mylogic.FractalBinaryFileMappingObserver;
import mylogic.FractalEdgeListObserver;
import timeutil.TimeStamp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class GenerateBufferedBinaryFileMapping
{
    private final int bytes = 1024*1024;
    public GenerateBufferedBinaryFileMapping(int level){
        KochFractal kochFractal = new KochFractal();
        kochFractal.setLevel(level);

        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin();
        try(RandomAccessFile memoryMappedFile = new RandomAccessFile(String.format("%sMapEdges.bin", String.valueOf(level)), "rw")) {

            //Mapping a file into memory
            FileChannel fc = memoryMappedFile.getChannel();
            MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, bytes);

            FractalEdgeListObserver fractalEdgeListObserver = new FractalEdgeListObserver();
            kochFractal.addObserver(fractalEdgeListObserver);

            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();
            kochFractal.generateLeftEdge();

            List<Edge> edges = fractalEdgeListObserver.getEdges();

            out.position(0);
            for (Edge edge : edges) {
                out.putDouble(edge.X1);
                out.putDouble(edge.Y1);
                out.putDouble(edge.X2);
                out.putDouble(edge.Y2);
                out.putDouble(edge.getColor().getHue());
            }


            System.out.print("mapped done!");
            timeStamp.setEnd();
            System.out.printf("Generating binary filemapping: %s", timeStamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
