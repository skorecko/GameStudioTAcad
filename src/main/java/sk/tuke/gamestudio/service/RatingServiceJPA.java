package sk.tuke.gamestudio.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import sk.tuke.gamestudio.entity.Rating;

@Transactional
public class RatingServiceJPA implements RatingService {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        final String STATEMENT_GET_RATING = "select rt from Rating rt where rt.game=:myGame and rt.username=:myUsername";
        Rating rating2write = null;

        try{
            rating2write = (Rating) entityManager.createQuery(STATEMENT_GET_RATING)
                    .setParameter("myGame", rating.getGame())
                    .setParameter("myUsername", rating.getUsername())
                    .getSingleResult();
            rating2write.setRating(rating.getRating());
            rating2write.setRatedAt(rating.getRatedAt());
        }catch(NoResultException e){

            //rating2write=new Rating(rating.getGame(),rating.getUsername(), rating.getRating(),rating.getRatedAt());
            //entityManager.persist(rating2write);

            entityManager.persist(rating);
        }
    }

    @Override
    public int getAverageRating(String game) {

        final String STATEMENT_GET_AVG_RATING = "select AVG(rt.rating) from Rating rt where rt.game=:myGame";

        var rating=entityManager
            .createQuery(STATEMENT_GET_AVG_RATING)
            .setParameter("myGame",game)
            .getSingleResult();
        if(rating==null) {
            return 0;
        }else{
            return ((Number)rating).intValue();
        }

    }

    @Override
    public int getRating(String game, String username) {
        final String STATEMENT_GET_RATING = "select rt from Rating rt where rt.game=:myGame and rt.username=:myUsername";
        Rating rating = null;
        try{
            rating = (Rating) entityManager.createQuery(STATEMENT_GET_RATING)
                    .setParameter("myGame", game)
                    .setParameter("myUsername", username)
                    .getSingleResult();
            return rating.getRating();
        }catch(NoResultException e){
            return 0;
        }
    }

    @Override
    public void reset() {
        final String STATEMENT_RESET = "DELETE FROM rating";
        entityManager.createNativeQuery(STATEMENT_RESET).executeUpdate();

    }
}
