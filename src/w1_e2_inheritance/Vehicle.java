package w1_e2_inheritance;

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
