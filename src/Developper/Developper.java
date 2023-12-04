package Developper;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import BallGame.Game;
import Objects.Level;
import Objects.Trap;
import Objects.Wall;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import javafx.scene.shape.Rectangle;
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
    int trapIndex  = 0;
    
    
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
    VBox trapValues;
    
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
        VBox trapBox = trapTerminal();
        trapBox.setStyle("-fx-border-color: #000000");
        
        HBox root = new HBox(20,ballBox,wallBox,trapBox);
        return new Scene(root,1050,500);
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
            Wall wall = testGame.getLevel().walls.get(wallIndex);
            Label startXlabel = new Label("startX:");
            TextField startXField = new TextField(String.valueOf(testGame.getLevel().walls.get(wallIndex).startX));
            startXField.setOnAction(e ->{
                wall.setStartX( Double.parseDouble(startXField.getText()));         
            });
            HBox startXBox = new HBox(startXlabel,startXField);
            
            Label startYlabel = new Label("startY:");
            TextField startYField = new TextField(String.valueOf(testGame.getLevel().walls.get(wallIndex).startY));
            startYField.setOnAction(e ->{
                wall.setStartY( Double.parseDouble(startYField.getText()));                
            });
            HBox startYBox = new HBox(startYlabel,startYField);
            
            Label endXlabel = new Label("endX:");
            TextField endXField = new TextField(String.valueOf(testGame.getLevel().walls.get(wallIndex).endX));
            endXField.setOnAction(e ->{
                wall.setEndX( Double.parseDouble(endXField.getText())); 
            });
            HBox endXBox = new HBox(endXlabel,endXField);
            
            Label endYlabel = new Label("endY:");
            TextField endYField = new TextField(String.valueOf(testGame.getLevel().walls.get(wallIndex).endY));
            endYField.setOnAction(e ->{
                wall.setEndY( Double.parseDouble(endYField.getText()));  
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
        ballPositionX = new Label("Position X: "+String.valueOf(Math.round(testGame.getLevel().ball.getPositionX()*100)/100.0)+"cm");
        ballPositionY = new Label("Position Y: "+String.valueOf(Math.round(testGame.getLevel().ball.getPositionY()*100)/100.0)+"cm");
        ballVelocityX = new Label("Velocity X: "+String.valueOf(Math.round(testGame.getLevel().ball.getVelocityX()/10*100)/100.0)+"m/s");
        ballVelocityY = new Label("Velocity Y: "+String.valueOf(Math.round(testGame.getLevel().ball.getVelocityY()/10*100)/100.0)+"m/s");
        ballNetforceX = new Label("Net Force X: "+String.valueOf(Math.round(testGame.getLevel().ball.getNetForceX()/10*100)/100.0)+"N");
        ballNetForceY = new Label("NetForce Y: "+String.valueOf(Math.round(testGame.getLevel().ball.getNetForceY()/10*100)/100.0)+"N");
        ballAccelerationX = new Label("Acceleration X: "+String.valueOf(Math.round(testGame.getLevel().ball.getAccelerationX()*100/10)/100.0)+"m/s^2");
        ballAccelerationY = new Label("Acceleration X: "+String.valueOf(Math.round(testGame.getLevel().ball.getAccelerationY()/10*100/100.0))+"m/s^2");
        ballKE = new Label("Kinetic Energy: "+String.valueOf(Math.round(testGame.getLevel().ball.kE()/10*100)/100.0)+"J");
        ballSpeed = new Label("speed: "+String.valueOf(Math.round(testGame.getLevel().ball.speed())/100.0)+"m/s");
        ballValues.getChildren().setAll(
                ballPositionX,ballPositionY,
                ballVelocityX,ballVelocityY,
                ballNetforceX,ballNetForceY,
                ballAccelerationX,ballAccelerationY,
                ballKE,ballSpeed);
        
    }

    private VBox trapTerminal() {
        Label trapLabel = new Label("Trap Terminal");
        
        Button addTrapButton = new Button("Add Trap");
        addTrapButton.setOnAction(event ->
        {
            
            
            testGame.getLevel().traps.add(new Trap(200, 200, 400, 400, 200, "sand"));
            Trap trap = testGame.getLevel().traps.get(trapIndex);
            testGame.getRoot().getChildren().add(trap.rectangle);
            trap.updateVisualization();
            
            Label startXlabel = new Label("startX:");
            TextField startXField = new TextField(String.valueOf(testGame.getLevel().traps.get(trapIndex).getStartX()));
            startXField.setOnAction(e ->{
                trap.setStartX( Double.parseDouble(startXField.getText()));
                trap.updateVisualization();
                System.out.println("start X"+testGame.getLevel().traps.get(trapIndex-1).getStartX());
                System.out.println("start X"+testGame.getLevel().traps.get(trapIndex-1).getRectangle().getLayoutX()); 
            });
            HBox startXBox = new HBox(startXlabel,startXField);
            
            Label startYlabel = new Label("startY:");
            TextField startYField = new TextField(String.valueOf(testGame.getLevel().traps.get(trapIndex).getStartY()));
            startYField.setOnAction(e ->{
                trap.setStartX( Double.parseDouble(startYField.getText()));
                trap.updateVisualization();
                
            });
            HBox startYBox = new HBox(startYlabel,startYField);
            
            Label endXlabel = new Label("endX:");
            TextField endXField = new TextField(String.valueOf(testGame.getLevel().traps.get(trapIndex).getEndX()));
            endXField.setOnAction(e ->{
                trap.setEndX( Double.parseDouble(endXField.getText()));
                trap.updateVisualization();
                
            });
            HBox endXBox = new HBox(endXlabel,endXField);
            
            Label endYlabel = new Label("endY:");
            TextField endYField = new TextField(String.valueOf(testGame.getLevel().traps.get(trapIndex).getEndY()));
            endYField.setOnAction(e ->{
                trap.setEndY(Double.parseDouble(endYField.getText()));
                trap.updateVisualization();
                
            });
            HBox endYBox = new HBox(endYlabel,endYField);
            
            Label frictionLabel = new Label("Friction coeff:");
            TextField frictionField = new TextField(String.valueOf(testGame.getLevel().traps.get(trapIndex).getStartX()));
            frictionField.setOnAction(e ->{
                trap.setStartX( Double.parseDouble(frictionField.getText()));
                trap.updateVisualization();
                
            });
            HBox frictionBox = new HBox(frictionLabel,frictionField);

            HBox trapBox = new HBox(10,startXBox,startYBox,endXBox,endYBox,frictionBox);
            trapValues.getChildren().add(trapBox);
            trapIndex++;
        });
        trapValues = new VBox();
        VBox trapBox = new VBox(trapLabel,addTrapButton,trapValues);         
        trapBox.setStyle("-fx-border-color: #000000");
        return trapBox;
    }
}
