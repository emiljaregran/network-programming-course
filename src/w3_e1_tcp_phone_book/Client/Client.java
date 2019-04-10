package w3_e1_tcp_phone_book.Client;

import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;

public class Client
{
    private Client(String destinationIp, int destinationPort)
    {
        String receivedMessage;
        boolean waitingForResponse = true;
        System.out.println("Connecting to " + destinationIp + " on port " + destinationPort);

        try(Socket socket = new Socket(destinationIp, destinationPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            while (true)
            {
                if (!waitingForResponse)
                {
                    Scanner scanner = new Scanner(System.in);
                    writer.println(scanner.nextLine());
                    waitingForResponse = true;
                }

                while(reader.ready() && (receivedMessage = reader.readLine()) != null) {
                    System.out.println(receivedMessage);
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
