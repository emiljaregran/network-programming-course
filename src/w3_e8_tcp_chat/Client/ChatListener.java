package w3_e8_tcp_chat.Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ChatListener extends Thread
{
    private final Socket socket;
    private final ChatProtocol chatProtocol;

    ChatListener(ChatClient chatClient, ChatWindow chatWindow, JList<String> userList, Socket socket)
    {
        this.socket = socket;
        this.chatProtocol = new ChatProtocol(chatClient, chatWindow, userList);
        start();
    }

    @Override
    public void run()
    {
        try (BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            String receivedMessage;
            while ((receivedMessage = socketReader.readLine()) != null && !Thread.interrupted())
            {
                chatProtocol.processMessage(receivedMessage);
            }

            socket.close();
        }
        catch (SocketException e)
        {
            System.err.println(e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
