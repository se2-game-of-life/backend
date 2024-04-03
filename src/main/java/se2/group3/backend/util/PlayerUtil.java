package se2.group3.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se2.group3.backend.dto.PlayerDTO;

import java.io.IOException;

public class PlayerUtil {
    PlayerDTO player;
    ObjectMapper objectMapper;
    String player_JSON;

    private void createJSON(){
        objectMapper = new ObjectMapper();
        try{
            player_JSON = objectMapper.writeValueAsString(player);
        } catch (StreamWriteException sw) {
            sw.printStackTrace();
        } catch (DatabindException db) {
            db.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void messageReceivedFromApp(String message) {
        objectMapper = new ObjectMapper();
        try {
            player = objectMapper.readValue(message, PlayerDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
