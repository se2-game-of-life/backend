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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        Mockito.when(player.getPlayerID()).thenReturn(null);
        Mockito.when(player.getCurrentCellPosition()).thenReturn(0);
        Mockito.when(player.getMoney()).thenReturn(0);
        Mockito.when(player.getCareerCard()).thenReturn(null);
        Mockito.when(player.isHasMidlifeCrisis()).thenReturn(false);
        Mockito.when(player.getInvestmentLevel()).thenReturn(0);
        Mockito.when(player.getInvestmentLevel()).thenReturn(0);
        Mockito.when(player.getNumberOfPegs()).thenReturn(0);
        Mockito.when(player.isGrowFamilyPath()).thenReturn(false);
        Mockito.when(player.isRetireEarlyPath()).thenReturn(false);
        Mockito.when(player.isCollegePath()).thenReturn(false);
        Mockito.when(player.isMarriedPath()).thenReturn(false);
        Mockito.when(player.isGrowFamilyPath()).thenReturn(false);


        try {
            String result = SerializationUtil.jsonStringFromClass(lobby);
            String expectedJson = "{\"lobbyID\":13,\"host\":{\"playerName\":\"Samantha\",\"playerID\":null,\"currentCellPosition\":0,\"money\":0,\"careerCard\":null,\"hasMidlifeCrisis\":false,\"investmentNumber\":0,\"investmentLevel\":0,\"numberOfPegs\":0,\"growFamilyPath\":false,\"retireEarlyPath\":false,\"collegePath\":false,\"marriedPath\":false},\"players\":[{\"playerName\":\"Samantha\",\"playerID\":null,\"currentCellPosition\":0,\"money\":0,\"careerCard\":null,\"hasMidlifeCrisis\":false,\"investmentNumber\":0,\"investmentLevel\":0,\"numberOfPegs\":0,\"growFamilyPath\":false,\"retireEarlyPath\":false,\"collegePath\":false,\"marriedPath\":false}]}";
            //review: check this assertion
            //Assertions.assertEquals(expectedJson, result);
            verify(player, times(2)).getPlayerID();
            verify(player, times(2)).getPlayerName();
            verify(lobby, times(1)).getLobbyID();
            verify(lobby, times(1)).getHost();
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