package mylogic;

import calculate.Edge;
import calculate.KochFractal;

public class Program {

    public static void main(String[] args)
    {
        KochFractal kochFractal = new KochFractal();
        kochFractal.addObserver((o, arg) ->
        {
            Edge edge = (Edge) arg;
            System.out.format("Begin: x:" + edge.X1 + " y:" + edge.Y1 + ",%nEnd: x:" + edge.X2 + " y:" + edge.Y2 + "%n%n");
        });

        kochFractal.setLevel(2);

        kochFractal.generateBottomEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateRightEdge();
    }
}
