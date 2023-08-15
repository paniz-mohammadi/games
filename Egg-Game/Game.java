import javax.swing.*;
import java.awt.*;

public class Game extends JFrame
{
    public Game()
    {
        add(new Board(this));
        Board bord = new Board(this);
        addKeyListener(bord);
        setTitle("Game");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
