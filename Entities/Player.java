package Entities;

import Main.MainPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

import Main.KeyHandler;

public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX, screenY;
    public boolean gottenQuest = false;
    public int totalAllies = 0;

    public Player(MainPanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = (gp.SCREENWIDTH / 2) - (gp.TILESIZE / 2);
        screenY = (gp.SCREENHEIGHT / 2) - (gp.TILESIZE / 2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setStartingValues();
        getPlayerImage();
    }

    public void setStartingValues() {
        worldX = gp.TILESIZE * 82;
        worldY = gp.TILESIZE * 53;
        speed = 7; // normal: 5
        direction ="down";
    }

    public void getPlayerImage() {

        up1 = setup("/Images\\player\\slimeup1");
        up2 = setup("/Images\\player\\slimeup2");
        down1 = setup ("/Images\\player\\slimedown1");
        down2 = setup("/Images\\player\\slimedown2");
        left1 = setup("/Images\\player\\slimeleft1");
        left2 = setup("/Images\\player\\slimeleft2");
        right1 = setup("/Images\\player\\slimeright1");
        right2 = setup("/Images\\player\\slimeright2");
    }

    public void update() {

        if(totalAllies >= 5 && worldY <= (32 * gp.TILESIZE) && (worldX > (43 * gp.TILESIZE) || worldX < (66 * gp.TILESIZE))) { 
            speed = 0;
            gp.gameState = gp.CUTSCENESTATE;
            gp.csManager.sceneNum = gp.csManager.finalScene;
        }

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }
    
            // check collisions
            collisionOn = false;
            gp.collChecker.checkTile(this);
            int npcIndex = gp.collChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            
            if(collisionOn == false) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
        }
        // update sprite images
        spriteCounter++;
        if (spriteCounter > 25) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if (keyH.enterPressed) {
            // check collisions
            collisionOn = false;
            gp.collChecker.checkTile(this);
            int npcIndex = gp.collChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
        }
    }

    public void interactWithObject(int index) { // NOTE TO SELF: this is where to input more objects if/when added
        if( index != -1) {
            
        }
    }

    public void interactNPC(int index) {
        if( index != -1) {
            if(keyH.enterPressed == true) {
                gp.npc[index].speak();
            }
        }
        keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }

    public void moveRight() {
        if(worldX < (worldX + gp.TILESIZE)){
            direction = "right";
            worldX += 2;
        }
    }
}
