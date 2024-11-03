package Entities;

import Main.MainPanel;

public class NPC_PurpleSlime extends Entity{
    
    public NPC_PurpleSlime(MainPanel gp) {
        super(gp);
        name = "PurpleSlime";
        direction = "down";

        getImage();
        setDialogue();
    } 

    public void getImage() {

        up1 = setup("/Images\\purple\\purpleup1");
        up2 = setup("/Images\\purple\\purpleup2");
        down1 = setup ("/Images\\purple\\purpledown1");
        down2 = setup("/Images\\purple\\purpledown2");
        left1 = setup("/Images\\purple\\purpleleft1");
        left2 = setup("/Images\\purple\\purpleleft2");
        right1 = setup("/Images\\purple\\purpleright1");
        right2 = setup("/Images\\purple\\purpleright2");
    }

    public void setDialogue() {
        dialogues[0][0] = "I keep hearing strange noises from inside \ntown. I'm worried about everyone, but I \nreally shouldn't leave my post...";
        dialogues[0][1] = "Can you check for me?";

        dialogues[1][0] = "They need help? Then I will gladly lend my \naid! Now go find some others who can join \nus too!";
    }

    public void setAction() {
        if(onPath) {
            searchPath(42, 9);
        }
    }

    public void speak() {
        facePlayer();
        if(gp.player.gottenQuest) {
            dialogueSet = 1;
            if(!talkedTo) {
                gp.player.totalAllies++;
            }
            talkedTo = true;
        }
        startDialogue(this, dialogueSet);
    }
}
