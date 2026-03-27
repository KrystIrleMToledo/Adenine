package Q2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author USER
 */
public class interaction implements ActionListener{
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


    public interaction() {
    }
    public void MainCodeBlock() {
        frame = new JFrame("PE3JONSON");
        x = new JButton("X");
        but1 = new JButton("Who are you?");
        but2 = new JButton("How do I get out of here?");
        but2.setVisible(false);
        but1.addActionListener(this);
        x.addActionListener(this);
        but2.addActionListener(this);
        frame.setLayout(new GraphPaperLayout(new Dimension(12, 12)));
        label = new JLabel("Hi there young man.", SwingConstants.CENTER);
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
    public boolean startdainteraction() {
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
                label.setText("<html>Who am I? I definitely look<br>like an old man.");
                but1.setText("Next");
                prog++;
            }
            else if (prog == 1){
                label.setText("<html>But in reality, I represent the faint determination<br>and motivation you have inside you.</html>");
                prog++;
            }
            else if (prog == 2){
                but2.setVisible(true);
                label.setText("<html>So, I am here to help you.</html>");
                but1.setText("Where am I?");
                but2.setText("How do I get out of here?");
                prog++;
            }
            else if (prog == 3) {
                but2.setVisible(false);
                label.setText("<html>You are inside a matrix, a place where a snake lives, and practically rules over</html>");
                prog++;
                but1.setText("Next");
            }
            else if (prog == 4) {
                label.setText("<html>This place has been influenced by your past trauma of being bitten by a snake once in the oval</html>");
                prog++;
            }
            else if (prog == 5) {
                label.setText("<html>You ran and.. you tripped.</html>");
                prog++;
            }
            else if (prog == 6) {
                label.setText("<html>Causing you to be sent to the hospital, both for getting the venom off your body,</html>");
                prog++;
            }
            else if (prog == 7) {
                label.setText("<html>and healing the wound on your head, which hit a rock when you tripped.</html>");
                prog++;
            }
            else if (prog == 8) {
                label.setText("<html>This snake.. represents misfortune.</html>");
                prog++;
            }
            else if (prog == 9) {
                label.setText("<html>This parallel universe, this dark version of the oval,</html>");
                prog++;
            }
            else if (prog == 10) {
                label.setText("<html>This place.. it represents, trauma.</html>");
                prog++;
            }
            else if (prog == 11) {
                but1.setVisible(false);
                label.setText("<html>Escape this place at all costs!</html>");
                if (howb == true) {
                    but2.setVisible(false);
                }
                else {
                    but2.setText("How do I get out of here?");
                    but2.setVisible(true);
                }
                whenb = true;
            }
        }
        
        else if (e.getSource() == but2){
            if (how == 0) {
                but1.setVisible(false);
                label.setText("<html>In the middle of the map, there is a snake, sealed in concrete.</html>");
                but2.setText("Next");
                how++;
            }
            else if (how == 1) {
                label.setText("<html>Obtaining one of the eyes of the snake will let you open this portal fully, leading you out of this place.</html>");
                but2.setText("Next");
                how++;
            }
            else if (how == 2) {
                label.setText("<html>To motivate it to break its own seal, you need to appetize it, by bringing atleast 6 rats</html>");
                but2.setText("Next");
                how++;
            }
            else if (how == 3) {
                label.setText("<html>Rats live inside trees.  You have to find them by interacting with the trees.</html>");
                but2.setText("Next");
                how++;
            }
            else if (how == 4) {
                label.setText("<html>Be careful though, as if you are too reckless, the rats may either scurry away, or fight you.</html>");
                but2.setText("Next");
                how++;
            }
            else if (how == 5) {
                label.setText("<html>If they do fight you and you get their health low, they might try to flee..</html>");
                but2.setText("Next");
                how++;
            }
            else if (how == 6) {
                label.setText("<html>Once you do get 6 rats, bring it to the snake to fight it.</html>");
                but2.setText("Next");
                how++;
            }
            else if (how == 7) {
                label.setText("<html>I wish you good luck.</html>");
                how++;
                but2.setVisible(false);
                if (whenb == true) {
                    but2.setVisible(false);
                }
                else {
                    but1.setText("Where am I?");
                    but1.setVisible(true);
                }
                howb = true;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(interaction::new);
    }
}