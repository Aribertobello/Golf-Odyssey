package Developper;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import BallGame.Game;
import Objects.Level;
import Objects.Wall;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author aribe
 */
public class Developper extends Application {

    Game testGame = new Game();
    Level testLevel = null;
    Timeline timeline = testTimeline();
    
    int wallIndex  = 0;
    
    
    Label ballPositionX;
    Label ballPositionY ;
    Label ballVelocityX;
    Label ballVelocityY;
    Label ballNetforceX;
    Label ballNetForceY;
    Label ballAccelerationX;
    Label ballAccelerationY;
    Label ballKE;
    Label ballSpeed;
    VBox ballValues;
    VBox wallValues;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        testLevel = FileReader.readProperties(new File("src\\LevelProperties\\levelTestProperties.txt"));
        testGame.setLevel(FileReader.readProperties(new File("src\\LevelProperties\\levelTestProperties.txt")));
        Button Test = new Button("Test");
        Test.setOnAction(event -> 
        {
            primaryStage.setScene(developperTerminal());
            primaryStage.setTitle("Developper Terminal");
            timeline.play();
            testGame.runGame();
        });
        VBox ButtonBox =  new VBox(10,Test);
        ButtonBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(ButtonBox,600,600);
        primaryStage.setTitle("Test Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Scene developperTerminal() {
        
        Label trapLabel = new Label("Trap Terminal");
                
        VBox ballBox = ballTerminal();
        VBox wallBox = WallTerminal();
        VBox trapBox = new VBox(trapLabel);
        trapBox.setStyle("-fx-border-color: #000000");
        
        HBox root = new HBox(20,ballBox,wallBox,trapBox);
        root.setAlignment(Pos.CENTER);
        return new Scene(root,1800,500);
    }
    
    private VBox ballTerminal(){
        Label ballLabel = new Label("Ball Terminal");
        ballLabel.setAlignment(Pos.CENTER);
        
        Label masslabel = new Label("mass:");
        TextField massField = new TextField(String.valueOf(testGame.getLevel().ball.getMass()));
        massField.setOnAction(event ->{
            testGame.getLevel().ball.setMass(Double.parseDouble(massField.getText()));
        });
        HBox massBox = new HBox(masslabel,massField); 
        
        Label radlabel = new Label("radius:");
        TextField radField = new TextField(String.valueOf(testGame.getLevel().ball.getRadius()));
        radField.setOnAction(event ->{
            testGame.getLevel().ball.setRadius(Double.parseDouble(radField.getText()));
        });
        HBox radBox = new HBox(radlabel,radField); 

        ballValues = new VBox();
        
        VBox ballBox = new VBox(10,ballLabel,massBox,radBox,ballValues);
        ballBox.setStyle("-fx-border-color: #000000");
        return ballBox;
    }
    
    private VBox WallTerminal(){
        Label wallLabel = new Label("Wall Terminal");
        
        Button addWallButton = new Button("Add Wall");
        addWallButton.setOnAction(event ->
        {
            wallIndex++;
            testGame.getLevel().walls.add(new Wall(300, 300, 300, 300,false));
            testGame.getRoot().getChildren().add(testGame.getLevel().walls.get(wallIndex).line);
            Label startXlabel = new Label("startX:");
            TextField startXField = new TextField(String.valueOf(testGame.getLevel().walls.get(wallIndex).startX));
            startXField.setOnAction(e ->{
                testGame.getLevel().walls.get(wallIndex).setStartX( Double.parseDouble(startXField.getText()));
                
            });
            HBox startXBox = new HBox(startXlabel,startXField);
            
            Label startYlabel = new Label("startY:");
            TextField startYField = new TextField(String.valueOf(testGame.getLevel().walls.get(wallIndex).startY));
            startYField.setOnAction(e ->{
                testGame.getLevel().walls.get(wallIndex).setStartY( Double.parseDouble(startYField.getText()));
                
            });
            HBox startYBox = new HBox(startYlabel,startYField);
            
            Label endXlabel = new Label("endX:");
            TextField endXField = new TextField(String.valueOf(testGame.getLevel().walls.get(wallIndex).endX));
            endXField.setOnAction(e ->{
                testGame.getLevel().walls.get(wallIndex).setEndX( Double.parseDouble(endXField.getText()));
                
            });
            HBox endXBox = new HBox(endXlabel,endXField);
            
            Label endYlabel = new Label("endY:");
            TextField endYField = new TextField(String.valueOf(testGame.getLevel().walls.get(wallIndex).endY));
            endYField.setOnAction(e ->{
                testGame.getLevel().walls.get(wallIndex).setEndY( Double.parseDouble(endYField.getText()));
                
            });
            HBox endYBox = new HBox(endYlabel,endYField);
            
            HBox wallBox = new HBox(10,startXBox,startYBox,endXBox,endYBox);
            wallValues.getChildren().add(wallBox);
        });
        wallValues = new VBox();
        VBox wallBox = new VBox(wallLabel,addWallButton,wallValues);         
        wallBox.setStyle("-fx-border-color: #000000");
        return wallBox;
    }
    

    private Timeline testTimeline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100),event->{updateValues();}));
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    private void updateValues() {
        ballPositionX = new Label("Position X: "+String.valueOf(testGame.getLevel().ball.getPositionX())+"cm");
        ballPositionY = new Label("Position Y: "+String.valueOf(testGame.getLevel().ball.getPositionY())+"cm");
        ballVelocityX = new Label("Velocity X: "+String.valueOf(testGame.getLevel().ball.getVelocityX()/10)+"m/s");
        ballVelocityY = new Label("Velocity Y: "+String.valueOf(testGame.getLevel().ball.getVelocityY()/10)+"m/s");
        ballNetforceX = new Label("Net Force X: "+String.valueOf(testGame.getLevel().ball.getNetForceX()/10)+"N");
        ballNetForceY = new Label("NetForce Y: "+String.valueOf(testGame.getLevel().ball.getNetForceY()/10)+"N");
        ballAccelerationX = new Label("Acceleration X: "+String.valueOf(testGame.getLevel().ball.getAccelerationX()/10)+"m/s^2");
        ballAccelerationY = new Label("Acceleration X: "+String.valueOf(testGame.getLevel().ball.getAccelerationY()/10)+"m/s^2");
        ballKE = new Label("Kinetic Energy: "+String.valueOf(testGame.getLevel().ball.kE()/100)+"J");
        ballSpeed = new Label("speed: "+String.valueOf(testGame.getLevel().ball.speed()/100)+"m/s");
        ballValues.getChildren().setAll(
                ballPositionX,ballPositionY,
                ballVelocityX,ballVelocityY,
                ballNetforceX,ballNetForceY,
                ballAccelerationX,ballAccelerationY,
                ballKE,ballSpeed);
        
    }
}
