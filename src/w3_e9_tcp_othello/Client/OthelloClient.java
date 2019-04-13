package w3_e9_tcp_othello.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class OthelloClient implements ActionListener
{
    final int DISCONNECTED = 0;
    final int CONNECTED = 1;
    final int YOUR_TURN = 2;
    final int OPPONENTS_TURN = 3;
    int currentState = DISCONNECTED;

    private Play play;
    private Socket socket;
    private PrintWriter socketWriter;


    final int BOARD_SIZE = 8;
    final Square[][] board = new Square[BOARD_SIZE][BOARD_SIZE];

    final ImageIcon blankIcon = new ImageIcon("src\\w3_e9_tcp_othello\\img\\empty.png");
    final ImageIcon whiteIcon = new ImageIcon("src\\w3_e9_tcp_othello\\img\\white.png");
    final ImageIcon blackIcon = new ImageIcon("src\\w3_e9_tcp_othello\\img\\black.png");

    private final JTextField hostnameTextField = new JTextField("127.0.0.1");
    private final JTextField portTextField = new JTextField("8901");
    private final JButton connectButton = new JButton("Connect");
    final JLabel playerLabel = new JLabel("");
    final JLabel blackScoreLabel = new JLabel("");
    final JLabel whiteScoreLabel = new JLabel("");
    final JLabel statusLabel = new JLabel("Welcome to Othello!");

    private OthelloClient()
    {
        JFrame frame = new JFrame("Othello - Emil Jaregran 2019");
        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        JPanel sidePanel = new JPanel(new GridLayout(20, 1));
        JPanel bottomPanel = new JPanel();

        frame.setLayout(new BorderLayout());
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(sidePanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

        // Initial placement of game pieces
        board[3][3] = new Square(whiteIcon, 3, 3);
        board[3][4] = new Square(blackIcon, 3, 4);
        board[4][3] = new Square(blackIcon, 4, 3);
        board[4][4] = new Square(whiteIcon, 4, 4);

        // Set the rest of all squares to blank and add all squares to the board panel
        for (int row = 0; row < BOARD_SIZE; row++)
        {
            for (int column = 0; column < BOARD_SIZE; column++)
            {
                if (board[row][column] == null)
                {
                    board[row][column] = new Square(blankIcon, row, column);
                }

                board[row][column].addActionListener(this);
                boardPanel.add(board[row][column]);
            }
        }

        JLabel hostnameLabel = new JLabel("Hostname:");
        JLabel portLabel = new JLabel("Port:");
        JLabel invisibleLabel1 = new JLabel("");
        JLabel invisibleLabel2 = new JLabel("");
        JLabel invisibleLabel3 = new JLabel("");

        playerLabel.setFont(new Font(playerLabel.getFont().getName(), Font.PLAIN, 14));
        blackScoreLabel.setFont(new Font(blackScoreLabel.getFont().getName(), Font.BOLD, 14));
        whiteScoreLabel.setFont(new Font(whiteScoreLabel.getFont().getName(), Font.BOLD, 14));

        hostnameTextField.setColumns(10);
        connectButton.addActionListener(this);

        sidePanel.add(hostnameLabel);
        sidePanel.add(hostnameTextField);
        sidePanel.add(portLabel);
        sidePanel.add(portTextField);
        sidePanel.add(invisibleLabel1);
        sidePanel.add(connectButton);
        sidePanel.add(invisibleLabel2);
        sidePanel.add(playerLabel);
        sidePanel.add(invisibleLabel3);
        sidePanel.add(blackScoreLabel);
        sidePanel.add(whiteScoreLabel);

        bottomPanel.add(statusLabel);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == connectButton && currentState == DISCONNECTED)
        {
            connect();
        }
        else if (e.getSource() == connectButton && currentState != DISCONNECTED)
        {
            disconnect();
        }
        else if (e.getSource() instanceof Square)
        {
            Square square = (Square)e.getSource();

            if (currentState == YOUR_TURN)
            {
                socketWriter.println("MOVE " + square.getRow() + "," + square.getColumn());
            }
        }
    }

    private void connect()
    {
        try
        {
            socket = new Socket(hostnameTextField.getText(), Integer.parseInt(portTextField.getText()));
            socketWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            play = new Play(this, socketReader);

            currentState = CONNECTED;
            connectButton.setText("Disconnect");
            statusLabel.setText("Welcome to Othello!");
        }
        catch (ConnectException e)
        {
            statusLabel.setText(e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    void disconnect()
    {
        socketWriter.println("QUIT");
        play.interrupt();

        try
        {
            socket.close();
            connectButton.setText("Connect");
            playerLabel.setText("");
            blackScoreLabel.setText("");
            whiteScoreLabel.setText("");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        currentState = DISCONNECTED;
    }

    public static void main(String[] args)
    {
        new OthelloClient();
    }
}
