package week_1_exercise_2;

public class Main
{
    private Main()
    {
        Car car = new Car(50, 1200, 4, 5, 4);
        Boat boat = new Boat(25, 50000, 30000);
        Train train = new Train(160, 80000, 8, 7);
        Bicycle bicycle = new Bicycle(30, 12, 2, 27, 24);

        printVehicle(car);
        printVehicle(boat);
        printVehicle(train);
        printVehicle(bicycle);

        printVehicleByInterface(car);
        printVehicleByInterface(boat);
        printVehicleByInterface(train);
        printVehicleByInterface(bicycle);

        IWheeled car2 = new Car(110, 900, 4, 5, 5);
        IWheeled train2 = new Train(250, 60000, 8, 4);
        IWheeled bicycle2 = new Bicycle(35, 10, 2, 24, 23);

        printNrOfWheels(car2);
        printNrOfWheels(train2);
        printNrOfWheels(bicycle2);
    }

    private void printVehicle(Vehicle vehicle)
    {
        vehicle.printMe();
    }

    private void printVehicleByInterface(IPrintable printable)
    {
        printable.printMe();
    }

    private void printNrOfWheels(IWheeled wheeled)
    {
        System.out.println("Has " + wheeled.getNrOfWheels() + " wheels.");
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
