package Objects;

import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Trap extends Obstacle {
    public Rectangle rectangle;
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
         //  rectangle.setWidth(rectangle.getWidth() * 0.95); //just to test and see for us
         //  rectangle.setHeight(rectangle.getHeight() * 0.95);
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

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
        this.rectangle = new Rectangle(startX, startY, endX - startX, endY - startY);
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
        this.rectangle.setLayoutY(startY);
        this.rectangle = new Rectangle(startX, startY, endX - startX, endY - startY);
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
        this.rectangle.setWidth(endX - startX);
        this.rectangle = new Rectangle(startX, startY, endX - startX, endY - startY);
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
        this.rectangle.setHeight(endY - startY);
        this.rectangle = new Rectangle(startX, startY, endX - startX, endY - startY);
    }

    public String getTrapType() {
        return trapType;
    }

    public void setTrapType(String trapType) {
        this.trapType = trapType;
    }

    
    
 
    
}
