package se.group3.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.services.SerializationService;

import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SerializationServiceTest {

    @InjectMocks
    private SerializationService SerializationUtil;

    @Test
    void jsonStringFromClass() {
        LobbyDTO lobby = Mockito.mock(LobbyDTO.class);
        PlayerDTO player = Mockito.mock(PlayerDTO.class);

        Mockito.when(lobby.getLobbyID()).thenReturn(13L);
        Mockito.when(lobby.getPlayers()).thenReturn(Arrays.stream(new PlayerDTO[]{player}).toList());
        Mockito.when(player.getPlayerName()).thenReturn("Samantha");
        Mockito.when(player.getPlayerUUID()).thenReturn(null);
        Mockito.when(player.getCurrentCellPosition()).thenReturn(0);
        Mockito.when(player.getMoney()).thenReturn(0);
        Mockito.when(player.getCareerCard()).thenReturn(null);

        try {
            String result = SerializationUtil.jsonStringFromClass(lobby);
            String expectedJson = "{\"lobbyID\":13,\"host\":{\"playerName\":\"Samantha\",\"playerID\":null,\"currentCellPosition\":0,\"money\":0,\"careerCard\":null,\"hasMidlifeCrisis\":false,\"investmentNumber\":0,\"investmentLevel\":0,\"numberOfPegs\":0,\"growFamilyPath\":false,\"retireEarlyPath\":false,\"collegePath\":false,\"marriedPath\":false},\"players\":[{\"playerName\":\"Samantha\",\"playerID\":null,\"currentCellPosition\":0,\"money\":0,\"careerCard\":null,\"hasMidlifeCrisis\":false,\"investmentNumber\":0,\"investmentLevel\":0,\"numberOfPegs\":0,\"growFamilyPath\":false,\"retireEarlyPath\":false,\"collegePath\":false,\"marriedPath\":false}]}";
            //review: check this assertion
            //Assertions.assertEquals(expectedJson, result);
            verify(player, times(1)).getPlayerUUID();
            verify(player, times(1)).getPlayerName();
            verify(lobby, times(1)).getLobbyID();
        } catch (JsonProcessingException e) {
            Assertions.fail(e.getCause());
        }
    }

/*    @Test
    void toObject() {
        try {
            LobbyDTO lobby = (LobbyDTO) SerializationUtil.toObject("{\"lobbyID\":13,\"host\":{\"playerName\":\"Samantha\"},\"players\":[{\"playerName\":\"Samantha\"}]}", LobbyDTO.class);
            Assertions.assertEquals(13L, lobby.getLobbyID());
            int listSize = lobby.getPlayers().size();
           Assertions.assertEquals(1, listSize);
        } catch (AssertionFailedError | JsonProcessingException e) {
            Assertions.fail(e.getCause());
        }
    }*/
}