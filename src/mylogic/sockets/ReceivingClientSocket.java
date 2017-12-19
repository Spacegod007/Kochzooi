package mylogic.sockets;

import calculate.Edge;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReceivingClientSocket
{
    private ReceivingClientSocket()
    {}

    public static List<Edge> getEdges(int level) throws IOException
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
                    edges.add((Edge) readobject);
                }
                else
                {
                    System.out.println("Something went wrong with receiving edges...");
                }

                readobject = objectInputStream.readObject();
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (EOFException ignored)
        { }

        objectOutputStream.close();
        objectInputStream.close();

        return edges;
    }
}
