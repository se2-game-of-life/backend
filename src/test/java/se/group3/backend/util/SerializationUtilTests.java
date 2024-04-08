package se.group3.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class SerializationUtilTests {

    @Test
    void jsonStringFromClass() {
        LobbyDTO lobby = Mockito.mock(LobbyDTO.class);
        PlayerDTO player = Mockito.mock(PlayerDTO.class);

        Mockito.when(lobby.getHost()).thenReturn(player);
        Mockito.when(lobby.getLobbyID()).thenReturn(13L);
        Mockito.when(lobby.getPlayers()).thenReturn(new PlayerDTO[]{player});
        Mockito.when(player.getPlayerName()).thenReturn("Samantha");

        try {
            String result = SerializationUtil.jsonStringFromClass(lobby);
            Assertions.assertEquals("{\"lobbyID\":13,\"host\":{\"playerName\":\"Samantha\"},\"players\":[{\"playerName\":\"Samantha\"}]}", result);
        } catch (JsonProcessingException e) {
            Assertions.fail(e.getCause());
        }
    }

    @Test
    void toObject() {
        try {
            LobbyDTO lobby = (LobbyDTO) SerializationUtil.toObject("{\"lobbyID\":13,\"host\":{\"playerName\":\"Samantha\"},\"players\":[{\"playerName\":\"Samantha\"}]}", LobbyDTO.class);
            Assertions.assertEquals(13L, lobby.getLobbyID());
            Assertions.assertEquals("Samantha", lobby.getHost().getPlayerName());
            Assertions.assertEquals(1, Arrays.stream(lobby.getPlayers()).count());
        } catch (JsonProcessingException | ClassCastException e) {
            Assertions.fail(e.getCause());
        }
    }
}