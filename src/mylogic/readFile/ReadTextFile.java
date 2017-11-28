package mylogic.readFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import calculate.Edge;
import calculate.KochManager;
import timeutil.TimeStamp;


public class ReadTextFile {

    public ReadTextFile(KochManager manager, int level)
    {

        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin();

        try(InputStreamReader inputStreamReader = new FileReader(String.format("%sedges.txt", String.valueOf(level)))){
            List<Edge> edges = new ArrayList<>();
            List<String> edgeStrings = new ArrayList<>();
            String edgeData = "";
            int data = inputStreamReader.read();
            while(data != -1){
                if((char) data == '$'){
                    edgeStrings.add(edgeData);
                    edgeData = "";
                }
                else {
                    edgeData += (char) data;
                }
                    data = inputStreamReader.read();

            }

            for(String edgeString : edgeStrings){
                String[] edgeSplit = edgeString.split(" ");
                double[] edgeValues = new double[4];
                for(int x = 0; x<4; x++) {
                    edgeValues[x] = Double.parseDouble(edgeSplit[x]);
                }
                Edge edge = new Edge(edgeValues[0],edgeValues[1],edgeValues[2],edgeValues[3], edgeSplit[4]);
                System.out.println(edge.toString());
                edges.add(edge);
            }
            manager.addEdges(edges);

            timeStamp.setEnd();
            System.out.printf("Read text normal: %s", timeStamp);

        }
        catch (IOException e) {
            System.out.println("There is no file for this level");
        }
    }
}
