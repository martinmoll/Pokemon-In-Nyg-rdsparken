package InGameCharacters;

import main.GamePanel;
import main.CharacterController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Klassen og læringen av buffered reader er modifisert fra: https://youtu.be/wT9uNGzMEM4?si=P5n27Q3gv_kiNZRP
 */
public class Player extends Characters{
    GamePanel gamePanel;
    CharacterController characterController;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, CharacterController characterController){
        this.gamePanel = gamePanel;
        this.characterController = characterController;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2) ;
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Initialiserer standard verdiene til Player karakteren vår
     */
    public void setDefaultValues(){
        worldX = gamePanel.tileSize * 8;
        worldY = gamePanel.tileSize * 42 ;
        characterSpeed = 4;
        direction = "down";
    }

    /**
     * Metode som oppdaterer
     */
    public void update(){
        // Ved å ha dette ytterste if setningen, passer vi på at karakteren kun bytter bilde når vi faktisk beveger oss
        // Uten denne linjen bevegde karakteren seg når den stod stille siden koden for animasjonen blir kalt på,
        // uansett om man har trykket på en pil eller ikke, når update blir kalt, altså 60 ganger i sekundet.
        if(characterController.upPressed == true || characterController.downPressed == true
                || characterController.leftPressed == true || characterController.rightPressed == true) {
            if(characterController.upPressed){
                direction = "up";
                worldY -= characterSpeed;
            }
            else if(characterController.downPressed){
                direction = "down";
                worldY += characterSpeed;
            }
            else if(characterController.rightPressed){
                direction = "right";
                worldX += characterSpeed;
            }
            else if(characterController.leftPressed){
                direction = "left";
                worldX -= characterSpeed;
            }

            // Hver gang vi oppdaterer gamet(60 ganger i sekunder) legger vi til 1 i characterCounter
            // Når denne når over 15, sjekker vi characterNum og endrer denne så vi veksler på bildet som vises.
            // Jo høyere threshold på characterCounter vi velger, jo langsommere skjer animasjonen.
            characterCounter++;
            if(characterCounter > 15){
                if(characterNum == 1){
                    characterNum = 2;
                }
                else if(characterNum == 2){
                    characterNum = 1;
                }
                characterCounter = 0; //resetter counteren igjen
            }
        }

    }

    /**
     * Tegner spilleren med riktig bilde i henhold til retning spilleren beveger seg
     * @param graphics2D
     */
    public void drawPlayer(Graphics2D graphics2D){

        // Sjekker retningen vi går i
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if(characterNum == 1){
                    image = up1;
                }
                if (characterNum == 2){
                    image = up2;
                }
            }
            case "down" -> {
                if(characterNum == 1){
                    image = down1;
                }
                if (characterNum == 2){
                    image = down2;
                }
            }
            case "left" -> {
                if(characterNum == 1){
                    image = left1;
                }
                if (characterNum == 2){
                    image = left2;
                }
            }
            case "right" -> {
                if(characterNum == 1){
                    image = right1;
                }
                if (characterNum == 2){
                    image = right2;
                }
            }
        };
        graphics2D.drawImage(image, screenX, screenY,gamePanel.tileSize,gamePanel.tileSize,null);
    }
    
    /**
     * Henter ut alle 8 bilder som trengs for animasjonen av spilleren.
     * Bilder er lagret i res/Player folderen.
     * Gjør en sjekk med try/catch for feil ved innlasing av bilder.
     */
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/Player/player_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/Player/player_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/Player/player_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/Player/player_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/Player/player_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/Player/player_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/Player/player_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/Player/player_right_2.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        // Linje for å sjekke om den klarer å laste inn bildene
        System.out.println("Image loading started");
        System.out.println("Image loading ended.");
    }

}
