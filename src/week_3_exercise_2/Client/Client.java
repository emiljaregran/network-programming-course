package week_3_exercise_2.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.net.ConnectException;
import week_3_exercise_2.Server.Friend;

public class Client
{
    private Client(String destinationIp, int destinationPort)
    {
        Friend receivedFriend;
        boolean waitingForResponse = false;
        System.out.println("Connecting to " + destinationIp + " on port " + destinationPort);

        try(Socket socket = new Socket(destinationIp, destinationPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()))
        {
            while (true)
            {
                if (!waitingForResponse)
                {
                    Scanner scanner = new Scanner(System.in);
                    writer.println(scanner.nextLine());
                    waitingForResponse = true;
                }

                if((receivedFriend = (Friend)inputStream.readObject()) != null) {
                    System.out.printf("%s\n%s\n%s\n", receivedFriend.getName(), receivedFriend.getAddress(), receivedFriend.getPhoneNumber());
                    waitingForResponse = false;
                }
            }
        }
        catch(ConnectException e)
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
        new Client("127.0.0.1", 55555);
    }
}
