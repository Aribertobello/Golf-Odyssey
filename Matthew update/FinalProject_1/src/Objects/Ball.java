package Objects;
import BallGame.Game;
import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.ColorPicker;
import static javafx.scene.paint.Color.WHEAT;


public class Ball extends Body{
    
    double radius;
    Circle ball;
    public ColorPicker ballColorPicker;
    Game game = new Game();

    
    public Ball(double mass, double radius, Double positionX, Double positionY) {
        
        super(mass);
        this.radius = radius;
        this.mass = mass;
        position = new ArrayList<>();
        position.add(positionX);
        position.add(positionY);
        ball = new Circle(radius);
     
        ball.setStroke(Color.BLACK);
        ball.relocate(positionX,positionY); 
      
        ball.setStroke(Color.BLACK);
       // ball.setFill(Color.WHEAT); // Set initial color
        ball.relocate(positionX, positionY);
     // ballColorPicker = new ColorPicker(Color.WHEAT);
        ball.setFill(game.sharedColor);
      
    }
    //getters setters
    
      public ColorPicker getBallColorPicker() {
        return ballColorPicker;
    }
      
        public void setBallColor(Color ballColor) {
        ball.setFill(ballColor);
    }

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

