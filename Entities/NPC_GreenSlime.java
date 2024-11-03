package Entities;

import Main.MainPanel;

public class NPC_GreenSlime extends Entity {

    public NPC_GreenSlime(MainPanel gp) {
        super(gp);
        name = "GreenSlime";
        direction = "down";

        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/Images\\green\\greenup1");
        up2 = setup("/Images\\green\\greenup2");
        down1 = setup ("/Images\\green\\greendown1");
        down2 = setup("/Images\\green\\greendown2");
        left1 = setup("/Images\\green\\greenleft1");
        left2 = setup("/Images\\green\\greenleft2");
        right1 = setup("/Images\\green\\greenright1");
        right2 = setup("/Images\\green\\greenright2");
    }

    public void setDialogue() {
        dialogues[0][0] = "I love hiding in the forest.";
        dialogues[0][1] = "But what are you doing here? Do you need \nhelp with something?";
        dialogues[0][2] = "The town's in danger? Of course I'll help! \nI'll meet you in town in a bit.";
        dialogues[0][3] = "I have heard another slime lives in this \nforest too. Something about a lake maybe? \nHopefully you can find even more slimes to \nhelp us out!";
    }

    public void setAction() {
        if(onPath) {
            searchPath(40, 9);
        }
    }

    public void speak() {
        facePlayer();
        if(!talkedTo) {
            gp.player.totalAllies++;
            talkedTo = true;
        }
        startDialogue(this, dialogueSet);
    }
}
