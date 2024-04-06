package se2.group3.backend.dto.mapper;

import se2.group3.backend.dto.PlayerDTO;
import se2.group3.backend.model.Player;

public class PlayerMapper {
    private PlayerMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }
    public static Player toPlayerModel(PlayerDTO host) {
        return new Player(host.playerName());
    }
}
