package Main;

import java.awt.event.*;

public class KeyHandler implements KeyListener {

    public MainPanel gp;
    // public String direction;
    public Boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, enterPressed = false;

    // DEBUG
    boolean showDebugs = false;

    public KeyHandler(MainPanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent k) {
        int code = k.getKeyCode();
        // TITLE STATE
        if (gp.gameState == gp.TITLESTATE) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            } else if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            } else if (code == KeyEvent.VK_E) {
                enterPressed = true;
            }
        }

        // PLAY STATE
        if(gp.gameState == gp.PLAYSTATE) {
            switch(code) {
                case KeyEvent.VK_W: upPressed = true; break;
                case KeyEvent.VK_A: leftPressed = true; break;
                case KeyEvent.VK_S: downPressed = true; break;
                case KeyEvent.VK_D: rightPressed = true; break;
                
                case KeyEvent.VK_P:
                    gp.gameState = gp.PAUSESTATE;
                    break;
                case KeyEvent.VK_ESCAPE:
                    gp.gameState = gp.OPTIONSSTATE;
                    break;
                
                case KeyEvent.VK_E:
                    enterPressed = true;
                    break;

                //DEBUG
                case KeyEvent.VK_T:
                    showDebugs = !showDebugs;
                    break;
            }
        } 
        
        // PAUSE STATE
        else if(gp.gameState == gp.PAUSESTATE) {
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.PLAYSTATE;
            }
        }

        // DIALOGUE STATE
        else if(gp.gameState == gp.DIALOGUESTATE || gp.gameState == gp.CUTSCENESTATE) {
            if(code == KeyEvent.VK_E) {
                enterPressed = true;
            }
        }

        // OPTIONS STATE
        else if (gp.gameState == gp.OPTIONSSTATE) {
            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.PLAYSTATE;
            } else if (code == KeyEvent.VK_E) {
                enterPressed = true;
            }

            int maxCommandNum = 0;
            switch(gp.ui.subState) {
                case 0: maxCommandNum = 4; break;
                case 2: maxCommandNum = 1; break;
            }

            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                gp.playSE(1);
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = maxCommandNum;
                }
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                gp.playSE(1);
                if(gp.ui.commandNum > maxCommandNum) {
                    gp.ui.commandNum = 0;
                }
            }

            if(code == KeyEvent.VK_A) {
                if(gp.ui.subState == 0) {
                    if(gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
                        gp.music.volumeScale--;
                        gp.music.checkVolume();
                        gp.playSE(1);
                    }
                    if(gp.ui.commandNum == 1 && gp.sound.volumeScale > 0) {
                        gp.sound.volumeScale--;
                        gp.playSE(1);
                    }
                }
            }
            if(code == KeyEvent.VK_D) {
                if(gp.ui.subState == 0) {
                    if(gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
                        gp.music.volumeScale++;
                        gp.music.checkVolume();
                        gp.playSE(1);
                    }
                    if(gp.ui.commandNum == 1 && gp.sound.volumeScale < 5) {
                        gp.sound.volumeScale++;
                        gp.playSE(1);
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent k) {
        switch(k.getKeyCode()) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
        }
    }
}
