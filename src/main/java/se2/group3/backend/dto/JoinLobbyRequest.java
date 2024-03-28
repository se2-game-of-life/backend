package se2.group3.backend.dto;

import lombok.Getter;

@Getter
public record JoinLobbyRequest(long lobbyID, PlayerDTO player) {

}
