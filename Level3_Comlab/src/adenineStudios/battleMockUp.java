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

    ImageIcon defaultPunchAnim1;
    
    ImageIcon defaultPunchAnim2;
    
    ImageIcon defaultMouthAnim1;
    
    ImageIcon defaultMouthAnim2;
    
    ImageIcon defaultShieldAnim1;
    
    ImageIcon alarm;

    int iconMode;
    int animMode = 0;
    
    boolean swordActive = false;
    boolean attackOnCooldown = false;
    boolean takeDMG = false;
    
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
    int round = 0;
    
    long end;
    float time;
    
    boolean hasParried = false;
    boolean hasFailedParry = false;
    boolean hasTakenDamage = false;
    boolean parryWindowActive = false;
    
    private boolean battleFinished = false;
    private boolean playerWon = false;

    JProgressBar playerHPBar;
    JProgressBar enemyHPBar;
    
    JPanel movePanel;
    JLabel battleMessage;
    
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
        
        alarm = new ImageIcon("Images/alarm.png");
        
        defaultPunchAnim1 = new ImageIcon("Images/openf.png");
        
        defaultPunchAnim2 = new ImageIcon("Images/closef.png");
        
        defaultMouthAnim1 = new ImageIcon("Images/mouthopen.png");
        
        defaultMouthAnim2 = new ImageIcon("Images/mouthclose.png");
        
        defaultShieldAnim1 = new ImageIcon("Images/shieldsuccess.png");
        
        defaultPlayer = new ImageIcon(defaultPlayer.getImage().getScaledInstance((frameWidth / mapWidth) * 3,(frameHeight / mapHeight) * 3, Image.SCALE_DEFAULT));
        
        defaultOpp = new ImageIcon(defaultOpp.getImage().getScaledInstance((frameWidth / mapWidth) * 3,(frameHeight / mapHeight) * 3, Image.SCALE_DEFAULT));
        
        defaultPunchAnim1 = new ImageIcon(defaultPunchAnim1.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));
        
        defaultPunchAnim2 = new ImageIcon(defaultPunchAnim2.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));
        
        defaultMouthAnim1 = new ImageIcon(defaultMouthAnim1.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));
        
        defaultMouthAnim2 = new ImageIcon(defaultMouthAnim2.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));
        
        defaultShieldAnim1 = new ImageIcon(defaultShieldAnim1.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));
        
        alarm = new ImageIcon(alarm.getImage().getScaledInstance((frameWidth/mapWidth) * 2, (frameHeight/mapHeight) * 2, Image.SCALE_DEFAULT));



        swordTimer = new Timer(16, e -> {
            if (!swordActive) return;

            long now = System.currentTimeMillis();
            long elapsed = now - swordStartTime;
            
            end = System.currentTimeMillis();
            time = (end - beginning) / 1000f;
            if (elapsed >= 2000) {
                parryWindowActive = false;
                swordLabel.setVisible(false);
                swordActive = false;
                attackOnCooldown = false;
                takeDMG = false;

                swordTimer.stop();

                if (oh > 0 && !hasParried && !hasTakenDamage) {
                    takeDamage();
                }

                hasFailedParry = false;
                hasParried = false;

                if (oh == 0) {
                    JOptionPane.showMessageDialog(frame, "Hacker.", "Win", JOptionPane.INFORMATION_MESSAGE);
                    playerWon = true;
                    battleFinished = true;
                    frame.dispose();
                }
            } else if (elapsed >= 1800) {
                if (hasParried) {
                    swordLabel.setIcon(defaultShieldAnim1);
                }
                else if (!takeDMG) {
                    swordLabel.setIcon(defaultMouthAnim2);
                }

            } else if (elapsed >= 1440) {
                if(!takeDMG) {
                    swordLabel.setIcon(defaultMouthAnim1);
                }
            } else if (elapsed >= 480) {
                swordLabel.setIcon(alarm);
            } else if (elapsed >= 240) {
                if (animMode == 1) {
                    swordLabel.setIcon(defaultPunchAnim2);
                } else if (animMode == 2) {
                    swordLabel.setIcon(defaultSwordAnim2);
                }
            } else {
                if (animMode == 1) {
                    swordLabel.setIcon(defaultPunchAnim1);
                } else if (animMode == 2) {
                    swordLabel.setIcon(defaultSwordAnim1);
                }
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

        playerHPBar = new JProgressBar(0, 200);
        playerHPBar.setStringPainted(true);
        playerHPBar.setForeground(Color.GREEN);
        playerHPBar.setBackground(Color.DARK_GRAY);
        playerHPBar.setBorderPainted(true);
        playerHPBar.setFont(new Font("Monospaced", Font.BOLD, 14));
        playerHPBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        enemyHPBar = new JProgressBar(0, 1000);
        enemyHPBar.setStringPainted(true);
        enemyHPBar.setForeground(Color.RED);
        enemyHPBar.setBackground(Color.DARK_GRAY);
        enemyHPBar.setBorderPainted(true);
        enemyHPBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        updateHealthBars();
        
        frame.add(playerHPBar, new Rectangle(1, 2, 4, 1));
        frame.add(enemyHPBar, new Rectangle(7, 2, 4, 1));
        
        
        JLabel playerText = new JLabel("Player Health:");
        playerText.setForeground(Color.WHITE);
        playerText.setFont(new Font("Monospaced", Font.BOLD, 14));

        JLabel enemyText = new JLabel("Enemy Health:");
        enemyText.setForeground(Color.WHITE);
        enemyText.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        frame.add(playerText, new Rectangle(1, 1, 4, 1));
        frame.add(enemyText, new Rectangle(7, 1, 4, 1));
        
        battleMessage = new JLabel("Choose an action...");
        battleMessage.setHorizontalAlignment(SwingConstants.CENTER);
        battleMessage.setForeground(Color.WHITE);
        battleMessage.setBackground(Color.BLACK);
        battleMessage.setOpaque(true);
        battleMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        battleMessage.setFont(new Font("Monospaced", Font.BOLD, 18));

        frame.add(battleMessage, new Rectangle(1, 7, 10, 1));
        
        movePanel = new JPanel();
        movePanel.setLayout(new GridLayout(2, 2, 5, 5));
        movePanel.setBackground(Color.BLACK);
        movePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        
        JButton move1 = createMoveButton("Jab");
        JButton move2 = createMoveButton("Sword Strike");
        JButton move3 = createMoveButton("Shield Block");
        JButton move4 = createMoveButton("");
        
        move1.addActionListener(e -> {
            if (!attackOnCooldown) {
                performAttack();
                if (oh > 0) {
                    oh -= 100;
                    if (oh < 0) oh = 0;

                    updateHealthBars();
                    showMessage("Enemy Hit!");
                }
                animMode = 1;
            }
            
        });
        move2.addActionListener(e -> {
            if (!attackOnCooldown) {
                performAttack();
                if (oh > 0) {
                    oh -= 200;
                    if (oh < 0) oh = 0;

                    updateHealthBars();
                    showMessage("Enemy Hit!");
                }
                animMode = 2;
            }
        });
        move3.addActionListener(e -> {
            end = System.currentTimeMillis();
            time = (end - beginning) / 1000f;
            
            if (time >= 1.44 && time <= 1.75) {
                if (!parryWindowActive) {
                    showMessage("No attack to parry!");
                    return;
                }

                if (hasParried) {
                    showMessage("Already parried, you cannot parry again!");
                    return;
                }
                if (!hasFailedParry) {
                    hasParried = true;
                    showMessage("Parried!");
                }
                
            }
            else {
                if (hasFailedParry) {
                    showMessage("Already parried, you cannot parry again!");
                }
                else {
                    showMessage("You failed the Parry!");
                    hasFailedParry = true;
                }
            }
            
        });
        
        movePanel.add(move1);
        movePanel.add(move2);
        movePanel.add(move3);
        movePanel.add(move4);
        
        frame.add(movePanel, new Rectangle(1, 8, 10, 3));
        
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
    
    private JButton createMoveButton(String name) {
        JButton move = new JButton(name);

        move.setFont(new Font("Times New Roman", Font.BOLD, 16));
        move.setFocusPainted(false);

        move.setBackground(Color.DARK_GRAY);
        move.setForeground(Color.WHITE);

        move.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        return move;
    }

    
    public void timeStart(){
        beginning = System.currentTimeMillis();
        System.out.println("\nRound Start");
        
    }
    
    private void takeDamage() {
        if (!hasTakenDamage) {
            if (ph > 100) {
                ph -= 100;
                playerHPBar.setValue(ph);
                System.out.println("You got hit, Health: " + ph + "/200");
            } else {
                ph -= 100;
                updateHealthBars();
                JOptionPane.showMessageDialog(frame, "You died EZ KID", "Lose", JOptionPane.INFORMATION_MESSAGE);
                playerWon = false;
                battleFinished = true;

                swordTimer.stop();
                frame.dispose();
            }
            hasTakenDamage = true;
        }
    }
    
    private void performAttack() {
        if (swordActive || attackOnCooldown) return;

        attackOnCooldown = true;
        parryWindowActive = true;
        hasParried = false;
        hasFailedParry = false;
        hasTakenDamage = false;

        timeStart();

        swordActive = true;
        swordStartTime = System.currentTimeMillis();

        swordTimer.start();
    }
    
    private void updateHealthBars() {
        playerHPBar.setValue(ph);
        playerHPBar.setString(ph + " / 200");

        enemyHPBar.setValue(oh);
        enemyHPBar.setString(oh + " / 1000");
    }
    
    private void showMessage(String text) {
        battleMessage.setText(text);
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

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
    
    public static void main(String[] args) {
        battleMockUp sg = new battleMockUp();
        sg.setFrame();
    }   
}