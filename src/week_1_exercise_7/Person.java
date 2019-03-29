package week_1_exercise_7;

public class Person
{
    private String name;
    private String address;
    private String town;
    private int age;
    private int weight;
    private int length;

    public Person(String name, String address, String town, int age, int weight, int length)
    {
        this.name = name;
        this.address = address;
        this.town = town;
        this.age = age;
        this.weight = weight;
        this.length = length;
    }

    public int getLength()
    {
        return length;
    }

    public String getPersonData()
    {
        return String.format("%s, %s, %s\n%d, %d, %d", name, address, town, age, weight, length);
    }
}
