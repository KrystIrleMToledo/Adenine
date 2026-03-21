package adenineStudios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;


public class battleMockUp implements KeyListener{
    long beginning;
    
    JFrame frame;
    
    int mapHeight = 12;
    int mapWidth = 12;
    
    int frameHeight = 800;
    int frameWidth = 800;
    
    ImageIcon defaultTexture;
    
    ImageIcon defaultPlayer;
    
    ImageIcon defaultOpp;
    
    ImageIcon defaultSwordAnim1;
    
    ImageIcon defaultSwordAnim2;
    
    ImageIcon defaultSwordAnim3;
  
    ImageIcon defaultSwordAnim4;

    int iconMode;
    
    boolean swordActive = false;  // is sword currently being shown
    
    long swordStartTime = 0;      // when sword animation started
        
   
    
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


    
    public battleMockUp(){
        frame = new JFrame("how are you po sir?");
        
        defaultTexture = new ImageIcon("Images/adenineStudions/bossStuff/Puzzle2");
        
        defaultPlayer = new ImageIcon("Images/dog.jpg");
        
        defaultOpp = new ImageIcon("Images/adenineStudios/bossStuff/boss.png"); //transparent, final boss
        
        defaultSwordAnim1 = new ImageIcon("Images/sword1.png");
        
        defaultSwordAnim2 = new ImageIcon("Images/sword2.png");
        
        defaultSwordAnim3 = new ImageIcon("Images/sword3.png");
        
        defaultSwordAnim4 = new ImageIcon("Images/mugshot.jpg");
        
        defaultTexture = new ImageIcon(defaultTexture.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
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

                // if player neither parried nor took damage yet
                if (!hasParried && !hasTakenDamage) {
                    takeDamage();
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
        
        //add player here
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
            playerMap[i] = new JLabel(); // all empty
        }

        // find the top-left of the 3x3 player area
        for (int i = 0; i < playerStarting.length; i++) {
            if (playerStarting[i] == 1) {
                playerPos = i;
                break;
            }
        }
        
        // find the top-left of the 3x3 opponent area
        for (int i = 0; i < playerStarting.length; i++) {
            if (playerStarting[i] == 2) {
                oppPos = i;
                break;
            }
        }
        
        // find the top-left of the 3x3 sword area
        for (int i = 0; i < playerStarting.length; i++) {
            if (playerStarting[i] == 3) {
                swordPos = i;
                break;
            }
        }

        
        //shape map here
        dynamicMap = new JLabel[mapHeight*mapWidth];
        startingMap = new int[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        };
        for(int x = 0; x < dynamicMap.length; x++){
            switch(startingMap[x]){
                case 0 -> dynamicMap[x] = new JLabel(defaultTexture);
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
        int sx = px + 3;  // directly in front
        int sy = py + 1;  // center of player height


        JLabel player = new JLabel(defaultPlayer);
        frame.add(player, new Rectangle(px, py, 3, 3));

        JLabel opponent = new JLabel(defaultOpp);
        frame.add(opponent, new Rectangle(ox, oy, 3, 3));
        
        // ---- SWORD (hidden by default) ----
        swordLabel = new JLabel(defaultSwordAnim2);

        // example position: right of player
        swordLabel.setVisible(false); // hidden initially
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
                
            }
            hasTakenDamage = true; // mark that damage has been applied for this swing
        }
    }



    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        // handle keys
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_RIGHT -> {
                timeStart();
                swordActive = true;
                swordStartTime = System.currentTimeMillis();
                hasParried = false;       // can parry this swing
                hasTakenDamage = false;   // damage not yet applied
                oh -= 100;
                System.out.println("You hit the enemy, Enemy Health: " + oh + "/1000");
                swordTimer.start();
            }



            case KeyEvent.VK_LEFT -> {
                end = System.currentTimeMillis();
                time = (end - beginning) / 1000f;

                if (time >= 1.44 && time <= 1.75) { // parry window
                    if (!hasParried) {
                        System.out.println("Parry");
                        hasParried = true;
                    } else {
                        System.out.println("You already parried");
                        takeDamage();
                    }
                } else { // too early or too late
                    System.out.println("You failed the parry");
                    takeDamage();
                }
            }


            case KeyEvent.VK_E -> {
                if (oh == 0) {
                    JOptionPane.showMessageDialog(frame, "Hacker.", "Win", JOptionPane.INFORMATION_MESSAGE);
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
