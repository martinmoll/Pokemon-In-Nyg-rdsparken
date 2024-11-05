package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Hele klassen og logikken som er implementert er tatt fra: https://youtu.be/VpH33Uw-_0E?si=vcxK14wlBsEI0E1V
 */
public class CharacterController implements KeyListener {
    public boolean upPressed;
    public boolean downPressed;
    public boolean rightPressed;
    public boolean leftPressed;
    private boolean inBattle;
    private GamePanel gamePanel;

    public CharacterController(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        inBattle = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (!inBattle) {
            gamePanel.checkInteraction();
        }
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
            if (code == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (code == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            if (code == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (code == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
        }
    }

