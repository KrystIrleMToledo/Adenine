package Q2;

import javax.swing.*;
import java.awt.*;

public class GameObject {

    protected int x;
    protected int y;
    protected int size;
    protected ImageIcon sprite;

    public GameObject(int x, int y, int size, ImageIcon sprite) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.sprite = sprite;
    }

    // Encapsulation (getters)
    public int getX() { return x; }
    public int getY() { return y; }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public void draw(Graphics g, Component c) {
        g.drawImage(sprite.getImage(), x, y, size, size, c);
    }
}