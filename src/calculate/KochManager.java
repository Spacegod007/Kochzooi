package calculate;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import mylogic.generatefile.GenerateTextFile;
import mylogic.readFile.*;
import mylogic.sockets.ReceivingClientSocket;
import timeutil.TimeStamp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KochManager
{
    private final JSF31KochFractalFX jsf31KochFractalFX;
    private final KochFractal kochFractal;
    private final DoubleProperty processingProgressProperty;
    private final List<Edge> edges;
    private ExecutorService executorService;

    private TimeStamp generateTimestamp;

    private final Object drawSync;

    private EdgeManager rightEdgeManager;
    private EdgeManager bottomEdgeManager;
    private EdgeManager leftEdgeManager;

    public KochManager(JSF31KochFractalFX jsf31KochFractalFX, DoubleProperty processingProgressProperty) {
        this.jsf31KochFractalFX = jsf31KochFractalFX;
        this.processingProgressProperty = processingProgressProperty;
        kochFractal = new KochFractal();
        edges = new ArrayList<>();
        drawSync = new Object();
    }

    public void addEdges(List<Edge> edges) {
        synchronized (drawSync)
        {
            this.edges.clear();
            this.edges.addAll(edges);
        }

        //executorService.shutdown();

        generateTimestamp.setEnd();

        jsf31KochFractalFX.setTextCalc(generateTimestamp.toString());

        jsf31KochFractalFX.requestDrawEdges();
    }

    public void changeLevel(int currentLevel) {

        cancelExistingCalculation();
        kochFractal.setLevel(currentLevel);

        generateTimestamp = new TimeStamp();

        makeThreads(currentLevel);

        generateTimestamp.setBegin();

        jsf31KochFractalFX.setTextCalc(generateTimestamp.toString());
        jsf31KochFractalFX.setTextNrEdges(String.valueOf(kochFractal.getNrOfEdges()));
    }

    private void cancelExistingCalculation()
    {
        if (rightEdgeManager != null)
        {
            rightEdgeManager.cancel();
            bottomEdgeManager.cancel();
            leftEdgeManager.cancel();
        }
    }

    private void makeThreads(int currentLevel) {
        /*executorService = Executors.newFixedThreadPool(4);

        rightEdgeManager = new EdgeManager(this, currentLevel, Side.RIGHT);
        bottomEdgeManager = new EdgeManager(this, currentLevel, Side.BOTTOM);
        leftEdgeManager = new EdgeManager(this, currentLevel, Side.LEFT);

        processingProgressProperty.bind(rightEdgeManager.progressProperty().add(bottomEdgeManager.progressProperty().add(leftEdgeManager.progressProperty())));
*/
//        new Thread(() -> new GenerateTextFile(currentLevel)).start();

        //Text file unbuffered.
//        new ReadTextFile(this, currentLevel);
        //Text file buffered.
//          new ReadBufferedTextFile(this,currentLevel);
        //Binary file unbuffered.
//          new ReadBinaryFile(this,currentLevel);
        //Binary file buffered.
//          new ReadBufferedBinaryFile(this,currentLevel);
        //Buffered binary file mapping
            //new ReadBufferedBinaryFileMapping(this,currentLevel);

        /*
        executorService.execute(rightEdgeManager);
        executorService.execute(bottomEdgeManager);
        executorService.execute(leftEdgeManager);

        executorService.execute(new EdgeTracker(this, rightEdgeManager, bottomEdgeManager, leftEdgeManager));
        */

        try
        {
            addEdges(ReceivingClientSocket.getEdges(currentLevel));
        }
        catch (IOException e)
        {
            System.out.println("Error with sockets");
            e.printStackTrace();
        }
    }

    public void drawEdges()
    {
        jsf31KochFractalFX.clearKochPanel();

        TimeStamp drawingTimestamp = new TimeStamp();

        drawingTimestamp.setBegin();

        synchronized (drawSync)
        {
            for (Edge edge : edges)
            {
                jsf31KochFractalFX.drawEdge(edge);
            }
        }

        drawingTimestamp.setEnd();

        jsf31KochFractalFX.setTextDraw(drawingTimestamp.toString());
    }

    public void preDrawEdges(Edge arg)
    {
        synchronized (drawSync)
        {
            Edge tempEdge = new Edge(arg.X1, arg.Y1, arg.X2, arg.Y2, Color.WHITE);
            edges.add(tempEdge);
            jsf31KochFractalFX.requestDrawEdges();
        }
    }
}
