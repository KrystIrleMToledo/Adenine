package swing;
//Other members: Maia Adelle Soyao & Zionne Kay Babia
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class PD7p1 implements KeyListener{
    JFrame frame;
    ImageIcon tile;
    ImageIcon s1;
    ImageIcon s2;
    ImageIcon s3;
    ImageIcon s4;
    ImageIcon s5;
    ImageIcon secret;
    ImageIcon secret2;
    ImageIcon board;
    ImageIcon playerIcon;
    ImageIcon playerIconfront;
    ImageIcon playerIconfront2;
    ImageIcon playerIconback;
    ImageIcon playerIconback2;
    ImageIcon playerIconleft;
    ImageIcon playerIconright;
    JLabel tiles[];
    JLabel character[];
    int mapLayout[];
    int onelocation;
    int twolocation;
    int threelocation;
    int fourlocation;
    int fivelocation;
    int boardlocation;
    int s5location;
    int secretlocation;
    int secret2location;
    int characterPlace[];
    int characterPosition;
    int mapWidth=12;
    int mapHeight=12;
    int frameWidth=450;
    int frameHeight=450;
    int points = 0;
    int attempts;
    int correctAnswers = 0;
    int characterMode = 1;
    boolean mathlock = false;
    boolean sciencelock = false;
    boolean historylock = false;
    boolean trivialock = false;
    boolean trivia2lock = false;
    long startTime;
    private int timeLeft = 120;  // seconds
    private int retries = 0;
    private Timer countdownTimer;
    private JLabel retryLabel;     // To show retries at top-left
    private JLabel timerLabel;     // To show countdown at top-right
   
    public PD7p1(){
        frame=new JFrame();
        characterPosition=-1;
       
        int tileSize = frameWidth/mapWidth;
       
        tile = new ImageIcon(new ImageIcon("Images/1.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        s1 = new ImageIcon(new ImageIcon("Images/2.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        secret = new ImageIcon(new ImageIcon("Images/2.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        secret2 = new ImageIcon(new ImageIcon("Images/2.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        s2 = new ImageIcon(new ImageIcon("Images/3.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        s3 = new ImageIcon(new ImageIcon("Images/4.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        s4 = new ImageIcon(new ImageIcon("Images/5.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        board = new ImageIcon(new ImageIcon("Images/6.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        s5 = new ImageIcon(new ImageIcon("Images/7.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconfront = playerIcon = new ImageIcon(new ImageIcon("Images/14.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconfront2 = new ImageIcon(new ImageIcon("Images/8.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconback  = new ImageIcon(new ImageIcon("Images/11.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconback2 = new ImageIcon(new ImageIcon("Images/12.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconright = new ImageIcon(new ImageIcon("Images/13.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconleft = new ImageIcon(new ImageIcon("Images/9.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
       
        character=new JLabel[mapWidth*mapHeight];
        characterPlace=new int[]{
            0,0,0,0,0,0,0,0,0,0,1,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0
        };
        for(int i=0;i<character.length;i++){
            if(characterPlace[i]==1){
                character[i]=new JLabel(playerIcon);
                characterPosition=i;
            }
            else character[i]=new JLabel();
        }
       
        tiles=new JLabel[mapWidth*mapHeight];
        mapLayout=new int[]{
            1,1,1,1,6,6,6,6,1,1,1,1,
            1,7,1,7,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,1,1,
            1,2,1,1,3,1,1,2,1,1,5,1,
            1,1,1,1,1,1,1,1,1,1,1,1,
            1,3,1,1,5,1,1,8,1,1,4,1,
            1,1,1,1,1,1,1,1,1,1,1,1,
            1,2,1,1,2,1,1,5,1,1,2,1,
            1,1,1,1,1,1,1,1,1,1,1,1,
            1,5,1,1,9,1,1,3,1,1,3,1,
            1,1,1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,1,1
        };
        for (int i = 0; i < tiles.length; i++) {
            if (mapLayout[i] == 1)
                tiles[i] = new JLabel(tile);
            else if (mapLayout[i] == 2) {
                tiles[i] = new JLabel(s1);
                onelocation = i;
            }
            else if (mapLayout[i] == 3) {
                tiles[i] = new JLabel(s2);
                twolocation = i;
            }
            else if (mapLayout[i] == 4) {
                tiles[i] = new JLabel(s3);
                threelocation = i;
            }
            else if (mapLayout[i] == 5) {
                tiles[i] = new JLabel(s4);
                fourlocation = i;
            }
            else if (mapLayout[i] == 6) {
                tiles[i] = new JLabel(board);
                boardlocation = i;
            }
            else if (mapLayout[i] == 7) {
                tiles[i] = new JLabel(s5);
                fivelocation = i;
            }
            else if (mapLayout[i] == 8) {
                tiles[i] = new JLabel(secret);
                secretlocation = i;
            }
            else if (mapLayout[i] == 9) {
                tiles[i] = new JLabel(secret2);
                secret2location = i;
            }
        }
    }
    
   public void showIntroScreen(Runnable afterIntro) {
        JFrame introFrame = new JFrame();
        introFrame.setSize(frameWidth, frameHeight);
        introFrame.setUndecorated(true);
        introFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JTextArea textArea = new JTextArea();
        textArea.setForeground(Color.GREEN);
        textArea.setBackground(Color.BLACK);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        panel.add(scrollPane, gbc);

        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        continueButton.setVisible(false);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        panel.add(continueButton, gbc);

        introFrame.add(panel);
        introFrame.setVisible(true);

        String text1 = "OBJECTIVE:\n\n"
                     + "Move the character using the arrow keys.\n"
                     + "There are questions on the tables around the map.\n\n"
                     + "Answer all 5 questions, find the correct tables.\n"
                     + "You can check your score on the teacher's table.\n"
                     + "Each question can only be answered once.\n\n"
                     + "To pass the level:\n"
                     + "- Get at least 60%\n"
                     + "- Go to the teacher's table";

        String text2 = "YOU HAVE 2 MINUTES.\nGoodluck.";

        class TypeWriter {
            private JTextArea textArea;
            private Timer timer;
            private String fullText;
            private int index;

            public TypeWriter(JTextArea textArea) {
                this.textArea = textArea;
            }

            public void start(String text, Runnable onFinish) {
                this.fullText = text;
                this.index = 0;
                textArea.setText(""); // clear previous
                timer = new Timer(50, null);
                timer.addActionListener(e -> {
                    if (index < fullText.length()) {
                        textArea.append("" + fullText.charAt(index));
                        index++;
                        textArea.setCaretPosition(textArea.getDocument().getLength()); // scroll down
                    } else {
                        timer.stop();
                        if (onFinish != null) onFinish.run();
                    }
                });
                timer.start();
            }
        }

        TypeWriter typeWriter = new TypeWriter(textArea);

        // First text sequence
        typeWriter.start(text1, () -> continueButton.setVisible(true));

        continueButton.addActionListener(e -> {
            continueButton.setVisible(false);

            // Second text centered
            textArea.setFont(new Font("Monospaced", Font.BOLD, 28)); // bigger for emphasis
            textArea.setLineWrap(false);
            textArea.setWrapStyleWord(false);
            textArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
            textArea.setAlignmentY(JTextArea.CENTER_ALIGNMENT);

            typeWriter.start(text2, () -> continueButton.setVisible(true));

            continueButton.addActionListener(ev -> {
                introFrame.dispose();
                afterIntro.run(); // start actual game
            });
        });
    }
   
    public void setFrame(){
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth,mapHeight)));
        JPanel hudPanel = new JPanel(null); // absolute layout for label
        hudPanel.setOpaque(false);          // so it doesn’t cover the tiles
        hudPanel.setBounds(0, 0, frameWidth, frameHeight); // overlay the whole frame

        retryLabel = new JLabel("Retries: 0");
        retryLabel.setForeground(Color.BLACK);
        retryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        retryLabel.setBounds(10, 10, 100, 20); // top-left
        hudPanel.add(retryLabel);

        timerLabel = new JLabel("Time: 2:00");
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setBounds(frameWidth - 100, 10, 100, 20); // top-right
        hudPanel.add(timerLabel);

        
        int x=0, y=0, w=1, h=1;
        for(int i=0;i<character.length;i++){
            frame.add(character[i], new Rectangle(x,y,w,h));
            x++;
            if(x%mapWidth==0){
                x=0;
                y++;
            }
        }
        
        x=0; y=0; w=1; h=1;
        for(int i=0;i<tiles.length;i++){
            frame.add(tiles[i], new Rectangle(x,y,w,h));
            x++;
            if(x%mapWidth==0){
                x=0;
                y++;
            }
        }
        frame.add(hudPanel); // add it **after** tiles and characters
        
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
        retryLabel.setForeground(Color.BLACK);
        retryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        retryLabel.setBounds(10, 10, 100, 20); // top-left

        timerLabel.setForeground(Color.BLACK);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setBounds(frameWidth - 100, 10, 100, 20); // top-right
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                int minutes = timeLeft / 60;
                int seconds = timeLeft % 60;
                timerLabel.setText(String.format("Time: %d:%02d", minutes, seconds));
                hudPanel.repaint(); // force repaint of the labels

                if (timeLeft <= 0) {
                    countdownTimer.stop();
                    retries++;
                    retryLabel.setText("Retries: " + retries);
                    resetLevel();
                    timeLeft = 120;
                    countdownTimer.start();
                }
            }
        });
        countdownTimer.start();
        JLayeredPane layeredPane = frame.getLayeredPane();
        hudPanel.setBounds(0, 0, frameWidth, frameHeight);
        layeredPane.add(hudPanel, JLayeredPane.PALETTE_LAYER); // top layer
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        startTime = System.currentTimeMillis();
        
        frame.addKeyListener(this);
    }

    public void resetLevel() {
        character[characterPosition].setIcon(null);
        for (int i = 0; i < characterPlace.length; i++) {
            if (characterPlace[i] == 1) {
                characterPosition = i;
                break;
            }
        }
        playerIcon = playerIconfront;
        character[characterPosition].setIcon(playerIcon);
        points = 0;
        attempts = 0;
        correctAnswers = 0;
        mathlock = false;
        sciencelock = false;
        historylock = false;
        trivialock = false;
        trivia2lock = false;
    }

    public boolean askMathQuestion() {
        try {
            int n = (int)(Math.random() * 2);
            String answer;
            if (n == 0) {
                answer = JOptionPane.showInputDialog(
                    frame,
                    "What is the area of a square with side length 4?",
                    "Trivia Question",
                    JOptionPane.QUESTION_MESSAGE
                );
                if(answer == null)
                    throw new Exception("No input");
                int userAnswer = Integer.parseInt(answer);
                return userAnswer == 16;
            }
            else {
                answer = JOptionPane.showInputDialog(
                    frame,
                    "What is x in this equation? 6x - 10 = 24",
                    "Trivia Question",
                    JOptionPane.QUESTION_MESSAGE
                );
                if(answer == null)
                    throw new Exception("No input");
                return answer.equals("17/3");
            }
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid input. Please enter a NUMBER.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid input. Please try again.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
    
    public boolean askScienceQuestion() {
        try {
            int n = (int)(Math.random() * 2);
            String answer;
            if (n == 0) {
                answer = JOptionPane.showInputDialog(
                    frame,
                    "What is the monomer of protein?",
                    "Trivia Question",
                    JOptionPane.QUESTION_MESSAGE
                );
            } 
            else {
                answer = JOptionPane.showInputDialog(
                    frame,
                    "What contains the DNA of the cell?",
                    "Trivia Question",
                    JOptionPane.QUESTION_MESSAGE
                );
            }
            if(answer == null || answer.trim().isEmpty())
                throw new Exception("Invalid input");
            if (n == 0)
                return answer.equalsIgnoreCase("Amino Acid");
            else
                return answer.equalsIgnoreCase("Nucleus");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid input. Please type a valid answer.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
    
    public boolean askHistoryQuestion() {
        int n = (int)(Math.random() * 2);
        String answer;

        if (n == 0) {
            answer = JOptionPane.showInputDialog(
                frame,
                "What was Java originally called in 1991?",
                "Trivia Question",
                JOptionPane.QUESTION_MESSAGE
            );
            if (answer == null) return false;
            return answer.equalsIgnoreCase("Oak");
        } 
        else {
            answer = JOptionPane.showInputDialog(
                frame,
                "Who is the founder of Java?",
                "Trivia Question",
                JOptionPane.QUESTION_MESSAGE
            );
            if (answer == null) return false;
            return answer.equalsIgnoreCase("James Gosling");
        }
    }
    
    public boolean askTriviaQuestion() {
        int n = (int)(Math.random() * 2);
        String answer;

        if (n == 0) {
            answer = JOptionPane.showInputDialog(
                frame,
                "What is the unit name of mol/L?",
                "Trivia Question",
                JOptionPane.QUESTION_MESSAGE
            );
            if (answer == null) return false;
            return answer.equalsIgnoreCase("Molarity");
        } 
        else {
            answer = JOptionPane.showInputDialog(
                frame,
                "What is the powerhouse of the cell?",
                "Trivia Question",
                JOptionPane.QUESTION_MESSAGE
            );
            if (answer == null) return false;
            return answer.equalsIgnoreCase("Mitochondria");
        }
    }
    
    public boolean askTrivia2Question() {
        int n = (int)(Math.random() * 2);
        String answer;

        if (n == 0) {
            answer = JOptionPane.showInputDialog(
                frame,
                "What do you call it when an object's mirror image is not superimposable?",
                "Trivia Question",
                JOptionPane.QUESTION_MESSAGE
            );
            if (answer == null) return false;
            return answer.equalsIgnoreCase("Chiral");
        } 
        else {
            answer = JOptionPane.showInputDialog(
                frame,
                "If a substance receives the hydrogen ion, it is the?",
                "Trivia Question",
                JOptionPane.QUESTION_MESSAGE
            );
            if (answer == null) return false;
            return answer.equalsIgnoreCase("Base");
        }
    }
    
    public void answeredQuestion(boolean correct) {
        attempts++;
        if (correct) {
            points++;
            correctAnswers++;
            JOptionPane.showMessageDialog(
                frame,
                "Correct! One point has been added.",
                "Correct",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                frame,
                "Wrong answer!",
                "Incorrect",
                JOptionPane.ERROR_MESSAGE
            );
            character[characterPosition].setIcon(null);
            if (characterPosition % mapWidth !=0) {
                characterPosition -= 1;
            }
            character[characterPosition].setIcon(playerIcon);
        }
    }
    
    public void moveCharacter(int newPosition) {
        character[characterPosition].setIcon(null);
        characterPosition = newPosition;
        character[characterPosition].setIcon(playerIcon);
        if (mapLayout[characterPosition] == 5 && !mathlock) {
            boolean correct = askMathQuestion();
            answeredQuestion(correct);
            mathlock = true;
        }

        if (mapLayout[characterPosition] == 3 && !sciencelock) {
            boolean correct = askScienceQuestion();
            answeredQuestion(correct);
            sciencelock = true;
        }
        if (mapLayout[characterPosition] == 4 && !historylock) {
            boolean correct = askHistoryQuestion();
            answeredQuestion(correct);
            historylock = true;
        }
        if (mapLayout[characterPosition] == 8 && !trivialock) {
            boolean correct = askTriviaQuestion();
            answeredQuestion(correct);
            trivialock = true;
        }
        if (mapLayout[characterPosition] == 9 && !trivia2lock) {
            boolean correct = askTrivia2Question();
            answeredQuestion(correct);
            trivia2lock = true;
        }
        if (mapLayout[characterPosition] == 7) {
            if (attempts < 5) {
                JOptionPane.showMessageDialog(
                    frame,
                    "You currently have " + points + " points.",
                    "Current Score",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            if (correctAnswers >= 3) {
                JOptionPane.showMessageDialog(
                    frame,
                    "You passed! You got " + (correctAnswers*100/5) + "%!",
                    "Level Complete",
                    JOptionPane.INFORMATION_MESSAGE
                );

                long finishTime = System.currentTimeMillis() - startTime;
                saveFastestTime(finishTime);
                saveScore(correctAnswers); // saves the score to score.txt
                frame.dispose();
                PD7p2 level2 = new PD7p2(finishTime, attempts, correctAnswers*100/5);
                level2.showLevel2Intro(finishTime, attempts, correctAnswers*100/5, () -> {
                    level2.setFrame(); // start level 2 after intro
                });
            } else {
                JOptionPane.showMessageDialog(
                    frame,
                    "You failed! You only got " + (correctAnswers*100/5) + "%!",
                    "Level Failed",
                    JOptionPane.ERROR_MESSAGE
                );
                resetLevel();
            }
        }
    }

    public void saveFastestTime(long timeMillis) {
        try {
            File file = new File("fastestTime.txt");
            long bestTime = Long.MAX_VALUE;

            if(file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                if(line != null && !line.isEmpty()) {
                    bestTime = Long.parseLong(line);
                }
                br.close();
            }

            if(timeMillis < bestTime) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(String.valueOf(timeMillis));
                bw.close();
                JOptionPane.showMessageDialog(frame, 
                    "New record! Fastest time: " + timeMillis/1000.0 + " seconds.", 
                    "Fastest Time", 
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch(IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error saving fastest time.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Save points/score
    public void saveScore(int points) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("score.txt", true)); // append mode
            bw.write("Level 1 score: " + points + "\n");
            bw.close();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving score.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            int newPosition = characterPosition;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (characterPosition % mapWidth != mapWidth - 1) {
                    newPosition = characterPosition + 1;
                    playerIcon = playerIconright;
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (characterPosition % mapWidth != 0) {
                    newPosition = characterPosition - 1;
                    playerIcon = playerIconleft;
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (characterPosition + mapWidth < mapWidth * mapHeight) {
                    newPosition = characterPosition + mapWidth;
                    playerIcon = playerIconfront;
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (characterPosition - mapWidth >= 0) {
                    newPosition = characterPosition - mapWidth;
                    playerIcon = playerIconback;
                }
            }
            else {
                throw new IllegalArgumentException("Invalid key");
            }
            if (mapLayout[newPosition] == 6 || mapLayout[newPosition] == 2 
                    || (mapLayout[newPosition] == 5 && mathlock) 
                    || (mapLayout[newPosition] == 3 && sciencelock) 
                    || (mapLayout[newPosition] == 4 && historylock)
                    || (mapLayout[newPosition] == 8 && trivialock)
                    || (mapLayout[newPosition] == 9 && trivia2lock)) {
                return;
            }
            if (newPosition != characterPosition) {
                moveCharacter(newPosition);
            }
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid key. Use ARROW KEYS to move.",
                "Control Error",
                JOptionPane.WARNING_MESSAGE
            );
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(
                frame,
                "An unexpected error occurred.",
                "Game Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    public static void main(String[] args) {
        PD7p1 game = new PD7p1();
        game.showIntroScreen(() -> game.setFrame());
    }
}
