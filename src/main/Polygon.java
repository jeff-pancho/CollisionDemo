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
    
    private ArrayList<Line> lines;
    
    public Polygon(double centerX, double centerY, int sides, double circumRad) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.sides = sides;
        this.xPts = new double[sides];
        this.yPts = new double[sides];
        this.circumRad = circumRad;
        this.dir = 0;
        lines = new ArrayList<Line>();
        calcPts();
    }
    
    public void calcPts() {
        for(int i = 0; i < sides; i++) {
            xPts[i] = centerX + Math.cos(dir + (Math.PI * 2 / sides) * i) * circumRad;
            yPts[i] = centerY + Math.sin(dir + (Math.PI * 2 / sides) * i) * circumRad;
        }
    }
    
    public void render(GraphicsContext gc) {
//        gc.setStroke(Color.BLACK);
        gc.strokePolygon(xPts, yPts, xPts.length);
        
        gc.setStroke(Color.RED);
        gc.strokeOval(centerX - circumRad, centerY - circumRad, circumRad * 2, circumRad * 2);
        
        gc.setFill(Color.BLUE);
        gc.fillOval(centerX - 10, centerY - 10, 20, 20);
        for(int i = 0; i < sides; i++)
            gc.fillOval(xPts[i] - 3, yPts[i] - 3, 6, 6);
        for(Line l : lines)
            l.render(gc);
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
        if(dist <= circumRad + p.getCircumRad() + 100) {
            for(int i = 0; i < sides; i++) {
                Point2D pointStart = new Point2D(xPts[i], yPts[i]);
                Point2D pointEnd = new Point2D(xPts[(i + 1) % sides], yPts[(i + 1) % sides]);
                Point2D vector = vector(pointStart, pointEnd);
                Point2D axis = normalVector(vector);
                
//                System.out.println(vector.getX() + " " + vector.getY() + " " 
//                        + axis.getX() + " " + axis.getY()); 
                
                double[] dot1 = new double[sides];
                for(int j = 0; j < sides; j++) {
                    Point2D point = new Point2D(xPts[j], yPts[j]);
                    dot1[j] = point.dotProduct(axis);
                }
                
                double min1 = min(dot1);
                double max1 = max(dot1);
                
                double[] dot2 = new double[p.getSides()];
                for(int j = 0; j < p.getSides(); j++) {
                    Point2D point = new Point2D(p.getXPts()[j], p.getYPts()[j]);
                    dot2[j] = point.dotProduct(axis);
                }
                
                double min2 = min(dot2);
                double max2 = max(dot2);
                
                if(!(min1 < max2 && max1 < min1)) {
                    return false;
                }
            }
            return true;
        }
        return false;
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
