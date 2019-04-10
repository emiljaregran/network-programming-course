package w2_e7_threading_queue_priority;

import java.util.Scanner;

public class MainClass
{
    public static void main(String[] args) throws InterruptedException
    {
        SimpleQueue buffer = new SimpleQueue();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Number of producers: ");
        Producer[] producers = new Producer[scanner.nextInt()];

        for (int i = 0; i < producers.length; i++)
        {
            System.out.println("Producer no "+ (i + 1) + ":");
            System.out.println(" Interval:");
            int time = scanner.nextInt();

            System.out.println("Priority:");
            int priority = scanner.nextInt();

            System.out.println("Text? ");
            String text = scanner.next();
            producers[i] = new Producer(text, time, priority, buffer);
        }

        System.out.println("Number of consumers: ");
        Consumer[] consumers = new Consumer[scanner.nextInt()];

        for (int i = 0; i < consumers.length; i++)
        {
            System.out.println(" Interval:");
            int time = scanner.nextInt();
            consumers[i] = new Consumer(time, buffer);
        }

        for (Producer producer : producers)
        {
            producer.activity.start();
        }

        for (Consumer consumer : consumers)
        {
            consumer.activity.start();
        }

        Thread.sleep(15000);
        System.out.println("Left in queue: " + buffer.size());
        buffer.printQueue();
        System.exit(0);
    }
}
