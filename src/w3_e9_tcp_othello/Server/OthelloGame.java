package w3_e9_tcp_othello.Server;

class OthelloGame
{
    private OthelloPlayer currentPlayer;
    private static final int BOARD_SIZE = 8;
    private final OthelloPlayer[][] board = new OthelloPlayer[BOARD_SIZE][BOARD_SIZE];

    void initBoard(OthelloPlayer playerBlack, OthelloPlayer playerWhite)
    {
        board[3][3] = playerWhite;
        board[3][4] = playerBlack;
        board[4][3] = playerBlack;
        board[4][4] = playerWhite;
    }

    void setCurrentPlayer(OthelloPlayer player)
    {
        currentPlayer = player;
    }

    OthelloPlayer[][] getBoard()
    {
        return board;
    }

    synchronized boolean performTurn(int row, int column, OthelloPlayer player)
    {
        boolean hasFlipped = false;

        if (hasAdjecentOpponent(row, column, player))
        {
            hasFlipped |= checkDirection(row, column, player, -1, 0, true);
            hasFlipped |= checkDirection(row, column, player, 1, 0, true);
            hasFlipped |= checkDirection(row, column, player, 0, 1, true);
            hasFlipped |= checkDirection(row, column, player, 0, -1, true);
            hasFlipped |= checkDirection(row, column, player, 1, 1, true);
            hasFlipped |= checkDirection(row, column, player, -1, 1, true);
            hasFlipped |= checkDirection(row, column, player, -1, -1, true);
            hasFlipped |= checkDirection(row, column, player, 1, -1, true);
        }

        if (hasFlipped)
        {
            board[row][column] = player;
            currentPlayer = currentPlayer.getOpponent();
            currentPlayer.otherPlayerMoved();
        }

        return hasFlipped;
    }

    private boolean isInsideBoard(int row, int column)
    {
        if (row >= BOARD_SIZE || row < 0 || column >= BOARD_SIZE || column < 0)
        {
            return false;
        }

        return true;
    }

    synchronized boolean checkDirection(int row, int col, OthelloPlayer player, int rowDir, int colDir, boolean doFlip)
    {
        OthelloPlayer opponent = player.getOpponent();
        boolean possibleToFlipAnyPieces = false;
        int currentRow = row + rowDir;
        int currentCol = col + colDir;

        // Check if we are out of bounds
        if (!isInsideBoard(currentRow, currentCol))
        {
            return possibleToFlipAnyPieces;
        }

        // Proceed if we find a opponent piece in that direction
        while (board[currentRow][currentCol] == opponent)
        {
            currentRow += rowDir;
            currentCol += colDir;

            // Fail if we get out of the board before hitting one of our own pieces
            if (!isInsideBoard(currentRow, currentCol))
            {
                break;
            }

            // Stop when we hit one of our own pieces
            if (board[currentRow][currentCol] == player)
            {
                possibleToFlipAnyPieces = true;
                break;
            }
        }

        // Do the actual flipping
        if (possibleToFlipAnyPieces && doFlip)
        {
            // Move backwards to the original point, without flipping the origin piece
            while (!(currentRow == row && currentCol == col))
            {
                board[currentRow][currentCol] = player;
                currentRow -= rowDir;
                currentCol -= colDir;
            }
        }

        return possibleToFlipAnyPieces;
    }

    synchronized boolean hasAdjecentOpponent(int row, int column, OthelloPlayer player)
    {
        OthelloPlayer opponent = player.getOpponent();

        if (board[row][column] == null)
        {
            if (row + 1 < BOARD_SIZE && board[row + 1][column] == opponent)
            {
                return true;
            }
            else if (row + 1 < BOARD_SIZE && column + 1 < BOARD_SIZE && board[row + 1][column + 1] == opponent)
            {
                return true;
            }
            else if (column + 1 < BOARD_SIZE && board[row][column + 1] == opponent)
            {
                return true;
            }
            else if (row - 1 >= 0 && column + 1 < BOARD_SIZE && board[row - 1][column + 1] == opponent)
            {
                return true;
            }
            else if (row - 1 >= 0 && board[row - 1][column] == opponent)
            {
                return true;
            }
            else if (row - 1 >= 0 && column - 1 >= 0 && board[row - 1][column - 1] == opponent)
            {
                return true;
            }
            else if (column - 1 >= 0 && board[row][column - 1] == opponent)
            {
                return true;
            }
            else if (row + 1 < BOARD_SIZE && column - 1 >= 0 && board[row + 1][column - 1] == opponent)
            {
                return true;
            }
        }

        return false;
    }
}
