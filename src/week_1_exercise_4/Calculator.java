package week_1_exercise_4;

public class Calculator
{
    public static double calculate(double firstNumber, double secondNumber, String operator) throws OperatorNotSupportedException
    {
        double result;

        switch (operator)
        {
            case "+":
                result = firstNumber + secondNumber;
                break;

            case "-":
                result = firstNumber - secondNumber;
                break;

            case "*":
                result = firstNumber * secondNumber;
                break;

            case "/":
                result = firstNumber / secondNumber;
                break;

            default:
                throw new OperatorNotSupportedException("Operator is not supported.");
        }

        return result;
    }
}
