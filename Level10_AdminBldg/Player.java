package Q2;

import javax.swing.*;
import java.awt.*;

public class Player extends GameObject {

    private int speed = 1; // tile-based movement
    private boolean hasPen = false;

    public Player(int x, int y, int size, ImageIcon sprite) {
        super(x, y, size, sprite);
    }

    public void move(int dx, int dy) {
        x += dx * size * speed;
        y += dy * size * speed;
    }

    public void setHasPen(boolean value) {
        hasPen = value;
    }

    public boolean hasPen() {
        return hasPen;
    }

    // Overriding method
    @Override
    public void draw(Graphics g, Component c) {
        super.draw(g, c);
    }
}
