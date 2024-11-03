package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.MainPanel;
import Main.UtilityTool;

import AI.*;


public class Entity {
    MainPanel gp;
    PathFinder pathFinder;
    
    public int worldX, worldY, speed = 2; // this is position relative to world map, not the screen

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "down";
    public Rectangle solidArea = new Rectangle(10, 5, 37, 37);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean onPath = false;
    public BufferedImage image;
    public String name;
    public Boolean collision = false;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Counters
    public int actionLockCounter = 0;
    public int bounceSpeed;
    public boolean talkedTo = false;
    public Random rand = new Random();

    public String dialogues[][] = new String[10][10];
    public int dialogueSet = 0;
    public int dialogueIndex = 0;

    int pathIndex = 0;

    public Entity(MainPanel gp) {
        this.gp = gp;
        pathFinder = new PathFinder(gp);
        bounceSpeed = rand.nextInt(15) + 35;
    }

    public void draw(Graphics2D g2) {
        
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        BufferedImage image = null;

        if (worldX + gp.TILESIZE * 3 > gp.player.worldX - gp.player.screenX && 
            worldX - gp.TILESIZE * 3 < gp.player.worldX + gp.player.screenX && 
            worldY + gp.TILESIZE * 3 > gp.player.worldY - gp.player.screenY && 
            worldY - gp.TILESIZE * 3 < gp.player.worldY + gp.player.screenY) {

                switch(direction) {
                    case "up":
                        image = (spriteNum == 1) ? up1: up2;
                        break;
                    case "down":
                    image = (spriteNum == 1) ? down1: down2;
                        break;
                    case "left":
                    image = (spriteNum == 1) ? left1: left2;
                        break;
                    case "right":
                    image = (spriteNum == 1) ? right1: right2;
                        break;
                }
                g2.drawImage(image, screenX, screenY, null);
            }
    }

     public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            scaledImage = uTool.scaleImage(scaledImage, gp.TILESIZE, gp.TILESIZE);

        } catch(IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    public void speak() {}
    public void defeat() {}

    public void facePlayer() {
        switch(gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }

    public void startDialogue(Entity entity, int setNum) {
        gp.ui.npc = entity;
        dialogueSet = setNum;
        gp.gameState = gp.DIALOGUESTATE;
        
    }

    public void setAction() {}

    public void checkCollision() {
        collisionOn = false;
        gp.collChecker.checkTile(this);
        gp.collChecker.checkPLayer(this);
    }

    public void update() {
        setAction();
        checkCollision();

        if (!collisionOn && onPath) {
            moveAlongPath();
        }

        // update sprite images
        spriteCounter++;
        if (spriteCounter > bounceSpeed) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.TILESIZE;
        int startRow = (worldY + solidArea.y) / gp.TILESIZE;

        pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(pathFinder.search()) {
            onPath = true;
            pathIndex = 0;
        }
    }

    public void moveAlongPath() {
        if(pathIndex < pathFinder.pathList.size()) {
            Node nextNode = pathFinder.pathList.get(pathIndex);
            int nextX = nextNode.col * gp.TILESIZE;
            int nextY = nextNode.row * gp.TILESIZE;

            direction = getDirection(nextX, nextY);

            if(!collisionOn) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // check for the next node
            if(Math.abs(worldX - nextX) < speed && Math.abs(worldY - nextY) < speed) {
                worldX = nextX;
                worldY = nextY;
                pathIndex++;
            }
            // if goal reached, stop moving
            if(pathIndex == pathFinder.pathList.size()) {
                onPath = false;
            }
        } else {
            onPath = false;
        }
    }

    private String getDirection(int nextX, int nextY) {

        if (worldY > nextY) {
            direction = "up";
        } else if (worldY < nextY) {
            direction = "down";
        } else if (worldX > nextX) {
            direction = "left";
        } else if (worldX < nextX) {
            direction = "right";
        }
        return direction;
    }
}
