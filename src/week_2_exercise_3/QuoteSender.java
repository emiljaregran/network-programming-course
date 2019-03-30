package week_2_exercise_3;

import java.net.*;
import java.io.IOException;

public class QuoteSender
{
    private final String[] quotes = {"I am not lazy, I am on energy saving mode.",
                                    "If two wrongs don't make a right, try three.",
                                    "Doing nothing is hard, you never know when you're done.",
                                    "A balanced diet means a cupcake in each hand.",
                                    "An apple a day keeps anyone away if you throw it hard enough."};

    private QuoteSender(String destinationIp, int port, int interval)
    {
        DatagramSocket socket = createSocket();
        sendQuotes(socket, destinationIp, port, interval);
    }

    private void sendQuotes(DatagramSocket socket, String destinationIp, int port, int interval)
    {
        while (true)
        {
            String quote = getRandomQuote();
            byte[] quoteArray = quote.getBytes();

            InetAddress inetAddress = getInetAddress(destinationIp);
            DatagramPacket datagramPacket = new DatagramPacket(quoteArray, 0, quoteArray.length, inetAddress, port);

            try
            {
                socket.send(datagramPacket);
                System.out.println("Quote sent: " + quote);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            sleep(interval);
        }
    }

    private void sleep(int time)
    {
        try
        {
            Thread.sleep(time * 1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private InetAddress getInetAddress(String ip)
    {
        InetAddress inetAddress = null;

        try
        {
            inetAddress = InetAddress.getByName(ip);
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }

        return inetAddress;
    }

    private String getRandomQuote()
    {
        int randomNumber = (int)(Math.random() * quotes.length);

        return quotes[randomNumber];
    }

    private DatagramSocket createSocket()
    {
        DatagramSocket socket = null;

        try
        {
            socket = new DatagramSocket();
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
        new QuoteSender("127.0.0.1", 55555, 5);
    }
}
