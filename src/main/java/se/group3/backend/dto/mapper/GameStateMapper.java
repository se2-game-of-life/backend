package se.group3.backend.dto.mapper;


import se.group3.backend.domain.game.Game;
import se.group3.backend.domain.player.Player;
import se.group3.backend.dto.GameStateDTO;
import se.group3.backend.dto.PlayerDTO;

import java.util.ArrayList;
import java.util.List;

public class GameStateMapper {
    private GameStateMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

/*    public static GameStateDTO mapGameToGameStateDTO(Game game) {
        List<Player> playerList = game.getPlayers();
        List<PlayerDTO> playerDTOList = new ArrayList<>();
        for (Player player : playerList) {
            playerDTOList.add(PlayerMapper.mapPlayerToDTO(player));
        }
        return new GameStateDTO(playerDTOList.get(game.getCurrentPlayerIndex()), playerDTOList, game.spinSpinner(), null);
    }*/


}
