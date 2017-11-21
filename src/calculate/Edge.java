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
    
    public Edge(double X1, double Y1, double X2, double Y2, Color color) {
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.color = color.toString();
    }

    public Color getColor()
    {
        return Color.valueOf(color);
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s %s %s", X1, Y1, X2, Y2, color);
    }
}
