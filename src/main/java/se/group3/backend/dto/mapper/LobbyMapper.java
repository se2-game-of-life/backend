package se.group3.backend.dto.mapper;

import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.domain.Lobby;


public class LobbyMapper {

    private LobbyMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static LobbyDTO toLobbyDTO(Lobby newLobby) {
        return new LobbyDTO(
                newLobby.getLobbyID(),
                newLobby.getPlayers().stream().map(PlayerMapper::mapPlayerToDTO).toList(),
                PlayerMapper.mapPlayerToDTO(newLobby.getCurrentPlayer()),
                newLobby.isHasDecision(),
                CardMapper.toActionCardDTOList(newLobby.getActionCards()),
                CardMapper.toCareerCardDTOList(newLobby.getCareerCards()),
                CardMapper.toHouseCardDTOList(newLobby.getHouseCards()),
                newLobby.getSpunNumber(),
                newLobby.isHasStarted()
        );
    }
}
