package week_1_exercise_2;

public abstract class Vehicle
{
    protected int speed;
    protected final int weight;

    public Vehicle(int speed, int weight)
    {
        this.speed = speed;
        this.weight = weight;
    }

    public abstract void printMe();

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
}
