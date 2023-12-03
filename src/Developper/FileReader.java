package Developper;

import Objects.Ball;
import Objects.Level;
import Objects.Trap;
import Objects.Wall;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.paint.Color;

public class FileReader {

    public static Level readProperties(File file) throws IOException {
        Level level;
        Scanner scanner = new Scanner(file);

        String name = scanner.nextLine();
        int index = Integer.parseInt(scanner.nextLine().substring(6));
        String description = scanner.nextLine() + "\n" + scanner.nextLine() + "\n" + scanner.nextLine() + "\n" + scanner.nextLine() + "\n" + scanner.nextLine();
        scanner.nextLine();
        scanner.next();
        scanner.skip(" ");
        scanner.next();
        scanner.skip(" ");
        double timeLimit = scanner.nextDouble();
        scanner.nextLine();
        scanner.next();
        scanner.skip(" ");
        int attempts  = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        scanner.skip(" ");
        double ballMass = scanner.nextDouble();
        scanner.skip(" ");
        double ballRadius = scanner.nextDouble();
        scanner.skip(" ");
        double ballX = scanner.nextDouble();
        scanner.skip(" ");
        double ballY = scanner.nextDouble();
        scanner.nextLine();
        scanner.next();
        scanner.skip(" ");
        scanner.next();
        scanner.skip(" ");
        double finishLineStartX = scanner.nextDouble();
        scanner.skip(" ");
        double finishLineStartY = scanner.nextDouble();
        scanner.skip(" ");
        double finishLineEndX = scanner.nextDouble();
        scanner.skip(" ");
        double finishLineEndY = scanner.nextDouble();

        Ball ball = new Ball(ballMass, ballRadius, ballX, ballY);
        Wall finishLine = new Wall(finishLineStartX, finishLineStartY, finishLineEndX, finishLineEndY);
        finishLine.line.setStroke(Color.GREEN);
        level = new Level(name, description,timeLimit,attempts, ball, finishLine);

    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.startsWith("wall")) {
            try {
                String[] wallData = line.split(" ");
                double wallStartX = Double.parseDouble(wallData[1]);
                double wallStartY = Double.parseDouble(wallData[2]);
                double wallEndX = Double.parseDouble(wallData[3]);
                double wallEndY = Double.parseDouble(wallData[4]);

                Wall wall = new Wall(wallStartX, wallStartY, wallEndX, wallEndY);
                level.walls.add(wall);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Walls not here");
                e.printStackTrace(); // Print stack trace for debugging
            }
        } else if (line.startsWith("trap")) {
            try {
                String[] trapData = line.split(" ");
                double trapStartX = Double.parseDouble(trapData[1]);
                double trapStartY = Double.parseDouble(trapData[2]);
                double trapEndX = Double.parseDouble(trapData[3]);
                double trapEndY = Double.parseDouble(trapData[4]);
                double frictionCoefficient = Double.parseDouble(trapData[5]);
                String trapType = trapData[6];
          
                Trap trap = new Trap(trapStartX, trapStartY, trapEndX, trapEndY,frictionCoefficient,trapType);
                trap.setFrictionCoefficient(frictionCoefficient);
                level.addTrap(trap);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: traps not here");
                e.printStackTrace(); // Print stack trace for debugging
            }
        }
    }
        level.filePath = file.getPath();
        level.index = index;
        return level;
    }
}
