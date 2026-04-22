package PD8TOTAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.nio.file.*;

abstract class GameObject {
    private int id;
    private String name;
    public GameObject(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public abstract void interact(cs4backroom game, int pos);
}

class Item extends GameObject {
    public Item(int id) {
        super(id, "Item");
    }
    public Item(int id, String name) {
        super(id, name);
    }
    @Override
    public void interact(cs4backroom game, int pos) {
        if (game.getHoldingItemID() == 0) {
            game.setHoldingItemID(this.getId());
            game.placeObject(pos, 0);
        } else {
            JOptionPane.showMessageDialog(null, "You're already holding something!");
        }
    }
}

class Chest extends GameObject {
    public Chest(int id, String name) {
        super(id, name);
    }
    @Override
    public void interact(cs4backroom game, int pos) {
        game.handleChestLogic(pos);
    }
}

public class cs4backroom implements KeyListener {
    private JFrame frame;
    private Map<String, ImageIcon> playerSprites = new HashMap<>();
    private JLabel[] tiles, objectMap, playerMap;
    private int[] mapLayout;
    private int playerPos;
    private int walkstate = 0;
    private enum direction { up, down, left, right }
    private direction lastdirection = direction.down;
    private Timer idleTimer;
    private static final String INVENTORY_FILE = "backroom_inventory.txt";
    private static final String KEY_HOLDING   = "holdingItemID";
    private static final String KEY_IN_CHEST  = "itemInChest";
    private static final String KEY_CHEST_CL  = "chestClosed";
    private static final String KEY_FINISHED  = "gameFinished";
    private static final String KEY_GUARDS    = "guardsPresent";
    private int     holdingItemID = 0;
    private int     itemInChest   = 0;
    private boolean chestClosed   = false;
    private boolean gameFinished  = false;
    private boolean guardsPresent = true;
    static final String TIMER_FILE = "game_timer.txt";
    private static final String KEY_MAP1_START    = "map1StartMs";
    private static final String KEY_MAP1_ELAPSED  = "map1ElapsedMs";
    private static final String KEY_MAP2_START    = "map2StartMs";
    private static final String KEY_TOTAL_ELAPSED = "totalElapsedMs";
    private static final String KEY_COMPLETED     = "completed";
    private final int mapWidth = 12, mapHeight = 12;
    private final int FLOOR = 1, SPAWN_TILE = 2;
    private final int TV = 14, PENDULUM = 15, EXTINGUISHER = 16;
    private final int CHEST_OPEN = 17, CHEST_CLOSED = 18;
    private static final int GUARD_POS_1  = 110;
    private static final int GUARD_POS_2  = 111;
    private static final int PLAYER_SPAWN = 122;
    private static final int LADDER_POS_1 = 98;
    private static final int LADDER_POS_2 = 99;
    private static final int TV_POS    = 32;
    private static final int PEND_POS  = 70;
    private static final int EXTI_POS  = 102;
    private static final int CHEST_POS = 28;
    private Map<Integer, GameObject> gameObjectRegistry = new HashMap<>();
    private cs4game_physicslab labRef;
    private String playerType = "boy";
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
    private Map<String, String> readInventoryFile() {
        Map<String, String> data = new HashMap<>();
        Path path = Paths.get(INVENTORY_FILE);
        if (!Files.exists(path)) return data;
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                int eq = line.indexOf('=');
                if (eq < 0) continue;
                data.put(line.substring(0, eq).trim(), line.substring(eq + 1).trim());
            }
        } catch (IOException ex) {
            System.err.println("[Inventory] read error: " + ex.getMessage());
        }
        return data;
    }
    private void saveInventoryFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(INVENTORY_FILE))) {
            pw.println(KEY_HOLDING  + "=" + holdingItemID);
            pw.println(KEY_IN_CHEST + "=" + itemInChest);
            pw.println(KEY_CHEST_CL + "=" + chestClosed);
            pw.println(KEY_FINISHED + "=" + gameFinished);
            pw.println(KEY_GUARDS   + "=" + guardsPresent);
        } catch (IOException ex) {
            System.err.println("[Inventory] write error: " + ex.getMessage());
        }
    }
    private void loadInventoryFile() {
        Map<String, String> data = readInventoryFile();
        if (data.isEmpty()) return;          
        holdingItemID = parseInt(data, KEY_HOLDING,  0);
        itemInChest   = parseInt(data, KEY_IN_CHEST, 0);
        chestClosed   = parseBool(data, KEY_CHEST_CL, false);
        gameFinished  = parseBool(data, KEY_FINISHED, false);
        guardsPresent = parseBool(data, KEY_GUARDS,   true);
    }
    static void clearInventoryFile() {
        try { Files.deleteIfExists(Paths.get(INVENTORY_FILE)); }
        catch (IOException ex) { }
    }
    private static int parseInt(Map<String, String> m, String key, int def) {
        try { return Integer.parseInt(m.getOrDefault(key, String.valueOf(def))); }
        catch (NumberFormatException e) { return def; }
    }
    private static boolean parseBool(Map<String, String> m, String key, boolean def) {
        String v = m.get(key);
        if (v == null) return def;
        return Boolean.parseBoolean(v);
    }
    static Map<String, String> readTimerFile() {
        Map<String, String> data = new HashMap<>();
        Path path = Paths.get(TIMER_FILE);
        if (!Files.exists(path)) return data;
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                int eq = line.indexOf('=');
                if (eq < 0) continue;
                data.put(line.substring(0, eq).trim(), line.substring(eq + 1).trim());
            }
        } catch (IOException ex) {
            System.err.println("[Timer] read error: " + ex.getMessage());
        }
        return data;
    }
    static void writeTimerFile(Map<String, String> data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TIMER_FILE))) {
            for (Map.Entry<String, String> e : data.entrySet()) {
                pw.println(e.getKey() + "=" + e.getValue());
            }
        } catch (IOException ex) {
            System.err.println("[Timer] write error: " + ex.getMessage());
        }
    }
    private void startMap2Timer() {
        Map<String, String> data = readTimerFile();
        data.put(KEY_MAP2_START, String.valueOf(System.currentTimeMillis()));
        writeTimerFile(data);
    }
    private void stopTimerAndReport() {
        Map<String, String> data = readTimerFile();
        long map1Elapsed = 0;
        long map2Start   = 0;
        try { map1Elapsed = Long.parseLong(data.getOrDefault(KEY_MAP1_ELAPSED, "0")); }
        catch (NumberFormatException ignored) {}
        try { map2Start   = Long.parseLong(data.getOrDefault(KEY_MAP2_START,   "0")); }
        catch (NumberFormatException ignored) {}
        long map2Elapsed = (map2Start > 0) ? (System.currentTimeMillis() - map2Start) : 0;
        long totalMs     = map1Elapsed + map2Elapsed;
        data.put(KEY_MAP2_START,    String.valueOf(map2Start));
        data.put(KEY_TOTAL_ELAPSED, String.valueOf(totalMs));
        data.put(KEY_COMPLETED,     "true");
        writeTimerFile(data);
        long totalSec  = totalMs / 1000;
        long minutes   = totalSec / 60;
        long seconds   = totalSec % 60;
        long millis    = totalMs % 1000;
        String timeStr = String.format("%d min %d sec %d ms", minutes, seconds, millis);
        JOptionPane.showMessageDialog(frame,
            "🎉 You completed both maps!\n\n" +
            "Total time: " + timeStr + "\n\n" +
            "(Your time has been saved to " + TIMER_FILE + ")",
            "Completion Time", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new sumGUI().gr8a();
            
    }
    public cs4backroom(cs4game_physicslab lab) {
        this.labRef = lab;
        init();
    }
    public cs4backroom() {
        init();
    }
    private void init() {
        loadPlayerType();
        loadInventoryFile();
        gameObjectRegistry.put(TV,           new Item(TV,           "Television"));
        gameObjectRegistry.put(PENDULUM,     new Item(PENDULUM,     "Pendulum"));
        gameObjectRegistry.put(EXTINGUISHER, new Item(EXTINGUISHER, "Extinguisher"));
        gameObjectRegistry.put(CHEST_OPEN,   new Chest(CHEST_OPEN,   "Chest"));
        gameObjectRegistry.put(CHEST_CLOSED, new Chest(CHEST_CLOSED, "Chest"));
        frame = new JFrame("The Backrooms");
        String prefix = playerType.equals("girl") ? "girl" : "boy";
        String[] spriteNums = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for (String num : spriteNums) {
            String key = "girl" + num; // internal key stays the same for compatibility
            String file = prefix + num;
            int w = 1000/12, h = 1000/12;
            playerSprites.put(key, loadAndScale("physicslabtiles/" + file + ".png", w, h));
        }
        playerSprites.put("enemy", loadAndScale("physicslabtiles/enemy.png", 1300/12, 2000/12));
        mapLayout = new int[]{
            10,13, 4, 5,13,13,13,13,13,13,13,11,
             8, 1, 4, 5, 1, 1, 1, 1, 1, 1, 1, 9,
             8, 1, 4, 5, 1, 1, 1, 1, 1, 1, 1, 9,
             8, 1, 4, 5, 1, 1, 1, 1, 1, 1, 1, 9,
             8, 1, 4, 5, 1, 1, 1, 1, 1, 1, 1, 9,
             8, 1, 3, 7, 1, 1, 1, 1, 1, 1, 1, 9,
             8, 1, 3, 7, 1, 1, 1, 1, 1, 1, 1, 9,
             8, 1, 3, 7, 1, 1, 1, 1, 1, 1, 1, 9,
             8, 1, 3, 7, 1, 1, 1, 1, 1, 1, 1, 9,
             8, 1, 2, 6, 1, 1, 1, 1, 1, 1, 1, 9,
             8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9,
            12,12,12,12,12,12,12,12,12,12,12,12,
        };
        tiles     = new JLabel[144];
        objectMap = new JLabel[144];
        playerMap = new JLabel[144];
        for (int i = 0; i < 144; i++) {
            tiles[i]     = new JLabel(loadAndScale("physicslabtiles/asset" + mapLayout[i] + ".jpg", 1000/12, 1000/12));
            objectMap[i] = new JLabel();
            objectMap[i].setName("0");
            playerMap[i] = new JLabel();
        }
        playerPos = PLAYER_SPAWN;
        playerMap[playerPos].setIcon(playerSprites.get("girl01"));
        if (holdingItemID != TV && itemInChest != TV)
            placeObject(TV_POS,   TV);
        if (holdingItemID != PENDULUM && itemInChest != PENDULUM)
            placeObject(PEND_POS, PENDULUM);
        if (holdingItemID != EXTINGUISHER && itemInChest != EXTINGUISHER)
            placeObject(EXTI_POS, EXTINGUISHER);
        if (chestClosed) {
            placeObject(CHEST_POS, CHEST_CLOSED);
        } else {
            placeObject(CHEST_POS, CHEST_OPEN);
        }
        if (guardsPresent) {
            playerMap[GUARD_POS_1].setIcon(playerSprites.get("enemy"));
            playerMap[GUARD_POS_2].setIcon(playerSprites.get("enemy"));
        }
        objectMap[LADDER_POS_1].setName("LADDER");
        objectMap[LADDER_POS_2].setName("LADDER");
    }
    public int  getHoldingItemID()       { return holdingItemID; }
    public void setHoldingItemID(int id) {
        holdingItemID = id;
        saveInventoryFile();   
    }
    public JFrame getFrame()             { return frame; }
    public void placeObject(int pos, int assetID) {
        if (assetID <= 0) {
            objectMap[pos].setIcon(null);
            objectMap[pos].setName("0");
        } else {
            objectMap[pos].setIcon(loadAndScale("physicslabtiles/asset" + assetID + ".png", 1000/12, 1000/12));
            objectMap[pos].setName(String.valueOf(assetID));
        }
    }
    private ImageIcon loadAndScale(String path, int w, int h) {
        try {
            ImageIcon icon = new ImageIcon(path);
            return new ImageIcon(icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        } catch (Exception e) { return null; }
    }
    public void handleChestLogic(int chestPos) {
        if (chestClosed) {
            chestClosed = false;
            placeObject(chestPos, CHEST_OPEN);
            saveInventoryFile();
            return;
        }
        if (holdingItemID != 0) {
            if (itemInChest == 0) {
                itemInChest   = holdingItemID;
                holdingItemID = 0;
                saveInventoryFile();
                JOptionPane.showMessageDialog(frame, "You placed the object inside the chest.");
            } else {
                JOptionPane.showMessageDialog(frame, "The chest already has something in it!");
            }
        } else if (itemInChest != 0) {
            int confirm = JOptionPane.showConfirmDialog(frame,
                "Lock this item in? Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (itemInChest == PENDULUM) {
                    chestClosed  = true;
                    gameFinished = true;
                    placeObject(chestPos, CHEST_CLOSED);
                    guardsPresent = false;
                    playerMap[GUARD_POS_1].setIcon(null);
                    playerMap[GUARD_POS_2].setIcon(null);
                    saveInventoryFile();
                    JOptionPane.showMessageDialog(frame,
                        "Guard 1: \"You actually did it! You freed us!\"\n" +
                        "Guard 2: \"Thank you!\"\n" +
                        "Guard 1: \"Now get out of here! Take the ladder!\"");
                    stopTimerAndReport();
                } else {
                    String q;
                    boolean correctAnswer;
                    if (itemInChest == TV) {
                        q = "Are there 3 primary electric charges? (Yes / No)";
                        String ans = askYesNo(q);
                        correctAnswer = ans.equalsIgnoreCase("no");
                    } else {
                        q = "Is light ONLY considered a wave? (Yes / No)";
                        String ans = askYesNo(q);
                        correctAnswer = ans.equalsIgnoreCase("no");
                    }
                    if (correctAnswer) {
                        JOptionPane.showMessageDialog(frame,
                            "Guard 1: \"You're close!\"\n" +
                            "Guard 2: \"You got this, champ!\"");
                    } else {
                        JOptionPane.showMessageDialog(frame,
                            "Guard 1: \"We all start somewhere!\"\n" +
                            "Guard 2: \"Keep going!\"\n" +
                            "Guard 1: \"Pick yourself up!\"");
                    }
                    holdingItemID = itemInChest;
                    itemInChest   = 0;
                    placeObject(chestPos, CHEST_OPEN);
                    saveInventoryFile();
                }
            } else {
                holdingItemID = itemInChest;
                itemInChest   = 0;
                saveInventoryFile();
                JOptionPane.showMessageDialog(frame, "You took the item back out.");
            }
        }
    }
    private String askYesNo(String question) {
        while (true) {
            String ans = JOptionPane.showInputDialog(frame, question + "\n(Type Yes or No)");
            if (ans == null) continue;
            if (ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("no")) return ans;
            JOptionPane.showMessageDialog(frame, "Please type 'Yes' or 'No'.");
        }
    }
    private void handleGuardInteract() {
        JOptionPane.showMessageDialog(frame,
            "Guard 1: \"Put the right object in the chest!\"\n" +
            "Guard 2: \"Not that we're trying to trap you or anything.\"\n" +
            "Guard 1: \"It's just really important. Don't ask.\"\n" +
            "Guard 2: \"The items are scattered around. One of them is definitely the right one.\"\n" +
            "Guard 1: \"Just think Physics.\"");
    }
    private void handleLadderInteract() {
        if (!gameFinished) {
            JOptionPane.showMessageDialog(frame,
                "Guard 2: \"Where do you think you're going?!\"\n" +
                "Guard 1: \"The chest. Sort it out first. Then you can leave.\"");
            return;
        }
        JOptionPane.showMessageDialog(frame, "You climb the ladder back up to the Physics Lab...");
        frame.setVisible(false);
        if (labRef != null) {
            labRef.getFrame().setVisible(true);
        } else {
            cs4game_physicslab lab = new cs4game_physicslab();
            lab.setFrame();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_E) return;
        int target = getFacingPos();
        if (target == -1) return;
        String targetName = objectMap[target].getName();
        if ("LADDER".equals(targetName)) {
            handleLadderInteract();
            return;
        }
        if (gameFinished) return;
        if (guardsPresent && (target == GUARD_POS_1 || target == GUARD_POS_2)) {
            handleGuardInteract();
            return;
        }
        int objID = 0;
        try { objID = Integer.parseInt(targetName == null ? "0" : targetName); }
        catch (NumberFormatException ex) { objID = 0; }
        if (gameObjectRegistry.containsKey(objID)) {
            gameObjectRegistry.get(objID).interact(this, target);
        } else if (holdingItemID != 0 && objID == 0 && mapLayout[target] == FLOOR) {
            placeObject(target, holdingItemID);
            holdingItemID = 0;
            saveInventoryFile();
        }
    }
    private int getFacingPos() {
        int row = playerPos / 12, col = playerPos % 12;
        return switch (lastdirection) {
            case up    -> (row > 0)  ? playerPos - 12 : -1;
            case down  -> (row < 11) ? playerPos + 12 : -1;
            case left  -> (col > 0)  ? playerPos - 1  : -1;
            case right -> (col < 11) ? playerPos + 1  : -1;
        };
    }
    public void setFrame() {
        frame.setLayout(new GraphPaperLayout(new Dimension(12, 12)));
        for (int i = 0; i < 144; i++) frame.add(playerMap[i], new Rectangle(i % 12, i / 12, 1, 1));
        for (int i = 0; i < 144; i++) frame.add(objectMap[i], new Rectangle(i % 12, i / 12, 1, 1));
        for (int i = 0; i < 144; i++) frame.add(tiles[i],     new Rectangle(i % 12, i / 12, 1, 1));
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        SwingUtilities.invokeLater(() -> {
            startMap2Timer();   
            JOptionPane.showMessageDialog(frame,
                "Guard 1: \"Oh good, you made it down here.\"\n" +
                "Guard 2: \"Welcome to the Backrooms!\"\n" +
                "Guard 1: \"Put the right object in the chest over there if you want to leave.\"\n" +
                "Guard 2: \"Not that we're trying to trap you or anything. It's just really important.\"\n" +
                "Guard 1: \"Don't ask.\"");
        });
        idleTimer = new Timer(300, ev -> {
            String sprite = switch (lastdirection) {
                case up    -> "girl02";
                case down  -> "girl01";
                case left  -> "girl03";
                case right -> "girl04";
            };
            playerMap[playerPos].setIcon(playerSprites.get(sprite));
        });
        idleTimer.setRepeats(false);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int nextPos = playerPos;
        String s1 = "", s2 = "";
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP    -> { nextPos = playerPos - 12; lastdirection = direction.up;    s1 = "girl07"; s2 = "girl08"; }
            case KeyEvent.VK_DOWN  -> { nextPos = playerPos + 12; lastdirection = direction.down;  s1 = "girl05"; s2 = "girl06"; }
            case KeyEvent.VK_LEFT  -> { if (playerPos % 12 == 0) return;       nextPos = playerPos - 1; lastdirection = direction.left;  s1 = "girl10"; s2 = "girl12"; }
            case KeyEvent.VK_RIGHT -> { if ((playerPos+1) % 12 == 0) return;   nextPos = playerPos + 1; lastdirection = direction.right; s1 = "girl09"; s2 = "girl11"; }
        }
        boolean walkable = mapLayout[nextPos] == FLOOR
                        || mapLayout[nextPos] == SPAWN_TILE
                        || mapLayout[nextPos] == 6;
        boolean blocked  = objectMap[nextPos].getIcon() != null
                        || playerMap[nextPos].getIcon() != null;
        if (nextPos != playerPos && walkable && !blocked) {
            playerMap[playerPos].setIcon(null);
            playerPos = nextPos;
            playerMap[playerPos].setIcon(playerSprites.get(walkstate == 0 ? s1 : s2));
            walkstate = (walkstate == 0) ? 1 : 0;
            idleTimer.restart();
        }
    }
    @Override public void keyTyped(KeyEvent e) {}
    public static void main(String[] args) {
    // Ensure the UI is created on the Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
        // 1. Create the game instance
        cs4backroom game = new cs4backroom(); 
        
        // 2. Build the map and show the window
        game.setFrame();
    });
}
}