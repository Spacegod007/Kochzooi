package mylogic;

import mylogic.sockets.RunnableServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class Program {

    public static void main(String[] args)
    {
        new Program();
    }

    public Program()
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(8100);

            while (true)
            {
                new Thread(new RunnableServerSocket(serverSocket.accept())).start();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
