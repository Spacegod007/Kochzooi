package mylogic.generatefile;

import calculate.KochFractal;
import mylogic.FractalBinaryStreamObserver;
import timeutil.TimeStamp;

import java.io.*;

public class GenerateBufferedBinaryFile
{

    public GenerateBufferedBinaryFile(int level)
    {
        KochFractal kochFractal = new KochFractal();
        kochFractal.setLevel(level);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(String.format("%sEdges.bin", String.valueOf(level))))))
        {
            kochFractal.addObserver(new FractalBinaryStreamObserver(objectOutputStream));

            TimeStamp BinaryFileTime = new TimeStamp();

            BinaryFileTime.setBegin();

            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();
            kochFractal.generateLeftEdge();

            BinaryFileTime.setEnd();

            System.out.println(String.format("Buffered Binary file generating time: %s", BinaryFileTime.toString()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
