/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculate;

import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class Edge {
    public final double X1;
    public final double Y1;
    public final double X2;
    public final double Y2;
    public final Color color;
    
    public Edge(double X1, double Y1, double X2, double Y2, Color color) {
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.color = color;
    }
}
