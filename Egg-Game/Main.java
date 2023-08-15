import javax.swing.JFrame;

public class Main extends JFrame
{
    public Main()
    {
        Board board = new Board(this);
        add(new Board(board));
        setTitle("Game");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Shape");
        setResizable(false);
        setVisible(true);
    }   

    public static void main(String[] args) 
    {
        new Main();    
    }
}
