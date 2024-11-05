package no.uib.inf101.sample;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.CharacterController;
import main.GamePanel;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class TestCharacterController {


    @Test
    public void testKeyRelease() {
        GamePanel gamePanel = new GamePanel();
        CharacterController characterController = new CharacterController(gamePanel);
        KeyEvent keyEvent = new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' ');
        characterController.keyReleased(keyEvent);
        assertFalse(characterController.upPressed);
    }
}
