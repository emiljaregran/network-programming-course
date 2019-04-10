package w3_e8_tcp_chat.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class ChatClient implements ActionListener
{
    private final int DISCONNECTED = 0;
    private final int CONNECTED = 1;
    private int currentState = DISCONNECTED;

    private String nickname = "";
    private final JList<String> userList = new JList<>();

    private Socket socket;
    private PrintWriter socketWriter;
    private ChatListener chatListener;

    private final JButton connectButton;
    private final JTextField hostnameTextField;
    private final JTextField portTextField;

    private final JTextPane textPane = new JTextPane();

    private final ChatFonts fonts = new ChatFonts();
    private final ChatColors colors = new ChatColors();
    private final ChatWindow chatWindow = new ChatWindow(fonts, colors, textPane);

    private final JLabel nicknameLabel = new JLabel();
    private final JTextField inputMessageTextField = new JTextField();

    private ChatClient()
    {
        final String defaultHostname = "127.0.0.1";
        final String defaultPort = "12345";

        JFrame frame = new JFrame("Chat - Emil Jaregran 2019");
        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JScrollPane messagesScrollPane = new JScrollPane(textPane);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        topPanel.setBackground(colors.background);

        // Label "Hostname:"
        JLabel hostnameLabel = new JLabel("Hostname:");
        hostnameLabel.setForeground(colors.label);
        hostnameLabel.setFont(fonts.label);

        // Textfield for Hostname
        hostnameTextField = new JTextField(defaultHostname);
        hostnameTextField.setFont(fonts.textField);
        hostnameTextField.setBackground(colors.textFieldBackground);
        hostnameTextField.setForeground(colors.textFieldText);

        // Label "Port:"
        JLabel portLabel = new JLabel("Port:");
        portLabel.setForeground(colors.label);
        portLabel.setFont(fonts.label);

        // Textfield for Port number
        portTextField = new JTextField(defaultPort);
        portTextField.setFont(fonts.textField);
        portTextField.setBackground(colors.textFieldBackground);
        portTextField.setForeground(colors.textFieldText);
        portTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (portTextField.getText().length() >= 5 ) // Limit textfield to 5 characters
                    e.consume(); }});

        // Button for "Connect"
        connectButton = new JButton("Connect");
        connectButton.setBackground(colors.buttonBackground);
        connectButton.setForeground(colors.buttonText);

        hostnameTextField.setColumns(15);
        portTextField.setColumns(6);

        // List of current chat users
        userList.setEnabled(false);
        userList.setFixedCellWidth(130);
        userList.setFont(fonts.list);
        userList.setBackground(colors.background);
        userList.setForeground(colors.listText);

        nicknameLabel.setFont(fonts.label);
        nicknameLabel.setForeground(colors.nicknameLabelText);

        inputMessageTextField.setFont(fonts.inputMessageTextField);
        inputMessageTextField.setBackground(colors.background);
        inputMessageTextField.setForeground(colors.inputMessageTextField);
        inputMessageTextField.setEnabled(false);

        topPanel.add(hostnameLabel);
        topPanel.add(hostnameTextField);
        topPanel.add(portLabel);
        topPanel.add(portTextField);
        topPanel.add(connectButton);

        centerPanel.add(messagesScrollPane, BorderLayout.CENTER);
        centerPanel.add(userList, BorderLayout.EAST);

        bottomPanel.add(nicknameLabel, BorderLayout.WEST);
        bottomPanel.add(inputMessageTextField, BorderLayout.CENTER);

        connectButton.addActionListener(this);
        inputMessageTextField.addActionListener(this);

        frame.setVisible(true);
        frame.setLocation(300, 450);
        frame.setSize(950, 600);

        // Close down the connection to chat server gracefully
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (currentState == CONNECTED)
                {
                    disconnectFromServer();
                }
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    private void connectToServer(String destinationAddress, int destinationPort, String nickname)
    {
        System.out.println("Connecting to " + destinationAddress + " on port " + destinationPort);

        try
        {
            socket = new Socket(destinationAddress, destinationPort);
            socketWriter = new PrintWriter(socket.getOutputStream(), true);
            chatListener = new ChatListener(this, chatWindow, userList, socket);

            socketWriter.println("NICK:" + nickname);
            setConnectedState();
        }
        catch (ConnectException e)
        {
            String[] errorMessage = e.getMessage().split(":");
            chatWindow.append(chatWindow.getTimestamp(), Color.WHITE, true);
            chatWindow.append(errorMessage[0] + ".\n", Color.RED, true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setConnectedState()
    {
        currentState = CONNECTED;
        connectButton.setText("Disconnect");
        connectButton.setForeground(Color.RED);
        inputMessageTextField.setEnabled(true);
        inputMessageTextField.requestFocus();
        nicknameLabel.setText(" " + nickname + ": ");
    }

    void setDisconnectedState()
    {
        currentState = DISCONNECTED;
        connectButton.setText("Connect");
        connectButton.setForeground(Color.GREEN);
        chatWindow.append(chatWindow.getTimestamp(), Color.WHITE, true);
        chatWindow.append("Disconnected.\n", Color.RED, true);
        nicknameLabel.setText("");
        inputMessageTextField.setEnabled(false);
        userList.setListData(new String[] {});
        chatListener.interrupt();
    }

    private void disconnectFromServer()
    {
        socketWriter.println("LEAVE:" + nickname);
        chatListener.interrupt();
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        setDisconnectedState();
    }

    String getNickname()
    {
        return nickname;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == connectButton && currentState == DISCONNECTED)
        {
            nickname = JOptionPane.showInputDialog("Enter nickname:");
            int destinationPort = Integer.parseInt(portTextField.getText());
            if (nickname.length() > 0)
            {
                connectToServer(hostnameTextField.getText(), destinationPort, nickname);
            }
        }
        else if (e.getSource() == connectButton && currentState == CONNECTED)
        {
            disconnectFromServer();
        }
        else if (e.getSource() == inputMessageTextField && inputMessageTextField.getText().length() > 0)
        {
            socketWriter.println("MSG:" + inputMessageTextField.getText());
            inputMessageTextField.setText("");
        }
    }

    public static void main(String[] args)
    {
        new ChatClient();
    }
}
