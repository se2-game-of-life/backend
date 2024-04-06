package se2.group3.backend.mapper;


import se2.group3.backend.DTOs.PlayerDTO;
import se2.group3.backend.domain.player.Player;

public class PlayerMapper {

    public static Player mapDTOToPlayer(PlayerDTO dto) {
        Player player = new Player(dto.getPlayerName());
        player.setPlayerID(dto.getPlayerID());
        player.setCurrentCellPosition(dto.getCurrentCellPosition());
        player.setMoney(dto.getMoney());
        player.setCareerCard(dto.getCareerCard());
        player.setCollegePath(dto.isCollegePath());
        player.setMarriedPath(dto.isMarriedPath());
        player.setGrowFamiliePath(dto.isGrowFamiliePath());
        player.setHasMidlifeCrisis(dto.isHasMidlifeCrisis());
        player.setRetireEarlyPath(dto.isRetireEarlyPath());
        player.setInvestmentNumber(dto.getInvestmentNumber());
        player.setInvestmentLevel(dto.getInvestmentLevel());
        player.setNumberOfPegs(dto.getNumberOfPegs());

        return player;
    }

    public static PlayerDTO mapPlayerToDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setPlayerID(player.getPlayerID());
        dto.setPlayerName(player.getPlayerName());
        dto.setCurrentCellPosition(player.getCurrentCellPosition());
        dto.setMoney(player.getMoney());
        dto.setCareerCard(player.getCareerCard());
        dto.setCollegePath(player.isCollegePath());
        dto.setMarriedPath(player.isMarriedPath());
        dto.setGrowFamiliePath(player.isGrowFamiliePath());
        dto.setHasMidlifeCrisis(player.isHasMidlifeCrisis());
        dto.setRetireEarlyPath(player.isRetireEarlyPath());
        dto.setInvestmentNumber(player.getInvestmentNumber());
        dto.setInvestmentLevel(player.getInvestmentLevel());
        dto.setNumberOfPegs(player.getNumberOfPegs());

        return dto;
    }

}