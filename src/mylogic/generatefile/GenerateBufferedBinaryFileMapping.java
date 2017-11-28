package mylogic.generatefile;

import calculate.KochFractal;
import mylogic.FractalBinaryFileMappingObserver;
import timeutil.TimeStamp;

import java.io.IOException;
import java.io.RandomAccessFile;

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

            kochFractal.addObserver(new FractalBinaryFileMappingObserver(memoryMappedFile.getChannel()));

            kochFractal.generateLeftEdge();
            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();

            timeStamp.setEnd();
            System.out.printf("Generating binary filemapping: %s", timeStamp);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
