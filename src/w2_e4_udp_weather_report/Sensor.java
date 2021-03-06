package w2_e4_udp_weather_report;

import java.net.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;

public class Sensor
{
    private DatagramSocket socket;
    private InetAddress inetAddress;

    private Sensor(String destinationIp, int destinationPort)
    {
        createSocket();
        setInetAddress(destinationIp);
        String town = setTown();

        while (true)
        {
            String temperature = getTemperature();
            sendTemperature(town, temperature, destinationPort);
        }
    }

    private void sendTemperature(String town, String temperature, int destinationPort)
    {
        String message = town + ": " + temperature;
        byte[] messageArray = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(messageArray, messageArray.length, inetAddress, destinationPort);

        try
        {
            socket.send(datagramPacket);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void createSocket()
    {
        try
        {
            socket = new DatagramSocket();
        }
        catch(SocketException e)
        {
            System.out.println("Failed to create socket, exiting...");
            System.exit(-1);
        }
    }

    private String setTown()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("What town are you in? ");
        return scanner.nextLine();
    }

    private void setInetAddress(String destinationIp)
    {
        try
        {
            inetAddress = InetAddress.getByName(destinationIp);
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
    }

    private String getTemperature()
    {
        Scanner scanner = new Scanner(System.in);
        Double temperature = null;

        while (temperature == null){
            System.out.print("Enter current temperature: ");
            try
            {
                temperature = scanner.nextDouble();
            }
            catch (InputMismatchException e)
            {
                scanner.next();
                System.out.println("Wrong format entered, please try again.");
            }
        }

        return temperature.toString();
    }

    public static void main(String[] args)
    {
        new Sensor("127.0.0.1", 55555);
    }
}
