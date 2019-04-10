package w2_e7_threading_queue_priority;

public class  QueueElement
{
    private final String text;
    private final int priority;

    public QueueElement(String text, int priority)
    {
        this.text = text;
        this.priority = priority;
    }

    public String getText()
    {
        return text;
    }

    public int getPriority()
    {
        return priority;
    }
}
