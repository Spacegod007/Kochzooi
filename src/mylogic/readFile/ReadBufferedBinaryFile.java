package mylogic.readFile;


import calculate.Edge;
import calculate.KochManager;
import timeutil.TimeStamp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadBufferedBinaryFile {

    public ReadBufferedBinaryFile(KochManager manager, int level){
        List<Edge> edges = new ArrayList<>();
        List<Integer> test = new ArrayList<>();

        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(String.format("%sEdges.bin", String.valueOf(level)))))) {
            while(true) {
                try {
                    Edge edge = (Edge) objectInputStream.readObject();
                    edges.add(edge);
                }
                catch(Exception e){
                    break;
                }
            }

        }
        catch (IOException e) {
            System.out.println("There is no file of this level");
        }

        timeStamp.setEnd();

        System.out.printf("Read binary buffered: %s", timeStamp);

        manager.addEdges(edges);
    }
}
