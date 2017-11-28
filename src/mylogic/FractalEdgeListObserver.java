package mylogic;

import calculate.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FractalEdgeListObserver implements Observer
{
    private final List<Edge> edges;

    public FractalEdgeListObserver()
    {
        edges = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        edges.add((Edge) arg);
    }

    public List<Edge> getEdges()
    {
        return edges;
    }
}
