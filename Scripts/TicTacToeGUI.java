package Scripts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI implements ActionListener {
    JFrame frame = new JFrame();
    CardLayout cardLayout = new CardLayout();

    JPanel playPanel = new JPanel();
    JPanel grid = new JPanel();

    JPanel startPanel = new JPanel();
    JButton[] playButtons = new JButton[9];
    String[] players = {"X", "O"};
    int[] currentPlayer = {0};

    TicTacToeGUI() {

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(75, 20, 10, 20));
        frame.setBackground(new Color(64, 67, 79));
        frame.setLayout(cardLayout);

        createStartPanel();
        createPlayPanel();

        createButtons();

        frame.add(startPanel, "Start");
        frame.add(playPanel, "Game");


        cardLayout.show(frame.getContentPane(), "Start");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void createStartPanel(){
        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(new Color(64, 67, 79));
        startPanel.setBackground(new Color(64, 67, 79));
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Tic Tac Toe");
        welcome.setForeground(new Color(55, 159, 182));
        welcome.setFont(new Font("Arial", Font.BOLD, 70));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome.setBorder(BorderFactory.createEmptyBorder(50, 0, 40, 0));

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(new Color(67, 86, 89));
        startButton.setForeground(new Color(55, 159, 182));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFocusable(false);
        startButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Game"));

        startPanel.add(welcome);
        startPanel.add(startButton);

    }

    public void createPlayPanel(){

        playPanel.setLayout(new BorderLayout());
        playPanel.setBackground(new Color(64, 67, 79));
        playPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        grid.setLayout(new GridLayout(3, 3, 5, 5));
        grid.setBackground(new Color(64, 67, 79));

        JLabel status = new JLabel();
        status.setText("blah");
        status.setFont(new Font("Arial", Font.BOLD, 40));

        playPanel.add(status, BorderLayout.NORTH);
        playPanel.add(grid, BorderLayout.CENTER);
    }

    public void createButtons() {
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();

            button.setFont(new Font("Arial", Font.BOLD, 80));
            button.setBackground(new Color(47, 49, 58));
            button.setOpaque(true);  // Required for some LAFs
            button.setContentAreaFilled(true);
            button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setFocusable(false);

            button.addActionListener(this);

            playButtons[i] = button;
            grid.add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (clickedButton.getText().equals("")) {
            String player = players[currentPlayer[0]];
            clickedButton.setText(player);

            // ✅ Set color AFTER setting text
            if (player.equals("X")) {
                clickedButton.setBackground(new Color(83, 84, 143)); // Blue
            } else {
                clickedButton.setBackground(new Color(89, 49, 49)); // Green
            }

            clickedButton.setEnabled(false);
            currentPlayer[0] = 1 - currentPlayer[0]; // Switch player
        }
    }
}