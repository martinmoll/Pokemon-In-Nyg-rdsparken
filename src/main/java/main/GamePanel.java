package main;

import InGameCharacters.Player;
import MapTiles.TileManager;
import NPCs.SuperNPC;

import javax.swing.*;
import java.awt.*;

// Hele klassen, utenom StartBattle og CheckInteraction er tatt fra: https://youtu.be/VpH33Uw-_0E?si=YjZBq5C3ReoLJaSQ
// Lager denne som en sub-class av JPanel. Jeg får dermed alle funksjonene til JPanel.
public class GamePanel extends JPanel implements Runnable{
    // SKJERM INNSTILLINGER
    final int originalTileSize = 16; // Karakter størrelse i pixler. 16x16
    final int scale = 3; // Dette er skalaen vi øker karakteren med når vi viser den på skjermen. 16 -> 48x48
    public int tileSize = originalTileSize*scale; // 48x48
    public final int totalScreenRow = 12;
    public final int totalScreenCol = 16;
    public final int screenWidth = tileSize*totalScreenCol; // 758 px
    public final int screenHeight = tileSize*totalScreenRow; // 576 px

    //INSTILLINGER FOR MAPPET
    public final int maxWorldCols = 50;
    public final int maxWorldRows = 50;
    public final int worldWidth = tileSize * maxWorldCols;
    public final int worldHeight = tileSize * maxWorldRows;

    CharacterController characterController = new CharacterController(this);

    // Vi bruker Thread for å hjelpe oss med kjøringen av spillet over tid.
    // Dette blir nyttig når vi vil oppdatere spillet for å få en smooth opplevelse.
    // Jeg sikter etter å kjøre spillet i 60 FPS
    Thread gameThread;
    public Player player = new Player(this, characterController);
    TileManager tileManager = new TileManager(this);
    // Bruker dette som et array med 5 plasser for NPCer/objekter. Vi kan vise disse fem i et slags inventory som
    // troféer for NPCer man har slått.
    public SuperNPC npc[] = new SuperNPC[5];
    public NPCplacer npcPlacer = new NPCplacer(this);
    public boolean inBattle;
    public final Rectangle battleArena;
    public StringBuilder battleMessages = new StringBuilder();
    int FPS = 60; // FPS - frames per second. Hvor ofte vi oppdaterer skjermen.


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Et grep for å forbedre rendering performance
        inBattle = false;
        battleArena = new Rectangle(250,150,300,300);
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
     * Kaller på update metoden fra Player klassen.
     */
    public void update(){

        player.update();
}

    /**
     * Metoden tegner tilesene, Player, og NPCen Badekamerat
     * @param graphics the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        // Viktig å tegne tiles før player siden vi vil tegne det lag-vis i den rekkefølgen
        tileManager.drawTile(graphics2D);
        // Tegner NPC
        // Looper igjennom arrayet vårt og sjekker om det finnes noe i arrayet, her finner vi kun Badekamerat.
        for(int i = 0; i < npc.length; i++) {
            // Sjekker not null for å slippe nullpointerexception
            if(npc[i] != null) {
                npc[i].drawNPC(graphics2D,this);
            }
        }
        // Tegner Player spriten vår
        player.drawPlayer(graphics2D);
        // Tegner et rektangel hvis inBattle = true, der vi viser kampforløpet og resultatet
        if (inBattle){
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setColor(new Color(0,0,0,150));
            g2d.fillRect(battleArena.x, battleArena.y, battleArena.width, battleArena.height);
            g2d.fillRect(battleArena.x, battleArena.y, battleArena.width, battleArena.height);

            //Tegner kampmeldingen i det fargede rektangelet
            Font font = new Font("Arial",Font.BOLD, 12);
            g2d.setFont(font);
            g2d.setColor(Color.white);

            String[] lines = battleMessages.toString().split("\n");

            int lineHeight = g2d.getFontMetrics().getHeight();
            int startY = battleArena.y + lineHeight;
            for (int i = 0; i < lines.length; i++){
                graphics2D.drawString(lines[i], battleArena.x +10, startY + i*lineHeight);
            }

        }
        graphics2D.dispose(); //"Disposes of this graphics context and releases any system resources that it is using."
}
    /**
     * Her starter vi Threaden vår.
     */
    public void startThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Vi vil kalle på denne metoden som utfører setNpc() før gamet starter.
     * Kaller derfor på denne i main klassen vår før gameThread, og tegner da Badekamerat.
     */
    public void setupGame(){
        npcPlacer.setNPC();
    }

    /**
     * Metode for å sjekke om Player har en interaction med Badekamerat NPCen.
     * Hvis Player går over Badekamerat, altså samme koordinater, starter vi en battle.
     */
    public void checkInteraction() {
        int playerRow = player.worldY / tileSize;
        int playerCol = player.worldX / tileSize;
        if (playerRow == 45 && playerCol == 8){
            startBattle();
        }
        else {
            inBattle = false;
        }
    }

    /**
     * Her gjenbruker jeg mye koden fra lab2(pokemon) for å håndtere kampen.
     * Resultatet av kampen tegnes i rektangelet vårt istedenfor å vise det i terminalen.
     */
    private void startBattle(){
        battleMessages.setLength(0); // Fjerner tidligere kampmeldinger fra rektangelet
        inBattle = true;
        System.out.println("Battle started");
        battleMessages.append("You disturbed the Badekamerat during his swim!\nBadekamerat has deployed Squirtle!\n");
        battleMessages.append("Go, Bulbasaur!\n");
        // Lager Bulbasaur og Squirtle for kampen
        Pokemon bulbasaur = new Pokemon("Bulbasaur", 45, 49,this); // Adjust healthPoints and strength as needed
        Pokemon squirtle = new Pokemon("Squirtle", 44, 48,this); // Adjust healthPoints and strength as needed

        // Start kampen helt til en Pokemon dør
        while (bulbasaur.isAlive() && squirtle.isAlive()) {
            // Bulbasaur angriper Squirtle
            bulbasaur.attack(squirtle);
            // Sjekk om Squirtle fortsatt lever etter Bulbasaur's angrep
            if (squirtle.isAlive()) {
                // Squirtle angriper Bulbasaur
                squirtle.attack(bulbasaur);
            }
        }
        // Vis resultatet av kampen
        if (!bulbasaur.isAlive()) {
            battleMessages.append("Bulbasaur fainted. You lose!" +
                    "\nYour punishment: a swim with Badekamerat!" +
                    " \nCheck out Echo Sports Club's swimming instagram\n" +
                    "for information about his swims!\n" +
                    "IG: bade_kameratene");
        } else {
            battleMessages.append("Squirtle fainted. You win!");
        }
    }
    }

