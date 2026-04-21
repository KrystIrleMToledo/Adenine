package Quarter4;

import Quarter2.GraphPaperLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Group6_PD4 implements KeyListener {
    JFrame frame;
    ImageIcon board1, board2, corner, door, floor, gray, green, seat, table, w1, w2, boyfront, boyback, blob, chest;
    ImageIcon boyidle1;
    ImageIcon boyidle2;
    ImageIcon boyidle3;
    ImageIcon boyidle4;
    ImageIcon boywalk1;
    ImageIcon boywalk2;
    ImageIcon boywalk3;
    ImageIcon boywalk4;
    ImageIcon boywalk5;
    ImageIcon boywalk6;
    ImageIcon boywalk7;
    ImageIcon boywalk8;
    
    JLabel tiles[];
    JLabel character[];
    int mapLayout[];
    int characterPlace[];
    int mapWidth = 12;
    int mapHeight = 12;
    int frameWidth = 450;
    int frameHeight = 450;
    int characterPosition;
    int characterMode;
    // QUIZ + BATTLE
    int blobHP = 3;
    Integer[] collisionTiles;
    boolean hasCollision = false;
    boolean quizFinished = false;
    boolean chestOpened = false;

    JProgressBar blobHPBar;

    String[] questions = {
        "What is the basic unit of life?",
        "Which organelle is the powerhouse of the cell?",
        "What macromolecule are enzymes?"
    };

    String[][] choices = {
        {"Tissue", "Cell", "Organ", "System"},
        {"Nucleus", "Ribosome", "Mitochondria", "Vacuole"},
        {"Lipid", "Carbohydrate", "Protein", "Nucleic Acid"}
    };

    int[] answers = {1, 2, 2};

    public Group6_PD4() {
        frame = new JFrame("BIO LAB");
        characterPosition = -1;
        characterMode = 0;
        
        board1 = new ImageIcon("Images/board1.png");
        board2 = new ImageIcon("Images/board2.png");
        corner = new ImageIcon("Images/corner.png");
        door = new ImageIcon("Images/door.png");
        floor = new ImageIcon("Images/floor.png");
        gray = new ImageIcon("Images/gray.png");
        green = new ImageIcon("Images/green.png");
        seat = new ImageIcon("Images/seat.png");
        table = new ImageIcon("Images/table.png");
        w1 = new ImageIcon("Images/w1.png");
        w2 = new ImageIcon("Images/w2.png");
        boyfront = new ImageIcon("Images/boyfront.png");
        boyback = new ImageIcon("Images/boyback.png");
        blob = new ImageIcon("Images/blob1.png");
        chest = new ImageIcon("Images/chest.png");
        
        boyidle1 = new ImageIcon("Images/boyidle1.png");
        boyidle2 = new ImageIcon("Images/boyidle2.png");
        boyidle3 = new ImageIcon("Images/boyidle3.png");
        boyidle4 = new ImageIcon("Images/boyidle4.png");
        boywalk1 = new ImageIcon("Images/boywalk1.png");
        boywalk2 = new ImageIcon("Images/boywalk2.png");
        boywalk3 = new ImageIcon("Images/boywalk3.png");
        boywalk4 = new ImageIcon("Images/boywalk4.png");
        boywalk5 = new ImageIcon("Images/boywalk5.png");
        boywalk6 = new ImageIcon("Images/boywalk6.png");
        boywalk7 = new ImageIcon("Images/boywalk7.png");
        boywalk8 = new ImageIcon("Images/boywalk8.png");
                
        board1 = new ImageIcon(board1.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        board2 = new ImageIcon(board2.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        corner = new ImageIcon(corner.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        door = new ImageIcon(door.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        floor = new ImageIcon(floor.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        gray = new ImageIcon(gray.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        green = new ImageIcon(green.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        seat = new ImageIcon(seat.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        table = new ImageIcon(table.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        w1 = new ImageIcon(w1.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        w2 = new ImageIcon(w2.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boyfront = new ImageIcon(boyfront.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boyback = new ImageIcon(boyback.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        blob = new ImageIcon(blob.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        chest = new ImageIcon(chest.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        
        boyidle1 = new ImageIcon(boyidle1.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boyidle2 = new ImageIcon(boyidle2.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boyidle3 = new ImageIcon(boyidle3.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boyidle4 = new ImageIcon(boyidle4.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boywalk1 = new ImageIcon(boywalk1.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boywalk2 = new ImageIcon(boywalk2.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boywalk3 = new ImageIcon(boywalk3.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boywalk4 = new ImageIcon(boywalk4.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boywalk5 = new ImageIcon(boywalk5.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boywalk6 = new ImageIcon(boywalk6.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boywalk7 = new ImageIcon(boywalk7.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        boywalk8 = new ImageIcon(boywalk8.getImage().getScaledInstance(frameWidth / mapWidth, frameHeight / mapHeight, Image.SCALE_DEFAULT));
        
        
        //map
        mapLayout = new int[]{
            2,3,5,5,5,5,5,5,5,5,3,2,
            6,4,4,4,4,4,4,4,4,4,4,6,
            6,4,7,8,7,4,7,8,7,4,0,6,
            6,4,7,8,7,4,7,8,7,4,0,6,
            6,4,7,8,7,4,7,8,7,4,0,6,
            6,4,4,4,4,11,4,4,4,4,0,6,
            6,4,4,4,4,4,4,4,4,4,1,6,
            6,4,7,8,7,4,7,8,7,4,1,6,
            6,4,7,8,7,4,7,8,7,4,1,6,
            6,4,7,8,7,4,7,8,7,4,1,6,
            6,4,4,4,4,4,4,4,4,4,4,6,
            2,9,10,9,10,9,10,9,10,9,10,2
        };

        //character
        characterPlace = new int[]{
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,1,0,0,0,0,0,0,0,0,0,0,
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

        tiles = new JLabel[mapWidth * mapHeight];
        character = new JLabel[mapWidth * mapHeight];

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new JLabel();
            character[i] = new JLabel();

            if (characterPlace[i] == 1) {
                character[i].setIcon(boyidle1);
                characterPosition = i;
            }

            switch (mapLayout[i]) {
                case 0 -> tiles[i].setIcon(board1);
                case 1 -> tiles[i].setIcon(board2);
                case 2 -> tiles[i].setIcon(corner);
                case 3 -> tiles[i].setIcon(door);
                case 4 -> tiles[i].setIcon(floor);
                case 5 -> tiles[i].setIcon(gray);
                case 6 -> tiles[i].setIcon(green);
                case 7 -> tiles[i].setIcon(seat);
                case 8 -> tiles[i].setIcon(table);
                case 9 -> tiles[i].setIcon(w1);
                case 10 -> tiles[i].setIcon(w2);
                case 11 -> tiles[i].setIcon(blob);
            }
        }
        collisionTiles = new Integer[]{7, 8, 2, 5, 6, 9, 10};

        // HP BAR
        blobHPBar = new JProgressBar(0, 3);
        blobHPBar.setValue(blobHP);
        blobHPBar.setStringPainted(true);
        blobHPBar.setVisible(false);
    }

    //frame
    public void setFrame() {

        frame.setLayout(null);

        JLayeredPane lp = new JLayeredPane();
        lp.setBounds(0, 25, 450, 450);

        JPanel tilePanel = new JPanel(new GraphPaperLayout(new Dimension(mapWidth, mapHeight)));
        tilePanel.setBounds(0, 0, 450, 450);

        JPanel charPanel = new JPanel(new GraphPaperLayout(new Dimension(mapWidth, mapHeight)));
        charPanel.setBounds(0, 0, 450, 450);
        charPanel.setOpaque(false);

        int x = 0, y = 0;
        for (int i = 0; i < tiles.length; i++) {
            tilePanel.add(tiles[i], new Rectangle(x, y, 1, 1));
            charPanel.add(character[i], new Rectangle(x, y, 1, 1));
            if (++x % mapWidth == 0) { x = 0; y++; }
        }

        lp.add(tilePanel, Integer.valueOf(0));
        lp.add(charPanel, Integer.valueOf(1));

        blobHPBar.setBounds(120, 5, 200, 15);
        frame.add(blobHPBar);
        frame.add(lp);

        frame.setSize(450, 520);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addKeyListener(this);
    }
    public void collision() {
        
    }

    //movement
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            try{
                    hasCollision = Arrays.asList(collisionTiles).contains(mapLayout[characterPosition+1]);
                }catch (Exception k){
                    System.out.println("collider error");
                }
            if((characterPosition+1)%mapWidth != 0 && hasCollision == false){
                character[characterPosition].setIcon(null);
                if(characterMode==0){
                    character[characterPosition+1].setIcon(boywalk3);
                    characterMode=1;
                }
                else{
                    character[characterPosition+1].setIcon(boywalk7);
                    characterMode=0;
                }
                characterPosition++;
            } else {
                character[characterPosition].setIcon(boyidle3);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            try{
                    hasCollision = Arrays.asList(collisionTiles).contains(mapLayout[characterPosition-1]);
                }catch (Exception k){
                    System.out.println("collider error");
                }
            if((characterPosition-1)%mapWidth != 0 && hasCollision == false){
                character[characterPosition].setIcon(null);
                if(characterMode==0){
                    character[characterPosition-1].setIcon(boywalk4);
                    characterMode=1;
                }
                else{
                    character[characterPosition-1].setIcon(boywalk8);
                    characterMode=0;
                }
                characterPosition--;
            } else {
                character[characterPosition].setIcon(boyidle2);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            try{
                    hasCollision = Arrays.asList(collisionTiles).contains(mapLayout[characterPosition+mapWidth]);
                }catch (Exception k){
                    System.out.println("collider error");
                }
            if((characterPosition+mapWidth < mapWidth*mapHeight) && hasCollision == false){
                character[characterPosition].setIcon(null);
                if(characterMode==0){
                    character[characterPosition+=mapWidth].setIcon(boywalk1);
                    characterMode=1;
                }
                else{
                    character[characterPosition+=mapWidth].setIcon(boywalk2);
                    characterMode=0;
                }
            } else {
                character[characterPosition].setIcon(boyidle1);
            }
        };
        if (e.getKeyCode() == KeyEvent.VK_UP){   
            try{
                    hasCollision = Arrays.asList(collisionTiles).contains(mapLayout[characterPosition-mapWidth]);
                }catch (Exception k){
                    System.out.println("collider error");
                }
            if((characterPosition-mapWidth > -1) && hasCollision == false){
                character[characterPosition].setIcon(null);
                if(characterMode==0){
                    character[characterPosition-=mapWidth].setIcon(boywalk6);
                    characterMode=1;
                }
                else{
                    character[characterPosition-=mapWidth].setIcon(boywalk5);
                    characterMode=0;
                }
            } else {
                character[characterPosition].setIcon(boyidle4);
            }
        }
        if (mapLayout[characterPosition] == 11 && !quizFinished) {
            startBlobQuiz();
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_E && mapLayout[characterPosition] == 12) {
            openChest();
        }
    }
    
    //quiz
    public void startBlobQuiz() {
        blobHPBar.setVisible(true);
        JOptionPane.showMessageDialog(frame,
            "A container breaks! The Blob attacks!",
            "BIO LAB",
            JOptionPane.WARNING_MESSAGE);
        
        for (int i = 0; i < questions.length; i++) {
            String ans = (String) JOptionPane.showInputDialog(
                frame, questions[i], "BIO QUIZ",
                JOptionPane.QUESTION_MESSAGE, null,
                choices[i], choices[i][0]);

            if (Arrays.asList(choices[i]).indexOf(ans) == answers[i]) {
                blobHP--;
                blobHPBar.setValue(blobHP);
            } else {
                JOptionPane.showMessageDialog(frame,"Wrong!");
                i--;
            }

            if (blobHP == 0) {
                quizFinished = true;
                blobHPBar.setVisible(false);
                JOptionPane.showMessageDialog(frame,"Blob defeated! A chest appears!");
                spawnChest();
                break;
            }
        }
    }

    public void spawnChest() {
        for (int i = 0; i < mapLayout.length; i++) {
            if (mapLayout[i] == 11) {
                mapLayout[i] = 12;
                tiles[i].setIcon(chest);
            }
        }
    }

    public void openChest() {
        if (!chestOpened) {
            chestOpened = true;
            JOptionPane.showMessageDialog(frame, "You obtained the KEY! Moving on to the Flower Garden!"); //
            new Thread(() -> {
                boolean connect = new Group6_PD8().connect();
            }).start();
        }
    }

    private ImageIcon scale(String path) {
        ImageIcon img = new ImageIcon(path);
        return new ImageIcon(img.getImage().getScaledInstance(450 / mapWidth, 450 / mapHeight, Image.SCALE_DEFAULT));
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new Group6_PD4().setFrame();
    }
}
