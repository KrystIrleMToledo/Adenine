package PD8TOTAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author USER
 */
public class chunk4q implements ActionListener{
    JFrame frame;
    JLabel label;
    JButton d;
    JButton p;
    JButton t;
    JButton a;
    private boolean finished = false;
    private boolean answerCorrect = false;


    public chunk4q() {

    }
    public void MainCodeBlock() {
        frame = new JFrame("PE3JONSON");
        d = new JButton("electron");
        p = new JButton("neutron");
        t = new JButton("proton");
        a = new JButton("nucleus");
        p.addActionListener(this);
        d.addActionListener(this);
        t.addActionListener(this);
        a.addActionListener(this);
        frame.setLayout(new GraphPaperLayout(new Dimension(12, 12)));
        label = new JLabel("What is the part of an atom with a negative charge?", SwingConstants.CENTER);
        frame.add(d, new Rectangle(1, 6, 5, 2));
        frame.add(a, new Rectangle(6, 6, 5, 2));
        frame.add(p, new Rectangle(1, 8, 5, 2));
        frame.add(t, new Rectangle(6, 8, 5, 2));
        frame.add(label, new Rectangle(3, 4, 6, 1));
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public boolean startdaquestion4() {
        SwingUtilities.invokeLater(this::MainCodeBlock);

        while (!finished) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {}
        }

        return answerCorrect;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == d) {
            JOptionPane.showMessageDialog(frame, "Correct!", "Info", JOptionPane.INFORMATION_MESSAGE);
            answerCorrect = true;
        } else {
            JOptionPane.showMessageDialog(frame, "You are Wrong!", "Info", JOptionPane.INFORMATION_MESSAGE);
            answerCorrect = false;
        }

        finished = true;
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(chunk4q::new);
    }
}