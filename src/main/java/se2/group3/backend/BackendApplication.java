package se2.group3.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import se2.group3.backend.model.Game;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(BackendApplication.class, args);

        // Create a game instance
        Game game = context.getBean(Game.class);

        // Initialize decks
        game.initializeDecks();
        game.initializeBoard();
    }

}
