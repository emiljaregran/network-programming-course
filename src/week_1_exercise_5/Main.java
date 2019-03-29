package week_1_exercise_5;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
    private final int[] billCoinValues = {1000, 500, 200, 100, 50, 20, 10, 5, 2, 1};

    private Main()
    {
        int costOfItems = readValue("Enter cost of the items: ");
        int amountPaid = readValue("Enter how much is paid: ");

        if (amountPaid >= costOfItems)
        {
            int changeToGetBack = amountPaid - costOfItems;
            int[] billsAndCoinsToGetBack = calculateBillsAndCoins(changeToGetBack);

            System.out.println("\nThis is what you will get back:");
            printBillsAndCoins(billsAndCoinsToGetBack);
        }
        else
        {
            System.out.println("You didn't pay enough money.");
        }
    }

    private void printBillsAndCoins(int[] billsAndCoins)
    {
        for (int i = 0; i < billCoinValues.length; i++)
        {
            System.out.println(billCoinValues[i] + ": " + billsAndCoins[i]);
        }
    }

    private int[] calculateBillsAndCoins(int change)
    {
        int[] nrOfBillsAndCoins = new int[billCoinValues.length];

        for (int i = 0; i < billCoinValues.length; i++)
        {
            nrOfBillsAndCoins[i] = change / billCoinValues[i];
            change %= billCoinValues[i];
        }

        return nrOfBillsAndCoins;
    }

    private int readValue(String textPrompt)
    {
        Integer value = null;

        while (value == null)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print(textPrompt);

            try
            {
                value = scanner.nextInt();
            }
            catch (InputMismatchException e)
            {
                System.out.println("Wrong format entered, try again.");
            }
        }

        return value;
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
