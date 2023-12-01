package BallGame;

import Developper.PhysicsEngine;
import Objects.Level;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
    
    public Boolean gameWon = false;
    public boolean gameEnd = false;
    private Timeline timeline = timelineBuilder();
    private double time = 0;
    private double dragStartX, dragStartY;
    private boolean dragging = false;
    Line trajectoryLine = new Line();
    
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
    
    public void runGame(){
        
        trajectoryLine.setStroke(Color.BLACK);
        scene = new Scene(gameRoot, 600, 600);
        
        startMouseDragEvent(scene, trajectoryLine);
        endMouseDragEvent(scene, trajectoryLine);
        createTrajectoryLine(scene, trajectoryLine);
        
        gameRoot.getChildren().setAll(level.ball.getBall(), level.goalLine.line, level.walls.get(1).line, trajectoryLine);
        gameStage.setScene(scene);
        gameStage.setTitle(level.name);
        timeline.play();
        gameStage.showAndWait();
    }
    public void createTrajectoryLine(Scene scene, Line trajectoryLine){
         scene.setOnMouseDragged(event -> {
            if (dragging) {
                double dragX = event.getSceneX() - dragStartX;
                double dragY = event.getSceneY() - dragStartY;

                trajectoryLine.setStartX(level.ball.getBall().getLayoutX() );
                trajectoryLine.setStartY(level.ball.getBall().getLayoutY() );
                trajectoryLine.setEndX(level.ball.getBall().getLayoutX() + level.ball.getRadius() + dragX  );
                trajectoryLine.setEndY(level.ball.getBall().getLayoutY() + level.ball.getRadius() + dragY );
            }
        });     
    }
    public void startMouseDragEvent(Scene scene,Line trajectoryLine){
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragStartX = event.getSceneX();
                dragStartY = event.getSceneY();
                dragging = true;
                gameRoot.getChildren().setAll(level.ball.getBall(), level.goalLine.line, level.walls.get(1).line, trajectoryLine);
            }
        });
    }
    public void endMouseDragEvent(Scene scene,Line trajectoryLine){
        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                
                double velocityX = -event.getSceneX() + dragStartX;
                double velocityY = -event.getSceneY() + dragStartY;
                level.ball.setVelocityX(velocityX);
                level.ball.setVelocityY(velocityY);
                dragging = false;
                gameRoot.getChildren().setAll(level.ball.getBall(), level.goalLine.line, level.walls.get(1).line);
            }
        });
    }   
    private Timeline timelineBuilder(){
        timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> 
        {
        time = time + 0.001;
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
        level.ball.addForce(PhysicsEngine.Friction(level.ball, 2));
        if (PhysicsEngine.collides(level.ball, level.walls.get(1))) { 
            PhysicsEngine.collision(level.ball,level.walls.get(1));
        }
        if (level.ball.getBall().getLayoutX() <= 0 || level.ball.getBall().getLayoutX() >= scene.getHeight() - level.ball.getRadius()) {
            level.ball.setVelocityX(-level.ball.getVelocityX());
        }
        if (level.ball.getBall().getLayoutY() <= 0 || level.ball.getBall().getLayoutY() >= scene.getHeight() - level.ball.getRadius()) {
            level.ball.setVelocityY(-level.ball.getVelocityY());
        }
        gameRoot.getChildren().setAll(level.ball.getBall(), level.goalLine.line, level.walls.get(1).line, trajectoryLine);
                {
                    System.out.println("position x :" +level.ball.getPositionX());
                    System.out.println("position y :" +level.ball.getPositionY());
                    System.out.println("velocity x :" +level.ball.getVelocityX());
                    System.out.println("velocity y :" +level.ball.getVelocityY());
                    System.out.println("acceleration x :" +level.ball.getAccelerationX());
                    System.out.println("acceleration y :" +level.ball.getAccelerationY());
                    System.out.println("force x :" +level.ball.getNetForceX());
                    System.out.println("force y :" +level.ball.getNetForceY());
                }
                
        if (level.ball.getBall().getLayoutY() <= level.goalLine.line.getStartY() + level.ball.getRadius() && level.ball.getVelocityY() < 0) {
            System.out.println("Goal!");
            timeline.stop();
            gameWon = true;
            gameEnd = true;
            gameStage.close();
            }
        if (time>=10000) {
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
