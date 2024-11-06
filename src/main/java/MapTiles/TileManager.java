package MapTiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Logikken for klassen er tatt fra: https://youtu.be/ugzxCcpoSdE?si=xIhrEB_B6qHQ9BPN
 * Egne tiles er funnet på internet.
 */
public class TileManager {
    public GamePanel gamePanel;
    public Tile[] tile;
    public int[][] mapTileNum;

    /**
     * Tar inn GamePanelet vårt og tegner
     * @param gamePanel
     */
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10]; // Dette betyr vi har plass til 10 typer tiles
        // mapTileNum arrayet lagrer alle nummerene vi har i vårt map
        mapTileNum = new int[gamePanel.maxWorldCols][gamePanel.maxWorldRows];
        getTileImage();
        loadMap("Maps/map1.txt");
    }

    /**
     * Henter .png bildet og leser dette.
     * De blir koblet til et nummer som representerer den typen tile.
     * Her er selve .read(........) kallet endret for å ikke få nullPointer errors
     */
    public void getTileImage() {
        try {
            // Gress tile
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/MapTilesImage/pokemon_grass.png")));
            // Høyt gress tile
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/MapTilesImage/pokemon_tall_grass.png")));

            // Jord tile https://www.deviantart.com/supercommanderwolfy/art/Grass-Dirt-Tiles-001-265189221
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/MapTilesImage/pokemon_dirt.png")));
            // Vann tile
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/MapTilesImage/water_tile.png")));
            // Stein tile
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/MapTilesImage/stone_tile.png")));
            tile[4].collision = true;
            // Stein RFB tile
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/MapTilesImage/RFB_stonetile.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metode som tegner tilesene våre
     * @param graphics2D
     */
    public void drawTile(Graphics2D graphics2D) {

        int worldCol = 0;
        int worldRow = 0;
        // Denne while løkken fyller hele rektangelet vårt med "tile[0]" som er vanlig gress
        // Den gjør det fra col til totalScreenCol, så hopper den ned en rad og fortsetter til alt er fylt
        while(worldCol < gamePanel.maxWorldCols && worldRow < gamePanel.maxWorldRows){
            int tileNumber = mapTileNum[worldCol][worldRow]; // tileNumber fungerer som indeksen vår
            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            graphics2D.drawImage(tile[tileNumber].image, screenX, screenY ,
                    gamePanel.tileSize, gamePanel.tileSize,null);
            worldCol++;
            // Resetter col hvis vi når totalScreenCol, som er 16
            // Hopper ned en rad for å fylle den neste
            if(worldCol == gamePanel.maxWorldCols){
                worldCol = 0;

                worldRow ++;

            }
        }
    }

    /**
     * Skanner tekstfilen for mappet linje for linje.
     * Deler opp tekstfilen nummer for nummer og mapper den til arrayet vårt mapTileNum
     */
    public void loadMap(String filePathMap){

        try{
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePathMap);
            // BufferedReader har som ansvar å lese innholdet i tekstfilen vår
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;
            while(col < gamePanel.maxWorldCols && row < gamePanel.maxWorldRows){
                String line = bufferedReader.readLine(); // Leser én linje med tekst og legger den inne i "line"
                while (col < gamePanel.maxWorldCols) {

                    String numbers[] = line.split(" "); // Splitter stringen ved mellomrom og legger den til i arrayet
                    int number = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = number;
                    col++;
                }
                // Hvis vi har iterert igjennom alle kolonnene på første rad resetter vi col, og hopper til neste rad
                if (col == gamePanel.maxWorldCols){
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (Exception e){

        }
    }
}
