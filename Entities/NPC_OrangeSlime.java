package Entities;

import Main.MainPanel;

public class NPC_OrangeSlime extends Entity {
    
    public NPC_OrangeSlime(MainPanel gp) {
        super(gp);
        name = "GrandmaSlime";
        direction = "down";

        getImage();
        setDialogue();
    } 

    public void getImage() {

        up1 = setup("/Images\\orange\\orangeup1");
        up2 = setup("/Images\\orange\\orangeup2");
        down1 = setup ("/Images\\orange\\orangedown1");
        down2 = setup("/Images\\orange\\orangedown2");
        left1 = setup("/Images\\orange\\orangeleft1");
        left2 = setup("/Images\\orange\\orangeleft2");
        right1 = setup("/Images\\orange\\orangeright1");
        right2 = setup("/Images\\orange\\orangeright2");
    }

    public void setDialogue() {
        // general dialogue
        
        // main dialogue
        dialogues[0][0] = "Can you go into town and buy some more \nslimesicles? We're running quite low.";
        dialogues[0][1] = "If you need help getting there, Just follow \nthat worn-down path. Town's not too far \nfrom here, just head north.";
        dialogues[0][2] = "Thanks for all your help, dearie!";

        dialogues[1][0] = "The town's in trouble? That's awful! I hope \nyou're able to find some slimes to help!";

        dialogues[2][0] = "You've found 4 slimes already? Wow, that's \nimpressive! But didn't you need 5?";
        dialogues[2][1] = "It is clear you need more help... but it's \nbeen a whlie since I fought my last \ngreat evil. I guess I could still try to \nassist you though...";
        dialogues[2][2] = "Alright, you've convinced me! Let's save \nthe town together! Walk in and I'll join you \nfor the showdown.";

        dialogues[3][0] = "They're not alone! Hope we all made it in \ntime!";
    }

    public void setAction() {
        if(onPath) {
            searchPath(38, 9);
        }
    }

    public void speak() {
            
        facePlayer();
        if(gp.gameState == gp.CUTSCENESTATE) {
            dialogueSet = 3;
        }else if(gp.player.totalAllies >= 4) {
            dialogueSet = 2;
            gp.player.totalAllies++;
        } else if (gp.player.gottenQuest) {
            dialogueSet = 1;
        }
        startDialogue(this, dialogueSet);
    }
}
