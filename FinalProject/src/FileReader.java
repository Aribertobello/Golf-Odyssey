
import Objects.Ball;
import Objects.Level;
import Objects.Wall;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aribe
 */
public class FileReader {
    public static Level readProperties(File file) throws IOException{
        Level level;
        Scanner scanner = new Scanner(file);
        
        String name  = scanner.nextLine();
        scanner.nextLine();
        String description = scanner.nextLine()+"\n"+scanner.nextLine()+"\n"+scanner.nextLine()+"\n"+scanner.nextLine()+"\n"+scanner.nextLine();
        scanner.nextLine();
        scanner.next(); scanner.skip(" ");
        double ballMass = scanner.nextDouble();
        scanner.skip(" ");
        double ballRadius = scanner.nextDouble();
        scanner.skip(" ");
        double ballX = scanner.nextDouble();
        scanner.skip(" ");
        double ballY = scanner.nextDouble();
        scanner.nextLine();
        scanner.next(); scanner.skip(" "); scanner.next(); scanner.skip(" ");
        double FinishLineStartX = scanner.nextDouble();
        scanner.skip(" ");
        double FinishLineStartY = scanner.nextDouble();
        scanner.skip(" ");
        double FinishLineEndX = scanner.nextDouble();
        scanner.skip(" ");
        double FinishLineEndY = scanner.nextDouble();

        Ball ball = new Ball(ballMass,ballRadius,ballX,ballY,0.0,0.0);
        Wall finishLine = new Wall(FinishLineStartX,FinishLineStartY,FinishLineEndX,FinishLineEndY);
        finishLine.line.setStroke(Color.GREEN);
        level = new Level(name,description,ball,finishLine);
        
        while(scanner.hasNext()){
            scanner.nextLine();
            scanner.next(); scanner.skip(" ");
            double wallStartX = scanner.nextDouble();
            scanner.skip(" ");
            double wallStartY = scanner.nextDouble();
            scanner.skip(" ");
            double wallEndX = scanner.nextDouble();
            scanner.skip(" ");
            double wallEndY = scanner.nextDouble();
            Wall wall = new Wall(wallStartX,wallStartY,wallEndX,wallEndY);
            wall.line.setStroke(Color.BLACK);
            level.walls.add(wall);
        }
        return level;
    }
}
