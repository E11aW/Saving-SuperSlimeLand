package Main;

import java.util.Random;

import Entities.*;
import Objects.BlackHole;

public class AssetSetter {
    
    MainPanel gp;
    Random rand = new Random();

    public AssetSetter(MainPanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new BlackHole(gp);
        gp.obj[0].worldX = 37 * gp.TILESIZE;
        gp.obj[0].worldY = 3 * gp.TILESIZE;
    }

    public void setNPC() {
        // QuestGiver
        gp.npc[0] = new NPC_QuestGiver(gp);
        gp.npc[0].worldX = 42 * gp.TILESIZE;
        gp.npc[0].worldY = 7 * gp.TILESIZE;
        // PurpleSlime
        gp.npc[1] = new NPC_PurpleSlime(gp);
        gp.npc[1].worldX = 66 * gp.TILESIZE;
        gp.npc[1].worldY = 35 * gp.TILESIZE;
        // BlueSlime
        gp.npc[2] = new NPC_BlueSlime(gp);
        gp.npc[2].worldX = 81 * gp.TILESIZE;
        gp.npc[2].worldY = 89 * gp.TILESIZE;
        // GreenSlime
        gp.npc[3] = new NPC_GreenSlime(gp);
        gp.npc[3].worldX = 5 * gp.TILESIZE;
        gp.npc[3].worldY = 92 * gp.TILESIZE;
        // YellowSlime
        gp.npc[4] = new NPC_YellowSlime(gp);
        gp.npc[4].worldX = 95 * gp.TILESIZE; 
        gp.npc[4].worldY = 3 * gp.TILESIZE;
        // GrandmaSlime
        gp.npc[5] = new NPC_OrangeSlime(gp);
        gp.npc[5].worldX = 78 * gp.TILESIZE;
        gp.npc[5].worldY = 51 * gp.TILESIZE;

        // General TownsFolk
        setGenerals(6, 73, 5);
        setGenerals(7, 67, 6);
        setGenerals(8, 73, 13);
        setGenerals(9, 70, 22);
        setGenerals(10, 40, 32);
        setGenerals(11, 26, 25);
        setGenerals(12, 28, 27);
        setGenerals(13, 15, 22);
        setGenerals(14, 6, 14);
        setGenerals(15, 7, 6);
        setGenerals(16, 17, 11);
        setGenerals(17, 25, 16);
    }

    public void setGenerals(int i, int x, int y) {
        gp.npc[i] = new NPC_General(gp);
        gp.npc[i].worldX = gp.TILESIZE * x;
        gp.npc[i].worldY = gp.TILESIZE * y;
    }
}
