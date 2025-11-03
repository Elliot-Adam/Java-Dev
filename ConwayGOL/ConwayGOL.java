package ConwayGOL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class ConwayGOL implements ActionListener , MouseListener {
    public void mouseEntered(MouseEvent e){};
    public void mouseExited(MouseEvent e){};
    public void mouseReleased(MouseEvent e){};
    public void mouseClicked(MouseEvent e){};

    final int SCREEN_WIDTH = 500;
    final int SCREEN_HEIGHT = 500;

    final int PIXEL_WIDTH = 1;
    final int PIXEL_HEIGHT = 1;

    Timer timer = new Timer(100,this);
    private Rectangle rect = new Rectangle();
    private JLabel screenLabel = new JLabel(){
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            switch (drawStatus) {
                case ADD:
                    System.out.println("HERE3");
                    g2d.fill3DRect(rect.x,rect.y,rect.width,rect.height,false);
                    break;
            
                case DELETE:
                    g2d.clearRect(rect.x,rect.y,rect.width,rect.height);
                    break;
            }
           
    }};
    
    private JFrame mainFrame = new JFrame(){};
    private JButton playButton = new JButton();

    Cell[][] board = new Cell[SCREEN_WIDTH / PIXEL_WIDTH][SCREEN_HEIGHT / PIXEL_HEIGHT];
    ArrayList<Cell> aliveCells = new ArrayList<Cell>();
    private Rectangle playButtonRect = new Rectangle(0,0,50,50);
    
    enum DrawStatus {
        DELETE,
        ADD
    }

    enum GameStatus {
        PAUSED,
        PLAYING
    }
    
    private GameStatus gameStatus = GameStatus.PAUSED;
    private DrawStatus drawStatus = DrawStatus.ADD;
    
    

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
        for (Cell[] column : board){
            Arrays.fill(column,new Cell());
        }
        displaySetup();
        mainFrame.addMouseListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == playButton){
            HashMap<GameStatus, GameStatus> switcher = new HashMap<GameStatus, GameStatus>(){{
                put(GameStatus.PLAYING,GameStatus.PAUSED);
                put(GameStatus.PAUSED,GameStatus.PLAYING);
            }};
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
            //Every tick while active
            ArrayList<Cell> delCells = new ArrayList<Cell>();
            for (Cell cell : aliveCells){
                ArrayList<Cell> neighbors = cell.getNeighbors(board);
                neighbors.add(cell);
                for (Cell local : neighbors){
                    boolean status = local.checkStatus(board);
                    if (!status){
                        delCells.add(local);
                    }
                }                
            }
            for (Cell cell : delCells) {
                board[cell.x][cell.y].alive = false;
                aliveCells.remove(cell);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        if (gameStatus == GameStatus.PLAYING) {
            return;
        }

        //System.out.println(e.getPoint());
        Cell clickedCell = board[e.getX() / PIXEL_WIDTH][e.getY() / PIXEL_HEIGHT]; 
        if (!clickedCell.alive){
            // Clicked on a dead cell
            Cell newCell =  new Cell(e.getX() / PIXEL_WIDTH, e.getY() / PIXEL_HEIGHT);
            newCell.alive = true;
            board[e.getX() / PIXEL_WIDTH][e.getY() / PIXEL_HEIGHT] = newCell;
            aliveCells.add(newCell);
            System.out.println("HERE2");
            drawStatus = DrawStatus.ADD;
            rect.setBounds(e.getX() ,e.getY(),PIXEL_WIDTH,PIXEL_HEIGHT);
            screenLabel.paintImmediately(rect);
        }

        else {
            board[e.getX() / PIXEL_WIDTH][e.getY() / PIXEL_HEIGHT].alive = false;
            aliveCells.remove(clickedCell);

            drawStatus = DrawStatus.DELETE;
            rect.setBounds(e.getX() - 15 ,e.getY() - 35,PIXEL_WIDTH + 5,PIXEL_HEIGHT + 5);
            screenLabel.paintImmediately(rect);
            System.out.println("HERE");
            
            //screenLabel.remove(screenLabel.getComponentAt(e.getX(), e.getY()));
            //screenLabel.repaint();
        }
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