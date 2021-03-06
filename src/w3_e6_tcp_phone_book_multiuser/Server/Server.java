package w3_e6_tcp_phone_book_multiuser.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.SocketException;

public class Server
{
    private Server(int listenPort)
    {
        System.out.println("Listening on port " + listenPort);

        try(ServerSocket serverSocket = new ServerSocket(listenPort))
        {
            while (true)
            {
                new ClientHandler(serverSocket.accept());
            }
        }
        catch(SocketException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public static void main(String[] args)
    {
        new Server(55555);
    }
}
