package PD8TOTAL;

// Paller
// Daned
// Jonson



import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class GroupPD implements KeyListener, ActionListener{
    JFrame frame;
    ImageIcon playerIcon1;
    ImageIcon playerIcon2;
    ImageIcon playerIcon3;
    ImageIcon playerIcon4;
    ImageIcon playerIcon5;
    ImageIcon playerIcon6;
    ImageIcon playerIcon7;
    ImageIcon playerIcon8;
    ImageIcon playerIcon9;
    ImageIcon playerIcon10;
    ImageIcon playerIcon11;
    ImageIcon playerIcon12;
    ImageIcon oldman;
    ImageIcon tile1;
    ImageIcon tree1;
    ImageIcon flower1;
    ImageIcon snake1;
    ImageIcon snake2;
    ImageIcon portal1;
    ImageIcon portal2;
    ImageIcon cave;
    int charPlace[];
    int mapLayout[];
    int mapWidth=12;
    int mapHeight=12;
    int frameWidth=1050;
    int frameHeight=1050;
    int charac;
    int cht = 0;
    int bct = 0;
    int cct = 0;
    int charX;
    int charY;
    int characterMode;
    int worldWidth = 36;
    int worldHeight = 36;
    int[] world = new int[worldWidth * worldHeight];
    int viewWidth = 12;
    int viewHeight = 12;
    JLabel[] tiles = new JLabel[viewWidth * viewHeight];
    JLabel[] character = new JLabel[viewWidth * viewHeight];
    JLabel snakeLabel;
    int camX = 0;   // world column of top-left camera tile
    int camY = 0;   // world row of top-left camera tile
    int playerIndex;     // index in the WORLD
    int playerX = 6;         // world column
    int playerY = 6;         // world row
    int tempX = 0;
    int tempY = 0;
    int keyc = 0;
    int tree1n = 0;
    int tree2n = 0;
    int tree3n = 0;
    int tree4n = 0;
    int tree5n = 0;
    int tree6n = 0;
    int tree7n = 0;
    int tree8n = 0;
    int GRASS = 0;
    int TREE = 1;
    int FLOWER = 2;
    int CAVE = 3;
    int OLDMAN = 4;
    int PORTAL = 5;
    int snakess = 0;
    int portall = 0;
    int direction = 0; // 0=down, 1=left, 2=right, 3=up
    int animFrame = 0; // 0=idle, 1=walk1, 2=walk2
    int walkStep = 0;
    int wincon = 0;
    
    boolean inCave = false;
    
    JLabel chatBox;
    JLabel chatText;
    JButton nextB;
    JButton exitB;
    
    ImageIcon getPlayerFrame() {
    switch (direction) {
        case 0: // DOWN
            if (animFrame == 1) return playerIcon2;
            if (animFrame == 2) return playerIcon3;
            return playerIcon1;

        case 1: // LEFT
            if (animFrame == 1) return playerIcon5;
            if (animFrame == 2) return playerIcon6;
            return playerIcon4;

        case 2: // RIGHT
            if (animFrame == 1) return playerIcon8;
            if (animFrame == 2) return playerIcon9;
            return playerIcon7;

        case 3: // UP
            if (animFrame == 1) return playerIcon11;
            if (animFrame == 2) return playerIcon12;
            return playerIcon10;
        }   
        return playerIcon1; // fallback
    }
    int c;
    int ph;

    String playerType = "";
    
    int worldIndex(int x, int y) {
        return y * worldWidth + x;
    }
    int viewIndex(int x, int y) {
        return y * viewWidth + x;
    }
    private void loadPlayerHealth() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("phealth.txt"));
            String line = br.readLine();
            if (line != null) {
                ph = Integer.parseInt(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Text not found, going 200");
            ph = 200;
        }
    }
    
    private void writePlayerHealth() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("phealth.txt"));
            bw.write("200");
            bw.close();
        } catch (IOException e) {
            System.out.println("Can't locate file");
        }
    }
    
    private void loadPlayerType() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("substitute.txt"));
            String line = br.readLine();
            if (line != null) {
                playerType = line.trim().toLowerCase();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("substitute.txt not found, defaulting to boy");
            playerType = "boy";
        }
    }


    public GroupPD() {
        writePlayerHealth();
        
        chatBox=new JLabel();
        chatText=new JLabel("Hello wanderer");
        nextB=new JButton("Who are you?");
        exitB=new JButton("Exit");
        
        frame = new JFrame();
        charX=-1;
        charY=-1;
        characterMode=0;
        
        tile1 = new ImageIcon("Images/gr9/tile1.png");
        tree1 = new ImageIcon("Images/gr9/tree1.png");
        flower1 = new ImageIcon("Images/gr9/tree2.png");
        
        loadPlayerType();
        if (playerType.equals("girl")) {
            //downwards animation
            playerIcon1=new ImageIcon("Images/gr9/girlidle1.png");
            playerIcon2=new ImageIcon("Images/gr9/girlwalk1.png");
            playerIcon3=new ImageIcon("Images/gr9/girlwalk2.png");

            //left animation
            playerIcon4=new ImageIcon("Images/gr9/girlidle2.png");
            playerIcon5=new ImageIcon("Images/gr9/girlwalk4.png");
            playerIcon6=new ImageIcon("Images/gr9/girlwalk8.png");

            //right animation
            playerIcon7=new ImageIcon("Images/gr9/girlidle3.png");
            playerIcon8=new ImageIcon("Images/gr9/girlwalk3.png");
            playerIcon9=new ImageIcon("Images/gr9/girlwalk7.png");

            //upwards animation
            playerIcon10=new ImageIcon("Images/gr9/girlidle4.png");
            playerIcon11=new ImageIcon("Images/gr9/girlwalk5.png");
            playerIcon12=new ImageIcon("Images/gr9/girlwalk6.png");
        } else {
            //downwards animation
            playerIcon1=new ImageIcon("Images/gr9/boyidle1.png");
            playerIcon2=new ImageIcon("Images/gr9/boywalk1.png");
            playerIcon3=new ImageIcon("Images/gr9/boywalk2.png");

            //left animation
            playerIcon4=new ImageIcon("Images/gr9/boyidle2.png");
            playerIcon5=new ImageIcon("Images/gr9/boywalk4.png");
            playerIcon6=new ImageIcon("Images/gr9/boywalk8.png");

            //right animation
            playerIcon7=new ImageIcon("Images/gr9/boyidle3.png");
            playerIcon8=new ImageIcon("Images/gr9/boywalk3.png");
            playerIcon9=new ImageIcon("Images/gr9/boywalk7.png");

            //upwards animation
            playerIcon10=new ImageIcon("Images/gr9/boyidle4.png");
            playerIcon11=new ImageIcon("Images/gr9/boywalk5.png");
            playerIcon12=new ImageIcon("Images/gr9/boywalk6.png");
        }
        
        
        snake1=new ImageIcon("Images/gr9/snake1.png");
        snake2=new ImageIcon("Images/gr9/snake2.png");
        oldman=new ImageIcon("Images/gr9/oldman.png");
        portal1=new ImageIcon("Images/gr9/portal1.png");
        portal2=new ImageIcon("Images/gr9/portal2.png");
        cave=new ImageIcon("Images/gr9/cave.png");
        
        playerIcon1=new ImageIcon(playerIcon1.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon2=new ImageIcon(playerIcon2.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon3=new ImageIcon(playerIcon3.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon4=new ImageIcon(playerIcon4.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon5=new ImageIcon(playerIcon5.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon6=new ImageIcon(playerIcon6.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon7=new ImageIcon(playerIcon7.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon8=new ImageIcon(playerIcon8.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon9=new ImageIcon(playerIcon9.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon10=new ImageIcon(playerIcon10.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon11=new ImageIcon(playerIcon11.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        playerIcon12=new ImageIcon(playerIcon12.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        tile1=new ImageIcon(tile1.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        tree1 = new ImageIcon(tree1.getImage().getScaledInstance((frameWidth / mapWidth),(frameHeight / mapHeight),Image.SCALE_DEFAULT));
        flower1 = new ImageIcon(flower1.getImage().getScaledInstance((frameWidth / mapWidth),(frameHeight / mapHeight),Image.SCALE_DEFAULT));
        snake1 = new ImageIcon(snake1.getImage().getScaledInstance((frameWidth / mapWidth),(frameHeight / mapHeight),Image.SCALE_DEFAULT));
        snake2 = new ImageIcon(snake2.getImage().getScaledInstance((frameWidth / mapWidth),(frameHeight / mapHeight),Image.SCALE_DEFAULT));
        oldman = new ImageIcon(oldman.getImage().getScaledInstance((frameWidth / mapWidth),(frameHeight / mapHeight),Image.SCALE_DEFAULT));
        portal1 = new ImageIcon(portal1.getImage().getScaledInstance((frameWidth / mapWidth),(frameHeight / mapHeight),Image.SCALE_DEFAULT));
        portal2 = new ImageIcon(portal2.getImage().getScaledInstance((frameWidth / mapWidth),(frameHeight / mapHeight),Image.SCALE_DEFAULT));
        cave = new ImageIcon(cave.getImage().getScaledInstance((frameWidth / mapWidth),(frameHeight / mapHeight),Image.SCALE_DEFAULT));
        
        for (int i = 0; i < world.length; i++) {
            world[i] = 0;
        }

        // First chunk (A chunk is 12x12 btw)(Top-left corner)
        world[worldIndex(6, 3)] = 4;
        world[worldIndex(7, 3)] = 5;
        world[worldIndex(3, 5)] = 1;
        world[worldIndex(6, 9)] = 1; //
        world[worldIndex(8, 4)] = 1;
        world[worldIndex(5, 6)] = 1;
        world[worldIndex(11, 11)] = 1;
        
        //Second chunk (Top-right corner)
        world[worldIndex(35, 5)] = 1;
        world[worldIndex(26, 7)] = 1; //
        world[worldIndex(30, 11)] = 1;
        world[worldIndex(28, 4)] = 1;
        world[worldIndex(32, 0)] = 1;

        //Third chunk (Top-middle)
        world[worldIndex(13, 5)] = 1;
        world[worldIndex(15, 7)] = 1;
        world[worldIndex(20, 11)] = 1;
        world[worldIndex(24, 4)] = 1;
        world[worldIndex(22, 0)] = 1;
        
        //Fourth chunk (Bottom-left corner)
        world[worldIndex(0, 33)] = 1;
        world[worldIndex(7, 35)] = 1; //
        world[worldIndex(5, 26)] = 1;
        world[worldIndex(9, 28)] = 1;
        world[worldIndex(11, 30)] = 1;
        
        //Fitth chunk (Bottom-right corner)
        world[worldIndex(33, 33)] = 1; //
        world[worldIndex(26, 35)] = 1;
        world[worldIndex(30, 26)] = 1;
        world[worldIndex(35, 28)] = 1;
        world[worldIndex(28, 30)] = 1;
        
        //Sixth chunk (Bottom-middle corner)
        world[worldIndex(13, 35)] = 1;
        world[worldIndex(15, 33)] = 1;
        world[worldIndex(20, 28)] = 1;
        world[worldIndex(24, 26)] = 1; //
        world[worldIndex(22, 30)] = 1;
        
        //SIX-Seventh chunk (Middle-left corner)
        world[worldIndex(3, 25)] = 1;
        world[worldIndex(6, 20)] = 1;//
        world[worldIndex(8, 16)] = 1;
        world[worldIndex(5, 14)] = 1;
        world[worldIndex(11, 23)] = 1;
        
        //Eighth chunk (Middle-right corner)
        world[worldIndex(33, 23)] = 1;
        world[worldIndex(26, 20)] = 1; //
        world[worldIndex(30, 14)] = 1;
        world[worldIndex(35, 17)] = 1;
        world[worldIndex(28, 13)] = 1;
        
        //Ninthc chunk (Middle-middle corner)
        world[worldIndex(22, 23)] = 1;
        world[worldIndex(24, 18)] = 1;
        world[worldIndex(14, 14)] = 1;
        world[worldIndex(17, 17)] = 1; //
        world[worldIndex(18, 18)] = 3;
        world[worldIndex(16, 13)] = 1;
        
        character=new JLabel[mapWidth*mapHeight];
        charPlace=new int[]{
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,1,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
        };
        for(int i=0;i<character.length;i++){
            if(charPlace[i]==1){
                character[i]=new JLabel(playerIcon1);
                charac=i;
            }
            else character[i]=new JLabel();
        }

        tiles=new JLabel[mapWidth*mapHeight];
        mapLayout=new int[]{
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
        };
        for(int i=0;i<tiles.length;i++){
            if(mapLayout[i]==0) tiles[i]=new JLabel(tile1);
        }


    }
    public void MainCodeBlock() {
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth, mapHeight)));
        
        frame.add(chatText, new Rectangle(1,8,7,1));
        frame.add(nextB, new Rectangle(10,9,2,1));
        frame.add(exitB, new Rectangle(8,9,2,1));
        frame.add(chatBox, new Rectangle(0,7,12,4));
        chatBox.setOpaque(true);
        chatBox.setBackground(Color.white);

        int x=0, y=0, w=1, h=1;
        for(int i=0;i<character.length;i++){
            frame.add(character[i], new Rectangle(x,y,w,h));
            x++;
            if(x%mapWidth==0){
                x=0;
                y++;
            }
        }
        
        x=0; y=0; w=1; h=1;
        for(int i=0;i<tiles.length;i++){
            frame.add(tiles[i], new Rectangle(x,y,w,h));
            x++;
            if(x%mapWidth==0){
                x=0;
                y++;
            }
        }
        frame.setSize(frameWidth,frameHeight);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.requestFocusInWindow();
        frame.addKeyListener(this);
        
        exitB.addActionListener(this);
        nextB.addActionListener(this);
        setChatVisible(false);
        
        render(snakess, portall);
        
    }
    
    public void setChatVisible(boolean b){
        chatBox.setVisible(b);
        chatText.setVisible(b);
        nextB.setVisible(b);
        exitB.setVisible(b);
    }
    
    public void movePlayer(int dx, int dy) {
        int nx = playerX + dx;
        int ny = playerY + dy;

        // bounds check
        if (nx < 0 || ny < 0 || nx >= worldWidth || ny >= worldHeight) {
            animFrame = 0;   // idle
            walkStep = 0;
            render(snakess, portall);
            return;
        }

        // TREE / SNAKE COLLISION CHECK
        if (isBlocked(nx, ny)) {
            animFrame = 0;   // idle
            walkStep = 0;
            render(snakess, portall);
            return;
        }

        
        //move player
        playerX = nx;
        playerY = ny;
        
        // animation logic
        if (direction == 1 || direction == 2) {
            // left - right
            walkStep++;

            if (walkStep == 1) animFrame = 1;      // walk1
            else if (walkStep == 2) animFrame = 0; // idle
            else if (walkStep == 3) animFrame = 2; // walk2
            else {
                animFrame = 0;
                walkStep = 0;
            }
        } else {
            // up - down
            animFrame++;
            if (animFrame > 2) animFrame = 1;
        }




        // move camera so player stays near middle
        camX = playerX - viewWidth / 2;
        camY = playerY - viewHeight / 2;

        // clamp camera bounds
        if (camX < 0) camX = 0;
        if (camY < 0) camY = 0;
        if (camX > worldWidth - viewWidth) camX = worldWidth - viewWidth;
        if (camY > worldHeight - viewHeight) camY = worldHeight - viewHeight;

        render(snakess, portall);
    }


    public void render(int snakep, int portalp) {
    for (int vy = 0; vy < viewHeight; vy++) {
        for (int vx = 0; vx < viewWidth; vx++) {

            int wx = camX + vx;
            int wy = camY + vy;

            int wIndex = worldIndex(wx, wy);
            int vIndex = viewIndex(vx, vy);

            // draw tile based on world value
            if (world[wIndex] == 0) {
                tiles[vIndex].setIcon(tile1);   // grass
            } else if (world[wIndex] == 1) {
                tiles[vIndex].setIcon(tree1);   // tree
            } else if (world[wIndex] == 2) {
                tiles[vIndex].setIcon(flower1);   // hive tree
            } else if (world[wIndex] == 3) {
                tiles[vIndex].setIcon(cave);
            } else if (world[wIndex] == 4) {
                tiles[vIndex].setIcon(oldman);    //old man
            } else if (world[wIndex] == 5) {
                if (portalp == 0) {
                    tiles[vIndex].setIcon(portal1);   // snake stone
                }
                else {
                    tiles[vIndex].setIcon(portal2);   // snake
                }
            }


            character[vIndex].setIcon(null);

            // draw player if here
            if (!inCave && wx == playerX && wy == playerY)
                character[vIndex].setIcon(getPlayerFrame());
            }
        }
    }

    public boolean gr9() {
        SwingUtilities.invokeLater(this::MainCodeBlock);
        return true;
    }
    public static void main(String[] args) {
        GroupPD yes = new GroupPD();
        yes.MainCodeBlock();
    }
    public boolean isBlocked(int x, int y) {
        int index = worldIndex(x, y);
        return world[index] == TREE || world[index] == CAVE || world[index] == FLOWER || world[index] == OLDMAN || world[index] == PORTAL;
    }
    public void enterCave() {
        inCave = true;
        render(snakess, portall); // refresh screen
    }
    public void exitCave() {
        inCave = false;
        render(snakess, portall);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (inCave) return;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direction = 2;
            movePlayer(1, 0);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            direction = 1;
            movePlayer(-1, 0);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            direction = 0;
            movePlayer(0, 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            direction = 3;
            movePlayer(0, -1);
        }


        //Get x and y values in real time
        System.out.println(playerX + " " + playerY);
        
        //Interaction
        if ((playerX == 6 && playerY == 4)||(playerX == 7 && playerY == 3)||(playerX == 6 && playerY == 2)||(playerX == 5 && playerY == 3)) {
            if (e.getKeyCode() == KeyEvent.VK_E) {
                setChatVisible(true);
                nextB.setVisible(true);
            }
        }
        
        //First Tree Key (top left)
        if ((playerX == 6 && playerY == 8)||(playerX == 5 && playerY == 9)||(playerX == 6 && playerY == 10)||(playerX == 7 && playerY == 9)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(tree1n == 0) {
                    new Thread(() -> {
                        boolean correct = new chunk1q().startdaquestion1();
                        if (correct) {
                            keyc++;
                            JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            tree1n++;
                        } else {
                            c = (int)(Math.random()*10+1);
                            JOptionPane.showMessageDialog(frame, "Oh no! You were too reckless and the rat was startled!", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            if (c >= 1 && c <=4) {
                                JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                tree1n++; //here
                            }
                            else {
                                JOptionPane.showMessageDialog(frame, "The Rat decided to choose violence.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                new Thread(() -> {
                                    boolean won = new battleMockUp2().startBattleAndWait2();
                                    if (won) {
                                        keyc++;
                                        JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree1n++; //here
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree1n++; //here
                                    }
                                }).start();
                            }
                        }
                    }).start();

                    
                }

           }
        }

        //Second Tree Key (top right)
        if ((playerX == 26 && playerY == 8)||(playerX == 27 && playerY == 7)||(playerX == 26 && playerY == 6)||(playerX == 25 && playerY == 7)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(tree2n == 0) {
                    new Thread(() -> {
                        boolean correct = new chunk2q().startdaquestion2();

                        if (correct) {
                            keyc++;
                            JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            tree2n++;
                        } else {
                            c = (int)(Math.random()*10+1);
                            JOptionPane.showMessageDialog(frame, "Oh no! You were too reckless and the rat was startled!", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            if (c >= 1 && c <=4) {
                                JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                tree2n++; //here
                            }
                            else {
                                JOptionPane.showMessageDialog(frame, "The Rat decided to choose violence.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                new Thread(() -> {
                                    boolean won = new battleMockUp2().startBattleAndWait2();
                                    if (won) {
                                        keyc++;
                                        JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree2n++; //here
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree2n++; //here
                                    }
                                }).start();
                            }
                        }
                    }).start();
                }
            }
        }

        //Third Tree Key (middle left)
        if ((playerX == 6 && playerY == 19)||(playerX == 7 && playerY == 20)||(playerX == 6 && playerY == 21)||(playerX == 5 && playerY == 20)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(tree3n == 0) {
                    new Thread(() -> {
                        boolean correct = new chunk3q().startdaquestion3();

                        if (correct) {
                            keyc++;
                            JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            tree3n++;
                        } else {
                            c = (int)(Math.random()*10+1);
                            JOptionPane.showMessageDialog(frame, "Oh no! You were too reckless and the rat was startled!", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            if (c >= 1 && c <=4) {
                                JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                tree3n++; //here
                            }
                            else {
                                JOptionPane.showMessageDialog(frame, "The Rat decided to choose violence.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                new Thread(() -> {
                                    boolean won = new battleMockUp2().startBattleAndWait2();
                                    if (won) {
                                        keyc++;
                                        JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree3n++; //here
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree3n++; //here
                                    }
                                }).start();
                            }
                        }
                    }).start();
                }
            }
        }

        //Fourth Tree Key (middle middle)
        if ((playerX == 17 && playerY == 18)||(playerX == 16 && playerY == 17)||(playerX == 17 && playerY == 16)||(playerX == 18 && playerY == 17)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(tree4n == 0) {
                    new Thread(() -> {
                        boolean correct = new chunk4q().startdaquestion4(); //

                        if (correct) {
                            keyc++;
                            JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            tree4n++; //
                        } else {
                            c = (int)(Math.random()*10+1);
                            JOptionPane.showMessageDialog(frame, "Oh no! You were too reckless and the rat was startled!", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            if (c >= 1 && c <=4) {
                                JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                tree4n++; //here
                            }
                            else {
                                JOptionPane.showMessageDialog(frame, "The Rat decided to choose violence.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                new Thread(() -> {
                                    boolean won = new battleMockUp2().startBattleAndWait2();
                                    if (won) {
                                        keyc++;
                                        JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree4n++; //here
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree4n++; //here
                                    }
                                }).start();
                            }
                        }
                    }).start();
                }
            }
        }

        //Fifth Tree Key (middle right)
        if ((playerX == 26 && playerY == 19)||(playerX == 27 && playerY == 20)||(playerX == 26 && playerY == 21)||(playerX == 25 && playerY == 20)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(tree5n == 0) {
                    new Thread(() -> {
                        boolean correct = new chunk5q().startdaquestion5(); //

                        if (correct) {
                            keyc++;
                            JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            tree5n++; //
                        } else {
                            c = (int)(Math.random()*10+1);
                            JOptionPane.showMessageDialog(frame, "Oh no! You were too reckless and the rat was startled!", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            if (c >= 1 && c <=4) {
                                JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                tree5n++; //here
                            }
                            else {
                                JOptionPane.showMessageDialog(frame, "The Rat decided to choose violence.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                new Thread(() -> {
                                    boolean won = new battleMockUp2().startBattleAndWait2();
                                    if (won) {
                                        keyc++;
                                        JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree5n++; //here
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree5n++; //here
                                    }
                                }).start();
                            }
                        }
                    }).start();
                }
            }
        }

        //Sixth Tree Key (bottom right)
        if ((playerX == 33 && playerY == 32)||(playerX == 32 && playerY == 33)||(playerX == 33 && playerY == 34)||(playerX == 34 && playerY == 33)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(tree6n == 0) {
                    new Thread(() -> {
                        boolean correct = new chunk6q().startdaquestion6(); //

                        if (correct) {
                            keyc++;
                            JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            tree6n++; //
                        } else {
                            c = (int)(Math.random()*10+1);
                            JOptionPane.showMessageDialog(frame, "Oh no! You were too reckless and the rat was startled!", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            if (c >= 1 && c <=4) {
                                JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                tree6n++; //here
                            }
                            else {
                                JOptionPane.showMessageDialog(frame, "The Rat decided to choose violence.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                new Thread(() -> {
                                    boolean won = new battleMockUp2().startBattleAndWait2();
                                    if (won) {
                                        keyc++;
                                        JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree6n++; //here
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree6n++; //here
                                    }
                                }).start();
                            }
                        }
                    }).start();
                }
            }
        }

        //Seventh Tree Key (bottom middle)
        if ((playerX == 24 && playerY == 27)||(playerX == 23 && playerY == 26)||(playerX == 24 && playerY == 25)||(playerX == 25 && playerY == 26)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(tree7n == 0) {
                    new Thread(() -> {
                        boolean correct = new chunk7q().startdaquestion7(); //

                        if (correct) {
                            keyc++;
                            JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            tree7n++; //
                        } else {
                            c = (int)(Math.random()*10+1);
                            JOptionPane.showMessageDialog(frame, "Oh no! You were too reckless and the rat was startled!", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            if (c >= 1 && c <=4) {
                                JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                tree7n++; //here
                            }
                            else {
                                JOptionPane.showMessageDialog(frame, "The Rat decided to choose violence.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                new Thread(() -> {
                                    boolean won = new battleMockUp2().startBattleAndWait2();
                                    if (won) {
                                        keyc++;
                                        JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree7n++; //here
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree7n++; //here
                                    }
                                }).start();
                            }
                        }
                    }).start();
                }
            }
        }

        //Eighth Tree Key (bottom left)
        if ((playerX == 6 && playerY == 35)||(playerX == 7 && playerY == 34)||(playerX == 8 && playerY == 35)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(tree8n == 0) {
                    new Thread(() -> {
                        boolean correct = new chunk8q().startdaquestion8(); //

                        if (correct) {
                            keyc++;
                            JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            tree8n++; //
                        } else {
                            c = (int)(Math.random()*10+1);
                            JOptionPane.showMessageDialog(frame, "Oh no! You were too reckless and the rat was startled!", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                            if (c >= 1 && c <=4) {
                                JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                tree8n++; //here
                            }
                            else {
                                JOptionPane.showMessageDialog(frame, "The Rat decided to choose violence.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                new Thread(() -> {
                                    boolean won = new battleMockUp2().startBattleAndWait2();
                                    if (won) {
                                        keyc++;
                                        JOptionPane.showMessageDialog(frame, "You found a Rat! " + keyc + "/6 Rats found.", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree8n++; //here
                                    } else {
                                        JOptionPane.showMessageDialog(frame, "The Rat scurried away..", "Oh! A Rat!", JOptionPane.INFORMATION_MESSAGE);
                                        tree8n++; //here
                                    }
                                }).start();
                            }
                        }
                    }).start();
                }
            }
        }

        //Cave enter
        if ((playerX == 18 && playerY == 19)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(keyc >= 6) {
                    enterCave();
                    new Thread(() -> {
                        boolean received = new GroupPD6().GroupPD6Connect();
                        
                        SwingUtilities.invokeLater(() -> {
                            if (received) {
                            exitCave();//
                            chatText.setText("Quick! Open the portal! Just one more step to reach freedom.");
                            nextB.setText("Next");
                            wincon++;
                            } else {
                                frame.dispose();
                            }
                        });
                        
                    }).start();
                } else {
                    JOptionPane.showMessageDialog(frame, "Missing Requirements: " + keyc + "/6 rats", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        
        //Portal Open
        if ((playerX == 7 && playerY == 4)||(playerX == 8 && playerY == 3)||(playerX == 7 && playerY == 2)) {
            if(e.getKeyCode() == KeyEvent.VK_E){
                if(wincon == 1) {
                    JOptionPane.showMessageDialog(frame, "The portal has started to open..", "Information", JOptionPane.INFORMATION_MESSAGE);
                    portall++;
                    wincon++;
                    chatText.setText("Hey..");
                    cct++;
                    nextB.setText("Thank you.");
                    render(snakess, portall);
                }
            }
        }
        
        if ((playerX == 7 && playerY == 4)||(playerX == 8 && playerY == 3)||(playerX == 7 && playerY == 2)) {
            if(e.getKeyCode() == KeyEvent.VK_K){
                if(wincon == 2) {
                    loadPlayerHealth();
                    JOptionPane.showMessageDialog(frame, "You have escaped!, " + ph + "/200 Health Remaining.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new ADMINBLDG().gr10();
                }
            }
        }
    }

    
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exitB){
            setChatVisible(false);
            frame.setFocusable(true);
            frame.setVisible(false);
            frame.setVisible(true);
        } else if (e.getSource()==nextB) {
            if (wincon == 0) {
                if (cht == 0) {
                    chatText.setText("Who am I? I definitely look like an old man.");
                    cht++;
                    nextB.setVisible(true);
                    nextB.setText("Next");
                } else if (cht == 1) {
                    chatText.setText("But in reality, I represent the faint determination and motivation you have inside you.");
                    cht++;
                } else if (cht == 2) {
                    chatText.setText("So, I am here to help you.");
                    cht++;
                } else if (cht == 3) {
                    chatText.setText("You are inside a matrix, a place where a snake lives, and practically rules over");
                    cht++;
                } else if (cht == 4) {
                    chatText.setText("This place has been influenced by your past trauma of being bitten by a snake once in the oval");
                    cht++;
                } else if (cht == 5) {
                    chatText.setText("You ran and.. you tripped.");
                    cht++;
                } else if (cht == 6) {
                    chatText.setText("Causing you to be sent to the hospital.");
                    cht++;
                } else if (cht == 7) {
                    chatText.setText("This snake.. represents misfortune.");
                    cht++;
                } else if (cht == 8) {
                    chatText.setText("This parallel universe, this dark version of the oval,");
                    cht++;
                } else if (cht == 9) {
                    chatText.setText("This place.. it represents, trauma.");
                    cht++;
                } else if (cht == 10) {
                    chatText.setText("Escape this place at all costs!");
                    cht++;
                    nextB.setText("How?");
                } else if (cht == 11) {
                    chatText.setText("In the center of this world, there is a snake, sealed in concrete.");
                    cht++;
                    nextB.setText("Next");
                } else if (cht == 12) {
                    chatText.setText("btaining one of the eyes of the snake will let you open this portal fully, leading you out of this place.");
                    cht++;
                } else if (cht == 13) {
                    chatText.setText("To motivate it to break its own seal, you need to appetize it, by bringing atleast 6 rats.");
                    cht++;
                } else if (cht == 14) {
                    chatText.setText("Rats live inside trees.  You have to find them by interacting with the trees.");
                    cht++;
                } else if (cht == 15) {
                    chatText.setText("Be careful though, as if you are too reckless, the rats may either scurry away, or fight you.");
                    cht++;
                } else if (cht == 16) {
                    chatText.setText("If they do fight you and you get their health low, they might try to flee..");
                    cht++;
                } else if (cht == 17) {
                    chatText.setText("Once you do get 6 rats, enter the cave entrance at the middle of the map.");
                    cht++;
                } else if (cht == 18) {
                    chatText.setText("I will see you there, inside the cave.");
                    cht = 11;
                    nextB.setVisible(false);
                }
            } else if (wincon == 2) {
                if (cct == 1) {
                    chatText.setText("...");
                    cct++;
                    nextB.setText("Next");
                } else if (cct == 2) {
                    chatText.setText("I represent your determination, don't thank me.");
                    cct++;
                    nextB.setText("Next");
                } else if (cct == 3) {
                    chatText.setText("Thank yourself.");
                    cct++;
                    nextB.setVisible(false);
                }
            }
            
        }
    }
}



    
