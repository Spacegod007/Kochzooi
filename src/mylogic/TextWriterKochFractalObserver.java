package mylogic;

import calculate.Edge;
import calculate.KochFractal;

import java.util.Observable;
import java.util.Observer;

public class TextWriterKochFractalObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        KochFractal kochFractal = (KochFractal) o;
        Edge edge = (Edge) arg;

        TextStreamFractal.writeEdgesAsText(kochFractal.getLevel(), edge);

        System.out.println(edge.toString());
    }
}
