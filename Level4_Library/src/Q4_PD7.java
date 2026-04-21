import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.*;

public class Q4_PD7 implements KeyListener {
    JFrame frame;
    JLayeredPane layeredPane;
    JPanel mapPanel, playerPanel;

    ImageIcon[] walkUp, walkDown, walkLeft, walkRight, idles;
    JLabel[] tiles;
    JLabel[] character;

    int currentLevel = 1;
    String assetFolder = "Images/PD4/";

    int mapWidth = 12;
    int mapHeight = 12;
    int frameWidth = 900;
    int frameHeight = 900;

    int characterPosition;
    int animationToggle = 0;
    int lastDirection = 0;
    boolean isMoving = false;
    boolean isQuizRunning = false;

    int currentQuizLevel = 1;
    int quiz1TileIndex = 33;
    int quiz2TileIndex = 14;
    int[] blockedTiles;
    String playerType = "boy";

    private Clip backgroundMusic;
    
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

    public Q4_PD7() {
        loadPlayerType(); 
        loadLevel(1);
        setFrame();
    }

    private void playMusic(String fileName) {
        try {
            if (backgroundMusic != null && backgroundMusic.isRunning()) {
                backgroundMusic.stop();
                backgroundMusic.close();
            }
            File soundFile = new File("Sounds/" + fileName);
            if (soundFile.exists()) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioIn);
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundMusic.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLevel(int level) {
        this.currentLevel = level;
        this.currentQuizLevel = 1;

        if (level == 1) {
            playMusic("map1_theme.wav");
            assetFolder = "Images/PD4/";
            blockedTiles = new int[]{
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 23, 24, 35, 36, 47, 48, 59, 60, 71, 72, 83, 84, 95, 96, 107, 108, 119,
                132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143,
                18, 19, 20, 21, 22, 25, 26, 27, 28, 34, 40, 41, 43, 50, 51, 58, 59, 
                62, 63, 65, 67, 70, 82, 85, 86, 89, 94, 97, 98, 106, 109, 110, 118, 
                121, 122, 128, 129, 130
            };
            characterPosition = 100;
        } else {
            playMusic("map2_theme.wav");
            assetFolder = "Images/PD6/";
            blockedTiles = new int[]{ 
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 23, 24, 35, 36, 47, 48, 59, 60, 71, 72, 83, 84, 95, 96, 107, 108, 119,
                132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143,
                22, 28, 34, 38, 43, 46, 50, 52, 62, 65, 70, 75, 82, 86, 88, 90, 91, 92, 94, 
                97, 98, 104, 109, 110, 112, 114, 115, 116
            };
            characterPosition = 112;
        }

        int tw = frameWidth / mapWidth;
        int th = frameHeight / mapHeight;

        tiles = new JLabel[mapWidth * mapHeight];
        for (int r = 0; r < mapHeight; r++) {
            for (int c = 0; c < mapWidth; c++) {
                int i = r * mapWidth + c;
                tiles[i] = new JLabel(getScaledIcon(assetFolder + c + "" + r + ".png", tw, th));
            }
        }

        updateTileImage(11, "row-1-column-12.png");
        updateTileImage(23, "row-2-column-12.png");

        String prefix = playerType.equals("girl") ? "girl" : "boy";

        walkDown = loadAnimation(prefix + "walk1.png", prefix + "walk2.png");
        walkRight = loadAnimation(prefix + "walk3.png", prefix + "walk7.png");
        walkLeft = loadAnimation(prefix + "walk4.png", prefix + "walk8.png");
        walkUp = loadAnimation(prefix + "walk5.png", prefix + "walk6.png");
        idles = loadAnimation(prefix + "idle1.png", prefix + "idle2.png", prefix + "idle3.png", prefix + "idle4.png");

        character = new JLabel[mapWidth * mapHeight];
        for (int i = 0; i < character.length; i++) {
            character[i] = new JLabel();
            character[i].setBounds((i % mapWidth) * tw, (i / mapWidth) * th, tw, th);
            if (i == characterPosition) character[i].setIcon(idles[0]);
        }

        if (mapPanel != null) refreshUI();
    }

    private ImageIcon getScaledIcon(String path, int w, int h) {
        ImageIcon raw = new ImageIcon(path);
        if (raw.getImageLoadStatus() == MediaTracker.COMPLETE) {
            return new ImageIcon(raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        }
        return null;
    }

    private ImageIcon[] loadAnimation(String... files) {
        ImageIcon[] res = new ImageIcon[files.length];
        for (int i = 0; i < files.length; i++) {
            res[i] = getScaledIcon(assetFolder + files[i], frameWidth / mapWidth, frameHeight / mapHeight);
        }
        return res;
    }

    public void setFrame() {
        frame = new JFrame("PD Game - Adenine Group 4");
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

        mapPanel = new JPanel(null);
        mapPanel.setBounds(0, 0, frameWidth, frameHeight);
        playerPanel = new JPanel(null);
        playerPanel.setBounds(0, 0, frameWidth, frameHeight);
        playerPanel.setOpaque(false);

        refreshUI();

        layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(playerPanel, JLayeredPane.PALETTE_LAYER);

        frame.add(layeredPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(this);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void refreshUI() {
        mapPanel.removeAll();
        playerPanel.removeAll();
        int tw = frameWidth / mapWidth;
        int th = frameHeight / mapHeight;

        for (int i = 0; i < tiles.length; i++) {
            tiles[i].setBounds((i % mapWidth) * tw, (i / mapWidth) * th, tw, th);
            mapPanel.add(tiles[i]);
            playerPanel.add(character[i]);
        }
        mapPanel.revalidate();
        mapPanel.repaint();
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    private void updateTileImage(int index, String name) {
        tiles[index].setIcon(getScaledIcon(assetFolder + name, frameWidth / mapWidth, frameHeight / mapHeight));
    }

    private boolean isBlocked(int pos) {
        for (int b : blockedTiles) if (pos == b) return true;
        return false;
    }

    private void triggerQuiz() {
    if (isQuizRunning) return;
    isQuizRunning = true;

    String[][] data;
    
    if (currentLevel == 1) {
        if (currentQuizLevel == 1) {
            data = new String[][]{
                {"Which binary number represents the decimal value of 5?", "101", "111", "010", "110"},
                {"According to Newton's Second Law, Force equals Mass times what?", "Acceleration", "Velocity", "Inertia", "Gravity"}
            };
        } else {
            data = new String[][]{
                {"Which subatomic particle has a negative charge?", "Electron", "Proton", "Neutron", "Photon"},
                {"In a right-angled triangle, a² + b² equals?", "c²", "c²", "2c", "a+b"}
            };
        }
    } else { // MAP 2 QUESTIONS
        if (currentQuizLevel == 1) {
            data = new String[][]{
                {"What is the most abundant gas in Earth's atmosphere?", "Nitrogen", "Oxygen", "Argon", "Carbon Dioxide"},
                {"Which organ in the human body is responsible for filtering blood?", "Kidney", "Heart", "Lungs", "Stomach"}
            };
        } else {
            data = new String[][]{
                {"What does 'HTTP' stand for in a website address?", "Hypertext Transfer Protocol", "High Tech Trust Process", "Hyperlink Text Terminal", "Home Tool Transfer Program"},
                {"Who is known as the father of modern Computer Science?", "Alan Turing", "Isaac Newton", "Albert Einstein", "Steve Jobs"}
            };
        }
    }

    for (String[] q : data) {
        String[] opts = new String[q.length - 1];
        System.arraycopy(q, 1, opts, 0, q.length - 1);
        List<String> list = Arrays.asList(opts);
        Collections.shuffle(list);
        opts = list.toArray(new String[0]);

        int res = JOptionPane.showOptionDialog(frame, q[0], "Map " + currentLevel + " - Challenge " + currentQuizLevel, 
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);
        
        if (res == JOptionPane.CLOSED_OPTION || !opts[res].equals(q[1])) {
            JOptionPane.showMessageDialog(frame, "❌ Incorrect!\nTry again.");
            isQuizRunning = false;
            frame.requestFocusInWindow();
            return;
        }
    }

    // Progression Logic
    if (currentQuizLevel == 1) {
        JOptionPane.showMessageDialog(frame, "✅ You completed this question set! Now, move to the next spot.");
        updateTileImage(quiz1TileIndex, "92_after.png");
        updateTileImage(quiz2TileIndex, "21_during.png");
        currentQuizLevel = 2;
    } else {
        if (currentLevel == 1) {
            JOptionPane.showMessageDialog(frame, "✅ CONGRATULATIONS! \n You have cleared - wait, what's going on?");
            loadLevel(2);
        } else {
            JOptionPane.showMessageDialog(frame, "✅ FINAL MISSION COMPLETE!\n Now, follow the nurse's direction without hesitation.");
            updateTileImage(quiz2TileIndex, "21_after.png");
        }
    }
    isQuizRunning = false;
    frame.requestFocusInWindow();
}

    public void keyPressed(KeyEvent e) {
        if (isMoving || isQuizRunning) return;
        int next = characterPosition;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: next -= mapWidth; lastDirection = 3; break;
            case KeyEvent.VK_DOWN: next += mapWidth; lastDirection = 0; break;
            case KeyEvent.VK_LEFT: next -= 1; lastDirection = 1; break;
            case KeyEvent.VK_RIGHT: next += 1; lastDirection = 2; break;
            default: return;
        }

        if (next < 0 || next >= tiles.length || isBlocked(next)) return;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && characterPosition % mapWidth == mapWidth - 1) return;
        if (e.getKeyCode() == KeyEvent.VK_LEFT && characterPosition % mapWidth == 0) return;

        isMoving = true;
        character[characterPosition].setIcon(null);
        characterPosition = next;
        
        ImageIcon[] anim = (lastDirection == 0) ? walkDown : (lastDirection == 1) ? walkLeft : (lastDirection == 2) ? walkRight : walkUp;
        character[characterPosition].setIcon(anim[animationToggle]);

        if ((currentQuizLevel == 1 && characterPosition == quiz1TileIndex) || (currentQuizLevel == 2 && characterPosition == quiz2TileIndex)) {
            Timer t = new Timer(200, a -> triggerQuiz());
            t.setRepeats(false);
            t.start();
        }

        Timer mt = new Timer(150, a -> {
            animationToggle = (animationToggle == 0) ? 1 : 0;
            isMoving = false;
        });
        mt.setRepeats(false);
        mt.start();
    }

    public void keyReleased(KeyEvent e) {
        if (!isMoving && characterPosition >= 0) character[characterPosition].setIcon(idles[lastDirection]);
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Q4_PD7::new);
    }
}
Write to Heaven Ronelle Fernandez

    }
}
