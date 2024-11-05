package NPCs;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Super klassen til alle NPCs og objekter vi legger til i spillet
 * Skal i utgangspunktet være ganske lik Characters klassen
 */
public class SuperNPC {

    public BufferedImage image;
    public String name;
    public int worldX, worldY;

    /**
     * Tegner NPCen vår
     * Her brukes noe av samme kodelogikk som drawTile i TileManager klassen.
     * @param graphics2D
     * @param gamePanel
     */
    public void drawNPC(Graphics2D graphics2D, GamePanel gamePanel){
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        graphics2D.drawImage(image, screenX, screenY ,
                gamePanel.tileSize, gamePanel.tileSize,null);
    }
}

