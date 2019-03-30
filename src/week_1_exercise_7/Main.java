package week_1_exercise_7;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    List<Person> personList = new LinkedList<>();
    List<Person> longPersonList = new LinkedList<>();

    private Main()
    {
        readPersonDataFile("src\\week_1_exercise_7\\Person_data.txt");
        addLongPersonsToList(200);
        saveLongPersonDataFile("src\\week_1_exercise_7\\Long_persons.txt");

        System.out.println(longPersonList.size() + " persons written to file.");
    }

    private void saveLongPersonDataFile(String filename)
    {
        Path filePath = Paths.get(filename);
        try(PrintWriter writer = new PrintWriter(Files.newBufferedWriter(filePath)))
        {
            for (Person person : longPersonList)
            {
                writer.println(person.getPersonData());
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File could not be found.");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void addLongPersonsToList(int lengthLimit)
    {
        for (Person person : personList)
        {
            if (person.getLength() > lengthLimit)
            {
                longPersonList.add(person);
            }
        }
    }

    private void readPersonDataFile(String filename)
    {
        String firstRow;
        String secondRow;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            while((firstRow = reader.readLine()) != null)
            {
                secondRow = reader.readLine();
                savePersonInPersonList(firstRow, secondRow);
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("The file could not be found.");
            System.exit(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void savePersonInPersonList(String firstRow, String secondRow)
    {
        String[] firstRowSplitted = firstRow.split("[,]");
        String[] secondRowSplitted = secondRow.split("[,]");

        String name = firstRowSplitted[0].trim();
        String address = firstRowSplitted[1].trim();
        String town = firstRowSplitted[2].trim();
        int age = Integer.parseInt(secondRowSplitted[0].trim());
        int weight = Integer.parseInt(secondRowSplitted[1].trim());
        int length = Integer.parseInt(secondRowSplitted[2].trim());

        personList.add(new Person(name, address, town, age, weight, length));
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
