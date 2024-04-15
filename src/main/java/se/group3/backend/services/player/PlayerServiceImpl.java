package se.group3.backend.services.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.cells.Cell;
import se.group3.backend.domain.cells.PaydayCell;
import se.group3.backend.domain.cells.stopcells.StopCell;
import se.group3.backend.domain.player.Player;
import se.group3.backend.repositories.player.PlayerRepository;
import se.group3.backend.DTOs.PlayerDTO;
import se.group3.backend.domain.cards.CareerCard;

import java.security.SecureRandom;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Integer INVESTMENT = 50000;


    //the tests do not pass, if the repository is initialized in a constructor
    private final PlayerRepository repository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void chooseCollagePath(PlayerDTO dto) {
        if (dto.isCollegePath() && repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            if (player.getMoney() > INVESTMENT) {
                player.setMoney(player.getMoney() - INVESTMENT);
                player.setCollegePath(true);
                repository.save(player);
            }
        }
    }

    @Override
    public void chooseMarryPath(PlayerDTO dto) {
        if (dto.isMarriedPath() && repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            if (player.getMoney() > INVESTMENT) {
                player.setMoney(player.getMoney() - INVESTMENT);
                player.setMarriedPath(true);
                repository.save(player);
            }

        }
    }

    @Override
    public void chooseGrowFamilyPath(PlayerDTO dto) {
        if (dto.isGrowFamiliePath() && repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            if (player.getMoney() > INVESTMENT && dto.isGrowFamiliePath()) {
                player.setMoney(player.getMoney() - INVESTMENT);
                player.setGrowFamiliePath(true);
                repository.save(player);
            }

        }
    }

    @Override
    public void midLifeCrisisPath(PlayerDTO dto) {
        if (dto.isHasMidlifeCrisis() && repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            player.setHasMidlifeCrisis(true);
            repository.save(player);
        }
    }

    @Override
    public void chooseRetireEarlyPath(PlayerDTO dto) {
        if (dto.isRetireEarlyPath() && repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            if (player.getMoney() > INVESTMENT) {
                player.setMoney(player.getMoney() - INVESTMENT);
                player.setRetireEarlyPath(true);
                repository.save(player);
            }
        }
    }

    @Override
    public void increaseNumberOfPegs(PlayerDTO dto) {
        if (repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            player.setNumberOfPegs(player.getNumberOfPegs() + 1);
            repository.save(player);
        }
    }

    @Override
    public void invest(PlayerDTO dto, Integer investmentNumber) {
        if (repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();

            player.setMoney(player.getMoney() - INVESTMENT);
            player.setInvestmentNumber(investmentNumber);

            repository.save(player);
        }
    }

    @Override
    public void collectInvestmentPayout(PlayerDTO dto, Integer spinResult) {
        if (repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();

            if (player.getInvestmentNumber() == spinResult) {
                int MULTIPLIER = 10000;
                player.setMoney(player.getInvestmentLevel() * MULTIPLIER);
                player.setInvestmentLevel(player.getInvestmentLevel() + 1);
                repository.save(player);
            }

        }
    }

    @Override
    public void setOrUpdateCareer(PlayerDTO dto, CareerCard careerCard) {
        if (repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();

            player.setCareerCard(careerCard);
            repository.save(player);
        }
    }

    @Override
    public void getPayOut(PlayerDTO dto, Cell passedCell) {
        if (passedCell instanceof PaydayCell && repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            player.setMoney(player.getMoney() + player.getCareerCard().getSalary());

            repository.save(player);
        }
    }

    @Override
    public void spin(PlayerDTO dto) {
        SecureRandom spinResult = new SecureRandom();
        if (repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            player.setCurrentCellPosition(player.getCurrentCellPosition() + spinResult.nextInt(10));
        }
    }

    @Override
    public void checkCellAndPerformAction(PlayerDTO dto, Cell cell) {
        /*if (repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            //todo: implement the logic when a player steps on a cell
            if (cell instanceof ActionCell) {

            }
            if (cell instanceof AddPegCell) {

            }
            if (cell instanceof CareerCell) {

            }
            if (cell instanceof FinalRetirementCell) {

            }
            if (cell instanceof HouseCell) {

            }
            if (cell instanceof InvestCell) {

            }
            if (cell instanceof PaydayCell) {

            }
        }
        */
    }

    @Override
    public void checkStopCellAndPerformAction(PlayerDTO dto, StopCell stopCell) {
        /*
        if (repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            if (stopCell instanceof SpinToGraduateStopCell) {
                stopCell.performAction(player);
            }
            if (stopCell instanceof GetMarriedStopCell) {
                //todo: logic of function not yet implemented
                stopCell.performAction(player);
            }
            //todo: rest of stop cells not yet implemented
        }

         */
    }


}
