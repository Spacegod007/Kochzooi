package mylogic.sockets;

import calculate.Edge;
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
            if(readObject == -5){
                Edge edge = calcEdgePackage((EdgePackage)objectInputStream.readObject());
                try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
                    objectOutputStream.writeObject(edge);
                    objectOutputStream.flush();
                }
                //System.out.println(((EdgePackage)objectInputStream.readObject()).getZoom());
            }

            integerMessageReceived(readObject, outputStream);


        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    private Edge calcEdgePackage(EdgePackage edgePackage){
        Edge e = edgePackage.getEdge();
        double zoom = edgePackage.getZoom();
        double zoomTranslateX = edgePackage.getZoomTranslateX();
        double zoomTranslateY = edgePackage.getZoomTranslateY();
        Edge edge = new Edge(
                e.X1 * zoom + zoomTranslateX,
                e.Y1 * zoom + zoomTranslateY,
                e.X2 * zoom + zoomTranslateX,
                e.Y2 * zoom + zoomTranslateY,
                e.getColor()
        );
        return edge;
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

