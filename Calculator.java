import javax.swing.*;
import java.awt.event.*;
import java.awt.*;  

public class Calculator implements ActionListener{
    private JButton[] buttons;
    private double total;
    private String display = "";
    private static JTextArea calc_display = new JTextArea("");
    private JFrame f = new JFrame();
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
        JButton equal = new JButton("=");// 0   1    2    3    4    5    6   7      8    9     10   11   12  13   14     15
        JButton[] buttons = new JButton[] {one,two,three,plus,four,five,six,minus,seven,eight,nine,mult,clr,zero,equal, div};
        return buttons;
    }
    
    public static void display_setup(){
        final int DISPLAY_WIDTH = 200;
        final int DISPLAY_HEIGHT = 50;
        final int YDISPLAY_OFFSET = 100 - DISPLAY_HEIGHT;
        final int XDISPLAY_OFFSET = 100;
        
        calc_display.setBounds(XDISPLAY_OFFSET,YDISPLAY_OFFSET,DISPLAY_WIDTH,DISPLAY_HEIGHT);
        calc_display.setBackground(Color.blue);
        calc_display.setFont(new Font("SERIF",Font.PLAIN, DISPLAY_HEIGHT));
    }
    
    public void Calc_Runner() {
        final int SCREEN_WIDTH = 400;
        final int SCREEN_HEIGHT = 400;
        final int BUTTON_HEIGHT = 50;
        final int BUTTON_WIDTH = 50;
        
        buttons = getButtons();
        for (int i = 0; i < buttons.length; i++){
            buttons[i].setBounds(BUTTON_WIDTH*(i % 4) + 100,BUTTON_HEIGHT*Math.floorDiv(i,4) + 100,BUTTON_WIDTH,BUTTON_HEIGHT);
            buttons[i].addActionListener(this);
            f.add(buttons[i]);
        }
        display_setup();
        
        f.add(calc_display);
        f.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
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
                System.out.println("FAILURE " + exception);
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
                Calculator test = new Calculator();
                test.Calc_Runner();
            }
        });
    }
}