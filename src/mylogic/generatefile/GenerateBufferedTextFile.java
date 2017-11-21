package mylogic.generatefile;

import calculate.KochFractal;

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
    }
}
