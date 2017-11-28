package mylogic.readFile;


import calculate.Edge;
import calculate.KochManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadBinaryFile {

    public ReadBinaryFile(KochManager manager, int level) {
        List<Edge> edges = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(String.format("%sEdges.bin", String.valueOf(level))))) {
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
        manager.addEdges(edges);
    }
}