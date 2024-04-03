package se2.group3.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se2.group3.backend.dto.PlayerDTO;

public class PlayerUtil {
    PlayerDTO player;
    ObjectMapper objectMapper;
    String player_JSON;

    private void messageReceivedFromApp(String message) {
        objectMapper = new ObjectMapper();
        try {
            player = objectMapper.readValue(message, PlayerDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
