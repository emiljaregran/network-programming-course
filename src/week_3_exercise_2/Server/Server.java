package week_3_exercise_2.Server;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

public class Server
{
    private Server(int listenPort)
    {
        DAO DAO = new DAO();
        System.out.println("Listening on port " + listenPort);

        try(ServerSocket serverSocket = new ServerSocket(listenPort);
            Socket socket = serverSocket.accept();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            String receivedMessage;
            System.out.println(socket.getInetAddress() + " is connected!");

            while ((receivedMessage = reader.readLine()) != null)
            {
                System.out.println("Received message: " + receivedMessage);
                Friend friend = DAO.getFriend(receivedMessage.trim());
                if (friend != null)
                {
                    outputStream.writeObject(friend);
                    outputStream.flush();
                    System.out.println("Sent: " + friend.getName());
                }
                else
                {
                    outputStream.writeObject(new Friend("No friend with that name was found in the DAO.", "", ""));
                    outputStream.flush();
                    System.out.println("Sent: No friend with that name was found in the DAO.");
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
