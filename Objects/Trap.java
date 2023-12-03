package Objects;

import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Trap extends Obstacle {
    private Rectangle rectangle;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double frictionCoefficient;
    private String trapType;
   
    public Trap(double startX, double startY, double endX, double endY,double frictionCoefficient,String trapType) {
        super();
        this.trapType=trapType;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.position.add((endX + startX) / 2);
        this.position.add((endY + startY) / 2);
        this.mass = 1000000000; // Modify mass if needed
        this.frictionCoefficient = frictionCoefficient;
        rectangle = new Rectangle(startX, startY, endX - startX, endY - startY);
    }
    
     public void updateVisualization() {
        if (trapType.equalsIgnoreCase("sand")) {
            rectangle.setFill(Color.YELLOW);
        } else if (trapType.equalsIgnoreCase("ice")) {
            rectangle.setFill(Color.LIGHTBLUE);
        } else {
            rectangle.setFill(Color.BLACK); 
        }
    }
    
    public Rectangle getRectangle() {
        return rectangle;
    }
    
     public void setFrictionCoefficient(double frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }
    
    public double getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public boolean isBallInside(Ball ball) {
        return rectangle.getBoundsInParent().intersects(ball.getBall().getBoundsInParent());
    }

 
    
}
