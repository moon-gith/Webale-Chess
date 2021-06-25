import java.util.*;

//this class is to determine the player (in color)
//there will be 2 players - 1 red 1 blue
public class GamePlayer{
    private String color;
    
    GamePlayer(String color){
        setColor(color);
    }
    
    //this method is used to get the player's respective color
    public void setColor(String color){
        this.color = color;
    }
    
    //this method is used to get the player's respective color
    public String getColor(){
        return this.color;
    }
}