package se2.group3.backend.services.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se2.group3.backend.domain.cards.CareerCard;
import se2.group3.backend.domain.cells.Cell;
import se2.group3.backend.domain.cells.PaydayCell;
import se2.group3.backend.domain.player.Player;
import se2.group3.backend.repositories.player.PlayerRepository;

import java.util.Random;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final Integer INVESTMENTCOST = 500000;

    private final Integer INVESTMENTMULTIPLIER = 10000;

    private final PlayerRepository repository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void chooseCollagePath(String playerID) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            if (player.getMoney() > INVESTMENTCOST) {
                player.setMoney(player.getMoney() - INVESTMENTCOST);
                player.setCollegePath(true);
                repository.save(player);
            }
        }

    }

    @Override
    public void chooseMarryPath(String playerID) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            if (player.getMoney() > INVESTMENTCOST) {
                player.setMoney(player.getMoney() - INVESTMENTCOST);
                player.setMarriedPath(true);
                repository.save(player);
            }
        }
    }

    @Override
    public void chooseGrowFamilyPath(String playerID) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            if (player.getMoney() > INVESTMENTCOST) {
                player.setMoney(player.getMoney() - INVESTMENTCOST);
                player.setGrowFamiliePath(true);
                repository.save(player);
            }
        }
    }

    @Override
    public void midLifeCrisisPath(String playerID) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            player.setHasMidlifeCrisis(true);
            repository.save(player);

        }
    }

    @Override
    public void chooseRetireEarlyPath(String playerID) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            if (player.getMoney() > INVESTMENTCOST) {
                player.setMoney(player.getMoney() - INVESTMENTCOST);
                player.setRetireEarlyPath(true);
                repository.save(player);
            }
        }
    }

    @Override
    public void increaseNumberOfPegs(String playerID, Integer amount) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            player.setNumberOfPegs(player.getNumberOfPegs() + amount);
            repository.save(player);
        }
    }

    @Override
    public void invest(String playerID, Integer investmentNumber) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();

            player.setMoney(player.getMoney() - INVESTMENTCOST);
            player.setInvestmentNumber(investmentNumber);

            repository.save(player);
        }
    }

    @Override
    public void collectInvestmentPayout(String playerID, Integer spinResult) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();

            if (player.getInvestmentNumber() == spinResult) {
                player.setMoney(player.getInvestmentLevel() * INVESTMENTMULTIPLIER);
                player.setInvestmentLevel(player.getInvestmentLevel() + 1);
                repository.save(player);
            }

        }
    }
    @Override
    public void setOrUpdateCareer(String playerID, CareerCard careerCard) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();

            player.setCareerCard(careerCard);
            repository.save(player);
        }
    }

    @Override
    public void getPayOut(String playerID, Cell passedCell) {
        if (passedCell instanceof PaydayCell) {
            if (repository.findById(playerID).isPresent()) {
                Player player = repository.findById(playerID).get();
                player.setMoney(player.getMoney() + player.getCareerCard().getSalary());

                repository.save(player);
            }
        }
    }

    @Override
    public void spin(String playerID) {
        Random spinResult = new Random();
        if(repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            player.setCurrentCellPosition(player.getCurrentCellPosition() + spinResult.nextInt(10));
        }
    }

}
