package Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class MyFrame
{
    private static ArrayList<Player> players = new ArrayList<>();
    static String inputName = "";
    static JFrame frm;
    static Font font;
    static ImageIcon playImg, recordImg, settingsImg, pauseImg, quitImg;
    static JButton playBtn, recordBtn, settingsBtn, backBtn, pauseBtn, quitBtn;
    static int livesCount, coinsCount;
    public MyFrame()
    {
        initialize();
    }

    private static void initialize()
    {
        frm = new JFrame("Pacman");
        frm.setSize(600, 750);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setLocationRelativeTo(null);
        frm.setResizable(false);
    }

    private static void mainMenu()
    {
        playImg = new ImageIcon("src\\Pacman\\images\\play.png");
        playBtn = new JButton(playImg);
        playBtn.setBounds(180, 10, 200, 220);
        playBtn.setFocusable(false);

        recordImg = new ImageIcon("src\\Pacman\\images\\record.png");
        recordBtn = new JButton(recordImg);
        recordBtn.setBounds(180, 245, 200, 220);
        recordBtn.setFocusable(false);

        settingsImg = new ImageIcon("src\\Pacman\\images\\settings.jpg");
        settingsBtn = new JButton(settingsImg);
        settingsBtn.setBounds(180, 480, 200, 220);
        settingsBtn.setFocusable(false);

        frm.setLayout(null);
        frm.getContentPane().setBackground(Color.BLACK);
        frm.add(playBtn);
        frm.add(recordBtn);
        frm.add(settingsBtn);
        frm.setVisible(true);
        actionButtons();
    }

    private static void actionButtons()
    {
        playBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String name = JOptionPane.showInputDialog(frm,"Enter Your name");
                if(name.equals(""))
                {
                    name = JOptionPane.showInputDialog(frm,"Enter Your name");
                }

                else
                {
                    inputName = name;
                    frm.dispose();

                    new MyFrame();
                    GameBoard board = new GameBoard();
                    backBtn = new JButton("Back");
                    font = backBtn.getFont();
                    backBtn.setFont(font.deriveFont(Font.BOLD));
                    backBtn.setFont(font.deriveFont(font.getStyle() | Font.ITALIC, 24));
                    backBtn.setBounds(75, 40,120, 70);
                    backBtn.setBackground(Color.CYAN);
                    backBtn.setFocusable(false);
                    pauseImg = new ImageIcon("src\\Pacman\\images\\pause.png");
                    pauseBtn = new JButton(pauseImg);
                    pauseBtn.setBounds(235, 20,120, 120);
                    pauseBtn.setFocusable(false);
                    quitImg = new ImageIcon("src\\Pacman\\images\\exit.jpg");
                    quitBtn = new JButton(quitImg);
                    quitBtn.setBounds(400, 40, 140, 70);
                    quitBtn.setFocusable(false);

                    frm.getContentPane().add(board);
                    frm.getContentPane().add(backBtn);
                    frm.getContentPane().add(quitBtn);
                    //frm.getContentPane().add(pauseBtn);
                    frm.setLayout(null);
                    frm.getContentPane().setBackground(Color.BLACK);
                    frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frm.setVisible(true);

                    backBtn.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            frm.dispose();
                            initialize();
                            mainMenu();
                        }
                    });

                    quitBtn.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            int a = JOptionPane.showConfirmDialog(frm, "Are you sure?");
                            if (a == JOptionPane.YES_OPTION) {
                                System.exit(0);
                            }
                        }
                    });
                }
            }
        });

        recordBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frm.dispose();
                frm = new JFrame("Record List");
                frm.setSize(750, 500);
                frm.getContentPane().setBackground(Color.GRAY);
                JPanel pnl = new JPanel();
                pnl.setBounds(10, 20, 700, 400);
                pnl.setBackground(Color.GRAY);

                //in case it is the first time --> before start playing
                if (players.isEmpty())
                    readRecordFile();
                int n = players.size();

                //Sort Records In Descending Order
                String[][] rows = new String[n][2];
                for (int i = 0; i < n-1; i++)
                {
                    for (int j = i+1; j < n; j++)
                    {
                        if(players.get(j).getRecord() > players.get(i).getRecord())
                        {
                            String tempName = players.get(i).getName();
                            int tempRecord = players.get(i).getRecord();
                            players.get(i).setName(players.get(j).getName());
                            players.get(i).setRecord(players.get(j).getRecord());
                            players.get(j).setName(tempName);
                            players.get(j).setRecord(tempRecord);
                        }
                    }
                }

                for (int i = 0; i < n; i++)
                {
                    for (int j = 0; j < 2; j++)
                    {
                        rows[i][j++] = players.get(i).getName();
                        rows[i][j++] = String.valueOf(players.get(i).getRecord());
                    }
                }

                String[] columns ={"Name", "Record"};
                JTable recordTable = new JTable(rows,columns);
                recordTable.setEnabled(false);
                recordTable.setCellSelectionEnabled(false);

                JScrollPane sp = new JScrollPane(recordTable);

                JButton backButton = new JButton("Back");
                font = backButton.getFont();
                backButton.setFont(font.deriveFont(Font.BOLD));
                backButton.setFont(font.deriveFont(font.getStyle() | Font.ITALIC, 18));
                backButton.setSize(80,25);
                backButton.setBackground(new Color(0x4B9D72));
                backButton.setFocusable(false);

                pnl.add(sp);
                frm.getContentPane().add(pnl);
                frm.getContentPane().add(backButton);
                frm.setLayout(null);
                frm.setLocationRelativeTo(null);
                frm.setResizable(false);
                frm.setVisible(true);
                frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frm.dispose();
                        initialize();
                        mainMenu();
                    }
                });
            }
        });

        settingsBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frm.dispose();
                frm = new JFrame("Settings");
                frm.setSize(500, 400);
                frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frm.setLocationRelativeTo(null);
                frm.setResizable(false);
                frm.setLayout(null);
                frm.getContentPane().setBackground(new Color(0xE59E9E));

                JLabel levelLbl = new JLabel("Level:");
                font = levelLbl.getFont();
                levelLbl.setFont(font.deriveFont(Font.BOLD));
                levelLbl.setFont(font.deriveFont(font.getStyle() | Font.ITALIC, 24));
                levelLbl.setBounds(30, 20, 100, 20);

                JRadioButton easy = new JRadioButton("Easy(5 lives)");
                font = easy.getFont();
                easy.setFont(font.deriveFont(font.getStyle() | Font.ITALIC, 17));
                easy.setBounds(40, 70, 180, 25);
                easy.setOpaque(false);
                easy.setFocusable(false);

                JRadioButton normal = new JRadioButton("Normal(4 lives )");
                font = normal.getFont();
                normal.setFont(font.deriveFont(font.getStyle() | Font.ITALIC, 17));
                normal.setBounds(40, 120, 180, 25);
                normal.setOpaque(false);
                normal.setFocusable(false);

                JRadioButton difficult = new JRadioButton("Difficult(3 lives)");
                font = difficult.getFont();
                difficult.setFont(font.deriveFont(font.getStyle() | Font.ITALIC, 17));
                difficult.setBounds(40, 170, 190, 25);
                difficult.setOpaque(false);
                difficult.setFocusable(false);

                ButtonGroup levels = new ButtonGroup();
                levels.add(easy);
                levels.add(normal);
                levels.add(difficult);

                JLabel lblRange = new JLabel("Coins Number(75-150):");
                font = lblRange.getFont();
                lblRange.setFont(font.deriveFont(font.getStyle() | Font.ITALIC, 18));
                lblRange.setBounds(250, 20, 250, 25);

                JTextField txtField = new JTextField();
                txtField.setBounds(300, 70, 80, 25);

                JButton btnConfirm = new JButton();
                btnConfirm.setText("Ok");
                btnConfirm.setBounds(70, 280, 70, 40);
                btnConfirm.setBackground(new Color(107, 224, 172, 223));
                btnConfirm.setFocusable(false);

                JButton btnBack = new JButton();
                btnBack.setText("Back");
                btnBack.setBounds(320, 280, 70, 40);
                btnBack.setBackground(new Color(107, 224, 172, 223));
                btnBack.setFocusable(false);

                frm.getContentPane().add(levelLbl);
                frm.getContentPane().add(easy);
                frm.getContentPane().add(normal);
                frm.getContentPane().add(difficult);
                frm.getContentPane().add(lblRange);
                frm.getContentPane().add(txtField);
                frm.getContentPane().add(btnConfirm);
                frm.getContentPane().add(btnBack);
                frm.setVisible(true);

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

                        int temp = Integer.parseInt(txtField.getText());

                        if(temp >= 75 && temp <= 150)
                        {
                            coinsCount = temp;
                            if(easy.isSelected() || normal.isSelected() || difficult.isSelected())
                            {
                                if(level.equals("easy"))
                                {
                                    livesCount = 5;
                                }
                                else if(level.equals("normal"))
                                {
                                    livesCount = 4;
                                }
                                else if (level.equals("difficult"))
                                {
                                    livesCount = 3;
                                }
                                frm.dispose();
                                initialize();
                                mainMenu();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Please choose the level first",
                                        "Alert",JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Coins should be in this range [75-150]",
                                    "Alert",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });

                btnBack.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frm.dispose();
                        initialize();
                        mainMenu();
                    }
                });
            }
        });
    }

    public static int getLivesCount()
    {
        return livesCount;
    }

    public static int getCoinsCount()
    {
        return coinsCount;
    }

    public static void saveRecord(int score)
    {
        //in case it is full already to avoid repeated object in the arraylist
        players.clear();
        readRecordFile();
        boolean flg = false;
        for (Player p:players)
        {
            if(p.getName().equalsIgnoreCase(inputName))
            {
                flg = true;
                if(score > p.getRecord())
                {
                    p.setRecord(score);
                    JOptionPane.showMessageDialog(null, "Congratulations! New Record is " + score);
                    editFile(score);
                    break;
                }
            }
        }

        if(!flg)
        {
            Player newPlayer = new Player(inputName, score);
            players.add(newPlayer);
            writeToFile();
        }
    }

    private static void readRecordFile()
    {
        FileReader in = null;

        try
        {
            in = new FileReader("src\\Pacman\\Records.txt");
            BufferedReader br = new BufferedReader(in);
            String thisLine = new String();

            while((thisLine = br.readLine()) != null)
            {
                String[] parts = thisLine.split(",");
                String playerName = parts[0];
                int playerRecord = Integer.parseInt(parts[1]);
                Player p = new Player(playerName, playerRecord);
                players.add(p);
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void writeToFile()
    {
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter("src\\Pacman\\Records.txt", false));
            for (Player p : players)
            {
                bw.write(p.getName() + "," + p.getRecord() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void editFile(int score)
    {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter("src\\Pacman\\Records.txt", false));

            for (Player p : players)
            {
                bw.write(p.getName() + "," + p.getRecord() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        new MyFrame();
        mainMenu();
    }
}
