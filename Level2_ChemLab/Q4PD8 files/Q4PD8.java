package Q4PD8files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List; 
import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import q2.PD8; 

/* Submitted by: Ralph Jabez Abonado, Ayessa Faye S. Sadian, and Althea Robee V. Cajara */
/* 10-Adenine - PSHS-DRC */

class InvalidInputException extends Exception {
    public InvalidInputException(String message) { super(message); }
}

public class Q4PD8 extends JPanel {
    private PD8 window; 
    private final int TILE_SIZE = 55;
    private final int mapWidth = 9, mapHeight = 9;
    private int playerX = 0, playerY = 7;
    private int itemsAccepted = 0;
    private boolean hasList = false;
    private int timeLeft = 90; 
    private long startTimeMillis;
    private Timer gameTimer;
    
    private ImageIcon currentIcon;
    private ImageIcon boyidlefront, boyidleback, tile, wall, oldmanIcon, oldmanDesk;
    private ImageIcon shelf_L, shelf_R, shelf_L2, shelf_R2, shelf_L3, shelf_R3;
    private HashMap<Integer, ImageIcon[]> directionalFrames;
    private HashMap<String, ArrayList<ImageIcon>> shelfInventories;

    private final String TIME_FILE = "fastest_time.txt";
    private final String RECORD_KEY = "TIME RECORD (map 2): ";

    private class LoggedItem {
        private ImageIcon icon;
        private boolean isSafe;
        private boolean submitted = false;
        public LoggedItem(ImageIcon i, boolean s) { this.icon = i; this.isSafe = s; }
        public ImageIcon getIcon() { return icon; }
        public boolean isSafe() { return isSafe; }
        public void setSubmitted(boolean s) { this.submitted = s; }
        public boolean isSubmitted() { return submitted; }
    }

    private ArrayList<LoggedItem> collectionLog = new ArrayList<>();


    public Q4PD8(PD8 window) {
        this.window = window;
        loadAssets();
        resetGame();
        setFocusable(true);
        setPreferredSize(new Dimension(mapWidth * TILE_SIZE, mapHeight * TILE_SIZE));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (timeLeft <= 0) return;
                try {
                    validateAndHandleInput(e);
                } catch (InvalidInputException ex) {
                    displayMessage(ex.getMessage());
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                int k = e.getKeyCode();
                if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP) currentIcon = boyidleback;
                else if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) currentIcon = boyidlefront;
                repaint();
            }
        });
    }

    private void resetGame() {
        playerX = 0; playerY = 7;
        itemsAccepted = 0;
        hasList = false;
        timeLeft = 90;
        collectionLog.clear();
        initShelfInventories();
        currentIcon = boyidlefront;
        if (gameTimer != null) gameTimer.stop();
        initTimer();
        repaint();
    }

    private void checkAndSaveFastestTime(int secondsTaken) {
        int bestTimeEver = Integer.MAX_VALUE;
        File file = new File(TIME_FILE);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(RECORD_KEY)) {
                        String timePart = line.substring(RECORD_KEY.length()).split("\\|")[0].trim();
                        int foundTime = Integer.parseInt(timePart);
                        if (foundTime < bestTimeEver) bestTimeEver = foundTime;
                    } else if (line.matches("\\d+")) {
                        int foundTime = Integer.parseInt(line.trim());
                        if (foundTime < bestTimeEver) bestTimeEver = foundTime;
                    }
                }
            } catch (Exception e) {
                System.err.println("Scanning history...");
            }
        }

        if (secondsTaken < bestTimeEver) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                writer.println("\n" + RECORD_KEY + secondsTaken + " | Achieved: " + new java.util.Date());
                writer.flush(); 
                displayMessage("NEW PERSONAL BEST! " + secondsTaken + "s.");
            } catch (IOException e) {
                displayMessage("Log error: " + e.getMessage());
            }
        } else {
            displayMessage("Time: " + secondsTaken + "s. (Best: " + (bestTimeEver == Integer.MAX_VALUE ? "None" : bestTimeEver + "s") + ")");
        }
        showRetryOption("Mission Accomplished!");
    }
    private void validateAndHandleInput(KeyEvent e) throws InvalidInputException {
        int dx = 0, dy = 0, key = e.getKeyCode();
        boolean isMove = (key == KeyEvent.VK_W || key == KeyEvent.VK_UP || key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN || 
                          key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT);
        
        if (!isMove && key != KeyEvent.VK_E) throw new InvalidInputException("Use WASD and 'E'.");

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) dy = -1;
        else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) dy = 1;
        else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) dx = -1;
        else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) dx = 1;
        else if (key == KeyEvent.VK_E) { showInventory(); return; }

        if (directionalFrames.containsKey(key)) {
            int frame = (System.currentTimeMillis() / 150 % 2 == 0) ? 0 : 1;
            currentIcon = directionalFrames.get(key)[frame];
        }

        int nextX = playerX + dx, nextY = playerY + dy;
        if (nextX >= 0 && nextX < mapWidth && nextY >= 0 && nextY < mapHeight) {
            int target = mapConfig[nextY][nextX];
            if (target == 2) { playerX = nextX; playerY = nextY; }
            else { handleObjective(target, nextX, nextY); }
        }
        repaint();
    }

    private void handleObjective(int id, int x, int y) {
        if (id == 13 || id == 14) {
            if (!hasList) {
                displayMessage("Staff: Gather 9 safe glassware pieces.");
                hasList = true;
                startTimeMillis = System.currentTimeMillis();
                gameTimer.start();
            } else {
                long carryCount = collectionLog.stream().filter(item -> item.isSafe() && !item.isSubmitted()).count();
                if (carryCount < 9) displayMessage("Need " + (9 - carryCount) + " more safe items.");
                else openSubmissionMenu();
            }
        } else {
            String key = x + "," + y;
            if (shelfInventories.containsKey(key) && hasList && timeLeft > 0) pickFromShelf(key);
        }
    }

    private void openSubmissionMenu() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 5, 5));
        JDialog box = new JDialog((Frame)null, "Lab Submission", true);
        for (LoggedItem item : collectionLog) {
            if (item.isSubmitted()) continue;
            JButton btn = new JButton(item.getIcon());
            btn.addActionListener(e -> {
                item.setSubmitted(true);
                btn.setEnabled(false);
                if (item.isSafe()) {
                    itemsAccepted++;
                    if (itemsAccepted >= 9) {
                        gameTimer.stop();
                        int timeSpent = (int)((System.currentTimeMillis() - startTimeMillis) / 1000);
                        box.dispose();
                        checkAndSaveFastestTime(timeSpent);
                    }else{
                        displayMessage("Staff: This one's good.");
                    }
                }else{
                    displayMessage("Staff: Thank you for reporting this faulty glassware!");
                }
                repaint();
            });
            panel.add(btn);
        }
        box.add(new JScrollPane(panel));
        box.setSize(400, 300);
        box.setLocationRelativeTo(this);
        box.setVisible(true);
    }

    private void pickFromShelf(String coordKey) {
        ArrayList<ImageIcon> items = shelfInventories.get(coordKey);
        if (items.isEmpty()) return;
        int pick = JOptionPane.showOptionDialog(this, "Select glassware:", "Shelf", 0, -1, null, items.toArray(), items.get(0));
        if (pick != -1) {
            boolean isSafe = new Random().nextInt(100) >= 30;
            collectionLog.add(new LoggedItem(items.get(pick), isSafe));
            displayMessage(isSafe ? "Glassware secured." : "WARNING: This one is cracked!", items.get(pick));
            items.remove(pick);
            checkRNGStall();
        }
    }

   
    private void checkRNGStall() {
        boolean shelfEmpty = shelfInventories.values().stream().allMatch(list -> list.isEmpty());
        long safeAvailable = collectionLog.stream().filter(i -> i.isSafe() && !i.isSubmitted()).count();
        if (shelfEmpty && (safeAvailable + itemsAccepted) < 9) {
            gameTimer.stop();
            showRetryOption("OUT OF STOCK! Not enough safe items to finish.");
        }
    }

    private void initTimer() {
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0) { 
                gameTimer.stop(); 
                showRetryOption("TIME'S UP!"); 
            }
            repaint();
        });
    }

    private void showRetryOption(String reason) {
        int choice = JOptionPane.showConfirmDialog(this, reason + "\nRetry?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) resetGame();
        else System.exit(0);
    }

    private void displayMessage(String m) { JOptionPane.showMessageDialog(this, m); }
    private void displayMessage(String m, ImageIcon i) { JOptionPane.showMessageDialog(this, m, "Log", 1, i); }

    private void loadAssets() {
        boyidlefront = loadImg("PD8_assets/boyidlefront.png");
        boyidleback = loadImg("PD8_assets/boyidleback.png");
        directionalFrames = new HashMap<>();
        directionalFrames.put(KeyEvent.VK_S, new ImageIcon[]{loadImg("PD8_assets/boywalkfront1.png"), loadImg("PD8_assets/boywalkfront2.png")});
        directionalFrames.put(KeyEvent.VK_W, new ImageIcon[]{loadImg("PD8_assets/boywalkbehind1.png"), loadImg("PD8_assets/boywalkbehind2.png")});
        directionalFrames.put(KeyEvent.VK_A, new ImageIcon[]{loadImg("PD8_assets/leftfrontprof.png"), loadImg("PD8_assets/leftbackprof.png")});
        directionalFrames.put(KeyEvent.VK_D, new ImageIcon[]{loadImg("PD8_assets/rightfrontprof.png"), loadImg("PD8_assets/rightbackprof.png")});
        tile = loadImg("PD8_assets/40.png"); wall = loadImg("PD8_assets/41.png");
        shelf_L = loadImg("PD8_assets/2.png"); shelf_R = loadImg("PD8_assets/3.png");
        shelf_L2 = loadImg("PD8_assets/12.png"); shelf_R2 = loadImg("PD8_assets/13.png");
        shelf_L3 = loadImg("PD8_assets/8.png"); shelf_R3 = loadImg("PD8_assets/9.png");
        oldmanIcon = loadImg("PD8_assets/oldman.png"); oldmanDesk = loadImg("PD8_assets/1.png");
    }

    private ImageIcon loadImg(String p) {
        try { 
            File f = new File(p);
            if (!f.exists()) return new ImageIcon(new BufferedImage(TILE_SIZE, TILE_SIZE, 2));
            return new ImageIcon(ImageIO.read(f).getScaledInstance(TILE_SIZE, TILE_SIZE, 4)); 
        } catch (Exception e) { return new ImageIcon(); }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int id = mapConfig[y][x];
                if (id == 13) g.drawImage(tile.getImage(), x * TILE_SIZE, y * TILE_SIZE, null);
                g.drawImage(getIcon(id).getImage(), x * TILE_SIZE, y * TILE_SIZE, null);
            }
        }
        g.drawImage(currentIcon.getImage(), playerX * TILE_SIZE, playerY * TILE_SIZE, null);
        
        long carryCount = collectionLog.stream().filter(item -> item.isSafe() && !item.isSubmitted()).count();

        g.setColor(new Color(0, 0, 0, 160));
        g.fillRoundRect(10, 10, 240, 50, 15, 15);
        g.setColor(Color.CYAN);
        g.drawRoundRect(10, 10, 240, 50, 15, 15);
        g.setColor(Color.WHITE);
        g.drawString("Timer: " + timeLeft + "s", 25, 30);
        g.drawString("Held: " + carryCount + "/9 | Done: " + itemsAccepted + "/9", 25, 50);
    }

    private ImageIcon getIcon(int id) {
        return switch (id) {
            case 1 -> wall; case 8 -> shelf_L2; case 3 -> shelf_L; 
            case 4 -> shelf_R2; case 5 -> shelf_R; case 6 -> shelf_L3; 
            case 7 -> shelf_R3; case 13 -> oldmanIcon; case 14 -> oldmanDesk;
            default -> tile;
        };
    }

    private final int[][] mapConfig = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 8, 4, 2, 1, 2, 3, 5, 1}, {1, 2, 2, 2, 1, 2, 2, 2, 1}, 
        {1, 6, 7, 2, 1, 2, 8, 4, 1}, {1, 2, 2, 2, 1, 2, 2, 2, 1}, {1, 3, 5, 2, 1, 2, 3, 5, 1}, 
        {1, 1, 1, 2, 1, 2, 1, 1, 1}, {2, 2, 2, 2, 2, 2, 2, 14, 13}, {1, 1, 1, 2, 2, 2, 2, 2, 2} 
    };

    private void initShelfInventories() {
        shelfInventories = new HashMap<>();
        Random rand = new Random();
        ImageIcon[] allItems = { loadImg("PD6/f1.png"), loadImg("PD6/f2.png"), loadImg("PD6/f3.png"), loadImg("PD6/f4.png") };
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (mapConfig[y][x] >= 3 && mapConfig[y][x] <= 11) {
                    ArrayList<ImageIcon> items = new ArrayList<>();
                    for (int i = 0; i < 3; i++) items.add(allItems[rand.nextInt(4)]);
                    shelfInventories.put(x + "," + y, items);
                }
            }
        }
    }

    private void showInventory() {
        JPanel p = new JPanel(new GridLayout(0, 3, 10, 10));
        for (LoggedItem item : collectionLog) {
            JLabel lbl = new JLabel(item.isSafe() ? "SECURE" : "DAMAGED", item.getIcon(), 0);
            lbl.setForeground(item.isSafe() ? new Color(0, 200, 0) : Color.RED);
            p.add(lbl);
        }
        JOptionPane.showMessageDialog(this, new JScrollPane(p), "Inventory", -1);
    }
}
