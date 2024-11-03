package Entities;

import Main.MainPanel;

public class NPC_BlueSlime extends Entity{
    
    public NPC_BlueSlime(MainPanel gp) {
        super(gp);
        name = "BlueSlime";
        direction = "down";

        getImage();
        setDialogue();
    } 

    public void getImage() {

        up1 = setup("/Images\\blue\\blueup1");
        up2 = setup("/Images\\blue\\blueup2");
        down1 = setup ("/Images\\blue\\bluedown1");
        down2 = setup("/Images\\blue\\bluedown2");
        left1 = setup("/Images\\blue\\blueleft1");
        left2 = setup("/Images\\blue\\blueleft2");
        right1 = setup("/Images\\blue\\blueright1");
        right2 = setup("/Images\\blue\\blueright2");
    }

    public void setDialogue() {
        dialogues[0][0] = "Wow, I didn't know anyone else knew about \nthis lake!";
        dialogues[0][1] = "You're out here searching for help? Well I \ncan't just leave all those people to die! Of \ncourse I'll come with you!";
        dialogues[0][2] = "And if you are still looking for more slimes, \nI know an old friend of mine lives \nsomewhere north of here. They are a little \ngrumpy, but will surely help us out!";
        dialogues[0][3] = "I can meet you back at town once \neveryone else is ready.";
    }

    public void setAction() {
        if(onPath) {
            searchPath(41, 9);
        }
    }

    public void speak() {
        facePlayer();
        if(!talkedTo) {
            gp.player.totalAllies++; 
            onPath = true;
            talkedTo = true;
        }
        startDialogue(this, dialogueSet);
    }
}
