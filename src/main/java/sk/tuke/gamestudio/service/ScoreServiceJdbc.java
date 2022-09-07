package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJdbc implements ScoreService {

    private static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio2";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "postgres";



    @Override
    public void addScore(Score score) {

        final String STATEMENT_ADD_SCORE = "INSERT INTO score VALUES (?, ?, ?, ?)";

        try(
            var connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            var statement = connection.prepareStatement(STATEMENT_ADD_SCORE);
        )
        {
            statement.setString(1, score.getGame());
            statement.setString(2, score.getUsername());
            statement.setInt(3, score.getPoints());
            statement.setTimestamp(4,  new Timestamp(score.getPlayedAt().getTime()));
            statement.executeUpdate();
       }catch(SQLException e){
            System.out.println(e.getMessage());
       }

    }

    @Override
    public List<Score> getBestScores(String game) {
        final String STATEMENT_BEST_SCORES = "SELECT game, username, points, played_on FROM score WHERE game= ? ORDER BY points DESC LIMIT 5";

        try( var connection =DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_BEST_SCORES)
        )
        {
            statement.setString(1,game);
            try(var rs = statement.executeQuery()){
                var scores= new ArrayList<Score>();
                while(rs.next()) {
                    scores.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return scores;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void reset() {
        final String STATEMENT_RESET = "DELETE FROM score";
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.createStatement()
        )
        {
            statement.executeUpdate(STATEMENT_RESET);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
