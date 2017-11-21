package mylogic.generatefile;

import calculate.KochFractal;
import mylogic.FractalTextStreamObserver;
import timeutil.TimeStamp;

import java.io.*;

/**
 * Created by Jordi van Roij on 21-Nov-17.
 */
public class GenerateBufferedTextFile implements Runnable
{
    private final int level;

    public GenerateBufferedTextFile(int level)
    {
        this.level = level;
    }

    @Override
    public void run()
    {
        KochFractal kochFractal = new KochFractal();
        kochFractal.setLevel(level);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(String.format("%sbufferedEdges.txt", String.valueOf(level)))))
        {

            kochFractal.addObserver(new FractalTextStreamObserver(bufferedWriter));

            TimeStamp bufferedTextFileTime = new TimeStamp();

            bufferedTextFileTime.setBegin();

            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();
            kochFractal.generateLeftEdge();

            bufferedTextFileTime.setEnd();

            System.out.println(String.format("Buffered text file generating time: %s", bufferedTextFileTime.toString()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
