package se.group3.backend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.group3.backend.domain.Cell;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.repositories.ActionCardRepository;
import se.group3.backend.repositories.CareerCardRepository;
import se.group3.backend.repositories.CellRepository;
import se.group3.backend.repositories.HouseCardRepository;
import se.group3.backend.services.PlayerService;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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


    @Test
    void testMidLife_true(){
        Player player = new Player("123", "player1");
        Cell cell = mock(Cell.class);
        Lobby lobby = mock(Lobby.class);
        when(cell.getNextCells()).thenReturn(List.of(1,2));

        playerService.midLife(player, cell, lobby, 3);
        assertEquals(1, player.getCurrentCellPosition());
        verify(lobby).nextPlayer();
    }

    @Test
    void testMidLife_false(){
        Player player = new Player("123", "player1");
        Cell cell = mock(Cell.class);
        Lobby lobby = mock(Lobby.class);
        when(cell.getNextCells()).thenReturn(List.of(1,2));

        playerService.midLife(player, cell, lobby, 1);
        assertEquals(2, player.getCurrentCellPosition());
        verify(lobby).nextPlayer();
    }

    @Test
    void testGetCareerCards_College(){
        Player player = new Player("123", "player1");
        player.setCollegeDegree(true);
        Lobby lobby = new Lobby(1L,player);
        lobby.setPlayers(List.of(player));

        CareerCard career = new CareerCard("123", "career", 100,100, true);
        when(careerCardRepository.findRandomCareerCard()).thenReturn(career);

        playerService.getCareerCards(player, lobby);

        assertEquals(List.of(career, career), lobby.getCareerCards());
        assertTrue(lobby.isHasDecision());
    }

    @Test
    void testGetCareerCards_Career(){
        Player player = new Player("123", "player1");
        Lobby lobby = new Lobby(1L,player);
        lobby.setPlayers(List.of(player));

        CareerCard career = new CareerCard("123", "career", 100,100, false);
        when(careerCardRepository.findRandomCareerCard()).thenReturn(career);

        playerService.getCareerCards(player, lobby);

        assertEquals(List.of(career, career), lobby.getCareerCards());
        assertTrue(lobby.isHasDecision());
    }


    @Test
    void getHouseCards_enoughMoney(){
        Player player = new Player("123", "player1");
        Lobby lobby = new Lobby(1L,player);
        lobby.setPlayers(List.of(player));
        HouseCard house1 = new HouseCard("1", "house", 100, 100, 100);
        HouseCard house2 = new HouseCard("2", "house2", 100, 100, 100);
        List<HouseCard> houses = List.of(house1, house2);

        when(houseCardRepository.searchAffordableHousesForPlayer(player.getMoney())).thenReturn(houses);

        playerService.getHouseCards(player, lobby);

        assertEquals(houses, lobby.getHouseCards());
        assertTrue(lobby.isHasDecision());
    }

    @Test
    void getHouseCards_notEnoughMoney(){
        Player player = new Player("123", "player1");
        Lobby lobby = new Lobby(1L,player);
        lobby.setPlayers(List.of(player));
        HouseCard house1 = new HouseCard("1", "house", 100, 100, 100);
        List<HouseCard> houses = List.of(house1);

        when(houseCardRepository.searchAffordableHousesForPlayer(player.getMoney())).thenReturn(houses);

        playerService.getHouseCards(player, lobby);

        assertEquals(List.of(), lobby.getHouseCards());
        assertFalse(lobby.isHasDecision());
    }



    @Test
    void testMarryAndFamilyPathChoice_true(){
        Player player = new Player("123", "player1");
        player.setMoney(50000);
        Lobby lobby = new Lobby(1L, player);
        lobby.setPlayers(List.of(player));
        Cell cell = mock(Cell.class);
        when(cell.getNextCells()).thenReturn(List.of(1,2));

        playerService.marryAndFamilyPathChoice(player, true, cell);

        assertEquals(0, player.getMoney());
        assertEquals(2, player.getNumberOfPegs());
        assertEquals(1, player.getCurrentCellPosition());
    }

    @Test
    void testMarryAndFamilyPathChoice_false(){
        Player player = new Player("123", "player1");
        player.setMoney(50000);
        Lobby lobby = new Lobby(1L, player);
        lobby.setPlayers(List.of(player));
        Cell cell = mock(Cell.class);
        when(cell.getNextCells()).thenReturn(List.of(1,2));

        playerService.marryAndFamilyPathChoice(player, false, cell);

        assertEquals(50000, player.getMoney());
        assertEquals(1, player.getNumberOfPegs());
        assertEquals(2, player.getCurrentCellPosition());
    }

    @Test
    void testHouseChoice_left(){
        Player player = new Player("123", "player1");
        player.setMoney(100);
        Lobby lobby = new Lobby(1L, player);
        lobby.setPlayers(List.of(player));

        HouseCard house1 = new HouseCard("1", "house", 100, 100, 100);
        HouseCard house2 = new HouseCard("2", "house2", 50, 100, 100);
        List<HouseCard> houses = List.of(house1, house2);

        lobby.setHouseCards(houses);

        playerService.houseChoice(player, lobby, true);


        assertEquals(0, player.getMoney());
        assertEquals(List.of(house1), player.getHouses());

    }

    @Test
    void testHouseChoice_right(){
        Player player = new Player("123", "player1");
        player.setMoney(100);
        Lobby lobby = new Lobby(1L, player);
        lobby.setPlayers(List.of(player));

        HouseCard house1 = new HouseCard("1", "house", 100, 100, 100);
        HouseCard house2 = new HouseCard("2", "house2", 50, 100, 100);
        List<HouseCard> houses = List.of(house1, house2);

        lobby.setHouseCards(houses);

        playerService.houseChoice(player, lobby, false);


        assertEquals(50, player.getMoney());
        assertEquals(List.of(house2), player.getHouses());
    }

    @Test
    void testCareerChoice_true() {
        Player player = new Player("123", "player1");
        player.setCollegeDegree(true);
        Lobby lobby = new Lobby(1L,player);
        lobby.setPlayers(List.of(player));

        CareerCard career1 = new CareerCard("123", "career1", 100,100, true);
        CareerCard career2 = new CareerCard("123", "career2", 100,100, true);
        List<CareerCard> careerCards = List.of(career1, career2);

        lobby.setCareerCards(careerCards);

        playerService.careerChoice(player, lobby, true);

        assertEquals(career1, player.getCareerCard());
    }


    @Test
    void testCareerChoice_false() {
        Player player = new Player("123", "player1");
        player.setCollegeDegree(true);
        Lobby lobby = new Lobby(1L,player);
        lobby.setPlayers(List.of(player));

        CareerCard career1 = new CareerCard("123", "career1", 100,100, true);
        CareerCard career2 = new CareerCard("123", "career2", 100,100, true);
        List<CareerCard> careerCards = List.of(career1, career2);

        lobby.setCareerCards(careerCards);

        playerService.careerChoice(player, lobby, false);

        assertEquals(career2, player.getCareerCard());
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
