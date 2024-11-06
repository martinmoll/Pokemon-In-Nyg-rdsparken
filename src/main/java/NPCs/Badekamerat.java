package NPCs;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * Klassen for NPCen kalt "badekamerat"
 */
public class Badekamerat extends SuperNPC {
    public Badekamerat(){
        name = "Badekamerat";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("res/NPC/Badekamerat_svømmende_transbakgrunn.png")));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
