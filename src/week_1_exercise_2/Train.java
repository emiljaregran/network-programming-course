package week_1_exercise_2;

public class Train extends Vehicle implements IPrintable, IWheeled
{
    private final int nrOfWheels;
    private int nrOfWagons;

    public Train(int speed, int weight, int nrOfWheels, int nrOfWagons)
    {
        super(speed, weight);
        this.nrOfWheels = nrOfWheels;
        this.nrOfWagons = nrOfWagons;
    }

    public void connectWagon()
    {
        nrOfWagons++;
        System.out.println("Wagon connected, there are now " + nrOfWagons + " connected.");
    }

    public int getNrOfWheels()
    {
        return nrOfWheels;
    }

    public void printMe()
    {
        System.out.println("Train - Speed: " + speed + " Weight: " + weight + " Nr of wagons: " + nrOfWagons);
    }
}
