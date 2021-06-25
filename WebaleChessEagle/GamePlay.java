import java.util.*;
import java.lang.*;

public class GamePlay{
    ChessBoard gameboard;
    GamePlayer playerBlue;
    GamePlayer playerRed;
    ArrayList<GamePlayer> playerList = new ArrayList<GamePlayer>();

    private static GamePiece queue = null;
    private static GameSlot temp = null;
    private static int numTurn = 0;
    private static boolean gameWinner;
    private static boolean movement = false;
    private static String type;
    private static  int oldX,oldY, newX, newY, x, y;

    GamePlay(){
        gameboard = new ChessBoard();
        playerBlue = new GamePlayer("B");
        playerRed = new GamePlayer("R");
        playerList.add(playerRed);
        playerList.add(playerBlue);
    }
    
    //this method is used to restart the game
    //it will take in class ChessBoard and clear it
    //it will read method gamePieceSet to set the gamepieces in their respective place
    //the numTurn will be set to 0 again
    //there will be no winner since the game has restart
    public void gameRestart(){
        gameboard.clear();
        gamePieceSet();
        numTurn = 0;
        gameWinner = false;
    }
    
    //this method is to determine the placement of the chess pieces
    public void gamePieceSet(){      
        String[] placementLower = {"Plus","Triangle","Chevron","Sun","Chevron","Triangle","Plus"};
        String placementUpper = "Arrow";
        boolean reachEnd = false;
        boolean reachEnd2 = true;
        for(int i = 0; i < gameboard.getHeight(); i++){
            for(int j = 0; j < gameboard.getWidth(); j++){
                if (i == 0){
                    //the blue chess gamepieces besides the blue arrow will be placed at height = 0
                    gameboard.addChessGamePiece(i, j, new GamePiece(placementLower[j], playerBlue,reachEnd2));
                }
                if (i == 1){
                    //the arrow blue chess gamepieces will be placed at height = 1
                    gameboard.addChessGamePiece(i, j, new GamePiece(placementUpper, playerBlue,reachEnd));
                    j++;
                }
                if (i == 6){
                    //the arrow red chess gamepieces will be placed at height = 6
                    gameboard.addChessGamePiece(i, j, new GamePiece(placementUpper, playerRed,reachEnd));
                    j++;
                }
                if (i == 7){
                    //the red chess gamepieces besides the red arrow will be placed at height = 7
                    gameboard.addChessGamePiece(i, j, new GamePiece(placementLower[j], playerRed,reachEnd2));
                }
            }
        }
    }
    
    public boolean gameMove(GameSlot gameslot){
        //if clicked button has gamepiece
        if(gameslot.getGamePiece() != null && gameMoveable(gameslot)){
            //if queue is empty
            if(queue == null){
                type = gameslot.getGamePiece().getGamePieceName();
                oldX = gameslot.getX();
                oldY = gameslot.getY();
                queue = gameslot.getGamePiece();
                temp = gameslot;
            }
            //if queue is occupied
            else{
                newX = gameslot.getX();
                newY = gameslot.getY();
                movement = canMove(type,oldX,oldY,newX,newY,queue);
                if(!queue.getGamePlayer().equals(gameslot.getGamePiece().getGamePlayer()) && movement){
                    temp.setGamePiece(null);
                    gameslot.setGamePiece(queue);
                    queue = null;
                    temp = null;
                    numTurn++;
                    return true;
                }
                queue = null;
                temp = null;
            }
        }
        //if clicked button has no gamepiece
        else{
            if(temp != null){
                newX = gameslot.getX();
                newY = gameslot.getY();

                movement = canMove(type,oldX,oldY,newX,newY,queue);
                if(movement){                                   
                    gameslot.setGamePiece(queue);
                    queue = null;
                    temp.setGamePiece(null);
                    temp = null;
                    numTurn++;
                    return true;
                }
            }
            queue = null;
            temp = null;
        }
        return false;
    }

    public boolean gameMoveable(GameSlot gameslot){
        if(gameslot.getGamePiece().getGamePlayer().equals(getGamePlayerTurn())){
            return true;
        }
        return false;
    }
    
    //this method determine how the chess pieces move
    public boolean canMove(String type, int oldX, int oldY, int newX, int newY,GamePiece queue){
        x = oldX - newX;
        y = oldY - newY;
        
        //the arrow chess piece movement
        if(type.equals("Arrow")){
            if(oldY == newY){   
                if(queue.getReachEnd()){
                    if(x == -1 || (x == -2 && gameboard.getGameSlot(oldX + 1,oldY).getGamePiece() == null)){ 
                        if(newX == 7){
                            queue.setReachEnd(false);
                        }    
                        return true;
                    }
                }
                else{
                    if(x == 1 || (x == 2 && gameboard.getGameSlot(oldX - 1,oldY).getGamePiece() == null)){
                        if(newX == 0){
                            queue.setReachEnd(true);
                        }
                        return true;
                    }
                }
            }
        }
        else if (type.equals("Plus")){ 
            x = Math.abs(oldX - newX);
            y = Math.abs(oldY - newY);
            
            //the plus chess piece movement (left or forward)
            if((oldX - newX) > 0 || (oldY - newY) > 0){
                //the plus chess piece move left
                if((x == 0 && y >= 1)){
                    for(int i = 1; i <= y-1; i++){
                        if(gameboard.getGameSlot(oldX,oldY-i).getGamePiece() != null)
                        {    
                            return false;
                        }
                    }
                    return true;
                }

                //the plus chess piece move forward
                else if(x >= 1 && y == 0){
                    for(int i = 1; i <= x-1; i++){
                        if(gameboard.getGameSlot(oldX - i,oldY).getGamePiece() != null){   
                            return false;
                        }
                    }
                    return true;
                }
            }

            //the plus chess piece movement (right or backward)
            else if((oldX - newX) < 0 || (oldY - newY) < 0){
                //the plus chess piece move right
                if(x == 0 && (oldY - newY) <= -1){
                    for(int i = y-1 ; i > 0;  i--){
                        if(gameboard.getGameSlot(oldX, oldY + i).getGamePiece() != null){    
                            return false;
                        }
                    }
                    return true;
                }

                //the plus chess piece move backward
                else if((oldX - newX) <= -1 && y == 0){
                    for(int i = x-1 ; i > 0;  i--){
                        if(gameboard.getGameSlot(newX - i, newY).getGamePiece() != null){    
                            return false;
                        }
                    }
                    return true;
                }
            }
        }

        else if(type.equals("Triangle")){
            //the triangle chess piece can only move diagonally
            //abs(x) always equals to abs(y)
            if(Math.abs(x) == Math.abs(y)){
                //check for obstruction
                //upper left
                if(newX < oldX && newY > oldY){
                    for(int i = 1; i < x; i++){
                        if(gameboard.getGameSlot(oldX-i, oldY+i).getGamePiece() != null) {
                            return false;
                        }
                    }
                    return true;
                }
                //upper right
                else if(newX > oldX && newY > oldY){
                    for(int i = 1; i < x; i++){
                        if(gameboard.getGameSlot(oldX+i, oldY+i).getGamePiece() != null) {
                            return false;
                        }
                    }
                    return true;
                }
                //lower left
                else if(newX < oldX && newY < oldY){
                    for(int i = 1; i < x; i++){
                        if(gameboard.getGameSlot(oldX-i, oldY-i).getGamePiece() != null){
                            return false;
                        }
                    }
                    return true;
                }
                //lower right
                else if(newX > oldX && newY < oldY){
                    for(int i = 1; i < x; i++){
                        if(gameboard.getGameSlot(oldX+i, oldY-i).getGamePiece() != null){
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        //the chevron chess piece movement
        else if(type.equals("Chevron")){
            if(((x == -2 || x == 2) && (y == 1 || y == -1)) || ((x == -1 || x == 1) && (y == -2 || y == 2))){
                return true;
            }
        }
        //the sun chess piece movement
        else if(type.equals("Sun")){
            if((x == -1 || x == 0 || x == 1) && (y == -1 || y == 0 || y == 1)){
                return true;
            }
        }
        return false;
    }
    
    //this method is used to change the chess piece of triangle to plus and vice versa
    public void gamepieceChange(){
        for(int i = 0; i < gameboard.getBoardSize(); i++){
            if(gameboard.getGameSlot(i).getGamePiece() != null){
                if(gameboard.getGameSlot(i).getGamePiece().getGamePieceName().equals("Triangle")){
                    gameboard.getGameSlot(i).getGamePiece().setGamePieceName("Plus");
                    continue;
                }
                else if(gameboard.getGameSlot(i).getGamePiece().getGamePieceName().equals("Plus")){
                    gameboard.getGameSlot(i).getGamePiece().setGamePieceName("Triangle");
                    continue;
                }
            }
        }
    }
    
    public String getWinner(){
        int sun = 0;
        String winner = null;
        for(int i = 0; i < gameboard.getBoardSize(); i++){
            GameSlot gameslot = gameboard.getGameSlot(i);
            GamePiece gamepiece = gameslot.getGamePiece();
            if(gamepiece != null){
                if(gamepiece.getGamePieceName() == "Sun"){
                    winner = gamepiece.getGamePlayer().getColor();
                    gameWinner = true;
                    sun++;
                }
            }
        }
        if(sun == 2){
            winner = null;
            gameWinner = false;
        }
        return winner;        
    }

    public GamePlayer getGamePlayerTurn(){
        if(gameWinner){
            return playerList.get((numTurn - 1) % 2);
        }
        else{
            return playerList.get(numTurn % 2);
        }
    }
    
    public int getNumTurn(){
        return numTurn;
    }

}
