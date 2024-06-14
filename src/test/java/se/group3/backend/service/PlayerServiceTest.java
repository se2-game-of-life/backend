package se.group3.backend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.group3.backend.domain.Player;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.repositories.ActionCardRepository;
import se.group3.backend.repositories.CareerCardRepository;
import se.group3.backend.repositories.CellRepository;
import se.group3.backend.repositories.HouseCardRepository;
import se.group3.backend.services.PlayerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceTest {
    private PlayerService playerService;
    private CareerCardRepository careerCardRepository;
    private ActionCardRepository actionCardRepository;
    private HouseCardRepository houseCardRepository;
    private CellRepository cellRepository;

    @BeforeEach
    public void setUp(){
        this.careerCardRepository = mock(CareerCardRepository.class);
        this.actionCardRepository = mock(ActionCardRepository.class);
        this.houseCardRepository = mock(HouseCardRepository.class);
        this.cellRepository = mock(CellRepository.class);
        this.playerService = new PlayerService(careerCardRepository, cellRepository, houseCardRepository, actionCardRepository);
    }



    @Test
    void testCareerOrCollegeChoice_College(){
        Player player = new Player("123", "player1");
        player.setMoney(200000);

        playerService.careerOrCollegeChoice(player, true);

        assertEquals(100000, player.getMoney());
        assertTrue(player.isCollegeDegree());
        assertEquals(1, player.getCurrentCellPosition());
    }

    @Test
    void testCareerOrCollegeChoice_Career(){
        Player player = new Player("123", "player1");
        player.setMoney(200000);
        when(careerCardRepository.findCareerCardNoDiploma()).thenReturn(new CareerCard("1", "career", 100,100, false));

        playerService.careerOrCollegeChoice(player, false);

        assertEquals(200000, player.getMoney());
        assertFalse(player.isCollegeDegree());
        assertEquals("career", player.getCareerCard().getName());
        assertEquals(14, player.getCurrentCellPosition());
    }


    @AfterEach
    public void breakDown(){
        this.careerCardRepository = null;
        this.actionCardRepository = null;
        this.houseCardRepository = null;
        this.cellRepository = null;
        this.playerService = null;
    }
}
