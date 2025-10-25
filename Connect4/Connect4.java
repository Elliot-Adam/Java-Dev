import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;  

public class Connect4 {
    private JFrame frame = new JFrame("Connect 4");
    
    public void connect_4(){
        frame.setSize(1000,1000);
        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Assets\\board.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
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