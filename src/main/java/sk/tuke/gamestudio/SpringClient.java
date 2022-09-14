package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.mines.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication

@ComponentScan(
        excludeFilters =
        @ComponentScan.Filter(
                type= FilterType.REGEX,
                pattern = "sk.tuke.gamestudio.server.*"
        )
)
public class SpringClient {

    public static void main(String[] args){

        //SpringApplication.run(SpringClient.class);
        new SpringApplicationBuilder(SpringClient.class)
                .web(WebApplicationType.NONE).run(args);
    }

    //@Bean
    public CommandLineRunner runnerSimple(){
        return args -> {
            System.out.println("Hello from Spring");
        };
    }
    //@Bean
    public CommandLineRunner runnerPlaygroundJPA(PlaygroundJPA console){
        return args -> {
            console.play();
        };
    }

    @Bean
    public CommandLineRunner runnerMines(ConsoleUI console){
        return args -> {
            console.play();
        };
    }

    //@Bean
    /**
     * THIS RUNNER FAILS because ConsoleUI console is not instantiated as a Spring component
     * The correct one is runnerMines
     * Here, console.scoreService will be null because
     * @Autowired does not work when instantiated
     * in the classical way (new ConsoleUI ...)
     */
    public CommandLineRunner runnerMinesWrong(){
        return args -> {
            ConsoleUI console = new ConsoleUI(new Field(9, 9, 1));
            console.play();
        };
    }

    //@Bean
    public CommandLineRunner runnerTiles(sk.tuke.gamestudio.game.tiles.consoleui.ConsoleUI console){
        return args -> {
            console.play();
        };
    }

    @Bean
    public PlaygroundJPA consolePlaygroundJPA(){
        return new PlaygroundJPA();
    }

    @Bean
    public ConsoleUI consoleMines(Field field){
        return new ConsoleUI(field);
    }

    @Bean
    public sk.tuke.gamestudio.game.tiles.consoleui.ConsoleUI consoleTiles(sk.tuke.gamestudio.game.tiles.core.Field field){
        return new sk.tuke.gamestudio.game.tiles.consoleui.ConsoleUI(field);
    }

    @Bean
    public Field fieldMines(){
        return new  Field(9, 9, 1);
    }

    @Bean
    public sk.tuke.gamestudio.game.tiles.core.Field fieldTiles(){
        return new sk.tuke.gamestudio.game.tiles.core.Field(10,10);
    }

    @Bean
    public ScoreService scoreService(){
        //return new ScoreServiceJdbc();
        //return new ScoreServiceJPA();
        return new ScoreServiceREST();
    }


    @Bean
    public RatingService ratingService(){
        return new RatingServiceJPA();
    }

    @Bean
    public RestTemplate restTemplate(){ return new RestTemplate();}



}
