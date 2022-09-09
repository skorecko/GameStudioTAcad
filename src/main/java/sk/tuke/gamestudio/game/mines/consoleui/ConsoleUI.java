package sk.tuke.gamestudio.game.mines.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.game.mines.core.FieldState;
import sk.tuke.gamestudio.game.mines.core.Tile;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJdbc;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private Field field;

    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private ScoreService scoreService;
    //private ScoreService scoreService = new ScoreServiceJdbc();


    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {



        do {
            printField();
            processInput();
        } while (field.getState() == FieldState.PLAYING);
        printField();
        System.out.print(field.getState());

        //vypis score a zapis score do databazy
        if(field.getState()==FieldState.SOLVED){
            System.out.printf(" Your score: %d\n", field.getScore());
            scoreService.addScore(
                    new Score("mines",
                            System.getProperty("user.name"),
                            field.getScore(),
                            new Date())
            );
        }

        //vypis bestScores z databazy
        List<Score> bestScores = scoreService.getBestScores("mines");
        System.out.println("Best scores (name, points, played at):");
        for (var score:bestScores) {
            System.out.printf("%s %d %tD\n",
                    score.getUsername(),score.getPoints(),score.getPlayedAt());
        }

    }

    private void processInput() {
        System.out.print("Enter input: ");
        var line = scanner.nextLine().toUpperCase().trim();
        if ("X".equals(line))
            System.exit(0);
        var pattern = Pattern.compile("([OM])([A-I])([1-9])");
        var matcher = pattern.matcher(line);
        if (matcher.matches()) {
            var row = matcher.group(2).charAt(0) - 'A';
            var column = Integer.parseInt(matcher.group(3)) - 1;
            if("O".equals(matcher.group(1)))
                field.openTile(row, column);
            else
                field.markTile(row, column);
        } else {
            System.err.println("Invalid input!");
        }
    }

    private void printField() {
        printFieldHeader();
        printFieldBody();
    }

    private void printFieldBody() {
        for (var row = 0; row < field.getRowCount(); row++) {
            System.out.print((char) ('A' + row));
            for (var column = 0; column < field.getColumnCount(); column++) {
                var tile = field.getTile(row, column);
                System.out.print(" ");
                printTile(tile);
            }
            System.out.println();
        }
    }

    private void printFieldHeader() {
        System.out.print(" ");
        for (var column = 0; column < field.getColumnCount(); column++) {
            System.out.print(" ");
            System.out.print(column + 1);
        }
        System.out.println();
    }

    private void printTile(Tile tile) {
        switch (tile.getState()) {
            case CLOSED:
                System.out.print("-");
                break;
            case MARKED:
                System.out.print("M");
                break;
            case OPEN:
                if (tile instanceof Clue)
                    System.out.print(((Clue) tile).getValue());
                else
                    System.out.print("X");
        }
    }


}
