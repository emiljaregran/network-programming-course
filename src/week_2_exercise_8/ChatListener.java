package week_2_exercise_8;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class ChatListener extends Thread
{
    private final MulticastSocket socket;
    private final JTextArea textArea;
    private final int RECEIVE_BUFFER_SIZE;

    public ChatListener(MulticastSocket socket, JTextArea textArea, int receiveBufferSize)
    {
        this.socket = socket;
        this.textArea = textArea;
        this.RECEIVE_BUFFER_SIZE = receiveBufferSize;
    }

    @Override
    public void run()
    {
        while(!Thread.interrupted())
        {
            byte[] receivedArray = new byte[RECEIVE_BUFFER_SIZE];
            DatagramPacket datagramPacket = new DatagramPacket(receivedArray, receivedArray.length);

            try
            {
                socket.receive(datagramPacket);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            String receivedMessage = new String(receivedArray, 0, receivedArray.length);
            textArea.append("\n" + receivedMessage.trim());
        }
    }
}
