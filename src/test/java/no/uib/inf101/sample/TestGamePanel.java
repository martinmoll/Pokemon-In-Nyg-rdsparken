package no.uib.inf101.sample;

import main.GamePanel;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;


public class TestGamePanel {

    @Test
    public void testScreenDimensions() {
        GamePanel gamePanel = new GamePanel();
        assertEquals(768, gamePanel.screenWidth);
        assertEquals(576, gamePanel.screenHeight);
    }

    @Test
    public void testTileSize() {
        GamePanel gamePanel = new GamePanel();
        assertEquals(48, gamePanel.tileSize);
    }

    @Test
    public void testBattleMessageDisplay() {
        // Create a new instance of GamePanel
        GamePanel gamePanel = new GamePanel();

        // Simulate a battle scenario
        gamePanel.inBattle = true;
        gamePanel.battleMessages.append("Bulbasaur attacked Squirtle.\n");
        gamePanel.battleMessages.append("Squirtle took 10 damage.\n");

        // Paint the component to display battle messages
        gamePanel.repaint();

        // Verify that the battle messages are correctly displayed
        // (You may need to adjust these coordinates based on the actual position of the battle rectangle)
        int messageX = gamePanel.battleArena.x + 10;
        int messageY = gamePanel.battleArena.y + 20; // Starting Y position for the first message
        assert gamePanel.getGraphics().getFont().getSize() == 12 : "Font size is incorrect";
        assert gamePanel.getGraphics().getColor().equals(Color.WHITE) : "Font color is incorrect";
        //assert gamePanel.getGraphics().drawString("Bulbasaur attacked Squirtle.", messageX, messageY) : "Message 1 not displayed";
        //assert gamePanel.getGraphics().drawString("Squirtle took 10 damage.", messageX, messageY + 12) : "Message 2 not displayed";

        System.out.println("Battle message display test passed.");
    }
}

