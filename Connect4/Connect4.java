import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.event.*;

import java.util.HashMap;

public class Connect4 implements MouseInputListener {
    public void mouseDragged(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    
    private JFrame frame = new JFrame("Connect 4");
    private Board board = new Board();
    
    private String player = "red";

    private String switchPlayer(String player){
        HashMap<String, String> player_map = new HashMap<>();
        player_map.put("red","yellow");
        player_map.put("yellow","red");
        
        return player_map.get(player);
    }
    
    private int getColumnPressed(int x){
        int width = (int)frame.getSize().getWidth();
        int column = (int)(x / (width / 7)) + 1;
        return column;
    }
    
    public void mousePressed(MouseEvent e){
        int column = getColumnPressed(e.getX());
        JLabel img = null;
        try{img = new JLabel(new ImageIcon(ImageIO.read(new File("Connect4\\Assets\\piece_" + player + ".png"))));}
        catch (IOException exception) {exception.printStackTrace();}
        
        
        switchPlayer(player);
    }
    
    public void mouseMoved(MouseEvent e){
        int column = getColumnPressed(e.getX());
        JLabel img = null;
        try{img = new JLabel(new ImageIcon(ImageIO.read(new File("Connect4\\Assets\\piece_" + player + ".png"))));}
        catch (IOException exception) {exception.printStackTrace();}
        img.setBounds(0,0,100,100);
        
        frame.add(img);
        System.out.println(img);
    }
    
    public void connect_4(){
        try {frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Connect4\\Assets\\board.png")))));}
        catch (IOException exception) {exception.printStackTrace();}
        frame.pack();
        frame.setVisible(true);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Connect4 mainObj = new Connect4();
                mainObj.connect_4();
            }
        });
    }
}