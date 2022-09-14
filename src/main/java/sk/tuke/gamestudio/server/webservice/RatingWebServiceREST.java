package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingWebServiceREST {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    void setRating(@RequestBody Rating rating){
        ratingService.setRating(rating);
    }

/*
    @GetMapping("/{game}")
    int getAverageRating(@PathVariable String game){
        return ratingService.getAverageRating(game);
    }
*/

/*
    @GetMapping("/{game}")
    Rating getAverageRating(@PathVariable String game){
        Rating ratingWithAvg=
                new Rating(game,"Average",
                        ratingService.getAverageRating(game),
                        new Date());
        return ratingWithAvg;
    }
*/

    @GetMapping("/{game}")
    Rating getAverageRating(@PathVariable String game){
        return new Rating(ratingService.getAverageRating(game));
    }

/*
    @GetMapping("/{game}/{username}")
    int getRating(@PathVariable String game,@PathVariable String username){
        return ratingService.getRating(game,username);
    }
*/

/*
    @GetMapping("/{game}/{username}")
    Rating getRating(@PathVariable String game,@PathVariable String username){
        Rating rating=
                new Rating(game,username,
                        ratingService.getRating(game,username),
                        new Date());
        return rating;
    }
*/

    @GetMapping("/{game}/{username}")
    Rating getRating(@PathVariable String game,@PathVariable String username){
        return new Rating(ratingService.getRating(game,username));
    }

}
