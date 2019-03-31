package week_3_exercise_3.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.net.ConnectException;
import week_3_exercise_3.Server.Intro;
import week_3_exercise_3.Server.Response;

public class Client
{
    private Client(String destinationIp, int destinationPort)
    {
        Object receivedObject;
        boolean waitingForResponse = true;
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

                if((receivedObject = inputStream.readObject()) != null) {
                    if (receivedObject instanceof Intro)
                    {
                        System.out.println("Successfully connected to the server.");
                    }
                    else if (receivedObject instanceof Response)
                    {
                        Response response = (Response)receivedObject;
                        if (response.getFound())
                        {
                            System.out.printf("%s\n%s\n%s\n", response.getFriend().getName(), response.getFriend().getAddress(), response.getFriend().getPhoneNumber());
                        }
                        else
                        {
                            System.out.println("No friend with that name was found in the DAO.");
                        }
                    }

                    waitingForResponse = false;
                }
            }
        }
        catch(ConnectException e)
        {
            System.out.println(e.getMessage());
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
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
