package calculate;

import javafx.concurrent.Task;

import java.util.*;
import java.util.concurrent.Callable;

public class EdgeManager extends Task<List<Edge>> implements Observer {

    private KochManager kochManager;
    private KochFractal kochFractal;
    private List<Edge> edges;
    private Side side;

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
    protected void cancelled()
    {
        kochFractal.cancel();
        super.cancelled();
    }
}
