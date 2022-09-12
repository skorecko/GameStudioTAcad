package sk.tuke.gamestudio.service;

public interface RatingService {

    void setRating(sk.tuke.gamestudio.entity.Rating rating);

    int getAverageRating(String game);

    int getRating(String game,String username);

    void reset();
}
