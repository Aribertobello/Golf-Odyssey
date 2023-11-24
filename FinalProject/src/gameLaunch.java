
import Objects.Level;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
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


public class gameLaunch extends Application{
    
    GolfGameTest golfgame = new GolfGameTest();
    final double WIDTH = 600.0, HEIGHT = 600.0;
    File LEVEL_1 = new File("src\\LevelProperties\\level1Properties.txt");
    File LEVEL_2 = new File("src\\LevelProperties\\level2Properties.txt");
    File LEVEL_3 = new File("src\\LevelProperties\\level3Properties.txt");
    
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        stage.setScene(createScene(stage,LEVEL_2));
        stage.show();
    }
    
    public Scene createScene(Stage stage,File file){
        Level level_1 = null;
        try{
        level_1 = FileReader.readProperties(file);
        }catch(IOException e){
            System.out.println("file not found");
        }
        golfgame.setLevel(level_1);
        
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu(level_1.name);
        MenuItem exitItem = new MenuItem("Back To Main Menu");
        fileMenu.getItems().add(exitItem);
        
        exitItem.setOnAction(event -> {
        stage.close();
        });
        
        menuBar.getMenus().addAll(fileMenu);
        
        Label levelDescriptionLabel = new Label(level_1.description);
        levelDescriptionLabel.setAlignment(Pos.CENTER);
        levelDescriptionLabel.setFont(new Font(30));
        Button play = new Button("PLAYYYYY");
        play.setOnAction(event -> 
        {
           golfgame.runGame();
           stage.setScene(endScene(stage));
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
    
    public Scene endScene(Stage stage){
    
        Label endLabel = new Label();
        endLabel.setFont(new Font(50));
        Button retryButton = new Button("Try Again");
        Button nextButton = new Button("Next Level");
        retryButton.setOnAction(event ->
        {
            
        });
        
        if(golfgame.gameWon){
            endLabel.setText("Congratulations");
             nextButton.setOnAction(event ->
            {
                stage.setScene(createScene(stage,LEVEL_2));                
            });
        }else{
            endLabel.setText("Better Luck Next Time!");
            nextButton.setOnAction(event ->
            {

            });
        }

        HBox buttonBox = new HBox(30,retryButton,nextButton);
        buttonBox.setAlignment(Pos.CENTER);
    
        VBox Box = new VBox(10,endLabel,buttonBox);
        Box.setAlignment(Pos.CENTER);
        
        return new Scene(Box,WIDTH,HEIGHT);
    } 
    
}
