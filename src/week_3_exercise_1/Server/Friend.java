package week_3_exercise_1.Server;

public class Friend
{
    private final String name;
    private final String address;
    private final String phoneNumber;

    public Friend(String name, String address, String phoneNumber)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }
}
