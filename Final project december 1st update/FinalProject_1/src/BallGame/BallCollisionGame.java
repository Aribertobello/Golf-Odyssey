/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BallGame;

import Developper.FileReader;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author aribe
 */
public class BallCollisionGame extends Application{
    
    ArrayList<String> levelsProperties;
    
    public static void main(String args[]){launch(args);}
    public void start(Stage primaryStage){
        levelsProperties = new ArrayList<>();
        loadLevelProperties(5);
        primaryStage.setScene(TitleScene(primaryStage));
        primaryStage.setTitle("Final Project");
        primaryStage.show();
    }


















    
    
    
    
    
    
    public Scene TitleScene(Stage stage){
        Label label = new Label("BALL COLLISON GAME!");
        Button playButton = new Button("Play");
        Button exitButton = new Button("close game");
        HBox bottomBox = new HBox(10,exitButton);
        bottomBox.setPadding(new Insets(10));
        bottomBox.setAlignment(Pos.BASELINE_RIGHT);
        VBox titleScreenBox = new VBox(10,label,playButton,bottomBox);
        titleScreenBox.setAlignment(Pos.CENTER);
        Scene titleScene = new Scene(titleScreenBox,600,600);
        
        playButton.setOnAction(event -> {stage.setScene(menuScene(stage));});
        exitButton.setOnAction(event -> {stage.close();});
        
        return titleScene;
    }
    public Scene menuScene(Stage stage){
        gameLaunch gamelaunch = new gameLaunch();
        BorderPane levelMenuPane = new BorderPane();
        HBox levelBox = new HBox();
        for (int i = 0, n = 0;i < levelsProperties.size();){
            VBox levelStack = new VBox();
            for (; n < 5&&i < levelsProperties.size(); i++,n++) {
                int index = i;
                if(!(i==0)){
                    Button levelButton = new Button("Level "+levelsProperties.get(i).substring(25, 26));
                    levelButton.setOnAction(event -> {stage.setScene(gamelaunch.levelScene(this, stage, new File(levelsProperties.get(index))));});
                    levelStack.getChildren().add(levelButton);
                }
            }
            n=0;
            levelBox.getChildren().add(levelStack);
        }
        return new Scene(levelBox,600,600);
    }        

    private void loadLevelProperties(int n) {
        levelsProperties.add("src\\LevelProperties\\levelTestProperties.txt");
        for (int i = 1; i < n+1; i++) {
            levelsProperties.add("src\\LevelProperties\\level"+i+"Properties.txt");
        }
    }
    
}
