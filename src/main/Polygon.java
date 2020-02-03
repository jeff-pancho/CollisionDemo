package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polygon {
    private double[] xPts;
    private double[] yPts;
    private double centerX;
    private double centerY;
    private final int sides;
    private final double circumRad;
    private double dir;
    
    public Polygon(double centerX, double centerY, int sides, double circumRad) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.sides = sides;
        this.xPts = new double[sides];
        this.yPts = new double[sides];
        this.circumRad = circumRad;
        this.dir = 0;
        calcPts();
    }
    
    public void calcPts() {
        for(int i = 0; i < sides; i++) {
            xPts[i] = centerX + Math.cos(dir + (Math.PI * 2 / sides) * i) * circumRad;
            yPts[i] = centerY + Math.sin(dir + (Math.PI * 2 / sides) * i) * circumRad;
        }
    }
    
    public void render(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokePolygon(xPts, yPts, xPts.length);
        gc.setFill(Color.BLUE);
        gc.fillOval(centerX - 10, centerY - 10, 20, 20);
        gc.setStroke(Color.RED);
        gc.strokeOval(centerX - circumRad, centerY - circumRad, circumRad * 2, circumRad * 2);
    }
    
    public void setPos(double x, double y) {
        this.centerX = x;
        this.centerY = y;
        calcPts();
    }
    
    public double getCenterX() {
        return this.centerX;
    }
    
    public double getCenterY() {
        return this.centerY;
    }
    
    public double getCircumRad() {
        return this.circumRad;
    }
}
