package se2.group3.backend.dto;

import se2.group3.backend.domain.player.Player;

public record LobbyDTO(
    long lobbyID,
    Player host
) {}
