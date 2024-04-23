package se.group3.backend.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.group3.backend.domain.player.Player;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.dto.mapper.PlayerMapper;

public class PlayerMapperTest {

    @Test
    void toPlayerModel() {
        PlayerDTO playerDTO = Mockito.mock(PlayerDTO.class);
        Mockito.when(playerDTO.getPlayerName()).thenReturn("Test Player");

        Player player = PlayerMapper.mapDTOToPlayer(playerDTO);
        Assertions.assertEquals("Test Player", player.getPlayerName());
    }

    @Test
    void toPlayerDTO() {
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPlayerName()).thenReturn("Test Player");

        PlayerDTO playerDTO = PlayerMapper.mapPlayerToDTO(player);
        Assertions.assertEquals("Test Player", playerDTO.getPlayerName());
    }
}
