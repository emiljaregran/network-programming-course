package w3_e0a_tcp_client_to_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class Server
{
    private Server(int listenPort)
    {
        while(true)
        {
            String receivedMessage;
            System.out.println("Listening for connections on port " + listenPort);

            try(ServerSocket serverSocket = new ServerSocket(listenPort);
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
            {
                System.out.println(socket.getInetAddress() + " is connected!");
                while ((receivedMessage = reader.readLine()) != null)
                {
                    System.out.println("Received message: " + receivedMessage);
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
    }

    public static void main(String[] args)
    {
        new Server(55555);
    }
}
