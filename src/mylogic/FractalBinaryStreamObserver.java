package mylogic;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jordi van Roij on 21-Nov-17.
 */
public class FractalBinaryStreamObserver implements Observer
{
    private final ObjectOutput objectOutput;

    public FractalBinaryStreamObserver(ObjectOutput objectOutput)
    {
        this.objectOutput = objectOutput;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        try
        {
            objectOutput.writeObject(arg);
            objectOutput.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
