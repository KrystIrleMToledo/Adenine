package Quarter3;

import Quarter2.GraphPaperLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Group6_PD5 {
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
          boyfront, boyback, smallflower, bigflower; 
    
    JLabel tiles[];
    JLabel character[];
    int mapLayout[];
    int characterPlace[];
    int mapWidth = 8;
    int mapHeight = 8;
    int frameWidth = 450;
    int frameHeight = 450;
    int characterPosition;
    int characterMode;
    
    public Group6_PD5() {
        /*
        Planned objectives:
            Move the character using the arrow keys.
            There is a flower in the middle of the map.
            When you interact with it, you will be asked to label its parts.
            Once you labelled something wrong, the game will reset.
            To pass the level you need to correctly label each parts.       
        */
        frame = new JFrame("BIO LAB");
        characterPosition = -1;
        characterMode = 0;
        
        img1  = new ImageIcon(new ImageIcon("Images/NewMap/1.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img2  = new ImageIcon(new ImageIcon("Images/NewMap/2.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img3  = new ImageIcon(new ImageIcon("Images/NewMap/3.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img4  = new ImageIcon(new ImageIcon("Images/NewMap/4.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img5  = new ImageIcon(new ImageIcon("Images/NewMap/5.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img6  = new ImageIcon(new ImageIcon("Images/NewMap/6.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img7  = new ImageIcon(new ImageIcon("Images/NewMap/7.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img8  = new ImageIcon(new ImageIcon("Images/NewMap/8.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));

        img9  = new ImageIcon(new ImageIcon("Images/NewMap/9.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img10 = new ImageIcon(new ImageIcon("Images/NewMap/10.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img11 = new ImageIcon(new ImageIcon("Images/NewMap/11.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img12 = new ImageIcon(new ImageIcon("Images/NewMap/12.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img13 = new ImageIcon(new ImageIcon("Images/NewMap/13.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img14 = new ImageIcon(new ImageIcon("Images/NewMap/14.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img15 = new ImageIcon(new ImageIcon("Images/NewMap/15.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img16 = new ImageIcon(new ImageIcon("Images/NewMap/16.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));

        img17 = new ImageIcon(new ImageIcon("Images/NewMap/17.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img18 = new ImageIcon(new ImageIcon("Images/NewMap/18.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img19 = new ImageIcon(new ImageIcon("Images/NewMap/19.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img20 = new ImageIcon(new ImageIcon("Images/NewMap/20.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img21 = new ImageIcon(new ImageIcon("Images/NewMap/21.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img22 = new ImageIcon(new ImageIcon("Images/NewMap/22.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));        
        img23 = new ImageIcon(new ImageIcon("Images/NewMap/23.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img24 = new ImageIcon(new ImageIcon("Images/NewMap/24.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));

        img25 = new ImageIcon(new ImageIcon("Images/NewMap/25.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img26 = new ImageIcon(new ImageIcon("Images/NewMap/26.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img27 = new ImageIcon(new ImageIcon("Images/NewMap/27.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img28 = new ImageIcon(new ImageIcon("Images/NewMap/28.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img29 = new ImageIcon(new ImageIcon("Images/NewMap/29.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img30 = new ImageIcon(new ImageIcon("Images/NewMap/30.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img31 = new ImageIcon(new ImageIcon("Images/NewMap/31.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img32 = new ImageIcon(new ImageIcon("Images/NewMap/32.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));

        img33 = new ImageIcon(new ImageIcon("Images/NewMap/33.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img34 = new ImageIcon(new ImageIcon("Images/NewMap/34.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img35 = new ImageIcon(new ImageIcon("Images/NewMap/35.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img36 = new ImageIcon(new ImageIcon("Images/NewMap/36.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img37 = new ImageIcon(new ImageIcon("Images/NewMap/37.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img38 = new ImageIcon(new ImageIcon("Images/NewMap/38.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img39 = new ImageIcon(new ImageIcon("Images/NewMap/39.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img40 = new ImageIcon(new ImageIcon("Images/NewMap/40.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));

        img41 = new ImageIcon(new ImageIcon("Images/NewMap/41.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img42 = new ImageIcon(new ImageIcon("Images/NewMap/42.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img43 = new ImageIcon(new ImageIcon("Images/NewMap/43.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img44 = new ImageIcon(new ImageIcon("Images/NewMap/44.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img45 = new ImageIcon(new ImageIcon("Images/NewMap/45.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img46 = new ImageIcon(new ImageIcon("Images/NewMap/46.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img47 = new ImageIcon(new ImageIcon("Images/NewMap/47.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img48 = new ImageIcon(new ImageIcon("Images/NewMap/48.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));

        img49 = new ImageIcon(new ImageIcon("Images/NewMap/49.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img50 = new ImageIcon(new ImageIcon("Images/NewMap/50.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img51 = new ImageIcon(new ImageIcon("Images/NewMap/51.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img52 = new ImageIcon(new ImageIcon("Images/NewMap/52.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img53 = new ImageIcon(new ImageIcon("Images/NewMap/53.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img54 = new ImageIcon(new ImageIcon("Images/NewMap/54.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img55 = new ImageIcon(new ImageIcon("Images/NewMap/55.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img56 = new ImageIcon(new ImageIcon("Images/NewMap/56.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));

        img57 = new ImageIcon(new ImageIcon("Images/NewMap/57.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img58 = new ImageIcon(new ImageIcon("Images/NewMap/58.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img59 = new ImageIcon(new ImageIcon("Images/NewMap/59.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img60 = new ImageIcon(new ImageIcon("Images/NewMap/60.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img61 = new ImageIcon(new ImageIcon("Images/NewMap/61.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img62 = new ImageIcon(new ImageIcon("Images/NewMap/62.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img63 = new ImageIcon(new ImageIcon("Images/NewMap/63.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        img64 = new ImageIcon(new ImageIcon("Images/NewMap/64.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        smallflower = new ImageIcon(new ImageIcon("Images/NewMap/64.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boyfront = new ImageIcon(new ImageIcon("Images/boyfront.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boyback = new ImageIcon(new ImageIcon("Images/boyback.png").getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
       
        character = new JLabel [mapWidth * mapHeight];
        
        characterPlace = new int[]{
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            1,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0
        };
        
        for(int i=0;i<character.length;i++){
            if(characterPlace[i]==1){
                character[i] = new JLabel(boyfront);
                characterPosition = i;
            } else {
                character[i] = new JLabel();
            }
        }
        
        tiles = new JLabel[]{
            new JLabel(img1),  new JLabel(img2),  new JLabel(img3),  new JLabel(img4),  new JLabel(img5),  new JLabel(img6),  new JLabel(img7),  new JLabel(img8),
            new JLabel(img9),  new JLabel(img10), new JLabel(img11), new JLabel(img12), new JLabel(img13), new JLabel(img14), new JLabel(img15), new JLabel(img16),
            new JLabel(img17), new JLabel(img18), new JLabel(img19), new JLabel(img20), new JLabel(img21), new JLabel(img22), new JLabel(img23), new JLabel(img24),
            new JLabel(img25), new JLabel(img26), new JLabel(img27), new JLabel(img28), new JLabel(img29), new JLabel(img30), new JLabel(img31), new JLabel(img32),
            new JLabel(img33), new JLabel(img34), new JLabel(img35), new JLabel(img36), new JLabel(img37), new JLabel(img38), new JLabel(img39), new JLabel(img40),
            new JLabel(img41), new JLabel(img42), new JLabel(img43), new JLabel(img44), new JLabel(img45), new JLabel(img46), new JLabel(img47), new JLabel(img48),
            new JLabel(img49), new JLabel(img50), new JLabel(img51), new JLabel(img52), new JLabel(img53), new JLabel(img54), new JLabel(img55), new JLabel(img56),
            new JLabel(img57), new JLabel(img58), new JLabel(img59), new JLabel(img60), new JLabel(img61), new JLabel(img62), new JLabel(img63), new JLabel(img64)
        };
    }

    public void setFrame() {
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth, mapHeight)));
        int x = 0, y = 0, w = 1, h = 1;

        for (int i = 0; i < tiles.length; i++) {

            // If this position has the character
            if (i == characterPosition) {
                tiles[i].setIcon(boyfront);
            }

            frame.add(tiles[i], new Rectangle(x, y, w, h));

            x++;
            if (x % mapWidth == 0) {
                x = 0;
                y++;
            }
        }
        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        Group6_PD5 sg = new Group6_PD5();
        sg.setFrame();
    }
}
