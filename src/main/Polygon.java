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
        gc.setStroke(Color.BLACK);
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
    
    public boolean ifCollide(Polygon p) {
        double dist = new Point2D(centerX, centerY)
                .distance(p.getCenterX(), p.getCenterY());
//        if(dist <= circumRad + p.getCircumRad()) {
            generateLines();
            p.generateLines();
//        }
        return false;
    }
    
    private void generateLines() {
        double axisSize = 300;
        double axisDist = 400;
        for(int i = 0; i < sides; i++) {
            Point2D p1 = new Point2D(xPts[i], yPts[i]);
            Point2D p2 = new Point2D(xPts[(i + 1) % sides], yPts[(i + 1) % sides]);
            Point2D p3 = p2.add(p1.subtract(p2).normalize().multiply(axisDist));
            //vector perpendicular to p1 -> p2
            Point2D p4 = new Point2D(p1.getY() - p3.getY(), p3.getX() - p1.getX());
            lines.add(new Line(p3.add(p4.normalize().multiply(-axisSize))
                    , p3.add(p4.normalize().multiply(axisSize))));
        }
    }
    
    public void deleteLines() {
        lines.clear();
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
}
