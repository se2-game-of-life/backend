package se2.group3.backend.dto;

import se2.group3.backend.model.Player;

public record LobbyDTO(
    long lobbyID,
    Player host
) {}
