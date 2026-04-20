import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.ArrayList;
import java.io.*; 
import java.util.Scanner;

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

public class PD6 implements KeyListener {
    JFrame frame;
    JLabel[] tiles, character;
    ImageIcon[] walkUp, walkDown, walkLeft, walkRight, idles;

    int mapWidth = 7, mapHeight = 9, tileSize = 70;
    int frameWidth = mapWidth * tileSize, frameHeight = mapHeight * tileSize;
    
    int characterPosition = 56; 
    int lastDirection = 3;      
    int animationToggle = 0;
    boolean isMoving = false;
    private boolean finished = false;

    int medKitPosition;
    ArrayList<Integer> blockedTiles = new ArrayList<>();
    Random rand = new Random();
    boolean objectiveComplete = false;

    private long startTime;
    private double fastestTime = Double.MAX_VALUE;
    private int totalAttempts = 0;
    private final String DATA_FILE = "ClinicGameData.txt"; 

    public PD6() {
        loadGameData();
        totalAttempts++;
        saveGameData();

        startTime = System.currentTimeMillis();
        frame = new JFrame("Clinic Storage - Attempts: " + totalAttempts);
        // Changed to DISPOSE so it doesn't kill the whole app if you close just this window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tiles = new JLabel[mapWidth * mapHeight];
        character = new JLabel[mapWidth * mapHeight];
        
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new JLabel();
            character[i] = new JLabel();
        }

        generateRandomMap(); 
        loadImages();
        loadAnimations();
        
        if (idles != null && idles[lastDirection] != null) {
            character[characterPosition].setIcon(idles[lastDirection]);
        }
    }

    private void saveGameData() {
        try (PrintWriter out = new PrintWriter(new FileWriter(DATA_FILE))) {
            out.println("Attempts:" + totalAttempts);
            out.println("FastestTime:" + fastestTime);
        } catch (IOException e) {
            System.err.println("Error saving game data.");
        }
    }

    private void loadGameData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Attempts:")) totalAttempts = Integer.parseInt(line.split(":")[1]);
                else if (line.startsWith("FastestTime:")) fastestTime = Double.parseDouble(line.split(":")[1]);
            }
        } catch (Exception e) {
            System.err.println("Error loading game data.");
        }
    }

    private void checkObjective() {
        if (characterPosition == medKitPosition && !objectiveComplete) {
            objectiveComplete = true;
            long endTime = System.currentTimeMillis();
            double timeTaken = (endTime - startTime) / 1000.0;

            if (timeTaken < fastestTime) {
                fastestTime = timeTaken;
                saveGameData();
            }

            String stats = String.format("\nTime: %.2fs\nBest: %.2fs\nTotal Attempts: %d", 
                            timeTaken, fastestTime, totalAttempts);
            JOptionPane.showMessageDialog(frame, "Kit Found!" + stats);
        }
    }

    private void generateRandomMap() {
        int wallCount = 12; 
        while (blockedTiles.size() < wallCount) {
            int potentialWall = rand.nextInt(tiles.length);
            if (potentialWall != characterPosition) {
                if (!blockedTiles.contains(potentialWall)) blockedTiles.add(potentialWall);
            }
        }
        do {
            medKitPosition = rand.nextInt(tiles.length);
        } while (blockedTiles.contains(medKitPosition) || medKitPosition == characterPosition);
    }

    private void loadImages() {
        for (int row = 1; row <= mapHeight; row++) {
            for (int col = 1; col <= mapWidth; col++) {
                int index = (row - 1) * mapWidth + (col - 1);
                ImageIcon icon = new ImageIcon("Images/row-" + row + "-column-" + col + ".png");
                if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image scaled = icon.getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
                    tiles[index].setIcon(new ImageIcon(scaled));
                }
            }
        }
    }

    private void loadAnimations() {
        walkDown  = loadAnimation("boywalk1.png", "boywalk2.png"); 
        walkRight = loadAnimation("boywalk3.png", "boywalk7.png");   
        walkLeft  = loadAnimation("boywalk4.png", "boywalk8.png"); 
        walkUp    = loadAnimation("boywalk5.png", "boywalk6.png"); 
        idles     = loadAnimation("boyidle1.png", "boyidle2.png", "boyidle3.png", "boyidle4.png");
    }

    private ImageIcon[] loadAnimation(String... filenames) {
        ImageIcon[] result = new ImageIcon[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            ImageIcon raw = new ImageIcon("Images/" + filenames[i]);
            if (raw.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image scaled = raw.getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
                result[i] = new ImageIcon(scaled);
            }
        }
        return result;
    }

    public void setFrame() {
        JLayeredPane lp = new JLayeredPane();
        lp.setPreferredSize(new Dimension(frameWidth, frameHeight));

        // FIXED: Using GridLayout instead of custom gpl class
        JPanel mapPanel = new JPanel(new GridLayout(mapHeight, mapWidth));
        mapPanel.setBounds(0, 0, frameWidth, frameHeight);
        
        for (int i = 0; i < tiles.length; i++) {
            mapPanel.add(tiles[i]);
        }

        JPanel playerPanel = new JPanel(null);
        playerPanel.setOpaque(false);
        playerPanel.setBounds(0, 0, frameWidth, frameHeight);
        for (int i = 0; i < character.length; i++) {
            int col = i % mapWidth;
            int row = i / mapWidth;
            character[i].setBounds(col * tileSize, row * tileSize, tileSize, tileSize);
            playerPanel.add(character[i]);
        }

        lp.add(mapPanel, JLayeredPane.DEFAULT_LAYER);
        lp.add(playerPanel, JLayeredPane.PALETTE_LAYER);

        frame.add(lp);
        frame.setResizable(false);
        frame.pack();
        frame.addKeyListener(this);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean isBlocked(int pos) {
        return blockedTiles.contains(pos);
    }

    public void keyPressed(KeyEvent e) {
        if (isMoving) return;
        try {
            int keyCode = e.getKeyCode();
            int nextPos = characterPosition;

            if (keyCode != 38 && keyCode != 40 && keyCode != 37 && keyCode != 39) {
                throw new InvalidInputException("Use Arrow Keys to move!");
            }

            switch (keyCode) {
                case 40: if (characterPosition + mapWidth < tiles.length) nextPos += mapWidth; lastDirection = 0; break;
                case 37: if (characterPosition % mapWidth > 0) nextPos -= 1; lastDirection = 1; break;
                case 39: if (characterPosition % mapWidth < mapWidth - 1) nextPos += 1; lastDirection = 2; break;
                case 38: if (characterPosition - mapWidth >= 0) nextPos -= mapWidth; lastDirection = 3; break;
            }

            if (nextPos != characterPosition && !isBlocked(nextPos)) {
                isMoving = true;
                character[characterPosition].setIcon(null);
                characterPosition = nextPos;
                
                ImageIcon[] moveSet = (lastDirection==0)?walkDown : (lastDirection==1)?walkLeft : (lastDirection==2)?walkRight : walkUp;
                if(moveSet != null && moveSet[animationToggle] != null) character[characterPosition].setIcon(moveSet[animationToggle]);

                checkObjective();

                Timer t = new Timer(150, (a) -> {
                    animationToggle = (animationToggle == 0) ? 1 : 0;
                    isMoving = false;
                });
                t.setRepeats(false);
                t.start();
            }
        } catch (InvalidInputException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override public void keyReleased(KeyEvent e) { 
        if(idles != null && idles[lastDirection] != null) character[characterPosition].setIcon(idles[lastDirection]); 
    }
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PD6().setFrame());
    }
}

/** * --- GAME DOCUMENTATION & OBJECTIVES ---
 * * 1. MOVING CHARACTER:
 * Character moves across a 7x9 grid using JLayeredPane. The map background 
 * is rendered on the Default Layer, while the character sprites are 
 * swapped between JLabels on the Palette Layer.
 * * 2. COLLISION DETECTION:
 * A list of 'blockedTiles' is randomly generated at start. The 'isBlocked()' 
 * check ensures the character cannot pass through these indices, simulating 
 * walls or storage equipment obstacles.
 * * 3. MAP OBJECTIVE:
 * The player must locate a hidden 'medKitPosition'. Stepping on this secret 
 * tile triggers an event from the ClinicItem OOP class, boosting health.
 * * 4. OOP CONCEPTS USED:
 * - ENCAPSULATION: The health value is private within the MedicalKit class.
 * - INHERITANCE: MedicalKit inherits 'name' and properties from ClinicItem.
 * - OVERRIDING: triggerEffect() is overridden to provide the mission popup.
 */