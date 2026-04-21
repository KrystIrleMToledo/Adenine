package Quarter4;

import Quarter2.GraphPaperLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;

public class Group6_PD8 implements KeyListener {
    JFrame frame;

    ImageIcon img1,img2,img3,img4,img5,img6,img7,img8,
            img9,img10,img11,img12,img13,img14,img15,img16,
            img17,img18,img19,img20,img21,img22,img23,img24,
            img25,img26,img27,img28,img29,img30,img31,img32,
            img33,img34,img35,img36,img37,img38,img39,img40,
            img41,img42,img43,img44,img45,img46,img47,img48,
            img49,img50,img51,img52,img53,img54,img55,img56,
            img57,img58,img59,img60,img61,img62,img63,img64,
            smallflower,bigflower;

    JLabel tiles[], character[], mapLayout;

    int characterPlace[];
    int mapWidth=8, mapHeight=8;
    int frameWidth=450, frameHeight=450;
    ImageIcon playerIdle1, playerIdle2, playerIdle3, playerIdle4;
    ImageIcon playerWalk1, playerWalk2, playerWalk3, playerWalk4;
    ImageIcon playerWalk5, playerWalk6, playerWalk7, playerWalk8;

    int characterPosition;
    int characterMode;
    ImageIcon playerIcon;

    // GAME DATA
    long startTime;
    long fastestTime = Long.MAX_VALUE;
    int attempts = 0;
    int lives = 3;

    final String FILE_NAME="saveData.txt";

    Player player;
    Flower flower;
    private boolean finished;
    Integer[] collisionTiles;
    boolean hasCollision = false;
    boolean quizFinished = false;
    boolean chestOpened = false;
    String playerType = "boy";;
    
    
    
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
    private void loadPlayerSprites() {
        String prefix;

        if (playerType.equals("girl")) {
            prefix = "girl";
        } else {
            prefix = "boy"; // default
        }

        // IDLE
        playerIdle1 = new ImageIcon(new ImageIcon("Images/" + prefix + "idle1.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerIdle2 = new ImageIcon(new ImageIcon("Images/" + prefix + "idle2.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerIdle3 = new ImageIcon(new ImageIcon("Images/" + prefix + "idle3.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerIdle4 = new ImageIcon(new ImageIcon("Images/" + prefix + "idle4.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));

        // WALK
        playerWalk1 = new ImageIcon(new ImageIcon("Images/" + prefix + "walk1.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerWalk2 = new ImageIcon(new ImageIcon("Images/" + prefix + "walk2.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerWalk3 = new ImageIcon(new ImageIcon("Images/" + prefix + "walk3.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerWalk4 = new ImageIcon(new ImageIcon("Images/" + prefix + "walk4.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerWalk5 = new ImageIcon(new ImageIcon("Images/" + prefix + "walk5.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerWalk6 = new ImageIcon(new ImageIcon("Images/" + prefix + "walk6.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerWalk7 = new ImageIcon(new ImageIcon("Images/" + prefix + "walk7.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));
        playerWalk8 = new ImageIcon(new ImageIcon("Images/" + prefix + "walk8.png").getImage().getScaledInstance(frameWidth/mapWidth, frameHeight/mapHeight, Image.SCALE_DEFAULT));

        playerIcon = playerIdle1;
    }
 
        public Group6_PD8(){
            loadPlayerType();
            loadPlayerSprites();
        /*
        Planned objectives:
            Move the character using the arrow keys.
            There is a flower in the middle of the map.
            When you interact with it, you will be asked to label its parts.
            Once you labelled something wrong, the game will reset.
            To pass the level you need to correctly label each parts.       
        */
        frame=new JFrame("BIO LAB");
        characterPosition = -1;
        characterMode = 0;
        
        loadData();
        startTime = System.currentTimeMillis();

        smallflower = new ImageIcon(new ImageIcon("Images/NewMap/smallflower.png")
                .getImage().getScaledInstance(frameWidth/mapWidth,frameHeight/mapHeight,Image.SCALE_DEFAULT));

        bigflower = new ImageIcon(new ImageIcon("Images/NewMap/bigflower.png")
                .getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT));

        ImageIcon[] temp = new ImageIcon[64];
        for(int i=1;i<=64;i++){
            temp[i-1] = new ImageIcon(
                    new ImageIcon("Images/NewMap/"+i+".png")
                            .getImage().getScaledInstance(
                                    frameWidth/mapWidth,
                                    frameHeight/mapHeight,
                                    Image.SCALE_DEFAULT));
        }

        img1=temp[0]; img2=temp[1]; img3=temp[2]; img4=temp[3];
        img5=temp[4]; img6=temp[5]; img7=temp[6]; img8=temp[7];
        img9=temp[8]; img10=temp[9]; img11=temp[10]; img12=temp[11];
        img13=temp[12]; img14=temp[13]; img15=temp[14]; img16=temp[15];
        img17=temp[16]; img18=temp[17]; img19=temp[18]; img20=temp[19];
        img21=temp[20]; img22=temp[21]; img23=temp[22]; img24=temp[23];
        img25=temp[24]; img26=temp[25]; img27=temp[26]; img28=temp[27];
        img29=temp[28]; img30=temp[29]; img31=temp[30]; img32=temp[31];
        img33=temp[32]; img34=temp[33]; img35=temp[34]; img36=temp[35];
        img37=temp[36]; img38=temp[37]; img39=temp[38]; img40=temp[39];
        img41=temp[40]; img42=temp[41]; img43=temp[42]; img44=temp[43];
        img45=temp[44]; img46=temp[45]; img47=temp[46]; img48=temp[47];
        img49=temp[48]; img50=temp[49]; img51=temp[50]; img52=temp[51];
        img53=temp[52]; img54=temp[53]; img55=temp[54]; img56=temp[55];
        img57=temp[56]; img58=temp[57]; img59=temp[58]; img60=temp[59];
        img61=temp[60]; img62=temp[61]; img63=temp[62]; img64=temp[63];

        tiles = new JLabel[]{
            new JLabel(img1), new JLabel(img2), new JLabel(img3), new JLabel(img4),new JLabel(img5), new JLabel(img6), new JLabel(img7), new JLabel(img8),
            new JLabel(img9), new JLabel(img10), new JLabel(img11), new JLabel(img12),new JLabel(img13), new JLabel(img14), new JLabel(img15), new JLabel(img16),
            new JLabel(img17), new JLabel(img18), new JLabel(img19), new JLabel(img20),new JLabel(img21), new JLabel(img22), new JLabel(img23), new JLabel(img24),
            new JLabel(img25), new JLabel(img26), new JLabel(img27), new JLabel(smallflower),new JLabel(img29), new JLabel(img30), new JLabel(img31), new JLabel(img32),
            new JLabel(img33), new JLabel(img34), new JLabel(img35), new JLabel(img36),new JLabel(img37), new JLabel(img38), new JLabel(img39), new JLabel(img40),
            new JLabel(img41), new JLabel(img42), new JLabel(img43), new JLabel(img44),new JLabel(img45), new JLabel(img46), new JLabel(img47), new JLabel(img48),
            new JLabel(img49), new JLabel(img50), new JLabel(img51), new JLabel(img52),new JLabel(img53), new JLabel(img54), new JLabel(img55), new JLabel(img56),
            new JLabel(img57), new JLabel(img58), new JLabel(img59), new JLabel(img60),new JLabel(img61), new JLabel(img62), new JLabel(img63), new JLabel(img64)
        };
        

        character = new JLabel[64];

        characterPlace=new int[]{
            1,1,1,0,0,1,1,1,
            1,1,1,0,0,1,1,1,
            1,1,1,0,0,1,1,1,
            0,2,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            1,1,1,0,0,1,1,1,
            1,1,1,0,0,1,1,1,
            1,1,1,0,0,1,1,1
        };

        for(int i=0;i<64;i++){
            character[i]=new JLabel();
            if(characterPlace[i]==2){
                character[i].setIcon(playerIcon);
                characterPosition=i;
            }
        }

        player=new Player(characterPosition,playerIcon);
        flower=new Flower(27,bigflower,this);
    }
    
    public void setFrame(){

        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth,mapHeight)));

        int x=0,y=0;
        for(int i=0;i<64;i++){
            frame.add(character[i],new Rectangle(x,y,1,1));
            x++; if(x%mapWidth==0){x=0;y++;}
        }

        x=0;y=0;
        for(int i=0;i<64;i++){
            frame.add(tiles[i],new Rectangle(x,y,1,1));
            x++; if(x%mapWidth==0){x=0;y++;}
        }

        frame.setSize(frameWidth,frameHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(this);
        frame.setTitle("BIO LAB | Attempts: " + attempts + " | Lives: " + lives);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                attempts = 0;        // reset attempts
                saveData();         // save to file
            }
        });
    }
    public boolean connect(){
        SwingUtilities.invokeLater(this::setFrame);
        
        while(!finished) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {}
        }
        return false;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(Group6_PD8::new);
    }
    
    private void movePlayer(int newPosition, ImageIcon walk1, ImageIcon walk2, ImageIcon idle){

    character[characterPosition].setIcon(null);

    if(characterMode == 0){
        character[newPosition].setIcon(walk1);
        characterMode = 1;
    } else {
        character[newPosition].setIcon(walk2);
        characterMode = 0;
    }

    characterPosition = newPosition;
}
    @Override
    public void keyPressed(KeyEvent e) {

        int newPosition = characterPosition;

        // MOVE LOGIC
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            newPosition++;
            if(newPosition % mapWidth != 0 && characterPlace[newPosition] != 1){
                movePlayer(newPosition, playerWalk3, playerWalk7, playerIdle3);
            } else {
                character[characterPosition].setIcon(playerIdle3);
            }
        }

        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            newPosition--;
            if(characterPosition % mapWidth != 0 && characterPlace[newPosition] != 1){
                movePlayer(newPosition, playerWalk4, playerWalk8, playerIdle2);
            } else {
                character[characterPosition].setIcon(playerIdle2);
            }
        }

        if(e.getKeyCode()==KeyEvent.VK_DOWN){
            newPosition += mapWidth;
            if(newPosition < 64 && characterPlace[newPosition] != 1){
                movePlayer(newPosition, playerWalk1, playerWalk2, playerIdle1);
            } else {
                character[characterPosition].setIcon(playerIdle1);
            }
        }

        if(e.getKeyCode()==KeyEvent.VK_UP){
            newPosition -= mapWidth;
            if(newPosition >= 0 && characterPlace[newPosition] != 1){
                movePlayer(newPosition, playerWalk6, playerWalk5, playerIdle4);
            } else {
                character[characterPosition].setIcon(playerIdle4);
            }
        }

        // INTERACTION (flower)
        if(characterPosition == flower.getPosition()){
            flower.interact(frame);
        }
    }

    @Override public void keyTyped(KeyEvent e){}
    @Override public void keyReleased(KeyEvent e){}

    // FILE LOAD
    private void loadData(){
        try{
            File f=new File(FILE_NAME);
            if(f.exists()){
                BufferedReader br=new BufferedReader(new FileReader(f));
                fastestTime=Long.parseLong(br.readLine());
                attempts=Integer.parseInt(br.readLine());
                br.close();
            }
        }catch(Exception e){
            System.out.println("No save file.");
        }
    }

    // FILE SAVE
    public void saveData(){
        try{
            BufferedWriter bw=new BufferedWriter(new FileWriter(FILE_NAME));
            bw.write(fastestTime+"\n");
            bw.write(attempts+"\n");
            bw.close();
        }catch(Exception e){
            System.out.println("Save error.");
        }
    }
}

// OOP
class GameObject{
    private int position;

    public GameObject(int p){ position=p; }
    public int getPosition(){ return position; }
    public void setPosition(int p){ position=p; }

    public void interact(JFrame frame){}
}

class Player extends GameObject{
    private ImageIcon icon;

    public Player(int p,ImageIcon i){
        super(p);
        icon=i;
    }

    @Override
    public void interact(JFrame frame){
        System.out.println("Player interacts");
    }
}

class Flower extends GameObject{

    private ImageIcon big;
    private Group6_PD8 game;

    public Flower(int p,ImageIcon b,Group6_PD8 g){
        super(p);
        big=b;
        game=g;
    }

    @Override
    public void interact(JFrame frame){

        JDialog d=new JDialog(frame,"Flower",true);
        d.setSize(400,400);
        d.setLocationRelativeTo(frame);
        d.setLayout(new BorderLayout());

        JLabel img=new JLabel(big);
        img.setHorizontalAlignment(JLabel.CENTER);

        JButton start=new JButton("Start Quiz");

        start.addActionListener(e->{
            d.dispose();
            startQuiz(frame);
        });

        d.add(img,BorderLayout.CENTER);
        d.add(start,BorderLayout.SOUTH);
        d.setVisible(true);
    }

    public void startQuiz(JFrame frame){
        game.attempts++;
        game.saveData();

        // update display
        game.frame.setTitle("BIO LAB | Attempts: " + game.attempts + " | Lives: " + game.lives);
        game.frame.setTitle("BIO LAB | Attempts: " + game.attempts + " | Lives: " + game.lives);
        
        try{
            int a1=Integer.parseInt(JOptionPane.showInputDialog(frame,"1) Pollen producer?\n1.Stigma\n2.Anther\n3.Sepal"));
            if(a1!=2){ wrong(frame); return; }

            int a2=Integer.parseInt(JOptionPane.showInputDialog(frame,"2) Receives pollen?\n1.Stigma\n2.Root\n3.Leaf"));
            if(a2!=1){ wrong(frame); return; }

            int a3=Integer.parseInt(JOptionPane.showInputDialog(frame,"3) Protects bud?\n1.Petal\n2.Sepal\n3.Stem"));
            if(a3!=2){ wrong(frame); return; }

            long time=System.currentTimeMillis()-game.startTime;

            if(time<game.fastestTime){
                game.fastestTime=time;
                JOptionPane.showMessageDialog(frame,"NEW RECORD: "+time/1000.0+"s");
            }else{
                JOptionPane.showMessageDialog(frame,"Win!\nTime: "+time/1000.0+"\nBest: "+game.fastestTime/1000.0);
            }
            
            game.saveData();

        }catch(Exception e){
            JOptionPane.showMessageDialog(frame,"Invalid input (1-3 only)");
        }
    }

    private void wrong(JFrame frame){
        if(game.lives>0){
            JOptionPane.showMessageDialog(frame, "Wrong! Lives left: "+game.lives);
        }else{
            JOptionPane.showMessageDialog(frame, "Game Over! Restarting...");
            frame.dispose();
            new Group6_PD8().setFrame();
        }
        game.lives--;
    }
}
