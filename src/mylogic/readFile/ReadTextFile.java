package mylogic.readFile;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import calculate.Edge;
import calculate.KochManager;
import timeutil.TimeStamp;

import static java.nio.file.StandardWatchEventKinds.*;

public class ReadTextFile {
    private KochManager manager;


    public ReadTextFile(KochManager manager, int level) {
        this.manager = manager;

        final WatchService watcher;
        Path path = Paths.get("tmp");
        WatchKey key;

        try {
            watcher = FileSystems.getDefault().newWatchService();
            path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            while (true) {
                key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;

                    Path filename = ev.context();
                    Path child = path.resolve(filename);

                    WatchEvent.Kind kind = ev.kind();
                    if (kind == ENTRY_CREATE) {
                        System.out.println("Create");
                    }
                    if (kind == ENTRY_DELETE) {
                        System.out.println("Delete");
                    }
                    if (kind == ENTRY_MODIFY) {
                        read("tmp/edges");
                    }
                }
                key.reset();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void read(String fileName) {


        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin();

        try (InputStreamReader inputStreamReader = new FileReader(fileName)) {
            List<Edge> edges = new ArrayList<>();
            List<String> edgeStrings = new ArrayList<>();
            String edgeData = "";
            int data = inputStreamReader.read();
            while (data != -1) {
                if ((char) data == '$') {
                    edgeStrings.add(edgeData);
                    edgeData = "";
                } else {
                    edgeData += (char) data;
                }
                data = inputStreamReader.read();

            }

            for (String edgeString : edgeStrings) {
                String[] edgeSplit = edgeString.split(" ");
                double[] edgeValues = new double[4];
                for (int x = 0; x < 4; x++) {
                    edgeValues[x] = Double.parseDouble(edgeSplit[x]);
                }
                Edge edge = new Edge(edgeValues[0], edgeValues[1], edgeValues[2], edgeValues[3], edgeSplit[4]);
                System.out.println(edge.toString());
                edges.add(edge);
            }
            manager.addEdges(edges);

            timeStamp.setEnd();
            System.out.printf("Read text normal: %s", timeStamp);

        } catch (IOException e) {
            System.out.println("There is no file for this level");
        }

    }
}
