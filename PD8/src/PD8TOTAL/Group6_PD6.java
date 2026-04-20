package PD8TOTAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Group6_PD6 implements KeyListener {

    JFrame frame;

    ImageIcon 
        img1, img2, img3, img4, img5, img6, img7, img8,
        img9, img10, img11, img12, img13, img14, img15, img16,
        img17, img18, img19, img20, img21, img22, img23, img24,
        img25, img26, img27, img28, img29, img30, img31, img32,
        img33, img34, img35, img36, img37, img38, img39, img40,
        img41, img42, img43, img44, img45, img46, img47, img48,
        img49, img50, img51, img52, img53, img54, img55, img56,
        img57, img58, img59, img60, img61, img62, img63, img64,
        smallflower, bigflower;

    JLabel tiles[];
    JLabel character[];

    int characterPlace[];
    int mapWidth = 8;
    int mapHeight = 8;
    int frameWidth = 450;
    int frameHeight = 450;

    int characterPosition;
    ImageIcon playerIcon;

    Player player;
    Flower flower;

    public Group6_PD6() {
        /*
        Planned objectives:
            Move the character using the arrow keys.
            There is a flower in the middle of the map.
            When you interact with it, you will be asked to label its parts.
            Once you labelled something wrong, the game will reset.
            To pass the level you need to correctly label each parts.       
        */
        frame = new JFrame("BIO LAB");

        playerIcon = new ImageIcon(new ImageIcon("Images/boyidle1.png")
                .getImage().getScaledInstance(frameWidth/mapWidth,
                        frameHeight/mapHeight, Image.SCALE_DEFAULT));

        smallflower = new ImageIcon(new ImageIcon("Images/Map2/smallflower.png")
                .getImage().getScaledInstance(frameWidth/mapWidth,
                        frameHeight/mapHeight, Image.SCALE_DEFAULT));

        bigflower = new ImageIcon(new ImageIcon("Images/Map2/bigflower.png")
                .getImage().getScaledInstance(300,300,
                        Image.SCALE_DEFAULT));

        ImageIcon[] temp = new ImageIcon[64];
        for(int i=1;i<=64;i++){
            temp[i-1] = new ImageIcon(
                    new ImageIcon("Images/Map2/"+i+".png")
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

        characterPlace = new int[]{
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
            character[i] = new JLabel();
            if(characterPlace[i]==2){
                character[i].setIcon(playerIcon);
                characterPosition = i;
            }
        }

        player = new Player(characterPosition, playerIcon);
        flower = new Flower(27, bigflower); // center
    }

    public void setFrame() {

        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth,mapHeight)));

        int x=0,y=0;
        for(int i=0;i<64;i++){
            frame.add(character[i], new Rectangle(x,y,1,1));
            x++; if(x%mapWidth==0){ x=0; y++; }
        }

        x=0; y=0;
        for(int i=0;i<64;i++){
            frame.add(tiles[i], new Rectangle(x,y,1,1));
            x++; if(x%mapWidth==0){ x=0; y++; }
        }

        frame.setSize(frameWidth,frameHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(this);
    }

    public static void main(String[] args){
        Group6_PD6 g = new Group6_PD6();
        g.setFrame();
    }

    @Override
    public void keyPressed(KeyEvent e){

        int newPosition = characterPosition;

        if(e.getKeyCode()==KeyEvent.VK_RIGHT) newPosition++;
        if(e.getKeyCode()==KeyEvent.VK_LEFT) newPosition--;
        if(e.getKeyCode()==KeyEvent.VK_DOWN) newPosition+=mapWidth;
        if(e.getKeyCode()==KeyEvent.VK_UP) newPosition-=mapWidth;

        if(newPosition>=0 &&
           newPosition<characterPlace.length &&
           characterPlace[newPosition]!=1){

            character[characterPosition].setIcon(null);
            character[newPosition].setIcon(playerIcon);
            characterPosition=newPosition;
            player.setPosition(newPosition);

            if(characterPosition==flower.getPosition()){
                flower.interact(frame);
            }
        }
    }

    @Override public void keyTyped(KeyEvent e){}
    @Override public void keyReleased(KeyEvent e){}
}

    class GameObject{
        private int position;

        public GameObject(int position){
            this.position=position;
        }

        public int getPosition(){ return position; }
        public void setPosition(int position){ this.position=position; }

        public void interact(JFrame frame){}
    }

    class Player extends GameObject{
        private ImageIcon icon;

        public Player(int position, ImageIcon icon){
            super(position);
            this.icon=icon;
        }

        public ImageIcon getIcon(){ return icon; }

        @Override
        public void interact(JFrame frame){
            System.out.println("Player interaction.");
        }
    }

    class Flower extends GameObject{

        private ImageIcon big;

        public Flower(int position, ImageIcon big){
            super(position);
            this.big=big;
        }

        @Override
        public void interact(JFrame frame){

            JDialog dialog=new JDialog(frame,"Flower",true);
            dialog.setSize(400,400);
            dialog.setLocationRelativeTo(frame);
            dialog.setLayout(new BorderLayout());

            JLabel label=new JLabel(big);
            label.setHorizontalAlignment(JLabel.CENTER);

            JButton start=new JButton("Start Quiz");

            start.addActionListener(e->{
                dialog.dispose();
                startQuiz(frame);
            });

            dialog.add(label,BorderLayout.CENTER);
            dialog.add(start,BorderLayout.SOUTH);
            dialog.setVisible(true);
        }

        private boolean check(String input,String correct){
            return input!=null && input.equalsIgnoreCase(correct);
        }

        private boolean check(String input,String c1,String c2){
            return input!=null &&
                   (input.equalsIgnoreCase(c1) ||
                    input.equalsIgnoreCase(c2));
        }

        private void startQuiz(JFrame frame){

            String a1=JOptionPane.showInputDialog(frame,
                    "What part produces pollen?");
            if(!check(a1,"anther")){
                reset(frame); return;
            }

            String a2=JOptionPane.showInputDialog(frame,
                    "What part receives pollen?");
            if(!check(a2,"stigma")){
                reset(frame); return;
            }

            String a3=JOptionPane.showInputDialog(frame,
                    "What protects the bud?");
            if(!check(a3,"sepal","sepals")){
                reset(frame); return;
            }

            JOptionPane.showMessageDialog(frame,
                    "You Win! All parts labeled correctly!");
        }

        private void reset(JFrame frame){
            JOptionPane.showMessageDialog(frame,
                    "Wrong answer. Game resetting.");
            frame.dispose();
            new Group6_PD6().setFrame();
        }
    }