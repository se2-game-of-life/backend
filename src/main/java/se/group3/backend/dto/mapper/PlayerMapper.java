package se.group3.backend.dto.mapper;

import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.dto.HouseCardDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.domain.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerMapper {

    private PlayerMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Player mapDTOToPlayer(PlayerDTO dto) {
        Player player = new Player(dto.getPlayerUUID(), dto.getPlayerName());
        player.setCurrentCellPosition(dto.getCurrentCellPosition());
        player.setMoney(dto.getMoney());
        player.setCareerCard(CardMapper.toCareerCard(dto.getCareerCard()));
        player.setNumberOfPegs(dto.getNumberOfPegs());

        return player;
    }

    public static PlayerDTO mapPlayerToDTO(Player player) {
        PlayerDTO dto = new PlayerDTO(
                player.getPlayerUUID(),
                player.getPlayerName(),
                player.getLobbyID(),
                player.getCurrentCellPosition(),
                player.getMoney(),
                CardMapper.toCareerCardDTO(player.getCareerCard()),
                player.getNumberOfPegs(),
                null, //will set below int the forEach loop
                player.isCollegeDegree()
        );
        List<HouseCardDTO> houses = new ArrayList<>();
        if (player.getHouses() == null) {
            dto.setHouses(houses);
        } else {
            for (HouseCard house : player.getHouses()) {
                houses.add(CardMapper.toHouseCardDTO(house));
            }
        }
        dto.setHouses(houses);
        return dto;
    }

}