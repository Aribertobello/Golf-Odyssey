
import Objects.Ball;
import Objects.Level;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 2246982
 */
public class GolfGameTest extends Application{
    
    private Level level;
    boolean gameEnd = false;
    
    public void setLevel(Level level){
        this.level = level;
    }
    
    
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
    
    private double dragStartX, dragStartY;
    private boolean dragging = false;
    private  long startTime;
    private long endTime;
    private double time;
    
    public void run(Stage primaryStage) {
        
        
        
        BorderPane root = new BorderPane();
  
        Label velocityX = new Label();
        Label velocityY = new Label();
        Label velocity = new Label();
        HBox hbox = new HBox(10,velocity);

        Line trajectoryLine = new Line();
        trajectoryLine.setStroke(Color.BLACK);

        root.getChildren().addAll(level.ball.getBall(), level.goalLine.line, level.walls.get(1).line, trajectoryLine);
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

                trajectoryLine.setStartX(level.ball.getBall().getLayoutX() );
                trajectoryLine.setStartY(level.ball.getBall().getLayoutY() );
                trajectoryLine.setEndX(level.ball.getBall().getLayoutX() + level.ball.getRadius() + dragX / 5);
                trajectoryLine.setEndY(level.ball.getBall().getLayoutY() + level.ball.getRadius() + dragY / 5);
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double dragX = -event.getSceneX() + dragStartX;
                double dragY = -event.getSceneY() + dragStartY;

                level.ball.setVelocityX(dragX / 20);
                level.ball.setVelocityY(dragY / 20);
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
                level.ball.getBall().setLayoutX(level.ball.getBall().getLayoutX() + level.ball.getVelocityX());
                level.ball.getBall().setLayoutY(level.ball.getBall().getLayoutY() + level.ball.getVelocityY());

                if (level.ball.getBall().getBoundsInParent().intersects(level.walls.get(1).line.getBoundsInParent())) {
                    level.ball.setVelocityY(-level.ball.getVelocityY());
                 
                    double neededNewtonBreak = 125.0;
                    double speed = level.ball.speed();
                   
                    
                    endTime = System.currentTimeMillis();
                    time = (endTime - startTime) /1000.0;
                    System.out.println(time);
                    
                    
                    double acceleration = level.ball.acceleration(speed, time);
                    double force = level.ball.force(acceleration);
                    System.out.println("Acceleration of ball: " + acceleration);
                    System.out.println("Force of ball: " + force);
                    
                    if(force>neededNewtonBreak){
                        System.out.println("Wall broken");
                         level.walls.get(1).line.setStartX(0);
                         level.walls.get(1).line.setStartY(0);
                         level.walls.get(1).line.setEndX(0);
                         level.walls.get(1).line.setEndY(0);
                    }
                }

                if (level.ball.getBall().getLayoutY() <= level.goalLine.line.getStartY() + level.ball.getRadius() && level.ball.getVelocityY() < 0) {
                    System.out.println("Goal!");
                     
                //    endTime = System.currentTimeMillis();
             //   time = (endTime - startTime) /1000.0;
               //   System.out.println(time);
                    stop();
                    gameEnd = !gameEnd;
                    primaryStage.close();
                  //  double acceleration = ball.acceleration(ball.speed(), time);
                  //  double force = ball.force(acceleration);
                  //  System.out.println("Acceleration of ball: " +acceleration);
                  //  System.out.println("Force of ball: " + force);
              
                }

                if (level.ball.getBall().getLayoutX() <= 0 || level.ball.getBall().getLayoutX() >= scene.getHeight() - level.ball.getRadius()) {
                    level.ball.setVelocityX(-level.ball.getVelocityX());
                }

                if (level.ball.getBall().getLayoutY() <= 0 || level.ball.getBall().getLayoutY() >= scene.getHeight() - level.ball.getRadius()) {
                    level.ball.setVelocityY(-level.ball.getVelocityY());
                }

                
                double speedFactor = 0.99;
                level.ball.setVelocityX (level.ball.getVelocityX()*speedFactor);
                level.ball.setVelocityY (level.ball.getVelocityY()*speedFactor);
                
                velocity.setText(String.valueOf(level.ball.speed()));
                
               //double acceleration = ball.acceleration(ball.speed(), time);
               //double force = ball.force(acceleration);
     
                //velocityY.setText(String.valueOf(ballSpeedY));
                
                //System.out.println(String.valueOf(ballSpeedX));
                

                root.getChildren().setAll(level.ball.getBall(), level.goalLine.line, level.walls.get(1).line, trajectoryLine,hbox);
            }
        };
          
 
        timer.start();


        primaryStage.setScene(scene);
        primaryStage.setTitle("Ball Game");
        
        primaryStage.show();
        
        if(gameEnd){
            primaryStage.close();
        }
        
    }
    
   
    
}
