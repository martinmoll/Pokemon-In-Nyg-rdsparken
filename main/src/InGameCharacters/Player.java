package InGameCharacters;

import main.GamePanel;
import main.CharacterController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
     *
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
    public void drawPlayer(Graphics2D graphics2D){
//        graphics2D.setColor(Color.white);
//        graphics2D.fillRect(characterX,characterY, gamePanel.tileSize,gamePanel.tileSize);

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
    public void getPlayerImage(){
        // her laster vi inn bildene for Player characteren vår
        // Alle bildene er tatt fra denne linken: https://www.deviantart.com/streakofsprites/art/Ash-Ketchum-Sprite-Set-435950341
        // All ære går til de som lagde bildene:
//        Intro Walking Sprite Base: Otamago, lordindy
//        In-Battle Sprite Base: akuma-tsubasa
//        Mugshot Base: sketchfox7, akuma-tsubasa
//        Backsprite base: alzatia
//        OW's Base: Metapod23
//        OW's (Hatless) Reference: lordindy
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/player_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/player_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/player_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/player_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/player_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/player_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/player_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Player/player_right_2.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        // Sjekke om den klarer loade
        System.out.println("Image loading started");
        System.out.println("Image loading ended.");
    }

}
