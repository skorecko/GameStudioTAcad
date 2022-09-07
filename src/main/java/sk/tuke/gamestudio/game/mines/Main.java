package sk.tuke.gamestudio.game.mines;

import sk.tuke.gamestudio.game.mines.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.mines.core.Field;

public class Main {
    public static void main(String[] args) {
        var field = new Field(9, 9, 1);
        var ui = new ConsoleUI(field);
        ui.play();
    }
}
