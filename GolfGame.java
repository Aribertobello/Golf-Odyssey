


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import Objects.Ball;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 2246982
 */
public class GolfGame extends Application{
    
    public static void main(String[] args){
        launch(args);
    }
    
    public void start(Stage primaryStage){

        primaryStage.setTitle("Menu");
        primaryStage.setScene(createScene(primaryStage));
        primaryStage.show(); 
    }
    
    
    private Scene createScene(Stage stage){
        final double WIDTH = 300.0, HEIGHT = 200.0;
        
        MenuBar menuBar = new MenuBar();
        
        Menu fileMenu = new Menu("Exit");
        MenuItem exitItem = new MenuItem("Exit");
        fileMenu.getItems().add(exitItem);
        
        exitItem.setOnAction(event -> {
        stage.close();
        });
        
        menuBar.getMenus().addAll(fileMenu);
        
        Button play = new Button("PLAYYYYY");
        play.setOnAction(poop -> 
        {
            stage.close();
           run(stage);
        });
        HBox btns  = new HBox(play);
        btns.setAlignment(Pos.CENTER);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(btns);
        
        Scene scene = new Scene(borderPane,WIDTH , HEIGHT);
        
        
        return scene;
    }
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

    public void run(Stage primaryStage) {
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

        root.getChildren().addAll(ball.getBall(), goalLine, obstacle, trajectoryLine);
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

                trajectoryLine.setStartX(ball.getBall().getLayoutX() );
                trajectoryLine.setStartY(ball.getBall().getLayoutY() );
                trajectoryLine.setEndX(ball.getBall().getLayoutX() + BALL_RADIUS + dragX / 5);
                trajectoryLine.setEndY(ball.getBall().getLayoutY() + BALL_RADIUS + dragY / 5);
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double dragX = -event.getSceneX() + dragStartX;
                double dragY = -event.getSceneY() + dragStartY;

                ball.setVelocityX(dragX / 20);
                ball.setVelocityY(dragY / 20);
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
                ball.getBall().setLayoutX(ball.getBall().getLayoutX() + ball.getVelocityX());
                ball.getBall().setLayoutY(ball.getBall().getLayoutY() + ball.getVelocityY());

                if (ball.getBall().getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
                    ball.setVelocityY(-ball.getVelocityY());
                 
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

                if (ball.getBall().getLayoutY() <= GOAL_Y + BALL_RADIUS && ball.getVelocityY() < 0) {
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

                if (ball.getBall().getLayoutX() <= 0 || ball.getBall().getLayoutX() >= scene.getHeight() - BALL_RADIUS) {
                    ball.setVelocityX(-ball.getVelocityX());
                }

                if (ball.getBall().getLayoutY() <= 0 || ball.getBall().getLayoutY() >= scene.getHeight() - BALL_RADIUS) {
                    ball.setVelocityY(-ball.getVelocityY());
                }

                
                double speedFactor = 0.99;
                ball.setVelocityX (ball.getVelocityX()*speedFactor);
                ball.setVelocityY (ball.getVelocityY()*speedFactor);
                
                velocity.setText(String.valueOf(ball.speed()));
                
               //double acceleration = ball.acceleration(ball.speed(), time);
               //double force = ball.force(acceleration);
     
                //velocityY.setText(String.valueOf(ballSpeedY));
                
                //System.out.println(String.valueOf(ballSpeedX));
                

                root.getChildren().setAll(ball.getBall(), goalLine, obstacle, trajectoryLine,hbox);
            }
        };
          
 
        timer.start();


        primaryStage.setScene(scene);
        primaryStage.setTitle("Ball Game");
        primaryStage.show();
    }
    
}
