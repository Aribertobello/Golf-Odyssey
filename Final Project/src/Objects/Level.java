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
    public double timelimit;
    public int maxAttempts;
    public int attempts;
    public double frictionCoefficient;

    public Level(String name, String description, double timeLimit, int maxAttempts, Ball ball, Wall goalLine, Wall... walls) {
        this.name = name;
        this.description = description;
        this.timelimit = timeLimit;
        this.maxAttempts = maxAttempts;
        this.attempts = maxAttempts;
        this.ball = ball;
        this.goalLine = goalLine;
        this.walls.add(goalLine);
        this.frictionCoefficient = 5.0;
        this.walls.add(new Wall(0,0,600,0,false));
        this.walls.add(new Wall(0,600,0,0,false));
        this.walls.add(new Wall(600,0,600,600,false));
        this.walls.add(new Wall(0,600,600,600,false));
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
