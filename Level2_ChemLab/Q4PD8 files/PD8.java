package q2; 

import javax.swing.*;

public class PD8 extends JFrame {
    
    public PD8() {
        setTitle("LEVEL 2: Catalyst");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showMap1(); 
    }

    public void showMap1() {
       
        q2.Q2PD4 map1 = new Q2PD4(this); 
        setContentPane(map1.getMainPanel()); 
        revalidate();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showMap2() {
    
        Q4PD8files.Q4PD8 map2 = new Q4PD8files.Q4PD8(this);
        setContentPane(map2);
        revalidate();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PD8());
    }
}