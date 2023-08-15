import javax.swing.*;
import java.awt.*;

public class Chronometer implements Runnable
{
    Thread t1;
    JLabel label;
    boolean flag;
    int lastUpdate;

    public Chronometer(JLabel label)
    {
        this.label = label;
        t1 = new Thread(this);
        t1.start();
        flag = true;
    }

    @Override
    public void run()
    {
        int i = 0;
        int minute = 0;

        while (flag)
        {
            try
            {
                if (i == 60)
                {
                    i = 0;
                    minute++;
                    if (minute == 60)
                        minute = 0;
                }

                label.setText(Integer.toString(minute) + " : " + Integer.toString(i));
                label.setBackground(Color.WHITE);
                i++;
                t1.sleep(1000); // 1000ms = 1
            }
            catch (InterruptedException e) {
                //Logger.getLogger(ThreadsUsing.class.getName().log(Level.SEVERE, null, e));
                e.printStackTrace();
            }
        }
    }

    public void resumeTime(JLabel lbl)
    {
        //System.currentTimeMillis();
    }
}