package ConwayGOL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.ArrayList;

public class ConwayGOL implements ActionListener , MouseListener {
    public void mouseEntered(MouseEvent e){};
    public void mouseExited(MouseEvent e){};
    public void mouseReleased(MouseEvent e){};
    public void mouseClicked(MouseEvent e){};

    final int SCREEN_WIDTH = 500;
    final int SCREEN_HEIGHT = 500;

    final int PIXEL_WIDTH = 5;
    final int PIXEL_HEIGHT = 5;

    Timer timer = new Timer(100,this);
    private Rectangle rect = new Rectangle();
    private JLabel screenLabel = new JLabel(){
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.fill3DRect(rect.x,rect.y,rect.width,rect.height,false);
            //g2d.draw(rect);
    }};
    
    private JFrame mainFrame = new JFrame(){};
    private JButton playButton = new JButton();

    Cell[][] board = new Cell[SCREEN_WIDTH / PIXEL_WIDTH][SCREEN_HEIGHT / PIXEL_HEIGHT];
    ArrayList<Cell> aliveCells = new ArrayList<Cell>();
    private Rectangle playButtonRect = new Rectangle(0,0,50,50);
    
    enum GameStatus {
        PAUSED,
        PLAYING
    }
    
    private GameStatus gameStatus = GameStatus.PAUSED;
    
    

    private ImageIcon imgScaler(String fileName){
        ImageIcon newImgIcon = null;
        try{
            newImgIcon = new ImageIcon(ImageIO.read(new File(fileName)).getScaledInstance(playButtonRect.width, playButtonRect.height, java.awt.Image.SCALE_SMOOTH));
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
        return newImgIcon;
    }

    private void displaySetup(){
        mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainFrame.setVisible(true);
        mainFrame.setLayout(null);
        
        playButton.setIcon(imgScaler("ConwayGOL\\Assets\\play_button.png"));
        playButton.setBounds(playButtonRect);
        playButton.addActionListener(this);

        screenLabel.setBounds(mainFrame.getBounds());
        screenLabel.setVisible(true);

        mainFrame.add(playButton);
        mainFrame.add(screenLabel);
    }
    
    public void gameOfLife(){
        displaySetup();
        mainFrame.addMouseListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        HashMap<GameStatus, GameStatus> switcher = new HashMap<GameStatus, GameStatus>(){{
            put(GameStatus.PLAYING,GameStatus.PAUSED);
            put(GameStatus.PAUSED,GameStatus.PLAYING);
        }};

        if (e.getSource() == playButton){
            gameStatus = switcher.get(gameStatus);
            switch (gameStatus){
                case PLAYING:
                    playButton.setIcon(imgScaler("ConwayGOL\\Assets\\pause_button.png"));
                    timer.start();
                    break;
                    
                case PAUSED:
                    playButton.setIcon(imgScaler("ConwayGOL\\Assets\\play_button.png"));
                    timer.stop();
                    break;
            }
        
            mainFrame.repaint();
        }

        if (e.getSource() == timer){
            for (Cell cell : aliveCells){
                boolean status = cell.checkStatus(board);
                ArrayList<Cell> neighbors = cell.getNeighbors(board);
            }

        }
        

        
    }

    @Override
    public void mousePressed(MouseEvent e){
        if (gameStatus == GameStatus.PLAYING) {
            return;
        }

        //System.out.println(e.getPoint());
        if (board[e.getX() / PIXEL_WIDTH][e.getY() / PIXEL_HEIGHT] == null){
            Cell newCell =  new Cell(e.getX() / PIXEL_WIDTH, e.getY() / PIXEL_HEIGHT)
            board[e.getX() / PIXEL_WIDTH][e.getY() / PIXEL_HEIGHT] = newCell;
            aliveCells.add(newCell);
        }

        rect.setBounds(e.getX() - 5 ,e.getY() - 25,PIXEL_WIDTH,PIXEL_HEIGHT);
        screenLabel.paintImmediately(rect);
    };
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConwayGOL game = new ConwayGOL();
                game.gameOfLife();
            }
        });
    }
}