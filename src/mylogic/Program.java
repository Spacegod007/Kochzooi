package mylogic;

import calculate.Edge;
import calculate.KochFractal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {

    public static void main(String[] args)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter the desired level: ");
        String levelString = null;
        try {
            levelString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int level = Integer.parseInt(levelString);








        KochFractal kochFractal = new KochFractal();
        kochFractal.addObserver((o, arg) ->
        {
            Edge edge = (Edge) arg;
            System.out.format("Begin: x:" + edge.X1 + " y:" + edge.Y1 + ",%nEnd: x:" + edge.X2 + " y:" + edge.Y2 + "%n%n");
        });

        kochFractal.setLevel(level);

        kochFractal.generateBottomEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateRightEdge();
    }
}
