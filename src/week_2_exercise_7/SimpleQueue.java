package week_2_exercise_7;

import java.util.*;

public class SimpleQueue
{
    private final List<QueueElement> list = new ArrayList<>();

    public int size()
    {
        return list.size();
    }

    public synchronized void put(QueueElement element)
    {
        System.out.println("Putting " + element.getText());
        list.add(element);
        notify();
    }

    public synchronized QueueElement take()
    {
        while (list.isEmpty())
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                return null;
            }
        }

        QueueElement element = list.get(0);
        list.remove(0);
        return element;
    }

    public void printQueue()
    {
        list.forEach(element -> System.out.println(element.getText()));
    }
}
