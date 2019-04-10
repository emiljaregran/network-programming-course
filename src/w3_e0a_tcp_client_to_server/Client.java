package w3_e0a_tcp_client_to_server;

import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

public class Client
{
    private Client(String address, int port)
    {
        System.out.println("Connecting to " + address + " on port " + port + "...");

        try(Socket socket = new Socket(address, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true))
        {
            while(true)
            {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter message to send: ");
                String message = scanner.nextLine();
                writer.println(message);
                System.out.println("Sent message: " + message);
            }
        }
        catch(SocketException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args)
    {
        new Client("127.0.0.1", 55555);
    }
}
