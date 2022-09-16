package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceREST implements ScoreService{

    @Autowired
    RestTemplate restTemplate;

    @Value("${remote.server.api}")
    private String url;

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url+"/score",score,Score.class);
    }

    @Override
    public List<Score> getBestScores(String game) {
        return Arrays.asList(restTemplate.getForEntity(url+"/score/"+game,Score[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset not supported via web.");

    }
}
