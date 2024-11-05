package main;
import java.util.Random;
import java.util.Scanner;

/**
 * Klassen er tatt fra Lab 2(pokemon) og modifisert for å passe inn til spillet
 */
public class Pokemon {

    static Scanner sc = new Scanner(System.in);
    String name;
    Integer healthPoints;
    Integer maxHealthPoints;
    Integer strength;
    private GamePanel gamePanel;

    public Pokemon(String name, int healthPoints, int strength, GamePanel gamePanel){
        this.name = name;
        this.healthPoints = healthPoints;
        this.maxHealthPoints = healthPoints;
        this.strength = strength;
        this.gamePanel = gamePanel;
    }


    /**
     * Get current health points of pokémon
     * @return current HP of pokémon
     */
    int getCurrentHP() {
        return healthPoints;
    }

    /**
     * Get maximum health points of pokémon
     * @return max HP of pokémon
     */
    int getMaxHP() {
        return maxHealthPoints;
    }

    /**
     * Check if the pokémon is alive.
     * A pokemon is alive if current HP is higher than 0
     * @return true if current HP > 0, false if not
     */
    boolean isAlive() {
        if (getCurrentHP() > 0){
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Damage the pokémon. This method reduces the number of
     * health points the pokémon has by <code>damageTaken</code>.
     * If <code>damageTaken</code> is higher than the number of current
     * health points then set current HP to 0.
     *
     * It should not be possible to deal negative damage, i.e. increase the number of health points.
     *
     * The method should print how much HP the pokemon is left with.
     *
     * @param damageTaken
     */
    void damage(int damageTaken) {
        if (damageTaken <= 0){
            return;
        }
        else {
            healthPoints -= damageTaken;
            if (healthPoints < 0) {
                healthPoints = 0;
            }
            gamePanel.battleMessages.append(name + " takes " + damageTaken + " damage and is left with " + healthPoints + "/" + maxHealthPoints + " HP\n");
        }
    }

    /**
     * Attack another pokémon. The method conducts an attack by <code>this</code>
     * on <code>target</code>. Calculate the damage using the pokémons strength
     * and a random element. Reduce <code>target</code>s health.
     *
     * If <code>target</code> has 0 HP then print that it was defeated.
     *
     * @param target pokémon that is being attacked
     */
    void attack(Pokemon target) {
        Random rand = new Random();
        int damageInflincted = (int) (rand.nextInt(this.strength+1));
        gamePanel.battleMessages.append(this.name + " attacks " + target.name + ".\n");

        if (target.isAlive()) {
            target.damage(damageInflincted);
            if (target.isAlive() == false) {
                gamePanel.battleMessages.append(target.name + " is defeated by " + this.name + "\n");
            }
        }

    }

    @Override
    public String toString() {
        return (name + " HP:" + " (" + healthPoints + "/" + maxHealthPoints + ")" + " STR: " + strength);
    }

}

