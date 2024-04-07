package se2.group3.backend.dto.mapper;

import se2.group3.backend.domain.player.Player;
import se2.group3.backend.dto.PlayerDTO;

public class PlayerMapper {
    private PlayerMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }
    public static Player toPlayerModel(PlayerDTO host) {
        return new Player(host.getPlayerName());
    }
    public static PlayerDTO toPlayerDTO(Player player) {return new PlayerDTO(player.getPlayerName());}
}
