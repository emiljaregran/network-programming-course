package w1_e4_calculator;

public class OperatorNotSupportedException extends Exception
{
    public OperatorNotSupportedException(){}

    public OperatorNotSupportedException(String message)
    {
        super(message);
    }
}
