package w1_e2_inheritance;

public class Boat extends Vehicle implements IPrintable
{
    private final int deadweight;

    public Boat(int speed, int weight, int deadweight)
    {
        super(speed, weight);
        this.deadweight = deadweight;
    }

    public void turn(String direction)
    {
        System.out.println("The boat is turning " + direction);
    }

    public void printMe()
    {
        System.out.println("Boat - Speed: " + speed + " Weight: " + weight + " Deadweight: " + deadweight);
    }
}
