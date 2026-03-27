package Q2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author USER
 */
public class chunk1q implements ActionListener{
    JFrame frame;
    JLabel label;
    JButton d;
    JButton p;
    JButton t;
    JButton a;
    private boolean finished = false;
    private boolean answerCorrect = false;


    public chunk1q() {
    }
    public void MainCodeBlock() {
        frame = new JFrame("PE3JONSON");
        d = new JButton("var x = 0");
        t = new JButton("x = 0");
        p = new JButton("int x = 0");
        a = new JButton("(int/float/double/String) x = 0");
        p.addActionListener(this);
        d.addActionListener(this);
        t.addActionListener(this);
        a.addActionListener(this);
        frame.setLayout(new GraphPaperLayout(new Dimension(12, 12)));
        label = new JLabel("When Coding in Python, how do you initialize a variable?", SwingConstants.CENTER);
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
    public boolean startdaquestion1() {
        SwingUtilities.invokeLater(this::MainCodeBlock);

        while (!finished) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {}
        }

        return answerCorrect;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == t) {
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
        SwingUtilities.invokeLater(PE3JONSON::new);
    }
}