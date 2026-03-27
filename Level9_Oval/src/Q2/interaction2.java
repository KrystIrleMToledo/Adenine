package Q2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author USER
 */
public class interaction2 implements ActionListener{
    JFrame frame;
    JLabel label;
    JButton x;
    JButton but1;
    JButton but2;
    private boolean finished = false;
    private boolean answerCorrect = false;
    int prog = 0;
    int wh = 0;
    int how = 0;
    boolean howb = false;
    boolean whenb = false;


    public interaction2() {
    }
    public void MainCodeBlock() {
        frame = new JFrame("PE3JONSON");
        x = new JButton("X");
        but1 = new JButton("Yes");
        but2 = new JButton("How do I get out of here?");
        but2.setVisible(false);
        but1.addActionListener(this);
        x.addActionListener(this);
        but2.addActionListener(this);
        frame.setLayout(new GraphPaperLayout(new Dimension(12, 12)));
        label = new JLabel("<html>Did you get the snake eye yet?</html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        frame.add(x, new Rectangle(11, 0, 1, 1));
        frame.add(but2, new Rectangle(1, 8, 5, 2));
        frame.add(but1, new Rectangle(6, 8, 5, 2));
        frame.add(label, new Rectangle(1, 4, 10, 2));
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public boolean startdainteraction2() {
        SwingUtilities.invokeLater(this::MainCodeBlock);

        while (!finished) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {}
        }

        return answerCorrect;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == x) {
            finished = true;
            frame.dispose();
        } else if (e.getSource() == but1){
            if (prog == 0) {
                label.setText("<html>Quick! Open the portal!</html>.");
                but1.setVisible(false);
                prog++;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(interaction2::new);
    }
}