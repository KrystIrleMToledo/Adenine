package cs4qt3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
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
    private int  holdingItemID = 0;
    private int  itemInChest   = 0;
    private boolean chestClosed   = false;
    private boolean gameFinished  = false;
    private boolean guardsPresent = true;
    private final int mapWidth = 12, mapHeight = 12;
    private final int FLOOR = 1, SPAWN_TILE = 2;
    private final int TV = 14, PENDULUM = 15, EXTINGUISHER = 16;
    private final int CHEST_OPEN = 17, CHEST_CLOSED = 18;
    private static final int GUARD_POS_1 = 110;
    private static final int GUARD_POS_2 = 111;
    private static final int PLAYER_SPAWN = 122;
    private static final int LADDER_POS_1 = 98;
    private static final int LADDER_POS_2 = 99;
    private static final int TV_POS    = 32;
    private static final int PEND_POS  = 70;
    private static final int EXTI_POS  = 102;
    private static final int CHEST_POS = 28;
    private Map<Integer, GameObject> gameObjectRegistry = new HashMap<>();
    private cs4game_physicslab labRef;
    public cs4backroom(cs4game_physicslab lab) {
        this.labRef = lab;
        init();
    }
    public cs4backroom() {
        init();
    }
    private void init() {
        gameObjectRegistry.put(TV,           new Item(TV,           "Television"));
        gameObjectRegistry.put(PENDULUM,     new Item(PENDULUM,     "Pendulum"));
        gameObjectRegistry.put(EXTINGUISHER, new Item(EXTINGUISHER, "Extinguisher"));
        gameObjectRegistry.put(CHEST_OPEN,   new Chest(CHEST_OPEN,   "Chest"));
        gameObjectRegistry.put(CHEST_CLOSED, new Chest(CHEST_CLOSED, "Chest"));
        frame = new JFrame("The Backrooms");
        String[] spriteNames = {
            "girl01","girl02","girl03","girl04","girl05","girl06",
            "girl07","girl08","girl09","girl10","girl11","girl12","enemy"
        };
        for (String name : spriteNames) {
            int w = 1000/12, h = 1000/12;
            if (name.equals("enemy")) { w = 1300/12; h = 2000/12; }
            playerSprites.put(name, loadAndScale("physicslabtiles/" + name + ".png", w, h));
        }
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
        placeObject(TV_POS,   TV);
        placeObject(PEND_POS, PENDULUM);
        placeObject(EXTI_POS, EXTINGUISHER);
        placeObject(CHEST_POS, CHEST_OPEN);
        playerMap[GUARD_POS_1].setIcon(playerSprites.get("enemy"));
        playerMap[GUARD_POS_2].setIcon(playerSprites.get("enemy"));
        objectMap[LADDER_POS_1].setName("LADDER");
        objectMap[LADDER_POS_2].setName("LADDER");
    }
    public int  getHoldingItemID()       { return holdingItemID; }
    public void setHoldingItemID(int id) { holdingItemID = id;   }
    public JFrame getFrame()             { return frame;         }
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
            return;
        }
        if (holdingItemID != 0) {
            if (itemInChest == 0) {
                itemInChest   = holdingItemID;
                holdingItemID = 0;
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
                    JOptionPane.showMessageDialog(frame,
                        "Guard 1: \"THANK YOU! You actually did it! You freed us!\"\n" +
                        "Guard 2: \"Don't ask how, it's a long story. Seriously, don't ask.\"\n" +
                        "Guard 1: \"Now get out of here! Take the ladder — see you on the other side!\"");
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
                            "Guard 1: \"Okay, you at least know SOMETHING. Silver linings!\"\n" +
                            "Guard 2: \"Wrong object, right answer. You're like 40% there. You got this, champ!\"");
                    } else {
                        JOptionPane.showMessageDialog(frame,
                            "Guard 1: \"Wrong object AND wrong answer?! Bold strategy, honestly.\"\n" +
                            "Guard 2: \"Hey, at least you're consistently incorrect — that takes real talent!\"\n" +
                            "Guard 1: \"Pick yourself up. The right item is definitely out there. We believe in you. Mostly.\"");
                    }
                    holdingItemID = itemInChest;
                    itemInChest   = 0;
                    placeObject(chestPos, CHEST_OPEN);
                }
            } else {
                holdingItemID = itemInChest;
                itemInChest   = 0;
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
            "Guard 1: \"Still here? Put the right object in the chest!\"\n" +
            "Guard 2: \"Not that we're trying to trap you or anything...\"\n" +
            "Guard 1: \"It's just really, REALLY important. Cosmically important.\"\n" +
            "Guard 2: \"The items are scattered around. One of them is definitely the right one.\"\n" +
            "Guard 1: \"...Probably.\"");
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
            JOptionPane.showMessageDialog(frame,
                "Guard 1: \"Oh good, you made it down here.\"\n" +
                "Guard 2: \"Welcome to the Backrooms! We'd say make yourself at home, but...\"\n" +
                "Guard 1: \"Put the right object in the chest over there if you want to leave.\"\n" +
                "Guard 2: \"Not that we're trying to trap you or anything. It's just really important.\"\n" +
                "Guard 1: \"...Cosmically important.\"");
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
}