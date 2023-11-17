
import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 2246982
 */
public class Ball{
    
    double radius;
    double mass;
    
    Double positionX;
    Double positionY;
    ArrayList<Double> position;
    
    Double velocityX;
    Double velocityY;
    Vector<Double> velocity;
    
    Circle ball;


    public Ball(double radius, double mass, Double positionX, Double positionY, Double velocityX, Double velocityY) {
        this.radius = radius;
        this.mass = mass;
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        
        position = new ArrayList<>();
        position.add(positionX);
        position.add(positionY);
        
        velocity = new Vector<>();
        velocity.add(velocityX);
        velocity.add(velocityY);
        
        ball = new Circle(radius,Color.WHEAT);
        ball.setStroke(Color.BLACK);
        ball.relocate(positionX,positionY); 
    }
    
    
    
    
    
     public double acceleration(double velocity, double time){
        return  velocity/time;
     
    }
     
     public double force(double acceleration){
        return  acceleration*mass;
       
    }
    
    
    public double speed(){
        return Math.sqrt(velocityX*velocityX + velocityY*velocityY);
    }
    
}
