package se.group3.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.game.Cell;
import se.group3.backend.domain.player.Player;
import se.group3.backend.dto.mapper.PlayerMapper;
import se.group3.backend.repositories.player.PlayerRepository;
import se.group3.backend.services.player.PlayerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository repository;

    @InjectMocks
    private PlayerServiceImpl service;

    private Player player;
    private PlayerDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(PlayerRepository.class);

        // Gemeinsame Testdaten für alle Tests
        player = new Player("player1");
        player.setPlayerID("TestID");

        repository.save(player);
        dto = PlayerMapper.mapPlayerToDTO(player);


        lenient().when(repository.findById(dto.getPlayerID())).thenReturn(Optional.ofNullable(player));
    }

    @Test
    void increaseNumberOfPegs_increasesPegs() {
        int initialPegs = player.getNumberOfPegs();

        service.increaseNumberOfPegs(dto.getPlayerID());

        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialPegs + 1, player.getNumberOfPegs());
    }

    @Test
    void getSalary_withPaydayCell_increasesMoneyBySalary() {
        CareerCard careerCard = new CareerCard("Doctor", 150000, 20000, false);
        player.setCareerCard(careerCard);
        int initialMoney = player.getMoney();

        service.getSalary(dto.getPlayerID());

        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialMoney + careerCard.getSalary(), player.getMoney());
    }

    @Test
    void getSalary_withBonus() {
        CareerCard careerCard = new CareerCard("Doctor", 150000, 20000, false);
        player.setCareerCard(careerCard);
        int initialMoney = player.getMoney();

        service.getSalaryWithBonus(dto.getPlayerID());

        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialMoney + careerCard.getBonus(), player.getMoney());
    }

    @Test
    void setCareer_withExistingPlayer_updatesCareerCard() {
        CareerCard newCareerCard = new CareerCard("Engineer", 100000, 15000, false);
        when(repository.findById(dto.getPlayerID())).thenReturn(Optional.of(player));
        service.setCareer(dto.getPlayerID(), newCareerCard);

        verify(repository, times(2)).save(player);
        Assertions.assertEquals(newCareerCard, player.getCareerCard());
    }
}
