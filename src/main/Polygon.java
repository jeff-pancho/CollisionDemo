package main;

import java.util.ArrayList;

import javafx.geometry.Point2D;
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
    
    private Color activeColor;
    
    public Polygon(double centerX, double centerY, int sides, double circumRad) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.sides = sides;
        this.xPts = new double[sides];
        this.yPts = new double[sides];
        this.circumRad = circumRad;
        this.dir = 0;
        this.activeColor = Color.BLACK;
        
        calcPts();
    }
    
    public void calcPts() {
        for(int i = 0; i < sides; i++) {
            xPts[i] = centerX + Math.cos(dir + (Math.PI * 2 / sides) * i) * circumRad;
            yPts[i] = centerY + Math.sin(dir + (Math.PI * 2 / sides) * i) * circumRad;
        }
    }
    
    public void render(GraphicsContext gc) {
        gc.setStroke(activeColor);
        gc.strokePolygon(xPts, yPts, xPts.length);
        
        gc.setStroke(Color.RED);
        gc.strokeOval(centerX - circumRad, centerY - circumRad, circumRad * 2, circumRad * 2);
        
        gc.setFill(Color.BLUE);
        gc.fillOval(centerX - 10, centerY - 10, 20, 20);
        for(int i = 0; i < sides; i++)
            gc.fillOval(xPts[i] - 3, yPts[i] - 3, 6, 6);
    }
    
    private Point2D vector(Point2D pt1, Point2D pt2) {
        return pt1.subtract(pt2).normalize();
    }
    
    private Point2D normalVector(Point2D vector) {
        return new Point2D(-vector.getY(), vector.getX()).normalize();
    }
    
    public boolean ifCollide(Polygon p) {
        double dist = new Point2D(centerX, centerY)
                .distance(p.getCenterX(), p.getCenterY());
        if(dist <= circumRad + p.getCircumRad() + 25) {
            ArrayList<Point2D> axes = new ArrayList<Point2D>();
            addAxes(axes, this);
            addAxes(axes, p);
            
            // if the scalar projections don't overlap, immediately stop
            for(Point2D axis : axes) {
                Scalar s1 = project(this, axis);
                Scalar s2 = project(p, axis);
                if(!s1.ifOverlap(s2))
                    return false;
            }
            return true;
        }
        return false;
    }
    
    private Scalar project(Polygon p, Point2D axis) {
        double dot[] = new double[p.getSides()];
        for(int i = 0; i < p.getSides(); i++) {
            Point2D point = new Point2D(p.getXPts()[i], p.getYPts()[i]);
            dot[i] = point.dotProduct(axis);
        }
        return new Scalar(min(dot), max(dot));
    }
    
    private void addAxes(ArrayList<Point2D> arr, Polygon p) {
        for(int i = 0; i < p.getSides(); i++) {
            Point2D pointStart = new Point2D(p.getXPts()[i], p.getYPts()[i]);
            Point2D pointEnd = new Point2D(p.getXPts()[(i + 1) % p.getSides()]
                    , p.getYPts()[(i + 1) % p.getSides()]);
            Point2D vector = vector(pointStart, pointEnd);
            arr.add(normalVector(vector));
        }
    }
    
    private double min(double[] arr) {
        double min = arr[0];
        for(int i  = 1; i < arr.length; i++) {
            if(arr[i] < min)
                min = arr[i];
        }
        return min;
    }
    
    private double max(double[] arr) {
        double max = arr[0];
        for(int i  = 1; i < arr.length; i++) {
            if(arr[i] > max)
                max = arr[i];
        }
        return max;
    }
    
    
    public void setPos(double x, double y) {
        this.centerX = x;
        this.centerY = y;
        calcPts();
    }
    
    public void setActiveColor(Color activeColor) {
        this.activeColor = activeColor;
    }
    
    public double[] getXPts() {
        return this.xPts;
    }
    
    public double[] getYPts() {
        return this.yPts;
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
    
    public int getSides() {
        return this.sides;
    }
}
