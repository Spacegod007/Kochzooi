package mylogic.sockets;

import calculate.Edge;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels Verheijen on 19/12/2017.
 */
public class ReceivingClientSocketInstant {

    private ReceivingClientSocketInstant()
    {}

    public static Edge getZoomedEdge(EdgePackage edgePackage) throws IOException{
        Socket socket = new Socket("127.0.0.1", 8100);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        objectOutputStream.writeInt(-5);
        objectOutputStream.writeObject(edgePackage);
        objectOutputStream.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        Object readobject = null;

        try {
            readobject = objectInputStream.readObject();
            while (readobject != null) {
                if (readobject instanceof Edge) {
                    return ((Edge) readobject);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("uhoh");
        return edgePackage.getEdge();
    }

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
