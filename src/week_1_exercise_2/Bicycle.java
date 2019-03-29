package week_1_exercise_2;

public class Bicycle extends Vehicle implements IPrintable, IWheeled
{
    private final int nrOfWheels;
    private final int nrOfGears;
    private int currentGear;

    public Bicycle(int speed, int weight, int nrOfWheels, int nrOfGears, int currentGear)
    {
        super(speed, weight);
        this.nrOfWheels = nrOfWheels;
        this.nrOfGears = nrOfGears;
        this.currentGear = currentGear;
    }

    public void setGear(int gear)
    {
        if (gear > 0 && gear <= nrOfGears)
        {
            currentGear = gear;
        }

        System.out.println("You are now using gear: " + gear);
    }

    public int getNrOfWheels()
    {
        return nrOfWheels;
    }

    public void printMe()
    {
        System.out.println("Bicycle - Speed: " + speed + " Weight: " + weight + " Nr of gears: " + nrOfGears + " Current gear: " + currentGear);
    }
}
