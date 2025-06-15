package Scripts;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Board extends JFrame {
    public void generateBoard(){
        Panel panel = new Panel();

        setTitle("Tic Tac Toe");
        setSize(500,400);

        ImageIcon image = new ImageIcon("Scripts/XO.png");
        setIconImage(image.getImage());

        add(panel);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
