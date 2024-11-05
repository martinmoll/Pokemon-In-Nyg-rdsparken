package InGameCharacters;

import java.awt.image.BufferedImage;

/**
 * Dette er super-klassen til alle mulige karakterer vi lager.
 * Her lagrer vi alle variablene vi skal bruke videre for karakterene hvis vi vil legge til flere enn bare én.
 */
public class Characters {

    public int worldX, worldY; // X og Y plassering i hele verdenen, altså det totale mappet som nå er 50x50.
    public int characterSpeed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    // Bruker characterCounter og characterNum for å muliggjøre bruk av begge bildene i samme retning
    // Da kan de veksle mellom versjon 1 og 2 av samme "bilde retning", og vi får dermed en gå animasjon.
    public int characterCounter = 0;
    public int characterNum = 1;

}
