/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objects;

import java.util.Vector;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author 2246982
 */
public class Wall extends Obstacle{
    
    public Line line;
    
    public double startX;
    public double endX;
    public double endY;
    public double startY;
    
    
 
    private double Length(){return line.getEndX();}
    public Wall(double startX, double startY, double endX, double endY,boolean isBreakable) {
        super();
        this.isbreakable = isBreakable;
        this.startX = startX;
        this.endX = endX;
        this.endY = endY;
        this.startY = startY;
        this.position.add((endX+startX)/2);
        this.position.add((endY+startY)/2);
        this.mass = 100000000;
        line = new Line(startX,startY,endX,endY);
        if(isBreakable){
            line.setStroke(Color.RED);
            this.strength = 500;
        }else{
            this.strength = Double.POSITIVE_INFINITY;
        }
        
    }
    
    public boolean isbreakableWall(){
        return isbreakable;
    } 
     
       public void setStartX(double startX) {
        line.setStartX(startX);
        this.startX = startX;
    }
    public void setEndX(double endX) {
        line.setEndX(endX);
        this.endX = endX;
    }
    public void setEndY(double endY) {
        line.setEndY(endY);
        this.endY = endY;
    }
    public void setStartY(double startY) {
        line.setStartY(startY);
        this.startY = startY;
    }
    public double getStrength(){
        return this.strength;
    } 
    
    public Vector<Double> DirectionVector(){
        Vector<Double> direction = new Vector<Double>();
        double deltaX = endX-startX;
        double deltaY = endY-startY;
        double directionMagnitude = Math.sqrt(deltaX*deltaX+deltaY*deltaY) ;
        direction.add(deltaX/directionMagnitude);
        direction.add(deltaY/directionMagnitude);
        return direction;  
    }
    /**
     * 
     * @return array of coefficients of the equation of the line in the form Ax + By = C; 
     */
    @Override
     public double[] getBounds(){
        double deltaX = endX-startX;
        double deltaY = endY-startY;
        double slope = deltaY/deltaX;
        double B = -1;
        double[] bounds = new double[3];
        if(Double.isInfinite(slope)){slope=1;B=0;}
        bounds[0] = slope;
        bounds[1] = B;
        bounds[2] = B*startY+slope*startX;
        return bounds;
    } 

      
}
