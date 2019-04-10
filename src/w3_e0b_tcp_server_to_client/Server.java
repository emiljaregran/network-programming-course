package w3_e0b_tcp_server_to_client;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class Server
{
    private Server(int port)
    {
        int counter = 0;
        String message = "This is message number ";

        while (true)
        {
            System.out.println("Listening for connection on port " + port);

            try(ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true))
            {
                System.out.println(socket.getInetAddress() + " is now cennected!");

                while(socket.isConnected())
                {
                    writer.println(message + counter);
                    System.out.println("Sending: " + message + counter);
                    counter++;
                    Thread.sleep(1000);
                }
            }
            catch(InterruptedException e)
            {
                break;
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
