package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getBestScores(String game) {

        final String STATEMENT_BEST_SCORES = "select sc from Score sc where sc.game=:myGame  order by sc.points desc";

        return entityManager
                .createQuery(STATEMENT_BEST_SCORES)
                .setParameter("myGame",game)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public void reset() {
        final String STATEMENT_RESET = "DELETE FROM score";
        entityManager.createNativeQuery(STATEMENT_RESET).executeUpdate();
    }
}
