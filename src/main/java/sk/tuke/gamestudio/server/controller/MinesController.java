package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.game.mines.core.Tile;
import sk.tuke.gamestudio.game.mines.core.TileState;

import java.util.Date;

@Controller
@RequestMapping("/mines")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController {

    private Field mineField = new Field(9,9,3);

    @RequestMapping
    public String processUserInput(@RequestParam(required = false) Integer row,
                                   @RequestParam(required = false) Integer column){

        if(row!=null && column!=null){
            mineField.openTile(row.intValue(),column.intValue());
        }
        return("mines");
    }

    public String getCurrentTime(){
        return new Date().toString();
    }

    public  String getHtmlField(){
        StringBuilder sb = new StringBuilder();

        sb.append("<table class='minefield'> \n");

        int rowCount=mineField.getRowCount();
        int colCount=mineField.getColumnCount();

        for(int row=0;row<rowCount;row++){
            sb.append("<tr>");
            for(int col=0;col<colCount;col++){
                Tile tile =mineField.getTile(row,col);
                sb.append("<td class='"+getTileClass(tile)+"'>");

                if(tile.getState() != TileState.OPEN){
                    sb.append("<a href='/mines?row="+row+"&column="+col+"'>");
                }

                sb.append("<span>"+getTileText(tile)+"</span>");

                if(tile.getState() != TileState.OPEN){
                    sb.append("</a>");
                }
                sb.append("</td>\n");
            }
            sb.append("</tr> \n");
        }
        sb.append("</table> \n");
        return sb.toString();
    }

    private String getTileClass(Tile tile) {
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


    private String getTileText(Tile tile) {
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


}
