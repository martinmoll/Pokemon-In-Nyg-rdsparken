import org.junit.Test;

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
}

