package swing;
//Other members: Maia Adelle Soyao & Zionne Kay Babia
//This code was AI assisted with corrections in game logic

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GameCharacter {
    private int position;
    private int points;
    
    public GameCharacter(int startPosition) {
        this.position = startPosition;
        this.points = 0;
    }
    public int getPosition() { return position; }
    public void setPosition(int pos) { this.position = pos; }
    public int getPoints() { return points; }
    public void addPoints() { points++; }
    public void addPoints(int value) { points += value; }
    
    public void reset() {
        points = 0;
    }
}

class PlayerCharacter extends GameCharacter {
    public PlayerCharacter(int startPosition) {
        super(startPosition);
    }
    @Override
    public void reset() {
        super.reset();
    }
}

public class PD7G1 implements KeyListener{
    JFrame frame;
    ImageIcon img1, img2, img3, img4, img5, img6, img7, img8,
          img9, img10, img11, img12, img13, img14, img15, img16,
          img17, img18, img19, img20, img21, img22, img23, img24,
          img25, img26, img27, img28, img29, img30, img31, img32,
          img33, img34, img35, img36, img37, img38, img39, img40,
          img41, img42, img43, img44, img45, img46, img47, img48,
          img49, img50, img51, img52, img53, img54, img55, img56,
          img57, img58, img59, img60, img61, img62, img63, img64,
          img65, img66, img67, img68, img69, img70, img71, img72,
          img73, img74, img75, img76, img77, img78, img79, img80,
          img81, img82, img83, img84, img85, img86, img87, img88,
          img89, img90, img91, img92, img93, img94, img95, img96,
          img97, img98, img99, img100, img101, img102, img103, img104;
    ImageIcon board;
    ImageIcon playerIcon;
    JLabel tiles[];
    JLabel character[];
    int mapLayout[];
    int onelocation;
    int twolocation;
    int threelocation;
    int fourlocation;
    int fivelocation;
    int boardlocation;
    int s5location;
    int characterPlace[];
    int characterPosition;
    int mapWidth = 13;
    int mapHeight = 8;
    int points = 0;
    int attempts = 0;
    int correctAnswers = 0;
    private int startPosition;
    private PlayerCharacter player;
    boolean q4Answered = false;
    boolean q5Answered = false;
    boolean q8Answered = false;
    boolean hasKey = false;
    int tileSize = 90;
    int frameWidth = tileSize * mapWidth;
    int frameHeight = tileSize * mapHeight;
    int characterMode = 1;
    ImageIcon playerIconfront;
    ImageIcon playerIconfront2;
    ImageIcon playerIconback;
    ImageIcon playerIconback2;
    ImageIcon playerIconleft;
    ImageIcon playerIconright;
    int cols = 13; 
    
    /*OBJECTIVE
            Move the character using the arrow keys.
            There are questions on the tables around the map.
            Some tables have trivia questions, once done, go to the vending machine to get the key
            You can check your score on the teacher's table.
            Each question can only be answered once.
            To pass the level:
            - Find all questions
            - Get perfect score
            - Go to the vending machine to get key
            - Escape 
    */
    
   
    public PD7G1(){
        frame=new JFrame();
        characterPosition=-1;
        startPosition = characterPosition;
        img1  = new ImageIcon(new ImageIcon("Images/map/1.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img2  = new ImageIcon(new ImageIcon("Images/map/2.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img3  = new ImageIcon(new ImageIcon("Images/map/3.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img4  = new ImageIcon(new ImageIcon("Images/map/4.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img5  = new ImageIcon(new ImageIcon("Images/map/5.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img6  = new ImageIcon(new ImageIcon("Images/map/6.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img7  = new ImageIcon(new ImageIcon("Images/map/7.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img8  = new ImageIcon(new ImageIcon("Images/map/8.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img9  = new ImageIcon(new ImageIcon("Images/map/9.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img10 = new ImageIcon(new ImageIcon("Images/map/10.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img11 = new ImageIcon(new ImageIcon("Images/map/11.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img12 = new ImageIcon(new ImageIcon("Images/map/12.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img13 = new ImageIcon(new ImageIcon("Images/map/13.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img14 = new ImageIcon(new ImageIcon("Images/map/14.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img15 = new ImageIcon(new ImageIcon("Images/map/15.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img16 = new ImageIcon(new ImageIcon("Images/map/16.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img17 = new ImageIcon(new ImageIcon("Images/map/17.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img18 = new ImageIcon(new ImageIcon("Images/map/18.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img19 = new ImageIcon(new ImageIcon("Images/map/19.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img20 = new ImageIcon(new ImageIcon("Images/map/20.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img21 = new ImageIcon(new ImageIcon("Images/map/21.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img22 = new ImageIcon(new ImageIcon("Images/map/22.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img23 = new ImageIcon(new ImageIcon("Images/map/23.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img24 = new ImageIcon(new ImageIcon("Images/map/24.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img25 = new ImageIcon(new ImageIcon("Images/map/25.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img26 = new ImageIcon(new ImageIcon("Images/map/26.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img27 = new ImageIcon(new ImageIcon("Images/map/27.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img28 = new ImageIcon(new ImageIcon("Images/map/28.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img29 = new ImageIcon(new ImageIcon("Images/map/29.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img30 = new ImageIcon(new ImageIcon("Images/map/30.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img31 = new ImageIcon(new ImageIcon("Images/map/31.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img32 = new ImageIcon(new ImageIcon("Images/map/32.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img33 = new ImageIcon(new ImageIcon("Images/map/33.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img34 = new ImageIcon(new ImageIcon("Images/map/34.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img35 = new ImageIcon(new ImageIcon("Images/map/35.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img36 = new ImageIcon(new ImageIcon("Images/map/36.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img37 = new ImageIcon(new ImageIcon("Images/map/37.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img38 = new ImageIcon(new ImageIcon("Images/map/38.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img39 = new ImageIcon(new ImageIcon("Images/map/39.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img40 = new ImageIcon(new ImageIcon("Images/map/40.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img41 = new ImageIcon(new ImageIcon("Images/map/41.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img42 = new ImageIcon(new ImageIcon("Images/map/42.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img43 = new ImageIcon(new ImageIcon("Images/map/43.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img44 = new ImageIcon(new ImageIcon("Images/map/44.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img45 = new ImageIcon(new ImageIcon("Images/map/45.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img46 = new ImageIcon(new ImageIcon("Images/map/46.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img47 = new ImageIcon(new ImageIcon("Images/map/47.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img48 = new ImageIcon(new ImageIcon("Images/map/48.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img49 = new ImageIcon(new ImageIcon("Images/map/49.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img50 = new ImageIcon(new ImageIcon("Images/map/50.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img51 = new ImageIcon(new ImageIcon("Images/map/51.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img52 = new ImageIcon(new ImageIcon("Images/map/52.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img53 = new ImageIcon(new ImageIcon("Images/map/53.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img54 = new ImageIcon(new ImageIcon("Images/map/54.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img55 = new ImageIcon(new ImageIcon("Images/map/55.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img56 = new ImageIcon(new ImageIcon("Images/map/56.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img57 = new ImageIcon(new ImageIcon("Images/map/57.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img58 = new ImageIcon(new ImageIcon("Images/map/58.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img59 = new ImageIcon(new ImageIcon("Images/map/59.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img60 = new ImageIcon(new ImageIcon("Images/map/60.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img61 = new ImageIcon(new ImageIcon("Images/map/61.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img62 = new ImageIcon(new ImageIcon("Images/map/62.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img63 = new ImageIcon(new ImageIcon("Images/map/63.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img64 = new ImageIcon(new ImageIcon("Images/map/64.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img65 = new ImageIcon(new ImageIcon("Images/map/65.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img66 = new ImageIcon(new ImageIcon("Images/map/66.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img67 = new ImageIcon(new ImageIcon("Images/map/67.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img68 = new ImageIcon(new ImageIcon("Images/map/68.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img69 = new ImageIcon(new ImageIcon("Images/map/69.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img70 = new ImageIcon(new ImageIcon("Images/map/70.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img71 = new ImageIcon(new ImageIcon("Images/map/71.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img72 = new ImageIcon(new ImageIcon("Images/map/72.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img73 = new ImageIcon(new ImageIcon("Images/map/73.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img74 = new ImageIcon(new ImageIcon("Images/map/74.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img75 = new ImageIcon(new ImageIcon("Images/map/75.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img76 = new ImageIcon(new ImageIcon("Images/map/76.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img77 = new ImageIcon(new ImageIcon("Images/map/77.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img78 = new ImageIcon(new ImageIcon("Images/map/78.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img79 = new ImageIcon(new ImageIcon("Images/map/79.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img80 = new ImageIcon(new ImageIcon("Images/map/80.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img81 = new ImageIcon(new ImageIcon("Images/map/81.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img82 = new ImageIcon(new ImageIcon("Images/map/82.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img83 = new ImageIcon(new ImageIcon("Images/map/83.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img84 = new ImageIcon(new ImageIcon("Images/map/84.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img85 = new ImageIcon(new ImageIcon("Images/map/85.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img86 = new ImageIcon(new ImageIcon("Images/map/86.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img87 = new ImageIcon(new ImageIcon("Images/map/87.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img88 = new ImageIcon(new ImageIcon("Images/map/88.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img89 = new ImageIcon(new ImageIcon("Images/map/89.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img90 = new ImageIcon(new ImageIcon("Images/map/90.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img91 = new ImageIcon(new ImageIcon("Images/map/91.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img92 = new ImageIcon(new ImageIcon("Images/map/92.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img93 = new ImageIcon(new ImageIcon("Images/map/93.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img94 = new ImageIcon(new ImageIcon("Images/map/94.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img95 = new ImageIcon(new ImageIcon("Images/map/95.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img96 = new ImageIcon(new ImageIcon("Images/map/96.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img97 = new ImageIcon(new ImageIcon("Images/map/97.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img98 = new ImageIcon(new ImageIcon("Images/map/98.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        img99  = new ImageIcon(new ImageIcon("Images/map/99.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img100 = new ImageIcon(new ImageIcon("Images/map/100.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img101 = new ImageIcon(new ImageIcon("Images/map/101.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img102 = new ImageIcon(new ImageIcon("Images/map/104.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img103 = new ImageIcon(new ImageIcon("Images/map/102.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        img104 = new ImageIcon(new ImageIcon("Images/map/103.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));

        playerIconfront = playerIcon = new ImageIcon(new ImageIcon("Images/player1.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconfront2 = new ImageIcon(new ImageIcon("Images/player2.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconback  = new ImageIcon(new ImageIcon("Images/player3.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconback2 = new ImageIcon(new ImageIcon("Images/player4.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconright = new ImageIcon(new ImageIcon("Images/player5.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconleft  = new ImageIcon(new ImageIcon("Images/player6.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        character=new JLabel[mapWidth*mapHeight];
        
        characterPlace = new int[]{
            1,1,1,1,1,1,1,1,1,1,1,1,1,
            1,3,1,1,1,1,1,1,0,0,0,0,1,
            1,0,0,0,8,0,0,1,0,0,0,0,1,
            1,0,4,0,1,5,0,1,0,0,0,0,1,
            1,0,0,0,0,0,0,1,0,0,0,0,1,
            1,6,1,1,0,1,1,1,1,1,1,7,1,
            1,2,0,0,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1,1,1,1
        };
        
        for(int i=0;i<character.length;i++){
            if(characterPlace[i]==2){
                character[i]=new JLabel(playerIcon);
                characterPosition = i;
                startPosition = i;
                player = new PlayerCharacter(i);
            }
            else character[i]=new JLabel();
        }
       
        tiles = new JLabel[]{
            new JLabel(img1),  new JLabel(img2),  new JLabel(img3),  new JLabel(img4),  new JLabel(img5),  new JLabel(img6),  new JLabel(img7),  new JLabel(img8),
            new JLabel(img9),  new JLabel(img10), new JLabel(img11), new JLabel(img12), new JLabel(img13), new JLabel(img14), new JLabel(img15), new JLabel(img16),
            new JLabel(img17), new JLabel(img18), new JLabel(img19), new JLabel(img20), new JLabel(img21), new JLabel(img22), new JLabel(img23), new JLabel(img24),
            new JLabel(img25), new JLabel(img26), new JLabel(img27), new JLabel(img28), new JLabel(img29), new JLabel(img30), new JLabel(img31), new JLabel(img32),
            new JLabel(img33), new JLabel(img34), new JLabel(img35), new JLabel(img36), new JLabel(img37), new JLabel(img38), new JLabel(img39), new JLabel(img40),
            new JLabel(img41), new JLabel(img42), new JLabel(img43), new JLabel(img44), new JLabel(img45), new JLabel(img46), new JLabel(img47), new JLabel(img48),
            new JLabel(img49), new JLabel(img50), new JLabel(img51), new JLabel(img52), new JLabel(img53), new JLabel(img54), new JLabel(img55), new JLabel(img56),
            new JLabel(img57), new JLabel(img58), new JLabel(img59), new JLabel(img60), new JLabel(img61), new JLabel(img62), new JLabel(img63), new JLabel(img64),
            new JLabel(img65),  new JLabel(img66),  new JLabel(img67),  new JLabel(img68),  new JLabel(img69),  new JLabel(img70),  new JLabel(img71),  new JLabel(img72),
            new JLabel(img73),  new JLabel(img74),  new JLabel(img75),  new JLabel(img76),  new JLabel(img77),  new JLabel(img78),  new JLabel(img79),  new JLabel(img80),
            new JLabel(img81),  new JLabel(img82),  new JLabel(img83),  new JLabel(img84),  new JLabel(img85),  new JLabel(img86),  new JLabel(img87),  new JLabel(img88),
            new JLabel(img89),  new JLabel(img90),  new JLabel(img91),  new JLabel(img92),  new JLabel(img93),  new JLabel(img94),  new JLabel(img95),  new JLabel(img96),
            new JLabel(img97),  new JLabel(img98),  new JLabel(img99),  new JLabel(img100), new JLabel(img101), new JLabel(img102), new JLabel(img103), new JLabel(img104)
        };
    }
   
    public void setFrame(){
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth,mapHeight)));
       
        int x=0, y=0, w=1, h=1;
        for(int i=0;i<character.length;i++){
            frame.add(character[i], new Rectangle(x,y,w,h));
            x++;
            if(x%mapWidth==0){
                x=0;
                y++;
            }
        }
        
        x=0; y=0;
        
        for(int i=0;i<tiles.length;i++){
            frame.add(tiles[i], new Rectangle(x,y,w,h));
            x++;
            if(x%mapWidth==0){
                x=0;
                y++;
            }
        }
        frame.setSize(frameWidth,frameHeight);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);;
    }
    
    private void showFailScreen(int percentage){
        int choice = JOptionPane.showOptionDialog(
            frame,
            "You failed with " + percentage + "%.\nRestart?",
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            new String[]{"Restart","Exit"},
            "Restart"
        );
        
        if(choice == 0){
            restartGame();
        } else {
            System.exit(0);
        }
    }
    
    public boolean askQuestion4() {
        try {
            String answer = JOptionPane.showInputDialog(
                frame,
                "Question 1:\nWhat is (5 + 7)*8?",
                "Question",
                JOptionPane.QUESTION_MESSAGE
            );
            if (answer == null)
                throw new Exception("No input");
            int userAnswer = Integer.parseInt(answer);
            return userAnswer == 96;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid input. Please enter a NUMBER.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid input. Please try again.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
    
    public boolean askQuestion5() {
        try {
            String answer = JOptionPane.showInputDialog(
                frame,
                "Question 2:\nWhat is the capital of China?",
                "Question",
                JOptionPane.QUESTION_MESSAGE
            );
            if(answer == null || answer.trim().isEmpty()){
                throw new Exception("Empty input");
            }
            return answer.equalsIgnoreCase("Beijing");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid input. Please type a valid answer.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
    
    public boolean askQuestion8() {
        String answer = JOptionPane.showInputDialog(
            frame,
            "Question 3:\nWhat planet was taken off the original 9 planets?",
            "Question",
            JOptionPane.QUESTION_MESSAGE
        );
        if (answer == null) return false;
        return answer.equalsIgnoreCase("Pluto");
    }
    
    public void processAnswer(boolean correct){
        attempts++;

        if(correct){
            correctAnswers++;
            player.addPoints();
            JOptionPane.showMessageDialog(frame, "Correct!");
        } else {
            JOptionPane.showMessageDialog(frame, "Wrong!");
        }
    }
    
    public void moveCharacter(int newPosition) {
        character[characterPosition].setIcon(null);
        characterPosition = newPosition;
        player.setPosition(newPosition);
        character[newPosition].setIcon(playerIcon);

        int tile = characterPlace[newPosition];

        switch(tile) {

            case 3:
                JOptionPane.showMessageDialog(frame,
                    "OBJECTIVE:\n\n"
                  + "1. Answer all 3 questions.\n"
                  + "2. Get a perfect score.\n"
                  + "3. Get the key.\n"
                  + "4. Reach the gate.",
                    "Game Objectives",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
                
                
            case 4:
                if(!q4Answered){
                    boolean correct = askQuestion4();
                    processAnswer(correct);
                    q4Answered = true;
                }
                break;

            case 5:
                if(!q5Answered){
                    boolean correct = askQuestion5();
                    processAnswer(correct);
                    q5Answered = true;
                }
                break;

            case 8:
                if(!q8Answered){
                    boolean correct = askQuestion8();
                    processAnswer(correct);
                    q8Answered = true;
                }
                break;

            case 6:
                if(attempts < 3){
                    JOptionPane.showMessageDialog(frame,
                        "Answer all 3 questions first!");
                    return;
                }

                int percentage = (correctAnswers * 100) / 3;

                if(percentage >= 60){
                    hasKey = true;
                    JOptionPane.showMessageDialog(frame,
                        "You passed with " + percentage + "%!\nYou obtained the key!");
                } else {
                    showFailScreen(percentage);
                }
                break;

            case 7:
                if(hasKey){
                    JOptionPane.showMessageDialog(frame,
                        "LEVEL COMPLETE!\nFinal Score: "
                        + ((correctAnswers*100)/3) + "%");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame,
                        "The gate is locked.\nGet the key first!");
                }
                break;
        }
    }
    
    private void handleInteraction(int tile) {
        switch(tile) {
            case 3:
                JOptionPane.showMessageDialog(frame,
                    "OBJECTIVE:\n\n"
                  + "1. Answer all 3 questions.\n"
                  + "2. Get a perfect score.\n"
                  + "3. Get the key.\n"
                  + "4. Reach the gate.",
                    "Game Objectives",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
                
            case 4:
                if(!q4Answered){
                    boolean correct = askQuestion4();
                    processAnswer(correct);
                    q4Answered = true;
                }
                break;
                
            case 5:
                if(!q5Answered){
                    boolean correct = askQuestion5();
                    processAnswer(correct);
                    q5Answered = true;
                }
                break;
                
            case 8:
                if(!q8Answered){
                    boolean correct = askQuestion8();
                    processAnswer(correct);
                    q8Answered = true;
                }
                break;
                
            case 6:
                if(attempts < 3){
                    JOptionPane.showMessageDialog(frame,
                        "Answer all 3 questions first!");
                    return;
                }
                
                int percentage = (correctAnswers * 100) / 3;
                
                if(percentage >= 60){
                    hasKey = true;
                    JOptionPane.showMessageDialog(frame,
                        "You passed with " + percentage + "%!\nYou obtained the key!");
                } else {
                    showFailScreen(percentage);
                }
                break;
                
            case 7:
                if(hasKey){
                    JOptionPane.showMessageDialog(frame,
                        "LEVEL COMPLETE!\nFinal Score: "
                        + ((correctAnswers*100)/3) + "%");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame,
                        "The gate is locked.\nGet the key first!");
                }
                break;
        }
    }

    
    private void restartGame(){
        correctAnswers = 0;
        attempts = 0;
        hasKey = false;
        
        q4Answered = false;
        q5Answered = false;
        q8Answered = false;
        
        player.reset();
        
        character[characterPosition].setIcon(null);
        characterPosition = startPosition;
        character[characterPosition].setIcon(playerIconfront);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            int newPosition = characterPosition;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (characterPosition % mapWidth != mapWidth - 1)
                    newPosition = characterPosition + 1;
                playerIcon = playerIconright;
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (characterPosition % mapWidth != 0)
                    newPosition = characterPosition - 1;
                playerIcon = playerIconleft;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (characterPosition + mapWidth < mapWidth * mapHeight)
                    newPosition = characterPosition + mapWidth;
                playerIcon = playerIconfront;
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (characterPosition - mapWidth >= 0)
                    newPosition = characterPosition - mapWidth;
                playerIcon = playerIconback;
            }
            else {
                throw new IllegalArgumentException("Invalid key");
            }
            if (newPosition < 0 || newPosition >= characterPlace.length)
                return;
            int tile = characterPlace[newPosition];
            if (tile == 1)
                return;
            if (tile == 3 || tile == 4 || tile == 5 || tile == 6 || tile == 7 || tile == 8) {
                handleInteraction(tile);
                return;
            }
            if (newPosition != characterPosition)
                moveCharacter(newPosition);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid key. Use ARROW KEYS to move.",
                "Control Error",
                JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                frame,
                "An unexpected error occurred. Please try again.",
                "Game Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    public static void main(String[] args) {
            PD7G1 game = new PD7G1();
            game.setFrame();
        }

}