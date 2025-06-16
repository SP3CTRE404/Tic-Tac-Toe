package Source;

public class GameLogic {
    private final String[] board;
    private String currentPlayer;
    private int[] winningLineIndices; // To store the winning line indices

    public GameLogic() {
        board = new String[9];
        currentPlayer = "X"; // X starts first
        winningLineIndices = null; // Initialize winning line indices
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int position) {
        if (position < 0 || position >= 9 || board[position] != null) {
            return false; // Invalid move
        }
        board[position] = currentPlayer;
        return true; // Move made successfully
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    public boolean checkWin() {
        //all possible winning combinations so the method can return the winning line
        int[][] lines = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6} // Diagonals
        };
        for (int[] line : lines) {
            String p1 = board[line[0]];
            String p2 = board[line[1]];
            String p3 = board[line[2]];

            if (p1 != null && p1.equals(p2) && p1.equals(p3)) {
                winningLineIndices = line; // Store the winning line
                return true; // Win detected
            }
        }
        return false; // No winning combination found
    }

    public String[] getBoard() {
        return board;
    }

    public boolean isBoardFull() {
        for (String cell : board) {
            if (cell == null) {
                return false; // Found an empty cell
            }
        }
        return true; // No empty cells, board is full
    }
    public int[] getWinningLine() {
        return winningLineIndices;
    }
}
