package se.group3.backend.dto.mapper;


import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.domain.player.Player;

/**
 * Class to map a PlayerDTO to a Player object and back
 */
public class PlayerMapper {

    public static Player mapDTOToPlayer(PlayerDTO dto) {
        Player player = new Player(dto.getPlayerName());
        player.setPlayerUUID(dto.getPlayerID());
        player.setCurrentCellPosition(dto.getCurrentCellPosition());
        player.setMoney(dto.getMoney());
        player.setCareerCard(dto.getCareerCard());
        player.setNumberOfPegs(dto.getNumberOfPegs());

        return player;
    }

    public static PlayerDTO mapPlayerToDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setPlayerID(player.getPlayerUUID());
        dto.setPlayerName(player.getPlayerName());
        dto.setCurrentCellPosition(player.getCurrentCellPosition());
        dto.setMoney(player.getMoney());
        dto.setCareerCard(player.getCareerCard());

        dto.setNumberOfPegs(player.getNumberOfPegs());

        return dto;
    }

}