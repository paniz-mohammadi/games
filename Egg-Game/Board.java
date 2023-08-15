import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JPanel implements Runnable, KeyListener
{
    int width = 200, height = 50, x = 10, y = 10;
    //int x2 = 50, y2 = 100, width2 = 200, height2 = 50;
    int x3 = 50, y3 = 200, dim = 70;
    //int x4 = 50, y4 = 300, width4 = 200, height4 = 50;
    static int xa = 1, ya = 1;
    private Thread animator;
    JFrame main;

    public Board(JFrame main)
    {
        this.main = main;
    }

    public void move()
    {
        if (x3 + xa > main.getWidth() - dim)
            xa = -1;
        if (y3 + ya > main.getHeight() - dim)
            ya = -1;
        if (x3 + xa < 0)
            xa = 1;
        if (y3 + ya < 0)
            ya = 1;

        x3 += xa;
        y3 += ya;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.magenta);
        g2.drawRect(x, y, width, height);
        g2.setColor(Color.red);
        g2.fillOval(x3, y3, dim, dim);
    }

    public void addNotify()
    {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void run() 
    {
        while(true)
        {
            repaint();
            move();
            try
            {
                Thread.sleep(10);   
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) 
    {
        // TODO Auto-generated method stub
        super.addKeyListener(l);
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {   
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            xa = 1;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            xa = -1;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            ya = -1;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            ya = 1;   
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        // TODO Auto-generated method stub    
    }
}