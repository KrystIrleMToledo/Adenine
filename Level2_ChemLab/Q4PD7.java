package q4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * <====================================================================================>
 * PROJECT DELIVERABLE 7 - Exception Handling & Advanced OOP
 * Submitted by: Ralph Jabez Abonado, Ayessa Faye S. Sadian, and Althea Robee V. Cajara 
 * Section: 10-Adenine
 * <====================================================================================>
*/

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

public class Q4PD7 extends JPanel {
    private final int TILE_SIZE = 55;
    private final int mapWidth = 9, mapHeight = 9;
    private int playerX = 0, playerY = 7;
    private int itemsAccepted = 0;
    private boolean hasList = false;
    private int timeLeft = 90; 
    private Timer gameTimer;
    private ImageIcon currentIcon;
    private ImageIcon boyidlefront, boyidleback;
    private HashMap<Integer, ImageIcon[]> directionalFrames;
    private HashMap<String, ArrayList<ImageIcon>> shelfInventories; 

    
    private class LoggedItem {
        private ImageIcon icon;
        private boolean isSafe;
        private boolean submitted = false;

        public LoggedItem(ImageIcon i, boolean s) { 
            this.icon = i; 
            this.isSafe = s; 
        }

        public ImageIcon getIcon() { return icon; }
        public boolean isSafe() { return isSafe; }
        public void setSubmitted(boolean s) { this.submitted = s; }
        public boolean isSubmitted() { return submitted; }
    }

    private ArrayList<LoggedItem> collectionLog = new ArrayList<>(); 
    private ImageIcon tile, wall, shelf_L, shelf_R, shelf_L2, shelf_R2, shelf_L3, shelf_R3, oldmanIcon, oldmanDesk;

    private final int[][] mapConfig = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 8, 4, 2, 1, 2, 3, 5, 1},
        {1, 2, 2, 2, 1, 2, 2, 2, 1}, 
        {1, 6, 7, 2, 1, 2, 8, 4, 1},
        {1, 2, 2, 2, 1, 2, 2, 2, 1},
        {1, 3, 5, 2, 1, 2, 3, 5, 1}, 
        {1, 1, 1, 2, 1, 2, 1, 1, 1},
        {2, 2, 2, 2, 2, 2, 2, 14, 13},
        {1, 1, 1, 2, 2, 2, 2, 2, 2} 
    };

    public Q4PD7() {
        loadAssets();
        initShelfInventories();
        initTimer();
        currentIcon = boyidlefront;
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

            
            private void validateAndHandleInput(KeyEvent e) throws InvalidInputException {
                int dx = 0, dy = 0, key = e.getKeyCode();
          
                boolean isMove = (key == KeyEvent.VK_W || key == KeyEvent.VK_UP || 
                                  key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN || 
                                  key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT || 
                                  key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT);
           
                boolean isAction = (key == KeyEvent.VK_E);

               
                if (!isMove && !isAction) {
                    throw new InvalidInputException("Invalid input. Please use WASD or Arrow Keys to move, and 'E' for Inventory.");
                }

                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) dy = -1;
                else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) dy = 1;
                else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) dx = -1;
                else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) dx = 1;
                
                if (key == KeyEvent.VK_E) { showInventory(); return; }

                if (directionalFrames.containsKey(key)) {
                    int animFrame = (System.currentTimeMillis() / 150 % 2 == 0) ? 0 : 1; 
                    currentIcon = directionalFrames.get(key)[animFrame];
                }
                
                int nextX = playerX + dx, nextY = playerY + dy;
                if (nextX >= 0 && nextX < mapWidth && nextY >= 0 && nextY < mapHeight) {
                    int target = mapConfig[nextY][nextX];
                    if (target == 2) { playerX = nextX; playerY = nextY; }
                    else { handleObjective(target, nextX, nextY); }
                }
                repaint();
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

    private void displayMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

 
    private void displayMessage(String msg, ImageIcon icon) {
        JOptionPane.showMessageDialog(this, msg, "Inspection", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private void initTimer() {
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0) {
                gameTimer.stop();
                displayMessage("TIME'S UP!");
            }
            repaint();
        });
    }

    private void initShelfInventories() {
        shelfInventories = new HashMap<>();
        Random rand = new Random();
        ImageIcon[] allItems = { loadImg("PD7_assets/f1.png"), loadImg("PD7_assets/f2.png"), loadImg("PD7_assets/f3.png"), loadImg("PD7_assets/f4.png") };
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int id = mapConfig[y][x];
                if (id >= 3 && id <= 11) {
                    ArrayList<ImageIcon> items = new ArrayList<>();
                    for (int i = 0; i < 3; i++) { items.add(allItems[rand.nextInt(4)]); }
                    shelfInventories.put(x + "," + y, items);
                }
            }
        }
    }

    private void handleObjective(int id, int x, int y) {
        if (id == 13 || id == 14) {
            if (!hasList) {
                displayMessage("Staff: Collect at least 9 safe glassware and report any faulty glassware. \n                                  Press E to view your inventory.");
                hasList = true;
                gameTimer.start();
            } else {
                long carryCount = collectionLog.stream().filter(item -> item.isSafe() && !item.isSubmitted()).count();
                if (carryCount < 9) {
                    displayMessage("Staff: You're only carrying " + carryCount + " safe glassware. I need a batch of 9!");
                } else {
                    openSubmissionMenu();
                }
            }
        } else {
            String coordKey = x + "," + y;
            if (shelfInventories.containsKey(coordKey) && hasList && timeLeft > 0) {
                pickFromShelf(coordKey);
            }
        }
    }

    private void pickFromShelf(String coordKey) {
        ArrayList<ImageIcon> items = shelfInventories.get(coordKey);
        if (items.isEmpty()) {
            displayMessage("This shelf is empty.");
            return;
        }
        
        int pick = JOptionPane.showOptionDialog(this, "Select glassware:", "Shelf", 0, -1, null, items.toArray(), items.get(0));
        if (pick != -1) {
            int roll = new Random().nextInt(100);
            boolean isSafe = (roll >= 30); // 70% safe
            
            ImageIcon selectedIcon = items.get(pick);
            collectionLog.add(new LoggedItem(selectedIcon, isSafe));
            
            if (!isSafe) displayMessage("Notice: This glassware looks cracked/unsafe!", selectedIcon);
            else displayMessage("Notice: This glassware looks safe for use.", selectedIcon);
            
            items.remove(pick); 
        }
    }

    private void openSubmissionMenu() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 5, 5));
        JDialog submissionBox = new JDialog((Frame)null, "SUBMISSION PANEL", true);
        
        for (LoggedItem item : collectionLog) {
            if (item.isSubmitted()) continue;

            JButton btn = new JButton(item.getIcon());
            btn.addActionListener(e -> {
                item.setSubmitted(true);
                btn.setEnabled(false);
                
                if (!item.isSafe()) {
                    displayMessage("Staff: Thank you for reporting this faulty glassware!");
                } else {
                    itemsAccepted++;
                    if (itemsAccepted >= 9) {
                        gameTimer.stop();
                        displayMessage("Mission Complete! All 9 safe glassware submitted.");
                        submissionBox.dispose();
                    }
                }
                repaint();
            });
            panel.add(btn);
        }

        submissionBox.add(new JLabel("Click items to submit them:", JLabel.CENTER), BorderLayout.NORTH);
        submissionBox.add(new JScrollPane(panel), BorderLayout.CENTER);
        submissionBox.setSize(450, 400);
        submissionBox.setLocationRelativeTo(this);
        submissionBox.setVisible(true);
    }

    private void showInventory() {
        JPanel p = new JPanel(new GridLayout(0, 3, 10, 10));
        for (LoggedItem item : collectionLog) {
            JLabel lbl = new JLabel(item.isSafe() ? "SAFE" : "UNSAFE", item.getIcon(), 0);
            lbl.setForeground(item.isSafe() ? new Color(0, 150, 0) : Color.RED);
            if (item.isSubmitted()) lbl.setText(lbl.getText() + " (DONE)");
            p.add(lbl);
        }
        JOptionPane.showMessageDialog(this, new JScrollPane(p), "Inventory", -1);
    }

    private void loadAssets() {
        boyidlefront = loadImg("PD7_assets/boyidlefront.png");
        boyidleback = loadImg("PD7_assets/boyidleback.png");
        directionalFrames = new HashMap<>();
        directionalFrames.put(KeyEvent.VK_S, new ImageIcon[]{loadImg("PD7_assets/boywalkfront1.png"), loadImg("PD7_assets/boywalkfront2.png")});
        directionalFrames.put(KeyEvent.VK_W, new ImageIcon[]{loadImg("PD7_assets/boywalkbehind1.png"), loadImg("PD7_assets/boywalkbehind2.png")});
        directionalFrames.put(KeyEvent.VK_A, new ImageIcon[]{loadImg("PD7_assets/leftfrontprof.png"), loadImg("PD7_assets/leftbackprof.png")});
        directionalFrames.put(KeyEvent.VK_D, new ImageIcon[]{loadImg("PD7_assets/rightfrontprof.png"), loadImg("PD7_assets/rightbackprof.png")});
        tile = loadImg("PD7_assets/40.png"); wall = loadImg("PD7_assets/41.png");
        shelf_L = loadImg("PD7_assets/2.png"); shelf_R = loadImg("PD7_assets/3.png");
        shelf_L2 = loadImg("PD7_assets/12.png"); shelf_R2 = loadImg("PD7_assets/13.png");
        shelf_L3 = loadImg("PD7_assets/8.png"); shelf_R3 = loadImg("PD7_assets/9.png");
        oldmanIcon = loadImg("PD7_assets/oldman.png");
        oldmanDesk = loadImg("PD7_assets/1.png");
    }

    private ImageIcon loadImg(String p) {
        try { return new ImageIcon(ImageIO.read(new File(p)).getScaledInstance(TILE_SIZE, TILE_SIZE, 4)); }
        catch (Exception e) { return new ImageIcon(); }
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
        
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(5, 5, 260, 45); 
        g.setColor(Color.WHITE);
        g.drawString("Time: " + timeLeft + "s", 10, 20);
        g.drawString("Carrying: " + carryCount + "/9 | Submitted: " + itemsAccepted + "/9", 10, 40);
    }

    private ImageIcon getIcon(int id) {
        return switch (id) {
            case 1 -> wall; case 8 -> shelf_L2; case 3 -> shelf_L; 
            case 4 -> shelf_R2; case 5 -> shelf_R; case 6 -> shelf_L3; 
            case 7 -> shelf_R3; case 13 -> oldmanIcon; case 14 -> oldmanDesk;
            default -> tile;
        };
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Lab Stockroom"); f.add(new Q4PD7()); f.pack();
        f.setLocationRelativeTo(null); f.setDefaultCloseOperation(3); f.setVisible(true);
    }
}