package se.group3.backend.dto.mapper;

import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.domain.Player;

public class PlayerMapper {

    public static Player mapDTOToPlayer(PlayerDTO dto) {
        Player player = new Player(dto.getPlayerUUID(), dto.getPlayerName());
        player.setCurrentCellPosition(dto.getCurrentCellPosition());
        player.setMoney(dto.getMoney());
        player.setCareerCard(dto.getCareerCard());
        player.setNumberOfPegs(dto.getNumberOfPegs());

        return player;
    }

    public static PlayerDTO mapPlayerToDTO(Player player) {
        return new PlayerDTO(
                player.getPlayerUUID(),
                player.getPlayerName(),
                player.getLobbyID(),
                player.getCurrentCellPosition(),
                player.getMoney(),
                player.getCareerCard(),
                player.getNumberOfPegs(),
                player.getHouses(),
                player.isCollegeDegree()
        );
    }

}