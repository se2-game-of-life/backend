/*
package se.group3.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.Player;
import se.group3.backend.dto.mapper.PlayerMapper;
import se.group3.backend.repositories.PlayerRepository;
import se.group3.backend.services.PlayerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
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

        player = new Player("TestID","player1");

        repository.save(player);
        dto = PlayerMapper.mapPlayerToDTO(player);


        lenient().when(repository.findById(dto.getPlayerUUID())).thenReturn(Optional.ofNullable(player));
    }

    @Test
    void increaseNumberOfPegs_increasesPegs() {
        int initialPegs = player.getNumberOfPegs();

        service.increaseNumberOfPegs(dto.getPlayerUUID());

        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialPegs + 1, player.getNumberOfPegs());
    }

    @Test
    void getSalary_withPaydayCell_increasesMoneyBySalary() {
        CareerCard careerCard = new CareerCard("Doctor", 150000, 20000, false);
        player.setCareerCard(careerCard);
        int initialMoney = player.getMoney();

        service.getSalary(dto.getPlayerUUID());

        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialMoney + careerCard.getSalary(), player.getMoney());
    }

    @Test
    void getSalary_withBonus() {
        CareerCard careerCard = new CareerCard("Doctor", 150000, 20000, false);
        player.setCareerCard(careerCard);
        int initialMoney = player.getMoney();

        service.getSalaryWithBonus(dto.getPlayerUUID());

        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialMoney + careerCard.getBonus(), player.getMoney());
    }

    @Test
    void setCareer_withExistingPlayer_updatesCareerCard() {
        CareerCard newCareerCard = new CareerCard("Engineer", 100000, 15000, false);
        when(repository.findById(dto.getPlayerUUID())).thenReturn(Optional.of(player));
        service.setCareer(dto.getPlayerUUID(), newCareerCard);

        verify(repository, times(2)).save(player);
        Assertions.assertEquals(newCareerCard, player.getCareerCard());
    }

    @Test
    void addHouse_withExistingPlayer_addsHouse() {
        HouseCard houseCard = new HouseCard("Beach House", 500000, 450000, 550000);
        List<HouseCard> houses = new ArrayList<>();
        player.setHouses(houses);

        when(repository.findById("TestID")).thenReturn(Optional.of(player));

        service.addHouse("TestID", houseCard);

        assertTrue(player.getHouses().contains(houseCard), "The house card should be added to the player's houses.");
        verify(repository, atLeastOnce()).save(player);
    }

    @Test
    void addHouse_withNonExistentPlayer_doesNotAddHouse() {
        HouseCard houseCard = new HouseCard("Beach House", 500000, 450000, 550000);
        when(repository.findById("NonExistentID")).thenReturn(Optional.empty());

        service.addHouse("NonExistentID", houseCard);

        verify(repository, times(1)).save(any(Player.class));
    }

}
*/
