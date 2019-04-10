package w2_e7_threading_queue_priority;

public class Producer implements Runnable
{
    private final int interval;
    private final int priority;
    private final SimpleQueue queue;
    private final QueueElement queueElement;
    public Thread activity = new Thread(this);

    public Producer (String text, int seconds, int priority, SimpleQueue queue)
    {
        this.interval = seconds * 1000;
        this.priority = priority;
        this.queue = queue;
        this.queueElement = new QueueElement(text, priority);
    }

    @Override
    public void run()
    {
        activity.setPriority(priority);

        while(!Thread.interrupted())
        {
            try
            {
                Thread.sleep(interval);
                queue.put(queueElement);
                System.out.println("Putting " + queueElement.getText() + " [" + queueElement.getPriority() + "]");
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }
}
