package Objects;






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


    public Ball(double mass, double radius, Double positionX, Double positionY, Double velocityX, Double velocityY) {
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //getters setters

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Double getPositionX() {
        return positionX;
    }

    public void setPositionX(Double positionX) {
        this.positionX = positionX;
    }

    public Double getPositionY() {
        return positionY;
    }

    public void setPositionY(Double positionY) {
        this.positionY = positionY;
    }

    public ArrayList<Double> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Double> position) {
        this.position = position;
    }

    public Double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(Double velocityX) {
        this.velocityX = velocityX;
    }

    public Double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(Double velocityY) {
        this.velocityY = velocityY;
    }

    public Vector<Double> getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector<Double> velocity) {
        this.velocity = velocity;
    }

    public Circle getBall() {
        return ball;
    }

    public void setBall(Circle ball) {
        this.ball = ball;
    }
}
