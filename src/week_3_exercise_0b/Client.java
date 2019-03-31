package week_3_exercise_0b;

import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;

public class Client
{
    private Client(String destinationIp, int destinationPort)
    {
        String receivedMessage;
        System.out.println("Connecting to " + destinationIp + " on port " + destinationPort);

        try(Socket socket = new Socket(destinationIp, destinationPort);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            while((receivedMessage = reader.readLine()) != null)
            {
                System.out.println(receivedMessage);
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
