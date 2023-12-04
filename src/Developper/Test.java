/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Developper;

import Objects.Ball;
import Objects.Level;
import Objects.Wall;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author aribe
 */
public class Test {
    
    
    public static void main(String[] args){
        Ball ball = new Ball(2, 4, 200.0, 196.0);
        Wall wall = new Wall(200, 200, 600, 200,false);
        ball.setVelocityX(0);
        ball.setVelocityY(-100);
        System.out.println(ball.getVelocityX()+" "+ball.getVelocityY()); 
        System.out.println(PhysicsEngine.vectorNormal(wall.DirectionVector()));
        if(PhysicsEngine.collides(ball, wall)){
            PhysicsEngine.collision(ball, wall);
        }
        System.out.println(ball.getVelocityX()+" "+ball.getVelocityY());
    }
    
    
    
}
