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
import se.group3.backend.domain.cells.Cell;
import se.group3.backend.domain.cells.PaydayCell;
import se.group3.backend.domain.cells.StopCell;
import se.group3.backend.domain.player.Player;
import se.group3.backend.dto.mapper.PlayerMapper;
import se.group3.backend.repositories.player.PlayerRepository;
import se.group3.backend.services.player.PlayerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
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

        // Gemeinsame Testdaten für alle Tests
        player = new Player("player1");
        player.setPlayerID("TestID");

        repository.save(player);
        dto = PlayerMapper.mapPlayerToDTO(player);


        lenient().when(repository.findById(dto.getPlayerID())).thenReturn(Optional.ofNullable(player));
    }

    @Test
    void chooseCollegePath_withEnoughMoney_updatesPlayer() {
        dto.setCollegePath(true);

        service.chooseCollagePath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertTrue(player.isCollegePath());
        assertEquals(200000, player.getMoney());
    }

    @Test
    void chooseCollegePathFalse() {
        dto.setCollegePath(false);

        service.chooseCollagePath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertFalse(player.isCollegePath());
    }

    @Test
    void chooseMarriedPath_withEnoughMoney_updatesPlayer() {
        dto.setMarriedPath(true);

        service.chooseMarryPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertTrue(player.isMarriedPath());
        assertEquals(200000, player.getMoney());
    }

    @Test
    void chooseMarriedPathFalse() {
        dto.setMarriedPath(false);

        service.chooseMarryPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertFalse(player.isMarriedPath());
    }

    @Test
    void chooseGrowFamilyPath_withEnoughMoney_updatesPlayer() {
        dto.setGrowFamilyPath(true);

        service.chooseGrowFamilyPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertTrue(player.isGrowFamilyPath());
        assertEquals(200000, player.getMoney());
    }

    @Test
    void chooseGrowFamilyPathFalse() {
        dto.setGrowFamilyPath(false);

        service.chooseGrowFamilyPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertFalse(player.isGrowFamilyPath());
    }

    @Test
    void midLifeCrisisPath_setsFlag() {
        dto.setHasMidlifeCrisis(true);

        service.midLifeCrisisPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertTrue(player.isHasMidlifeCrisis());
    }

    @Test
    void midLifeCrisisPath_setsFlagFalse() {
        dto.setHasMidlifeCrisis(false);

        service.midLifeCrisisPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertFalse(player.isHasMidlifeCrisis());
    }

    @Test
    void chooseRetireEarlyPath_withEnoughMoney_updatesPlayer() {
        dto.setRetireEarlyPath(true);

        service.chooseRetireEarlyPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertTrue(player.isRetireEarlyPath());
        assertEquals(200000, player.getMoney());
    }

    @Test
    void chooseRetireEarlyPathFalse() {
        dto.setRetireEarlyPath(false);

        service.chooseRetireEarlyPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertFalse(player.isRetireEarlyPath());
    }

    @Test
    void increaseNumberOfPegs_increasesPegs() {
        int initialPegs = player.getNumberOfPegs();

        service.increaseNumberOfPegs(dto);

        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialPegs + 1, player.getNumberOfPegs());
    }
    @Test
    void spin_increasesCurrentCellPosition() {
        int initialPosition = player.getCurrentCellPosition();

        service.spin(dto);

        assertTrue(player.getCurrentCellPosition() >= initialPosition);
    }
    @Test
    void collectInvestmentPayout_withMatchingSpinResult_increasesMoney() {
        player.setInvestmentNumber(5);
        player.setInvestmentLevel(1);

        service.collectInvestmentPayout(dto, 5);

        verify(repository, atLeastOnce()).save(player);
        assertEquals(10000, player.getMoney());
        assertEquals(2, player.getInvestmentLevel());
    }

    @Test
    void invest_decreasesMoneyAndSetsInvestmentNumber() {
        Integer investmentNumber = 5;

        service.invest(dto, investmentNumber);

        verify(repository, atLeastOnce()).save(player);
        assertEquals(200000, player.getMoney());
        assertEquals(investmentNumber, player.getInvestmentNumber());
    }



    @Test
    void getPayOut_withPaydayCell_increasesMoneyBySalary() {
        PaydayCell paydayCell = new PaydayCell(5, "Payday", Arrays.asList(6, 7), 6, 7); // Position und mögliche nächste Zellen
        CareerCard careerCard = new CareerCard("Doctor", 150000, 20000, false);
        player.setCareerCard(careerCard);
        int initialMoney = player.getMoney();

        service.getPayOut(dto, paydayCell);

        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialMoney + careerCard.getSalary(), player.getMoney());
    }

    @Test
    void setOrUpdateCareer_withExistingPlayer_updatesCareerCard() {
        CareerCard newCareerCard = new CareerCard("Engineer", 100000, 15000, false);
        when(repository.findById(dto.getPlayerID())).thenReturn(Optional.of(player));
        service.setOrUpdateCareer(dto, newCareerCard);

        verify(repository, times(2)).save(player);
        Assertions.assertEquals(newCareerCard, player.getCareerCard());
    }

    @Test
    void checkCellAndPerformAction_StopCellPresent() {
        PlayerDTO dto = new PlayerDTO();
        dto.setPlayerID("TestID");
        Player player = new Player("player1");
        Cell cell1 = mock(Cell.class);
        StopCell stopCell = mock(StopCell.class);
        Cell cell3 = mock(Cell.class);
        List<Cell> cells = Arrays.asList(cell1, stopCell, cell3);

        when(repository.findById(dto.getPlayerID())).thenReturn(Optional.of(player));

        service.checkCellAndPerformAction(dto, cells);

        verify(cell1).performAction(player);
        verify(stopCell).performAction(player);
        verify(cell3, never()).performAction(player); // Verifies action stops after StopCell
    }

    @Test
    void checkCellAndPerformAction_PlayerNotFound() {
        // Setup
        PlayerDTO dto = new PlayerDTO();
        dto.setPlayerID("TestID");
        Cell cell = mock(Cell.class);
        List<Cell> cells = Collections.singletonList(cell);

        when(repository.findById(dto.getPlayerID())).thenReturn(Optional.empty());

        // Execution
        service.checkCellAndPerformAction(dto, cells);

        // Verification
        verify(cell, never()).performAction(any(Player.class));
    }
}
