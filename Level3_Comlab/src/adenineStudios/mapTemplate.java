package adenineStudios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class mapTemplate implements KeyListener{
    JFrame frame;
    
    int mapHeight = 12;
    int mapWidth = 12;
    
    int frameHeight = 800;
    int frameWidth = 800;
    
    ImageIcon defaultTexture;
    ImageIcon secondTexture;
    
    ImageIcon defaultPlayer;
    ImageIcon secondPlayer;

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
    
    public mapTemplate(){
        frame = new JFrame("how are you po sir?");
        
        defaultTexture = new ImageIcon("Images/mugshot.jpg");
        secondTexture = new ImageIcon("Images/coal.png");
        
        defaultPlayer = new ImageIcon("Images/cat.jpg");
        secondPlayer = new ImageIcon("Images/dog.jpg");
        
        defaultTexture = new ImageIcon(defaultTexture.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        secondTexture = new ImageIcon(secondTexture.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        defaultPlayer = new ImageIcon(defaultPlayer.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        secondPlayer = new ImageIcon(secondPlayer.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
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
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
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
                case 1 -> dynamicMap[x] = new JLabel(secondTexture);
            }
        }
        
        collisionTiles = new Integer[]{1};
        
        interactionTiles = new Integer[]{1};
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
                        playerMap[playerPos+1].setIcon(secondPlayer);
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
                        playerMap[playerPos-1].setIcon(secondPlayer);
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
                        playerMap[playerPos-mapWidth].setIcon(secondPlayer);
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
                        playerMap[playerPos+mapWidth].setIcon(secondPlayer);
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
                        case 1 -> System.out.print("here");
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
        mapTemplate sg=new mapTemplate();
        sg.setFrame();
    }
}
//jbsrizkewl
//also denzel hontanosas and eoan ablas