package cs4qt3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.nio.file.*;
public class cs4game_physicslab implements KeyListener{
    private ImageIcon loadAndScale(String path, int width, int height){
    ImageIcon icon = new ImageIcon(path);
    Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(scaled);
    }
    Map<String, ImageIcon> playerSprites = new HashMap<>();
    JFrame frame;
    ImageIcon label;
    ImageIcon floortile;
    ImageIcon stool;
    ImageIcon beigewall;
    ImageIcon bluewall;
    ImageIcon locker;
    ImageIcon board1;
    ImageIcon board2;
    ImageIcon board3;
    ImageIcon table1;
    ImageIcon table2;
    ImageIcon table3;
    ImageIcon door;
    ImageIcon window;
    ImageIcon rbox;
    ImageIcon player;
    ImageIcon leftdown;
    ImageIcon rightdown;
    ImageIcon leftup;
    ImageIcon rightup;
    ImageIcon up;
    ImageIcon frontleft;
    ImageIcon backleft;
    ImageIcon frontright;
    ImageIcon backright;
    JLabel character[];
    int characterplace[];
    int walkstateu = 0;
    int walkstater = 0;
    int walkstatel = 0;
    int walkstated = 0;
    int action = 0;
    JLabel playerMap[];
    int playerStarting[];
    int playerPos;
    JLabel tiles[];
    int mapLayout[];
    int mapWidth=12;
    int mapHeight=12;
    int frameWidth=1000;
    int frameHeight=1000;
    boolean[] enemyTiles;
    enum direction {
    up, down, left, right
    }
    direction lastdirection = direction.down;
    Timer idleTimer;
    int completion = 0;
    boolean openLockerRevealed = false;
    static final int OPEN_LOCKER_POS = 15;
    cs4backroom backroomRef = null;
    private String playerType = "boy";
    private void loadPlayerType() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("substitute.txt"));
            String line = br.readLine();
            if (line != null) {
                playerType = line.trim().toLowerCase();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("substitute.txt not found, defaulting to boy");
            playerType = "boy";
        }
    }
    private boolean collision(int targetPos) {
        if (targetPos < 0 || targetPos >= mapLayout.length) return false;
        int tile = mapLayout[targetPos];
        boolean walkableTile = tile == 1 || tile == 2 || tile == 12;
        boolean blockedByEnemy = enemyTiles[targetPos];
        return walkableTile && !blockedByEnemy;
    }
    private int facing() {
        int row = playerPos / mapWidth;
        int col = playerPos % mapWidth;
        switch (lastdirection) {
            case up:
                if (row > 0) return playerPos - mapWidth;
                break;
            case down:
                if (row < mapHeight - 1) return playerPos + mapWidth;
                break;
            case left:
                if (col > 0) return playerPos - 1;
                break;
            case right:
                if (col < mapWidth - 1) return playerPos + 1;
                break;
        }
        return -1; 
    }
    static final int BOX_LEFT   = 111;
    static final int BOX_CENTER = 114;
    static final int BOX_RIGHT  = 117;
    static final String[] Q_VECTORS = {
        "Vectors have both magnitude and ___?",
        "Which of these is a vector quantity?",
        "What does the arrow on a vector represent?",
        "Which operation gives you the resultant of two vectors?",
        "A vector with magnitude 0 is called a ___?"
    };
    static final String[][] OPT_VECTORS = {
        {"Direction", "Speed", "Color", "Volume"},
        {"Temperature", "Mass", "Velocity", "Time"},
        {"Speed only", "Direction only", "Both direction and magnitude", "Color"},
        {"Subtraction", "Division", "Addition", "Multiplication"},
        {"Null vector", "Unit vector", "Position vector", "Scalar"}
    };
    static final int[] ANS_VECTORS = {0, 2, 2, 2, 0};
    static final String[] Q_NEWTON = {
        "Force equals mass times ___?",
        "Newton's 1st Law is also called the Law of ___?",
        "What is the SI unit of force?",
        "Newton's 3rd Law states every action has an equal and ___ reaction?",
        "Which law explains why you feel pushed back in a car that accelerates?"
    };
    static final String[][] OPT_NEWTON = {
        {"Velocity", "Acceleration", "Mass", "Weight"},
        {"Inertia", "Gravity", "Motion", "Energy"},
        {"Joule", "Watt", "Newton", "Pascal"},
        {"Opposite", "Greater", "Smaller", "Parallel"},
        {"Newton's 1st Law", "Newton's 2nd Law", "Newton's 3rd Law", "Law of Gravity"}
    };
    static final int[] ANS_NEWTON = {1, 0, 2, 0, 0};
    static final String[] Q_GRAVITY = {
        "What is described as an invisible force of attraction between any two objects with mass?",
        "Who first described gravity mathematically?",
        "On which planet would you weigh the most?",
        "What is the approximate gravitational acceleration on Earth?",
        "Gravity is a _____ force — it only attracts, never repels."
    };
    static final String[][] OPT_GRAVITY = {
        {"Tension", "Pulling", "Magnetic", "Gravity"},
        {"Einstein", "Newton", "Galileo", "Tesla"},
        {"Mars", "Mercury", "Jupiter", "Saturn"},
        {"5 m/s²", "9.8 m/s²", "15 m/s²", "1 m/s²"},
        {"Repulsive", "Attractive", "Neutral", "Electric"}
    };
    static final int[] ANS_GRAVITY = {3, 1, 2, 1, 1};
    public cs4game_physicslab(){
        loadPlayerType();
        frame=new JFrame();
        enemyTiles = new boolean[mapWidth * mapHeight];
        String[] tileNames = {
            "floor.jpg", "stoolonfloor.jpg", "beigewalls.JPG",
            "bluewalls.jpg", "locker.png", "board1.jpg",
            "board2.jpg", "board3.jpg", "table1.jpg",
            "table2.jpg", "table3.jpg", "door.jpg", "window.jpeg"
        };
        ImageIcon[] tileIcons = new ImageIcon[tileNames.length];
        for(int i=0;i<tileNames.length;i++){
            tileIcons[i] = loadAndScale("physicslabtiles/" + tileNames[i], frameWidth/mapWidth, frameHeight/mapHeight);
        }
        floortile = tileIcons[0];
        stool = tileIcons[1];
        beigewall = tileIcons[2];
        bluewall = tileIcons[3];
        locker = tileIcons[4];
        board1 = tileIcons[5];
        board2 = tileIcons[6];
        board3 = tileIcons[7];
        table1 = tileIcons[8];
        table2 = tileIcons[9];
        rbox = tileIcons[9];
        table3 = tileIcons[10];
        door = tileIcons[11];
        window = tileIcons[12];
        playerSprites = new HashMap<>();
        String prefix = playerType.equals("girl") ? "girl" : "boy";
        String[] spriteNums = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for (String num : spriteNums) {
            String key = "girl" + num; // internal key stays the same for compatibility
            String file = prefix + num;
            playerSprites.put(key, loadAndScale("physicslabtiles/" + file + ".png", (int)(frameWidth/mapWidth * 0.8), (int)(frameHeight/mapHeight * 0.8)));
        }
        playerSprites.put("enemy", loadAndScale("physicslabtiles/enemy.png", 1300/mapWidth, 2000/mapHeight));
        playerMap = new JLabel[mapHeight*mapWidth];
        playerStarting = new int[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        };
        for(int x = 0; x < playerMap.length; x++){
            switch(playerStarting[x]){
                case 0 -> playerMap[x] = new JLabel();
                case 1 -> {
                    playerMap[x] = new JLabel(playerSprites.get("girl01"));
                    playerPos = x;
                }
            }
        }
        tiles=new JLabel[mapWidth*mapHeight];
        mapLayout=new int[]{
            3,4,12,4,4,4,4,4,4,12,4,3,
            4,1,1,5,5,5,5,5,5,1,1,4,
            4,1,1,1,1,1,1,1,1,1,1,4,
            4,1,1,1,1,1,1,1,1,1,1,4,
            4,6,2,11,2,2,11,2,2,11,2,4,
            4,7,2,9,2,2,9,2,2,9,2,4,
            4,7,2,9,2,2,9,2,2,9,2,4,
            4,7,2,9,2,2,9,2,2,9,2,4,
            4,7,2,9,2,2,9,2,2,9,2,4,
            4,8,2,14,2,2,14,2,2,14,2,4,
            4,1,1,1,1,1,1,1,1,1,1,4,
            3,4,13,13,4,13,13,4,13,13,4,3,
        };
        for(int i=0;i<tiles.length;i++){
            switch(mapLayout[i]){
                case 1: tiles[i]=new JLabel(floortile); break;
                case 2: tiles[i]=new JLabel(stool); break;
                case 3: tiles[i]=new JLabel(beigewall); break;
                case 4: tiles[i]=new JLabel(bluewall); break;
                case 5: tiles[i]=new JLabel(locker); break;
                case 6: tiles[i]=new JLabel(board1); break;
                case 7: tiles[i]=new JLabel(board2); break;
                case 8: tiles[i]=new JLabel(board3); break;
                case 9: tiles[i]=new JLabel(table1); break;
                case 10: tiles[i]=new JLabel(table2); break;
                case 11: tiles[i]=new JLabel(table3); break;
                case 12: tiles[i]=new JLabel(door); break;
                case 13: tiles[i]=new JLabel(window); break;
                case 14: tiles[i]=new JLabel(rbox); break;
            }
        }
    }
    public void setFrame(){
        frame.setLayout(new GraphPaperLayout(new Dimension(mapWidth,mapHeight)));
        int x=0, y=0, w=1, h=1;
        for(int n = 0; n < playerMap.length; n++){
            frame.add(playerMap[n], new Rectangle(x, y, w, h));
            x++;
            if(x%mapWidth == 0){
                x = 0;
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
        frame.setSize(frameWidth,frameHeight);
        frame.setVisible(true);
        startMap1Timer();   
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(this);
        idleTimer = new Timer(300, e -> {
            switch (lastdirection) {
                case up    -> playerMap[playerPos].setIcon(playerSprites.get("girl02"));
                case down  -> playerMap[playerPos].setIcon(playerSprites.get("girl01"));
                case left  -> playerMap[playerPos].setIcon(playerSprites.get("girl03"));
                case right -> playerMap[playerPos].setIcon(playerSprites.get("girl04"));
            }
        });
        idleTimer.setRepeats(false);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_RIGHT ->{
                walkstateu = 0;
                walkstatel = 0;
                walkstated = 0;
                int targetPos = playerPos + 1;
                idleTimer.restart();
                if((playerPos+1)%mapWidth != 0 && collision(targetPos)){
                    lastdirection = direction.right;
                    playerMap[playerPos].setIcon(null);
                    playerPos++;
                    switch(walkstater){
                        case 0: playerMap[playerPos].setIcon(playerSprites.get("girl09")); walkstater=1; break;
                        case 1: playerMap[playerPos].setIcon(playerSprites.get("girl11")); walkstater=0; break;
                    }
                }else{
                    lastdirection = direction.right;
                    idleTimer.stop();
                    playerMap[playerPos].setIcon(playerSprites.get("girl04"));
                }
            }
            case KeyEvent.VK_LEFT ->{
                walkstateu = 0;
                walkstater = 0;
                walkstated = 0;
                int targetPos = playerPos - 1;
                idleTimer.restart();
                if((playerPos-1)%mapWidth != mapWidth-1 && playerPos-1 > -1 && collision(targetPos)){
                    lastdirection = direction.left;
                    playerMap[playerPos].setIcon(null);
                    playerPos--;
                    switch(walkstatel){
                        case 0: playerMap[playerPos].setIcon(playerSprites.get("girl10")); walkstatel=1; break;
                        case 1: playerMap[playerPos].setIcon(playerSprites.get("girl12")); walkstatel=0; break;
                    }
                }else{
                    lastdirection = direction.left;
                    idleTimer.stop();
                    playerMap[playerPos].setIcon(playerSprites.get("girl03"));
                }
            }
            case KeyEvent.VK_UP ->{
                walkstatel = 0;
                walkstater = 0;
                walkstated = 0;
                int targetPos = playerPos - mapWidth;
                idleTimer.restart();
                if(playerPos-mapWidth > -1 && collision(targetPos)){
                    lastdirection = direction.up;
                    playerMap[playerPos].setIcon(null);
                    playerPos-=mapWidth;
                    switch(walkstateu){
                        case 0: playerMap[playerPos].setIcon(playerSprites.get("girl07")); walkstateu=1; break;
                        case 1: playerMap[playerPos].setIcon(playerSprites.get("girl08")); walkstateu=0; break;
                    }
                }else{
                    lastdirection = direction.up;
                    idleTimer.stop();
                    playerMap[playerPos].setIcon(playerSprites.get("girl02"));
                }
            }
            case KeyEvent.VK_DOWN ->{
                walkstatel = 0;
                walkstater = 0;
                walkstateu = 0;
                int targetPos = playerPos + mapWidth;
                idleTimer.restart();
                if(playerPos+mapWidth < mapWidth*mapHeight && collision(targetPos)){
                    lastdirection = direction.down;
                    playerMap[playerPos].setIcon(null);
                    playerPos+=mapWidth;
                    switch(walkstated){
                        case 0: playerMap[playerPos].setIcon(playerSprites.get("girl05")); walkstated=1; break;
                        case 1: playerMap[playerPos].setIcon(playerSprites.get("girl06")); walkstated=0; break;
                    }
                }else{
                    lastdirection = direction.down;
                    idleTimer.stop();
                    playerMap[playerPos].setIcon(playerSprites.get("girl01"));
                }
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E) {
            int target = facing();
            if (target == -1) return;
            if (openLockerRevealed && target == OPEN_LOCKER_POS) {
                JOptionPane.showMessageDialog(frame, "The locker swings open... a dark passage leads downward.", "Message", JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(false);
                pauseMap1Timer();   
                if (backroomRef == null) {
                    backroomRef = new cs4backroom(this);
                    backroomRef.setFrame();
                } else {
                    backroomRef.getFrame().setVisible(true);
                }
                return;
            }
            int tile = mapLayout[target];
            if (tile == 14) {
                String hint;
                String[] questions;
                String[][] options;
                int[] answers;
                if (target == BOX_LEFT) {
                    hint      = "There seems to be a solar system model in this box...\nLooks like something to do with gravity.";
                    questions = Q_GRAVITY;
                    options   = OPT_GRAVITY;
                    answers   = ANS_GRAVITY;
                } else if (target == BOX_CENTER) {
                    hint      = "There's a Newton's cradle in here...\nMust be something about forces and Newton's Laws.";
                    questions = Q_NEWTON;
                    options   = OPT_NEWTON;
                    answers   = ANS_NEWTON;
                } else { 
                    hint      = "You find a compass and some arrows drawn on paper...\nThis one seems to be about vectors.";
                    questions = Q_VECTORS;
                    options   = OPT_VECTORS;
                    answers   = ANS_VECTORS;
                }
                JOptionPane.showMessageDialog(frame, hint, "Box Hint", JOptionPane.INFORMATION_MESSAGE);
                int choice = JOptionPane.showConfirmDialog(frame,
                    "Would you like to take this quiz?", "Take Quiz?", JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) return;
                if (action == 0) {
                    JOptionPane.showMessageDialog(frame,
                        "Something rustles in the locker behind you...\nA mysterious figure emerges. It has questions.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    action++;
                    playerMap[29].setIcon(playerSprites.get("enemy"));
                    enemyTiles[29] = true;
                }
                runQuiz(questions, options, answers);
                return;
            }
            if (enemyTiles[target] && completion == 0) {
                JOptionPane.showMessageDialog(frame,
                    "The figure stares at you. Inspect one of the boxes to choose your quiz topic.", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else if (enemyTiles[target]) {
                JOptionPane.showMessageDialog(frame, "You're done here already. Leave.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    private void runQuiz(String[] questions, String[][] options, int[] answers) {
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) order.add(i);
        while (true) {
            Collections.shuffle(order);
            int score = 0;
            for (int idx : order) {
                int answer = -1;
                boolean valid = false;
                while (!valid) {
                    try {
                        answer = JOptionPane.showOptionDialog(
                            frame,
                            questions[idx],
                            "Quiz — Question " + (order.indexOf(idx) + 1) + " of " + questions.length,
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options[idx],
                            options[idx][0]
                        );
                        if (answer == JOptionPane.CLOSED_OPTION) {
                            throw new Exception("Please choose an answer!");
                        }
                        valid = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage());
                    }
                }
                if (answer == answers[idx]) {
                    score++;
                } else {
                    JOptionPane.showMessageDialog(frame,
                        "The figure shakes its head.\n\"Incorrect! Try again!\"",
                        "Wrong Answer", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
            if (score == questions.length) {
                JOptionPane.showMessageDialog(frame,
                    "The figure nods slowly.\n\"You answered all " + questions.length + " questions correctly. You may leave the Physics Lab.\"",
                    "Quiz Passed!", JOptionPane.INFORMATION_MESSAGE);
                completion = 1;
                revealOpenLocker();
                return;
            }
        }
    }
    private void startMap1Timer() {
        cs4backroom.clearInventoryFile();
        try { java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(cs4backroom.TIMER_FILE)); }
        catch (java.io.IOException ignored) {}
        java.util.Map<String, String> data = new java.util.HashMap<>();
        data.put("map1StartMs",   String.valueOf(System.currentTimeMillis()));
        data.put("map1ElapsedMs", "0");
        data.put("map2StartMs",   "0");
        data.put("totalElapsedMs","0");
        data.put("completed",     "false");
        cs4backroom.writeTimerFile(data);
    }
    private void pauseMap1Timer() {
        java.util.Map<String, String> data = cs4backroom.readTimerFile();
        long start = 0;
        try { start = Long.parseLong(data.getOrDefault("map1StartMs", "0")); }
        catch (NumberFormatException ignored) {}
        long elapsed = (start > 0) ? (System.currentTimeMillis() - start) : 0;
        data.put("map1ElapsedMs", String.valueOf(elapsed));
        data.put("map1StartMs",   "0");   
        cs4backroom.writeTimerFile(data);
    }
    public JFrame getFrame() { return frame; }
    private void revealOpenLocker() {
        if (openLockerRevealed) return;
        openLockerRevealed = true;
        tiles[OPEN_LOCKER_POS].setIcon(door);
        mapLayout[OPEN_LOCKER_POS] = 1;
        JOptionPane.showMessageDialog(frame,
            "One of the lockers behind the mysterious figure has swung open. Investigate it.",
            "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}