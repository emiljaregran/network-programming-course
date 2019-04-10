package w2_e7_threading_queue_priority;

import java.util.*;

public class SimpleQueue
{
    private final LinkedList<QueueElement> list = new LinkedList<>();

    public int size()
    {
        return list.size();
    }

    public synchronized void put(QueueElement element)
    {
        ListIterator<QueueElement> listIterator = list.listIterator(0);

        if (list.isEmpty())
        {
            list.addFirst(element);
        }
        else
        {
            int nextIndex = 0;

            while(listIterator.hasNext())
            {
                nextIndex = listIterator.nextIndex();
                if (element.getPriority() >= listIterator.next().getPriority())
                {
                    break;
                }
            }

            list.add(nextIndex, element);
        }

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

        return list.poll();
    }

    public synchronized void printQueue()
    {
        list.forEach(element -> System.out.println(element.getText() + " [" + element.getPriority() + "]"));
    }
}
