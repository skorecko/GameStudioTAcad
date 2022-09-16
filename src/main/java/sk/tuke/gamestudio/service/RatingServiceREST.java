package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class RatingServiceREST implements RatingService {


    @Autowired
    RestTemplate restTemplate;

    @Value("${remote.server.api}")
    private String url;

    @Override
    public void setRating(Rating rating) {
        restTemplate
                .postForEntity(url+"/rating",
                        rating,
                        Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
        Rating rating =
        restTemplate.getForEntity(url+"/rating/"+game,Rating.class).getBody();
        return ((Number)rating.getRating()).intValue();

    }

    @Override
    public int getRating(String game, String username) {
        Rating rating = restTemplate.getForEntity(url+"/rating/"+game+"/"+username,Rating.class).getBody();
        return ((Number)rating.getRating()).intValue();
    }

    @Override
    public void reset() {
    }
}
