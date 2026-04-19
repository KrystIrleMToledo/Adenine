package q2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;
import java.util.List;

/* Submitted by: Ralph Jabez Abonado, Ayessa Faye S. Sadian, and Althea Robee V. Cajara */
/* 10-Adenine */


public class Q2PD4 extends JPanel {
   
    private PD8 window;
    
    private final int TILE_SIZE = 32;
    private final int mapWidth = 12;
    private final int mapHeight = 12;

    private ImageIcon tile, wall, obj3_BS_TL, obj5_BS_TR, obj8_BS_BL, obj9_BS_BR, obj4_sink;
    private ImageIcon obj6_VerticalTable_Top, obj7_VerticalTable_Bottom, potionIcon;
    private ImageIcon objA_CT1_L, objB_CT1_R, objA_CT2_L, objB_CT2_R, objA_CT3_L, objB_CT3_R;
    private ImageIcon boyidlefront, boyidleback;
    
    private Map<Integer, List<ImageIcon>> directionalFrames;
    private int currentDirection = KeyEvent.VK_DOWN;
    private int currentFrameIndex = 0;
    private int playerX = 1;
    private int playerY = 1;
    private boolean hasMovedYet = false;

    private int gameState = 0; 
    private int totalReported = 0;
    private final int TOTAL_GOAL = 3;
    private int timeLeft = 60; 
    
    private boolean isLucky = false; 
    private int luckTimeLeft = 0;
    private javax.swing.Timer luckTimer;

    private javax.swing.Timer gameTimer;
    private JLabel statusLabel;
    private boolean gameFinished = false;
    private Set<String> usedSamples = new HashSet<>();

    JLabel[][] tileMap;
    private int[][] currentMapConfig;

    private final Set<Integer> wallTiles = Set.of(1, 3, 5, 8, 9, 4, 6, 7, 10, 11, 12, 13, 14, 15); 
    private final Set<Integer> collectibleIds = Set.of(10, 12, 14);

    private final int[][] initialMapConfig = {
        {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
        {1, 2, 2, 3, 5, 2, 3, 5, 16, 3, 5, 1}, 
        {1, 2, 2, 8, 9, 2, 8, 9, 2, 8, 9, 1}, 
        {1, 2, 2, 4, 6, 2, 4, 6, 2, 4, 6, 1}, 
        {1, 6, 2, 2, 7, 2, 2, 7, 2, 2, 7, 1}, 
        {1, 7, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1}, 
        {1, 2, 2, 4, 6, 2, 4, 6, 2, 4, 6, 1}, 
        {1, 2, 2, 2, 7, 2, 2, 7, 2, 2, 7, 1}, 
        {1, 14, 15, 2, 12, 13, 2, 10, 11, 2, 2, 1}, 
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1}, 
        {1, 14, 15, 2, 10, 11, 2, 12, 13, 2, 2, 1}, 
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1} 
    };

    public Q2PD4(PD8 window) {
        this.window = window;
        this.setLayout(new BorderLayout()); 
        
        tileMap = new JLabel[mapHeight][mapWidth];
        currentMapConfig = copyMap(initialMapConfig);
        statusLabel = new JLabel("Time: 60s | Goal: 0/3", SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.WHITE);
        
        loadAssets();
        
       
        JPanel p = new JPanel(new GridLayout(mapHeight, mapWidth));
        initializeMapDisplay(p);
        
        this.add(statusLabel, BorderLayout.NORTH);
        this.add(p, BorderLayout.CENTER);
        
        startTimer();
        setupLuckTimer();


        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameFinished) handleMovement(e.getKeyCode());
            }
        });
    }


    public JPanel getMainPanel() {
        return this;
    }

    private void setupLuckTimer() {
        luckTimer = new javax.swing.Timer(1000, e -> {
            luckTimeLeft--;
            if (luckTimeLeft <= 0) {
                isLucky = false;
                luckTimer.stop();
            }
            updateStatusText();
        });
    }

    private void loadAssets() {
        tile = loadTransparentIcon("PD4_assets/40.png");
        wall = loadTransparentIcon("PD4_assets/41.png");
        obj3_BS_TL = loadTransparentIcon("PD4_assets/30.png");
        obj5_BS_TR = loadTransparentIcon("PD4_assets/31.png");
        obj8_BS_BL = loadTransparentIcon("PD4_assets/32.png");
        obj9_BS_BR = loadTransparentIcon("PD4_assets/33.png");
        obj6_VerticalTable_Top = loadTransparentIcon("PD4_assets/6.png");
        obj7_VerticalTable_Bottom = loadTransparentIcon("PD4_assets/7.png");
        obj4_sink = loadTransparentIcon("PD4_assets/34.png");
        objA_CT1_L = loadTransparentIcon("PD4_assets/28.png");
        objB_CT1_R = loadTransparentIcon("PD4_assets/29.png");
        objA_CT2_L = loadTransparentIcon("PD4_assets/35.png");
        objB_CT2_R = loadTransparentIcon("PD4_assets/36.png");
        objA_CT3_L = loadTransparentIcon("PD4_assets/38.png");
        objB_CT3_R = loadTransparentIcon("PD4_assets/37.png");
        potionIcon = loadTransparentIcon("PD4_assets/2.png"); 

        boyidlefront = loadTransparentIcon("PD4_assets/boyidlefront.png");
        boyidleback = loadTransparentIcon("PD4_assets/boyidleback.png");

        directionalFrames = new HashMap<>();
        directionalFrames.put(KeyEvent.VK_DOWN, loadSeq("boywalkfront1", "boywalkfront2"));
        directionalFrames.put(KeyEvent.VK_UP, loadSeq("boywalkbehind1", "boywalkbehind2"));
        directionalFrames.put(KeyEvent.VK_LEFT, loadSeq("leftfrontprof", "leftbackprof"));
        directionalFrames.put(KeyEvent.VK_RIGHT, loadSeq("rightfrontprof", "rightbackprof"));
    }

    private List<ImageIcon> loadSeq(String... names) {
        List<ImageIcon> list = new ArrayList<>();
        for (String s : names) list.add(loadTransparentIcon("PD4_assets/" + s + ".png"));
        return list;
    }

    private ImageIcon loadTransparentIcon(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            BufferedImage scaled = new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaled.createGraphics();
            g2d.drawImage(img, 0, 0, TILE_SIZE, TILE_SIZE, null);
            g2d.dispose();
            return new ImageIcon(scaled);
        } catch (IOException e) {
            return new ImageIcon(new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_ARGB));
        }
    }

    private ImageIcon getCombinedIcon(ImageIcon bg, ImageIcon player) {
        BufferedImage combined = new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();
        g.drawImage(bg.getImage(), 0, 0, null);
        g.drawImage(player.getImage(), 0, 0, null);
        g.dispose();
        return new ImageIcon(combined);
    }

    private void initializeMapDisplay(JPanel container) {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int tileId = currentMapConfig[y][x];
                ImageIcon baseIcon = getIconForId(tileId);
                if (tileId == 16) baseIcon = getCombinedIcon(tile, potionIcon);
                
                tileMap[y][x] = new JLabel(baseIcon);
                tileMap[y][x].setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                tileMap[y][x].setOpaque(false);
                container.add(tileMap[y][x]);
            }
        }
        updatePlayerDisplay();
    }

    private void startTimer() {
        gameTimer = new javax.swing.Timer(1000, e -> {
            timeLeft--; updateStatusText();
            if (timeLeft <= 0) {
                gameTimer.stop(); gameFinished = true;
                JOptionPane.showMessageDialog(this, "TIME'S UP!");
            }
        });
        gameTimer.start();
    }

    private void handleMovement(int code) {
        int dx = 0, dy = 0;
        int dirKey = currentDirection;

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) { dy = -1; dirKey = KeyEvent.VK_UP; }
        else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) { dy = 1; dirKey = KeyEvent.VK_DOWN; }
        else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) { dx = -1; dirKey = KeyEvent.VK_LEFT; }
        else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) { dx = 1; dirKey = KeyEvent.VK_RIGHT; }
        else return;

        int nx = playerX + dx, ny = playerY + dy;

        if (nx >= 0 && nx < mapWidth && ny >= 0 && ny < mapHeight) {
            int targetId = currentMapConfig[ny][nx];

            if (nx == 9 && ny == 11 && totalReported >= TOTAL_GOAL) {
                tileMap[playerY][playerX].setIcon(getIconForId(currentMapConfig[playerY][playerX]));
                playerX = nx; playerY = ny;
                tileMap[playerY][playerX].setIcon(getCombinedIcon(tile, boyidleback));
                gameTimer.stop(); gameFinished = true;
                JOptionPane.showMessageDialog(this, "You Passed! Entering Stockroom...");
                
                if(window != null) window.showMap2(); // Asks PD8 to switch screens [cite: 93]
                return;
            }

            if (targetId == 16) {
                isLucky = true;
                luckTimeLeft = 10;
                luckTimer.restart();
                currentMapConfig[ny][nx] = 2; 
                tileMap[ny][nx].setIcon(tile);
            }

            if (!wallTiles.contains(targetId)) {
                tileMap[playerY][playerX].setIcon(getIconForId(currentMapConfig[playerY][playerX]));
                playerX = nx; playerY = ny;
                hasMovedYet = true;
                if (currentDirection == dirKey) {
                    currentFrameIndex = (currentFrameIndex + 1) % 2;
                } else {
                    currentDirection = dirKey;
                    currentFrameIndex = 0;
                }
                checkProximityInteractions();
                updatePlayerDisplay();
            }
        }
    }

    private void checkProximityInteractions() {
        if (playerX + 1 < mapWidth) {
            int rId = currentMapConfig[playerY][playerX + 1];
            String loc = (playerX + 1) + "," + playerY;
            if (gameState == 0 && collectibleIds.contains(rId) && !usedSamples.contains(loc)) {
                gameState = 1; usedSamples.add(loc);
                JOptionPane.showMessageDialog(this, "Sample Collected!");
            } else if (gameState == 1 && rId == 4) {
                gameState = 2;
                JOptionPane.showMessageDialog(this, "Analyzed!");
            }
        }

        if (playerX == 2 && (playerY == 4 || playerY == 5)) {
            int lId = currentMapConfig[playerY][playerX - 1];
            if (gameState == 2 && (lId == 6 || lId == 7)) {
                double successThreshold = isLucky ? 1.0 : 0.8;

                if (Math.random() < successThreshold) {
                    totalReported++;
                    gameState = 0;
                    String msg = isLucky ? "LUCKY SUCCESS! Sample accepted." : "Teacher accepted the report!";
                    JOptionPane.showMessageDialog(this, msg);
                    
                    if (totalReported >= TOTAL_GOAL) {
                        currentMapConfig[11][9] = 2; tileMap[11][9].setIcon(tile);
                        JOptionPane.showMessageDialog(this, "Goal reached! Exit open.");
                    }
                } else {
                    gameState = 0; 
                    JOptionPane.showMessageDialog(this, "Rejected! Get a new sample.", "Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        updateStatusText();
    }

    private void updateStatusText() {
        String luckyStr = isLucky ? "[LUCKY: " + luckTimeLeft + "s] " : "";
        String t = switch(gameState) { 
            case 0->"Collect"; case 1->"Analyze"; case 2->"Report"; default->"Exit"; 
        };
        statusLabel.setText("Time: " + timeLeft + "s | Score: " + totalReported + "/" + TOTAL_GOAL + " | " + luckyStr + t);
        statusLabel.setForeground(isLucky ? new Color(0, 100, 0) : Color.BLACK);
    }

    private void updatePlayerDisplay() {
        ImageIcon pIcon = (!hasMovedYet) ? boyidlefront : directionalFrames.get(currentDirection).get(currentFrameIndex);
        ImageIcon bgIcon = getIconForId(currentMapConfig[playerY][playerX]);
        tileMap[playerY][playerX].setIcon(getCombinedIcon(bgIcon, pIcon));
    }

    private ImageIcon getIconForId(int id) {
        return switch(id) {
            case 1->wall; case 3->obj3_BS_TL; case 5->obj5_BS_TR; case 8->obj8_BS_BL;
            case 9->obj9_BS_BR; case 4->obj4_sink; case 6->obj6_VerticalTable_Top;
            case 7->obj7_VerticalTable_Bottom; case 10->objA_CT1_L; case 11->objB_CT1_R;
            case 12->objA_CT2_L; case 13->objB_CT2_R; case 14->objA_CT3_L;
            case 15->objB_CT3_R; case 16->potionIcon; default->tile;
        };  
    }

    private int[][] copyMap(int[][] orig) {
        int[][] res = new int[orig.length][];
        for (int i = 0; i < orig.length; i++) res[i] = orig[i].clone();
        return res;
    }
}
