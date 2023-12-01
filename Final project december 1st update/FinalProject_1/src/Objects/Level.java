 package Objects;

import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 2246982
 */
public class Level {
    
    public Ball ball;
    public Wall goalLine;
    public ArrayList<Wall> walls = new ArrayList<>();
    public String name;
    public String description;
    public int index;
    public String filePath;

    public Level(String name,String description,Ball ball,Wall goalLine, Wall... walls) {
        this.name  = name;
        this.description = description;
        this.ball = ball;
        this.goalLine = goalLine;
        this.walls.add(goalLine);
        for(Wall wall:walls){
        this.walls.add(wall);
        }
    }
    
    
    
    
}
