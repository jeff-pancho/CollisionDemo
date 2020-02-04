package main;

public class Scalar {
    private double min;
    private double max;
    
    public Scalar(double min, double max) {
        this.min = min;
        this.max = max;
    }
    
    public boolean ifOverlap(Scalar s) {
        return min < s.max && max > s.min;
    }
    
    public boolean ifOverlap(double min, double max) {
        return this.min < max && this.max > min;
    }
}
