package w3_e8_tcp_chat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener extends Thread
{
    private final ChatServer chatServer;
    private final int listeningPort;

    ConnectionListener(ChatServer chatServer, int listeningPort)
    {
        this.chatServer = chatServer;
        this.listeningPort = listeningPort;
        start();
    }

    @Override
    public void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(listeningPort))
        {
            System.out.println("Listening on port " + listeningPort);
            while (!Thread.interrupted())
            {
                Socket clientSocket = serverSocket.accept();
                new ChatUser(chatServer, clientSocket);
                System.out.println(clientSocket.getInetAddress() + " has connected!");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
