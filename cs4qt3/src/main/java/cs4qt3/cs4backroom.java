/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
            JOptionPane.showMessageDialog(null, "You're holding an item.");
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
    private int holdingItemID = 0;
    private int itemInChest = 0;
    private boolean chestClosed = false;
    private boolean gameFinished = false;
    private Map<Integer, GameObject> gameObjectRegistry = new HashMap<>();

    private final int mapWidth = 12, mapHeight = 12;
    private final int FLOOR = 1, SPAWN_TILE = 2;
    private final int TV = 14, PENDULUM = 15, EXTINGUISHER = 16, CHEST_OPEN = 17, CHEST_CLOSED = 18;

    private enum direction { up, down, left, right }
    private direction lastdirection = direction.down;
    private Timer idleTimer;
    private int walkstate = 0;

    public cs4backroom() {
        gameObjectRegistry.put(TV, new Item(TV, "Television"));
        gameObjectRegistry.put(PENDULUM, new Item(PENDULUM)); 
        gameObjectRegistry.put(EXTINGUISHER, new Item(EXTINGUISHER, "Extinguisher"));
        gameObjectRegistry.put(CHEST_OPEN, new Chest(CHEST_OPEN, "Chest"));
        gameObjectRegistry.put(CHEST_CLOSED, new Chest(CHEST_CLOSED, "Chest"));
        
        frame = new JFrame("Backroom Adventure");
        String[] spriteNames = {"girl01", "girl02", "girl03", "girl04", "girl05", "girl06", "girl07", "girl08", "girl09", "girl10", "girl11", "girl12"};
        for (String name : spriteNames) {
            playerSprites.put(name, loadAndScale("physicslabtiles/" + name + ".png", 1000/12, 1000/12));
        }

        mapLayout = new int[]{
            10,13,4,5,13,13,13,13,13,13,13,11,
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

        tiles = new JLabel[144]; objectMap = new JLabel[144]; playerMap = new JLabel[144];
        int spawnIndex = 13;
        for (int i = 0; i < 144; i++) {
            tiles[i] = new JLabel(loadAndScale("physicslabtiles/asset" + mapLayout[i] + ".jpg", 1000/12, 1000/12));
            objectMap[i] = new JLabel();
            playerMap[i] = new JLabel();
            if (mapLayout[i] == SPAWN_TILE) spawnIndex = i;
        }
        playerPos = spawnIndex;
        playerMap[playerPos].setIcon(playerSprites.get("girl01"));

        placeObject(25, TV); placeObject(42, CHEST_OPEN); placeObject(73, PENDULUM); placeObject(109, EXTINGUISHER);
    }

    public int getHoldingItemID() { return holdingItemID; }
    public void setHoldingItemID(int id) { this.holdingItemID = id; }

    public void placeObject(int pos, int assetID) {
        if (assetID <= 0) {
            objectMap[pos].setIcon(null);
            objectMap[pos].setName("0");
        } else {
            objectMap[pos].setIcon(loadAndScale("physicslabtiles/asset" + assetID + ".png", 1000/12, 1000/12));
            objectMap[pos].setName(String.valueOf(assetID));
        }
    }

    private ImageIcon loadAndScale(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(path);
            return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (Exception e) { return null; }
    }

    public void handleChestLogic(int chestPos) {
        if (chestClosed) {
            chestClosed = false;
            placeObject(chestPos, CHEST_OPEN);
        } else {
            if (holdingItemID != 0) {
                if (itemInChest == 0) {
                    itemInChest = holdingItemID;
                    holdingItemID = 0;
                    JOptionPane.showMessageDialog(frame, "You placed the object inside.");
                } else {
                    JOptionPane.showMessageDialog(frame, "You're holding an item.");
                }
            } else if (itemInChest != 0) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (itemInChest == PENDULUM) {
                        chestClosed = true; gameFinished = true;
                        placeObject(chestPos, CHEST_CLOSED);
                        JOptionPane.showMessageDialog(frame, "Correct item!");
                    } else {
                        String q = (itemInChest == TV) ? "Are there 3 primary electric charges?" : "Is light only considered a wave?";
                        String ans = "";

                        while (true) {
                            try {
                                ans = JOptionPane.showInputDialog(frame, q + " (Yes/No)");
                                
                                if (ans == null || (!ans.equalsIgnoreCase("yes") && !ans.equalsIgnoreCase("no"))) {
                                    throw new IllegalArgumentException("Invalid input. Please enter a valid command.");
                                }
                                
                                break; 
                                
                            } catch (IllegalArgumentException ex) {
                                JOptionPane.showMessageDialog(frame, ex.getMessage());
                            }
                        }

                        if (ans.equalsIgnoreCase("no")) {
                            JOptionPane.showMessageDialog(frame, "Correct! You get another chance to place the correct item.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Incorrect! You lost equipment. Place the right item next time");
                        }
                        holdingItemID = itemInChest; itemInChest = 0;
                        placeObject(chestPos, CHEST_OPEN);
                    }
                } else {
                    holdingItemID = itemInChest; itemInChest = 0;
                    JOptionPane.showMessageDialog(frame, "You removed the item.");
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameFinished || e.getKeyCode() != KeyEvent.VK_E) return;
        int target = getFacingPos();
        if (target == -1) return;

        int objID = Integer.parseInt(objectMap[target].getName() == null ? "0" : objectMap[target].getName());

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
            case up -> (row > 0) ? playerPos - 12 : -1;
            case down -> (row < 11) ? playerPos + 12 : -1;
            case left -> (col > 0) ? playerPos - 1 : -1;
            case right -> (col < 11) ? playerPos + 1 : -1;
        };
    }

    public void setFrame() {
        frame.setLayout(new GraphPaperLayout(new Dimension(12, 12)));
        for (int i = 0; i < 144; i++) frame.add(playerMap[i], new Rectangle(i % 12, i / 12, 1, 1));
        for (int i = 0; i < 144; i++) frame.add(objectMap[i], new Rectangle(i % 12, i / 12, 1, 1));
        for (int i = 0; i < 144; i++) frame.add(tiles[i], new Rectangle(i % 12, i / 12, 1, 1));
        frame.setSize(1000, 1000); frame.setVisible(true); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "We have to put the right object in the chest. Which one is it though?"));
        
        idleTimer = new Timer(300, e -> {
            String sprite = switch (lastdirection) { case up->"girl02"; case down->"girl01"; case left->"girl03"; case right->"girl04"; };
            playerMap[playerPos].setIcon(playerSprites.get(sprite));
        });
        idleTimer.setRepeats(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int nextPos = playerPos;
        String s1 = "", s2 = "";
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> { nextPos = playerPos - 12; lastdirection = direction.up; s1 = "girl07"; s2 = "girl08"; }
            case KeyEvent.VK_DOWN -> { nextPos = playerPos + 12; lastdirection = direction.down; s1 = "girl05"; s2 = "girl06"; }
            case KeyEvent.VK_LEFT -> { if (playerPos % 12 == 0) return; nextPos = playerPos - 1; lastdirection = direction.left; s1 = "girl10"; s2 = "girl12"; }
            case KeyEvent.VK_RIGHT -> { if ((playerPos + 1) % 12 == 0) return; nextPos = playerPos + 1; lastdirection = direction.right; s1 = "girl09"; s2 = "girl11"; }
        }
        if (nextPos != playerPos && (objectMap[nextPos].getIcon() == null) && (mapLayout[nextPos] == 1 || mapLayout[nextPos] == 2 || mapLayout[nextPos] == 6)) {
            playerMap[playerPos].setIcon(null);
            playerPos = nextPos;
            playerMap[playerPos].setIcon(playerSprites.get(walkstate == 0 ? s1 : s2));
            walkstate = (walkstate == 0) ? 1 : 0;
            idleTimer.restart();
        }
    }
    @Override public void keyTyped(KeyEvent e) {}
}