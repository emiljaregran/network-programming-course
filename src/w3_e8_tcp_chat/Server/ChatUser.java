package w3_e8_tcp_chat.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ChatUser extends Thread
{
    private String nickname;
    private boolean keepAlive = true;
    private final Socket socket;
    private final ChatServer chatServer;
    private PrintWriter socketWriter;
    private BufferedReader socketReader;

    ChatUser(ChatServer chatServer, Socket socket)
    {
        this.socket = socket;
        this.chatServer = chatServer;

        try
        {
            socketWriter = new PrintWriter(socket.getOutputStream(), true);
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        start();
    }

    void sendMessageToClient(String message)
    {
        socketWriter.println(message);
        System.out.println("Sent to client: " + message);
    }

    String getNickname()
    {
        return nickname;
    }

    PrintWriter getSocketWriter()
    {
        return socketWriter;
    }

    private void nicknameInit(String[] message) throws IOException
    {

        if (message[0].equals("NICK") && message[1].length() > 0)
        {
            if (chatServer.nicknameAlreadyExists(message[1]))
            {
                socketWriter.println("ERR:Sorry, nickname already taken.");
                socket.close();
                keepAlive = false;
            }
            else
            {
                this.nickname = message[1];
                chatServer.addChatUser(this);
            }
        }
        else
        {
            socketWriter.println("ERR:No nickname supplied.");
            socket.close();
            keepAlive = false;
        }
    }

    @Override
    public void run()
    {
        String receivedMessage;

        try
        {
            while ((receivedMessage = socketReader.readLine()) != null && keepAlive)
            {
                if (receivedMessage.length() > 0)
                {
                    System.out.println("Received message: " + receivedMessage);
                    String[] message = receivedMessage.split(":", 2);

                    if (nickname == null)
                    {
                        nicknameInit(message);
                    }
                    else if (message[0].equals("LEAVE"))
                    {
                        chatServer.removeChatUser(this);
                    }
                    else if (message[0].equals("MSG") && message[1].length() > 0)
                    {
                        chatServer.propagateMessage(this.getNickname() + ": " + message[1]);
                    }
                }
            }
        }
        catch (SocketException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
