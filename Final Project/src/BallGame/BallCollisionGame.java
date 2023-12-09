package BallGame;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import BallGame.Game;
import BallGame.gameLaunch;
import Objects.Ball;
import Objects.Level;
import Objects.Wall;
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
    @Override
    public void start(Stage primaryStage){
        levelsProperties = new ArrayList<>();
        loadLevelProperties(5);
        primaryStage.setScene(MainMenuScene(primaryStage));
        primaryStage.setTitle("Final Project");
        primaryStage.show();
    }

    
    public Scene MainMenuScene(Stage stage) {
        Label titleLabel = new Label("BALL COLLISON GAME!");
        Button playButton = new Button("Play");
        Button exitButton = new Button("Close Game");

        VBox titleScreenBox = new VBox(10,titleLabel,playButton);
        titleScreenBox.setAlignment(Pos.CENTER);
        
        HBox topRightBox = new HBox(exitButton);
        topRightBox.setAlignment(Pos.TOP_RIGHT);
        topRightBox.setPadding(new Insets(20));
        
        BorderPane titleScreenPane = new BorderPane();
        titleScreenPane.setTop(topRightBox);
        titleScreenPane.setCenter(titleScreenBox);
        

        Scene titleScene = new Scene(titleScreenPane, 600, 600);

        playButton.setOnAction(event -> {
            stage.setScene(LevelSelectorScene(stage));
        });
        exitButton.setOnAction(event -> {
            stage.close();
        });
        titleScene.getStylesheets().add("BallCollisionGame.css");
        return titleScene;
    }

    
    public Scene LevelSelectorScene(Stage stage) {
        gameLaunch gamelaunch = new gameLaunch();
        Label menuLabel = new Label("Select A Level");
        Button backButton = new Button("Main Menu");
        
        backButton.setOnAction(event -> {
            stage.setScene(MainMenuScene(stage));
        });
        Button freePlay = new Button("Free Play");
        freePlay.setOnAction(event->
        {
            Game game = new Game(freplayLevel());
            game.displayPhysics = true;
            game.runGame();
        });
        VBox levelBox = new VBox(20,menuLabel);
        levelBox.setAlignment(Pos.CENTER);
        
        HBox topRightBox = new HBox(backButton);
        topRightBox.setAlignment(Pos.TOP_RIGHT);
        topRightBox.setPadding(new Insets(20));
        
        BorderPane levelSelectorPane = new BorderPane();
        levelSelectorPane.setTop(topRightBox);
        levelSelectorPane.setCenter(levelBox);
        levelSelectorPane.setBottom(freePlay);
    
    for (int i = 0, n = 0; i < levelsProperties.size();) {
        HBox levelRow = new HBox(20);
        levelRow.setAlignment(Pos.CENTER);

        for (; n <= 4 && i < levelsProperties.size(); i++, n++) {
            int index = i;
            if (!(i == 0)) {
                Button levelButton = new Button("Level " + levelsProperties.get(i).substring(25, 26));
                levelButton.setOnAction(event -> {
                    stage.setScene(gamelaunch.levelScene(this, stage, new File(levelsProperties.get(index))));
                });
                levelRow.getChildren().add(levelButton);
            }
        }
        n = 0;
        levelBox.getChildren().add(levelRow);
    }
    Scene scene = new Scene(levelSelectorPane, 600, 600);
    scene.getStylesheets().add("BallCollisionGame.css");
    return  scene;
}
    
    
    
    Level freplayLevel(){
        return new Level("FreePlay","",9999999,99999,
                new Ball(10.0, 5.0, 250.0 ,250.0),
                new Wall(0.0, 0.0, 0.0, 0.0, false),
                new Wall(100.0,100.0,100.0,400.0,false),
                new Wall(100.0,100.0,400.0,100.0,false),
                new Wall(400.0,100.0,400.0,400.0,false),
                new Wall(400.0,400.0,100.0,400.0,false)
                );
    }
    
    private void loadLevelProperties(int n) {
        levelsProperties.add("src\\LevelProperties\\levelTestProperties.txt");
        for (int i = 1; i < n+1; i++) {
            levelsProperties.add("src\\LevelProperties\\level"+i+"Properties.txt");
        }
    }
    
}
