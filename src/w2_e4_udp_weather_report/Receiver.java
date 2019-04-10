package w2_e4_udp_weather_report;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receiver
{
    private DatagramSocket socket;

    private Receiver(int port, int bufferSize)
    {
        createSocket(port);

        while (true)
        {
            System.out.println(receiveMessage(bufferSize));
        }
    }

    private String receiveMessage(int bufferSize)
    {
        byte[] buffer = new byte[bufferSize];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

        try
        {
            socket.receive(datagramPacket);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return new String(buffer, 0, buffer.length);
    }

    private void createSocket(int port)
    {
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
    }

    public static void main(String[] args)
    {
        new Receiver(55555, 256);
    }
}
