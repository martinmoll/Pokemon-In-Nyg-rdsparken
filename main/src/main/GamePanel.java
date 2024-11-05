package main;

import MapTiles.TileManager;
import InGameCharacters.Player;

import javax.swing.*;
import java.awt.*;

// Lager denne som en sub-class av JPanel. Jeg får dermed alle funksjonene til JPanel
public class GamePanel extends JPanel implements Runnable{
    // SKJERM INNSTILLINGER
    final int originalTileSize = 16; // Karakter størrelse i pixler. 16x16
    final int scale = 3; // Dette er skalaen vi øker karakteren med når vi viser den på skjermen. 16 -> 48x48
    public int tileSize = originalTileSize*scale; // 48x48
    public final int totalScreenRow = 12;
    public final int totalScreenCol = 16;
    public final int screenWidth = tileSize*totalScreenCol; // 758 px
    public final int screenHeight = tileSize*totalScreenRow; // 576 px

    // MAP INSTILLINGER
    public final int maxWorldCols = 50;
    public final int maxWorldRows = 50;
    public final int worldWidth = tileSize * maxWorldCols;
    public final int worldHeight = tileSize * maxWorldRows;

    CharacterController characterController = new CharacterController();

    // Vi bruker Thread for å hjelpe oss med kjøringen av spillet over tid.
    // Dette blir nyttig når vi vil oppdatere spillet for å få en smooth opplevelse.
    // Jeg sikter etter å kjøre spillet i 60 FPS
    Thread gameThread;
    public Player player = new Player(this, characterController);
    TileManager tileManager = new TileManager(this);
    // FPS - frames per second. Hvor ofte vi oppdaterer skjermen.
    int FPS = 60;

    /**
     *
     */
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Et grep for å forbedre rendering performance

        this.addKeyListener(characterController);

        this.setFocusable(true);
    }

    /**
     * "When an object implementing interface Runnable is used to create a thread,
     * starting the thread causes the object's run method to be called in that separately executing thread.
     * The general contract of the method run is that it may take any action whatsoever."
     */
    @Override
    public void run() {
        double drawTimeInterval = (double) 1000000000 /FPS; // 0.0166 sekunder mellom hver oppdatering
        double timeDelta = 0;
        long currentTime;
        // Her bruker jeg nanosekunder. Tatt fra: https://www.tutorialspoint.com/java/lang/system_nanotime.htm
        long lastTime = System.nanoTime();
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();

            timeDelta += (currentTime - lastTime) / drawTimeInterval;
            // For hver loop legger vi til lastTime til timeren vår.
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(timeDelta >= 1){
                // Oppdaterer diverse informasjon som posisjonen til karakterene
                update();
                // Tegner brettet med den oppdaterte informasjonen
                repaint();
                timeDelta --;
                drawCount ++;
            }
            // Logikk for å vise FPS i terminalen.
            // Når timeren overgår 1 sekund, sjekker vi hvor ofte den ble oppdatert innen dette ene sekundet.
            if(timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                // Resetter begge variablene våre etter den har regnet FPS'en.
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Hjelpemetode for å oppdatere informasjon som posisjonen til karakteren vår når run() løkken vår kjører.
     *
     */
    public void update(){
        // Kaller på update metoden fra Player klassen
        player.update();
}

public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        // Viktig å tegne tiles før player siden vi vil tegne det lag-vis i den rekkefølgen
        tileManager.drawTile(graphics2D);
        player.drawPlayer(graphics2D);

        graphics2D.dispose(); //"Disposes of this graphics context and releases any system resources that it is using."
}
    /**
     * Her starter vi Threaden vår.
     */
    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

}
