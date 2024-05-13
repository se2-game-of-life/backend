package se.group3.backend.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class CardButtonController {

    @MessageMapping("/app/buttonClicked")
    public void handleButtonClicked(@Payload String buttonInfo) {
        System.out.println("Button clicked: " + buttonInfo);
    }
}

