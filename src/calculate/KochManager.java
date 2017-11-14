package calculate;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class KochManager
{
    private JSF31KochFractalFX jsf31KochFractalFX;
    private KochFractal kochFractal;
    private DoubleProperty processingProgressProperty;
    private DoubleProperty drawingProgressProperty;
    private List<Edge> edges;
    private ExecutorService executorService;

    private TimeStamp generateTimestamp;
    private TimeStamp drawingTimestamp;

    private Object drawSync;

    private EdgeManager rightEdgeManager;
    private EdgeManager bottomEdgeManager;
    private EdgeManager leftEdgeManager;

    public KochManager(JSF31KochFractalFX jsf31KochFractalFX, DoubleProperty processingProgressProperty, DoubleProperty drawingProgressProperty) {
        this.jsf31KochFractalFX = jsf31KochFractalFX;
        this.processingProgressProperty = processingProgressProperty;
        this.drawingProgressProperty = drawingProgressProperty;
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

        executorService.shutdown();

        generateTimestamp.setEnd();
        System.out.println("Generating: " + generateTimestamp.toString());

        jsf31KochFractalFX.requestDrawEdges();
    }

    public void changeLevel(int currentLevel) {
        drawingProgressProperty.setValue(0);
        processingProgressProperty.setValue(0);

        cancelExistingCalculation();
        kochFractal.setLevel(currentLevel);

        generateTimestamp = new TimeStamp();

        makeThreads(currentLevel);

        generateTimestamp.setEnd();
        System.out.println("Genereren: " + generateTimestamp.toString());

        generateTimestamp.setEnd();

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
        executorService = Executors.newFixedThreadPool(4);

        rightEdgeManager = new EdgeManager(this, currentLevel, Side.RIGHT);
        bottomEdgeManager = new EdgeManager(this, currentLevel, Side.BOTTOM);
        leftEdgeManager = new EdgeManager(this, currentLevel, Side.LEFT);

        processingProgressProperty.bind(rightEdgeManager.progressProperty().add(bottomEdgeManager.progressProperty().add(leftEdgeManager.progressProperty())));

        executorService.execute(rightEdgeManager);
        executorService.execute(bottomEdgeManager);
        executorService.execute(leftEdgeManager);

        /*
        Future<List<Edge>> rightEdges = executorService.submit(rightEdgeManager);
        Future<List<Edge>> bottomEdges = executorService.submit(bottomEdgeManager);
        Future<List<Edge>> leftEdges = executorService.submit(leftEdgeManager);
        */

        executorService.execute(new EdgeTracker(this, rightEdgeManager, bottomEdgeManager, leftEdgeManager));
    }

    public void drawEdges()
    {
        jsf31KochFractalFX.clearKochPanel();

        drawingTimestamp = new TimeStamp();
        drawingTimestamp.setBegin();

        synchronized (drawSync)
        {
            for (int i = 0; i < edges.size(); i++)
            {
                Edge edge = edges.get(i);
                jsf31KochFractalFX.drawEdge(edge);
            }
        }

        drawingTimestamp.setEnd();
        System.out.println("Tekenen: " + drawingTimestamp.toString());
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
