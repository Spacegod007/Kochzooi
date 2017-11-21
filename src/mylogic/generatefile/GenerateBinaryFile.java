package mylogic.generatefile;

import calculate.Edge;
import calculate.KochFractal;
import mylogic.FractalBinaryStreamObserver;
import timeutil.TimeStamp;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GenerateBinaryFile implements Runnable
{
    private final int level;

    public GenerateBinaryFile(int level)
    {
        this.level = level;
    }

    @Override
    public void run()
    {
        KochFractal kochFractal = new KochFractal();
        kochFractal.setLevel(level);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(String.format("%sEdges.bin", String.valueOf(level)), true)))
        {
            kochFractal.addObserver(new FractalBinaryStreamObserver(objectOutputStream));

            TimeStamp BinaryFileTime = new TimeStamp();

            BinaryFileTime.setBegin();

            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();
            kochFractal.generateLeftEdge();

            BinaryFileTime.setEnd();

            System.out.println(String.format("Binary file generating time: %s", BinaryFileTime.toString()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }



        
    }
}
