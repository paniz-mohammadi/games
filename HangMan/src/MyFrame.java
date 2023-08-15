import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MyFrame
{
    JFrame frm = new JFrame();

    public MyFrame()
    {
        startScreen(frm);
    }

    private static void startScreen(JFrame frm)
    {
        frm.setSize(new Dimension(800, 700));
        frm.setTitle("HangMan");
        frm.setLocationRelativeTo(null);
        frm.setResizable(false);
        frm.setDefaultCloseOperation(frm.EXIT_ON_CLOSE);

        ImageIcon backgroundImg = new ImageIcon("img\\start_screen_bg.png");

        JLabel lblBackground = new JLabel(backgroundImg);
        JButton btnPlay = new JButton();
        btnPlay.setText("Play");
        btnPlay.setBackground(new Color(50, 200, 100));
        btnPlay.setBounds(530, 550, 120, 40);
        btnPlay.setFocusable(false);

        JButton btnExit = new JButton();
        btnExit.setText("Quit");
        btnExit.setBackground(new Color(255, 120, 120));
        btnExit.setBounds(360, 550, 120, 40);
        btnExit.setFocusable(false);

        JButton btnFeedback = new JButton();
        btnFeedback.setText("Feedback");
        btnFeedback.setBackground(new Color(80, 90, 200));
        btnFeedback.setBounds(200, 550, 120, 40);
        btnFeedback.setFocusable(false);

        lblBackground.add(btnPlay);
        lblBackground.add(btnExit);
        lblBackground.add(btnFeedback);
        frm.getContentPane().add(lblBackground);
        frm.setVisible(true);
        actionButtons(frm, btnPlay, btnExit, btnFeedback);
    }

    private static void actionButtons(JFrame frm, JButton btnPlay, JButton btnExit, JButton btnFeedback)
    {
        btnPlay.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frm.dispose();
                chooseOpponent();
            }
        });

        btnExit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int option = JOptionPane.showConfirmDialog(frm, "Are you sure that you want to exit?");
                if(option == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });

        btnFeedback.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frm.dispose();
                JFrame frm = new JFrame("Feedback");
                frm.setBounds(400, 250, 320, 350);

                JTextArea txtArea = new JTextArea();
                txtArea.setSize(200, 200);
                frm.getContentPane().add(txtArea);

                JButton btnSend = new JButton("Send");
                btnSend.setBounds(210, 250, 80, 30);
                btnSend.setBackground(new Color(230, 100, 100, 100));
                btnSend.setFocusable(false);
                frm.getContentPane().add(btnSend);

                frm.setResizable(false);
                frm.setLayout(null);
                frm.setVisible(true);

                btnSend.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        JOptionPane.showMessageDialog(frm, "Your massage has been sent successfully");
                        frm.dispose();
                        startScreen(new JFrame());
                    }
                });
            }
        });
    }

    private static void chooseOpponent()
    {
        JFrame frame = new JFrame();
        frame.setBounds(300, 300, 380, 300);
        ImageIcon backgroundImg = new ImageIcon("img\\chalkboard.png");

        JLabel lblBackground = new JLabel(backgroundImg);

        JButton btn1 = new JButton("Two players");
        btn1.setBounds(30, 100, 150, 30);
        btn1.setBackground(new Color(200, 240, 70));
        btn1.setFocusable(false);

        JButton btn2 = new JButton("Play with computer");
        btn2.setBounds(190, 100, 150, 30);
        btn2.setBackground(new Color(100, 230, 9));
        btn2.setFocusable(false);

        lblBackground.add(btn1);
        lblBackground.add(btn2);
        frame.getContentPane().add(lblBackground);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        btn1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                twoPlayerScreen();
            }
        });

        btn2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                MyFrame.onePlayerScreen();
            }
        });
    }

    private static void onePlayerScreen()
    {
        JFrame frm = new JFrame();
        frm.setBounds(400, 300, 450, 340);
        ImageIcon backgroundImg = new ImageIcon("img\\chalkboard.png");

        JLabel lblBackground = new JLabel(backgroundImg);
        JLabel lblDifficulty = new JLabel("Level");
        lblDifficulty.setBounds(20, 40, 100, 20);
        lblDifficulty.setBackground(new Color(180, 120, 100));

        JRadioButton easy = new JRadioButton("Easy");
        JRadioButton normal = new JRadioButton("Normal");
        JRadioButton difficult = new JRadioButton("Difficult");

        easy.setBounds(100, 40, 80, 20);
        easy.setOpaque(false);
        easy.setFocusable(false);
        normal.setBounds(200, 40, 90, 20);
        normal.setOpaque(false);
        normal.setFocusable(false);
        difficult.setBounds(300, 40, 100, 20);
        difficult.setOpaque(false);
        difficult.setFocusable(false);

        ButtonGroup levels = new ButtonGroup();
        levels.add(easy);
        levels.add(normal);
        levels.add(difficult);

        lblBackground.add(easy);
        lblBackground.add(normal);
        lblBackground.add(difficult);

        JLabel lblCategory = new JLabel("Category");
        lblCategory.setBounds(20, 130, 100, 20);
        lblCategory.setBackground(new Color(180, 120, 100));

        String[] wordCategory = {"Book", "Movie", "Song", "Food", "Fruit", "Animal", "Car", "Gadget", "Country", "City", "Random"};
        JComboBox box = new JComboBox(wordCategory);
        box.setBounds(100, 120, 100, 30);
        box.setBackground(new Color(150, 100, 30));
        lblBackground.add(box);

        JButton btnConfirm = new JButton();
        btnConfirm.setText("Ok");
        btnConfirm.setBounds(320, 250, 70, 40);
        btnConfirm.setBackground(new Color(200, 40, 50));
        btnConfirm.setFocusable(false);
        lblBackground.add(btnConfirm);

        JButton btnBack = new JButton();
        btnBack.setText("Back");
        btnBack.setBounds(100, 250, 70, 40);
        btnBack.setBackground(new Color(200, 40, 50));
        btnBack.setFocusable(false);
        lblBackground.add(btnBack);

        frm.getContentPane().add(lblDifficulty);
        frm.getContentPane().add(lblCategory);
        frm.getContentPane().add(lblBackground);
        frm.setLocationRelativeTo(null);
        frm.setResizable(false);
        frm.setDefaultCloseOperation(frm.EXIT_ON_CLOSE);
        frm.setVisible(true);

        HangMan.whichOption(box);

        btnConfirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String level = "";

                if(easy.isSelected())
                    level = "easy";

                else if(normal.isSelected())
                    level = "normal";

                else if(difficult.isSelected())
                    level = "difficult";

                String word = readWordsFromfile(box, level);

                if(easy.isSelected() || normal.isSelected() || difficult.isSelected())
                {
                    frm.dispose();
                    HangMan hangMan = new HangMan(word, level);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please choose the level first",
                            "Alert",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frm.dispose();
                startScreen(new JFrame());
            }
        });
    }

    private static String readWordsFromfile(JComboBox box, String level)
    {
        Scanner fileInput = null;
        List<String> words = new ArrayList<>();

        if(box.getSelectedItem().equals("Book"))
        {
            try {
                fileInput = new Scanner(new File("Books.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("Movie"))
        {
            try {
                fileInput = new Scanner(new File("Movies.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("Song"))
        {
            try {
                fileInput = new Scanner(new File("Songs.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("Food"))
        {
            try {
                fileInput = new Scanner(new File("Food.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("Fruit"))
        {
            try {
                fileInput = new Scanner(new File("Fruit.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("Animal"))
        {
            try {
                fileInput = new Scanner(new File("Animals.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("Car"))
        {
            try {
                fileInput = new Scanner(new File("Cars.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("Gadget"))
        {
            try {
                fileInput = new Scanner(new File("Gadgets.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("Country"))
        {
            try {
                fileInput = new Scanner(new File("Countries.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("City"))
        {
            try {
                fileInput = new Scanner(new File("Cities.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        if(box.getSelectedItem().equals("Random"))
        {
            try {
                fileInput = new Scanner(new File("RandomWords.txt"));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        while(fileInput.hasNext())
        {
            words.add(fileInput.nextLine());
        }

        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size()));

        System.out.println(word);
        return word;
    }

    private static void twoPlayerScreen()
    {
        JFrame frm = new JFrame();
        frm.setBounds(450, 200, 400, 300);
        ImageIcon imgIcon = new ImageIcon("img\\chalkboard.png");
        JLabel lblBackground = new JLabel(imgIcon);

        JLabel lblDifficulty = new JLabel("Level");
        lblDifficulty.setBounds(30, 30, 100, 20);
        lblDifficulty.setBackground(new Color(180, 120, 100));
        lblBackground.add(lblDifficulty);

        JRadioButton easy = new JRadioButton("Easy");
        JRadioButton normal = new JRadioButton("Normal");
        JRadioButton difficult = new JRadioButton("Difficult");

        easy.setBounds(100, 30, 80, 20);
        easy.setOpaque(false);
        easy.setFocusable(false);
        normal.setBounds(200, 30, 90, 20);
        normal.setOpaque(false);
        normal.setFocusable(false);
        difficult.setBounds(300, 30, 100, 20);
        difficult.setOpaque(false);
        difficult.setFocusable(false);

        ButtonGroup levels = new ButtonGroup();
        levels.add(easy);
        levels.add(normal);
        levels.add(difficult);

        lblBackground.add(easy);
        lblBackground.add(normal);
        lblBackground.add(difficult);

        JLabel lbl = new JLabel();
        lbl.setBounds(30, 100, 150, 20);
        lbl.setText("Enter The Word");
        lblBackground.add(lbl);

        JTextField txtField = new JTextField();
        txtField.setBounds(130, 100, 120, 30);
        lblBackground.add(txtField);

        JButton btnConfirm = new JButton();
        btnConfirm.setText("Ok");
        btnConfirm.setBounds(200, 200, 70, 40);
        btnConfirm.setBackground(new Color(200, 40, 50));
        btnConfirm.setFocusable(false);
        lblBackground.add(btnConfirm);

        JButton btnBack = new JButton();
        btnBack.setText("Back");
        btnBack.setBounds(100, 200, 70, 40);
        btnBack.setBackground(new Color(200, 40, 50));
        btnBack.setFocusable(false);
        lblBackground.add(btnBack);

        frm.getContentPane().add(lblBackground);
        frm.setVisible(true);
        frm.setResizable(false);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnConfirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String level = "";

                if(easy.isSelected())
                    level = "easy";

                else if(normal.isSelected())
                    level = "normal";

                else if(difficult.isSelected())
                    level = "difficult";

                if(easy.isSelected() || normal.isSelected() || difficult.isSelected())
                {
                    String word = txtField.getText();
                    frm.dispose();
                    if(txtField.getText().length() != 0)
                    {
                        HangMan hangMan = new HangMan(word, level);
                    }
                }
                else if(level.equals(null) && txtField.getText().length() != 0)
                {
                    JOptionPane.showMessageDialog(null, "Please choose the level first",
                            "Alert",JOptionPane.WARNING_MESSAGE);
                }
                else if(!level.equals(null) && txtField.getText().length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Please choose the word first",
                            "Alert",JOptionPane.WARNING_MESSAGE);
                }
                else if(level.equals(null) && txtField.getText().length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Please choose the level and the word first",
                            "Alert",JOptionPane.WARNING_MESSAGE);
                }
                //System.out.println(word);
            }
        });

        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frm.dispose();
                MyFrame.startScreen(new JFrame());
            }
        });
    }

    public static void main(String[] args)
    {
        MyFrame frame = new MyFrame();

    }
}
