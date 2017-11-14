package calculate;

import javafx.beans.property.DoubleProperty;
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
    private DoubleProperty progressProperty;
    private List<Edge> edges;
    private ExecutorService executorService;

    private TimeStamp generateTimestamp;
    private TimeStamp drawingTimestamp;

    public KochManager(JSF31KochFractalFX jsf31KochFractalFX, DoubleProperty progressProperty) {
        this.jsf31KochFractalFX = jsf31KochFractalFX;
        this.progressProperty = progressProperty;
        kochFractal = new KochFractal();
        edges = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(4);
    }

    public void addEdges(List<Edge> edges) {
        this.edges.clear();
        this.edges.addAll(edges);

        executorService.shutdown();

        generateTimestamp.setEnd();
        System.out.println("Generating: " + generateTimestamp.toString());

        jsf31KochFractalFX.requestDrawEdges();
    }

    public void changeLevel(int currentLevel) {
        kochFractal.setLevel(currentLevel);

        generateTimestamp = new TimeStamp();

        makeThreads(currentLevel);

        generateTimestamp.setEnd();
        System.out.println("Genereren: " + generateTimestamp.toString());

        generateTimestamp.setEnd();

        jsf31KochFractalFX.setTextCalc(generateTimestamp.toString());
        jsf31KochFractalFX.setTextNrEdges(String.valueOf(kochFractal.getNrOfEdges()));
    }

    private void makeThreads(int currentLevel) {

        EdgeManager rightEdgeManager = new EdgeManager(this, currentLevel, Side.RIGHT);
        EdgeManager bottomEdgeManager = new EdgeManager(this, currentLevel, Side.BOTTOM);
        EdgeManager leftEdgeManager = new EdgeManager(this, currentLevel, Side.LEFT);

        progressProperty.bind(rightEdgeManager.progressProperty().add(bottomEdgeManager.progressProperty().add(leftEdgeManager.progressProperty())));

        Future rightEdges = executorService.submit(rightEdgeManager);
        Future bottomEdges = executorService.submit(bottomEdgeManager);
        Future leftEdges = executorService.submit(leftEdgeManager);
        
        executorService.execute(new EdgeTracker(this, rightEdges, bottomEdges, leftEdges));
    }

    public void drawEdges()
    {
        jsf31KochFractalFX.clearKochPanel();

        drawingTimestamp = new TimeStamp();
        drawingTimestamp.setBegin();

        for (Edge edge : edges)
            jsf31KochFractalFX.drawEdge(edge);

        drawingTimestamp.setEnd();
        System.out.println("Tekenen: " + drawingTimestamp.toString());
    }
}
