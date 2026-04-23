package PD8TOTAL; 

import javax.swing.*;

public class PD8 extends JFrame {
    
    public PD8() {
        setTitle("LEVEL 2: Volatile Panic");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void gr2start(){
        showMap1();
    }

    public void showMap1() {
        PD8TOTAL.Q2PD4 map1 = new Q2PD4(this); 
        setContentPane(map1.getMainPanel()); 
        revalidate();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showMap2() {
        PD8TOTAL.Q4PD8 map2 = new PD8TOTAL.Q4PD8(this);
        setContentPane(map2);
        revalidate();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    

    public boolean gr2() {
        SwingUtilities.invokeLater(this::gr2start);
        return true;
    }
    public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> {

        PD8 game = new PD8();   // create level window
        game.gr2start();        // start Level 2

    });

    }
}
