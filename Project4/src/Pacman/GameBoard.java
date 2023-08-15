package Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JPanel implements ActionListener
{
    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private Image img;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;
    private boolean active = false;
    private boolean dead = false;
    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PAC_ANIM_DELAY = 2;
    private final int PACMAN_ANIM_COUNT = 4;
    private final int MAX_GHOSTS = 12;
    private final int PACMAN_SPEED = 6;
    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int N_Enemies = 6;
    private int lives, score;
    private int[] dx, dy;
    private int[] enemy_x, enemy_y, enemy_dx, enemy_dy, enemySpeed;
    private Image heart, enemy, pacmanToUp, pacmanToLeft, pacmanToRight, pacmanToDown;
    private int pacman_x, pacman_y, pacman_dx, pacman_dy;
    private int req_dx, req_dy, view_dx, view_dy;
    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;
    private int currentSpeed = 3;
    private short[] screenData;
    private Timer timer;

    private final short levelData[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
            1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
            1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
            1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
    };

    public GameBoard()
    {
        loadImages();
        initVariables();
        active = true;
        initGame();
        initBoard();
    }

    private void initBoard()
    {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
    }

    private void initVariables()
    {
        screenData = new short[N_BLOCKS * N_BLOCKS];
        mazeColor = new Color(5, 100, 5);
        d = new Dimension(600, 600);
        setBounds(120, 250, 650, 650);

        enemy_x = new int[MAX_GHOSTS];
        enemy_dx = new int[MAX_GHOSTS];
        enemy_y = new int[MAX_GHOSTS];
        enemy_dy = new int[MAX_GHOSTS];
        enemySpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify()
    {
        super.addNotify();
        initGame();
    }

    private void doAnim()
    {
        pacAnimCount--;

        if (pacAnimCount <= 0)
        {
            pacAnimCount = PAC_ANIM_DELAY;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0)
                pacAnimDir = -pacAnimDir;
        }
    }

    private void playGame(Graphics2D g2d)
    {
        if (dead)
        {
            death();
        }
        else
        {
            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    private void drawScore(Graphics2D g)
    {
        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (i = 0; i < lives; i++)
            g.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 1, this);
    }

    private void checkMaze()
    {
        short i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished)
        {
            if ((screenData[i] & 48) != 0)
                finished = false;
            i++;
        }

        if (finished)
        {
            score += 50;

            if (N_Enemies < MAX_GHOSTS)
                N_Enemies++;

            if (currentSpeed < maxSpeed)
                currentSpeed++;

            initLevel();
        }
    }

    private void death()
    {
        lives--;

        if (lives == 0)
        {
            active = false;
            System.out.println(score);
            int a = JOptionPane.showConfirmDialog(this,"Game Over! Do you want to start over?");
            if (a == JOptionPane.YES_OPTION)
            {
                initVariables();
                active = true;
                initGame();
                initBoard();
            }
            else
            {
                MyFrame.saveRecord(score);
            }
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d)
    {
        short i;
        int pos;
        int count;

        for (i = 0; i < N_Enemies; i++)
        {
            if (enemy_x[i] % BLOCK_SIZE == 0 && enemy_y[i] % BLOCK_SIZE == 0)
            {
                pos = enemy_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (enemy_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && enemy_dx[i] != 1)
                {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && enemy_dy[i] != 1)
                {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && enemy_dx[i] != -1)
                {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && enemy_dy[i] != -1)
                {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0)
                {
                    if ((screenData[pos] & 15) == 15)
                    {
                        enemy_dx[i] = 0;
                        enemy_dy[i] = 0;
                    }
                    else
                    {
                        enemy_dx[i] = -enemy_dx[i];
                        enemy_dy[i] = -enemy_dy[i];
                    }
                }
                else
                {
                    count = (int) (Math.random() * count);

                    if (count > 3)
                        count = 3;

                    enemy_dx[i] = dx[count];
                    enemy_dy[i] = dy[count];
                }
            }

            enemy_x[i] = enemy_x[i] + (enemy_dx[i] * enemySpeed[i]);
            enemy_y[i] = enemy_y[i] + (enemy_dy[i] * enemySpeed[i]);
            drawGhost(g2d, enemy_x[i] + 1, enemy_y[i] + 1);

            if (pacman_x > (enemy_x[i] - 12) && pacman_x < (enemy_x[i] + 12)
                && pacman_y > (enemy_y[i] - 12) && pacman_y < (enemy_y[i] + 12) && active)
            {
                dead = true;
            }
        }
    }

    private void drawGhost(Graphics2D g2d, int x, int y) { g2d.drawImage(enemy, x, y, this);}
    private void movePacman()
    {
        int pos;
        short ch;

        if (req_dx == -pacman_dx && req_dy == -pacman_dy)
        {
            pacman_dx = req_dx;
            pacman_dy = req_dy;
            view_dx = pacman_dx;
            view_dy = pacman_dy;
        }

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0)
        {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0)
            {
                screenData[pos] = (short) (ch & 15);
                score++;
            }

            if (req_dx != 0 || req_dy != 0)
            {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacman_dx = req_dx;
                    pacman_dy = req_dy;
                    view_dx = pacman_dx;
                    view_dy = pacman_dy;
                }
            }

            if ((pacman_dx == -1 && (pacman_dy == 0) && (ch & 1) != 0)
                    || (pacman_dx == 1 && pacman_dy == 0 && (ch & 4) != 0)
                    || (pacman_dx == 0 && pacman_dy == -1 && (ch & 2) != 0)
                    || (pacman_dx == 0 && pacman_dy == 1 && (ch & 8) != 0))
            {
                pacman_dx = 0;
                pacman_dy = 0;
            }
        }
        pacman_x = pacman_x + PACMAN_SPEED * pacman_dx;
        pacman_y = pacman_y + PACMAN_SPEED * pacman_dy;
    }

    private void drawPacman(Graphics2D g2d)
    {
        if (view_dx == -1)
        {
            g2d.drawImage(pacmanToLeft, pacman_x + 1, pacman_y + 1, this);

        }
        else if (view_dx == 1)
        {
            g2d.drawImage(pacmanToRight, pacman_x + 1, pacman_y + 1, this);
        }
        else if (view_dy == -1)
        {
            g2d.drawImage(pacmanToUp, pacman_x + 1, pacman_y + 1, this);
        }
        else
        {
            g2d.drawImage(pacmanToDown, pacman_x + 1, pacman_y + 1, this);
        }
    }
    private void drawMaze(Graphics2D g2d)
    {
        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE)
        {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE)
            {
                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0)
                {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0)
                {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0)
                {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0)
                {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0)
                {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    private void initGame()
    {
        lives = MyFrame.getLivesCount();
        if(lives != 3 || lives != 4 || lives != 5)
            lives = 3;
        score = 0;
        initLevel();
        N_Enemies = 6;
        currentSpeed = 3;
    }

    private void initLevel()
    {
        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++)
        {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }

    private void continueLevel()
    {
        short i;
        int dx = 1;
        int random;

        for (i = 0; i < N_Enemies; i++)
        {
            enemy_y[i] = 4 * BLOCK_SIZE;
            enemy_x[i] = 4 * BLOCK_SIZE;
            enemy_dy[i] = 0;
            enemy_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed)
            {
                random = currentSpeed;
            }

            enemySpeed[i] = validSpeeds[random];
        }

        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 11 * BLOCK_SIZE;
        pacman_dx = 0;
        pacman_dy = 0;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
        dead = false;
    }

    private void loadImages()
    {
        enemy = new ImageIcon("src\\Pacman\\images\\enemy.png").getImage();
        heart = new ImageIcon("src\\Pacman\\images\\heart.png").getImage();
        pacmanToUp = new ImageIcon("src\\Pacman\\images\\up.png").getImage();
        pacmanToDown = new ImageIcon("src\\Pacman\\images\\down.png").getImage();
        pacmanToLeft = new ImageIcon("src\\Pacman\\images\\left.png").getImage();
        pacmanToRight = new ImageIcon("src\\Pacman\\images\\right.png").getImage();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);
        doAnim();

        if (active)
        {
            playGame(g2d);
        }

        g2d.drawImage(img, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (active)
            {
                if (key == KeyEvent.VK_LEFT)
                {
                    req_dx = -1;
                    req_dy = 0;
                }
                else if (key == KeyEvent.VK_RIGHT)
                {
                    req_dx = 1;
                    req_dy = 0;
                }
                else if (key == KeyEvent.VK_UP)
                {
                    req_dx = 0;
                    req_dy = -1;
                }
                else if (key == KeyEvent.VK_DOWN)
                {
                    req_dx = 0;
                    req_dy = 1;
                }
                else if (key == KeyEvent.VK_ESCAPE && timer.isRunning())
                {
                    active = false;
                }
                else if (key == KeyEvent.VK_PAUSE)
                {
                    if (timer.isRunning())
                    {
                        timer.stop();
                    }
                    else
                    {
                        timer.start();
                    }
                }
            }
            else
            {
                active = true;
                initGame();
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT || key == Event.UP || key == Event.DOWN)
            {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {repaint();}
}
