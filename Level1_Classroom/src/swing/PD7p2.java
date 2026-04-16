package swing;
//Other members: Maia Adelle Soyao & Zionne Kay Babia
//This code was AI assisted with corrections in game logic
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GameCharacter {
    private int position;
    private int points;
    
    public GameCharacter(int startPosition) {
        this.position = startPosition;
        this.points = 0;
    }
    public int getPosition() { return position; }
    public void setPosition(int pos) { this.position = pos; }
    public int getPoints() { return points; }
    public void addPoints() { points++; }
    public void addPoints(int value) { points += value; }
    
    public void reset() {
        points = 0;
    }
}

class PlayerCharacter extends GameCharacter {
    public PlayerCharacter(int startPosition) {
        super(startPosition);
    }
    @Override
    public void reset() {
        super.reset();
    }
}

public class PD7p2 implements KeyListener{
    JFrame frame;
    ImageIcon[] tileImages = new ImageIcon[104];
    ImageIcon playerIcon;
    ImageIcon playerIconfront;
    ImageIcon playerIconfront2;
    ImageIcon playerIconback;
    ImageIcon playerIconback2;
    ImageIcon playerIconleft;
    ImageIcon playerIconright;
    ImageIcon shieldIcon;
    JLabel tiles[];
    JLabel character[];
    JLabel shieldLabel;
    JLabel timerLabel;
    JLabel retryLabel;
    int mapLayout[];
    int onelocation;
    int twolocation;
    int threelocation;
    int fourlocation;
    int fivelocation;
    int boardlocation;
    int s5location;
    int characterPlace[];
    int characterPosition;
    int mapWidth = 13;
    int mapHeight = 8;
    int points = 0;
    int attempts = 0;
    int correctAnswers = 0;
    int tileSize = 90;
    int frameWidth = tileSize * mapWidth;
    int frameHeight = tileSize * mapHeight;
    int characterMode = 1;
    int cols = 13; 
    int prevAttempts;
    int prevScorePercent;
    int score; 
    int timeLeft = 120; // 2 minutes
    int retries = 0;
    private int startPosition;
    private PlayerCharacter player;
    boolean q4Answered = false;
    boolean q5Answered = false;
    boolean q8Answered = false;
    boolean introShown = false;
    boolean hasShield = false; 
    long startTime;
    Timer countdownTimer;
    long finishTime;     // For storing the finish time
    long prevTime;
    
    
    String currentDirection = "down";
    /*OBJECTIVE
            Move the character using the arrow keys.
            There are questions on the tables around the map.
            Some tables have trivia questions, once done, go to the vending machine to get the key
            You can check your score on the teacher's table.
            Each question can only be answered once.
            To pass the level:
            - Find all questions
            - Get perfect score
            - Go to the vending machine to get key
            - Escape 
    */
    
    public void setFinishTime(long finishTime) { this.finishTime = finishTime; }
    public void setAttempts(int attempts) { this.attempts = attempts; }
    public void setScore(int score) { this.score = score; }
    public PD7p2(long finishTime, int prevAttempts, int prevScorePercent){
        this.finishTime = finishTime;
        this.attempts = 0;
        this.score = prevScorePercent;
        frame=new JFrame();
        startTime = System.currentTimeMillis();
        characterPosition=-1;
        startPosition = characterPosition;
        for (int i = 0; i < tileImages.length; i++) {
            // Calculate the file name
            // Note: your files seem to go from 1.png to 104.png
            int fileNumber = i + 1;
            String filePath = "Images/map/" + fileNumber + ".png";

            // Load and scale
            tileImages[i] = new ImageIcon(
                new ImageIcon(filePath).getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH)
            );
        }
        playerIconfront = playerIcon = new ImageIcon(new ImageIcon("Images/player1.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconfront2 = new ImageIcon(new ImageIcon("Images/player2.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconback  = new ImageIcon(new ImageIcon("Images/player3.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconback2 = new ImageIcon(new ImageIcon("Images/player4.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconright = new ImageIcon(new ImageIcon("Images/player5.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        playerIconleft  = new ImageIcon(new ImageIcon("Images/player6.png").getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH));
        character=new JLabel[mapWidth*mapHeight];
        int shieldSize = tileSize / 2;
        shieldIcon = new ImageIcon(new ImageIcon("Images/shield.png").getImage().getScaledInstance(shieldSize, shieldSize, Image.SCALE_SMOOTH));
        shieldLabel = new JLabel(shieldIcon);
        shieldLabel.setSize(shieldSize, shieldSize);
        
        characterPlace = new int[]{
            1,1,1,1,1,1,1,1,1,1,1,1,1,
            1,3,1,1,1,1,1,1,0,0,0,0,1,
            1,0,0,0,8,0,0,1,0,0,0,0,1,
            1,0,4,0,1,5,0,1,0,0,0,0,1,
            1,0,0,0,0,0,0,1,0,0,0,0,1,
            1,6,1,1,0,1,1,1,1,1,1,7,1,
            1,2,0,0,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1,1,1,1
        };
        
        for(int i=0;i<character.length;i++){
            if(characterPlace[i]==2){
                character[i]=new JLabel(playerIcon);
                characterPosition = i;
                startPosition = i;
                player = new PlayerCharacter(i);
            }
            else character[i]=new JLabel();
        }
       
        tiles = new JLabel[tileImages.length];
            for (int i = 0; i < tileImages.length; i++) {
                tiles[i] = new JLabel(tileImages[i]);
            }
    }
    
    private void startTimer() {
        if(countdownTimer != null && countdownTimer.isRunning())
            countdownTimer.stop();

        timeLeft = 120; // reset time each restart
        timerLabel.setText("Time: 2:00");

        countdownTimer = new Timer(1000, e -> {
            timeLeft--;
            int minutes = timeLeft / 60;
            int seconds = timeLeft % 60;
            timerLabel.setText(String.format("Time: %d:%02d", minutes, seconds));

            if(timeLeft <= 0){
                countdownTimer.stop();
                retries++;
                retryLabel.setText("Retries: " + retries);
                restartGame(); // reset the level
                startTimer();  // restart the timer
            }
        });
        countdownTimer.start();
    }
    
    void showLevel2Intro(long prevTimeMillis, int prevAttempts, int prevScorePercent, Runnable afterIntro) {
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

        String introText = "LEVEL 2\n\n"
                         + "Last run stats:\n"
                         + "- Time taken: " + (prevTimeMillis/1000.0) + " seconds\n"
                         + "- Number of attempts: " + prevAttempts + "\n"
                         + "- Score: " + prevScorePercent + "%\n\n"
                         + "Looks like someone’s ready for a tougher challenge... 😉\n"
                         + "Prepare yourself!\n\n\n\n\n\n\n\n\n\n\n\n"
                         + "Find the board.";

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

        typeWriter.start(introText, () -> continueButton.setVisible(true));

        continueButton.addActionListener(e -> {
            introFrame.dispose();
            afterIntro.run(); // start the actual level
        });
    }
    
    void showExitText(Runnable afterText) {
        JFrame exitFrame = new JFrame();
        exitFrame.setSize(frameWidth, frameHeight);
        exitFrame.setUndecorated(true);
        exitFrame.setLocationRelativeTo(null);

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

        exitFrame.add(panel);
        exitFrame.setVisible(true);

        String exitText = "CONGRATS, I GUESS...\n\n"
                        + "See you soon 😉";

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
        typeWriter.start(exitText, () -> continueButton.setVisible(true));

        continueButton.addActionListener(e -> {
            exitFrame.dispose();
            afterText.run(); // close the game after the player clicks
        });
    }
   
    public void setFrame() {
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth, mapHeight)));

        // === HUD PANEL ===
        JPanel hudPanel = new JPanel(null); // absolute positioning
        hudPanel.setOpaque(false);
        hudPanel.setBounds(0, 0, frameWidth, frameHeight);

        retryLabel = new JLabel("Retries: " + retries);
        retryLabel.setForeground(Color.BLACK);
        retryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        retryLabel.setBounds(10, 10, 120, 25); // top-left
        hudPanel.add(retryLabel);

        timerLabel = new JLabel("Time: 2:00");
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setBounds(frameWidth - 120, 10, 120, 25); // top-right
        hudPanel.add(timerLabel);

        // === ADD CHARACTERS ===
        int x = 0, y = 0, w = 1, h = 1;
        for (int i = 0; i < character.length; i++) {
            frame.add(character[i], new Rectangle(x, y, w, h));
            x++;
            if (x % mapWidth == 0) {
                x = 0;
                y++;
            }
        }

        // === ADD TILES ===
        x = 0; y = 0;
        for (int i = 0; i < tiles.length; i++) {
            frame.add(tiles[i], new Rectangle(x, y, w, h));
            x++;
            if (x % mapWidth == 0) {
                x = 0;
                y++;
            }
        }

        // === ADD HUD ON TOP ===
        frame.getLayeredPane().add(hudPanel, JLayeredPane.PALETTE_LAYER);

        frame.getLayeredPane().add(shieldLabel, JLayeredPane.DRAG_LAYER);
        shieldLabel.setVisible(false); // hidden at start
        // === FRAME SETTINGS ===
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
        frame.setFocusable(true);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // === START TIMER ===
        startTimer();
    }
    
    
    public void saveFastestTime(long timeMillis) {
        try {
            File file = new File("fastestTime2.txt"); // separate file for level 2
            long bestTime = Long.MAX_VALUE;

            if(file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                if(line != null && !line.isEmpty()) bestTime = Long.parseLong(line);
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

    public void saveScore(int points) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("score.txt", true)); // append
            bw.write("Level 2 score: " + points + "\n");
            bw.close();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving score.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showFailScreen(int percentage){
        int choice = JOptionPane.showOptionDialog(
            frame,
            "You failed with " + percentage + "%.\nRestart?",
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            new String[]{"Restart","Exit"},
            "Restart"
        );
        
        if(choice == 0){
            retries++;
            retryLabel.setText("Retries: " + retries);
            restartGame();
        } else {
            System.exit(0);
        }
    }
    
    public boolean askQuestion4() {
        while (true) {
            String answer = null;
            try {
                answer = JOptionPane.showInputDialog(
                    frame,
                    "Question 1:\nWhat is (5 + 7)*8?",
                    "Question",
                    JOptionPane.QUESTION_MESSAGE
                );
                if (answer == null) {
                    throw new Exception("No input");
                }
                int userAnswer = Integer.parseInt(answer);
                return userAnswer == 96;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Invalid input. Please enter a NUMBER.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    frame,
                    "You must enter an answer!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } finally {
                System.out.println("Question 1 attempt processed.");
            }
        }
    }
    
    public boolean askQuestion5() {
        while (true) {
            String answer = null;
            try {
                answer = JOptionPane.showInputDialog(
                    frame,
                    "Question 2:\nWhat is the capital of China?",
                    "Question",
                    JOptionPane.QUESTION_MESSAGE
                );
                if (answer == null || answer.trim().isEmpty()) {
                    throw new Exception("Empty input");
                }
                return answer.equalsIgnoreCase("Beijing");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Please enter a valid answer!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } finally {
                System.out.println("Question 2 attempt processed.");
            }
        }
    }
    
    public boolean askQuestion8() {
        while (true) {
            String answer = null;
            try {
                answer = JOptionPane.showInputDialog(
                    frame,
                    "Question 3:\nWhat planet was taken off the original 9 planets?",
                    "Question",
                    JOptionPane.QUESTION_MESSAGE
                );
                if (answer == null || answer.trim().isEmpty()) {
                    throw new Exception("Empty input");
                }
                return answer.equalsIgnoreCase("Pluto");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Please enter a valid answer!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } finally {
                System.out.println("Question 3 attempt processed.");
            }
        }
    }
    
    public void processAnswer(boolean correct){
        attempts++;
        if(correct){
            correctAnswers++;
            player.addPoints();
            JOptionPane.showMessageDialog(
                frame,
                "Correct! Your current score: " + player.getPoints(),
                "Correct",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                frame,
                "Incorrect! Try another question.",
                "Incorrect",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void moveCharacter(int newPosition) {
        character[characterPosition].setIcon(null);
        characterPosition = newPosition;
        player.setPosition(newPosition);
        character[newPosition].setIcon(playerIcon);
        JLayeredPane layeredPane = frame.getLayeredPane();

        if(currentDirection.equals("up")){
            // put shield JUST BELOW player but ABOVE tiles
            layeredPane.setLayer(shieldLabel, JLayeredPane.MODAL_LAYER);
        } else {
            // keep shield above player for other directions
            layeredPane.setLayer(shieldLabel, JLayeredPane.DRAG_LAYER);
        }

        if(hasShield){
            int baseX = (characterPosition % mapWidth) * tileSize;
            int baseY = (characterPosition / mapWidth) * tileSize;

            int offsetX = 0;
            int offsetY = 0;

            int shieldSize = tileSize / 2;

            switch(currentDirection){
                case "right":
                    offsetX = tileSize - shieldSize - 5; // closer to player
                    offsetY = tileSize / 4 - 15;
                    break;
                case "left":
                    offsetX = (-shieldSize / 2) + 5; // slightly tucked in
                    offsetY = tileSize / 4 - 15;
                    break;
                case "up":
                    offsetX = tileSize / 4 + 15;
                    offsetY = (-shieldSize / 2) + 28;
                    break;
                case "down":
                    offsetX = tileSize / 4;
                    offsetY = tileSize - shieldSize - 28; // pushed UP
                    break;
            }

            shieldLabel.setBounds(
                baseX + offsetX,
                baseY + offsetY,
                shieldSize,
                shieldSize
            );
        }
    }
    
    public void handleInteraction(int tile) {
        switch(tile) {
            case 3:
                if(!introShown){
                    JOptionPane.showMessageDialog(frame,
                        "OBJECTIVE:\n\n"
                      + "1. Answer all 3 questions. (Hint: It's all around the map)\n"
                      + "2. Get a perfect score.\n"
                      + "3. Get the key. (Hint: vending machine)\n"
                      + "4. Reach the gate.");
                    introShown = true;
                }
                break;

            case 4:
                if(!q4Answered){
                    boolean correct = askQuestion4();
                    processAnswer(correct);
                    q4Answered = true;
                }
                break;

            case 5:
                if(!q5Answered){
                    boolean correct = askQuestion5();
                    processAnswer(correct);
                    q5Answered = true;
                }
                break;

            case 8:
                if(!q8Answered){
                    boolean correct = askQuestion8();
                    processAnswer(correct);
                    q8Answered = true;
                }
                break;

            case 6:
                if(!(q4Answered && q5Answered && q8Answered)){
                    JOptionPane.showMessageDialog(frame,
                        "Answer all 3 questions first!");
                    return;
                }

                int percentage = (correctAnswers * 100) / 3;

                if(percentage == 100){
                    hasShield = true;

                    JLabel message = new JLabel("You obtained the SHIELD!", shieldIcon, JLabel.CENTER);
                    message.setHorizontalTextPosition(JLabel.CENTER);
                    message.setVerticalTextPosition(JLabel.BOTTOM);

                    JOptionPane.showMessageDialog(
                        frame,
                        message,
                        "Item Obtained",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    shieldLabel.setVisible(true);
                    moveCharacter(characterPosition);

                } else {
                    showFailScreen(percentage); // ✅ this now triggers properly
                }
                break;

            case 7:
                if(hasShield){
                    countdownTimer.stop(); // stop the timer
                    int finalScore = (correctAnswers * 100) / 3;
                    long finishTime = System.currentTimeMillis() - startTime;

                    // Show results first
                    JOptionPane.showMessageDialog(frame,
                        "LEVEL COMPLETE!\nFinal Score: " + finalScore + "%"
                        + "\nTime: " + (finishTime / 1000.0) + " seconds"
                        + "\nRetries: " + retries,
                        "Level Complete",
                        JOptionPane.INFORMATION_MESSAGE);

                    saveFastestTime(finishTime);
                    saveScore(correctAnswers); 

                    // === SHOW EXIT TEXT ===
                    showExitText(() -> System.exit(0)); // run System.exit(0) after text
                } else {
                    JOptionPane.showMessageDialog(frame,
                        "The gate is locked.\nGet the shield first!");
                }
                break;
        }
    }
    
    private void restartGame(){
        correctAnswers = 0;
        // attempts stays the same -> do NOT reset attempts
        hasShield = false;
        shieldLabel.setVisible(false);

        q4Answered = false;
        q5Answered = false;
        q8Answered = false;

        player.reset();

        character[characterPosition].setIcon(null);
        characterPosition = startPosition;
        character[characterPosition].setIcon(playerIconfront);

        // reset timer
        timeLeft = 120;
        timerLabel.setText("Time: 2:00");
        
        retryLabel.setText("Retries: " + retries);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            int newPosition = characterPosition;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (characterPosition % mapWidth != mapWidth - 1)
                    newPosition = characterPosition + 1;
                playerIcon = playerIconright;
                currentDirection = "right";
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (characterPosition % mapWidth != 0)
                    newPosition = characterPosition - 1;
                playerIcon = playerIconleft;
                currentDirection = "left";
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (characterPosition + mapWidth < mapWidth * mapHeight)
                    newPosition = characterPosition + mapWidth;
                playerIcon = playerIconfront;
                currentDirection = "down";
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (characterPosition - mapWidth >= 0)
                    newPosition = characterPosition - mapWidth;
                playerIcon = playerIconback;
                currentDirection = "up";
            }
            else {
                throw new IllegalArgumentException("Invalid key");
            }
            if (newPosition < 0 || newPosition >= characterPlace.length)
                return;
            int tile = characterPlace[newPosition];
            if (tile == 1)
                return;
            if (tile == 6) {
                // only allow interaction if coming from tile 2
                if (characterPlace[characterPosition] != 2) {
                    return; // block completely
                }
                handleInteraction(tile);
                return;
            }
            if (tile >= 3 && tile <= 8) {
                handleInteraction(tile);
                return;
            }
            moveCharacter(newPosition);
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid key. Use ARROW KEYS to move.",
                "Control Error",
                JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                frame,
                "An unexpected error occurred. Please try again.",
                "Game Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
}
