package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class EdgeTracker implements Runnable{

    private final KochManager manager;
    private final Future<List<Edge>> rightEdge;
    private final Future<List<Edge>> bottomEdge;
    private final Future<List<Edge>> leftEdge;

    private final List<Edge> edges;

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
        }
        catch (CancellationException e)
        {
            return;
        }
        catch (InterruptedException  | ExecutionException ignored)
        { }

        manager.addEdges(edges);
    }
}
