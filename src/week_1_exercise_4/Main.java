package week_1_exercise_4;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
    private Main()
    {
        while (true)
        {
            Scanner scanner = new Scanner(System.in);
            String result;
            String operator;
            double calculation;
            double firstNumber;
            double secondNumber;

            System.out.print("Enter an mathematical expression (a +-*/ b): ");
            try
            {
                firstNumber = scanner.nextDouble();
                operator = scanner.next();
                secondNumber = scanner.nextDouble();

                calculation = Calculator.calculate(firstNumber, secondNumber, operator);
                result = "The result is: " + calculation;
            }
            catch (InputMismatchException e)
            {
                result = "Invalid input format.";
            }
            catch (OperatorNotSupportedException e)
            {
                result = e.getMessage();
            }

            System.out.println(result);
        }
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
