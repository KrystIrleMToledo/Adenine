/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PD8TOTAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class sumGUI2 implements KeyListener, MouseListener {
    class InvalidKeyException extends Exception {
        public InvalidKeyException(String message) {
            super(message);
        }
    }
    JFrame frame;
    ImageIcon floorTile, brickVendingMachine, singleTable, fourTopTable;
    ImageIcon cashierCounterHalf1, cashierCounterHalf2, cashierCounterHalf3, cashierCounterHalf4;
    ImageIcon playerIcon, playerIconfront, playerIconfront2, playerIconback, playerIconback2, playerIconleft, playerIconright, fork;
    ImageIcon playerIconleft2, playerIconright2;
    String playerType = "boy";

    JLabel tiles[];
    JLabel character[];
    int mapLayout[];
    int centerloc;
    int characterPlace[];
    int characterPosition;
    int mapWidth=12;
    int mapHeight=12;
    int frameWidth=900;
    int frameHeight=900;
    int questionIndex = 0;
    int characterMode = 1;
    int direction = 1; // 0=UP, 1=DOWN, 2=LEFT, 3=RIGHT
    boolean tableUsed[];
    Timer animTimer;
    boolean moving = false;
    int animFrame = 0;

    String[][] questions = {
        {"Who bent the fork under the table?", "johan", "bon", "ant", "ahki", "1"},
        {"Who’s the best at Stickman Party?", "paula", "zionne", "reilly", "marlowe", "3"},
        {"Who is the fattest sumo wrestler?", "paula", "reilly", "marlowe", "zionne", "0"},
        {"Who doesn't know how to play memory games?", "marlowe", "eoan", "reeve", "zionne", "0"},
        {"Who is Batch 28 batch governor?", "ahki", "ashley", "ralph", "danika", "2"},
        {"Who is the campus director?", "sir jonald", "maam carrie", "maam jearvy", "sir jeff", "0"},
        {"Who is the president of adenine?", "bon", "reeve", "lance", "ahki", "3"},
        {"Who's the shortest ever?", "reilly", "marlowe", "paula", "zionne", "0"},
        {"What goes down always comes up?", "bird", "balloon", "67", "air", "2"}
    };
   
    private void loadPlayerType() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("substitute.txt"));
            String line = br.readLine();
            if (line != null) {
                playerType = line.trim().toLowerCase();
            }
            br.close();
        } catch (Exception e) {
            System.out.println("substitute.txt not found, defaulting to boy");
            playerType = "boy";
        }
    }

    public sumGUI2(){
        loadPlayerType(); // ADD THIS
        frame=new JFrame("Canteen Map Viewer");
        characterPosition=-1;
       
        int tileSize = frameWidth/mapWidth;
       
        floorTile = new ImageIcon(new ImageIcon("Images/Images/mainfloor.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        brickVendingMachine = new ImageIcon(new ImageIcon("Images/Images/brickpart.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        singleTable = new ImageIcon(new ImageIcon("Images/Images/table1.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        fourTopTable = new ImageIcon(new ImageIcon("Images/Images/table4.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        cashierCounterHalf1 = new ImageIcon(new ImageIcon("Images/Images/cashierhalf1.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        cashierCounterHalf2 = new ImageIcon(new ImageIcon("Images/Images/cashierhalf2.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        cashierCounterHalf3 = new ImageIcon(new ImageIcon("Images/Images/cashierhalf3.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        cashierCounterHalf4 = new ImageIcon(new ImageIcon("Images/Images/cashierhalf4.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
       
        if (playerType.equals("girl")) {

            playerIconfront = playerIcon = new ImageIcon(
                new ImageIcon("Images/Images1/girlwalk1.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconfront2 = new ImageIcon(
                new ImageIcon("Images/Images1/girlwalk2.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconback = new ImageIcon(
                new ImageIcon("Images/Images1/girlwalk5.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconback2 = new ImageIcon(
                new ImageIcon("Images/Images1/girlwalk6.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconright = new ImageIcon(
                new ImageIcon("Images/Images1/girlwalk3.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconleft = new ImageIcon(
                new ImageIcon("Images/Images1/girlwalk4.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconright2 = new ImageIcon(
                new ImageIcon("Images/Images1/girlwalk7.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconleft2 = new ImageIcon(
                new ImageIcon("Images/Images1/girlwalk8.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

        } else {

            playerIconfront = playerIcon = new ImageIcon(
                new ImageIcon("Images/Images1/boywalk1.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconfront2 = new ImageIcon(
                new ImageIcon("Images/Images1/boywalk2.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconback = new ImageIcon(
                new ImageIcon("Images/Images1/boywalk5.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconback2 = new ImageIcon(
                new ImageIcon("Images/Images1/boywalk6.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconright = new ImageIcon(
                new ImageIcon("Images/Images1/boywalk3.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconleft = new ImageIcon(
                new ImageIcon("Images/Images1/boywalk4.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconright2 = new ImageIcon(
                new ImageIcon("Images/Images1/boywalk7.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );

            playerIconleft2 = new ImageIcon(
                new ImageIcon("Images/Images1/boywalk8.png").getImage()
                .getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );
        }
       
        fork = new ImageIcon(new ImageIcon("Images/Images/fork.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        try {
            character=new JLabel[mapWidth*mapHeight];
            characterPlace=new int[]{
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,1,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,0,0
            };
            if(characterPlace.length != mapWidth * mapHeight){
                throw new ArrayIndexOutOfBoundsException("Size is incorrect");
            }
            for(int i=0;i<character.length;i++){
                if(characterPlace[i]==1){
                    character[i]=new JLabel(playerIcon);
                    characterPosition=i;
                }
                else character[i]=new JLabel();
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(frame,"World does not fit size","Error",JOptionPane.ERROR_MESSAGE);
        }
        try {
            tiles=new JLabel[mapWidth*mapHeight];
            tableUsed = new boolean[mapWidth*mapHeight];
            mapLayout=new int[]{
                1,1,8,9,8,9,8,9,8,9,1,1,
                1,1,1,1,1,1,1,1,1,1,1,1,
                2,1,4,3,1,3,3,1,10,3,1,7,
                2,1,3,3,1,3,4,1,3,3,1,6,
                2,1,1,1,1,1,1,1,1,1,1,1,
                1,1,3,3,1,4,3,1,3,3,1,1,
                1,1,4,3,1,3,3,1,3,4,1,1,
                1,1,1,1,1,1,1,1,1,1,1,1,
                1,3,4,1,3,3,1,3,4,1,1,7,
                1,3,3,1,4,3,1,3,3,1,1,6,
                1,1,1,1,1,1,1,1,1,1,1,1,
                1,1,3,3,1,3,3,1,3,4,1,1
            };
            if(mapLayout.length != mapWidth * mapHeight){
                throw new ArrayIndexOutOfBoundsException("Size is incorrect");
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(frame,"World does not fit size","Error",JOptionPane.ERROR_MESSAGE);
        }
        for (int i = 0; i < tiles.length; i++) {
            if (mapLayout[i] == 1)
                tiles[i] = new JLabel(floorTile);
            else if (mapLayout[i] == 2)
                tiles[i] = new JLabel(brickVendingMachine);
            else if (mapLayout[i] == 3)
                tiles[i] = new JLabel(singleTable);
            else if (mapLayout[i] == 4)
                tiles[i] = new JLabel(fourTopTable);
            else if (mapLayout[i] == 6)
                tiles[i] = new JLabel(cashierCounterHalf1);
            else if (mapLayout[i] == 7)
                tiles[i] = new JLabel(cashierCounterHalf2);
            else if (mapLayout[i] == 8)
                tiles[i] = new JLabel(cashierCounterHalf3);
            else if (mapLayout[i] == 9)
                tiles[i] = new JLabel(cashierCounterHalf4);
            else if (mapLayout[i] == 10) {
                tiles[i] = new JLabel(singleTable);
                centerloc = i;
            }
            else tiles[i] = new JLabel(floorTile);
            tiles[i].addMouseListener(this);
        }
            animTimer = new Timer(150, e -> {

            if (moving) {
                animFrame ^= 1;
            } else {
                animFrame = 0;
            }

            // update sprite based on direction + frame
            if (characterPosition != -1) {

                if (direction == 3)
                    playerIcon = (animFrame == 0) ? playerIconright : playerIconright2;

                else if (direction == 2)
                    playerIcon = (animFrame == 0) ? playerIconleft : playerIconleft2;

                else if (direction == 1)
                    playerIcon = (animFrame == 0) ? playerIconfront : playerIconfront2;

                else if (direction == 0)
                    playerIcon = (animFrame == 0) ? playerIconback : playerIconback2;

                character[characterPosition].setIcon(playerIcon);
            }

            frame.repaint();
        });

        animTimer.start();
        showInstructions();
    }

    public void showInstructions() {
        JOptionPane.showMessageDialog(frame, "📝 INSTRUCTIONS\n\nFind the fork!\nAnswer questions to check tables.\nStand next to a table to click it.");
    }

    public boolean askQuestion() {
        if (questionIndex >= questions.length) return false;
        String[] q = questions[questionIndex];
        String[] options = {"A. "+q[1],"B. "+q[2],"C. "+q[3],"D. "+q[4]};
        int correct = Integer.parseInt(q[5]);

        int choice = JOptionPane.showOptionDialog(frame, q[0], "Question "+(questionIndex+1),
                0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == correct) {
            questionIndex++;
            if (questionIndex == questions.length) {
                tiles[centerloc].setIcon(fork);
                JOptionPane.showMessageDialog(frame, "🎉 You found the fork!");
                frame.dispose();
                new GroupPD().gr9();
            } else {
                JOptionPane.showMessageDialog(frame, "✅ Correct!");
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(frame, "❌ Wrong! Progress reset.");
            questionIndex = 0;
            return false;
        }
    }

    public boolean isAdjacent(int i) {
        int pr = characterPosition / mapWidth;
        int pc = characterPosition % mapWidth;
        int tr = i / mapWidth;
        int tc = i % mapWidth;
        return (pr == tr && Math.abs(pc - tc) == 1) || (pc == tc && Math.abs(pr - tr) == 1);
    }
   
    private void directionSprite(int dir) {

        direction = dir;

        if (dir == 3) { // RIGHT
            playerIcon = (animFrame == 0) ? playerIconright : playerIconright2;
        }

        else if (dir == 2) { // LEFT
            playerIcon = (animFrame == 0) ? playerIconleft : playerIconleft2;
        }

        else if (dir == 1) { // DOWN
            playerIcon = (animFrame == 0) ? playerIconfront : playerIconfront2;
        }

        else if (dir == 0) { // UP
            playerIcon = (animFrame == 0) ? playerIconback : playerIconback2;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int newPosition = characterPosition;

        try {
            // Block WASD
            if (e.getKeyCode() == KeyEvent.VK_W ||
                e.getKeyCode() == KeyEvent.VK_A ||
                e.getKeyCode() == KeyEvent.VK_S ||
                e.getKeyCode() == KeyEvent.VK_D) {

                throw new InvalidKeyException("Use arrow keys only.");
            }

            moving = false; // reset movement state

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                if (characterPosition % mapWidth != mapWidth - 1) {
                    newPosition = characterPosition + 1;
                    direction = 3;
                    moving = true;
                }

            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                if (characterPosition % mapWidth != 0) {
                    newPosition = characterPosition - 1;
                    direction = 2;
                    moving = true;
                }

            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                if (characterPosition + mapWidth < mapWidth * mapHeight) {
                    newPosition = characterPosition + mapWidth;
                    direction = 1;
                    moving = true;
                }

            } else if (e.getKeyCode() == KeyEvent.VK_UP) {

                if (characterPosition - mapWidth >= 0) {
                    newPosition = characterPosition - mapWidth;
                    direction = 0;
                    moving = true;
                }
            }

        } catch (InvalidKeyException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }

        // BLOCK CHECK
        if (mapLayout[newPosition] != 1) {
            return;
        }

        // MOVE PLAYER
        if (newPosition != characterPosition) {

            character[characterPosition].setIcon(null);
            characterPosition = newPosition;
            character[characterPosition].setIcon(playerIcon);
        }
    }
   
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < tiles.length; i++) {
            if (e.getSource() == tiles[i] && (mapLayout[i] == 4 || mapLayout[i] == 3 || mapLayout[i] == 10)) {
                if (tableUsed[i]) {
                    JOptionPane.showMessageDialog(frame, "⚠️ Already checked.");
                    return;
                }
                if (isAdjacent(i)) {
                    if (askQuestion()) tableUsed[i] = true;
                } else {
                    JOptionPane.showMessageDialog(frame, "❌ Move closer!");
                }
                break;
            }
        }
    }

    @Override public void mousePressed(MouseEvent e){}
    @Override public void mouseReleased(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e){}
    @Override public void mouseExited(MouseEvent e){}

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(this);
        frame.requestFocus();
    }
    public boolean gr8b() {
        SwingUtilities.invokeLater(this::setFrame);
        return true;
    }
    public static void main(String[] args) {
        new sumGUI2().setFrame();
    }
}
