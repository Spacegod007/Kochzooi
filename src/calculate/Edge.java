/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculate;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 *
 * @author Peter Boots
 */
public class Edge implements Serializable{
    public final double X1;
    public final double Y1;
    public final double X2;
    public final double Y2;
    private final String color;

    public Edge(double x1, double y1, double x2, double y2, double hue)
    {
        X1 = x1;
        Y1 = y1;
        X2 = x2;
        Y2 = y2;
        color = Color.hsb(hue, 1, 1).toString();
    }

    public Edge(double X1, double Y1, double X2, double Y2, Color color) {
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.color = color.toString();
    }

    public Edge(double X1, double Y1, double X2, double Y2, String color){
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.color = color;
    }

    public Color getColor()
    {
        return Color.valueOf(color);
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s %s %s", X1, Y1, X2, Y2, color) + "$";
    }
}
