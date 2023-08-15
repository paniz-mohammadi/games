package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
public class View extends JFrame
{
    private int[][] maze =
            {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
                    {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1},
                    {1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
    private final List<Integer> path = new ArrayList<Integer>();
    private int pathIndex;
    public View()
    {
        setTitle("Simple Maze Solver");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        DFS.searchPath(maze, 1, 1, path);
        pathIndex = path.size() - 2;
        System.out.println(path);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        g.translate(50, 50);

        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[0].length; j++)
            {
                Color color;

                switch (maze[i][j])
                {
                    case 1:
                    {
                        color = Color.BLACK;
                        break;
                    }

                    case 9:
                    {
                        color = Color.RED;
                        break;
                    }
                    default: color = Color.WHITE;
                }
                g.setColor(color);
                g.fillRect(30 * j, 30 * i, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(30 * j, 30 * i, 30, 30);
            }
        }

        for (int i = 0; i < path.size(); i += 2)
        {
            int pathX= path.get(i);
            int pathY = path.get(i + 1);
            g.setColor(Color.GREEN);
            g.fillRect(pathX * 30, pathY * 30, 30, 30);
        }

        //The ball
        int pathX = path.get(pathIndex);
        int pathY = path.get(pathIndex + 1);
        g.setColor(Color.RED);
        g.fillOval(pathX * 30, pathY * 30, 30,30);

    }

    @Override
    protected void processKeyEvent(KeyEvent ke)
    {
        if(ke.getID() != KeyEvent.KEY_PRESSED)
            return;
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            pathIndex -= 2;
            if (pathIndex < 0)
                pathIndex = 0;
        }
        else if (ke.getKeyCode() == KeyEvent.VK_LEFT)
        {
            pathIndex += 2;
            if (pathIndex > path.size() - 2)
                pathIndex = path.size() - 2;
        }
        repaint();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                View view = new View();
                view.setVisible(true);
            }
        });
    }
}
