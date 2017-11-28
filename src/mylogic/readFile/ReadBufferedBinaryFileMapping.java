package mylogic.readFile;

import calculate.Edge;
import calculate.KochManager;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ReadBufferedBinaryFileMapping {

    private static final int NUMBER_OF_BYTES = 10*1024*1024; //10 MB of data

    public ReadBufferedBinaryFileMapping(KochManager manager, int level)
    {
        List<Edge> edges = new ArrayList<>();
        try(RandomAccessFile memoryMappedFile = new RandomAccessFile(String.format("%sMapEdges.bin", String.valueOf(level)), "r")) {

            //Mapping a file into memory
            FileChannel fc = memoryMappedFile.getChannel();
            MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_ONLY, 0, 1024*1024);

            int amountOfData = out.getInt(0) * 7;
            out.position(1);
            try {
                //reading 10 bytes from memory file in Java
                for (int i = 0; i < amountOfData; i+=7) {
                    double X1 = out.getDouble(i + 1);
                    double Y1 = out.getDouble(i + 2);
                    double X2 = out.getDouble(i + 3);
                    double Y2 = out.getDouble(i + 4);
                    double red = out.getDouble(i + 5);
                    double blue = out.getDouble(i + 6);
                    double green = out.getDouble(i + 7);
                    Edge edge = new Edge(X1, Y1, X2, Y2, red, blue, green);
                    System.out.println(edge.toString());
                    System.out.println(i);
                    edges.add(edge);
                }
                manager.addEdges(edges);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
