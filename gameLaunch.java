
import Objects.Ball;
import Objects.Level;
import Objects.Wall;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class gameLaunch extends Application{
    
    
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        
        double ballSpeedX = 0;
        double ballSpeedY = 0;
        double BALL_RADIUS = 10;
        long startTime;
        long endTime;
        double time;

    
        Ball ball =  new Ball(BALL_RADIUS, 2.0, 50.0, 400.0, ballSpeedX, ballSpeedY);
        
        
        Wall finishLine = new Wall(50, 50, 500);
        
        finishLine.line.setStroke(Color.GREEN);
        Wall wall = new Wall(100, 300, 300);

        Level level_1 = new Level(ball,finishLine,wall);
        
        GolfGameTest golfgame = new GolfGameTest();
        golfgame.setLevel(level_1);
        golfgame.start(stage);
    }
    
    
    
    
}
