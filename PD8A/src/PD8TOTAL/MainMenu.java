package PD8TOTAL;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;

public class MainMenu {

    JFrame frame;

    public MainMenu() {

        frame = new JFrame("Main Menu");
        frame.setSize(900, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        JLayeredPane layers = new JLayeredPane();
        layers.setBounds(0, 0, 900, 900);

        JLabel bg = new JLabel(new ImageIcon(
                new ImageIcon("Images/MainMenu/bg only.jpg")
                        .getImage()
                        .getScaledInstance(900, 900, Image.SCALE_SMOOTH)
        ));
        bg.setBounds(0, 0, 900, 900);

        JLabel decor1 = new JLabel(new ImageIcon(
                new ImageIcon("Images/MainMenu/characs.png")
                        .getImage()
                        .getScaledInstance(900, 900, Image.SCALE_SMOOTH)
        ));
        decor1.setBounds(0, 0, 900, 900);

        JLabel decor2 = new JLabel(new ImageIcon(
                new ImageIcon("Images/MainMenu/characs(1).png")
                        .getImage()
                        .getScaledInstance(900, 900, Image.SCALE_SMOOTH)
        ));
        decor2.setBounds(0, 0, 900, 900);

        JButton play = createButton("Images/MainMenu/playbutton.png", 350, 300);
        JButton load = createButton("Images/MainMenu/loadbutton.png", 350, 420);
        JButton settings = createButton("Images/MainMenu/settingsbutton.png", 350, 540);

        play.addActionListener(e -> {
            frame.dispose();
            new PD7p1().setFrame(); // start your game
        });

        load.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Load feature not implemented yet.");
        });

        settings.addActionListener(e -> showSettings());

        layers.add(bg, Integer.valueOf(0));
        layers.add(decor1, Integer.valueOf(1));
        layers.add(decor2, Integer.valueOf(2));
        layers.add(play, Integer.valueOf(3));
        layers.add(load, Integer.valueOf(3));
        layers.add(settings, Integer.valueOf(3));

        frame.add(layers);
        frame.setVisible(true);
    }

    private JButton createButton(String img, int x, int y) {
        JButton btn = new JButton(new ImageIcon(
                new ImageIcon(img)
                        .getImage()
                        .getScaledInstance(200, 80, Image.SCALE_SMOOTH)
        ));
        btn.setBounds(x, y, 200, 80);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        return btn;
    }

    private void showSettings() {

        String[] options = {"Boy", "Girl"};

        int choice = JOptionPane.showOptionDialog(
                frame,
                "Choose your character:",
                "Settings",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        String selected = (choice == 1) ? "girl" : "boy";

        try {
            FileWriter fw = new FileWriter("substitute.txt");
            fw.write(selected);
            fw.close();

            JOptionPane.showMessageDialog(frame,
                    "Saved: " + selected);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Error saving settings");
        }
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}