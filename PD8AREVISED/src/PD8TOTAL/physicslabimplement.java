package PD8TOTAL;

import javax.swing.SwingUtilities;

public class physicslabimplement {

    public boolean gr7() {
        SwingUtilities.invokeLater(() -> {
            cs4game_physicslab sg = new cs4game_physicslab();
            sg.setFrame();
        });
        return true;
    }

    public static void main(String[] args) {
        new physicslabimplement().gr7();
    }
}