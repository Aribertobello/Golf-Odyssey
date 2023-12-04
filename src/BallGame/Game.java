package BallGame;

import Developper.PhysicsEngine;
import Objects.Ball;
import Objects.Level;
import Objects.PowerUp;
import Objects.Trap;
import java.text.DecimalFormat;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    
    private Timeline timeline = timelineBuilder();
    private double time = 0;
    
    public boolean gameWon = false;
    public boolean gameEnd = false;
    public boolean displayPhysics = false;
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private Timeline timelineEffectTimer;
    
    
    private double dragStartX, dragStartY;
    private boolean dragging = false;
    Line trajectoryLine = new Line();
    
    private HBox time_AttempsBox = new HBox();
    private HBox TopPane = new HBox(15);
    private HBox MenuBox = new HBox();
    VBox PhysicsBox = new VBox();
    
    private int Size_PW;
    private int Force_PW;
    private long timeElapsed;
    private long finish;
    private long start;
    private boolean  sizeButtonClicked;
    private long lastClickedTime;
    
    private double originalRadius;
    private double originalMass;
 
    private Circle Size;
    private Circle Force;
    VBox PowerUpsBox = new VBox(3);
      
    public Game(Level level) {
        this.level = level;
    }
    public Game() {
    }
    
    public int getNumberOfTries(){
    return level.attempts;
    }
    
    public Level getLevel() {
        return level;
    }
    public BorderPane getRoot(){
        return gameRoot;
    }
    public void setLevel(Level level) {
        this.level = level;
    }

    public void runGame() {
       
        trajectoryLine.setStroke(Color.BLACK);
        scene = new Scene(gameRoot, 600, 600);
        
        startMouseDragEvent(scene, trajectoryLine);
        endMouseDragEvent(scene, trajectoryLine);
        createTrajectoryLine(scene, trajectoryLine);
        
        powerupBuilder();
        
        time_AttempsBox.setAlignment(Pos.CENTER);
        MenuBar menuBar = menuBar();
        MenuBox.getChildren().setAll(menuBar);
        TopPane.getChildren().setAll(MenuBox,time_AttempsBox);
        TopPane.setAlignment(Pos.CENTER);
        gameRoot.setTop(TopPane);
        gameRoot.setRight(PhysicsBox);
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
        timelineEffectTimer.play();
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
                level.attempts--;  
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
                double velocityX = -event.getSceneX() + dragStartX;
                double velocityY = -event.getSceneY() + dragStartY;
                level.ball.setVelocityX(velocityX);
                level.ball.setVelocityY(velocityY);
                dragging = false;
            }
        });
    }

    private Timeline timelineBuilder() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1), e
                -> {
            time = time + 0.001;
            time_AttempsBox.getChildren().setAll(new Label("Time Left: " + df.format(level.timelimit - time)+ " s " + " Attempts: " + level.attempts));
           
            if(level.ball.speed()<5){level.ball.setVelocityX(0);level.ball.setVelocityY(0);level.ball.getForces().clear();}
            
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
            level.ball.addForce(PhysicsEngine.Friction(level.ball, level.frictionCoefficient));
            
            for (int i = 1; i < level.walls.size(); i++) {
               
                if (  level.walls.get(i).isbreakableWall()) {
                if(PhysicsEngine.collides(level.ball, level.walls.get(i))){
                
                    if(level.ball.kE()>=level.walls.get(i).getStrength()){ 
               //making the lines just disappear
               level.walls.get(i).line.setStartX(0); 
               level.walls.get(i).line.setStartY(0);
               level.walls.get(i).line.setEndX(0);
               level.walls.get(i).line.setEndY(0);
               
               //Making the wall not being able to collide with the ball if if line are showing
               level.walls.get(i).endX=0.0;  
              level.walls.get(i).endY=0.0;
              level.walls.get(i).startX=0.0;
              level.walls.get(i).startY=0.0;
                    }
                    
                    else{ PhysicsEngine.collision(level.ball, level.walls.get(i));}
               
                 }
               }
                
            else  if (PhysicsEngine.collides(level.ball, level.walls.get(i))) {
                    PhysicsEngine.collision(level.ball, level.walls.get(i));
               }
            }
            
            for (Trap trap : level.traps) {
            if (trap.isBallInside(level.ball)) {
                
            double frictionCoefficient = trap.getFrictionCoefficient();
            level.ball.addForce(PhysicsEngine.Friction(level.ball, frictionCoefficient));
            }
            }
            
            if (level.ball.getBall().getLayoutX() <= 0 || level.ball.getBall().getLayoutX() >= scene.getHeight() - level.ball.getRadius()) {
                level.ball.setVelocityX(-level.ball.getVelocityX());
            }
            if (level.ball.getBall().getLayoutY() <= 0 || level.ball.getBall().getLayoutY() >= scene.getHeight() - level.ball.getRadius()) {
                level.ball.setVelocityY(-level.ball.getVelocityY());
            }
            
            
            if(this.displayPhysics){
                Label ballPositionX = new Label("Position X: "+String.valueOf(Math.round(this.getLevel().ball.getPositionX()*100)/100.0)+"cm");
                Label ballPositionY = new Label("Position Y: "+String.valueOf(Math.round(this.getLevel().ball.getPositionY()*100)/100.0)+"cm");
                Label ballVelocityX = new Label("Velocity X: "+String.valueOf(Math.round(this.getLevel().ball.getVelocityX()/10*100)/100.0)+"m/s");
                Label ballVelocityY = new Label("Velocity Y: "+String.valueOf(Math.round(this.getLevel().ball.getVelocityY()/10*100)/100.0)+"m/s");
                Label ballNetforceX = new Label("Net Force X: "+String.valueOf(Math.round(this.getLevel().ball.getNetForceX()/10*100)/100.0)+"N");
                Label ballNetForceY = new Label("NetForce Y: "+String.valueOf(Math.round(this.getLevel().ball.getNetForceY()/10*100)/100.0)+"N");
                Label ballAccelerationX = new Label("Acceleration X: "+String.valueOf(Math.round(this.getLevel().ball.getAccelerationX()*100/10)/100.0)+"m/s^2");
                Label ballAccelerationY = new Label("Acceleration X: "+String.valueOf(Math.round(this.getLevel().ball.getAccelerationY()/10*100/100.0))+"m/s^2");
                Label ballKE = new Label("Kinetic Energy: "+String.valueOf(Math.round(this.getLevel().ball.kE()/10*100)/100.0)+"J");
                Label ballSpeed = new Label("speed: "+String.valueOf(Math.round(this.getLevel().ball.speed())/100.0)+"m/s");
                    PhysicsBox.getChildren().setAll(
                        ballPositionX,ballPositionY,
                         ballVelocityX,ballVelocityY,
                        ballNetforceX,ballNetForceY,
                        ballAccelerationX,ballAccelerationY,
                        ballKE,ballSpeed);
            }
            
            
            
            
            if (PhysicsEngine.collides(level.ball, level.goalLine)) {
                System.out.println("Goal!");
                timeline.stop();
                gameWon = true;
                gameEnd = true;
                gameStage.close();
            }
            if (time >= level.timelimit) {
                System.out.println("fail");
                timeline.stop();
                gameWon = false;
                gameEnd = true;
                gameStage.close();
            }
         
          if (level.attempts == -1) {
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

    private MenuBar menuBar() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Exit");
        MenuItem exitItem = new MenuItem("Back To Main Menu");
        MenuItem ballColorChange = new MenuItem("Colors");
        fileMenu.getItems().addAll(ballColorChange,exitItem);
        menuBar.getMenus().addAll(fileMenu);
      
        exitItem.setOnAction(event -> {
        gameStage.close();
        });
        
        VBox colorsBox = new VBox();
        colorsBox.getChildren().addAll(menuBar, level.ball.getBall());

        ballColorChange.setOnAction(event -> {
            Stage colorSelectionStage = new Stage();
            VBox colorSelectionLayout = new VBox(10);

            // Creating a ColorPicker for selecting the shared color
            ColorPicker colorPicker = new ColorPicker(Ball.defaultColor);

            // Adding ColorPicker to the layout
            colorSelectionLayout.getChildren().addAll(colorPicker);

            // Event handler to update the shared color when the ColorPicker changes
            colorPicker.setOnAction(colorEvent -> {
                Ball.defaultColor = colorPicker.getValue();
               level.ball.setBallColor(Ball.defaultColor); // Apply the shared color to the ball      
            });

            Scene colorSelectionScene = new Scene(colorSelectionLayout, 200, 200);
            colorSelectionStage.setTitle("Select Ball Color");
            colorSelectionStage.setScene(colorSelectionScene);
            colorSelectionStage.show();
        });
        return menuBar;
    }
    
    public void powerupBuilder(){
        originalRadius =  level.ball.getBall().getRadius();
        originalMass  = level.ball.getMass();
        Size = new Circle(15);
        Force = new Circle(15);
      
        Size.setStroke(Color.BLUE);
        Size.setFill(Color.BROWN);
        Force.setStroke(Color.BLUE);
        Force.setFill(Color.GREEN);
      
      
        for (PowerUp powerup : level.powerUps) {
            Size_PW=powerup.getnumberOfSizePowerups();
            Force_PW=powerup.getnumberOfForcePowerUps();
        }
        
        Size.setOnMousePressed(event->  
        {
            level.attempts++;
            start = System.currentTimeMillis();
            sizeButtonClicked = true; 
            lastClickedTime = start;
            if(Size_PW!=0){
                System.out.println("CLICKED SIZE BUTTON");
                level.ball.getBall().setRadius(5.0);
                Size_PW = Size_PW-1;
            }
            if(Size_PW==0){
                System.out.println("NO MORE");
            }
        });
       
        Force.setOnMousePressed(event->  {
            start = System.currentTimeMillis();
            level.attempts++;
            sizeButtonClicked = true; 
            lastClickedTime = start;
            if(Force_PW!=0){
                System.out.println("CLICKED SIZE BUTTON");
                level.ball.setMass(500.0);
                Force_PW = Force_PW-1;
            }
            if(Force_PW==0){
                System.out.println("NO MORE");
            }
        });
        timelineEffectTimer = new Timeline(new KeyFrame(Duration.millis(100), e-> 
        {
            if (sizeButtonClicked) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - lastClickedTime;
                if (elapsedTime >= 5000) { // Check if 5 seconds (5000 milliseconds) have elapsed
                    level.ball.getBall().setRadius(originalRadius);
                    level.ball.setMass(originalMass);
                    sizeButtonClicked = false;  
                }
            }
        }));
        timelineEffectTimer.setCycleCount(Timeline.INDEFINITE);
        
        Label pw_message = new Label("Powers");      
        pw_message.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #333333; -fx-underline: true;");
     
        PowerUpsBox.setAlignment(Pos.CENTER);
        PowerUpsBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid; -fx-padding: 2px;");
     
        if(Size_PW>0 && Force_PW>0){
            PowerUpsBox.setMaxWidth(100);
            PowerUpsBox.setMaxHeight(100);
      
            PowerUpsBox.getChildren().setAll(pw_message,Size,Force);
            gameRoot.setLeft(PowerUpsBox);
        }
    }

}

