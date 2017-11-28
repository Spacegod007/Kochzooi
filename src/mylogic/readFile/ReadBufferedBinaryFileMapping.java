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

            int amountOfData = out.getInt(0)*5;
            out.position(1);
            try {
                //reading 10 bytes from memory file in Java
                for (int i = 0; i < amountOfData; i+=5) {

                    double X1 = out.get();
                    double Y1 = out.get();
                    double X2 = out.get();
                    double Y2 = out.get();
                    double hue = out.get();
                    Edge edge = new Edge(X1, Y1, X2, Y2, hue);
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
