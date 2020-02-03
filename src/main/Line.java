package main;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line {
    public double sX;
    public double sY;
    public double eX;
    public double eY;
    
    public Line(double sX, double sY, double eX, double eY) {
        this.sX = sX;
        this.sY = sY;
        this.eX = eX;
        this.eY = eY;
    }
    
    public Line(Point2D p1, Point2D p2) {
        this.sX = p1.getX();
        this.sY = p1.getY();
        this.eX = p2.getX();
        this.eY = p2.getY();
    }
    
    public void render(GraphicsContext gc) {
        gc.setStroke(Color.PINK);
        gc.strokeLine(sX, sY, eX, eY);
    }
}
