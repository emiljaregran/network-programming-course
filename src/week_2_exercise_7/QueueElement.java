package week_2_exercise_7;

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
