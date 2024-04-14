package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.group3.backend.DTOs.PlayerDTO;
import se.group3.backend.dto.CellDTO;
import se.group3.backend.dto.LobbyDTO;

@Slf4j
@Service
public class GameServiceImpl implements GameService {


    @Override
    public void startGame() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public void choosePath(PlayerDTO playerDTO) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public void handleCell(PlayerDTO playerDTO, CellDTO cellDTO) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public void getPlayerStats(LobbyDTO lobbyDTO) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}
