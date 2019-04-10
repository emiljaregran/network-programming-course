package w1_e3_scanner;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main
{
    private Main()
    {
        double currentMileage = readInput("Enter current mileage reading (km): ");
        double previousMileage = readInput("Enter the mileage reading one year ago (km): ");
        double consumedFuel = readInput("Enter number of litres fuel consumed during the last year: ");

        double kilometersDriven = currentMileage - previousMileage;
        double averageConsumption = (consumedFuel / kilometersDriven) * 10;

        System.out.printf("Kilometers driven: %.0f\n", kilometersDriven);
        System.out.printf("Litres of fuel consumed: %.1f\n", consumedFuel);
        System.out.printf("Litres of fuel consumed per 10 km: %.2f\n", averageConsumption);
    }

    private double readInput(String textPrompt)
    {
        Double enteredValue = null;

        while (enteredValue == null)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print(textPrompt);
            try
            {
                enteredValue = scanner.nextDouble();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid format, please try again.");
            }
        }

        return enteredValue;
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
