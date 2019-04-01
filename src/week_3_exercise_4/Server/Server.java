package week_3_exercise_4.Server;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

public class Server
{
    private Server(int listenPort)
    {
        System.out.println("Listening on port " + listenPort);

        while (true)
        {
            try(ServerSocket serverSocket = new ServerSocket(listenPort);
                Socket socket = serverSocket.accept();
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
            {
                String receivedMessage;
                System.out.println(socket.getInetAddress() + " is connected!");

                Protocol protocol = new Protocol();
                outputStream.writeObject(protocol.serve(null));

                while ((receivedMessage = reader.readLine()) != null)
                {
                    outputStream.writeObject(protocol.serve(receivedMessage));
                }
            }
            catch(SocketException e)
            {
                System.out.println(e.getMessage());
            }
            catch(IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args)
    {
        new Server(55555);
    }
}
