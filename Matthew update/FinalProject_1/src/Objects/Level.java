package Objects;

import java.util.ArrayList;

public class Level {
    public Ball ball;
    public Wall goalLine;
    public ArrayList<Wall> walls = new ArrayList<>();
    public ArrayList<Trap> traps = new ArrayList<>();
     public ArrayList<PowerUp> powerUps = new ArrayList<>();
    public String name;
    public String description;
    public int index;
    public String filePath;

    public Level(String name, String description, Ball ball, Wall goalLine, Wall... walls) {
        this.name = name;
        this.description = description;
        this.ball = ball;
        this.goalLine = goalLine;
        this.walls.add(goalLine);
        for (Wall wall : walls) {
            this.walls.add(wall);
        }
}
    
    public void addTrap(Trap trap) {
    this.traps.add(trap);
   }
    
    public void addPowerUps(PowerUp powerup) {
    this.powerUps.add(powerup);
   }
    
}
