package w3_e9_tcp_othello.Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

public class Play extends Thread
{
    private final OthelloClient othelloClient;
    private final BufferedReader socketReader;

    Play(OthelloClient othelloClient, BufferedReader socketReader)
    {
        this.othelloClient = othelloClient;
        this.socketReader = socketReader;
        start();
    }

    private synchronized void updateBoard(String boardData)
    {
        String[] data = boardData.split(",");
        int squareCounter = 0;
        int blackPieces = 0;
        int whitePieces = 0;

        for (int row = 0; row < othelloClient.BOARD_SIZE; row++)
        {
            for (int column = 0; column < othelloClient.BOARD_SIZE; column++)
            {
                if (data[squareCounter].equals("0"))
                {
                    othelloClient.board[row][column].setIcon(othelloClient.blankIcon);
                }
                else if (data[squareCounter].equals("Black"))
                {
                    othelloClient.board[row][column].setIcon(othelloClient.blackIcon);
                    blackPieces++;
                }
                else if (data[squareCounter].equals("White"))
                {
                    othelloClient.board[row][column].setIcon(othelloClient.whiteIcon);
                    whitePieces++;
                }

                squareCounter++;
            }
        }

        othelloClient.blackScoreLabel.setText("Black: " + blackPieces);
        othelloClient.whiteScoreLabel.setText("White: " + whitePieces);
    }

    @Override
    public void run()
    {
        String receivedMessage;

        try
        {
            while ((receivedMessage = socketReader.readLine()) != null && !Thread.interrupted())
            {
                if (receivedMessage.startsWith("WELCOME"))
                {
                    othelloClient.playerLabel.setText("You play: " + receivedMessage.substring(8));

                    othelloClient.blackScoreLabel.setText("Black: 2");
                    othelloClient.whiteScoreLabel.setText("White: 2");
                }

                else if (receivedMessage.startsWith("MESSAGE"))
                {
                    othelloClient.statusLabel.setText(receivedMessage.substring(8));

                    if (receivedMessage.endsWith("Your move"))
                    {
                        othelloClient.currentState = othelloClient.YOUR_TURN;
                    }
                    else if (receivedMessage.endsWith("?"))
                    {
                        othelloClient.statusLabel.setText("Not a valid move");
                    }
                }

                else if (receivedMessage.startsWith("VALID_MOVE"))
                {
                    othelloClient.currentState = othelloClient.OPPONENTS_TURN;
                    othelloClient.statusLabel.setText("Waiting for oppenent to make a move...");
                    updateBoard(receivedMessage.substring(12));
                }

                else if (receivedMessage.startsWith("OPPONENT_MOVED"))
                {
                    othelloClient.currentState = othelloClient.YOUR_TURN;
                    othelloClient.statusLabel.setText("Your move");
                    updateBoard(receivedMessage.substring(16));
                }

                else if (receivedMessage.startsWith("VICTORY"))
                {
                    JOptionPane.showMessageDialog(null, "You won, congratulations!");
                    othelloClient.disconnect();
                }

                else if (receivedMessage.startsWith("TIE"))
                {
                    JOptionPane.showMessageDialog(null, "It's a tie!");
                    othelloClient.disconnect();
                }

                else if (receivedMessage.startsWith("DEFEAT"))
                {
                    JOptionPane.showMessageDialog(null, "You lost...");
                    othelloClient.disconnect();
                }
            }
        }
        catch (SocketException e)
        {
            othelloClient.statusLabel.setText("Welcome to Othello!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
