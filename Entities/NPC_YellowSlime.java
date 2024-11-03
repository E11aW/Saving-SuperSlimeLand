package Entities;

import Main.MainPanel;

public class NPC_YellowSlime extends Entity{

    public NPC_YellowSlime(MainPanel gp) {
        super(gp);
        name = "YellowSlime";
        direction = "down";

        getImage();
        setDialogue();
    } 

    public void getImage() {

        up1 = setup("/Images\\yellow\\yellowup1");
        up2 = setup("/Images\\yellow\\yellowup2");
        down1 = setup ("/Images\\yellow\\yellowdown1");
        down2 = setup("/Images\\yellow\\yellowdown2");
        left1 = setup("/Images\\yellow\\yellowleft1");
        left2 = setup("/Images\\yellow\\yellowleft2");
        right1 = setup("/Images\\yellow\\yellowright1");
        right2 = setup("/Images\\yellow\\yellowright2");
    }

    public void setDialogue() {
        dialogues[0][0] = "They all kicked me out of town...";
        dialogues[0][1] = "No I don't know why! What would they even \nhave against me? I'm great at what I do!";
        dialogues[0][2] = "It just so happens to be stealing...";
        dialogues[0][3] = "I mean I guess I can help you. Will ya pay \nme though?";
        dialogues[0][4] = "Fiiiine I'll help to 'protect the greater \ngood' or whatever. You're welcome.";
    }

    public void setAction() {
        if(onPath) {
            searchPath(39, 9);
        }
    }

    public void speak() {
        facePlayer();
        startDialogue(this, dialogueSet);
        if(!talkedTo) {
            gp.player.totalAllies++;
            talkedTo = true;
            onPath = true;
        }
    }
}
