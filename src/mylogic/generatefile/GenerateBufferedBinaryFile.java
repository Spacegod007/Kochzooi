package mylogic.generatefile;

import calculate.KochFractal;
import mylogic.FractalBinaryStreamObserver;
import timeutil.TimeStamp;

import java.io.*;

public class GenerateBufferedBinaryFile implements Runnable
{
    private final int level;

    public GenerateBufferedBinaryFile(int level)
    {
        this.level = level;
    }

    @Override
    public void run()
    {
        KochFractal kochFractal = new KochFractal();
        kochFractal.setLevel(level);


//
//        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new ObjectOutputStream(new FileOutputStream(String.format("%sEdges.bin", String.valueOf(level)), true))))
//        {
//            kochFractal.addObserver(new FractalBinaryStreamObserver(bufferedOutputStream));
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }




        TimeStamp bufferedBinaryFileTime = new TimeStamp();

        bufferedBinaryFileTime.setBegin();

        kochFractal.generateBottomEdge();
        kochFractal.generateRightEdge();
        kochFractal.generateLeftEdge();

        bufferedBinaryFileTime.setEnd();

        System.out.println(String.format("Buffered binary file generating time: %s", bufferedBinaryFileTime.toString()));
    }
}
