
package Objects;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;


public class PowerUp {
    private double x;
    private double y;
    private double radius;
    private String type;
    private int numberOfSizePowerups;
    private int numberOfForcePowerUps;
    

    public PowerUp(int numberOfSizePowerups,int numberOfForcePowerUps){
    this.numberOfSizePowerups=numberOfSizePowerups;
    this.numberOfForcePowerUps=numberOfForcePowerUps;
    }
    
    public int getnumberOfSizePowerups(){
    return numberOfSizePowerups;
    }
    
    public int getnumberOfForcePowerUps(){
    return numberOfForcePowerUps;
    }
}


