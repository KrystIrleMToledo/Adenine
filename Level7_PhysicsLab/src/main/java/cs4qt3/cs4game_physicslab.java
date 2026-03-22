/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package cs4qt3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap; 
import java.util.Map;

public class cs4game_physicslab implements KeyListener{
    
    private ImageIcon loadAndScale(String path, int width, int height){
    ImageIcon icon = new ImageIcon(path);
    Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(scaled);
    }
    
    Map<String, ImageIcon> playerSprites = new HashMap<>();
   
    JFrame frame;
    ImageIcon label;
    ImageIcon floortile;
    ImageIcon stool;
    ImageIcon beigewall;
    ImageIcon bluewall;
    ImageIcon locker;
    ImageIcon board1;
    ImageIcon board2;
    ImageIcon board3;
    ImageIcon table1;
    ImageIcon table2;
    ImageIcon table3;
    ImageIcon door;
    ImageIcon window;
    ImageIcon rbox;
    
    ImageIcon player;
    ImageIcon leftdown;
    ImageIcon rightdown;
    ImageIcon leftup;
    ImageIcon rightup;
    ImageIcon up;
    ImageIcon frontleft;
    ImageIcon backleft;
    ImageIcon frontright;
    ImageIcon backright;
    
    JLabel character[];
    int characterplace[];
    
    int walkstateu = 0;
    int walkstater = 0;
    int walkstatel = 0;
    int walkstated = 0;
    int action = 0;
    
    JLabel playerMap[];
    int playerStarting[];
    int playerPos;
    
    JLabel tiles[];
    int mapLayout[];
    int mapWidth=12;
    int mapHeight=12;
    int frameWidth=1000;
    int frameHeight=1000;
    
    boolean[] enemyTiles;
    
    enum direction {
    up, down, left, right
    }

    direction lastdirection = direction.down;

    Timer idleTimer;
    
    int completion = 0;
    
    private boolean collision(int targetPos) {
        if (targetPos < 0 || targetPos >= mapLayout.length) return false;

        int tile = mapLayout[targetPos];

        boolean walkableTile = tile == 1 || tile == 2 || tile == 12;

        boolean blockedByEnemy = enemyTiles[targetPos];

        return walkableTile && !blockedByEnemy;
    }
        
    private int facing() {
        int row = playerPos / mapWidth;
        int col = playerPos % mapWidth;

        switch (lastdirection) {
            case up:
                if (row > 0) return playerPos - mapWidth;
                break;
            case down:
                if (row < mapHeight - 1) return playerPos + mapWidth;
                break;
            case left:
                if (col > 0) return playerPos - 1;
                break;
            case right:
                if (col < mapWidth - 1) return playerPos + 1;
                break;
        }
        return -1; // out of bounds
    }
    
    String[] questions = {
        "Vectors have both magnitude and?",
        "Force is equal to mass times what?",
        "What is described as an invisible force of attraction between any two objects with mass?"
    };
    
    String[][] options = {
        {"Direction", "Speed", "Force", "Acceleration"},
        {"Velocity", "Acceleration", "Mass", "Weight"},
        {"Tension", "Pulling", "Magnetic", "Gravity"}
    };
    
    int[] answerkey = {0, 1, 3};
    
    public cs4game_physicslab(){
        frame=new JFrame();
        
        enemyTiles = new boolean[mapWidth * mapHeight];
        
        String[] tileNames = {
            "floor.jpg", "stoolonfloor.jpg", "beigewalls.JPG",
            "bluewalls.jpg", "locker.png", "board1.jpg",
            "board2.jpg", "board3.jpg", "table1.jpg",
            "table2.jpg", "table3.jpg", "door.jpg", "window.jpeg"
        };
        ImageIcon[] tileIcons = new ImageIcon[tileNames.length];
        for(int i=0;i<tileNames.length;i++){
            tileIcons[i] = loadAndScale("physicslabtiles/" + tileNames[i], frameWidth/mapWidth, frameHeight/mapHeight);
        }
        floortile = tileIcons[0];
        stool = tileIcons[1];
        beigewall = tileIcons[2];
        bluewall = tileIcons[3];
        locker = tileIcons[4];
        board1 = tileIcons[5];
        board2 = tileIcons[6];
        board3 = tileIcons[7];
        table1 = tileIcons[8];
        table2 = tileIcons[9];
        rbox = tileIcons[9];
        table3 = tileIcons[10];
        door = tileIcons[11];
        window = tileIcons[12];
        
        
        
        playerSprites = new HashMap<>();
        String[] spriteNames = {
            "girl01", "girl02", "girl03", "girl04", "girl05", "girl06", "girl07", 
            "girl08", "girl09", "girl10", "girl11", "girl12", "enemy"
        };

        for (String name : spriteNames) {
            if (name.equals("enemy")) {
                playerSprites.put(name, loadAndScale("physicslabtiles/" + name + ".png", 1300/mapWidth, 2000/mapHeight));
            } else {
                playerSprites.put(name, loadAndScale("physicslabtiles/" + name + ".png", frameWidth/mapWidth, frameHeight/mapHeight));
            }
        } 

       
        playerMap = new JLabel[mapHeight*mapWidth];
        
        playerStarting = new int[]{
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
            0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        };
        for(int x = 0; x < playerMap.length; x++){
            switch(playerStarting[x]){
                case 0 -> playerMap[x] = new JLabel();
                case 1 -> {
                    playerMap[x] = new JLabel(playerSprites.get("girl01"));
                    playerPos = x;
                }
            }
        }
        
        tiles=new JLabel[mapWidth*mapHeight];
        mapLayout=new int[]{
            3,4,12,4,4,4,4,4,4,12,4,3,
            4,1,1,5,5,5,5,5,5,1,1,4,
            4,1,1,1,1,1,1,1,1,1,1,4,
            4,1,1,1,1,1,1,1,1,1,1,4,
            4,6,2,11,2,2,11,2,2,11,2,4,
            4,7,2,9,2,2,9,2,2,9,2,4,
            4,7,2,9,2,2,9,2,2,9,2,4,
            4,7,2,9,2,2,9,2,2,9,2,4,
            4,7,2,9,2,2,9,2,2,9,2,4,
            4,8,2,14,2,2,10,2,2,10,2,4,
            4,1,1,1,1,1,1,1,1,1,1,4,
            3,4,13,13,4,13,13,4,13,13,4,3,
        };
        for(int i=0;i<tiles.length;i++){
            switch(mapLayout[i]){
                case 1: tiles[i]=new JLabel(floortile); break;
                case 2: tiles[i]=new JLabel(stool); break;
                case 3: tiles[i]=new JLabel(beigewall); break;
                case 4: tiles[i]=new JLabel(bluewall); break;
                case 5: tiles[i]=new JLabel(locker); break;
                case 6: tiles[i]=new JLabel(board1); break;
                case 7: tiles[i]=new JLabel(board2); break;
                case 8: tiles[i]=new JLabel(board3); break;
                case 9: tiles[i]=new JLabel(table1); break;
                case 10: tiles[i]=new JLabel(table2); break;
                case 11: tiles[i]=new JLabel(table3); break;
                case 12: tiles[i]=new JLabel(door); break;
                case 13: tiles[i]=new JLabel(window); break;
                case 14: tiles[i]=new JLabel(rbox); break;
            }
        }
        


    }
    
    public void setFrame(){
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth,mapHeight)));
        
        
        int x=0, y=0, w=1, h=1;
        for(int n = 0; n < playerMap.length; n++){
            frame.add(playerMap[n], new Rectangle(x, y, w, h));
            x++;
            if(x%mapWidth == 0){
                x = 0;
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
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(this);
        
        idleTimer = new Timer(300, e -> {
            switch (lastdirection) {
                case up    -> playerMap[playerPos].setIcon(playerSprites.get("girl02"));
                case down  -> playerMap[playerPos].setIcon(playerSprites.get("girl01"));
                case left  -> playerMap[playerPos].setIcon(playerSprites.get("girl03"));
                case right -> playerMap[playerPos].setIcon(playerSprites.get("girl04"));
            }
        });
        idleTimer.setRepeats(false);
    
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_RIGHT ->{
                walkstateu = 0;
                walkstatel = 0;
                walkstated = 0;
                
                int targetPos = playerPos + 1;
                idleTimer.restart();
                if((playerPos+1)%mapWidth != 0 && collision(targetPos)){
                    lastdirection = direction.right;
                    playerMap[playerPos].setIcon(null);
                    playerPos++;
                    switch(walkstater){
                        case 0: playerMap[playerPos].setIcon(playerSprites.get("girl09")); walkstater=1; break;
                        case 1: playerMap[playerPos].setIcon(playerSprites.get("girl11")); walkstater=0; break;
                        
                    }
                }else{
                    lastdirection = direction.right;
                    idleTimer.stop();
                    playerMap[playerPos].setIcon(playerSprites.get("girl04"));
                }
            }
            
            case KeyEvent.VK_LEFT ->{
                walkstateu = 0;
                walkstater = 0;
                walkstated = 0;
                
                int targetPos = playerPos - 1;
                idleTimer.restart();
                if((playerPos-1)%mapWidth != mapWidth-1 && playerPos-1 > -1 && collision(targetPos)){
                    lastdirection = direction.left;
                    playerMap[playerPos].setIcon(null);
                    playerPos--;
                    switch(walkstatel){
                        case 0: playerMap[playerPos].setIcon(playerSprites.get("girl10")); walkstatel=1; break;
                        case 1: playerMap[playerPos].setIcon(playerSprites.get("girl12")); walkstatel=0; break;
                    }
                }else{
                    lastdirection = direction.left;
                    idleTimer.stop();
                    playerMap[playerPos].setIcon(playerSprites.get("girl03"));
                }
            }
            
            case KeyEvent.VK_UP ->{
                walkstatel = 0;
                walkstater = 0;
                walkstated = 0;
                
                int targetPos = playerPos - mapWidth;
                idleTimer.restart();
                if(playerPos-mapWidth > -1 && collision(targetPos)){
                    lastdirection = direction.up;
                    playerMap[playerPos].setIcon(null);
                    playerPos-=mapWidth;
                    switch(walkstateu){
                        case 0: playerMap[playerPos].setIcon(playerSprites.get("girl07")); walkstateu=1; break;
                        case 1: playerMap[playerPos].setIcon(playerSprites.get("girl08")); walkstateu=0; break;
                    }
                }else{
                    lastdirection = direction.up;
                    idleTimer.stop();
                    playerMap[playerPos].setIcon(playerSprites.get("girl02"));
                }
            }
            case KeyEvent.VK_DOWN ->{
                walkstatel = 0;
                walkstater = 0;
                walkstateu = 0;
                
                int targetPos = playerPos + mapWidth;
                idleTimer.restart();
                if(playerPos+mapWidth < mapWidth*mapHeight && collision(targetPos)){
                    lastdirection = direction.down;
                    playerMap[playerPos].setIcon(null);
                    playerPos+=mapWidth;
                    switch(walkstated){
                        case 0: playerMap[playerPos].setIcon(playerSprites.get("girl05")); walkstated=1; break;
                        case 1: playerMap[playerPos].setIcon(playerSprites.get("girl06")); walkstated=0; break;
                    }
                }else{
                    lastdirection = direction.down;
                    idleTimer.stop();
                    playerMap[playerPos].setIcon(playerSprites.get("girl01"));
                }
            }
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E) {
            int target = facing();
            System.out.println("Facing index: " + target + ", tile type: " + mapLayout[target]);

            if (target != -1 && mapLayout[target] == 14 && action == 0) {
                JOptionPane.showMessageDialog(frame, "Correct Box! Time to get a move on!", "Message", JOptionPane.INFORMATION_MESSAGE);
                completion = 1;
                action++;
            } else if (target != -1 && mapLayout[target] == 10 && action == 0) {
                JOptionPane.showMessageDialog(frame, "Ruh oh. Something popped out of the locker. That must've been the wrong box. No way out other than facing it. It might have a few questions for us.", "Message", JOptionPane.INFORMATION_MESSAGE);
                action++;
                playerMap[29].setIcon(playerSprites.get("enemy"));
                enemyTiles[29] = true;
            }

            if (enemyTiles[target] && completion == 0) {
                int score = 0;
                for (int i = 0; i < questions.length; i++) {
                    int answer = -1;
                    boolean valid = false;

                    while (!valid) {
                        try {
                            answer = JOptionPane.showOptionDialog(
                                    frame,
                                    questions[i],
                                    "Quiz Question",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options[i],
                                    options[i][0]
                            );

                            if (answer == JOptionPane.CLOSED_OPTION) {
                                throw new Exception("Invalid input. Please choose a valid answer.");
                            }

                            valid = true;

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, ex.getMessage());
                        }
                    }

                    if (answer == answerkey[i]) {
                        score++;
                    }
                }
                
                if (score == 3) {
                    JOptionPane.showMessageDialog(frame, "You answered all 3 questions correct! You may leave the Physics Lab.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    completion = 1;
                } else {
                    JOptionPane.showMessageDialog(frame, "WRONG! You failed to answer all 3 questions correct. Try again.", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (enemyTiles[target]) {
                JOptionPane.showMessageDialog(frame, "You're done here already. Leave.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
