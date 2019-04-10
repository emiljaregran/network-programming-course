package w3_e9_tcp_othello.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OthelloPlayer extends Thread
{
    private final Socket socket;
    private final String playerColor;
    private final OthelloGame game;
    private BufferedReader socketReader;
    private PrintWriter socketWriter;
    private OthelloPlayer opponent;

    /**
     * Constructs a handler thread for a given socket and color
     * initializes the stream fields, displays the first two
     * welcoming messages.
     */
    OthelloPlayer(Socket socket, String playerColor, OthelloGame othelloGame)
    {
        this.socket = socket;
        this.playerColor = playerColor;
        this.game = othelloGame;

        try
        {
            socketWriter = new PrintWriter(socket.getOutputStream(), true);
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketWriter.println("WELCOME " + playerColor);
            socketWriter.println("MESSAGE Waiting for opponent to connect");
        }
        catch (IOException e)
        {
            System.out.println("Player died: " + e);
        }
    }

    OthelloPlayer getOpponent()
    {
        return opponent;
    }

    private String getPlayerColor()
    {
        return playerColor;
    }

    void setOpponent(OthelloPlayer opponent)
    {
        this.opponent = opponent;
    }

    /**
     * Handles the otherPlayerMoved message.
     */
    void otherPlayerMoved()
    {
        socketWriter.println("OPPONENT_MOVED ," + getCurrentBoard());
    }

    private String getCurrentBoard()
    {
        OthelloPlayer[][] board = game.getBoard();
        StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < board.length; row++)
        {
            for (int column = 0; column < board[0].length; column++)
            {
                if (board[row][column] == null)
                {
                    stringBuilder.append("0,");
                }
                else
                {
                    stringBuilder.append(board[row][column].getPlayerColor());
                    stringBuilder.append(",");
                }
            }
        }

        return stringBuilder.toString();
    }

    private boolean gameIsFinished(OthelloPlayer player)
    {
        OthelloPlayer[][] board = game.getBoard();

        for (int row = 0; row < board.length; row++)
        {
            for (int column = 0; column < board[0].length; column++)
            {
                if (game.hasAdjecentOpponent(row, column, player))
                {
                    boolean possibleToFlip = false;
                    possibleToFlip |= game.checkDirection(row, column, player, -1, 0, false);
                    possibleToFlip |= game.checkDirection(row, column, player, 1, 0, false);
                    possibleToFlip |= game.checkDirection(row, column, player, 0, 1, false);
                    possibleToFlip |= game.checkDirection(row, column, player, 0, -1, false);
                    possibleToFlip |= game.checkDirection(row, column, player, 1, 1, false);
                    possibleToFlip |= game.checkDirection(row, column, player, -1, 1, false);
                    possibleToFlip |= game.checkDirection(row, column, player, -1, -1, false);
                    possibleToFlip |= game.checkDirection(row, column, player, 1, -1, false);

                    if (possibleToFlip)
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private int getScore()
    {
        OthelloPlayer[][] board = game.getBoard();
        int score = 0;

        for (int row = 0; row < board.length; row++)
        {
            for (int column = 0; column < board[0].length; column++)
            {
                if (board[row][column] == this)
                {
                    score++;
                }
            }
        }

        return score;
    }

     /*
     * The run method of this thread.
     */
    @Override
    public void run()
    {
        try
        {
            socketWriter.println("MESSAGE All players connected");

            if (playerColor.equals("Black")) {
                socketWriter.println("MESSAGE Your move");
            }

            while (true)
            {
                String command;
                while ((command = socketReader.readLine()) != null)
                {
                    if (command.startsWith("MOVE"))
                    {
                        String[] coordinates = command.substring(5).split(",");
                        int row = Integer.parseInt(coordinates[0]);
                        int column = Integer.parseInt(coordinates[1]);

                        if (game.performTurn(row, column, this))
                        {
                            socketWriter.println("VALID_MOVE ," + getCurrentBoard());
                            if (gameIsFinished(this.getOpponent()))
                            {
                                int ownScore = getScore();
                                int opponentScore = this.getOpponent().getScore();

                                if (ownScore > opponentScore)
                                {
                                    socketWriter.println("VICTORY");
                                    this.getOpponent().socketWriter.println("DEFEAT");
                                }
                                else if (ownScore == opponentScore)
                                {
                                    socketWriter.println("TIE");
                                    this.getOpponent().socketWriter.println("TIE");
                                }
                                else
                                {
                                    socketWriter.println("DEFEAT");
                                    this.getOpponent().socketWriter.println("VICTORY");
                                }
                            }
                        }
                        else
                        {
                            socketWriter.println("MESSAGE ?");
                        }
                    }
                    else if (command.startsWith("QUIT"))
                    {
                        return;
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Player died: " + e);
        }
        finally
        {
            try {socket.close();} catch (IOException e) {e.printStackTrace();}
        }
    }
}
