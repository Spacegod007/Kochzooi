package mylogic.sockets;

import calculate.Edge;

import java.io.Serializable;

/**
 * Created by Niels Verheijen on 19/12/2017.
 */
public class EdgePackage implements Serializable {
    private final Edge edge;
    private final double zoom;
    private final double zoomTranslateX;
    private final double zoomTranslateY;

    public EdgePackage(Edge edge, double zoom, double zoomTranslateX, double zoomTranslateY){
        this.edge = edge;
        this.zoom = zoom;
        this.zoomTranslateX = zoomTranslateX;
        this.zoomTranslateY = zoomTranslateY;
    }

    public Edge getEdge() {
        return edge;
    }

    public double getZoom() {
        return zoom;
    }

    public double getZoomTranslateX() {
        return zoomTranslateX;
    }

    public double getZoomTranslateY() {
        return zoomTranslateY;
    }
}
