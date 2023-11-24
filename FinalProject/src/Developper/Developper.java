package Developper;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

/**
 *
 * @author aribe
 */
public class Developper extends Application {
    
    BorderPane pane = new BorderPane();
    ArrayList<Line> walls = new ArrayList<>();
    
    Circle circle = new Circle(300,300,1);
    Line wall = new Line(300,300,300,300);
    
    @Override
    public void start(Stage primaryStage) {
        Button ballTest = new Button("BallPositionTester");
        ballTest.setOnAction(event -> 
        {
            Stage secondaryStage = new Stage();
            Scene gameScene = new Scene(pane,600,600);
            secondaryStage.setScene(gameScene);
            primaryStage.setScene(BallTest());
            secondaryStage.show();
        });
        Button wallTest = new Button("WallPositionTester");
        wallTest.setOnAction(event -> 
        {
            Stage secondaryStage = new Stage();
            Scene gameScene = new Scene(pane,600,600);
            secondaryStage.setScene(gameScene);
            primaryStage.setScene(WallTest());
            secondaryStage.show();
        });
        
        HBox ButtonBox =  new HBox(10,ballTest,wallTest);
        
        Scene scene = new Scene(ButtonBox,600,600);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    private Scene BallTest(){
            Label ballXlabel = new Label("X:");
            TextField ballX = new TextField();
            Label ballYlabel = new Label("Y:");
            TextField ballY = new TextField();
            Label ballRlabel = new Label("R:");
            TextField ballR = new TextField();
            ballX.setOnAction(e -> 
            {
                circle.setCenterX(Double.valueOf(ballX.getText()));         
            });
            ballY.setOnAction(e -> 
            {
                circle.setCenterY(Double.valueOf(ballY.getText()));         
            });
            ballR.setOnAction(e -> 
            {
                circle.setRadius(Double.valueOf(ballR.getText()));         
            });
            Button addBall = new Button("Add ball");
            pane.getChildren().add(circle);
            HBox textFields = new HBox(10,ballXlabel,ballX,ballYlabel,ballY,ballRlabel,ballR);
        
            VBox root = new VBox(textFields,addBall);
        
        return new Scene(root, 600, 200);
    }

    private Scene WallTest() {
        walls.add(new Line(300,300,300,300));
        Label wallStartXlabel = new Label("Start X:");
        TextField wallStartX = new TextField();
        Label wallStartYlabel = new Label("Start Y:");
        TextField wallStartY = new TextField();
        Label wallEndXlabel = new Label("End X:");
        TextField wallEndX = new TextField();
        Label wallEndYlabel = new Label("End Y:");
        TextField wallEndY = new TextField();
        wallStartX.setOnAction(event -> 
        {
            wall.setStartX(Double.valueOf(wallStartX.getText()));         
        });
        wallStartY.setOnAction(event -> 
        {
            wall.setStartY(Double.valueOf(wallStartY.getText()));         
        });
        wallEndX.setOnAction(event -> 
        {
            wall.setEndX(Double.valueOf(wallEndX.getText()));         
        });
        wallEndY.setOnAction(event -> 
        {
            wall.setEndY(Double.valueOf(wallEndY.getText()));         
        });
        Button addWall = new Button("Add wall");
        pane.getChildren().add(wall);
        HBox textFields = new HBox(10,wallStartXlabel,wallStartX,wallStartYlabel,wallStartY,wallEndXlabel,wallEndX,wallEndYlabel,wallEndY);
        VBox root = new VBox(textFields,addWall);
        return new Scene(root, 800, 200);
    }
    
    
}
