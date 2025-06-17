package Source;

public abstract class Minimax {
    public Minimax() {
    }

    private static final int MAX_DEPTH = 9; // Maximum depth for minimax search

    public static int findBestMove(String[] board) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        
        for (int i = 0; i < 9; i++) {
            if (board[i] == null) {
                board[i] = "O"; // Computer's move
                int score = minimax(board, 0, false);
                board[i] = null; // Undo move
                
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    private static int minimax(String[] board, int depth, boolean isMaximizing) {
        // Check terminal states
        if (checkWin(board, "O")) return 10 - depth;
        if (checkWin(board, "X")) return depth - 10;
        if (isBoardFull(board)) return 0;
        
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == null) {
                    board[i] = "O";
                    int score = minimax(board, depth + 1, false);
                    board[i] = null;
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == null) {
                    board[i] = "X";
                    int score = minimax(board, depth + 1, true);
                    board[i] = null;
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    private static boolean checkWin(String[] board, String player) {
        int[][] lines = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6} // Diagonals
        };
        
        for (int[] line : lines) {
            if (board[line[0]] != null && 
                board[line[0]].equals(player) && 
                board[line[1]] != null && 
                board[line[1]].equals(player) && 
                board[line[2]] != null && 
                board[line[2]].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBoardFull(String[] board) {
        for (String cell : board) {
            if (cell == null) {
                return false;
            }
        }
        return true;
    }
}
