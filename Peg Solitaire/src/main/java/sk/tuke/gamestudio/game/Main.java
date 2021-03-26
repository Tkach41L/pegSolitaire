package main.java.sk.tuke.gamestudio.game;

import main.java.sk.tuke.gamestudio.game.consoleUI.ConsoleUI;
import main.java.sk.tuke.gamestudio.game.core.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field();
        ConsoleUI consoleUI = new ConsoleUI(field);
        consoleUI.welcome();
        consoleUI.play();
    }
}
