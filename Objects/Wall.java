/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author 2246982
 */
public class Wall extends Objects.Obstacle{
    
    public Line line;
    
    private double startX;
    private double endX;
    private double endY;
    private double startY;
    
    private double Length;

    public Wall(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.endX = endX;
        this.endY = endY;
        this.startY = startY;
        line = new Line(startX,startY,endX,endY);
    }

    public Wall(double startX, double startY, double Length) {
        this.startX = startX;
        this.startY = startY;
        this.Length = Length;
        this.endX = startX +Length;
        this.endY = startY;
        
        line = new Line(startX,startY,endX,endY);
    }
    
    
    
    
    
}
