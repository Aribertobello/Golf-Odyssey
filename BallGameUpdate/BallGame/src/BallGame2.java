
import java.time.Duration;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javafx.util.Duration.seconds;


public class BallGame2 extends Application {

    
    
    private static final double BALL_RADIUS = 10;
    private static final double GOAL_Y = 50;
    private static final double OBSTACLE_WIDTH = 400;

    private double ballSpeedX = 0;
    private double ballSpeedY = 0;
    private double dragStartX, dragStartY;
    private boolean dragging = false;
    
     
      private  long startTime;
        private long endTime;
        private double time;
    
    private Ball ball =  new Ball(BALL_RADIUS, 2.0, 50.0, 400.0, ballSpeedX, ballSpeedY);
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
  
        Label velocityX = new Label();
        Label velocityY = new Label();
        Label velocity = new Label();
        HBox hbox = new HBox(10,velocity);

        Line goalLine = new Line(50, GOAL_Y, 550, GOAL_Y);
        goalLine.setStroke(Color.GREEN);

        Line obstacle = new Line(200, 300, 200 + OBSTACLE_WIDTH, 300);
        obstacle.setStroke(Color.RED);

        Line trajectoryLine = new Line();
        trajectoryLine.setStroke(Color.BLACK);

        root.getChildren().addAll(ball.ball, goalLine, obstacle, trajectoryLine);
        VBox vbox = new VBox(10,root,hbox);
        
       

        Scene scene = new Scene(vbox, 600, 600);

        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragStartX = event.getSceneX();
                dragStartY = event.getSceneY();
                dragging = true;
            }
        });

        scene.setOnMouseDragged(event -> {
            if (dragging) {
                double dragX = event.getSceneX() - dragStartX;
                double dragY = event.getSceneY() - dragStartY;

                trajectoryLine.setStartX(ball.ball.getLayoutX() );
                trajectoryLine.setStartY(ball.ball.getLayoutY() );
                trajectoryLine.setEndX(ball.ball.getLayoutX() + BALL_RADIUS + dragX / 5);
                trajectoryLine.setEndY(ball.ball.getLayoutY() + BALL_RADIUS + dragY / 5);
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double dragX = -event.getSceneX() + dragStartX;
                double dragY = -event.getSceneY() + dragStartY;

                ball.velocityX= dragX / 20;
                ball.velocityY = dragY / 20;
                dragging = false;
                
                startTime = System.currentTimeMillis();    
                
                trajectoryLine.setStartX(0);
                trajectoryLine.setStartY(0);
                trajectoryLine.setEndX(0);
                trajectoryLine.setEndY(0);
            }
        });
         
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.ball.setLayoutX(ball.ball.getLayoutX() + ball.velocityX);
                ball.ball.setLayoutY(ball.ball.getLayoutY() + ball.velocityY);

                if (ball.ball.getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
                    ball.velocityY = -ball.velocityY;
                 
                    double neededNewtonBreak = 125.0;
                    double speed = ball.speed();
                   
                    
                    endTime = System.currentTimeMillis();
                    time = (endTime - startTime) /1000.0;
                    System.out.println(time);
                    
                    
                    double acceleration = ball.acceleration(speed, time);
                    double force = ball.force(acceleration);
                    System.out.println("Acceleration of ball: " + acceleration);
                    System.out.println("Force of ball: " + force);
                    
                    if(force>neededNewtonBreak){
                        System.out.println("Wall broken");
                         obstacle.setStartX(0);
                         obstacle.setStartY(0);
                         obstacle.setEndX(0);
                         obstacle.setEndY(0);
                    }
                }

                if (ball.ball.getLayoutY() <= GOAL_Y + BALL_RADIUS && ball.velocityY < 0) {
                    System.out.println("Goal!");
                     
                //    endTime = System.currentTimeMillis();
             //   time = (endTime - startTime) /1000.0;
               //   System.out.println(time);
                    stop();
                    
                  //  double acceleration = ball.acceleration(ball.speed(), time);
                  //  double force = ball.force(acceleration);
                  //  System.out.println("Acceleration of ball: " +acceleration);
                  //  System.out.println("Force of ball: " + force);
              
                }

                if (ball.ball.getLayoutX() <= 0 || ball.ball.getLayoutX() >= scene.getWidth() - BALL_RADIUS) {
                    ball.velocityX = -ball.velocityX;
                }

                if (ball.ball.getLayoutY() <= 0 || ball.ball.getLayoutY() >= scene.getHeight() - BALL_RADIUS) {
                    ball.velocityY = -ball.velocityY;
                }

                
                double speedFactor = 0.99;
                ball.velocityX *= speedFactor;
                ball.velocityY *= speedFactor;
                
                velocity.setText(String.valueOf(ball.speed()));
                
               //double acceleration = ball.acceleration(ball.speed(), time);
               //double force = ball.force(acceleration);
     
                //velocityY.setText(String.valueOf(ballSpeedY));
                
                //System.out.println(String.valueOf(ballSpeedX));
                

                root.getChildren().setAll(ball.ball, goalLine, obstacle, trajectoryLine,hbox);
            }
        };
          
 
        timer.start();


        primaryStage.setScene(scene);
        primaryStage.setTitle("Ball Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    

}
