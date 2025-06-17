package Source;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class TicTacToeGUI implements ActionListener {

    private final JFrame frame = new JFrame();
    private final CardLayout cardLayout = new CardLayout();

    private final JPanel playPanel = new JPanel();
    private final JPanel startPanel = new JPanel();
    private final JPanel grid;

    private JLabel statusLabel;

    private final JButton[] playButtons = new JButton[9];
    private JButton resetButton;
    private JButton backbutton;

    private GameLogic gameLogic;

    private int[] winningLineDisplayIndices; // To store the winning line indices

    private boolean gameActive = true;
    private boolean vsComputer = false; // true if playing against computer
    private JRadioButton pvpButton;
    private JRadioButton pvcButton;
    private ButtonGroup modeGroup;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    TicTacToeGUI() {
        gameLogic = new GameLogic();
        grid = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g); // Paints children (buttons) first
                if (winningLineDisplayIndices != null && playButtons[0] != null) { // Ensure buttons are initialized
                    // Get the actual buttons using the stored indices
                    JButton button1 = playButtons[winningLineDisplayIndices[0]];
                    JButton button2 = playButtons[winningLineDisplayIndices[2]];

                    // Calculate center points relative to the grid panel
                    Point p1 = getButtonCenter(button1);
                    Point p2 = getButtonCenter(button2);

                    Graphics2D g2d = (Graphics2D) g.create();
                    try {
                        g2d.setColor(new Color(0, 255, 0, 200)); // Semi-transparent green
                        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                    } finally {
                        g2d.dispose();
                    }
                }
            }

            // Helper method to get center of a button relative to this grid panel
            private Point getButtonCenter(JButton button) {
                int x = button.getX() + button.getWidth() / 2;
                int y = button.getY() + button.getHeight() / 2;
                return new Point(x, y);
            }
        };

        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(75, 20, 10, 20));
        frame.setBackground(new Color(64, 67, 79));
        frame.setLayout(cardLayout);

        createStartPanel();
        createPlayPanel();

        frame.add(startPanel, "Start");
        frame.add(playPanel, "Game");

        cardLayout.show(frame.getContentPane(), "Start");
        frame.setLocationRelativeTo(null);
        updateStatusLabel();
        frame.setVisible(true);
    }

    @SuppressWarnings("unused")
    private void createStartPanel() {
        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(new Color(64, 67, 79));
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Tic Tac Toe");
        welcome.setForeground(new Color(55, 159, 182));
        welcome.setFont(new Font("Arial", Font.BOLD, 70));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome.setBorder(BorderFactory.createEmptyBorder(50, 0, 40, 0));

        // Game mode selection
        JPanel modePanel = new JPanel();
        modePanel.setBackground(new Color(64, 67, 79));
        modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.Y_AXIS));
        modePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // player vs player
        pvpButton = new JRadioButton("Player vs Player");
        pvpButton.setBackground(new Color(64, 67, 79));
        pvpButton.setForeground(Color.WHITE);
        pvpButton.setFocusable(false);
        pvpButton.setFont(new Font("Arial", Font.PLAIN, 20));
        pvpButton.setSelected(true);

        // player vs computer
        pvcButton = new JRadioButton("Player vs Computer");
        pvcButton.setBackground(new Color(64, 67, 79));
        pvcButton.setForeground(Color.WHITE);
        pvcButton.setFocusable(false);
        pvcButton.setFont(new Font("Arial", Font.PLAIN, 20));

        modeGroup = new ButtonGroup();
        modeGroup.add(pvpButton);
        modeGroup.add(pvcButton);
        modePanel.add(pvpButton);
        modePanel.add(pvcButton);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(new Color(67, 86, 89));
        startButton.setForeground(new Color(55, 159, 182));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFocusable(false);
        startButton.addActionListener(e -> {
            vsComputer = pvcButton.isSelected();
            cardLayout.show(frame.getContentPane(), "Game");
            resetGame();
        });

        startPanel.add(welcome);
        startPanel.add(modePanel);
        startPanel.add(startButton);
    }

    private void createPlayPanel() {
        // a line that drwas the winning line

        playPanel.setLayout(new BorderLayout());
        playPanel.setBackground(new Color(64, 67, 79));
        playPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(64, 67, 79)); // Match playPanel background

        //player label
        JLabel playerLabel = new JLabel("You are X");
        playerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setHorizontalAlignment(JLabel.CENTER);

        //status label
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 25));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 15));
        resetButton.setBackground(new Color(67, 86, 89));
        resetButton.setForeground(new Color(55, 159, 182));
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);
        
        backbutton = new JButton("Back");
        backbutton.setFont(new Font("Arial", Font.BOLD, 15));
        backbutton.setBackground(new Color(67, 86, 89));
        backbutton.setForeground(new Color(55, 159, 182));
        backbutton.setFocusable(false);
        backbutton.addActionListener(this);
        
        topPanel.add(playerLabel, BorderLayout.NORTH);
        topPanel.add(statusLabel, BorderLayout.CENTER);

        topPanel.add(resetButton, BorderLayout.EAST);
        topPanel.add(backbutton, BorderLayout.WEST);

        playPanel.add(topPanel, BorderLayout.NORTH);

        grid.setLayout(new GridLayout(3, 3, 3, 3));
        grid.setBackground(new Color(64, 67, 79));
        playPanel.add(grid, BorderLayout.CENTER);

        createButtons();
    }

    private void createButtons() {
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();

            button.setFont(new Font("Arial", Font.BOLD, 80));
            button.setBackground(new Color(47, 49, 58));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            button.setOpaque(true); // Required for some LAFs
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(true);
            button.setFocusable(false);

            button.addActionListener(this);

            playButtons[i] = button;
            grid.add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == resetButton) {
            resetGame();
            return;
        }
        if (source == backbutton) {
            cardLayout.show(frame.getContentPane(), "Start");
            resetGame();
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (source == playButtons[i]) {
                if (gameActive && playButtons[i].getText().isEmpty()) {
                    String playerMakingMove = gameLogic.getCurrentPlayer();

                    if (gameLogic.makeMove(i)) { // True if move was made
                        playButtons[i].setText(playerMakingMove);
                        playButtons[i].setEnabled(false); // Disable button after successful move

                        if (playerMakingMove.equals("X")) {
                            playButtons[i].setBackground(new Color(83, 84, 143)); // Blue
                        } else {
                            playButtons[i].setBackground(new Color(89, 49, 49)); // Reddish
                        }
                        // Foreground color is already white from createButtons, or can be set here too

                        if (gameLogic.checkWin()) {
                            statusLabel.setText("Player " + playerMakingMove + " wins!");
                            gameActive = false;
                            disableAllPlayButtons();
                            winningLineDisplayIndices = gameLogic.getWinningLine();
                            if (winningLineDisplayIndices != null) {
                                grid.repaint(); // Trigger redraw of the grid to show the line
                            }
                        } else if (gameLogic.isBoardFull()) {
                            statusLabel.setText("It's a draw!");
                            gameActive = false;
                            disableAllPlayButtons();
                        } else {
                            gameLogic.switchPlayer();
                            updateStatusLabel();
                            // If vsComputer and now it's O's turn (computer)
                            if (vsComputer && gameActive && gameLogic.getCurrentPlayer().equals("O")) {
                                scheduleComputerMove();
                            }
                        }
                    }
                }
                return; // Exit after processing a play button click
            }
        }
    }

    private void scheduleComputerMove() {
        disableAllPlayButtons();
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeComputerMove();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void makeComputerMove() {
        // Use Minimax to find the best move for O
        int move = Minimax.findBestMove(gameLogic.getBoard());
        if (move != -1) {
            gameLogic.makeMove(move);
            playButtons[move].setText("O");
            playButtons[move].setEnabled(false);
            playButtons[move].setBackground(new Color(89, 49, 49)); // Reddish
            if (gameLogic.checkWin()) {
                statusLabel.setText("Player O wins!");
                gameActive = false;
                disableAllPlayButtons();
                winningLineDisplayIndices = gameLogic.getWinningLine();
                if (winningLineDisplayIndices != null) {
                    grid.repaint();
                }
            } else if (gameLogic.isBoardFull()) {
                statusLabel.setText("It's a draw!");
                gameActive = false;
                disableAllPlayButtons();
            } else {
                gameLogic.switchPlayer();
                updateStatusLabel();
                enableEmptyPlayButtons(); // Enable buttons for the next player
            }
        }
    }

    private void enableEmptyPlayButtons() {
        for (int i = 0; i < 9; i++) {
            if (playButtons[i].getText().isEmpty()) {
                playButtons[i].setEnabled(true);
            }
        }
    }

    private void updateStatusLabel() {
        if (gameActive) {
            statusLabel.setText("Player " + gameLogic.getCurrentPlayer() + "'s turn");
        }
    }

    private void disableAllPlayButtons() {
        for (JButton btn : playButtons) {
            btn.setEnabled(false);
        }
    }

    private void resetGame() {
        gameLogic = new GameLogic();
        gameActive = true;
        for (int i = 0; i < 9; i++) {
            playButtons[i].setText("");
            playButtons[i].setBackground(new Color(47, 49, 58));
            playButtons[i].setEnabled(true);
        }
        winningLineDisplayIndices = null; // Reset winning line indices
        grid.repaint(); // Clear the winning line display
        updateStatusLabel();
        // If vsComputer and computer is O and starts first, make computer move
        if (vsComputer && gameLogic.getCurrentPlayer().equals("O")) {
            scheduleComputerMove();
        }
    }
}
