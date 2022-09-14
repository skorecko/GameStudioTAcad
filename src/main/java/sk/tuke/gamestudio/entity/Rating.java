package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name ="UniqueGameAndUsername",columnNames = {"game","username"})})
public class Rating {

    @Id
    @GeneratedValue
    private int ident;
    @Column(nullable = false, length=64)
    private String game;
    @Column(nullable = false, length=64)
    private String username;
    @Column(columnDefinition = "INT CHECK(rating BETWEEN 1 AND 5) NOT NULL") //use SQL names here!
    private int rating;

    @Column(nullable = false)
    private Date ratedAt;

    public Rating(){

    }


    public Rating(String game, String username, int rating, Date ratedAt) {
        this.game = game;
        this.username = username;
        this.rating = rating;
        this.ratedAt = ratedAt;
    }

    public Rating(int rating) {
        this.rating = rating;
    }

    public int getIdent() {
        return ident;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Date ratedAt) {
        this.ratedAt = ratedAt;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ident=" + ident +
                ", game='" + game + '\'' +
                ", username='" + username + '\'' +
                ", rating=" + rating +
                ", ratedAt=" + ratedAt +
                '}';
    }
}