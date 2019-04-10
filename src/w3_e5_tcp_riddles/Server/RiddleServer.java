package w3_e5_tcp_riddles.Server;

import java.io.*;
import java.net.*;

public class RiddleServer
{
    public static void main(String[] args)
    {
        final int portNumber = 55555;

        try (ServerSocket serverSocket = new ServerSocket(portNumber);
             Socket clientSocket = serverSocket.accept();
             PrintWriter socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            String clientMessage;
            String serverResponse;

            // Initiate conversation with client
            RiddleProtocol protocol = new RiddleProtocol();
            serverResponse = protocol.processInput(null);
            socketWriter.println(serverResponse);

            // Handle the connected client
            while ((clientMessage = socketReader.readLine()) != null)
            {
                serverResponse = protocol.processInput(clientMessage);
                socketWriter.println(serverResponse);
                if (serverResponse.equals("Bye."))
                {
                    break;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
