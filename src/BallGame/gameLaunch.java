package BallGame;


import Developper.FileReader;
import BallGame.Game;
import Developper.Test;
import Objects.Level;
import java.io.File;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class gameLaunch {
    
    Game golfgame = new Game();
    final double WIDTH = 600.0, HEIGHT = 600.0;
    Level level = null;
    public Scene levelScene(BallCollisionGame Application,Stage stage,File file){
        try{
        level = FileReader.readProperties(file);
        }catch(IOException e){
            System.out.println("file not found");
        }
        golfgame.setLevel(level);
        
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu(level.name);
        MenuItem exitItem = new MenuItem("Back To Main Menu");
        fileMenu.getItems().add(exitItem);
        
        exitItem.setOnAction(event -> {
        stage.setScene(Application.MainMenuScene(stage));
        });
        
        menuBar.getMenus().addAll(fileMenu);
        
        Label levelDescriptionLabel = new Label(level.description);
        levelDescriptionLabel.setAlignment(Pos.CENTER);
        levelDescriptionLabel.setFont(new Font(30));
        Button play = new Button("PLAY");
        play.setOnAction(event -> 
        {
           golfgame = new Game(level); 
           golfgame.runGame();
           stage.setScene(endScene(Application,stage));
        });
        HBox btns  = new HBox(play);
        btns.setAlignment(Pos.CENTER);
        VBox mainBox = new VBox(20,levelDescriptionLabel,btns);
        mainBox.setAlignment(Pos.CENTER);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(mainBox);
        
        Scene scene = new Scene(borderPane,WIDTH , HEIGHT);
        
        return scene;
    }
    
    public Scene endScene(BallCollisionGame Application,Stage stage){
    
        Label endLabel = new Label();
        endLabel.setFont(new Font(50));
        Button retryButton = new Button("Try Again");
        Button nextButton = new Button("Next Level");
        
        HBox buttonBox = new HBox(30,retryButton,nextButton);
        buttonBox.setAlignment(Pos.CENTER);
        VBox Box = new VBox(10,endLabel,buttonBox);
        Box.setAlignment(Pos.CENTER);
        
        retryButton.setOnAction(event ->
        {
            stage.setScene(levelScene(Application,stage,new File(Application.levelsProperties.get(level.index))));
        });
        if(golfgame.gameWon){
            endLabel.setText("Congratulations");
             nextButton.setOnAction(event ->
            {
                stage.setScene(levelScene(Application,stage,new File(Application.levelsProperties.get(level.index+1))));                
            });
        }else{
            endLabel.setText("Better Luck Next Time!");
            nextButton.setOnAction(event ->
            {
               Box.getChildren().add(new Label("You did not complete this level, please retry"));
            });
        }
        return new Scene(Box,WIDTH,HEIGHT);
    } 
    
}
