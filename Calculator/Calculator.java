import javax.swing.*;
import java.awt.event.*;
import java.awt.*;  

public class Calculator implements ActionListener{
    static class Consts{
        //Display bar Consts
        static final int DISPLAY_WIDTH = 200;
        static final int DISPLAY_HEIGHT = 50;
        static final int YDISPLAY_OFFSET = 100 - DISPLAY_HEIGHT;
        static final int XDISPLAY_OFFSET = 100;
        
         //Screen Consts
        static final int SCREEN_WIDTH = 400;
        static final int SCREEN_HEIGHT = 400;
        
        //Button Consts
        static final int BUTTON_HEIGHT = 50;
        static final int BUTTON_WIDTH = 50;
    }
    
    private JButton[] buttons;
    private double total;
    private String display = "";
    private static JTextArea calc_display = new JTextArea("");
    private JFrame f = new JFrame("Elliot's Calculator");
    EvalLogic eval = new EvalLogic();
    
    public static JButton[] getButtons(){
        JButton one = new JButton("1");
        JButton two = new JButton("2");
        JButton three = new JButton("3");
        JButton plus = new JButton("+");
        JButton four = new JButton("4");
        JButton five = new JButton("5");
        JButton six = new JButton("6");
        JButton minus = new JButton("-");
        JButton seven = new JButton("7");
        JButton eight = new JButton("8");
        JButton mult = new JButton("*");
        JButton nine = new JButton("9");
        JButton div = new JButton("/");
        JButton clr = new JButton("C");
        JButton zero = new JButton("0");
        JButton equal = new JButton("=");// 0   1    2    3    4    5    6   7      8    9     10   11   12  13   14    15
        JButton[] buttons = new JButton[] {one,two,three,plus,four,five,six,minus,seven,eight,nine,mult,clr,zero,equal, div};
        return buttons;
    }
    
    public static void display_setup(){
        calc_display.setBounds(Consts.XDISPLAY_OFFSET,Consts.YDISPLAY_OFFSET,Consts.DISPLAY_WIDTH,Consts.DISPLAY_HEIGHT);
        calc_display.setBackground(Color.CYAN);
        calc_display.setFont(new Font("SERIF",Font.PLAIN, Consts.DISPLAY_HEIGHT));
    }
    
    public void Calc_Runner() {
        System.out.println();
        buttons = getButtons();
        for (int i = 0; i < buttons.length; i++){
            buttons[i].setBounds(Consts.BUTTON_WIDTH*(i % 4) + 100,Consts.BUTTON_HEIGHT*Math.floorDiv(i,4) + 100,Consts.BUTTON_WIDTH,Consts.BUTTON_HEIGHT);
            buttons[i].addActionListener(this);
            f.add(buttons[i]);
        }
        display_setup();
        
        f.add(calc_display);
        f.setSize(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
        f.setLayout(null);
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        String pressed = e.getActionCommand();
        if (pressed.equals("C")){
            display = "";   
        }
        else if (pressed.equals("=")){
            try{ 
                total = eval.eval(display); 
                if ((int)(total) == total){ display = String.valueOf((int)(total));}
                else{ display = String.valueOf(total);}
            }
            catch (Exception exception){ 
                display = "NaN"; 
                System.out.println("FAILURE " + exception + "\n");
                exception.printStackTrace();
            }
        }
        else {
            display += pressed;
        }
        calc_display.setText(display);
        f.add(calc_display);
        //System.out.println(display); // DEBUG
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Calculator mainObj = new Calculator();
                mainObj.Calc_Runner();
            }
        });
    }
}