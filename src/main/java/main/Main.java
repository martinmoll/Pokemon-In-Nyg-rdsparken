package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Lager selve vinduet
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Pokemon: Nygårdsparken Edition");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        //tegner vinduet på midten av skjermen
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setupGame();
        gamePanel.startThread();
    }
}