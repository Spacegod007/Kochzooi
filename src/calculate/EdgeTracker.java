package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class EdgeTracker implements Runnable{

    private KochManager manager;
    private Future<List<Edge>> rightEdge;
    private Future<List<Edge>> bottomEdge;
    private Future<List<Edge>> leftEdge;

    private List<Edge> edges;

    public EdgeTracker(KochManager manager, Future rightEdge, Future bottomEdge, Future leftEdge) {
        this.manager = manager;
        this.rightEdge = rightEdge;
        this.bottomEdge = bottomEdge;
        this.leftEdge = leftEdge;

        edges = new ArrayList<>();
    }

    @Override
    public void run() {
        try
        {
            edges.addAll(rightEdge.get());
            edges.addAll(bottomEdge.get());
            edges.addAll(leftEdge.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        manager.addEdges(edges);
    }
}
