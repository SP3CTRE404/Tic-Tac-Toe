Tic Tac Toe
============================

A modern, feature-rich Tic Tac Toe game built in Java using Swing. Play against another player or challenge the computer's AI (Minimax algorithm). The game features a clean UI, visual winning line, and smooth gameplay.

Features
--------
- Player vs Player and Player vs Computer modes
- Minimax AI: The computer plays optimally and never loses
- Modern, intuitive GUI with clear status and player indicators
- Winning line drawn over the winning sequence
- Reset and Back buttons for easy navigation
- Responsive design and smooth user experience

Getting Started
---------------

Prerequisites:
- Java JDK 8 or higher

Running the Game:
1. Clone the repository:
   git clone https://github.com/yourusername/tic-tac-toe-java.git
   cd tic-tac-toe-java/Tic Tac Toe/Source

2. Compile the source files:
   javac *.java

3. Run the game:
   java Source.Main

Project Structure
-----------------
Tic Tac Toe/
  Source/
    GameLogic.java      # Game state and rules
    Main.java           # Entry point
    Minimax.java        # AI logic for computer moves
    TicTacToeGUI.java   # Main GUI and user interaction

How to Play
-----------
- Start the game and choose your mode: Player vs Player or Player vs Computer.
- Player X always goes first.
- Click on any empty cell to make your move.
- In Player vs Computer mode, the computer will play as O after a short delay.
- The game will highlight the winning line when someone wins.
- Use the Reset button to start a new game, or Back to return to the mode selection screen.

Customization
-------------
- You can adjust the AI difficulty or UI appearance by editing the source files.
- The buffer time before the computer moves can be changed in TicTacToeGUI.java (scheduleComputerMove method).

Contributing
------------
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

Enjoy playing Tic Tac Toe! 
