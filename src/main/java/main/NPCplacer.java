package main;

import NPCs.Badekamerat;

/**
 * Klassen der vi skriver metoden for å faktisk tegne NPCene på brettet
 */
public class NPCplacer {
    GamePanel gamePanel;

    public NPCplacer(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    /**
     * Plasserer Badekamerat på ønsket koordinat
     */
    public void setNPC(){
        gamePanel.npc[0] = new Badekamerat();
        gamePanel.npc[0].worldX = 8 * gamePanel.tileSize;
        gamePanel.npc[0].worldY = 46 * gamePanel.tileSize;

    }
}
