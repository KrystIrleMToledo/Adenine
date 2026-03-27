package Q2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author USER
 */
public class chunk5q implements ActionListener{
    JFrame frame;
    JLabel label;
    JButton d;
    JButton p;
    JButton t;
    JButton a;
    private boolean finished = false;
    private boolean answerCorrect = false;


    public chunk5q() {

    }
    public void MainCodeBlock() {
        frame = new JFrame("PE3JONSON");
        d = new JButton("hypotanus");
        p = new JButton("sin");
        t = new JButton("cos");
        a = new JButton("tan");
        p.addActionListener(this);
        d.addActionListener(this);
        t.addActionListener(this);
        a.addActionListener(this);
        frame.setLayout(new GraphPaperLayout(new Dimension(12, 12)));
        label = new JLabel("What does the S in SOHCAHTOA mean?", SwingConstants.CENTER);
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
    public boolean startdaquestion5() {
        SwingUtilities.invokeLater(this::MainCodeBlock);

        while (!finished) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {}
        }

        return answerCorrect;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == p) {
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
        SwingUtilities.invokeLater(chunk5q::new);
    }
}