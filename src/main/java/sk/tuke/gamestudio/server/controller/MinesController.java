package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.mines.core.*;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/mines")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController {

    private boolean marking=false;

    private Field mineField = null;

    @Autowired
    private UserController userController;

    @Autowired
    private ScoreService scoreService;

    private boolean isPlaying = true;


    @RequestMapping
    public String processUserInput(@RequestParam(required = false) Integer row,
                                   @RequestParam(required = false) Integer column,
                                   Model model){
        startOrUpdateGame(row,column);

        prepareModel(model);

        return("mines");
    }

    @RequestMapping("/mark")
    public String changeMode(Model model){
        changeGameMode();
        prepareModel(model);
        return("mines");
    }

    @RequestMapping("/new")
    public String newGame(Model model){
        startNewGame();
        prepareModel(model);
        return("mines");
    }

    @RequestMapping("/asynch")
    public String loadInAsynchMode(){
        startOrUpdateGame(null, null);
        return("minesAsynch");
    }

    @RequestMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field processUserInputJson(@RequestParam(required = false) Integer row,
                                      @RequestParam(required = false) Integer column){
        startOrUpdateGame(row,column);
        this.mineField.setMarking(this.marking);
        return this.mineField;
    }

    @RequestMapping(value = "/jsonmark", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field changeModeJson(){
        changeGameMode();
        this.mineField.setMarking(this.marking);
        return this.mineField;
    }

    @RequestMapping(value = "/jsonnew", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field newGameJson(){
        startNewGame();
        this.mineField.setMarking(this.marking);
        return this.mineField;
    }


    private void changeGameMode(){
        if(this.mineField.getState()==FieldState.PLAYING){
            this.marking=!this.marking;
        }
    }

    private void startNewGame(){
        this.isPlaying=true;
        this.marking=false;
        this.mineField = new Field(9,9,3);
    }

    private void startOrUpdateGame(Integer row, Integer column){
        if(this.mineField==null){
            startNewGame();
        }
        if(row!=null && column!=null){

            if(mineField.getState()==FieldState.PLAYING) {
                if (marking) {
                    mineField.markTile(row.intValue(), column.intValue());
                } else {
                    mineField.openTile(row.intValue(), column.intValue());
                }
            }

        }

        if(this.mineField.getState()!=FieldState.PLAYING && this.isPlaying){
            this.isPlaying=false;

            if(this.userController.isLogged() && this.mineField.getState()==FieldState.SOLVED){
                Score score = new Score("mines",
                        this.userController.getLoggedUser(),
                        this.mineField.getScore(),
                        new Date());
                this.scoreService.addScore(score);
            }
        }



    }

    private void prepareModel(Model model){
        model.addAttribute("marking",this.marking);
        model.addAttribute("gameStatus",getGameStatusMessage());
        model.addAttribute("mineFieldTiles",this.mineField.getTiles());
        model.addAttribute("isPlaying",this.mineField.getState()==FieldState.PLAYING);
        model.addAttribute("bestScores",this.scoreService.getBestScores("mines"));
    }

//    public String getCurrentTime(){
//        return new Date().toString();
//    }

    public boolean isMarking() {
        return this.marking;
    }
    public  String getHtmlField(){
        StringBuilder sb = new StringBuilder();

        sb.append("<table class='minefield'> \n");

        int rowCount=this.mineField.getRowCount();
        int colCount=this.mineField.getColumnCount();

        for(int row=0;row<rowCount;row++){
            sb.append("<tr>");
            for(int col=0;col<colCount;col++){
                Tile tile =this.mineField.getTile(row,col);
                sb.append("<td class='"+getTileClass(tile)+"'>");

                if(tile.getState() != TileState.OPEN && this.mineField.getState()==FieldState.PLAYING){
                    sb.append("<a href='/mines?row="+row+"&column="+col+"'>");
                }

                sb.append("<span>"+getTileText(tile)+"</span>");

                if(tile.getState() != TileState.OPEN  && this.mineField.getState()==FieldState.PLAYING){
                    sb.append("</a>");
                }
                sb.append("</td>\n");
            }
            sb.append("</tr> \n");
        }
        sb.append("</table> \n");
        return sb.toString();

    }

    //private String getTileClass(Tile tile) {
    public String getTileClass(Tile tile) {
        switch (tile.getState()) {
            case OPEN:
                if (tile instanceof Clue)
                    return "open" + ((Clue) tile).getValue();
                else
                    return "mine";
            case CLOSED:
                return "closed";
            case MARKED:
                return "marked";
            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }


    //private String getTileText(Tile tile) {
    public String getTileText(Tile tile) {
        switch (tile.getState()) {
            case CLOSED:
                return "-";
            case MARKED:
                return "M";
            case OPEN:
                if (tile instanceof Clue) {
                    return String.valueOf(((Clue) tile).getValue());
                } else {
                    return "X";
                }
            default:
                throw new IllegalArgumentException("Unsupported tile state " + tile.getState());
        }
    }

    public String getGameStatusMessage(){
        String gameStatus="";
        FieldState gameState= this.mineField.getState();
        if(gameState== FieldState.FAILED){
            gameStatus="You failed.";
        }else if(gameState== FieldState.SOLVED){
            gameStatus="You won (score: "+this.mineField.getScore()+")";
        }else{
            gameStatus="Playing - ";
            if(this.marking){
                gameStatus+="marking";
            }else{
                gameStatus+="opening";
            }
        }
        return gameStatus;
    }



}
