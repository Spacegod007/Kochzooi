package mylogic;

import calculate.Edge;

import java.util.Observable;
import java.util.Observer;

public class KochFractalObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Edge edge = (Edge) arg;
        System.out.println("Begin: x:" + edge.X1 + " y:" + edge.Y1 + ", End: x:" + edge.X2 + " y:" + edge.Y2);
    }
}
