package mylogic.sockets;

import calculate.KochFractal;
import mylogic.FractalBinaryStreamObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RunnableServerSocket implements Runnable
{
    private final Socket sender;

    private KochFractal kochFractal;

    public RunnableServerSocket(Socket sender)
    {
        this.sender = sender;
    }

    @Override
    public void run()
    {
        kochFractal = new KochFractal();

        ObjectInputStream objectInputStream;
        OutputStream outputStream;

        //get socket data
        try
        {
            objectInputStream = new ObjectInputStream(sender.getInputStream());
            outputStream = sender.getOutputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        //read socket data
        try
        {
            int readObject = objectInputStream.readInt();

            integerMessageReceived(readObject, outputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //close socket reader
        try
        {
            objectInputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void integerMessageReceived(Integer level, OutputStream outputStream)
    {
        System.out.printf("received message: %s%n", level);
        try
        {
            if (level > 0)
            {
                kochFractal.setLevel(level);

                //debug line
                System.out.printf("%nSelected level: %s%n", level);

                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream))
                {

                    kochFractal.addObserver(new FractalBinaryStreamObserver(objectOutputStream));

                    kochFractal.generateBottomEdge();
                    kochFractal.generateRightEdge();
                    kochFractal.generateLeftEdge();

                    objectOutputStream.close();
                }
            }
            else
            {
                System.out.println("Please enter a number higher than 0");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

