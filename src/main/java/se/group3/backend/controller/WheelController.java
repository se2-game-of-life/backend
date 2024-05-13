package se.group3.backend.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import se.group3.backend.domain.game.Game;

public class WheelController {
    private final Game game;

    public WheelController(Game game) {
        this.game = game;
    }

    @MessageMapping("/app/spinWheel")
    public int spinWheel(@Payload String playerUsername) {
        // Get the number of steps from the game's spinWheel method
        int steps = game.spinWheel();
        // Perform any additional logic here, such as updating player position, etc.
        return steps;
    }
}
