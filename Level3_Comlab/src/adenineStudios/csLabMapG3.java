package adenineStudios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class csLabMapG3 implements KeyListener{
    JFrame frame;
    
    int mapHeight = 12;
    int mapWidth = 12;
    
    int frameHeight = 800;
    int frameWidth = 800;
    
    ImageIcon computer;
    ImageIcon labTile;
    ImageIcon officeEntity;
    ImageIcon officeTile;
    ImageIcon table;
    ImageIcon wall1;
    ImageIcon wall2;
    ImageIcon wall3;
    ImageIcon wall4;
    ImageIcon wall5;
    ImageIcon wallDiv;
    ImageIcon outerGrounds;
    
    ImageIcon defaultPlayer;

    int iconMode;
    
    JLabel dynamicMap[];
    int startingMap[];
    
    JLabel playerMap[];
    int playerStarting[];
    int playerPos;
    
    Integer[] collisionTiles;
    boolean hasCollision = false;
    
    String[] questions;
    String[] answers;
    
    Integer[] interactionTiles;
    boolean isInteractible = false;
    int interactionResult;
    
    String input;
    
    int questionsLeft = 0;
    Integer[] questionLock;
    
    public csLabMapG3(){
        frame = new JFrame("how are you po sir?");
        
        computer = new ImageIcon("Images/adenineStudios/computer.jpeg");
        labTile = new ImageIcon("Images/adenineStudios/lab_floor.png");
        officeEntity = new ImageIcon("Images/adenineStudios/office_entity.png");
        officeTile = new ImageIcon("Images/adenineStudios/office_floor.png");
        table = new ImageIcon("Images/adenineStudios/table.jpeg");
        wall1 = new ImageIcon("Images/adenineStudios/wall1_top.png");
        wall2 = new ImageIcon("Images/adenineStudios/wall2.png");
        wall3 = new ImageIcon("Images/adenineStudios/wall3.png");
        wall4 = new ImageIcon("Images/adenineStudios/wall4.png");
        wall5 = new ImageIcon("Images/adenineStudios/wall5_down.png");
        wallDiv = new ImageIcon("Images/adenineStudios/wall_div.png");
        outerGrounds = new ImageIcon("Images/adenineStudios/ground.png");
        
        defaultPlayer = new ImageIcon("Images/cat.jpg");
        
        computer = new ImageIcon(computer.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        labTile = new ImageIcon(labTile.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        officeEntity = new ImageIcon(officeEntity.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        officeTile = new ImageIcon(officeTile.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        table = new ImageIcon(table.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall1 = new ImageIcon(wall1.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall2 = new ImageIcon(wall2.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall3 = new ImageIcon(wall3.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall4 = new ImageIcon(wall4.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        wall5 = new ImageIcon(wall5.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        outerGrounds = new ImageIcon(outerGrounds.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        defaultPlayer = new ImageIcon(defaultPlayer.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        playerPos = -1;
        iconMode = 0;
        
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
                    playerMap[x] = new JLabel(defaultPlayer);
                    playerPos = x;
                }
            }
        }
        
        dynamicMap = new JLabel[mapHeight*mapWidth];
        startingMap = new int[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 9, 9, 9, 5, 6, 10, 10, 10, 10, 10, 0,
            0, 9, 9, 9, 5, 6, 6, 6, 6, 6, 6, 0,
            1, 9, 9, 9, 5, 6, 6, 6, 6, 6, 6, 1,
            2, 7, 7, 7, 5, 6, 6, 6, 6, 6, 6, 2,
            3, 7, 7, 7, 5, 6, 6, 6, 6, 6, 6, 3,
            4, 7, 7, 7, 5, 6, 6, 6, 6, 6, 6, 4,
            4, 7, 7, 7, 5, 6, 6, 6, 6, 6, 6, 4,
            4, 7, 7, 7, 5, 6, 6, 6, 6, 6, 6, 4,
            4, 7, 4, 4, 4, 6, 4, 4, 4, 4, 4, 4,
            8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
            8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
        };
        for(int x = 0; x < dynamicMap.length; x++){
            switch(startingMap[x]){
                case 0 -> dynamicMap[x] = new JLabel(wall1);
                case 1 -> dynamicMap[x] = new JLabel(wall2);
                case 2 -> dynamicMap[x] = new JLabel(wall3);
                case 3 -> dynamicMap[x] = new JLabel(wall4);
                case 4 -> dynamicMap[x] = new JLabel(wall5);
                case 5 -> dynamicMap[x] = new JLabel(wallDiv);
                case 6 -> dynamicMap[x] = new JLabel(labTile);
                case 7 -> dynamicMap[x] = new JLabel(officeTile);
                case 8 -> dynamicMap[x] = new JLabel(outerGrounds);
                case 9 -> dynamicMap[x] = new JLabel(officeEntity);
                case 10 -> dynamicMap[x] = new JLabel(computer);
            }
        }
        
        collisionTiles = new Integer[]{0, 1, 2, 3, 4, 5, 9, 10};
        
        interactionTiles = new Integer[]{9, 10};
        
        questionLock = new Integer[]{-1, -1, -1, -1, -1};
        questions = new String[]{
            "______.out.print('Hello World!')", 
            "int num = input._______();", 
            "for(int count = 0; count < subject; count_1)", 
            "Separate answers with a space \nSystem.out.print(\"What is the symbol of Calcium?\"); \nString answer = input.________(); \nif(answer.equals(\"__\")){ \n\tSystem.out.print(\"Correct!\"); \n}", 
            "Separate answers with a space \nSystem.out.print(\"What is the symbol of Barium?\"); \nString answer = input.________(); \nif(answer.equals(\"__\")){ \n\tSystem.out.print(\"Correct!\"); \n}", 
            "Separate answers with a space \nSystem.out.print(\"What is the symbol of Sodium?\"); \nString answer = input.________(); \nif(answer.equals(\"__\")){ \n\tSystem.out.print(\"Correct!\"); \n}", 
            "Separate answers with a space \nSystem.out.print(\"What is the symbol of Lithium?\"); \nString answer = input.________(); \nif(answer.equals(\"__\")){ \n\tSystem.out.print(\"Correct!\"); \n}",
            "Make the message repeat thrice \nfor(int i = 0; i < _; i+1){ \n\t System.out.print(\"Hello\"); \n}",
            "Make r receive an input from the user \nfloat r=sc._______(); \nfloat a=r*r*3.14f; \nSystem.out.print(\"area: \" + a);",
            "Create an array named \"glaggle\" that can receive 5 integers. Add spaces arount the = sign",
            "Make the program look for the first character of the string \nString text=sc.nextLine(); \nchar last=text.charAt(_);"};
        answers = new String[]{"System", "nextInt", "+", "nextLine Ca", "nextLine Ba", "nextLine Na", "nextLine Li", "2", "nextFloat", "Integer[] glaggle = new Integer[5]", "0"};
    }
    
    public void setFrame(){
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
    
    public void challengeStart(){
        int rng = (int)(Math.random()*11);
        
        if(!Arrays.asList(questionLock).contains(rng)){
            input = JOptionPane.showInputDialog(questions[rng]);
            for(int x = 0; x < 4; x++){
                if(questionLock[x] == -1){
                    questionLock[x] = rng;
                    x = 4;
                }
            }
            
            if(input.equals(answers[rng])){
            questionsLeft--;
            JOptionPane.showMessageDialog(null, "Correct! " + questionsLeft + " left!");
            
            if(questionsLeft == 0){
                JOptionPane.showMessageDialog(null, "Go to the OFFICE, and defeat the SERVER");
            }
            }else{
                JOptionPane.showMessageDialog(null, "Try again!");
            }
        }
    }
    
    public void bossFight(){
        if(questionsLeft == 0){
            bossMapG3 m = new bossMapG3();
            m.setFrame();
            frame.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Defeat " + questionsLeft + " rogue PCs to weaken BOX");
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
                        playerMap[playerPos+1].setIcon(defaultPlayer);
                        iconMode = 1;
                    }else{
                        playerMap[playerPos+1].setIcon(defaultPlayer);
                        iconMode = 0;
                    }
                    playerPos++;
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
                        playerMap[playerPos-1].setIcon(defaultPlayer);
                        iconMode = 1;
                    }else{
                        playerMap[playerPos-1].setIcon(defaultPlayer);
                        iconMode = 0;
                    }
                    playerPos--;
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
                        playerMap[playerPos-mapWidth].setIcon(defaultPlayer);
                        iconMode = 1;
                    }else{
                        playerMap[playerPos-mapWidth].setIcon(defaultPlayer);
                        iconMode = 0;
                    }
                    
                    playerPos-=mapWidth;
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
                        playerMap[playerPos+mapWidth].setIcon(defaultPlayer);
                        iconMode = 1;
                    }else{
                        playerMap[playerPos+mapWidth].setIcon(defaultPlayer);
                        iconMode = 0;
                    }
                    
                    playerPos+=mapWidth;
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
                        case 10 -> challengeStart();
                        case 9 -> bossFight();
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
    
    public static void main(String[] args) {
        csLabMapG3 sg=new csLabMapG3();
        sg.setFrame();
    }
}
//jbsrizkewl
//also denzel hontanosas and eoan ablas