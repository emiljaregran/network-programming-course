package week_3_exercise_1.Server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.net.SocketException;
import java.io.InputStreamReader;

public class Server
{
    private Server(int listenPort)
    {
        DAO DAO = new DAO();
        System.out.println("Listening on port " + listenPort);

        try(ServerSocket serverSocket = new ServerSocket(listenPort);
            Socket socket = serverSocket.accept();
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            String receivedMessage;
            System.out.println(socket.getInetAddress() + " is connected!");
            writer.println("Welcome to the friend DAO server.\nEnter a name to search.");

            while (true)
            {
                while ((receivedMessage = reader.readLine()) != null)
                {
                    System.out.println("Received message: " + receivedMessage);
                    String friend = DAO.getFriend(receivedMessage.trim());
                    if (friend != null)
                    {
                        writer.println(friend);
                        System.out.println("Sent: " + friend);
                    }
                    else
                    {
                        writer.println("Sorry, no friend with that name was found in the DAO.");
                        System.out.println("Sent: Sorry, no friend with that name was found in the DAO.");
                    }
                }
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
