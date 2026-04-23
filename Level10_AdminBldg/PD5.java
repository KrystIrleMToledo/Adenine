PD5:
package Q2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PD5 extends JPanel implements KeyListener {
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

    // Player animation
    ImageIcon pU1, pU2, pD1, pD2, pL1, pL2, pR1, pR2;
    int characterMode = 0;

    Player player;

    boolean gameWon = false;

   
    int penRow = 0;
    int penCol = 9;

   
    int lastDX = 0;
    int lastDY = 0;
   
    String playerType = "boy"; // default

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
       
        try {
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
            if(mapLayout.length != mapWidth * mapHeight){
                throw new ArrayIndexOutOfBoundsException(
                    "Map is either too big or too small than the given size"
                );
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(frame,"Map is either too big or too small than the given size","Error",JOptionPane.ERROR_MESSAGE);
        }
       
       
       

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

        return tile == 0 || tile == 9; // shelves NOT walkable
    }
   
   

    @Override
    public void keyPressed(KeyEvent e) {
        try{
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {

                throw new InvalidKeyException("USE WASD KEYS ONLY");
            }
            int newX = player.getX();
            int newY = player.getY();

            if (e.getKeyCode() == KeyEvent.VK_W) {
                newY -= tileSize;
                lastDX = 0; lastDY = -1;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                newY += tileSize;
                lastDX = 0; lastDY = 1;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                newX -= tileSize;
                lastDX = -1; lastDY = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                newX += tileSize;
                lastDX = 1; lastDY = 0;
            }

            if (isWalkable(newX, newY)) {
                player.move((newX - player.getX()) / tileSize,
                (newY - player.getY()) / tileSize);

                // Animate based on direction
                ImageIcon current;

                if (lastDY == -1) { // UP
                    current = (characterMode == 0) ? pU1 : pU2;
                }
                else if (lastDY == 1) { // DOWN
                    current = (characterMode == 0) ? pD1 : pD2;
                }
                else if (lastDX == -1) { // LEFT
                    current = (characterMode == 0) ? pL1 : pL2;
                }
                else if (lastDX == 1) { // RIGHT
                    current = (characterMode == 0) ? pR1 : pR2;
                }

                player.setImage(current);
                characterMode ^= 1;
            }

            if (e.getKeyCode() == KeyEvent.VK_E) {
                interact();
            }
        }
        catch(InvalidKeyException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }

        repaint();
    }

    private void interact() {

        int playerCol = player.getX() / tileSize;
        int playerRow = player.getY() / tileSize;

       
        int targetCol = playerCol + lastDX;
        int targetRow = playerRow + lastDY;

        if (targetCol < 0 || targetCol >= mapWidth ||
            targetRow < 0 || targetRow >= mapHeight)
            return;

        int tile = mapLayout[targetRow * mapWidth + targetCol];

       
        if (tile == 3) {

            if (targetRow == penRow && targetCol == penCol && !player.hasPen()) {
                player.setHasPen(true);
                JOptionPane.showMessageDialog(this,
                        "You found the hidden pen!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Just old dusty books...");
            }
        }

       
        else if (tile == 9) {

            if (player.hasPen() && !gameWon) {
                gameWon = true;
                JOptionPane.showMessageDialog(this,
                        "Document Signed! Mission Complete!");
            } else if (!player.hasPen()) {
                JOptionPane.showMessageDialog(this,
                        "You need a pen first!");
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new PD5();
    }
}