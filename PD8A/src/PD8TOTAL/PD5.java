package PD8TOTAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PD5 extends JPanel implements KeyListener {

    // ===== INNER EXCEPTION =====
    class InvalidKeyException extends Exception {
        public InvalidKeyException(String message) {
            super(message);
        }
    }

    JFrame frame;

    int mapWidth = 12;
    int mapHeight = 12;
    int frameSize = 480;
    int tileSize = frameSize / mapWidth;

    int[] mapLayout;

    ImageIcon lighttile, shelf, books, pot, compdesk, scroll;

    ImageIcon pU1, pU2, pD1, pD2, pL1, pL2, pR1, pR2;
    int characterMode = 0;

    Player player;

    boolean gameWon = false;

    int penRow = 0;
    int penCol = 9;

    int lastDX = 0;
    int lastDY = 0;

    String playerType = "boy";

    private void loadPlayerType() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("substitute.txt"));
            String line = br.readLine();
            if (line != null) playerType = line.trim().toLowerCase();
            br.close();
        } catch (IOException e) {
            playerType = "boy";
        }
    }

    public PD5() {
        loadPlayerType();
        frame = new JFrame("Find the Pen Game");

        lighttile = new ImageIcon("GAMESPRITESCS/lighttile.png");
        shelf = new ImageIcon("GAMESPRITESCS/shelf.png");
        books = new ImageIcon("GAMESPRITESCS/books.png");
        pot = new ImageIcon("GAMESPRITESCS/pot.png");
        compdesk = new ImageIcon("GAMESPRITESCS/compdesk.png");
        scroll = new ImageIcon("GAMESPRITESCS/scroll.png");

        if (playerType.equals("girl")) {
            pU1 = new ImageIcon("Images/gr10/girlwalk5.png");
            pU2 = new ImageIcon("Images/gr10/girlwalk6.png");
            pD1 = new ImageIcon("Images/gr10/girlwalk1.png");
            pD2 = new ImageIcon("Images/gr10/girlwalk2.png");
            pL1 = new ImageIcon("Images/gr10/girlwalk4.png");
            pL2 = new ImageIcon("Images/gr10/girlwalk8.png");
            pR1 = new ImageIcon("Images/gr10/girlwalk3.png");
            pR2 = new ImageIcon("Images/gr10/girlwalk7.png");
        } else {
            pU1 = new ImageIcon("Images/gr10/boywalk5.png");
            pU2 = new ImageIcon("Images/gr10/boywalk6.png");
            pD1 = new ImageIcon("Images/gr10/boywalk1.png");
            pD2 = new ImageIcon("Images/gr10/boywalk2.png");
            pL1 = new ImageIcon("Images/gr10/boywalk4.png");
            pL2 = new ImageIcon("Images/gr10/boywalk8.png");
            pR1 = new ImageIcon("Images/gr10/boywalk3.png");
            pR2 = new ImageIcon("Images/gr10/boywalk7.png");
        }

        mapLayout = new int[]{
            3,3,3,3,3,3,3,3,3,3,3,3,
            0,0,1,0,0,0,1,0,0,0,1,0,
            0,4,4,0,4,4,0,4,4,0,4,0,
            1,0,0,1,0,0,0,0,0,0,0,0,
            0,1,2,0,1,2,0,1,2,0,1,0,
            0,0,0,0,0,1,0,0,0,1,0,0,
            0,3,3,1,3,3,0,3,3,0,3,0,
            1,0,0,0,0,0,0,0,0,1,0,0,
            0,2,2,0,2,2,0,2,2,0,2,0,
            0,1,0,0,1,0,0,0,1,0,0,0,
            0,2,2,0,2,2,0,2,2,0,2,1,
            0,0,0,0,0,0,0,0,0,0,0,0
        };

        mapLayout[5 * mapWidth + 5] = 9;

        player = new Player(0, 10 * tileSize, tileSize, pD1);

        frame.add(this);
        frame.setSize(frameSize, frameSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < mapLayout.length; i++) {
            int row = i / mapWidth;
            int col = i % mapWidth;
            int x = col * tileSize;
            int y = row * tileSize;

            switch (mapLayout[i]) {
                case 0 -> g.drawImage(lighttile.getImage(), x, y, tileSize, tileSize, this);
                case 1 -> g.drawImage(pot.getImage(), x, y, tileSize, tileSize, this);
                case 2 -> g.drawImage(compdesk.getImage(), x, y, tileSize, tileSize, this);
                case 3 -> g.drawImage(shelf.getImage(), x, y, tileSize, tileSize, this);
                case 4 -> g.drawImage(books.getImage(), x, y, tileSize, tileSize, this);
                case 9 -> g.drawImage(scroll.getImage(), x, y, tileSize, tileSize, this);
            }
        }

        player.draw(g, this);
    }

    private boolean isWalkable(int newX, int newY) {
        int col = newX / tileSize;
        int row = newY / tileSize;

        if (col < 0 || col >= mapWidth || row < 0 || row >= mapHeight)
            return false;

        int tile = mapLayout[row * mapWidth + col];
        return tile == 0 || tile == 9;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int newX = player.getX();
        int newY = player.getY();

        if (e.getKeyCode() == KeyEvent.VK_W) newY -= tileSize;
        if (e.getKeyCode() == KeyEvent.VK_S) newY += tileSize;
        if (e.getKeyCode() == KeyEvent.VK_A) newX -= tileSize;
        if (e.getKeyCode() == KeyEvent.VK_D) newX += tileSize;

        if (isWalkable(newX, newY)) {
            player.move((newX - player.getX()) / tileSize,
                        (newY - player.getY()) / tileSize);
        }

        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    
    public boolean gr10() {
        SwingUtilities.invokeLater(() -> new PD5());
        return true;
    }
    public static void main(String[] args) {
        new PD5();
    }
}

// ===== NON-PUBLIC CLASSES =====

class GameObjects {
    protected int x, y, size;
    protected ImageIcon sprite;

    public GameObjects(int x, int y, int size, ImageIcon sprite) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.sprite = sprite;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void draw(Graphics g, Component c) {
        g.drawImage(sprite.getImage(), x, y, size, size, c);
    }
}

class Player extends GameObjects {

    private boolean hasPen = false;

    public Player(int x, int y, int size, ImageIcon sprite) {
        super(x, y, size, sprite);
    }

    public void move(int dx, int dy) {
        x += dx * size;
        y += dy * size;
    }

    public boolean hasPen() { return hasPen; }
    public void setHasPen(boolean v) { hasPen = v; }
}