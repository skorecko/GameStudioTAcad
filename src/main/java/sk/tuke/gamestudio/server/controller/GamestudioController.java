package sk.tuke.gamestudio.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GamestudioController {

    @RequestMapping("/gamestudio")
    public String mainPage(){
        return "gamestudio";
    }
}
