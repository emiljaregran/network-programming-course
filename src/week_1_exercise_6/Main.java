package week_1_exercise_6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    private List<Double> temperatures = new LinkedList<>();

    private Main()
    {
        readTemperatureFile("src\\week_1_exercise_6\\Temperatures.txt");

        double maxTemperature = Collections.max(temperatures);
        double minTemperature = Collections.min(temperatures);
        double avgTemperature = calculateAverageTemperature();

        System.out.printf("Highest temperature: %.1f\n", maxTemperature);
        System.out.printf("Lowest temperature: %.1f\n", minTemperature);
        System.out.printf("Average temperature: %.1f\n", avgTemperature);
    }

    private double calculateAverageTemperature()
    {
        double sumOfTemperatures = 0;

        for (Double temperature : temperatures)
        {
            sumOfTemperatures += temperature;
        }

        return sumOfTemperatures / temperatures.size();
    }

    private void readTemperatureFile(String filename)
    {
        String row;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            while((row = reader.readLine()) != null)
            {
                temperatures.add(Double.parseDouble(row));
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found.");
            System.exit(0);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
