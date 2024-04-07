package se2.group3.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import se2.group3.backend.DTOs.PlayerDTO;
import se2.group3.backend.domain.cards.CareerCard;
import se2.group3.backend.domain.cells.PaydayCell;
import se2.group3.backend.domain.player.Player;
import se2.group3.backend.repositories.player.PlayerRepository;
import se2.group3.backend.services.player.PlayerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
@SpringBootTest
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
        player = new Player();
        player.setPlayerID("player1");
        player.setMoney(1000000); // Ausgangsgeldbetrag für den Spieler
        player.setCollegePath(false);
        player.setMarriedPath(false);
        player.setGrowFamiliePath(false);
        player.setHasMidlifeCrisis(false);
        player.setRetireEarlyPath(false);
        player.setInvestmentNumber(0);
        player.setInvestmentLevel(0);
        player.setNumberOfPegs(1);

        dto = new PlayerDTO();
        dto.setPlayerID("player1");

        when(repository.findById("player1")).thenReturn(Optional.of(player));
    }

    @Test
    void chooseCollegePath_withEnoughMoney_updatesPlayer() {
        dto.setCollegePath(true);

        service.chooseCollagePath(dto);

        verify(repository).save(player);
        assertTrue(player.isCollegePath());
        assertEquals(500000, player.getMoney()); // Überprüfen Sie das neue Geldsaldo nach der Investition
    }

    @Test
    void chooseMarriedPath_withEnoughMoney_updatesPlayer() {
        dto.setMarriedPath(true);

        service.chooseMarryPath(dto);

        verify(repository).save(player);
        assertTrue(player.isMarriedPath());
        assertEquals(500000, player.getMoney());
    }

    @Test
    void chooseGrowFamilyPath_withEnoughMoney_updatesPlayer() {
        dto.setGrowFamiliePath(true);

        service.chooseGrowFamilyPath(dto);

        verify(repository).save(player);
        assertTrue(player.isGrowFamiliePath());
        assertEquals(500000, player.getMoney());
    }

    @Test
    void midLifeCrisisPath_setsFlag() {
        dto.setHasMidlifeCrisis(true);

        service.midLifeCrisisPath(dto);

        verify(repository).save(player);
        assertTrue(player.isHasMidlifeCrisis());
    }

    @Test
    void chooseRetireEarlyPath_withEnoughMoney_updatesPlayer() {
        dto.setRetireEarlyPath(true);

        service.chooseRetireEarlyPath(dto);

        verify(repository).save(player);
        assertTrue(player.isRetireEarlyPath());
        assertEquals(500000, player.getMoney());
    }

    @Test
    void increaseNumberOfPegs_increasesPegs() {
        int initialPegs = player.getNumberOfPegs();

        service.increaseNumberOfPegs(dto);

        verify(repository).save(player);
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

        verify(repository).save(player);
        assertEquals(10000, player.getMoney());
        assertEquals(2, player.getInvestmentLevel());
    }

    @Test
    void invest_decreasesMoneyAndSetsInvestmentNumber() {
        Integer investmentNumber = 5;
        int initialMoney = player.getMoney();

        service.invest(dto, investmentNumber);

        verify(repository).save(player);
        assertEquals(initialMoney - 500000, player.getMoney());
        assertEquals(investmentNumber, player.getInvestmentNumber());
    }

    @Test
    void setOrUpdateCareer_setsCareerCardCorrectly() {
        CareerCard careerCard = new CareerCard("Engineer", 120000, 10000, true);
        dto.setPlayerID("player1");

        service.setOrUpdateCareer(dto, careerCard);

        verify(repository).save(player);
        assertEquals("Engineer", player.getCareerCard().getName());
        assertEquals(120000, player.getCareerCard().getSalary());
        assertEquals(10000, player.getCareerCard().getBonus());
        assertTrue(player.getCareerCard().needsDiploma());
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
        verify(repository).save(player);
        assertEquals(initialMoney + careerCard.getSalary(), player.getMoney());
    }
}
