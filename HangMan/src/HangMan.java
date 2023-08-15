import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class HangMan
{
    private int MAX_COUNT_OF_GUESSES;
    private Integer lives;
    private String word;
    private String level;
    private char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't','u', 'v', 'w', 'x', 'y', 'z'};
    private ArrayList<Character> trueGuessedLetters;

    private JButton[] buttons;
    private JButton btnReset, btnStop, btnRestart;
    private JFrame frame;
    private JPanel pnlUp;
    private JPanel pnlBottom;
    private Image backgroundImg;
    private Image[] steps;

    public HangMan(String word, String level)
    {
        this.word = word;
        this.level = level;
        //countOfGuesses = 0;

        if(level.equals("easy"))
        {
            MAX_COUNT_OF_GUESSES = 10;
            lives = 10;
            if(level.equals("easy"))
            {
                steps = new Image[11];
                for (int i = 0 ; i < MAX_COUNT_OF_GUESSES + 1 ; i++)
                {
                    Image img = new ImageIcon("img\\" + i + ".jpg").getImage();
                    steps[i] = img;
                }
            }
        }

        else if(level.equals("normal"))
        {
            MAX_COUNT_OF_GUESSES = 6;
            lives = 6;
            steps = new Image[7];
            for (int i = 0 ; i < MAX_COUNT_OF_GUESSES + 1 ; i++)
            {
                Image img = new ImageIcon("img\\Gallows" + i + ".png").getImage();
                steps[i] = img;
            }
        }

        else if(level.equals("difficult"))
        {
            MAX_COUNT_OF_GUESSES = 3;
            lives = 3;
            steps = new Image[4];
            for (int i = 0 ; i < MAX_COUNT_OF_GUESSES + 1; i++)
            {
                Image img = new ImageIcon("img\\Gallows" + i + ".png").getImage();
                steps[i] = img;
            }
        }

        trueGuessedLetters = new ArrayList<Character>();

        backgroundImg = new ImageIcon("img\\chalkboard.png").getImage();

        buttons = new JButton[26];
        frame = new JFrame();
        frame.setBounds(350, 100, 700, 500);

        pnlBottom = new JPanel();

        for (int i = 0 ; i < letters.length ; i++)
        {
            buttons[i] = new JButton(String.valueOf(letters[i]));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(120, 220, 100));
            pnlBottom.add(buttons[i]);
        }

        btnReset = new JButton("Reset");
        btnReset.setBackground(new Color(220, 80, 8));
        btnReset.setFocusable(false);
        pnlBottom.add(btnReset);

        pnlBottom.setLayout(new GridLayout(3,2));

        frame.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblTimer = new JLabel();
        lblTimer.setBounds(500, 20, 400, 100);
        //lblTimer.setText("Time");
        lblTimer.setBorder(BorderFactory.createTitledBorder("Time"));
        //Font font = new Font("serif", Font.PLAIN, 800);
        lblTimer.setFont(new Font("serif", Font.BOLD, 80));
        lblTimer.setBackground(Color.WHITE);
        lblTimer.setForeground(Color.WHITE);
        lblTimer.setOpaque(false);
        frame.getContentPane().add(lblTimer);

        Chronometer chronometer = new Chronometer(lblTimer);

        btnStop = new JButton("Stop");
        btnStop.setBounds(10, 80, 60, 20);
        btnStop.setBackground(new Color(220, 80, 8));
        btnStop.setFocusable(false);
        frame.getContentPane().add(btnStop);

        btnRestart = new JButton("Restart");
        btnRestart.setBounds(80, 80, 80, 20);
        btnRestart.setBackground(new Color(220, 80, 8));
        btnRestart.setFocusable(false);
        frame.getContentPane().add(btnRestart);

        btnStop.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                chronometer.flag = false;
            }
        });

        btnRestart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //chronometer.flag = true;
                Chronometer chronometer = new Chronometer(lblTimer);
            }
        });

        UsingGraphic graphic = new UsingGraphic(word, level);
        frame.getContentPane().add(graphic);

        actionButtons(buttons, btnReset, word, level, graphic);
    }

    private void actionButtons(JButton[] buttons, JButton btnReset, String word, String level, UsingGraphic graphic)
    {
        for(int i = 0 ; i < buttons.length ; i++)
        {
            int finalI = i;
            buttons[i].addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    //char btnValue = buttons[finalI].toString().charAt(0);
                    graphic.repaintScreen(buttons[finalI],trueGuessedLetters);
                    graphic.checkForVictory();
                }
            });
        }

        btnReset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure that you want to play a new game?");
                if(option == JOptionPane.YES_OPTION)
                {
                    frame.dispose();
                    HangMan hangMan = new HangMan(word, level);
                }
                else
                    System.exit(0);
            }
        });
    }

    class UsingGraphic extends JComponent
    {
        private String word;
        private String level;

        public UsingGraphic(String word, String level)
        {
            this.word = word;
            this.level = level;
        }

        @Override
        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g.create();
            super.paintComponent(g2);

            //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //Toolkit t = Toolkit.getDefaultToolkit();

            int space = 10;

            int blanksLength;
            if (word.length() <= 10)
                blanksLength = ((700- 40) / 10) - space;

            else
                blanksLength = ((700 - 20) / word.length()) - space;

            drawHangMan(level, lives, g2);
            int x1 =  (700 / 2) - ((blanksLength * word.length() + space * (word.length() + 1)) / 2);
            int y1 = (500 / 2) + 90;
            int x2 = x1 + blanksLength;
            int y2 = y1;

            drawBlanks(g2, word, blanksLength);
            drawGuessedLetters(g2, blanksLength);

            //Image pause = t.getImage("img\\Pngtree game pause button switch stereo.png");
            //g2.drawImage(pause,160,10,120,60,null);

        }

        private void drawHangMan(String level, Integer countOfGuesses, Graphics2D g2)
        {
            /*
            frame --> Width = 700, Height = 500
            gallows.png --> Width = 300, Height = 300
            easy-level.jpg Width = 200, Height = 350
            */

            g2.drawImage(backgroundImg, 2, 2, null);
            g2.drawImage(steps[(MAX_COUNT_OF_GUESSES - lives)], 200, 40, null);
        }

        private boolean checkLetter(char buttonValue, List<Character> trueGuessesLetters)
        {
            char[] wordToChar = word.toCharArray();

            boolean flg = false;
            for (int i = 0 ; i < wordToChar.length ; i++)
            {
                if (buttonValue == wordToChar[i])
                {
                    trueGuessesLetters.add(wordToChar[i]);
                    flg = true;
                }
            }
            if(flg)
                return true;
            else
                return false;
        }

        public void repaintScreen(JButton button, List<Character> trueGuessedLetters)
        {
            boolean exist = checkLetter(button.getText().toString().charAt(0), trueGuessedLetters);
            //System.out.println(button.getText().toString().charAt(0));
            if (!exist)
            {
                lives--;
                button.setBackground(new Color(220, 20, 10));
            }
            else
            {
                //checkForVictory();
                button.setEnabled(false);
            }
            repaint();
        }

        private void drawBlanks(Graphics2D g2, String word, int blanksLength)
        {
            int x1 = 8;
            int x2 = 68;
            char[] wordToChar = word.toCharArray();
            g2.setColor(Color.WHITE);

            for(int i = 0 ; i < word.length() ; i++)
            {
                char ch = wordToChar[i];

                if (String.valueOf(ch).matches("[a-zA-Z0-9]"))
                    g2.drawLine(x1, 30, x2, 30);

                x1 = x2 + 10; // 10 = space
                x2 = x1 + blanksLength;
            }
        }

        private void drawGuessedLetters(Graphics2D g2D, int blanksLength)
        {
            for (int i = 0; i < trueGuessedLetters.size() ; i++)
            {
                char ch = trueGuessedLetters.get(i);

                int index=1;
                for(int j=0; j<word.length(); j++)
                {
                    int charX1 = 8;
                    int charX2 = 68;
                    if(word.charAt(j) == ch)
                    {
                        index = j;
                        charX1 = index * (charX2-charX1+10)  + charX1 ;
                        charX2 = charX1 + blanksLength;
                        int x = charX1 + (blanksLength / 2) - (g2D.getFontMetrics().stringWidth(String.valueOf(ch).toUpperCase()) / 2);

                        if (String.valueOf(ch).matches("[a-zA-Z0-9]"))
                        {
                            g2D.drawString(String.valueOf(ch).toUpperCase(), x, 20);
                        }
                    }
                }
            }
        }

        private void drawMissingLetters(Graphics2D g2D, int x1, int y1, int x2, int space, int blanksLength)
        {
            g2D.setPaint(new Color(240, 180, 0));

            int charX1 = 8;
            int charX2 = 68;

            for (int i=0; i<word.length(); i++) {
                char ch = word.charAt(i);
                int x = charX1 + (blanksLength / 2) - (g2D.getFontMetrics().stringWidth(String.valueOf(ch).toUpperCase()) / 2);
                int y = y1 - 10;

                if (String.valueOf(ch).matches("[a-zA-Z0-9]") && trueGuessedLetters.get(i) != word.charAt(i)) {
                    g2D.drawString(String.valueOf(ch).toUpperCase(), x, y);
                }
                charX1 = charX2 + space;
                charX2 = charX1 + blanksLength;
            }
        }

        public void checkForVictory()
        {
            if(lives == 0)
            {
                for (int i = 0 ; i < buttons.length ; i++)
                {
                    buttons[i].setEnabled(false);
                }
                //failure
                JOptionPane.showMessageDialog(frame, "You Lost!\n" + "The word was: " + word);
            }
            else
            {
                //CheckForVictory
                int counter = 0;
                char[] wordToChar = word.toCharArray();

                for(int i = 0 ; i < wordToChar.length ; i++)
                {
                    for (int j = 0; j < trueGuessedLetters.size(); j++)
                    {
                        if(wordToChar[i] == trueGuessedLetters.get(j))
                        {
                            counter++;
                        }
                    }
                    System.out.println(counter);
                    System.out.println(wordToChar.length);
                }
                if(counter == wordToChar.length)
                {
                    System.out.println("Victory");
                    JOptionPane.showMessageDialog(frame, "Well Done!");
                    for (int i = 0 ; i < buttons.length ; i++)
                    {
                        buttons[i].setEnabled(false);
                    }
                }
            }
        }
    }

    public static String whichOption(JComboBox box)
    {
        String option = "";
        if(box.getSelectedItem().equals("Book"))
        {
            option = "Book";
        }

        if(box.getSelectedItem().equals("Movie"))
        {
            option = "Movie";
        }

        if(box.getSelectedItem().equals("Song"))
        {
            option = "Song";
        }

        if(box.getSelectedItem().equals("Food"))
        {
            option = "Food";
        }

        if(box.getSelectedItem().equals("Fruit"))
        {
            option = "Fruit";
        }

        if(box.getSelectedItem().equals("Animal"))
        {
            option = "Animal";
        }

        if(box.getSelectedItem().equals("Car"))
        {
            option = "Car";
        }

        if(box.getSelectedItem().equals("Gadget"))
        {
            option = "Gadget";
        }

        if(box.getSelectedItem().equals("Country"))
        {
            option = "Country";
        }

        if(box.getSelectedItem().equals("City"))
        {
            option = "City";
        }

        if(box.getSelectedItem().equals("Random"))
        {
            option = "Random";
        }

        return option;
    }
}
