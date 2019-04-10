package w2_e7_threading_queue_priority;

public class Consumer implements Runnable
{
    private final int interval;
    private final SimpleQueue queue;
    public Thread activity = new Thread(this);

    public Consumer (int seconds, SimpleQueue queue)
    {
        this.interval = seconds * 1000;
        this.queue = queue;
    }

    @Override
    public void run()
    {
        while(!Thread.interrupted())
        {
            try
            {
                Thread.sleep(interval);
                QueueElement element = queue.take();
                System.out.println("Taking " + element.getText() + " [" + element.getPriority() + "]");
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }
}
