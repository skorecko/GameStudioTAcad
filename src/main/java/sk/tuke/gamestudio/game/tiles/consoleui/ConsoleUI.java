package sk.tuke.gamestudio.game.tiles.consoleui;

import sk.tuke.gamestudio.game.tiles.core.Field;

import java.util.Scanner;

public class ConsoleUI {
    private final Field field;

    private Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {
        do {
            printField();
            processInput();
        } while (!field.isSolved());
        printField();
        System.out.println("Solved");
    }

    private void printField() {
        for (var row = 0; row < field.getRowCount(); row++) {
            for (var column = 0; column < field.getColumnCount(); column++) {
                var tile = field.getTile(row, column);
                System.out.print(" ");
                if (tile == null)
                    System.out.print("-");
                else
                    System.out.printf("%2d", tile.getValue());
            }
            System.out.println();
        }
    }

    private void processInput() {
        System.out.println("Enter tile number to move: ");
        var line = scanner.nextLine().toUpperCase().trim();
        if ("X".equals(line))
            System.exit(0);
        try {
            var tile = Integer.parseInt(line);
            field.move(tile);
        } catch (NumberFormatException e) {
            System.err.println("Invalid tile!");
        }
    }
}
