package Entities;

import Main.MainPanel;

public class NPC_General extends Entity {

    public NPC_General(MainPanel gp) {
        super(gp);
        name = "TownSlime";
        int dirNum = rand.nextInt(7) + 1; // gives number 1 to 7 for the 3 directions
        if(dirNum <= 3) direction = "down";
        else if(dirNum > 3 && dirNum <= 5) direction = "left";
        else if(dirNum > 5 && dirNum <= 7) direction = "right";
        bounceSpeed = rand.nextInt(15) + 35; // number between 35 and 50
        
        speed = 0;

        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/Images\\npc\\blankup1");
        up2 = setup("/Images\\npc\\blankup2");
        down1 = setup ("/Images\\npc\\blankdown1");
        down2 = setup("/Images\\npc\\blankdown2");
        left1 = setup("/Images\\npc\\blankleft1");
        left2 = setup("/Images\\npc\\blankleft2");
        right1 = setup("/Images\\npc\\blankright1");
        right2 = setup("/Images\\npc\\blankright2");
    }

    public void setDialogue() {
        dialogues[0][0] = "Please help us!";
        dialogues[1][0] = "I'm so scared...";
    }

    public void setAction() {}

    public void speak() {
        facePlayer();
        dialogueSet = rand.nextInt(2); // 0 to 1
        startDialogue(this, dialogueSet);
    }
}
