package week_2_exercise_8;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Chat implements ActionListener
{
    private final String nickname;
    private final int broadcastPort;

    private MulticastSocket socket;
    private InetAddress broadcastAddress;
    private ChatListener chatListener;

    private final JButton disconnectButton = new JButton("Disconnect");
    private final JTextArea textArea = new JTextArea();
    private final JTextField textField = new JTextField();

    private Chat(String nickname, String address, int port) throws IOException
    {
        this.nickname = nickname;
        this.broadcastPort = port;

        broadcastAddress = InetAddress.getByName(address);
        socket = new MulticastSocket(broadcastPort);
        socket.joinGroup(broadcastAddress);
        socket.setBroadcast(true);

        JFrame frame = new JFrame("Chat - " + nickname);
        JPanel panel = new JPanel(new BorderLayout());

        frame.add(panel);
        frame.setLocation(400, 200);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textField.setFont(new Font("Courier New", Font.PLAIN, 16));
        textArea.setFont(new Font("Courier New", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(textArea);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        textField.addActionListener(this);
        disconnectButton.addActionListener(this);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(disconnectButton, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.SOUTH);

        frame.setVisible(true);
        sendMessage("CONNECTED");
        chatListener = new ChatListener(socket, textArea, 256);
        chatListener.start();
    }

    private void sendMessage(String message)
    {
        String messageToSend = nickname + ": " + message;
        byte[] byteArray = messageToSend.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length, broadcastAddress, broadcastPort);

        try
        {
            socket.send(datagramPacket);
        }
        catch (IOException e)
        {
            textArea.append("\nERROR: Could not send message.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == disconnectButton)
        {
            sendMessage("DISCONNECTED");
            chatListener.interrupt();
            socket.close();
        }

        else if (event.getSource() == textField)
        {
            sendMessage(textField.getText());
            textField.setText("");
        }
    }

    public static void main(String[] args)
    {
        String nickname = JOptionPane.showInputDialog(null, "Enter your name:");
        if (nickname == null || nickname.length() == 0)
        {
            nickname = "Default";
        }

        try
        {
            new Chat(nickname, "234.235.236.237", 12540);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
