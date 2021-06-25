import java.util.*;

//this class is to determine the chess pieces slot & placement
//this will link with class GamePiece
public class GameSlot{
    private GamePiece gamepiece;
    private int x;
    private int y;
    
    GameSlot(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    GameSlot(int x, int y, GamePiece gamepiece){
        this.x = x;
        this.y = y;
        this.gamepiece = gamepiece;
    }
    
    public void setGamePiece(GamePiece gamepiece){
        this.gamepiece = gamepiece;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    //this method will take from class GamePiece
    public GamePiece getGamePiece(){
        return this.gamepiece;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
}
