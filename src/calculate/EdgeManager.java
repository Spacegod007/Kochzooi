package calculate;

import javafx.concurrent.Task;

import java.util.*;

public class EdgeManager extends Task<List<Edge>> implements Observer {

    private final KochManager kochManager;
    private final KochFractal kochFractal;
    private final List<Edge> edges;
    private final Side side;

    public EdgeManager(KochManager kochManager, int level, Side side) {
        this.kochManager = kochManager;
        this.side = side;

        this.kochFractal = new KochFractal();
        this.edges = new ArrayList<>();

        kochFractal.addObserver(this);
        kochFractal.setLevel(level);
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge)arg);
        kochManager.preDrawEdges((Edge)arg);
        updateProgress(edges.size(), kochFractal.getNrOfEdges());
    }

    //@Override
    public List<Edge> call() throws Exception {
        switch (side)
        {
            case LEFT:
                kochFractal.generateLeftEdge();
                break;
            case RIGHT:
                kochFractal.generateBottomEdge();
                break;
            case BOTTOM:
                kochFractal.generateRightEdge();
                break;
        }

        return edges;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        kochFractal.cancel();
        return super.cancel(mayInterruptIfRunning);
    }
}
