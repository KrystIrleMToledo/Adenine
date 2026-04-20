import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

// MEMBERS: OSUA, GOLPE, LIBARDOS
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

    private long startTime;
    private int health = 3; 
    private int totalAttempts = 0;
    private final String DATA_FILE = "ClinicGameData.txt"; 

    public ClinicMap() {
        loadPersistentData();
        totalAttempts++;
        savePersistentData();
        
        startTime = System.currentTimeMillis();
        frame = new JFrame("Clinic Adventure");
        characterPosition = 137; 

        initializeIcons();

        int tw = frameWidth / mapWidth;
        int th = frameHeight / mapHeight;
        camTileW = frameWidth / viewWidth;
        camTileH = frameHeight / viewHeight;

        scaleIcons(tw, th);
        playerIcon = walkDown[1];

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
            updateTileIcon(i); 
        }

        placeRandomSyringe();
    }

    private void savePersistentData() {
        try (PrintWriter out = new PrintWriter(new FileWriter(DATA_FILE))) {
            out.println("Attempts:" + totalAttempts);
            out.println("Health:" + health);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void loadPersistentData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Attempts:")) totalAttempts = Integer.parseInt(line.split(":")[1]);
                if (line.startsWith("Health:")) health = Integer.parseInt(line.split(":")[1]);
            }
        } catch (Exception e) { health = 3; totalAttempts = 0; }
    }

    private String getHealthIcons() {
        String h = "";
        for(int i=0; i<health; i++) h += "❤️";
        return h.isEmpty() ? "DEAD" : h;
    }

    private void initializeIcons() {
        walkLeft = new ImageIcon[]{new ImageIcon("Images/girlwalk8.png"), new ImageIcon("Images/girlidle3.png"), new ImageIcon("Images/girlwalk6.png")};
        walkRight = new ImageIcon[]{new ImageIcon("Images/girlwalk5.png"), new ImageIcon("Images/girlidle4.png"), new ImageIcon("Images/girlwalk7.png")};
        walkUp = new ImageIcon[]{new ImageIcon("Images/girlwalk3.png"), new ImageIcon("Images/girlidle2.png"), new ImageIcon("Images/girlwalk4.png")};
        walkDown = new ImageIcon[]{new ImageIcon("Images/girlwalk1.png"), new ImageIcon("Images/girlidle1.png"), new ImageIcon("Images/girlwalk2.png")};
        wall = new ImageIcon("Images/walls (frame).png");
        floor = new ImageIcon("Images/floor.png");
        glassDoor = new ImageIcon("Images/glass door.png");
        curtain = new ImageIcon("Images/curtain.png");
        p1 = new ImageIcon("Images/plant1.png"); p2 = new ImageIcon("Images/plant2.png");
        p3 = new ImageIcon("Images/plant3.png"); p4 = new ImageIcon("Images/plant4.png");
        weight = new ImageIcon("Images/weight.png");
        bed1 = new ImageIcon("Images/bed1.png"); bed2 = new ImageIcon("Images/bed2.png");
        bed3 = new ImageIcon("Images/bed3.png"); bed4 = new ImageIcon("Images/bed4.png");
        c1 = new ImageIcon("Images/counter1.png"); c2 = new ImageIcon("Images/counter2.png");
        c3 = new ImageIcon("Images/counter3.png"); c4 = new ImageIcon("Images/counter4.png");
        c5 = new ImageIcon("Images/counter5.png"); c6 = new ImageIcon("Images/counter6.png");
        c7 = new ImageIcon("Images/counter7.png"); c8 = new ImageIcon("Images/counter8.png");
        m1 = new ImageIcon("Images/measuring1.png"); m2 = new ImageIcon("Images/measuring2.png");
        plant = new ImageIcon("Images/plant.png");
        v1 = new ImageIcon("Images/villain1.png"); v2 = new ImageIcon("Images/villain2.png");
        v3 = new ImageIcon("Images/villain3.png"); v4 = new ImageIcon("Images/villain4.png");
        v5 = new ImageIcon("Images/villain5.png"); v6 = new ImageIcon("Images/villain6.png");
        v7 = new ImageIcon("Images/villain7.png");
    }

    private void scaleIcons(int tw, int th) {
        wall = new ImageIcon(wall.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        floor = new ImageIcon(floor.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        glassDoor = new ImageIcon(glassDoor.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        curtain = new ImageIcon(curtain.getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        for (int i = 0; i < 3; i++) {
            walkLeft[i] = new ImageIcon(walkLeft[i].getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
            walkRight[i] = new ImageIcon(walkRight[i].getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
            walkUp[i] = new ImageIcon(walkUp[i].getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
            walkDown[i] = new ImageIcon(walkDown[i].getImage().getScaledInstance(tw, th, Image.SCALE_DEFAULT));
        }
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
    }

    private void updateTileIcon(int i) {
        ImageIcon icon;
        switch (mapLayout[i]) {
            case 0: icon = wall; break;
            case 2: icon = glassDoor; break;
            case 3: icon = curtain; break;
            case 4: icon = p1; break;
            case 5: icon = p2; break;
            case 6: icon = weight; break;
            case 7: icon = bed1; break;
            case 8: icon = bed2; break;
            case 9: icon = bed3; break;
            case 10: icon = bed4; break;
            case 11: icon = c1; break;
            case 12: icon = c2; break;
            case 13: icon = c3; break;
            case 14: icon = c4; break;
            case 15: icon = c5; break;
            case 16: icon = c6; break;
            case 17: icon = c7; break;
            case 18: icon = c8; break;
            case 19: icon = m1; break;
            case 20: icon = m2; break;
            case 21: icon = p3; break;
            case 22: icon = p4; break;
            case 23: icon = plant; break;
            case 24: icon = v1; break;
            case 25: icon = v2; break;
            case 26: icon = v3; break;
            case 27: icon = v4; break;
            case 28: icon = v5; break;
            case 29: icon = v6; break;
            case 30: icon = v7; break;
            default: icon = floor; break;
        }
        tiles[i].setIcon(icon);
    }

    public void placeRandomSyringe() {
        boolean placed = false;
        int limit = 0;
        while (!placed && limit < 100) {
            limit++;
            int pos = (int) (Math.random() * mapLayout.length);
            int r = pos / mapWidth;
            int c = pos % mapWidth;
            if (c + 4 < mapWidth && r + 1 < mapHeight && mapLayout[pos] == 1) {
                mapLayout[pos] = 24; mapLayout[pos+1] = 25; mapLayout[pos+2] = 26;
                mapLayout[pos+mapWidth] = 27; mapLayout[pos+mapWidth+1] = 28; 
                mapLayout[pos+mapWidth+2] = 29; mapLayout[pos+mapWidth+3] = 30;
                for(int i = 0; i < mapLayout.length; i++) updateTileIcon(i); 
                placed = true;
            }
        }
        renderView();
    }

    public void triggerSyringeEvent() {
        if (!injured || syringeUsed) return;
        
        String[] q = {
            "1. BMI stands for?", "2. Normal BMI range?", "3. First aid for bleeding?", 
            "4. Used to clean wounds?", "5. Overweight BMI?", "6. First aid for burns?", 
            "7. Who needs first aid?", "8. What do you do for a nosebleed?", 
            "9. What is abrasion?", "10. How to treat a sprained ankle?", 
            "11. Which BMI is 'Healthy Weight'?", "12. True/False: Same BMI for kids and adults.", 
            "13. Calc BMI: 70kg, 1.75m tall."
        };
        String[][] choicesRaw = {
            {"Body Mass Index", "Blood Mass Index", "Bone Mass Index"}, 
            {"18.5–24.9", "10–15", "30–40"}, 
            {"Apply pressure", "Ignore", "Wash hands only"}, 
            {"Alcohol", "Oil", "Perfume"}, 
            {"25 and above", "Below 15", "10"}, 
            {"Cool running water", "Ice", "Butter"}, 
            {"Anyone injured", "Only adults", "Doctors"}, 
            {"Lean forward and pinch nose", "Tilt head backwards", "Let it finish"}, 
            {"Scrape from friction", "A clean cut", "Damage from heat"}, 
            {"RICE Method", "Walk it off", "Tap on the ankle"}, 
            {"18.5 to 24.9", "< 18.5", "25.0 to 29.9"}, 
            {"False", "True"}, 
            {"22.86 kg/m²", "20.50 kg/m²", "25.12 kg/m²"}
        };

        for (int i = 0; i < q.length; i++) {
            String correct = choicesRaw[i][0];
            ArrayList<String> currentChoices = new ArrayList<>();
            for(String s : choicesRaw[i]) currentChoices.add(s);
            Collections.shuffle(currentChoices); // RANDOMIZES THE BUTTONS

            int ans = JOptionPane.showOptionDialog(frame, q[i], "Health Check: " + getHealthIcons(), 
                      0, 3, null, currentChoices.toArray(), null);

            if (ans == -1 || !currentChoices.get(ans).equals(correct)) {
                health--;
                savePersistentData();
                if (health <= 0) {
                    JOptionPane.showMessageDialog(frame, "GAME OVER! No lives left.");
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(frame, "Incorrect! Health: " + getHealthIcons());
                return;
            }
        }

        injured = false;
        syringeUsed = true;
        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
        JOptionPane.showMessageDialog(frame, "HEALED!\nTime: " + timeTaken + "s\nTotal Attempts: " + totalAttempts);
        frame.dispose(); // Close current window
        PD6 secondMap = new PD6(); // Create second map
        secondMap.setFrame(); // Open second map
        
        for (int i = 0; i < mapLayout.length; i++) {
            if (mapLayout[i] >= 24) { mapLayout[i] = 1; updateTileIcon(i); }
        }
        renderView();
    }

    public void moveCharacter(int newPos, ImageIcon icon) {
        this.playerIcon = icon;
        if (newPos >= 0 && newPos < mapLayout.length && mapLayout[newPos] != 0) {
            if (mapLayout[newPos] >= 24) { triggerSyringeEvent(); return; }
            characterPosition = newPos;
        }
        renderView();
    }

    public void renderView() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(viewHeight, viewWidth));
        int pR = characterPosition / mapWidth;
        int pC = characterPosition % mapWidth;
        int sR = Math.max(0, Math.min(mapHeight - viewHeight, pR - viewHeight / 2));
        int sC = Math.max(0, Math.min(mapWidth - viewWidth, pC - viewWidth / 2));

        for (int r = 0; r < viewHeight; r++) {
            for (int c = 0; c < viewWidth; c++) {
                int idx = (sR + r) * mapWidth + (sC + c);
                JPanel cell = new JPanel();
                cell.setLayout(new OverlayLayout(cell)); 
                if (idx == characterPosition) cell.add(new JLabel(scaleForCamera(playerIcon)));
                cell.add(new JLabel(scaleForCamera((ImageIcon) tiles[idx].getIcon())));
                frame.add(cell);
            }
        }
        frame.setTitle("Health: " + getHealthIcons() + " | Retries: " + totalAttempts);
        frame.revalidate();
        frame.repaint();
    }

    public ImageIcon scaleForCamera(ImageIcon i) {
        return new ImageIcon(i.getImage().getScaledInstance(camTileW, camTileH, Image.SCALE_DEFAULT));
    }

    public void setFrame() {
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(3);
        frame.addKeyListener(this);
        frame.setVisible(true);
        renderView();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int next = characterPosition;
        stepCount = (stepCount + 1) % 3;
        if (key == 39) { next++; moveCharacter(next, walkRight[stepCount]); }
        else if (key == 37) { next--; moveCharacter(next, walkLeft[stepCount]); }
        else if (key == 40) { next += mapWidth; moveCharacter(next, walkDown[stepCount]); }
        else if (key == 38) { next -= mapWidth; moveCharacter(next, walkUp[stepCount]); }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new ClinicMap().setFrame();
    }
}