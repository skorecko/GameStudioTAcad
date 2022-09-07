package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    /**
     * Adds a new score record to database
     * @param score new score record
     */
    void addScore(Score score);

    List<Score> getBestScores(String game);

    void reset();
}
