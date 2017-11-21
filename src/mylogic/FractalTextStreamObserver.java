package mylogic;

import calculate.Edge;

import java.io.IOException;
import java.io.Writer;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jordi van Roij on 21-Nov-17.
 */
public class FractalTextStreamObserver implements Observer
{
    private final Writer writer;

    public FractalTextStreamObserver(Writer writer)
    {
        this.writer = writer;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        try
        {
            writer.write(String.format("%s%n", arg.toString()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
