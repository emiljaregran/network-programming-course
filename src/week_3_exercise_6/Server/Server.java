package week_3_exercise_6.Server;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

public class Server
{
    private Server(int listenPort) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(listenPort);
        System.out.println("Listening on port " + listenPort);

        while (true)
        {
            try
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getInetAddress() + " is connected!");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
            catch(SocketException e)
            {
                System.out.println(e.getMessage());
            }
            catch(IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        new Server(55555);
    }
}
