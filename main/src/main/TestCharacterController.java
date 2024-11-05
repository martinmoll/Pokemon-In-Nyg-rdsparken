package main;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class TestCharacterController {

    @Test
    public void testKeyPress() {
        CharacterController characterController = new CharacterController();
        KeyEvent keyEvent = new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' ');
        characterController.keyPressed(keyEvent);
        assertTrue(characterController.upPressed);
    }

    @Test
    public void testKeyRelease() {
        CharacterController characterController = new CharacterController();
        KeyEvent keyEvent = new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' ');
        characterController.keyReleased(keyEvent);
        assertFalse(characterController.upPressed);
    }
}
