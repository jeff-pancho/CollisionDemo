package main;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Demo extends Application {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    Canvas canvas;
    GraphicsContext gc;
    Group root;
    Scene scene;
    ArrayList<Polygon> polys;
    Polygon selectedPoly;
    double mouseX;
    double mouseY;
    double xOffset;
    double yOffset;

    public void start(Stage stage) throws Exception {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Group(canvas);
        scene = new Scene(root, WIDTH, HEIGHT);
        polys = new ArrayList<Polygon>();
        
        polys.add(new Polygon(200, 300, 5, 100));
        polys.add(new Polygon(400, 300, 3, 75));
        polys.add(new Polygon(600, 300, 4, 50));
        
        renderPolys();
        
        scene.setOnMousePressed(this::selectPoly);
        scene.setOnMouseDragged(this::dragPoly);
        scene.setOnMouseClicked(this::releasePoly);
        
        stage.setScene(scene);
        stage.setTitle("CollisionDemo");
        stage.show();
    }
    
    public void selectPoly(MouseEvent e) {
        mouseX = e.getSceneX();
        mouseY = e.getSceneY();
        
        for(Polygon p : polys) {
            double dist = Math.sqrt(Math.pow(mouseX - p.getCenterX(), 2) 
                    + Math.pow(mouseY - p.getCenterY(), 2));
            if(dist <= p.getCircumRad()) {
                selectedPoly = p;
                xOffset = mouseX - p.getCenterX();
                yOffset = mouseY - p.getCenterY();
            }
        }
    }
    
    public void dragPoly(MouseEvent e) {
        if(selectedPoly != null) {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            selectedPoly.setPos(mouseX - xOffset, mouseY - yOffset);
            renderPolys();
        }
    }
    
    private void releasePoly(MouseEvent e) {
        selectedPoly = null;
    }
    
    private void renderPolys() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        for(Polygon p : polys)
            p.render(gc);
    }
    
    public void run() {
        launch();
    }
}
