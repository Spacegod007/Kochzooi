package mylogic.sockets;

import calculate.Edge;
import calculate.KochManager;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReceivingClientSocket implements Runnable
{
    private final KochManager kochManager;
    private final int level;

    public ReceivingClientSocket(int level, KochManager kochManager)
    {
        this.level = level;
        this.kochManager = kochManager;
    }

    @Override
    public void run()
    {
        try
        {
            Socket socket = new Socket("127.0.0.1", 8100);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeInt(Integer.valueOf(level));
            objectOutputStream.flush();

            List<Edge> edges = new ArrayList<>();

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            Object readobject = null;

            try
            {
                readobject = objectInputStream.readObject();

                while (readobject != null)
                {
                    if (readobject instanceof Edge)
                    {
                        Edge arg = (Edge) readobject;
                        edges.add(arg);
                        kochManager.preDrawEdges(arg);
//                    updateProgress(edges.size(), kochFractal.getNrOfEdges());
                    } else
                    {
                        System.out.println("Something went wrong with receiving edges...");
                    }

                    readobject = objectInputStream.readObject();
                }
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            } catch (EOFException ignored)
            {
            }

            objectOutputStream.close();
            objectInputStream.close();

            kochManager.addEdges(edges);
        }
        catch (Exception ignored)
        { }
    }
}
