package adenineStudios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class battleMockUp implements KeyListener{
    long beginning;
    
    JFrame frame;
    
    int mapHeight = 12;
    int mapWidth = 12;
    
    int frameHeight = 800;
    int frameWidth = 800;
    
    ImageIcon wall1;
    ImageIcon wall2;
    ImageIcon wall3;
    ImageIcon wall4;
    
    ImageIcon defaultPlayer;
    
    ImageIcon defaultOpp;
    
    ImageIcon defaultSwordAnim1;
    
    ImageIcon defaultSwordAnim2;
    
    ImageIcon defaultSwordAnim3;
  
    ImageIcon defaultSwordAnim4;

    int iconMode;
    
    boolean swordActive = false;
    
    long swordStartTime = 0;
        
   
    
    JLabel dynamicMap[];
    int startingMap[];
    
    JLabel playerMap[];
    int playerStarting[];
    int playerPos;
    int oppPos;
    int swordPos;
    
    JLabel swordLabel;
    
    Timer swordTimer;
    
    Integer[] collisionTiles;
    boolean hasCollision = false;
    
    int swordFrame = 0;
    int ph = 200;
    int oh = 1000;
    
    long end;
    float time;
    boolean hasParried = false;
    boolean hasTakenDamage = false;
    
    private boolean battleFinished = false;
    private boolean playerWon = false;



    
    public battleMockUp(){
        frame = new JFrame("how are you po sir?");
        
        wall1 = new ImageIcon("Images/adenineStudios/bossStuff/Puzzle1.png");
        wall2 = new ImageIcon("Images/adenineStudios/bossStuff/Puzzle2.png");
        wall3 = new ImageIcon("Images/adenineStudios/bossStuff/Puzzle3.png");
        wall4 = new ImageIcon("Images/adenineStudios/bossStuff/Puzzle4.png");

        wall1 = new ImageIcon(wall1.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall2 = new ImageIcon(wall2.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall3 = new ImageIcon(wall3.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall4 = new ImageIcon(wall4.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        defaultPlayer = new ImageIcon("Images/adenineStudios/gamesprites/boyidle3.png");
        
        defaultOpp = new ImageIcon("Images/adenineStudios/bossStuff/boss.png"); //transparent, final boss
        
        defaultSwordAnim1 = new ImageIcon("Images/adenineStudios/gamesprites/sword1.png");
        
        defaultSwordAnim2 = new ImageIcon("Images/adenineStudios/gamesprites/sword2.png");
        
        defaultSwordAnim3 = new ImageIcon("Images/adenineStudios/gamesprites/sword3.png");
        
        defaultSwordAnim4 = new ImageIcon("Images/adenineStudios/gamesprites/sword4.png");
        
        defaultPlayer = new ImageIcon(defaultPlayer.getImage().getScaledInstance((frameWidth / mapWidth) * 3,(frameHeight / mapHeight) * 3, Image.SCALE_DEFAULT));
        
        defaultOpp = new ImageIcon(defaultOpp.getImage().getScaledInstance((frameWidth / mapWidth) * 3,(frameHeight / mapHeight) * 3, Image.SCALE_DEFAULT));
        
        defaultSwordAnim1 = new ImageIcon(defaultSwordAnim1.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));
        
        defaultSwordAnim2 = new ImageIcon(defaultSwordAnim2.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));
        
        defaultSwordAnim3 = new ImageIcon(defaultSwordAnim3.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));
        
        defaultSwordAnim4 = new ImageIcon(defaultSwordAnim4.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));
        
        



        swordTimer = new Timer(16, e -> {
            if (!swordActive) return;

            long now = System.currentTimeMillis();
            long elapsed = now - swordStartTime;
            
            end = System.currentTimeMillis();
            time = (end - beginning) / 1000f;
            if (elapsed >= 2000) {
                swordLabel.setVisible(false);
                swordActive = false;
                swordTimer.stop();

                // Enemy still alive → enemy attacks
                if (oh > 0 && !hasParried && !hasTakenDamage) {
                    takeDamage();
                }

                // Enemy died → NOW end the battle
                if (oh == 0) {
                    JOptionPane.showMessageDialog(frame, "Hacker.", "Win", JOptionPane.INFORMATION_MESSAGE);
                    playerWon = true;
                    battleFinished = true;
                    frame.dispose();
                }
            } else if (elapsed >= 1800) {
                swordLabel.setIcon(defaultSwordAnim4);
            } else if (elapsed >= 1440) {
                swordLabel.setIcon(defaultSwordAnim3);
                swordLabel.setVisible(true);
            } else if (elapsed >= 480) {
                swordLabel.setVisible(false);
            } else if (elapsed >= 240) {
                swordLabel.setIcon(defaultSwordAnim2);
            } else {
                swordLabel.setIcon(defaultSwordAnim1);
                swordLabel.setVisible(true);
            }
        });


        
        playerPos = -1;
        oppPos = -1;
        iconMode = 0;
        
        playerMap = new JLabel[mapHeight*mapWidth];
        playerStarting = new int[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 1, 1, 1, 3, 3, 2, 2, 2, 0, 0,
            0, 0, 1, 1, 1, 3, 3, 2, 2, 2, 0, 0,
            0, 0, 1, 1, 1, 0, 0, 2, 2, 2, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        };
        for (int i = 0; i < playerMap.length; i++) {
            playerMap[i] = new JLabel();
        }

        for (int i = 0; i < playerStarting.length; i++) {
            if (playerStarting[i] == 1) {
                playerPos = i;
                break;
            }
        }
        
        for (int i = 0; i < playerStarting.length; i++) {
            if (playerStarting[i] == 2) {
                oppPos = i;
                break;
            }
        }
        
        for (int i = 0; i < playerStarting.length; i++) {
            if (playerStarting[i] == 3) {
                swordPos = i;
                break;
            }
        }
        dynamicMap = new JLabel[mapHeight*mapWidth];
        startingMap = new int[]{
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
            1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4,
        };
        for(int x = 0; x < dynamicMap.length; x++){
            switch(startingMap[x]){
                case 0 -> {
                    dynamicMap[x] = new JLabel();
                    dynamicMap[x].setForeground(Color.red);
                    dynamicMap[x].setBackground(Color.red);
                }
                case 1 -> dynamicMap[x] = new JLabel(wall2);
                case 2 -> dynamicMap[x] = new JLabel(wall3);
                case 3 -> dynamicMap[x] = new JLabel(wall4);
                case 4 -> dynamicMap[x] = new JLabel(wall1);
            }
        }
        
        collisionTiles = new Integer[]{};
    }
    
    public void setFrame() {
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth, mapHeight)));

        int px = playerPos % mapWidth;
        int py = playerPos / mapWidth;
        int ox = oppPos % mapWidth;
        int oy = oppPos / mapWidth;
        int sx = px + 3;
        int sy = py + 1; 


        JLabel player = new JLabel(defaultPlayer);
        frame.add(player, new Rectangle(px, py, 3, 3));

        JLabel opponent = new JLabel(defaultOpp);
        frame.add(opponent, new Rectangle(ox, oy, 3, 3));
        
        swordLabel = new JLabel(defaultSwordAnim2);
        swordLabel.setVisible(false);
        frame.add(swordLabel, new Rectangle(sx, sy, 2, 2));


        
        int x = 0, y = 0;
        for (int n = 0; n < dynamicMap.length; n++) {
            frame.add(dynamicMap[n], new Rectangle(x, y, 1, 1));
            x++;
            if (x % mapWidth == 0) {
                x = 0;
                y++;
            }
        }
        
        

        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        frame.addKeyListener(this);
    }


    
    public void timeStart(){
        beginning = System.currentTimeMillis();
        System.out.println("\nRound Start");
        
    }
    
    private void takeDamage() {
        if (!hasTakenDamage) {
            if (ph > 100) {
                ph -= 100;
                System.out.println("You got hit, Health: " + ph + "/200");
            } else {
                ph -= 100;
                JOptionPane.showMessageDialog(frame, "You died EZ KID", "Lose", JOptionPane.INFORMATION_MESSAGE);
                playerWon = false;
                battleFinished = true;

                swordTimer.stop();
                frame.dispose();

            }
            hasTakenDamage = true;
        }
    }
    
    public boolean startBattleAndWait() {
        SwingUtilities.invokeLater(this::setFrame);

        while (!battleFinished) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {}
        }

        return playerWon;
    }




    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        switch(ke.getKeyCode()) {
            case KeyEvent.VK_RIGHT -> {
                timeStart();
                swordActive = true;
                swordStartTime = System.currentTimeMillis();
                hasParried = false;
                hasTakenDamage = false;
                if (oh > 0) {
                    oh -= 100;
                    if (oh < 0) oh = 0;
                    System.out.println("You hit the enemy, Enemy Health: " + oh + "/1000");

                if (oh == 0) {
                    hasParried = true;      // enemy cannot hit back
                    hasTakenDamage = true; // safety
                    // DO NOT end battle here
                }


            }

                swordTimer.start();
            }

            case KeyEvent.VK_LEFT -> {
                end = System.currentTimeMillis();
                time = (end - beginning) / 1000f;

                if (time >= 1.44 && time <= 1.75) {
                    if (!hasParried) {
                        System.out.println("Parry");
                        hasParried = true;
                    } else {
                        System.out.println("You already parried");
                        takeDamage();
                    }
                } else {
                    System.out.println("You failed the parry");
                    takeDamage();
                }
            }
        }
    }



    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
    
    public static void main(String[] args) {
        battleMockUp sg = new battleMockUp();
        sg.setFrame();
    }   
}