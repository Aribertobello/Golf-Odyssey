package Objects;


import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.shape.Circle;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 2246982
 */
public class Ball extends Body{
    
    double radius;
    Circle ball;
    private ColorPicker colorPicker;

    
    public Ball(double mass, double radius, Double positionX, Double positionY) {
        
        super(mass);
        this.radius = radius;
        this.mass = mass;
        position = new ArrayList<>();
        position.add(positionX);
        position.add(positionY);
        colorPicker = new ColorPicker(Color.WHEAT);
        colorPicker.setOnAction(event -> setBallColor(colorPicker.getValue()));
        ball = new Circle(radius, colorPicker.getValue());
        ball.setStroke(Color.BLACK);
        ball.relocate(positionX,positionY); 
    }
    
    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    

    public void setBallColor(Color ballColor) {
        this.ball.setFill(ballColor);
    }
    
    //getters setters

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Circle getBall() {
        return ball;
    }

    public void setBall(Circle ball) {
        this.ball = ball;
    }

    @Override
    public void setPositionX(double positionX) {
        super.setPositionX(positionX);
        ball.setLayoutX(positionX);
    }

    @Override
    public void setPositionY(double positionY) {
        super.setPositionY(positionY);
        ball.setLayoutY(positionY);
    }

    @Override
    public double[] getBounds() {
        double[] bounds = new double[3];
        bounds[0] = getPositionX();
        bounds[1] = getPositionY();
        bounds[2] = radius;
        
        return bounds;
    }    
}