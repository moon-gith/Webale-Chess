import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Image.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class MainWebaleChess extends JFrame implements ActionListener{
    private static JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private static JPanel mid = new JPanel(new GridLayout(8,7));
    private static JPanel bot = new JPanel(new FlowLayout());
    
    private static JMenuBar menuBar = new JMenuBar();
    private static JMenu menu = new JMenu("Menu");
    private static JMenuItem menuItemSave = new JMenuItem("Save Game");
    private static JMenuItem menuItemLoad = new JMenuItem("Load Game");
    private static JMenuItem menuItemRestart = new JMenuItem("Restart");
    private static JMenuItem menuItemInfo = new JMenuItem("Game Rules");
    private static JFileChooser fileChooser = new JFileChooser();
    
    private static ArrayList<JButton> buttonGameSlot = new ArrayList<JButton>();
    private static GamePlay gameplay = new GamePlay();
    private static ChessBoard board = gameplay.gameboard;
    private static JLabel notification = new JLabel("Begin! Player R turn.");
    
    MainWebaleChess(){
        super("Webale Chess Game");
        
        gameSet();
        gameBoard();
        
        setSize(600,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //this is show a confirmation box for the user to choose; either to quit the game or not
        this.addWindowListener( new WindowAdapter(){
            public void windowClosing(WindowEvent e){ 
                String windowTitle = "Quit Webale Chess";
                String windowMessage = "Do you wish to quit the game?";
                int result = JOptionPane.showConfirmDialog(mid, windowMessage, windowTitle, JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }
    
    //this method is the setting for the menu bar
    private void menuSet(){
        menu.add(menuItemSave);
        menu.add(menuItemLoad);
        menu.add(menuItemRestart);
        menu.add(menuItemInfo);
        menuBar.add(menu);    
        top.add(menuBar);
        bot.add(notification);
        
        this.setLayout(new BorderLayout());
        this.add(top, BorderLayout.NORTH);
        this.add(mid, BorderLayout.CENTER);
        this.add(bot, BorderLayout.SOUTH);
        
        //this is the menu item save code
        menuItemSave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    FileOutputStream file = new FileOutputStream("chess.txt"); //this will create the chess.txt is the file didn't exist yet
                    ObjectOutputStream object = new ObjectOutputStream(file);
                    object.writeObject(new MainWebaleChess()); //save the game into chess.txt
                    setVisible(false);
                    dispose();
                    //setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
                catch(Exception ex){
                    
                }
            }
        });
        
        //this is the menu item restart code
        menuItemRestart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String windowTitle = "Restart Webale Chess";
                String windowMessage = "Do you wish to restart the game?";
                int result = JOptionPane.showConfirmDialog(mid, windowMessage, windowTitle, JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION){
                    gameplay.gameRestart(); //call the gameRestart method from class GamePlay
                    gameReload(false);
                    notification.setText("Begin! Player R turn.");
                }
            }
        });
        
        //this is the menu item info code
        menuItemInfo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String infoInfo = "Rules of Webale Chess";
                String infoNote = "1. The Sun only move in one step in any direction. Game ends when the Sun is captured.\n2. The Chevron moves in L-Shape: similar to a knight.\n3. The Triangle moves diagonally: similar to a bishop.\n4. The Plus moves up and down, or left and right: similar to rook.\n5. The Arrow only move 1 or 2 steps forward each time and turns around when it reaches the other sides.";
                JOptionPane.showMessageDialog(mid, infoNote, infoInfo, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    //this method is used to get the image for the chess pieces
    //this method will call the method from class ChessBoard
    public void imageSet(){
        board.addRedImg("Assets/PlusR.png");
        board.addRedImg("Assets/TriangleR.png");
        board.addRedImg("Assets/ChevronR.png");
        board.addRedImg("Assets/SunR.png"); 
        board.addRedImg("Assets/ArrowR.png");
        
        board.addBlueImg("Assets/PlusB.png");
        board.addBlueImg("Assets/TriangleB.png");
        board.addBlueImg("Assets/ChevronB.png");
        board.addBlueImg("Assets/SunB.png");
        board.addBlueImg("Assets/ArrowB.png");
    }
    
    //this is the method for the game setting
    public void gameSet(){
        menuSet(); //call the menu setting method
        imageSet(); //call the images
        gameplay.gamePieceSet(); //call the chess piece setting method from class GamePlay
    }
    
    public void buttonSet(int i){
        GameSlot gameslot = board.getGameSlot(i);
        GamePiece gamepiece = gameslot.getGamePiece();
        Image icon;
        if(gamepiece != null){
            if(gamepiece.getGamePlayer().equals(gameplay.getGamePlayerTurn())){
                if(gamepiece.getGamePieceName().equals("Arrow") && gamepiece.getReachEnd()){
                    icon = loadImage(board.getImg(gamepiece.getGamePieceName() + gamepiece.getGamePlayer().getColor()), true);
                }
                else{
                    icon = loadImage(board.getImg(gamepiece.getGamePieceName() + gamepiece.getGamePlayer().getColor()), false);
                }
            }
            else{
                if(gamepiece.getGamePieceName().equals("Arrow") && gamepiece.getReachEnd()){
                    icon = loadImage(board.getImg(gamepiece.getGamePieceName() + gamepiece.getGamePlayer().getColor()), false);
                }
                else{
                    icon = loadImage(board.getImg(gamepiece.getGamePieceName() + gamepiece.getGamePlayer().getColor()), true);
                }
            }
        }
        else{
            icon = null;
        }
        
        JButton btn = new JButton();
        if(icon != null){
            btn.setIcon(new ImageIcon(icon));
        }
        btn.addActionListener(this);
        buttonGameSlot.add(btn);
        mid.add(btn);
    }
    
    //this method is used to create the chessboard and set the placement of respective chess pieces (button) on board
    public void gameBoard(){
        for(int i = 0; i < board.getBoardSize(); i++){
            buttonSet(i);
        }
    }    
    
    //this method is used to reload the game: similar to refresh
    public void gameReload(boolean endgameplay){
        mid.removeAll(); //remove everything in the middle panel
        buttonGameSlot.clear(); //clear the ArrayList button
        gameBoard(); //call back this method to set back the button
        revalidate();
        repaint();
        if(endgameplay){
            for(int i = 0; i < buttonGameSlot.size(); i++){
                buttonGameSlot.get(i).removeActionListener(this);
            }
        }
    }
    
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton)e.getSource();
        GameSlot gameslot = board.getGameSlot(buttonGameSlot.indexOf(button));
        boolean hasMoved = gameplay.gameMove(gameslot);
        if(hasMoved){
            String gameWinner = gameplay.getWinner();
            if(gameWinner != null){
                notification.setText("Player " + gameWinner + " wins the game!");
                gameReload(true);
            }
            else{
                String player = gameplay.getGamePlayerTurn().getColor();
                notification.setText("Player " + player + " turn!");
                if(gameplay.getNumTurn() % 4 == 0){
                    gameplay.gamepieceChange();
                }
                board.reverse(); //this is to rotate the chessboard
                gameReload(false);
            }
        }
        
    }
    
    private Image loadImage(String path, boolean flip){
        BufferedImage image = null;
        try{
            image = ImageIO.read(new File(path));
        }
        catch(IOException e){}
        
        if(flip){
            AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
            tx.translate(0, -image.getHeight(null));
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = op.filter(image, null);
        }
        image = resizeImage(image, 40, 40);
        
        return image;
    }
    
    public static BufferedImage resizeImage(BufferedImage image, int newW, int newH) { 
        Image tmp = image.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
    
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
    
        return dimg;
    }
    
    public static void main(String[] args){
        new MainWebaleChess();
    }
}