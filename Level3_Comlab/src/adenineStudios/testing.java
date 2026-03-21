package adenineStudios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.awt.Image;

public final class testing implements KeyListener{
    JFrame frame;
    
    int mapHeight = 12;
    int mapWidth = 12;
    
    int frameHeight = 800;
    int frameWidth = 800;
    
    ImageIcon defaultTexture;
    ImageIcon secondTexture;
    
    ImageIcon defaultPlayer;

    int iconMode;
    
    JLabel dynamicMap[];
    int currentMap[];
    int map1[];
    int map2[];
    
    JLabel playerMap[];
    int playerStarting[];
    int playerPos;
    
    Integer[] collisionTiles;
    boolean hasCollision = false;
    
    public testing(){
        frame = new JFrame("how are you po sir?");
        
        defaultTexture = new ImageIcon("Images/dog.jpg");
        secondTexture = new ImageIcon("Images/coal.png");
        
        defaultPlayer = new ImageIcon("Images/cat.jpg");
        
        defaultTexture = new ImageIcon(defaultTexture.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        defaultPlayer = new ImageIcon(defaultPlayer.getImage().getScaledInstance((frameWidth/mapWidth), (frameHeight/mapHeight), Image.SCALE_DEFAULT));
        
        playerPos = -1;
        iconMode = 0;
        
        //add player here
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
        
        //shape map here
        dynamicMap = new JLabel[mapHeight*mapWidth];
        map1 = new int[]{
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
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        };
        map2 = new int[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        };
        
        currentMap = map1;
        setDynamicMap();
        
        collisionTiles = new Integer[]{};
    }
    
    public void setDynamicMap(){
        for(int x = 0; x < dynamicMap.length; x++){
            switch(currentMap[x]){
                case 0 -> dynamicMap[x] = new JLabel(defaultTexture);
                case 1 -> dynamicMap[x] = new JLabel(secondTexture);
            }
        }
    }
    
    public void setFrame(){
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth, mapHeight)));
        
        //place player on map
        int x=0, y=0, w=1, h=1;
        for(int n = 0; n < playerMap.length; n++){
            frame.add(playerMap[n], new Rectangle(x, y, w, h));
            x++;
            if(x%mapWidth == 0){
                x = 0;
                y++;
            }
        }
        
        //place textures on map
        x=0; y=0; w=1; h=1;
        for(int n = 0; n < dynamicMap.length; n++){
            frame.add(dynamicMap[n], new Rectangle(x, y, w, h));
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
                    hasCollision = Arrays.asList(collisionTiles).contains(currentMap[playerPos+1]);
                }catch (Exception k){
                    System.out.println("handled");
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
                
                if((playerPos+1)%mapWidth == 0){
                    currentMap = map2;
                    setDynamicMap();
                    setFrame();
                }
            }
            
            case KeyEvent.VK_LEFT ->{
                try{
                    hasCollision = Arrays.asList(collisionTiles).contains(currentMap[playerPos-1]);
                }catch (Exception k){
                    System.out.println("handled");
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
                    hasCollision = Arrays.asList(collisionTiles).contains(currentMap[playerPos-mapWidth]);
                }catch (Exception k){
                    System.out.println("handled");
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
                    hasCollision = Arrays.asList(collisionTiles).contains(currentMap[playerPos+mapWidth]);
                }catch (Exception k){
                    System.out.println("handled");
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
            
            case KeyEvent.VK_X ->{
                frame.setVisible(false);
                frame.dispose();
                currentMap = map2;
                setDynamicMap();
                setFrame();
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
        testing sg=new testing();
        sg.setFrame();
    }    
}
//jbsrizkewl
//also denzel hontanosas and eoan ablas