package Main;

import javax.swing.*;

import AI.PathFinder;
import Entities.Entity;
import Entities.Player;
//import Objects.SuperObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Tiles.TileManager;

public class MainPanel extends JPanel implements Runnable {
    final int OGTILESIZE = 16; // 16X16 base for all tiles
    final int SCALE = 3;
    public final int TILESIZE = OGTILESIZE * SCALE; // 48X48

    // screen size
    public final int MAXSCREENCOLUMNS = 20;
    public final int MAXSCREENROWS = 15;
    public final int SCREENWIDTH = TILESIZE * MAXSCREENCOLUMNS; 
    public final int SCREENHEIGHT = TILESIZE * MAXSCREENROWS;

    // world settings
    public final int MAXWORLDCOLUMNS = 100;
    public final int MAXWORLDROWS = 100;
    public final int WORLDWIDTH = MAXWORLDCOLUMNS * TILESIZE;
    public final int WORLDHEIGHT = MAXWORLDROWS * TILESIZE;
    public final int MAXMAP = 5;
    public int currentMap = 0;

    // FPS
    int FPS = 60;

    // initializing all game elements
    public TileManager tileMan = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker collChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Sound sound = new Sound();
    Sound music = new Sound();
    public PathFinder pFinder = new PathFinder(this);
    public CutSceneManager csManager = new CutSceneManager(this);

    Thread gameThread;

    // set entities and objects
    public Player player = new Player(this, keyH);
    public Entity npc[] = new Entity[18];
    public Entity obj[] = new Entity[5];
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    public final int TITLESTATE = 0;
    public final int PLAYSTATE = 1;
    public final int PAUSESTATE = 2;
    public final int DIALOGUESTATE = 3;
    public final int CUTSCENESTATE = 4;
    public final int OPTIONSSTATE = 5;

    public MainPanel() {
        this.setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        
        playMusic(0);

        aSetter.setObject();
        aSetter.setNPC();
        gameState = TITLESTATE;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

            double drawInterval = 1000000000/FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;

        
        while(gameThread != null) {
            // Setting 'timer' for game
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
            
        }
    }

    public void update() {
        if (gameState == PLAYSTATE || gameState == CUTSCENESTATE) {
            player.update();
            // update NPCs
            for (int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
            // update objects
            for (int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    obj[i].update();
                }
            }
        }
        if (gameState == PAUSESTATE) {
            // nothing for now
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // debugging!
        long drawStart = 0;
        if(keyH.showDebugs) {
            drawStart = System.nanoTime();
        }
        
        // Title Screen
        if (gameState == TITLESTATE) {
            ui.draw(g2);
        } else {
            tileMan.draw(g2);
            tileMan.draw(g2); // can I fix this???
            
            // Drawing all Entities
            entityList.add(player);
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            Collections.sort(entityList, new Comparator<Entity>() {
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            for(int i = 0; i < entityList.size(); i++) {
                entityList.remove(i);
            }

            csManager.draw(g2);
            
            ui.draw(g2);
        }

        // more debugging :)
        if (keyH.showDebugs) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX: " + player.worldX, x, y);
            y+= lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y);
            y+= lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x) / TILESIZE, x, y);
            y+= lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y) / TILESIZE, x, y);
            y+= lineHeight;

            g2.drawString("Draw Time: " + passed, x, y);
        }
        
        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}