package week_3_exercise_5.Client;

import java.io.*;
import java.net.*;

public class RiddleClient {
    public static void main(String[] args)
    {
        final int portNumber = 55555;
        final String hostName = "127.0.0.1";

        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String messageFromServer;
            String inputFromUser;

            while ((messageFromServer = socketReader.readLine()) != null)
            {
                System.out.println("Server: " + messageFromServer);
                if (messageFromServer.equals("Bye."))
                {
                    break;
                }

                inputFromUser = stdIn.readLine();
                if (inputFromUser != null)
                {
                    System.out.println("Client: " + inputFromUser);
                    socketWriter.println(inputFromUser);
                }
            }
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
