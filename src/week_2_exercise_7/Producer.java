package week_2_exercise_7;

public class Producer implements Runnable
{
    private final int interval;
    private final SimpleQueue queue;
    private final QueueElement queueElement;
    public Thread activity = new Thread(this);

    public Producer (String text, int seconds, SimpleQueue queue)
    {
        this.interval = seconds * 1000;
        this.queue = queue;
        this.queueElement = new QueueElement(text);
    }

    @Override
    public void run()
    {
        while(!Thread.interrupted())
        {
            try
            {
                Thread.sleep(interval);
                queue.put(queueElement);
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }
}
