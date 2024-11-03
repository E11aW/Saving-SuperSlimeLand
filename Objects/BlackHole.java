package Objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import Entities.Entity;
import Main.MainPanel;
import Main.UtilityTool;
import java.awt.image.BufferedImage;

public class BlackHole extends Entity {
    boolean defeated = false;

    public BlackHole(MainPanel gp) {
        super(gp);
        name = "BlackHole";
        collision = true;
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream("/Images\\object\\blackHole.png"));
            down1 = uTool.scaleImage(scaledImage, gp.TILESIZE * 4, gp.TILESIZE * 4);
            scaledImage = ImageIO.read(getClass().getResourceAsStream("/Images\\object\\blackHoleTurned.png"));
            left1 = uTool.scaleImage(scaledImage, gp.TILESIZE * 4, gp.TILESIZE * 4);
            scaledImage = ImageIO.read(getClass().getResourceAsStream("/Images\\object\\blackHoleTurnedMore.png"));
            up1 = uTool.scaleImage(scaledImage, gp.TILESIZE * 4, gp.TILESIZE * 4);
            scaledImage = ImageIO.read(getClass().getResourceAsStream("/Images\\object\\Empty.png"));
            down2 = uTool.scaleImage(scaledImage, gp.TILESIZE * 4, gp.TILESIZE * 4);
            left2 = uTool.scaleImage(scaledImage, gp.TILESIZE * 4, gp.TILESIZE * 4);
            up2 = uTool.scaleImage(scaledImage, gp.TILESIZE * 4, gp.TILESIZE * 4);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        spriteCounter++;
        if (spriteCounter > bounceSpeed) {
            switch(direction) {
                case "down": direction = "left"; break;
                case "left": direction = "up"; break;
                case "up": direction = "down"; break;
            }
            spriteCounter = 0;
        }
    }
    
    public void defeat() {
        spriteNum = 2;      
    }
}
