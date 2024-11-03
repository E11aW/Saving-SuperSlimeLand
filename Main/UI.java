package Main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import Entities.Entity;

public class UI {
    
    MainPanel gp;
    Graphics2D g2;

    Font invasion;
    public boolean messageOn = false;
    public String message = "";
    public boolean gameOver = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public Entity npc;
    int subState = 0;
    int charIndex = 0;
    String combinedText = "";

    public UI(MainPanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/Fonts\\INVASION2000.TTF");
            invasion = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch(IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }

    // for displaying messages about events/objects - like door being closed
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(invasion);
        g2.setColor(Color.white);

        if(gp.gameState == gp.TITLESTATE) {
            drawTitleScreen();
        }else if(gp.gameState == gp.PLAYSTATE) {

        } else if (gp.gameState == gp.PAUSESTATE) {
            drawPauseScreen();
        } else if (gp.gameState == gp.DIALOGUESTATE) {
            drawDialogueScreen();
        }
        if(gp.gameState == gp.OPTIONSSTATE) {
            drawOptionsScreen();
        }
    }

    public void drawTitleScreen() {
        // background
        g2.setColor(new Color(79, 84, 140));
        g2.fillRect(0, 0, gp.SCREENWIDTH, gp.SCREENWIDTH);

        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 65F));
        String text = "Saving SuperSlimeLand";
        int x = getTextX(text);
        int y = gp.TILESIZE * 3;
        // shadow
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        // title text
        g2.setColor(new Color(250, 226, 249));
        g2.drawString(text, x, y);

        if(subState == 0) {
            // display character
            x = gp.SCREENWIDTH / 2 - (gp.TILESIZE);
            y += gp.TILESIZE * 2;
            g2.drawImage(gp.player.down1, x, y, gp.TILESIZE * 2, gp.TILESIZE * 2, null);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

            text = "Start Game";
            x = getTextX(text);
            y += gp.TILESIZE * 4;
            g2.drawString(text, x, y); 
            if(commandNum == 0) {
                g2.drawString(">", x - gp.TILESIZE, y);
                if(gp.keyH.enterPressed) {
                    gp.gameState = gp.PLAYSTATE;
                }
            }

            text = "Controls";
            x = getTextX(text);
            y += gp.TILESIZE * 2;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x - gp.TILESIZE, y);
                if(gp.keyH.enterPressed) {
                    subState = 1;
                    commandNum = 0;
                    gp.keyH.enterPressed = false;
                }
            }

            text = "Quit";
            x = getTextX(text);
            y += gp.TILESIZE * 2;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - gp.TILESIZE, y);
                if(gp.keyH.enterPressed) {
                    System.exit(0);
                }
            }
        }
        if (subState == 1) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(32F));

            // SUB WINDOW
            int frameX = gp.TILESIZE * 6;
            int frameY = gp.TILESIZE * 4;
            int frameWidth = gp.TILESIZE * 8;
            int frameHeight = gp.TILESIZE * 10;
            drawSubWindow(frameX, frameY, frameWidth, frameHeight);
            options_control(frameX, frameY);
            if(gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 1;
                gp.keyH.enterPressed = false;
            }
        }
        
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getTextX(text) - (int)(gp.TILESIZE * 1.75);
        int y = gp.SCREENHEIGHT / 2;
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(40F));

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // Dialogue Window
        int x = (int) (gp.TILESIZE * 1.5), y = gp.TILESIZE * 10; 
        int width = gp.SCREENWIDTH - (gp.TILESIZE * 3), height = gp.TILESIZE * 4;
        drawSubWindow(x, y, width, height);

        // Character Window
        x += gp.TILESIZE * 13;
        y = gp.TILESIZE * 10; 
        width = gp.TILESIZE * 4;
        height = gp.TILESIZE * 4;
        drawSubWindow(x, y, width, height);
        
        x+= gp.TILESIZE;
        y+= gp.TILESIZE / 2;
        g2.drawImage(npc.left1, x, y, gp.TILESIZE * 2, gp.TILESIZE * 2, null);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 17F));
        x = getTextX(npc.name) + (int) (gp.TILESIZE * 6.5);
        y+= (int) (gp.TILESIZE * 2.5);
        g2.drawString(npc.name, x, y);

        //draw the dialogue in this screen
        x = (int) (gp.TILESIZE * 1.5);
        y = gp.TILESIZE * 10;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 23F));
        x += gp.TILESIZE;
        y += gp.TILESIZE;

        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
            char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            if(charIndex < characters.length) {
                gp.playSE(1);

                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;

            }

            if(gp.keyH.enterPressed == true) {
                charIndex = 0;
                combinedText = "";
                npc.dialogueIndex++;
                gp.keyH.enterPressed = false;
            }

        } else {
            npc.dialogueIndex = 0;
            if(gp.gameState == gp.DIALOGUESTATE) {
                gp.gameState = gp.PLAYSTATE;
            } else if(gp.csManager.scenePhase > 0) {
                gp.gameState = gp.CUTSCENESTATE;
            }
        }

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 35;
        }
    }

    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        // SUB WINDOW
        int frameX = gp.TILESIZE * 6;
        int frameY = gp.TILESIZE;
        int frameWidth = gp.TILESIZE * 8;
        int frameHeight = gp.TILESIZE * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: options_control(frameX, frameY); break;
            case 2: options_endGameConfirmation(frameX, frameY); break;
        }
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Options";
        textX = getTextX(text);
        textY = frameY + gp.TILESIZE;
        g2.drawString(text, textX, textY);

        // Music
        textX = frameX + gp.TILESIZE;
        textY += gp.TILESIZE * 2;
        g2.drawString("Music", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
        }

        // Sound Effects
        textY += gp.TILESIZE;
        g2.drawString("SE", textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // Controls
        textY += gp.TILESIZE;
        g2.drawString("Controls", textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                subState = 1;
                gp.keyH.enterPressed = false;
                commandNum = 0;
            }
        }

        // End Game
        textY += gp.TILESIZE;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                subState = 2;
                gp.keyH.enterPressed = false;
                commandNum = 0;
            }
        }

        // Back
        textY += gp.TILESIZE * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                gp.gameState = gp.PLAYSTATE;
                gp.keyH.enterPressed = false;
                commandNum = 0;
            }
        }

        // Music Volume
        textX = frameX + (int) (gp.TILESIZE * 4.5);
        textY = frameY + gp.TILESIZE * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SE Volume
        textY += gp.TILESIZE;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Tilte
        String text = "Controls";
        textX = getTextX(text);
        textY = frameY + gp.TILESIZE;
        g2.drawString(text, textX, textY);

        // Control Options
        textX = frameX + gp.TILESIZE;
        textY += gp.TILESIZE * 2;
        g2.drawString("Move", textX, textY); textY += gp.TILESIZE;
        g2.drawString("Interact", textX, textY); textY += gp.TILESIZE;
        g2.drawString("Pause", textX, textY); textY += gp.TILESIZE;
        g2.drawString("Options", textX, textY); textY += gp.TILESIZE;

        textX = frameX + gp.TILESIZE * 5;
        textY = frameY + gp.TILESIZE * 3;
        g2.drawString("WASD", textX, textY); textY += gp.TILESIZE;
        g2.drawString("E", textX, textY); textY += gp.TILESIZE;
        g2.drawString("P", textX, textY); textY += gp.TILESIZE;
        g2.drawString("ESC", textX, textY); textY += gp.TILESIZE;

        // Back
        textX = frameX + gp.TILESIZE;
        textY = frameY + gp.TILESIZE * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                if(gp.gameState == gp.OPTIONSSTATE) {
                    subState = 0;
                    gp.keyH.enterPressed = false;
                    commandNum = 2;
                } else if (gp.gameState == gp.TITLESTATE) {
                    subState = 0;
                    commandNum = 1;
                    gp.keyH.enterPressed = false;
                }
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.TILESIZE;
        int textY = frameY + (int) (gp.TILESIZE * 2.5);

        currentDialogue = "Quit the game \nand return to \nthe title screen?";
        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "Yes";
        textX = getTextX(text);
        textY += gp.TILESIZE * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                subState = 0;
                gp.keyH.enterPressed = false;
                gp.gameState = gp.TITLESTATE;
            }
        }

        // NO
        text = "No";
        textX = getTextX(text);
        textY += gp.TILESIZE;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                subState = 0;
                gp.keyH.enterPressed = false;
                commandNum = 3;
            }
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 215);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getTextX(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.SCREENWIDTH / 2 - length / 2;
        return x;
    }
}
