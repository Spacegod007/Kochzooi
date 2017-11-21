package mylogic.generatefile;

import calculate.KochFractal;
import mylogic.FractalTextStreamObserver;
import timeutil.TimeStamp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GenerateTextFile implements Runnable
{
    private final int level;

    public GenerateTextFile(int level)
    {
        this.level = level;
    }

    @Override
    public void run()
    {
        KochFractal kochFractal = new KochFractal();
        kochFractal.setLevel(level);

        try (OutputStreamWriter outputStreamWriter = new FileWriter(String.format("%sedges.txt", String.valueOf(level)), true))
        {

            kochFractal.addObserver(new FractalTextStreamObserver(outputStreamWriter));

            TimeStamp textFileTime = new TimeStamp();

            textFileTime.setBegin();

            kochFractal.generateBottomEdge();
            kochFractal.generateRightEdge();
            kochFractal.generateLeftEdge();

            textFileTime.setEnd();

            System.out.println(String.format("text file generating time: %s", textFileTime.toString()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
