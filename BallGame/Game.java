package BallGame;

import Developper.PhysicsEngine;
import Objects.Level;
import Objects.Trap;
import Objects.Wall;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
scale
1 pixel = 1cm
100 pixel = 1m
 */
public class Game {

    private Level level;
    Stage gameStage = new Stage();
    Scene scene;
    BorderPane gameRoot = new BorderPane();
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public Boolean gameWon = false;
    public boolean gameEnd = false;
    boolean attemptLimitReached = false;
    boolean timeLimitReached = false;
    private Timeline timeline = null;
    private int numberOfTries ;
    private double time = 0;
    private double dragStartX, dragStartY;
    private boolean dragging = false;
    Line trajectoryLine = new Line();
    private HBox time_AttempsBox = new HBox();
      
    public Game(Level level) {
        this.level = level;
    }

    public Game() {
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public BorderPane getRoot(){
        return gameRoot;
    }
    
    public void runGame() {
        numberOfTries = level.maxAttempts;
        timeline = timelineBuilder();
        trajectoryLine.setStroke(Color.BLACK);
        scene = new Scene(gameRoot, 600, 600);
     //------------------------ 
         
          //timeBox.getChildren().addAll(new Label("Time: "+time), timeLabel);
         time_AttempsBox.setAlignment(Pos.CENTER); // Align the time label in the center of the HBox

         // Add the time label container (HBox) to the top of the BorderPane
         if(level.index!=0){gameRoot.setTop(time_AttempsBox);}
   //------------------
        startMouseDragEvent(scene, trajectoryLine);
        endMouseDragEvent(scene, trajectoryLine);
        createTrajectoryLine(scene, trajectoryLine);

        gameRoot.getChildren().addAll( level.goalLine.line, trajectoryLine);
        for (int i = 1; i < level.walls.size(); i++) {
            gameRoot.getChildren().add(level.walls.get(i).line);
        }
        
         for (Trap trap : level.traps) {
           trap.updateVisualization();
        gameRoot.getChildren().add(trap.getRectangle());
}
        gameRoot.getChildren().add(level.ball.getBall());
        gameStage.setScene(scene);
        gameStage.setTitle(level.name);
        timeline.play();
        gameStage.showAndWait();
    }

    public void createTrajectoryLine(Scene scene, Line trajectoryLine) {
        scene.setOnMouseDragged(event -> {
            if (dragging) {
                double dragX = event.getSceneX() - dragStartX;
                double dragY = event.getSceneY() - dragStartY;

                trajectoryLine.setStartX(level.ball.getBall().getLayoutX());
                trajectoryLine.setStartY(level.ball.getBall().getLayoutY());
                trajectoryLine.setEndX(level.ball.getBall().getLayoutX() + level.ball.getRadius() + dragX);
                trajectoryLine.setEndY(level.ball.getBall().getLayoutY() + level.ball.getRadius() + dragY);
            }
        });
    }

    public void startMouseDragEvent(Scene scene, Line trajectoryLine) {
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragStartX = event.getSceneX();
                dragStartY = event.getSceneY();
                dragging = true;
             
                if(numberOfTries!=0){
                numberOfTries = numberOfTries - 1;
                }
                else
                    numberOfTries=-1;    
            }
        });
    }
    public void endMouseDragEvent(Scene scene, Line trajectoryLine) {
        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                trajectoryLine.setStartX(0);
                trajectoryLine.setStartY(0);
                trajectoryLine.setEndX(0);
                trajectoryLine.setEndY(0);
                double momentumX = (-event.getSceneX() + dragStartX)/level.ball.getMass();
                double momentumY = (-event.getSceneY() + dragStartY)/level.ball.getMass();
                level.ball.setVelocityX(momentumX);
                level.ball.setVelocityY(momentumY);
                dragging = false;
            }
        });
    }

    private Timeline timelineBuilder() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1), e ->
            {
            time = time + 0.001;
            time_AttempsBox.getChildren().setAll(new Label("Time Left: " + df.format(level.timeLimit - time)+ " s " + " Attempts: " + numberOfTries));
           
            if(level.ball.speed()<10){level.ball.setVelocityX(0);level.ball.setVelocityY(0);level.ball.getForces().clear();}
            
            level.ball.setPositionX(
                    PhysicsEngine.velocityIntegral(
                            0.001,
                            level.ball.netForce().get(0),
                            level.ball.getMass(), level.ball.getVelocityX(),
                            level.ball.getPositionX()));
            level.ball.setPositionY(
                    PhysicsEngine.velocityIntegral(
                            0.001,
                            level.ball.netForce().get(1),
                            level.ball.getMass(),
                            level.ball.getVelocityY(),
                            level.ball.getPositionY()));
            level.ball.setVelocityX(
                    PhysicsEngine.accelerationIntegral(0.001, level.ball.getNetForceX(), level.ball.getMass(), level.ball.getVelocityX()));
            level.ball.setVelocityY(
                    PhysicsEngine.accelerationIntegral(0.001, level.ball.getNetForceY(), level.ball.getMass(), level.ball.getVelocityY()));
            level.ball.getForces().clear();
            level.ball.addForce(PhysicsEngine.Friction(level.ball, 10));
            for (int i = 1; i < level.walls.size(); i++) {
                if (PhysicsEngine.collides(level.ball, level.walls.get(i))) {
                    PhysicsEngine.collision(level.ball, level.walls.get(i));
                }
            }
            if (level.ball.getBall().getLayoutX() <= 0 || level.ball.getBall().getLayoutX() >= scene.getHeight() - level.ball.getRadius()) {
                level.ball.setVelocityX(-level.ball.getVelocityX());
            }
            if (level.ball.getBall().getLayoutY() <= 0 || level.ball.getBall().getLayoutY() >= scene.getHeight() - level.ball.getRadius()) {
                level.ball.setVelocityY(-level.ball.getVelocityY());
            }
            {
                /*
                System.out.println("position x :" + level.ball.getPositionX());
                System.out.println("position y :" + level.ball.getPositionY());
                System.out.println("velocity x :" + level.ball.getVelocityX());
                System.out.println("velocity y :" + level.ball.getVelocityY());
                System.out.println("acceleration x :" + level.ball.getAccelerationX());
                System.out.println("acceleration y :" + level.ball.getAccelerationY());
                System.out.println("force x :" + level.ball.getNetForceX());
                System.out.println("force y :" + level.ball.getNetForceY());
            */
            }
              for (Trap trap : level.traps) {
            if (trap.isBallInside(level.ball)) {
                
            double frictionCoefficient = trap.getFrictionCoefficient();
            level.ball.addForce(PhysicsEngine.Friction(level.ball, frictionCoefficient)); 
                System.out.println(level.ball.speed());
            }
            }
            if (PhysicsEngine.collides(level.ball, level.goalLine)) {
                System.out.println("Goal!");
                timeline.stop();
                gameWon = true;
                gameEnd = true;
                gameStage.close();
            }
            if (time >= level.timeLimit) {
                System.out.println("fail");
                timeline.stop();
                timeLimitReached = true;
                gameWon = false;
                gameEnd = true;
                gameStage.close();
            }
            if (numberOfTries == -1) {
                attemptLimitReached = true;
                System.out.println("fail");
                timeline.stop();
                gameWon = false;
                gameEnd = true;
                gameStage.close();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }
    

}
