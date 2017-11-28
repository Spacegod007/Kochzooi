package mylogic.readFile;


import calculate.Edge;
import calculate.KochManager;
import timeutil.TimeStamp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadBufferedTextFile
{
    public ReadBufferedTextFile(KochManager manager, int level){

        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(String.format("%sbufferedEdges.txt", String.valueOf(level))))){
            List<Edge> edges = new ArrayList<>();
            List<String> edgeStrings = new ArrayList<>();
            String edgeData = "";
            int data = bufferedReader.read();
            while(data != -1){
                if((char) data == '$'){
                    edgeStrings.add(edgeData);
                    edgeData = "";
                }
                else {
                    edgeData += (char) data;
                }
                data = bufferedReader.read();

            }

            for(String edgeString : edgeStrings){
                String[] edgeSplit = edgeString.split(" ");
                double[] edgeValues = new double[4];
                for(int x = 0; x<4; x++) {
                    edgeValues[x] = Double.parseDouble(edgeSplit[x]);
                }

                edges.add(new Edge(edgeValues[0],edgeValues[1],edgeValues[2],edgeValues[3], edgeSplit[4]));
            }

            timeStamp.setEnd();

            System.out.printf("Read text buffered: %s", timeStamp);

            manager.addEdges(edges);

        }
        catch (IOException e) {
            System.out.println("There is no file for this level");
        }
    }
}
