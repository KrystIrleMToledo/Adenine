package PD8TOTAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ADMINBLDG implements KeyListener {
   
    class InvalidKeyException extends Exception {
        public InvalidKeyException(String message) {
            super(message);
        }
    }

    JFrame frame;

    // Environment
    ImageIcon tile, deskfront, deskback, deskleft, deskright;
    ImageIcon chairfront, chairback, chairleft, chairright;
    ImageIcon waterfront, waterback;
    ImageIcon paper, spawner, pylon1, pylon2, pylon3, pylon4;
    ImageIcon tambaltile; // NEW

    // Player animation
    ImageIcon pU1, pU2, pD1, pD2, pL1, pL2, pR1, pR2;

    JLabel[] tiles;
    JLabel[] character;

    int[] mapLayout;
    int[] charPlace;

    int mapWidth = 12;
    int mapHeight = 12;
    int frameWidth = 800;
    int frameHeight = 800;

    int characterPosition = -1;
    int characterMode = 0;
    int facing = 1; // 0=Up,1=Down,2=Left,3=Right
   
    int condition = 0;
    String playerType = "girl"; // default
   
    private void loadPlayerType() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("substitute.txt"));
            String line = br.readLine();
            if (line != null) {
                playerType = line.trim().toLowerCase();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("substitute.txt not found, defaulting to girl");
            playerType = "girl";
        }
    }

    public ADMINBLDG() {
        frame = new JFrame("ADMIN BLDG");
       
        loadPlayerType();

        loadSprites();
        initCharacter();
        initMap();
    }

    private void loadSprites() {
        tile = load("Images/gr10/tile.png");
        deskfront = load("Images/gr10/deskfront.png");
        deskback = load("Images/gr10/deskback.png");
        deskleft = load("Images/gr10/deskleft.png");
        deskright = load("Images/gr10/deskright.png");

        chairfront = load("Images/gr10/chairfront.png");
        chairback = load("Images/gr10/chairback.png");
        chairleft = load("Images/gr10/chairleft.png");
        chairright = load("Images/gr10/chairright.png");

        waterfront = load("Images/gr10/waterfront.png");
        waterback = load("Images/gr10/waterback.png");

        paper = load("Images/gr10/paper.png");
        spawner = load("Images/gr10/spawner.png");

        pylon1 = load("Images/gr10/pylon1.png");
        pylon2 = load("Images/gr10/pylon2.png");
        pylon3 = load("Images/gr10/pylon3.png");
        pylon4 = load("Images/gr10/pylon4.png");

        tambaltile = load("Images/gr10/tambaltile.png"); // NEW

        if (playerType.equals("girl")) {
            pU1 = load("Images/gr10/girlwalk5.png");
            pU2 = load("Images/gr10/girlwalk6.png");
            pD1 = load("Images/gr10/girlwalk1.png");
            pD2 = load("Images/gr10/girlwalk2.png");
            pL1 = load("Images/gr10/girlwalk4.png");
            pL2 = load("Images/gr10/girlwalk8.png");
            pR1 = load("Images/gr10/girlwalk3.png");
            pR2 = load("Images/gr10/girlwalk7.png");
        } else {
            pU1 = load("Images/gr10/boywalk5.png");
            pU2 = load("Images/gr10/boywalk6.png");
            pD1 = load("Images/gr10/boywalk1.png");
            pD2 = load("Images/gr10/boywalk2.png");
            pL1 = load("Images/gr10/boywalk4.png");
            pL2 = load("Images/gr10/boywalk8.png");
            pR1 = load("Images/gr10/boywalk3.png");
            pR2 = load("Images/gr10/boywalk7.png");
        }
    }

    private ImageIcon load(String path) {
        return new ImageIcon(
            new ImageIcon(path).getImage()
                .getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT)
        );
    }

    private void initCharacter() {
        character = new JLabel[mapWidth * mapHeight];
        charPlace = new int[mapWidth * mapHeight];

        charPlace[10*mapWidth + 5] = 1; // starting position

        for(int i=0;i<character.length;i++){
            if(charPlace[i]==1){
                character[i]=new JLabel(pR1);
                characterPosition=i;
            } else {
                character[i]=new JLabel();
            }
        }
    }

    private void initMap() {
        try {
            tiles = new JLabel[mapWidth*mapHeight];

            // 0=walkable tile, 1-10=solid, 11=paper, 12=spawner, 13-16=pylons, 17=tambaltile
            mapLayout = new int[]{
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,9,5,4,0,1,6,1,11,3,7,0,
                0,3,5,4,11,1,6,1,11,3,7,0,
                0,11,0,11,11,0,13,0,0,11,11,0,
                0,1,6,1,0,14,12,15,0,1,6,0,
                0,1,10,1,0,0,16,0,11,1,5,0,
                0,0,11,11,11,0,0,0,11,11,0,0,
                0,3,10,4,11,1,6,1,11,3,9,0,
                0,3,8,4,11,1,5,1,11,3,8,0,
                0,0,0,11,11,0,0,0,11,0,0,0,
                0,9,10,0,0,11,0,0,0,9,10,0,
                0,0,0,0,0,0,0,0,0,0,0,0
            };
            if(mapLayout.length != mapWidth * mapHeight){
                throw new ArrayIndexOutOfBoundsException(
                    "Map is either too big or too small than the given size"
                );
            }
            for(int i=0;i<tiles.length;i++){
                tiles[i] = new JLabel(getTileIcon(mapLayout[i]));
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(frame,"Map is either too big or too small than the given size","Error",JOptionPane.ERROR_MESSAGE);
        }

       
    }

    private ImageIcon getTileIcon(int id){
        switch(id){
            case 1: return deskfront;
            case 2: return deskback;
            case 3: return deskleft;
            case 4: return deskright;
            case 5: return chairfront;
            case 6: return chairback;
            case 7: return chairleft;
            case 8: return chairright;
            case 9: return waterfront;
            case 10:return waterback;
            case 11:return paper;
            case 12:return spawner;
            case 13:return pylon1;
            case 14:return pylon2;
            case 15:return pylon3;
            case 16:return pylon4;
            case 17:return tambaltile; // NEW
            default:return tile;
        }
    }

    public void test(){
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth,mapHeight)));

        int x=0,y=0;
        for(JLabel c:character){
            frame.add(c,new Rectangle(x++,y,1,1));
            if(x==mapWidth){x=0;y++;}
        }

        x=0;y=0;
        for(JLabel t:tiles){
            frame.add(t,new Rectangle(x++,y,1,1));
            if(x==mapWidth){x=0;y++;}
        }

        frame.setSize(frameWidth,frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e){
        int next=characterPosition;
       
        try{
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {

                throw new InvalidKeyException("USE WASD KEYS ONLY");
            }
            // Movement
            if(e.getKeyCode()==KeyEvent.VK_D){
                next++;facing=3;
                System.out.println(characterPosition);
            }
            if(e.getKeyCode()==KeyEvent.VK_A){
                next--;facing=2;
                System.out.println(characterPosition);
            }
            if(e.getKeyCode()==KeyEvent.VK_S){
                next+=mapWidth;facing=1;
                System.out.println(characterPosition);
            }
            if(e.getKeyCode()==KeyEvent.VK_W){
                next-=mapWidth;facing=0;
                System.out.println(characterPosition);
            }
        }
        catch (InvalidKeyException ex){
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }

       
       

        // Only walkable tiles
        if(next>=0 && next<mapLayout.length && mapLayout[next]==0){
            character[characterPosition].setIcon(null);
            characterPosition=next;

            // Determine correct animation frame FIRST
            ImageIcon icon;

            if (facing == 0) { // UP
                icon = (characterMode == 0) ? pU1 : pU2;
            }
            else if (facing == 1) { // DOWN
                icon = (characterMode == 0) ? pD1 : pD2;
            }
            else if (facing == 2) { // LEFT
                icon = (characterMode == 0) ? pL1 : pL2;
            }
            else { // RIGHT
                icon = (characterMode == 0) ? pR1 : pR2;
            }

            // Only move if walkable
            if(next>=0 && next<mapLayout.length && mapLayout[next]==0){
                character[characterPosition].setIcon(null);
                characterPosition = next;
                characterMode ^= 1; // toggle ONLY when moving
            }

            // ALWAYS update sprite (even if blocked)
            character[characterPosition].setIcon(icon);
        }

        // Interaction key
        if(e.getKeyCode()==KeyEvent.VK_E){
            if (condition == 1) {
                //!!!!
                
                
                
                new Thread(() -> {
                                    boolean won = new battleMockUp4().startBattleAndWait4();
                                    if (won) {
                                        JOptionPane.showMessageDialog(frame, "You Won!", "Information", JOptionPane.INFORMATION_MESSAGE);
                                        frame.dispose();
                                    } else {
                                        frame.dispose(); // close current game

                                        SwingUtilities.invokeLater(() -> {
                                            new ADMINBLDG().test(); // start fresh game
                                        });
                                    }
                                }).start();
            }
            else {
                interact();
            }
        }
    }

    private void interact(){
        int target=characterPosition;

        // Determine tile in front of player
        if(facing==0) target-=mapWidth;
        if(facing==1) target+=mapWidth;
        if(facing==2) target-=1;
        if(facing==3) target+=1;

        if(target<0 || target>=mapLayout.length) return;

        int id=mapLayout[target];

        // Breakable: paper or pylons
        if(id==11 || (id>=13 && id<=16)){
            mapLayout[target]=0;
            tiles[target].setIcon(tile);
            return;
        }

        // Spawner breakable only if all pylons broken
        if(id==12){
            boolean allBroken=true;
            for(int i=0;i<mapLayout.length;i++){
                if(mapLayout[i]>=13 && mapLayout[i]<=16){allBroken=false;break;}
            }
            if(allBroken){
                mapLayout[target]=17; // replaced with tambaltile (impassable)
                tiles[target].setIcon(tambaltile);
                condition++;
            }
        }
    }

    @Override public void keyTyped(KeyEvent e){}
    @Override public void keyReleased(KeyEvent e){}
    public boolean gr10() {
        SwingUtilities.invokeLater(this::test);
        return true;
    }
    public static void main(String[] args){
        ADMINBLDG game=new ADMINBLDG();
        game.test();
    }
}
