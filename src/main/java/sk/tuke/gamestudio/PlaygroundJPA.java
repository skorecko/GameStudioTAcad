package sk.tuke.gamestudio;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Student;
import sk.tuke.gamestudio.entity.StudyGroup;
import sk.tuke.gamestudio.service.RatingService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public class PlaygroundJPA {

    @Autowired
    RatingService ratingService;
    public void play(){
        System.out.println("Opening PlaygroundJPA");

        ratingService.setRating(new Rating("pexeso","Roumano",1, new Date()));

        System.out.println("ratingService.getAverageRating(\"mines\") "+ratingService.getAverageRating("mines"));
        System.out.println("ratingService.getRating(\"pexeso\",\"Roumano\") "+ratingService.getRating("pexeso","Roumano"));

        System.out.println("Closing PlaygroundJPA");

    }
}
/*
@Transactional
public class PlaygroundJPA {

    @PersistenceContext
    private EntityManager entityManager;

    public void play(){
        System.out.println("Opening PlaygroundJPA");

//        entityManager.persist(new StudyGroup("basic"));
//        entityManager.persist(new StudyGroup("advanced"));
//        entityManager.persist(new StudyGroup("expert"));

        List<StudyGroup> studyGroups =
                entityManager.createQuery("select g from StudyGroup g")
                        .getResultList();

        int noOfGroups= studyGroups.size();

        for(int groupNo=0; groupNo<noOfGroups;groupNo++){
            System.out.println(groupNo+" "+studyGroups.get(groupNo));
        }

        Student student =
                //new Student("Peter","Trnka",studyGroups.get(1));
                //new Student("Jon","Nowec",studyGroups.get(0));
                new Student("Wen","Noweo",studyGroups.get(2));

        System.out.println(student);

        entityManager.persist(student);

        System.out.println(student);

        System.out.println("Closing PlaygroundJPA");

    }
}
*/

/*
public class PlaygroundJPA {

    @Autowired
    private RatingService ratingService;
    public void play(){
        System.out.println("Opening PlaygroundJPA");
        //ratingService.reset();
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

**
 */