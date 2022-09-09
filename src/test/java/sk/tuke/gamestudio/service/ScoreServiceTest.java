package sk.tuke.gamestudio.service;

import org.junit.Test;
import static org.junit.Assert.*;

import sk.tuke.gamestudio.entity.Score;

import java.util.Date;




public class ScoreServiceTest {

    //private final ScoreService scoreService = new ScoreServiceJdbc();
    private final ScoreService scoreService = new ScoreServiceFile();


    @Test
    public void testReset(){
        scoreService.addScore(new Score("mines","Feri",123,new Date()));


        assertTrue(scoreService.getBestScores("mines").size() > 0);

        scoreService.reset();
        assertEquals(0,scoreService.getBestScores("mines").size());
    }

    @Test
    public void testAddScore(){
        scoreService.reset();
        var date = new Date();
        final String gameName="mines";

        scoreService.addScore(new Score(gameName,"Jeno",123,date));

        var scores = scoreService.getBestScores(gameName);

        assertEquals(1, scores.size());

        assertEquals(gameName,scores.get(0).getGame());
        assertEquals("Jeno",scores.get(0).getUsername());
        assertEquals(123,scores.get(0).getPoints());
        assertEquals(date,scores.get(0).getPlayedAt());
    }


    @Test
    public void testGetBestScores(){
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("mines", "Palo", 330, date));
        scoreService.addScore(new Score("mines", "Peto", 140, date));
        scoreService.addScore(new Score("mines", "Katka", 150, date));
        scoreService.addScore(new Score("mines", "Zuzka", 290, date));
        scoreService.addScore(new Score("mines", "Jergus", 100, date));
        scoreService.addScore(new Score("mines", "Fero", 10, date));
        scoreService.addScore(new Score("tiles", "Jana", 500, date));

        var scores = scoreService.getBestScores("mines");
        assertEquals(5, scores.size());

        assertEquals("mines",scores.get(0).getGame());
        assertEquals("Palo",scores.get(0).getUsername());
        assertEquals(330,scores.get(0).getPoints());
        assertEquals(date,scores.get(0).getPlayedAt());

        assertEquals("mines",scores.get(1).getGame());
        assertEquals("Zuzka",scores.get(1).getUsername());
        assertEquals(290,scores.get(1).getPoints());
        assertEquals(date,scores.get(1).getPlayedAt());

        assertEquals("mines",scores.get(2).getGame());
        assertEquals("Katka",scores.get(2).getUsername());
        assertEquals(150,scores.get(2).getPoints());
        assertEquals(date,scores.get(2).getPlayedAt());

        assertEquals("mines",scores.get(3).getGame());
        assertEquals("Peto",scores.get(3).getUsername());
        assertEquals(140,scores.get(3).getPoints());
        assertEquals(date,scores.get(3).getPlayedAt());

        assertEquals("mines",scores.get(4).getGame());
        assertEquals("Jergus",scores.get(4).getUsername());
        assertEquals(100,scores.get(4).getPoints());
        assertEquals(date,scores.get(4).getPlayedAt());

    }



}
