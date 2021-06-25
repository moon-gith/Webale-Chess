import java.util.*;

//this class is to determine chess board
//this will link to class GameSlot for the placement of the chess pieces on the chess board
public class ChessBoard{
    private int width;
    private int height;
    private ArrayList<GameSlot> chessGameSlot = new ArrayList<GameSlot>();
    private ArrayList<String> B = new ArrayList<String>();
    private ArrayList<String> R = new ArrayList<String>();
    
    //this is the default constructor where the size of the board will be 7 width and 8 height
    ChessBoard(){
        setBoardSize(7, 8);
        addChessGameSlot();
    }
    
    //this is the custom constructor
    ChessBoard(int width, int height){
        setBoardSize(width, height);
        addChessGameSlot();
    }
    
    //this method is used to clear the chess board
    //it will remove the game slot in the arrayList chessGameSlot
    //it will then add the new game slot again
    public void clear(){
        chessGameSlot.clear();
        addChessGameSlot();
    }
    
    public void setBoardSize(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    //this method will be used to add the game slot
    //the game slot will be save in the arrayList chessGameSlot and will be used in the next class
    public void addChessGameSlot(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                GameSlot gameslot = new GameSlot(i, j);
                chessGameSlot.add(gameslot);
            }    
        }
    }
    
    public void addChessGamePiece(int x, int y, GamePiece gamepiece){
        chessGameSlot.get(x*width + y).setGamePiece(gamepiece);
    }
    
    public void addRedImg(String imgName){
        R.add(imgName);
    }
    
    public void addBlueImg(String imgName){
        B.add(imgName);
    }
    
    public GameSlot getGameSlot(int i){
        return chessGameSlot.get(i);
    }
    
    public GameSlot getGameSlot(int x, int y){
        return chessGameSlot.get(x*width + y);
    }
    
    //this method is used to rotate and show the other player's board
    public void reverse(){
        Collections.reverse(chessGameSlot);
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                chessGameSlot.get((i*width) + j).setX(i);
                chessGameSlot.get((i*width) + j).setY(j);
            }
        }
    }
    
    public String getImg(String img){
        img = "Assets/" + img + ".png";
        return img;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public int getBoardSize(){
        return this.height * this.width;
    }
}
