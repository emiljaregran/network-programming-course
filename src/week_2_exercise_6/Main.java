package week_2_exercise_6;

import javax.swing.JOptionPane;

public class Main
{
    private Main()
    {
        while(true)
        {
            int medicationInterval;

            String medicationType = JOptionPane.showInputDialog(null, "Enter medication:");
            if (medicationType == null || medicationType.length() == 0)
            {
                System.exit(0);
            }

            String interval = JOptionPane.showInputDialog(null, "How many times per minute?");
            if (interval == null || interval.length() == 0)
            {
                System.exit(0);
            }

            try
            {
                medicationInterval = Integer.parseInt(interval);
            }
            catch (NumberFormatException e)
            {
                continue;
            }

            new Medicine(medicationType, medicationInterval);
        }
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
