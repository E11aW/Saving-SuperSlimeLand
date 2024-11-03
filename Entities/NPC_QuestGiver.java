package Entities;

import Main.MainPanel;

public class NPC_QuestGiver extends Entity {

    public NPC_QuestGiver(MainPanel gp) {
        super(gp);
        name = "QuestGiver";
        direction = "down";

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
        dialogues[0][0] = "I'm so glad you came into town today! \nDisaster has struck! We'll need the help of \nyou and all those other loner slimes outside \nof town to save everyone!";
        dialogues[0][1] = "As the QuestGiver, it is my duty to give \nyou the bestest of quests so that your \nquest can save us all!";
        dialogues[0][2] = "This will be the questiest quest of all \nquests that surpasses any other quest \never given. You will love this quest so \nmuch that all future given quests will-";
        dialogues[0][3] = "Hey don't you dare call me annoying!";
        dialogues[0][4] = "Look, what you need to do is simple: just \nfind the 5 other rainbow colors so you all \ncan get rid of this blackhole.";
        dialogues[0][5] = "Once you do that, the rest of us will be \nsaved! Now, go search for help!";

        dialogues[1][0] = "It's been a while, did you find everyone? \nWhy are you alone?";

        dialogues[2][0] = "So you did find everyone, great job! Now \nyou should be able to form a perfect \nrainbow and face this blackhole so we can \nreturn color to our town!";
        
        dialogues[3][0] = "That... was SO FAST!";
        dialogues[3][1] = "And I am surprised that worked- I mean! \nMy quests are always accurate!";
        dialogues[3][2] = "Great job, all of you! The 7 of you, yes 7, \nmanaged to save our town and all of \nSuperSlimeLand.";
        dialogues[3][3] = "You are all heroes now! Thank you for \nsaving us!!!";
    }

    public void setAction() {}

    public void speak() {
        if(gp.gameState == gp.CUTSCENESTATE) {
            direction = "down";
            if(gp.csManager.scenePhase > 1 && gp.csManager.scenePhase < 8) {
                dialogueSet = 2;
                startDialogue(this, 2);
            } else {
                if(gp.csManager.scenePhase == 1) {
                    dialogueSet = 1;
                    startDialogue(this, 1);
                } else if(gp.csManager.scenePhase == 8) {
                    dialogueSet = 3;
                    startDialogue(this, 3);
                } 
            } 
        } else {
            facePlayer();
            gp.player.gottenQuest = true;
            startDialogue(this, dialogueSet);
        }
    }
}
