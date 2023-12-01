package Objects;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author aribe
 */
public class Body {
    
    public Body() {
        forces = new ArrayList<Vector<Double>>();
        position = new ArrayList<Double>();
        velocity = new Vector<Double>();
        
        velocity.add(0.0);
        velocity.add(0.0);
    }
    public Body(double mass) {
        this.mass = mass;
        
        forces = new ArrayList<Vector<Double>>();
        position = new ArrayList<Double>();
        velocity = new Vector<Double>();
        
        velocity.add(0.0);
        velocity.add(0.0);
    }
    
    double mass;
    /**
     * Position of the center of mass
     */
    ArrayList<Double> position;
    ArrayList<Vector<Double>> forces;
    
    Vector<Double> velocity;
    public Vector<Double> netForce(){
        double netX = 0;
        double netY = 0;
        for(Vector<Double> force : forces){
            netX = netX + force.get(0);
            netY = netY + force.get(1);
        }
        Vector<Double> netForce = new Vector<>();
        netForce.add(netX);
        netForce.add(netY);
        return netForce;
    }
    public Vector<Double> acceleration(){
        Vector<Double> acceleration = new Vector<>();
        acceleration.add(this.netForce().get(0)/mass);
        acceleration.add(this.netForce().get(1)/mass);
        return acceleration;
    }
    public Vector<Double> momentum(){
        Vector<Double> momentum = new Vector<>();
        momentum.add(this.velocity.get(0)*mass);
        momentum.add(this.velocity.get(1)*mass);
        return momentum;
    }
    
    //Magnitudes--------------------------------------------
    public double speed(){
        return Math.sqrt(
                velocity.get(0)*velocity.get(0)+velocity.get(1)*velocity.get(1));
    }
    public double NetForce(){
        return Math.sqrt(
                netForce().get(0)*netForce().get(0)+netForce().get(1)*netForce().get(1));
    }
    public double Acceleration(){
        return Math.sqrt(
                acceleration().get(0)*acceleration().get(0)+acceleration().get(1)*acceleration().get(1));
    }
    public double kE(){
        return mass*speed()*speed()/2;
    }
    //------------------------------------------------------

    public double getMass() {
        return mass;
    }
    public ArrayList<Vector<Double>> getForces() {
        return forces;
    }
    public Vector<Double> getVelocity() {
        return velocity;
    }
    public Vector<Double> DirectionVector(){
        Vector<Double> direction = new Vector<Double>();
        double directionMagnitude = Math.sqrt(getVelocityX()*getVelocityX()+getVelocityY()*getVelocityY()) ;
        for (Double v : velocity) {
            direction.add(v/directionMagnitude);
        }
        return direction;
    }
    public double[] getBounds(){
        return null; // to be overridden by children
    }
    public double getPositionX(){
        return position.get(0);
    }
    public double getPositionY(){
        return position.get(1);
    }
    public double getVelocityX(){
        return velocity.get(0);
    }
    public double getVelocityY(){
        return velocity.get(1);
    }
    public double getMomentumX(){
        return velocity.get(1);
    }
    public double getMomemntumY(){
        return velocity.get(1);
    }
    public double getNetForceX(){
        return netForce().get(0);
    }
    public double getNetForceY(){
        return netForce().get(1);
    }
    public double getAccelerationX(){
        return acceleration().get(0);
    }
    public double getAccelerationY(){
        return acceleration().get(1);
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public void setPositionX(double positionX){
        this.position.set(0, positionX);
    }
    public void setPositionY(double positionY){
        this.position.set(1, positionY);
    }
    public void setVelocityX(double velocityX){
        this.velocity.set(0, velocityX);
    }
    public void setVelocityY(double velocityY){
        this.velocity.set(1, velocityY);
    }
    
    
    public void addForce(double forceX, double forceY){
        Vector<Double> force = new Vector<>();
        force.add(forceX);
        force.add(forceY);
        forces.add(force);
    }
    public void addForce(Vector<Double> force){
        forces.add(force);
    }
    
}
