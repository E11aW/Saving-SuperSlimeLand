package Tiles;

import java.awt.Graphics2D;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.MainPanel;
import Main.UtilityTool;

public class TileManager {
    MainPanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(MainPanel gp) {
        this.gp = gp;

        // Read tile info file
        InputStream is = getClass().getResourceAsStream("/Maps\\tileInfo.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Getting tile names and collision from the file
        String line;
        try {
            while((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            } 
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];
        getTileImage();
        mapTileNum = new int [gp.MAXMAP][gp.MAXWORLDCOLUMNS][gp.MAXWORLDROWS];

        loadMap("/Maps\\worldMap.txt", 0);
    }

    public void getTileImage() {

            for(int i = 0; i < fileNames.size(); i++) {
                String fileName;
                boolean collision;

                fileName = fileNames.get(i);
                if(collisionStatus.get(i).equals("true")) {
                    collision = true;
                } else {
                    collision = false;
                }
                setup(i,fileName, collision);
            }

    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Images\\newTiles\\" + imageName));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.TILESIZE, gp.TILESIZE);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int column = 0, row = 0;

            while (row < gp.MAXWORLDROWS) {

                String line = br.readLine();
                if (line == null) {
                    break;
                }
                while (column < gp.MAXWORLDCOLUMNS) {
                    String numbers[] = line.split(" ");
                    if (column < numbers.length) {
                        int num = Integer.parseInt(numbers[column]);
                        mapTileNum[map][column][row] = num;
                    }
                    column++;
                }
                column = 0;
                row++;
            }
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {

        int worldColumn = 0, worldRow = 0;

        while (worldColumn < gp.MAXWORLDCOLUMNS && worldRow < gp.MAXWORLDROWS) {
            int tileNum = mapTileNum[gp.currentMap][worldColumn][worldRow];

            // setting position variables
            int worldX = worldColumn * gp.TILESIZE;
            int worldY = worldRow * gp.TILESIZE;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.TILESIZE > gp.player.worldX - gp.player.screenX && 
                worldX - gp.TILESIZE < gp.player.worldX + gp.player.screenX && 
                worldY + gp.TILESIZE > gp.player.worldY - gp.player.screenY && 
                worldY - gp.TILESIZE < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            worldColumn++;

            if (worldColumn == gp.MAXWORLDCOLUMNS) {
                worldColumn = 0;
                worldRow++;
            }
        }
    }
}
