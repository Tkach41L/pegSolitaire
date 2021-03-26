package main.java.sk.tuke.gamestudio.game.consoleUI;

import main.java.sk.tuke.gamestudio.game.core.Field;
import main.java.sk.tuke.gamestudio.game.core.Tile;
import main.java.sk.tuke.gamestudio.entity.Score;
import main.java.sk.tuke.gamestudio.service.*;
import main.java.sk.tuke.gamestudio.entity.Comment;
import main.java.sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.lang.String;
import java.util.concurrent.ThreadLocalRandom;

public class ConsoleUI {
    //colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    private final Field field;
    private final ScoreService scoreService = new ScoreServiceJDBC();
    private final CommentService commentService = new CommentServiceJDBC();
    private int score = 0;
    private int checker = 1;
    private int randomNum = ThreadLocalRandom.current().nextInt(6, 12 + 1);
    private String name;
    private final Scanner scanner = new Scanner(System.in);
    public static final String GAME_NAME = "peg solitaire";

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void welcome(){
        createName();
        System.out.printf(ANSI_GREEN);
        charWait("H");
        charWait("e");
        charWait("l");
        charWait("l");
        charWait("o");
        charWait(", ");
        System.out.print(name);
        textWait("! This is Peg Solitaire");
        textWait("The game consists of a playing area covered with balls,");
        textWait("with the exception of the initially empty space in the middle of the area.");
        textWait("The player's task is to clear the playing area using valid moves ");
        textWait("so that only one ball remains in the middle of the area.");
        textWait("Tile numbering control and direction 'wasd' (example 4 6 w)");
        textWait("For more information write 'help'");
        System.out.printf(ANSI_RESET);
    }

    public void play() {
        boolean won = false;
        while (!field.checkLose()) {
            if(checker == 1){
                while(!scanner.nextLine().equals("help"));
                System.out.printf(ANSI_GREEN);
                textWait("You have a few commands for game:");
                textWait("start - for a start the new game");
                textWait("exit - leave from the game without saving");
                textWait("leave - leave from the game with saving");
                textWait("top10 - check 10 the best players");
                textWait("top - check all players");
                textWait("comment - write a feedback");
                textWait("list - check all comments");
                textWait("list10 - check 10 lasts comments");
                textWait("resetList - reset all comments");
                textWait("restart - restart the game");
                textWait("resetTop - reset stats");
                textWait("Good luck!");
                wait(500);
                System.out.printf(ANSI_RESET);
                while(!scanner.nextLine().equals("start"));
                checker = 0;
            }
            renderField();
            System.out.printf(ANSI_YELLOW);
            System.out.printf("Score: %d\n", this.score);
            System.out.printf(ANSI_RESET);
            inputHandler();

            if (field.checkWin()) {
                won = true;
                break;
            }
        }
        if (won) {
            scoreService.addScore(new Score(GAME_NAME, name, score, new Date()));
            System.out.printf(ANSI_GREEN);
            System.out.println("Congratulations, you won!");
            System.exit(0);
            System.out.printf(ANSI_RESET);
            }
        else {
            scoreService.addScore(new Score(GAME_NAME, name, score, new Date()));
            System.out.printf(ANSI_GREEN);
            System.out.println("Oh, you lost :(");
            System.out.print("Do you wish to start a new game (Y/N)?");
            tryAgain();
            System.out.printf(ANSI_RESET);
        }
    }

    private void renderField() {
        Tile[][] tiles = this.field.getTiles();
        System.out.printf(ANSI_PURPLE);
        System.out.println("  1 2 3 4 5 6 7");
        System.out.printf(ANSI_RESET);
        for (int i = 0; i < 7; i++) {
            System.out.printf(ANSI_PURPLE);
            System.out.printf("%d ", i + 1);
            System.out.printf(ANSI_RESET);
            for (int j = 0; j < 7; j++) {
                if (tiles[i][j] == Tile.FIGURE) {
                    System.out.printf(ANSI_CYAN);
                    System.out.printf("X ");
                    System.out.printf(ANSI_RESET);
                } else if (tiles[i][j] == Tile.EMPTY) {
                    System.out.printf("0 ");
                    System.out.printf(ANSI_RESET);
                } else {
                    System.out.printf("  ");
                }
            }
            System.out.println();
        }

    }


    private boolean handleCommands(String line) {
        if (line.equals("exit")) {
            exitGame();
        } else if(line.equals("leave")){
            scoreService.addScore(new Score(GAME_NAME, name, score, new Date()));
            exitGame();
        } else if (line.equals("top")) {
            List<Score> scores = scoreService.getTopScores(GAME_NAME);
            for (Score score : scores) {
                System.out.printf(ANSI_GREEN);
                System.out.printf("%s %d\n", score.getPlayer(), score.getPoints());
                System.out.printf(ANSI_RESET);
            }
        }else if(line.equals("top10")) {
            List<Score> scores = scoreService.getTopScores(GAME_NAME);
            scores = scores.subList(0, Math.min(10, scores.size()));
            for (Score score : scores) {
                System.out.printf(ANSI_GREEN);
                System.out.printf("%s %d\n", score.getPlayer(), score.getPoints());
                System.out.printf(ANSI_RESET);
            }
        } else if (line.equals("comment")) {
            System.out.printf(ANSI_GREEN);
            System.out.println("You can write comment, it's very important for us :)");
            writeComment();
            System.out.println("Thank you!");
            wait(500);
            System.out.printf(ANSI_RESET);
        }
        else if (line.equals("list")) {
            List<Comment> comments = commentService.getComments(GAME_NAME);
            for (Comment comment : comments) {
                System.out.printf(ANSI_GREEN);
                System.out.printf("%s %s\n", comment.getGame(), comment.getComment());
                System.out.printf(ANSI_RESET);
            }
        }
        else if(line.equals("resetList")){
            resetComments();
        }
        else if (line.equals("list10")) {
            List<Comment> comments = commentService.getComments(GAME_NAME);
            comments = comments.subList(0, Math.min(10, comments.size()));
            for (Comment comment : comments) {
                System.out.printf(ANSI_GREEN);
                System.out.printf("%s %s\n", comment.getGame(), comment.getComment());
                System.out.printf(ANSI_RESET);
            }
        }
        else if (line.equals("restart")) {
            System.out.printf(ANSI_GREEN);
            restartGame();
            System.out.printf(ANSI_RESET);
        }
        else if(line.equals("resetTop")){
            resetScore();
        }
        else if (line.equals("help")) {
            System.out.printf(ANSI_GREEN);
            textWait("You have a few commands for game:");
            textWait("exit - leave from the game without saving");
            textWait("leave - leave from the game with saving");
            textWait("top10 - check 10 the best players");
            textWait("top - check all players");
            textWait("comment - write a feedback");
            textWait("list - check all comments");
            textWait("list10 - check 10 lasts comments");
            textWait("resetList - reset all comments");
            textWait("restart - restart the game");
            textWait("resetTop - reset stats");
            textWait("Good luck!");
            wait(500);
            System.out.printf(ANSI_RESET);
        }
        else{
            return false;
        }
        return true;
    }

    private void inputHandler() {
        String line = scanner.nextLine().trim();
        if (line.matches("^[1-7] [1-7] [wasd]$")) {
            Scanner scanner = new Scanner(line);
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            char move = scanner.nextLine().charAt(1);

            if (field.makeMove(x, y, move)) {
                this.score += randomNum;
            }
        } else {
            if (!handleCommands(line)) {
                System.out.printf(ANSI_RED);
                System.out.println("Incorrect input");
                System.out.printf(ANSI_RESET);
            }
        }
    }

    private void tryAgain(){
        System.out.print("Do you wish to start a new game (Y/N)?");
        String tryAgain = scanner.nextLine().toUpperCase();
        if (tryAgain.matches("^[YN]$")) {
            Scanner scanner = new Scanner(tryAgain);
            String finish = scanner.nextLine();
            if ("Y".equals(tryAgain)) {
                field.generate();
                this.score = 0;
            } else if("N".equals(tryAgain)) {
                System.exit(0);
            }
        }
    }

    private void restartGame(){
        System.out.print("Are you sure? (Y/N)?");
        String tryAgain = scanner.nextLine().toUpperCase();
        if (tryAgain.matches("^[YN]$")) {
            Scanner scanner = new Scanner(tryAgain);
            String finish = scanner.nextLine();
            if ("Y".equals(tryAgain)) {
                field.generate();
                this.score = 0;
            }
        }
    }

    private void exitGame(){
        System.out.printf(ANSI_GREEN);
        System.out.println("So hard? :)");
        System.out.println("Good bye");
        System.out.printf(ANSI_RESET);
        System.exit(0);
    }

    private void resetScore() {
        scoreService.reset();
    }

    private void resetComments() {
        commentService.reset();
    }

    private void writeComment(){
        String comment = scanner.nextLine().trim();
        commentService.addComment(new Comment(name,GAME_NAME, comment, new Date()));
    }

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public void textWait(String string){
        wait(500);
        System.out.println(string);
    }

    public void charWait(String string){
        wait(250);
        System.out.print(string);
    }

    public void createName(){
        String name;
        System.out.printf(ANSI_GREEN);
        System.out.println("Before I start, I need to know your name");
        name = this.scanner.nextLine();
        this.name = name;
        System.out.printf(ANSI_RESET);
    }
}
