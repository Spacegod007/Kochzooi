package mylogic;

import calculate.Edge;
import calculate.KochFractal;

import java.util.Observable;
import java.util.Observer;

public class Program {

    public static void main(String[] args)
    {
        KochFractal kochFractal = new KochFractal();
        kochFractal.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Edge edge = (Edge) arg;
                System.out.format("Begin: x:" + edge.X1 + " y:" + edge.Y1 + ",%nEnd: x:" + edge.X2 + " y:" + edge.Y2 + "%n%n");
            }
        });

        kochFractal.setLevel(2);

        kochFractal.generateBottomEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateRightEdge();
    }
}
