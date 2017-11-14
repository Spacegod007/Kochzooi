package calculate;

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
    private List<Edge> edges;
    private ExecutorService executorService;

    private TimeStamp generateTimestamp;
    private TimeStamp drawingTimestamp;

    public KochManager(JSF31KochFractalFX jsf31KochFractalFX) {
        this.jsf31KochFractalFX = jsf31KochFractalFX;
        kochFractal = new KochFractal();
        edges = new ArrayList<>();
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
        executorService = Executors.newFixedThreadPool(4);

        Future rightEdges = executorService.submit(new EdgeManager(this, currentLevel, Side.RIGHT));
        Future bottomEdges = executorService.submit(new EdgeManager(this, currentLevel, Side.BOTTOM));
        Future leftEdges = executorService.submit(new EdgeManager(this, currentLevel, Side.LEFT));
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
