package w2_e6_threading_medicine;

public class Medicine extends Thread
{
    private final String type;
    private final int interval;

    public Medicine(String type, int interval)
    {
        this.type = type;
        this.interval = interval;
        start();
    }

    @Override
    public void run()
    {
        while(!Thread.interrupted())
        {
            System.out.println("Take medicine: " + type);
            try
            {
                Thread.sleep(60000 / interval);
            }
            catch(InterruptedException e)
            {
                break;
            }
        }
    }
}
