package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.core.Field;
import sk.tuke.gamestudio.game.core.Tile;
import sk.tuke.gamestudio.game.core.tileWeb;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/pegsolitaire")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PegsolitaireController {
    @Autowired
    private UserController userController;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    private Field field = new Field();
    int pickX, pickY;
    boolean pickStage = true;
    boolean gameLost = false;
    boolean gameWon = false;
    int score = 0;


    @RequestMapping
    public String pegsolitaire( @RequestParam(required = false) String row,
                        @RequestParam(required = false) String column,
                        Model model) {

        if(gameLost || gameWon){
            return "pegsolitaire";
        }

        if(pickStage && row != null && column != null){
            System.out.println("PICK");
            pickX = Integer.parseInt(column);
            pickY = Integer.parseInt(row);
            if(field.getTile(pickY, pickX) == Tile.FIGURE){
                pickStage = false;
            }
        }
        else if(row != null && column != null){
            int newX = Integer.parseInt(column);
            int newY = Integer.parseInt(row);

            int checkX = Math.abs(newX - pickX);
            int checkY = Math.abs(newY - pickY);
            if( (checkX == 2 && checkY == 0) || (checkX == 0 && checkY == 2)){
                boolean check = field.makeWebMove(pickX, pickY, newX, newY);
                score+=6;
                field.setScore(score);
                System.out.println("CHECK "+ check);
            }
            pickStage = true;
            System.out.printf("END ELSE %d %d\n", checkX, checkY);
        }
        fillModel(model);

        if(field.checkLose()){
            gameLost = true;
        }
        else if(field.checkWin()){
            gameWon = true;
        }

        return "pegsolitaire";
    }

    @RequestMapping("/new")
    public String newGame(){
        field.setScore(0);
        field = new Field();
        gameWon = false;
        gameLost = false;
        pickStage = true;
        return "redirect:/pegsolitaire";
    }
    @RequestMapping("/end")
    public String endGame(){
        if(field.getScore() != 0){
            scoreService.addScore(new Score("peg_solitaire",userController.getLoggedUser().getLogin(),field.getScore(),new Date()));
        }
        newGame();
//        field.setScore(0);
        return "redirect:/pegsolitaire";
    }

    private void fillModel(Model model) {
        model.addAttribute("Topscores", scoreService.getTopScores("peg_solitaire"));
       model.addAttribute("Topcomments",commentService.getComments("peg_solitaire"));
    }

    @RequestMapping("/nComment")
    public String getNewComment(String comment){
        if(comment != null && userController.getLoggedUser() != null){
            try {
                commentService.addComment(new Comment("peg_solitaire", userController.getLoggedUser().getLogin(), comment, new Date()));
            } catch (CommentException e){
                e.printStackTrace();
            }
        }
        return "redirect:/pegsolitaire";
    }
    public int getScore(){
        return field.getScore();
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");

        for (int row = 0; row < 7; row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < 7; column++) {
                Tile tile = field.getTile(row, column);
                sb.append("<td>");
                sb.append(String.format("<a href='/pegsolitaire?row=%d&column=%d' class='" + getClass(tile) + "'></a>\n", row, column));
//                sb.append("<img src='/images/" + getImageName(tile) + ".png'>");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("</table>\n");
        return sb.toString();
    }

    public String getState(){
        return gameLost ? "Game Lost" : gameWon ? "Game Won" : "Do your best!";
    }

    public String getCurrentAction(){
        return pickStage ? "Pick figure" : "Pick where to move";
    }

    private String getClass(Tile tile) {
        return switch (tile) {
            case EMPTY -> "empty-tile";
            case FIGURE -> "fig-tile";
            case NONE -> "none-tile";
        };
    }

}
