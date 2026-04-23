package PD8TOTAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class bossMapG3 implements KeyListener{
    boolean loaded = false;
    JFrame frame;
    
    private int forComp = 0;
    
    public int getForComp(){
        return forComp;
    }
    
    public void setForComp(int newForComp){
        this.forComp = newForComp;
    }
    
    int mapHeight = 12;
    int mapWidth = 12;
    
    int frameHeight = 800;
    int frameWidth = 800;
    
    ImageIcon wall1;
    ImageIcon wall2;
    ImageIcon wall3;
    ImageIcon wall4;
    ImageIcon boss;
    
    ImageIcon defaultPlayer;
    ImageIcon defaultPlayerR;
    ImageIcon defaultPlayerL;
    ImageIcon defaultPlayerU;
    
    ImageIcon walkPlayer;
    ImageIcon walkPlayerR;
    ImageIcon walkPlayerL;
    ImageIcon walkPlayerU;
    ImageIcon walkPlayerAlt;
    ImageIcon walkPlayerAltR;
    ImageIcon walkPlayerAltL;
    ImageIcon walkPlayerAltU;

    int iconMode;
    
    JLabel dynamicMap[];
    int startingMap[];
    
    JLabel playerMap[];
    int playerStarting[];
    int playerPos;
    
    Integer[] collisionTiles;
    boolean hasCollision = false;
    
    Integer[] interactionTiles;
    boolean isInteractible = false;
    int interactionResult;
    int wincon = 0;
    
    public bossMapG3(){
        frame = new JFrame("boss room");
        
        wall1 = new ImageIcon("Images/adenineStudios/bossStuff/Puzzle1.png");
        wall2 = new ImageIcon("Images/adenineStudios/bossStuff/Puzzle2.png");
        wall3 = new ImageIcon("Images/adenineStudios/bossStuff/Puzzle3.png");
        wall4 = new ImageIcon("Images/adenineStudios/bossStuff/Puzzle4.png");
        boss = new ImageIcon("Images/adenineStudios/bossStuff/boss.png");
        
        defaultPlayer = new ImageIcon("Images/adenineStudios/gamesprites/boyidle1.png");
        defaultPlayerR = new ImageIcon("Images/adenineStudios/gamesprites/boyidle3.png");
        defaultPlayerL = new ImageIcon("Images/adenineStudios/gamesprites/boyidle2.png");
        defaultPlayerU = new ImageIcon("Images/adenineStudios/gamesprites/boyidle4.png");
        
        walkPlayer = new ImageIcon("Images/adenineStudios/gamesprites/boywalk1.png");
        walkPlayerR = new ImageIcon("Images/adenineStudios/gamesprites/boywalk3.png");
        walkPlayerL = new ImageIcon("Images/adenineStudios/gamesprites/boywalk4.png");
        walkPlayerU = new ImageIcon("Images/adenineStudios/gamesprites/boywalk5.png");
        walkPlayerAlt = new ImageIcon("Images/adenineStudios/gamesprites/boywalk2.png");
        walkPlayerAltR = new ImageIcon("Images/adenineStudios/gamesprites/boywalk7.png");
        walkPlayerAltL = new ImageIcon("Images/adenineStudios/gamesprites/boywalk8.png");
        walkPlayerAltU = new ImageIcon("Images/adenineStudios/gamesprites/boywalk6.png");
        
        wall1 = new ImageIcon(wall1.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall2 = new ImageIcon(wall2.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall3 = new ImageIcon(wall3.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall4 = new ImageIcon(wall4.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        boss = new ImageIcon(boss.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        defaultPlayer = new ImageIcon(defaultPlayer.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        defaultPlayerR = new ImageIcon(defaultPlayerR.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        defaultPlayerL = new ImageIcon(defaultPlayerL.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        defaultPlayerU = new ImageIcon(defaultPlayerU.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        walkPlayer = new ImageIcon(walkPlayer.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        walkPlayerR = new ImageIcon(walkPlayerR.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        walkPlayerL = new ImageIcon(walkPlayerL.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        walkPlayerU = new ImageIcon(walkPlayerU.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        walkPlayerAlt = new ImageIcon(walkPlayerAlt.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        walkPlayerAltR = new ImageIcon(walkPlayerAltR.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        walkPlayerAltL = new ImageIcon(walkPlayerAltL.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        walkPlayerAltU = new ImageIcon(walkPlayerAltU.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        playerPos = -1;
        iconMode = 0;
        
        playerMap = new JLabel[mapHeight*mapWidth];
        playerStarting = new int[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
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
        for(int x = 0; x < playerMap.length; x++){
            switch(playerStarting[x]){
                case 0 -> playerMap[x] = new JLabel();
                case 1 -> {
                    playerMap[x] = new JLabel(defaultPlayer);
                    playerPos = x;
                }
            }
        }
        
        dynamicMap = new JLabel[mapHeight*mapWidth];
        startingMap = new int[]{
            0, 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 3,
            0, 4, 4, 4, 0, 0, 1, 1, 2, 2, 3, 3,
            0, 4, 4, 4, 4, 0, 0, 1, 1, 2, 2, 3,
            0, 4, 4, 4, 4, 4, 0, 0, 1, 1, 2, 2,
            0, 0, 4, 4, 4, 4, 4, 0, 0, 1, 1, 2,
            1, 0, 0, 4, 4, 4, 4, 4, 0, 0, 1, 1,
            1, 1, 0, 0, 4, 4, 4, 4, 4, 0, 0, 1,
            2, 1, 1, 0, 0, 4, 4, 4, 4, 4, 0, 0,
            2, 2, 1, 1, 0, 0, 4, 4, 4, 4, 4, 0,
            3, 2, 2, 1, 1, 0, 0, 4, 4, 4, 4, 0,
            3, 3, 2, 2, 1, 1, 0, 0, 4, 4, 5, 0,
            3, 3, 3, 2, 2, 1, 1, 0, 0, 0, 0, 0,
        };
        for(int x = 0; x < dynamicMap.length; x++){
            switch(startingMap[x]){
                case 0 -> dynamicMap[x] = new JLabel(wall1);
                case 1 -> dynamicMap[x] = new JLabel(wall2);
                case 2 -> dynamicMap[x] = new JLabel(wall3);
                case 3 -> dynamicMap[x] = new JLabel(wall4);
                case 4 ->{
                    dynamicMap[x] = new JLabel();
                    dynamicMap[x].setForeground(Color.red);
                    dynamicMap[x].setBackground(Color.red);
                }
                case 5 -> dynamicMap[x] = new JLabel(boss);
            }
        }
        
        collisionTiles = new Integer[]{0, 1, 2, 3, 5};
        
        interactionTiles = new Integer[]{5};
    }
    
    public void setFrame(){
        saveHandling();
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth, mapHeight)));
        
        //place player on map
        int x=0, y=0, w=1, h=1;
        for (JLabel playerMap1 : playerMap) {
            frame.add(playerMap1, new Rectangle(x, y, w, h));
            x++;
            if(x%mapWidth == 0){
                x = 0;
                y++;
            }
        }
        
        //place textures on map
        x=0; y=0; w=1; h=1;
        for (JLabel dynamicMap1 : dynamicMap) {
            frame.add(dynamicMap1, new Rectangle(x, y, w, h));
            x++;
            if(x%mapWidth == 0){
                x = 0;
                y++;
            }
        }
        
        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        
        frame.addKeyListener(this);
    }
    
    public void saveHandling(){
        try{
            if(loaded == false){
                FileReader spriteRead = new FileReader("substitute.txt");
                try (BufferedReader spriteReader
                        = new BufferedReader(spriteRead)) {
                    String spriteVal = spriteReader.readLine();
                    if(!spriteVal.equals("boy")){
                        System.out.print("a");
                        defaultPlayer = new ImageIcon("Images/adenineStudios/gamesprites/girlidle1.png");
                        defaultPlayerR = new ImageIcon("Images/adenineStudios/gamesprites/girlidle4.png");
                        defaultPlayerL = new ImageIcon("Images/adenineStudios/gamesprites/girlidle3.png");
                        defaultPlayerU = new ImageIcon("Images/adenineStudios/gamesprites/girlidle2.png");

                        walkPlayer = new ImageIcon("Images/adenineStudios/gamesprites/girlwalk1.png");
                        walkPlayerR = new ImageIcon("Images/adenineStudios/gamesprites/girlwalk5.png");
                        walkPlayerL = new ImageIcon("Images/adenineStudios/gamesprites/girlwalk6.png");
                        walkPlayerU = new ImageIcon("Images/adenineStudios/gamesprites/girlwalk3.png");
                        walkPlayerAlt = new ImageIcon("Images/adenineStudios/gamesprites/girlwalk2.png");
                        walkPlayerAltR = new ImageIcon("Images/adenineStudios/gamesprites/girlwalk7.png");
                        walkPlayerAltL = new ImageIcon("Images/adenineStudios/gamesprites/girlwalk8.png");
                        walkPlayerAltU = new ImageIcon("Images/adenineStudios/gamesprites/girlwalk4.png");
                        
                        defaultPlayer = new ImageIcon(defaultPlayer.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        defaultPlayerR = new ImageIcon(defaultPlayerR.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        defaultPlayerL = new ImageIcon(defaultPlayerL.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        defaultPlayerU = new ImageIcon(defaultPlayerU.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));

                        walkPlayer = new ImageIcon(walkPlayer.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        walkPlayerR = new ImageIcon(walkPlayerR.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        walkPlayerL = new ImageIcon(walkPlayerL.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        walkPlayerU = new ImageIcon(walkPlayerU.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));

                        walkPlayerAlt = new ImageIcon(walkPlayerAlt.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        walkPlayerAltR = new ImageIcon(walkPlayerAltR.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        walkPlayerAltL = new ImageIcon(walkPlayerAltL.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        walkPlayerAltU = new ImageIcon(walkPlayerAltU.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
                        
                        for(int x = 0; x < playerMap.length; x++){
                            switch(playerStarting[x]){
                                case 0 -> playerMap[x] = new JLabel();
                                case 1 -> {
                                    playerMap[x] = new JLabel(defaultPlayer);
                                    playerPos = x;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e){
            System.out.print("IO error, save may be baddd");
        } catch (NumberFormatException e){
            System.out.print("idk wtf is happening here ngl");
        }
    }
    
    public void bossFight(){
        if (wincon == 0) {
            new Thread(() -> {
                        boolean won = new battleMockUp().startBattleAndWait();
                        if (won) {
                            wincon++;
                        } else {             
                            frame.dispose();
                        }
                    }).start();
        } else {
            frame.dispose();
            new Q4_PD7().gr4();
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_RIGHT ->{
                try{
                    hasCollision = Arrays.asList(collisionTiles).contains(startingMap[playerPos+1]);
                }catch (Exception k){
                    System.out.println("collider error");
                }
                
                if((playerPos+1)%mapWidth != 0 && hasCollision == false){
                    playerMap[playerPos].setIcon(null);
                    
                    if(iconMode == 0){
                        playerMap[playerPos+1].setIcon(walkPlayerR);
                        iconMode = 1;
                    }else{
                        playerMap[playerPos+1].setIcon(walkPlayerAltR);
                        iconMode = 0;
                    }
                    playerPos++;
                }else{
                    playerMap[playerPos].setIcon(defaultPlayerR);
                }
            }
            
            case KeyEvent.VK_LEFT ->{
                try{
                    hasCollision = Arrays.asList(collisionTiles).contains(startingMap[playerPos-1]);
                }catch (Exception k){
                    System.out.println("collider error");
                }
                
                if((playerPos-1)%mapWidth != mapWidth-1 && playerPos-1 > -1 && hasCollision == false){
                    playerMap[playerPos].setIcon(null);
                
                    if(iconMode == 0){
                        playerMap[playerPos-1].setIcon(walkPlayerL);
                        iconMode = 1;
                    }else{
                        playerMap[playerPos-1].setIcon(walkPlayerAltL);
                        iconMode = 0;
                    }
                    playerPos--;
                }else{
                    playerMap[playerPos].setIcon(defaultPlayerL);
                }
            }
            
            case KeyEvent.VK_UP ->{
                try{
                    hasCollision = Arrays.asList(collisionTiles).contains(startingMap[playerPos-mapWidth]);
                }catch (Exception k){
                    System.out.println("collider error");
                }
                
                if(playerPos-mapWidth > -1 && hasCollision == false){
                    playerMap[playerPos].setIcon(null);
                        
                    if(iconMode == 0){
                        playerMap[playerPos-mapWidth].setIcon(walkPlayerU);
                        iconMode = 1;
                    }else{
                        playerMap[playerPos-mapWidth].setIcon(walkPlayerAltU);
                        iconMode = 0;
                    }
                    
                    playerPos-=mapWidth;
                }else{
                    playerMap[playerPos].setIcon(defaultPlayerU);
                }
            }
            
            case KeyEvent.VK_DOWN ->{
                try{
                    hasCollision = Arrays.asList(collisionTiles).contains(startingMap[playerPos+mapWidth]);
                }catch (Exception k){
                    System.out.println("collider error");
                }
                    
                if(playerPos+mapWidth < mapWidth*mapHeight && hasCollision == false){
                    playerMap[playerPos].setIcon(null);
                
                    if(iconMode == 0){
                        playerMap[playerPos+mapWidth].setIcon(walkPlayer);
                        iconMode = 1;
                    }else{
                        playerMap[playerPos+mapWidth].setIcon(walkPlayerAlt);
                        iconMode = 0;
                    }
                    
                    playerPos+=mapWidth;
                }else{
                    playerMap[playerPos].setIcon(defaultPlayer);
                }
            }
            
            case KeyEvent.VK_Z ->{
                try{
                    if(Arrays.asList(interactionTiles).contains(startingMap[playerPos+1])){
                        interactionResult = startingMap[playerPos+1];
                        isInteractible = true;
                    }else if(Arrays.asList(interactionTiles).contains(startingMap[playerPos-1])){
                        interactionResult = startingMap[playerPos-1];
                        isInteractible = true;
                    }else if(Arrays.asList(interactionTiles).contains(startingMap[playerPos+mapWidth])){
                        interactionResult = startingMap[playerPos+mapWidth];
                        isInteractible = true;
                    }else if(Arrays.asList(interactionTiles).contains(startingMap[playerPos-mapWidth])){
                        interactionResult = startingMap[playerPos-mapWidth];
                        isInteractible = true;
                    }else{
                        isInteractible = false;
                    }
                }catch (Exception k){
                    System.out.println("interaction error");
                }
                
                if(isInteractible == true){
                    switch(interactionResult){
                        case 5 -> bossFight();
                    }
                }
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

class forSub extends bossMapG3{
    
    public static void main(String[] args) {
        forSub next = new forSub();
        bossMapG3 sg=new bossMapG3();
        sg.setFrame();
        
    }
}
//jbsrizkewl
//also denzel hontanosas and eoan ablas