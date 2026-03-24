import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//MEMBERS: OSUA,GOLPE,LIBARDOS
public class ClinicMap implements KeyListener {
    JFrame frame;
    ImageIcon wall, floor, glassDoor, curtain, plant;
    ImageIcon p1, p2, p3, p4;
    ImageIcon weight, bed1, bed2, bed3, bed4;
    ImageIcon c1, c2, c3, c4, c5, c6, c7, c8;
    ImageIcon m1, m2;
    ImageIcon v1, v2, v3, v4, v5, v6, v7;
    
    ImageIcon playerIcon; 
    ImageIcon[] walkUp, walkDown, walkLeft, walkRight;
    int stepCount = 0;

    JLabel tiles[];
    JLabel character[];
    int mapLayout[];
    int mapWidth = 17;
    int mapHeight = 17;
    int frameWidth = 900;
    int frameHeight = 900;
    int characterPosition;

    boolean injured = true;
    boolean syringeUsed = false;

    int viewWidth = 9;
    int viewHeight = 9;
    int camTileW;
    int camTileH;

    public ClinicMap() {
        frame = new JFrame();
        characterPosition = -1;

        walkLeft = new ImageIcon[]{
            new ImageIcon("Images1/girlwalk8.png"), 
            new ImageIcon("Images1/girlidle3.png"), 
            new ImageIcon("Images1/girlwalk6.png")
        };
        walkRight = new ImageIcon[]{
            new ImageIcon("Images1/girlwalk5.png"), 
            new ImageIcon("Images1/girlidle4.png"), 
            new ImageIcon("Images1/girlwalk7.png")
        };
        walkUp = new ImageIcon[]{
            new ImageIcon("Images1/girlwalk3.png"), 
            new ImageIcon("Images1/girlidle2.png"), 
            new ImageIcon("Images1/girlwalk4.png")
        };
        walkDown = new ImageIcon[]{
            new ImageIcon("Images1/girlwalk1.png"), 
            new ImageIcon("Images1/girlidle1.png"), 
            new ImageIcon("Images1/girlwalk2.png")
        };

        wall = new ImageIcon("Images1/walls (frame).png");
        floor = new ImageIcon("Images1/floor.png");
        glassDoor = new ImageIcon("Images1/glass door.png");
        curtain = new ImageIcon("Images1/curtain.png");
        p1 = new ImageIcon("Images1/plant1.png");
        p2 = new ImageIcon("Images1/plant2.png");
        p3 = new ImageIcon("Images1/plant3.png");
        p4 = new ImageIcon("Images1/plant4.png");
        weight = new ImageIcon("Images1/weight.png");
        bed1 = new ImageIcon("Images1/bed1.png");
        bed2 = new ImageIcon("Images1/bed2.png");
        bed3 = new ImageIcon("Images1/bed3.png");
        bed4 = new ImageIcon("Images1/bed4.png");
        c1 = new ImageIcon("Images1/counter1.png");
        c2 = new ImageIcon("Images1/counter2.png");
        c3 = new ImageIcon("Images1/counter3.png");
        c4 = new ImageIcon("Images1/counter4.png");
        c5 = new ImageIcon("Images1/counter5.png");
        c6 = new ImageIcon("Images1/counter6.png");
        c7 = new ImageIcon("Images1/counter7.png");
        c8 = new ImageIcon("Images1/counter8.png");
        m1 = new ImageIcon("Images1/measuring1.png");
        m2 = new ImageIcon("Images1/measuring2.png");
        plant = new ImageIcon("Images1/plant.png");
        v1 = new ImageIcon("Images1/villain1.png");
        v2 = new ImageIcon("Images1/villain2.png");
        v3 = new ImageIcon("Images1/villain3.png");
        v4 = new ImageIcon("Images1/villain4.png");
        v5 = new ImageIcon("Images1/villain5.png");
        v6 = new ImageIcon("Images1/villain6.png");
        v7 = new ImageIcon("Images1/villain7.png");

        int tw = frameWidth / mapWidth;
        int th = frameHeight / mapHeight;
        camTileW = frameWidth / viewWidth;
        camTileH = frameHeight / viewHeight;


        for (int i = 0; i < 3; i++) {
            walkLeft[i] = new ImageIcon(walkLeft[i].getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
            walkRight[i] = new ImageIcon(walkRight[i].getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
            walkUp[i] = new ImageIcon(walkUp[i].getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
            walkDown[i] = new ImageIcon(walkDown[i].getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        }
  
        playerIcon = walkDown[1];

        wall = new ImageIcon(wall.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        floor = new ImageIcon(floor.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        glassDoor = new ImageIcon(glassDoor.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        curtain = new ImageIcon(curtain.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        p1 = new ImageIcon(p1.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        p2 = new ImageIcon(p2.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        p3 = new ImageIcon(p3.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        p4 = new ImageIcon(p4.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        weight = new ImageIcon(weight.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        bed1 = new ImageIcon(bed1.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        bed2 = new ImageIcon(bed2.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        bed3 = new ImageIcon(bed3.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        bed4 = new ImageIcon(bed4.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        c1 = new ImageIcon(c1.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        c2 = new ImageIcon(c2.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        c3 = new ImageIcon(c3.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        c4 = new ImageIcon(c4.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        c5 = new ImageIcon(c5.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        c6 = new ImageIcon(c6.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        c7 = new ImageIcon(c7.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        c8 = new ImageIcon(c8.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        m1 = new ImageIcon(m1.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        m2 = new ImageIcon(m2.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        plant = new ImageIcon(plant.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        v1 = new ImageIcon(v1.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        v2 = new ImageIcon(v2.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        v3 = new ImageIcon(v3.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        v4 = new ImageIcon(v4.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        v5 = new ImageIcon(v5.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        v6 = new ImageIcon(v6.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        v7 = new ImageIcon(v7.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));

        character = new JLabel[mapWidth * mapHeight];
        int[] characterPlace = {
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        };
        for(int i = 0; i < character.length; i++){
            if(characterPlace[i] == 1){
                character[i] = new JLabel(playerIcon);
                characterPosition = i;
            } else {
                character[i] = new JLabel();
            }
        }

        tiles = new JLabel[mapWidth * mapHeight];
        mapLayout = new int[]{
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,
            0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,
            0,11,12,13,14,1,1,1,1,1,3,3,3,3,3,3,0,
            0,15,16,17,18,23,1,1,1,1,1,1,1,1,1,1,0,
            0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,10,0,
            0,1,1,1,1,1,1,1,1,1,3,1,1,7,8,9,0,
            2,1,1,1,1,1,1,1,1,1,3,3,3,3,3,3,0,
            2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,
            0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,10,0,
            0,1,1,1,1,1,1,1,1,1,3,1,1,7,8,9,0,
            0,1,1,1,1,1,1,1,1,1,3,3,3,3,3,3,0,
            0,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,0,
            0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,10,0,
            0,19,1,4,5,1,1,1,1,1,1,1,1,7,8,9,0,
            0,20,1,21,22,6,1,1,1,1,3,3,3,3,3,3,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
        };

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new JLabel();
            tiles[i].setOpaque(false);
            setStaticTileIcon(i); 
        }

        placeRandomSyringe();
    }

    private void setStaticTileIcon(int i) {
        switch (mapLayout[i]) {
            case 0: tiles[i].setIcon(wall); break;
            case 1: tiles[i].setIcon(floor); break;
            case 2: tiles[i].setIcon(glassDoor); break;
            case 3: tiles[i].setIcon(curtain); break;
            case 4: tiles[i].setIcon(p1); break;
            case 5: tiles[i].setIcon(p2); break;
            case 6: tiles[i].setIcon(weight); break;
            case 7: tiles[i].setIcon(bed1); break;
            case 8: tiles[i].setIcon(bed2); break;
            case 9: tiles[i].setIcon(bed3); break;
            case 10: tiles[i].setIcon(bed4); break;
            case 11: tiles[i].setIcon(c1); break;
            case 12: tiles[i].setIcon(c2); break;
            case 13: tiles[i].setIcon(c3); break;
            case 14: tiles[i].setIcon(c4); break;
            case 15: tiles[i].setIcon(c5); break;
            case 16: tiles[i].setIcon(c6); break;
            case 17: tiles[i].setIcon(c7); break;
            case 18: tiles[i].setIcon(c8); break;
            case 19: tiles[i].setIcon(m1); break;
            case 20: tiles[i].setIcon(m2); break;
            case 21: tiles[i].setIcon(p3); break;
            case 22: tiles[i].setIcon(p4); break;
            case 23: tiles[i].setIcon(plant); break;
            default: tiles[i].setIcon(floor); break;
        }
    }

    public void placeRandomSyringe() {
        boolean placed = false;
        int attempts = 0;
        
        while (!placed && attempts < 200) {
            attempts++;
            int startPos = (int) (Math.random() * mapLayout.length);
            int row = startPos / mapWidth;
            int col = startPos % mapWidth;

            if (col + 4 < mapWidth && row + 1 < mapHeight) {
                int[] row1 = {startPos, startPos + 1, startPos + 2};
                int[] row2 = {startPos + mapWidth, startPos + mapWidth + 1, startPos + mapWidth + 2, startPos + mapWidth + 3};

                boolean allClear = true;
                for (int idx : row1) if (mapLayout[idx] != 1) allClear = false;
                for (int idx : row2) if (mapLayout[idx] != 1) allClear = false;

                if (allClear) {
                    mapLayout[row1[0]] = 24; mapLayout[row1[1]] = 25; mapLayout[row1[2]] = 26;
                    mapLayout[row2[0]] = 27; mapLayout[row2[1]] = 28; mapLayout[row2[2]] = 29; mapLayout[row2[3]] = 30;

                    for (int idx : row1) tiles[idx].setIcon(getIconForType(mapLayout[idx]));
                    for (int idx : row2) tiles[idx].setIcon(getIconForType(mapLayout[idx]));
                    placed = true;
                }
            }
        }
    }

    private ImageIcon getIconForType(int type) {
        switch (type) {
            case 24: return v1; case 25: return v2; case 26: return v3;
            case 27: return v4; case 28: return v5; case 29: return v6;
            case 30: return v7;
            default: return floor;
        }
    }

    public void triggerSyringeEvent() {
        if (!injured || syringeUsed) return;

        String[] questions = {
            "1. BMI stands for?", 
            "2. Normal BMI range?", 
            "3. First aid for bleeding?",
            "4. Used to clean wounds?", 
            "5. Overweight BMI?", 
            "6. First aid for burns?",
            "7. Who needs first aid?",
            "8. What do you do when a person has a nosebleed?",
            "9. What is abrasion?",
            "10. How to treat a sprained ankle?",
            "11. Which of the following BMI ranges is considered 'Healthy Weight'?",
            "12. True/False: The same BMI categories apply equally to children and adults.",
            "13. A person weighs 70 kg and is 1.75 m tall. Calculate their BMI."
        };

        String[][] choices = {
            {"Body Mass Index", "Blood Mass Index", "Bone Mass Index"},
            {"18.5–24.9", "10–15", "30–40"},
            {"Apply pressure", "Ignore", "Wash hands only"},
            {"Alcohol", "Oil", "Perfume"},
            {"25 and above", "Below 15", "10"},
            {"Cool running water", "Ice", "Butter"},
            {"Anyone injured", "Only adults", "Doctors"},
            {"Lean them forward and pinch nose", "Tilt their head backwards", "Let the nosebleed finish on its own", "Make them drink water"},
            {"Scrape from friction", "A clean, straight cut", "Damage from heat", "Irregular tear from blunt force"},
            {"RICE (Rest, Ice, Compression, Elevation)", "Let the patient walk it off", "Tap on the ankle to check injury", "None of the above"},
            {"18.5 to 24.9", "< 18.5", "25.0 to 29.9", "> 30.0"},
            {"False", "True"},
            {"22.86 kg/m²", "20.50 kg/m²", "25.12 kg/m²", "18.90 kg/m²"}
        };

        for (int i = 0; i < questions.length; i++) {
            String correctAnswer = choices[i][0]; 
            String[] currentChoices = choices[i].clone();
            shuffleArray(currentChoices);
            
            int ans = JOptionPane.showOptionDialog(frame, questions[i], "Medical Check",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, currentChoices, null);

            if (ans == -1 || !currentChoices[ans].equals(correctAnswer)) {
                JOptionPane.showMessageDialog(frame, "Wrong answer! You are still injured.");
                return; 
            }
        }

        injured = false;
        syringeUsed = true;
        for (int i = 0; i < mapLayout.length; i++) {
            if (mapLayout[i] >= 24 && mapLayout[i] <= 30) {
                mapLayout[i] = 1;
                tiles[i].setIcon(floor);
            }
        }
        JOptionPane.showMessageDialog(frame, "You are healed!\n\nCLUE:\n\"The end leads back to the start.\"");
    }

    public void moveCharacter(int newPos, ImageIcon icon) {
 
        this.playerIcon = icon;

        if (newPos >= 0 && newPos < mapLayout.length && mapLayout[newPos] != 0) {
            if (mapLayout[newPos] >= 24 && mapLayout[newPos] <= 30) {
                triggerSyringeEvent();
                return; 
            }
            character[characterPosition].setIcon(null);
            characterPosition = newPos;
            renderView();
        } else {
            renderView();
        }
    }

    public void renderView() {
    frame.getContentPane().removeAll();
    frame.setLayout(new GridLayout(viewHeight, viewWidth));
    
    int playerRow = characterPosition / mapWidth;
    int playerCol = characterPosition % mapWidth;
    int startRow = Math.max(0, Math.min(mapHeight - viewHeight, playerRow - viewHeight / 2));
    int startCol = Math.max(0, Math.min(mapWidth - viewWidth, playerCol - viewWidth / 2));

    for (int r = 0; r < viewHeight; r++) {
        for (int c = 0; c < viewWidth; c++) {
            int mapIndex = (startRow + r) * mapWidth + (startCol + c);

            JPanel cell = new JPanel();
            cell.setLayout(new OverlayLayout(cell)); 

            JLabel background = new JLabel(scaleForCamera((ImageIcon) tiles[mapIndex].getIcon()));
 
            if (mapIndex == characterPosition) {
                JLabel player = new JLabel(scaleForCamera(playerIcon));
                cell.add(player);
            }
            
            cell.add(background); 
            frame.add(cell);
        }
    }
    frame.revalidate();
    frame.repaint();
}

    public void shuffleArray(String[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = (int)(Math.random() * (i + 1));
            String temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public ImageIcon scaleForCamera(ImageIcon icon) {
        return new ImageIcon(icon.getImage().getScaledInstance(camTileW, camTileH, Image.SCALE_DEFAULT));
    }

    public void setFrame() {
        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(this);
        renderView();
    }

    // --- UPDATED KEYPRESSED WITH EXCEPTION HANDLING ---
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            int keyCode = e.getKeyCode();
            
            // Check if the key pressed is one of the valid movement keys
            if (keyCode != KeyEvent.VK_RIGHT && keyCode != KeyEvent.VK_LEFT && 
                keyCode != KeyEvent.VK_DOWN && keyCode != KeyEvent.VK_UP) {
                
                // If it's not a movement key, we manually throw an exception
                throw new IllegalArgumentException("Invalid key pressed");
            }

            int newPos = characterPosition;
            ImageIcon nextIcon = playerIcon; 
            
            stepCount = (stepCount + 1) % 3;

            if (keyCode == KeyEvent.VK_RIGHT) {
                newPos = characterPosition + 1;
                nextIcon = walkRight[stepCount];
            } 
            else if (keyCode == KeyEvent.VK_LEFT) {
                newPos = characterPosition - 1;
                nextIcon = walkLeft[stepCount];
            } 
            else if (keyCode == KeyEvent.VK_DOWN) {
                newPos = characterPosition + mapWidth;
                nextIcon = walkDown[stepCount];
            } 
            else if (keyCode == KeyEvent.VK_UP) {
                newPos = characterPosition - mapWidth;
                nextIcon = walkUp[stepCount];
            }

            moveCharacter(newPos, nextIcon);

        } catch (IllegalArgumentException ex) {
            // This catches the error and displays the helpful message instead of crashing
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid command.", "Input Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            // General catch-all for any other unexpected errors
            System.out.println("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    
    @Override 
    public void keyReleased(KeyEvent e) {
        // Keeping keyReleased simple as main logic is now in keyPressed
    }

    public static void main(String[] args) {
        ClinicMap cm = new ClinicMap();
        cm.setFrame();
    }
}