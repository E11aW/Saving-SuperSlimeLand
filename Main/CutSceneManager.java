package Main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class CutSceneManager {
    MainPanel gp;
    Graphics2D g2;
    public int sceneNum = 0;
    public int scenePhase = -1;
    int counter = 0;
    float alpha = 0f;
    int y;

    public final int NA = 0;
    public final int finalScene = 1;

    public CutSceneManager(MainPanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        switch(sceneNum) {
            case finalScene: finalScene(); break;
        }
    }

    public void finalScene() {
        switch(scenePhase) {
            case -1: playFinalMusic(); break;
            case 0: scene0(); break;
            case 1: scene1(); break;
            case 2: scene2(); break;
            case 3: scene3(); break;
            case 4: scene4(); break;
            case 5: scene5(); break;
            case 6: scene6(); break;
            case 7: scene7(); break;
            case 8: scene8(); break;
            case 9: scene9(); break;
            case 10: scene10(); break;
            case 11: scene11(); break;
        }
    }

    public void playFinalMusic() {
        gp.stopMusic();
        gp.playMusic(2);
        scenePhase++;
    }
    private void scene0() { // speed usually 5
        if(gp.player.worldX > 63 * gp.TILESIZE) {
            gp.player.direction = "left";
            gp.player.worldX -= 10;
        }else if(gp.player.worldY > 9 * gp.TILESIZE) {
            gp.player.direction = "up";
            gp.player.worldY-= 10;                
        } else if(gp.player.worldX > 37 * gp.TILESIZE) {
            gp.player.direction = "left";
            gp.player.worldX -= 10;
        } else {
            gp.player.direction = "right";
            scenePhase++;
        }
    }
    private void scene1 () {
        gp.npc[0].speak();
        scenePhase++;
    }
    private void scene2() {
        int y = 9;
        int x = 48;
        for(int i = 5; i > 0; i--) {
            gp.npc[i].worldX = x * gp.TILESIZE;
            gp.npc[i].worldY = y * gp.TILESIZE;
            y++;
        }
        scenePhase++;
    }
    private void scene3() {
        gp.npc[5].onPath = true;
        if(gp.npc[5].worldX <= 39 * gp.TILESIZE) {
            gp.npc[5].speak();
            scenePhase++;
        }
    }
    private void scene4() {
        for(int i = 4; i > 0; i--) {
            gp.npc[i].onPath = true;
        }
        if(gp.npc[1].worldX <= 43 * gp.TILESIZE) {
            scenePhase++;
        }
    }
    private void scene5() {
        gp.npc[0].speak();
        if(gp.player.worldX < 38 * gp.TILESIZE) {
            gp.player.moveRight();
        } 
        if(gp.keyH.enterPressed) {
            scenePhase++;
        }
    }
    private void scene6() {
        for(int i = 5; i > 0; i--) {
            gp.npc[i].direction = "up";
        }
        if(gp.npc[1].direction.equals("up")) {
            gp.player.direction = "up";
            scenePhase++;
        }
    }
    private void scene7() { 
        gp.obj[0].bounceSpeed = 2;
        if(counterReached(45)) {
            gp.obj[0].defeat();
            scenePhase++;
        }           
    }
    private void scene8() {    
        gp.npc[0].speak();
        if(counterReached(270)) {
            scenePhase++;
        }
    }
    public void scene9() {
        // Darkening the screen
        alpha += 0.005f;
        if(alpha > 1f) {
            alpha = 1f;
        }
        drawBlackBackground(alpha);
        if(alpha == 1f) {
            alpha = 0;
            scenePhase++;
        }
    }
    public void scene10() {
        drawBlackBackground(1f);
        alpha += 0.005f;
        if(alpha > 1f) {
            alpha = 1f;
        }
        String text = "After overcoming the dangerous threat of a relatively small black\n"
                + "hole terrorizing town, color gradually returned to all injured\n" 
                + "slimes and our brave heroes were able to safely return\n" 
                + "home and reunite with some old friends. You did a\n"
                + "great job encouraging RedSlime and finding\n"
                + " the others. Hopefully you'll be back\n"
                + "soon... And just in time for the\n"
                + "next big disaster to strike.";
        drawString(alpha, 30f, 200, text, 50, gp.TILESIZE);
        if(counterReached(500)) {
            scenePhase++;
        }
    }
    public void scene11() {
        drawBlackBackground(1f);
        alpha += 0.005f;
        if(alpha > 1f) {
            alpha = 1f;
        }
        int y = gp.SCREENHEIGHT / 2;
        int x = gp.ui.getTextX("Thanks for playing!") - (gp.TILESIZE * 4);
        drawString(alpha, 48f, y, "Thanks for playing!", 50, x);
        if(counterReached(250)) {
            System.exit(0);
        }
    }

    public boolean counterReached(int target) {
        boolean counterReached = false;
        counter++;
        if(counter > target) {
            counterReached = true;
            counter = 0;
        }
        return counterReached;
    }

    public void drawBlackBackground(float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.SCREENWIDTH, gp.SCREENHEIGHT);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight, int x) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, fontSize));

        for(String line: text.split("\n")) {
            g2.drawString(line, x, y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
