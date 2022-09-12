package sk.tuke.gamestudio;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

import java.util.Date;

public class PlaygroundJPA {

    @Autowired
    private RatingService ratingService;
    public void play(){
        System.out.println("Opening PlaygroundJPA");
        ratingService.reset();
        Rating rating = new Rating("mines","Stevo",4,new Date());
        Rating rating2 = new Rating("mines","Palo",1,new Date());
        ratingService.setRating(rating);

        ratingService.setRating(rating2);

        System.out.printf("existing rating = %d %n",ratingService.getRating("mines","Stevo"));
        System.out.printf("non-existing rating = %d  %n",ratingService.getRating("mines","Peto"));

            System.out.printf("Avg rating mines = %d  %n",ratingService.getAverageRating("mines"));
        System.out.printf("Avg rating pexeso = %d  %n",ratingService.getAverageRating("pexeso"));



        //ratingService.setRating(new Rating("mines","Palo",4,new Date()));


        System.out.println("Closing PlaygroundJPA");

    }
}
