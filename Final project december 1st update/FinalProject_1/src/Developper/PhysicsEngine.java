/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Developper;

import Objects.Body;
import Objects.Wall;
import java.util.Vector;

/**
 *
 * @author aribe
 */
public class PhysicsEngine {
    
    static double G = 9.8;
    
    private static double NormalForce(Body body) {
        return body.getMass()*G;
    }
    public static double velocityIntegral(double time,double force,double mass, double velocity0,double position0){
        return 1/2*(force/mass)*time*time+velocity0*time+position0;
    }
    public static double accelerationIntegral(double time,double force,double mass, double velocity0){
        return (force/mass)*time+velocity0;
    }
    public static Vector<Double> Friction(Body body, double frictionCoefficient){
        Vector<Double> friction = new Vector<>();
        if(body.getVelocityX()==0&&body.getVelocityY()==0){
            friction.add(0.0);
            friction.add(0.0);
            
        }else{
            double k = 1/(Math.abs(body.getVelocityX())+Math.abs(body.getVelocityY()));
            
            friction.add(-body.getVelocityX()*NormalForce(body)*frictionCoefficient*k);
            friction.add(-body.getVelocityY()*NormalForce(body)*frictionCoefficient*k);
        }   
        return friction;
    }

    
    /**
     * 1. First you need to "enter the frame of reference of the 2 colliding particles". To do that you add the 2 speeds together (as vectors), divide by 2 and subtract from both vectors. You save this vector for later. To see why, think about the point that is in the middle of the line connecting the centers of the 2 objects. This point must be stationary.
        2. Now to find the new directions draw a line perpendicular to the line that connects the centers of the 2 spheres and flip the two (converted) velocity vectors along this line.
        3. To find the new speed values you need to consider that the total momentum must be preserved (m1*v1+m2*v2 == m1*v'1 + m2*v'2) and the kinetic energy must be preserved (m1*v1^2/2 + m2*v2^2/2 == m1*v'1^2/2 + m2*v'2^2/2). This gives you a system of 2 equations with 2 unknowns (the 2 new speeds).
        4. Once you know the new vectors, you add back the vector you subtracted in step 1 to get these vectors in the frame of reference where the box is stationary.

        The "ugly equations" shown here are these steps performed on two arbitrary vectors, using vector algebra. When you look at the steps above, you can see why their derivation (and their final look) is such a mess.
     * @param body1
     * @param body2 
     */
    
    public static double dotProduct(Vector<Double> vector1, Vector<Double> vector2){
        double result = 0.0;
        for (int i = 0; i < vector1.size(); i++) {
            result += vector1.get(i) * vector2.get(i);
        }
        return result;
     }
    public static Vector<Double> vectorNormal(Vector<Double> vector){
        Vector<Double> normal = new Vector<Double>();
        normal.add(-vector.get(1));
        normal.add(vector.get(0));
        return normal;
    }
    public static Vector<Double> vectorProjection(Vector<Double> projectedVector, Vector<Double> projectionVector) {
        double dotProduct = dotProduct(projectedVector, projectionVector);
        double magnitudeSquared = dotProduct(projectionVector, projectionVector);
        double scaleFactor = dotProduct / magnitudeSquared;
        Vector<Double> projection = new Vector<Double>();
        for (int i = 0; i < projectedVector.size(); i++) {
            projection.add(scaleFactor * projectionVector.get(i));
        }
        return projection;
    }
    public static Vector<Double> vectorReflexion(Vector<Double> vector, Vector<Double> reflexionAxisVector) {
        Vector<Double> projection = vectorProjection(vector, vectorNormal(reflexionAxisVector));
        Vector<Double> reflection = new Vector<Double>();
        for (int i = 0; i < vector.size(); i++) {
            reflection.add(vector.get(i)-2*projection.get(i));
        }
        return reflection;
    }
    public static void collision(Body body1, Body body2){
        
        Vector<Double> reflexionVector = vectorReflexion(body1.getVelocity(),  body2.DirectionVector());
            body1.setVelocityX(reflexionVector.get(0));
            body1.setVelocityY(reflexionVector.get(1));
        
    }
    public static boolean collides(Body body1, Body body2){
        
        if(body2 instanceof Wall){
        double h = body1.getBounds()[0];
        double k = body1.getBounds()[1];
        double r = body1.getBounds()[2];
        double a = body2.getBounds()[0];
        double b = body2.getBounds()[2];
        
        
        if(Double.isNaN(a)){
            a = 999999999.0;
        }
        
        
        if(!((body1.speed()==0)&&(body2.speed()==0))){
            double x = (-Math.sqrt(-b*b-2*b*h*a+2*b*k-h*h*a*a+2*h*k*a-k*k+a*a*r*r+r*r)-b*a+h+k*a)/(a*a+1);
            if(((Wall) body2).endX<x||((Wall) body2).startX>x){
                x = Double.NaN;
            }
            if(!(Double.isNaN(x))){
                return true;
            } 
        }
        }
        return false;
    }
}
