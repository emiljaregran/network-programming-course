package w1_e2_inheritance;

public class Car extends Vehicle implements IPrintable, IWheeled
{
    private final int nrOfWheels;
    private final int nrOfGears;
    private int currentGear;

    public Car(int speed, int weight, int nrOfWheels, int nrOfGears, int currentGear)
    {
        super(speed, weight);
        this.nrOfWheels = nrOfWheels;
        this.nrOfGears = nrOfGears;
        this.currentGear = currentGear;
    }

    public void setGear(int gear)
    {
        currentGear = gear;
    }

    public int getNrOfWheels()
    {
        return nrOfWheels;
    }

    public void printMe()
    {
        System.out.println("Car - Speed: " + speed + " Weight: " + weight + " Nr of gears: " + nrOfGears + " Current gear: " + currentGear);
    }
}
