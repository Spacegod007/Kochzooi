package mylogic.generatefile;

import calculate.Edge;
import calculate.KochFractal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

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

        kochFractal.addObserver((o, arg) ->
        {
            Edge edge = (Edge) arg;

            try (FileOutputStream fileOutputStream = new FileOutputStream(String.format("%sedges.txt", String.valueOf(level)), true);
                 PrintStream printStream = new PrintStream(fileOutputStream))
            {
                printStream.println(edge.toString());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });

        kochFractal.generateBottomEdge();
        kochFractal.generateRightEdge();
        kochFractal.generateLeftEdge();
    }
}
