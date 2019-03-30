package week_2_exercise_5;

import java.io.IOException;
import java.net.*;

public class Receiver
{
    private MulticastSocket socket;
    private InetAddress inetAddress;

    private Receiver(String multicastAddress, int port, int bufferSize)
    {
        setInetAddress(multicastAddress);
        createSocket(port);
        joinGroup(inetAddress);

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

    private void joinGroup(InetAddress inetAddress)
    {
        try
        {
            socket.joinGroup(inetAddress);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setInetAddress(String multicastAddress)
    {
        try
        {
            inetAddress = InetAddress.getByName(multicastAddress);
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
    }

    private void createSocket(int port)
    {
        try
        {
            socket = new MulticastSocket(port);
            System.out.println("Socket created.");
        }
        catch(SocketException e)
        {
            System.out.println("Failed to create socket, exiting...");
            System.exit(-1);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Receiver("234.235.236.237",55555, 256);
    }
}
