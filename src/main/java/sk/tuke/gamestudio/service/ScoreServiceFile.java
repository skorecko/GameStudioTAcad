package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.exceptions.ServiceException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreServiceFile implements ScoreService {

    private final String FILE = "score.bin";


    @Override
    public void addScore(Score score) {
        List<Score> scores=load();
        scores.add(score);
        save(scores);
    }

    @Override
    public List<Score> getBestScores(String game) {
        List<Score> scores=load();
        return
            scores
                .stream()
                .filter(score -> score.getGame().equals(game))
                .sorted((score1,score2)->-Integer.compare(score1.getPoints(),score2.getPoints()))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        List<Score> scores=new ArrayList<>();
        save(scores);
    }

    private void save(List<Score> scores2save){
        try(
            var os=new ObjectOutputStream(new FileOutputStream(FILE))
        ){
            os.writeObject(scores2save);
        }catch(IOException e){
            throw new ServiceException(e);
        }
    }

    private List<Score> load(){
        try(
            var is=new ObjectInputStream(new FileInputStream(FILE))
        ){
            return (List<Score>) is.readObject();
        }catch(IOException | ClassNotFoundException e){
            throw new ServiceException(e);
        }
    }

}
