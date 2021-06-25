import java.util.*;

//this class is to determine the chess piece
//it will also link with the class GamePlayer 
public class GamePiece{
    private String gpName;
    private GamePlayer gameplayer; //variable for class GamePlayer
    private boolean reachEnd;
    
    GamePiece(String gpName, GamePlayer gameplayer, boolean reachEnd){
        this.gpName = gpName;
        this.gameplayer = gameplayer;
        this.reachEnd = reachEnd;
    }
    
    //this method is to set the chess piece name
    public void setGamePieceName(String gpName){
        this.gpName = gpName;
    }
    
    //this method is to set the player
    //it will take in class GamePlayer
    public void setGamePlayer(GamePlayer gameplayer){
        this.gameplayer = gameplayer;
    }
    
    //this method is to get the chess piece name
    public String getGamePieceName(){
        return this.gpName;
    }
    
    public GamePlayer getGamePlayer(){
        return this.gameplayer;
    }
    
    public void setReachEnd(boolean reachEnd){
        this.reachEnd = reachEnd;
    }
    
    public boolean getReachEnd(){
        return this.reachEnd;
    }
}
