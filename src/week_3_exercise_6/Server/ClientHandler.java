package week_3_exercise_6.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread
{
    private final Socket socket;

    public ClientHandler(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            String receivedMessage;
            Protocol protocol = new Protocol();
            outputStream.writeObject(protocol.serve(null));

            while ((receivedMessage = reader.readLine()) != null && !Thread.interrupted())
            {
                outputStream.writeObject(protocol.serve(receivedMessage));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}