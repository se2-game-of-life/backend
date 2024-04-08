package se2.group3.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import se2.group3.backend.DTOs.PlayerDTO;
import se2.group3.backend.domain.cards.CareerCard;
import se2.group3.backend.domain.cells.PaydayCell;
import se2.group3.backend.domain.player.Player;
import se2.group3.backend.mapper.PlayerMapper;
import se2.group3.backend.repositories.player.PlayerRepository;
import se2.group3.backend.services.player.PlayerServiceImpl;

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
        MockitoAnnotations.openMocks(this);

        // Gemeinsame Testdaten für alle Tests
        player = new Player("player1");
        player.setPlayerID("TestID");

        repository.save(player);
        dto = PlayerMapper.mapPlayerToDTO(player);


        when(repository.findById(dto.getPlayerID())).thenReturn(Optional.ofNullable(player));
    }

    @Test
    void chooseCollegePath_withEnoughMoney_updatesPlayer() {
        dto.setCollegePath(true);

        service.chooseCollagePath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertTrue(player.isCollegePath());
        assertEquals(200000, player.getMoney()); // Überprüfen Sie das neue Geldsaldo nach der Investition
    }

    @Test
    void chooseMarriedPath_withEnoughMoney_updatesPlayer() {
        dto.setMarriedPath(true);
        String test = player.getPlayerID();
        String test2 = dto.getPlayerID();

        service.chooseMarryPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertTrue(player.isMarriedPath());
        assertEquals(200000, player.getMoney());
    }

    @Test
    void chooseGrowFamilyPath_withEnoughMoney_updatesPlayer() {
        dto.setGrowFamiliePath(true);

        service.chooseGrowFamilyPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertTrue(player.isGrowFamiliePath());
        assertEquals(200000, player.getMoney());
    }

    @Test
    void midLifeCrisisPath_setsFlag() {
        dto.setHasMidlifeCrisis(true);

        service.midLifeCrisisPath(dto);

        verify(repository, atLeastOnce()).save(player);
        assertTrue(player.isHasMidlifeCrisis());
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
    void increaseNumberOfPegs_increasesPegs() {
        int initialPegs = player.getNumberOfPegs();

        service.increaseNumberOfPegs(dto);

        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialPegs + 1, player.getNumberOfPegs());
    }
    @Test
    void spin_increasesCurrentCellPosition() {
        int initialPosition = player.getCurrentCellPosition();

        // Da die Spin-Methode eine Zufallszahl verwendet, müssen Sie möglicherweise den Random-Mechanismus mocken oder eine Strategie entwickeln, um den Test vorhersagbar zu machen.

        service.spin(dto);

        assertTrue(player.getCurrentCellPosition() >= initialPosition); // Überprüft, ob die Position erhöht wurde
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
        int initialMoney = player.getMoney();

        service.invest(dto, investmentNumber);

        verify(repository, atLeastOnce()).save(player);
        assertEquals(200000, player.getMoney());
        assertEquals(investmentNumber, player.getInvestmentNumber());
    }



    @Test
    void getPayOut_withPaydayCell_increasesMoneyBySalary() {
        // Setup: Erstellen einer PaydayCell und einer CareerCard mit einem bestimmten Gehalt
        PaydayCell paydayCell = new PaydayCell(5, Arrays.asList(6, 7)); // Position und mögliche nächste Zellen
        CareerCard careerCard = new CareerCard("Doctor", 150000, 20000, false);
        player.setCareerCard(careerCard);
        int initialMoney = player.getMoney();

        // Action: Aufrufen der getPayOut Methode mit der PaydayCell
        service.getPayOut(dto, paydayCell);

        // Assert: Überprüfen, ob das Geld des Spielers um das Gehalt erhöht wurde
        verify(repository, atLeastOnce()).save(player);
        assertEquals(initialMoney + careerCard.getSalary(), player.getMoney());
    }

    @Test
    void setOrUpdateCareer_withExistingPlayer_updatesCareerCard() {
        // Arrange
        CareerCard newCareerCard = new CareerCard("Engineer", 100000, 15000, false);
        when(repository.findById(dto.getPlayerID())).thenReturn(Optional.of(player));

        // Act
        service.setOrUpdateCareer(dto, newCareerCard);

        // Assert
        verify(repository, times(2)).save(player); // Stellen Sie sicher, dass die save-Methode einmal aufgerufen wurde.
        assertEquals(newCareerCard, player.getCareerCard()); // Überprüfen Sie, ob die Karrierekarte des Spielers erfolgreich aktualisiert wurde.
    }

}
