
import Objects.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
public class GolfGameTest {
    
    private Level level;
    public Boolean gameWon = false;
    public boolean gameEnd = false;
    private double dragStartX, dragStartY;
    private boolean dragging = false;
    private  long startTime;
    private long endTime;
    private double time;
    
    
    public void setLevel(Level level){
        this.level = level;
    }
    public void runGame() {
        
        Stage gameStage = new Stage();
        BorderPane root = new BorderPane();
        Label velocity = new Label();
        HBox hbox = new HBox(10,velocity);

        Line trajectoryLine = new Line();
        trajectoryLine.setStroke(Color.BLACK);

        root.getChildren().addAll(level.ball.getBall(), level.goalLine.line, trajectoryLine);
        for (int i = 1; i < level.walls.size(); i++) {
            root.getChildren().add(level.walls.get(i).line);
        }
        
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
  for (Wall wall : level.walls){
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
  }
                if (level.ball.getBall().getLayoutY() <= level.goalLine.line.getStartY() + level.ball.getRadius() && level.ball.getVelocityY() < 0) {
                    System.out.println("Goal!");
                     
                    stop();
                    gameWon = true;
                    gameEnd = true;
                    gameStage.close();
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
              //  root.getChildren().setAll(level.ball.getBall(), level.goalLine.line,trajectoryLine,hbox);
                
                /*for (int i = 1; i < level.walls.size(); i++) {
                    root.getChildren().setAll(level.walls.get(i).line);
                }*/
            }
        };
        timer.start();
        
        gameStage.setScene(scene);
        gameStage.setTitle(level.name);
        gameStage.showAndWait();
    }
    
   
    
}
