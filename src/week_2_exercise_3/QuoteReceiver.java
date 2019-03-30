package week_2_exercise_3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class QuoteReceiver
{
    private QuoteReceiver(int port, int bufferSize)
    {
        DatagramSocket socket = createSocket(port);

        while (true)
        {
            byte[] byteArray = new byte[bufferSize];
            DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);

            try
            {
                socket.receive(datagramPacket);
                String receivedMessage = new String(byteArray, 0, byteArray.length);
                System.out.println("Received from " + datagramPacket.getAddress() + ": " + receivedMessage);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private DatagramSocket createSocket(int port)
    {
        DatagramSocket socket = null;

        try
        {
            socket = new DatagramSocket(port);
            System.out.println("Socket created.");
        }
        catch(SocketException e)
        {
            System.out.println("Failed to create socket, exiting...");
            System.exit(-1);
        }

        return socket;
    }

    public static void main(String[] args)
    {
        new QuoteReceiver(55555, 256);
    }
}
