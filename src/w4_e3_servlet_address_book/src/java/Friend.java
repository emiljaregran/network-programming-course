public class Friend
{
    private final String name;
    private final String address;
    private final String phoneNumber;
    private static final long serialVersionUID = 1L;

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
