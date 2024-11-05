package no.uib.inf101.sample;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import MapTiles.TileManager;
import main.GamePanel;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TestTileManager {

    @Test
    public void testLoadMap() {
        TileManager tileManager = new TileManager(new GamePanel());
        tileManager.loadMap("Maps/test_map.txt");
        int[][] mapTileNum = tileManager.mapTileNum;
        assertNotNull(mapTileNum);
        assertEquals(50, mapTileNum.length);
        assertEquals(50, mapTileNum[0].length);
        assertEquals(1, mapTileNum[0][0]); // Assuming the first tile number is 1
        assertEquals(1, mapTileNum[6][5]); // Assuming the tile number at (6, 5) is 2
        assertEquals(4, mapTileNum[28][10]); // Assuming the tile number at (28, 8) is 4
    }

    @Test
    public void testDrawTile() {
        TileManager tileManager = new TileManager(new GamePanel());
        tileManager.getTileImage(); // Assuming tile images are properly loaded
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        tileManager.drawTile(graphics);
        // Assuming grass tile is loaded at index 0
        BufferedImage grassImage = tileManager.tile[1].image;
        // Assuming the grass tile is drawn at (0, 0) on the image
        assertEquals(grassImage, image.getSubimage(0, 0, tileManager.gamePanel.tileSize, tileManager.gamePanel.tileSize));
    }
}

