package se2.group3.backend.services.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se2.group3.backend.DTOs.PlayerDTO;
import se2.group3.backend.domain.cards.CareerCard;
import se2.group3.backend.domain.cells.*;
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
    public void chooseCollagePath(PlayerDTO dto) {
        if(dto.isCollegePath()) {
            if (repository.findById(dto.getPlayerID()).isPresent()) {
                Player player = repository.findById(dto.getPlayerID()).get();
                if (player.getMoney() > INVESTMENTCOST) {
                    player.setMoney(player.getMoney() - INVESTMENTCOST);
                    player.setCollegePath(true);
                    repository.save(player);
                }
            }
        }
    }

    @Override
    public void chooseMarryPath(PlayerDTO dto) {
        if(dto.isMarriedPath()) {
            if (repository.findById(dto.getPlayerID()).isPresent()) {
                Player player = repository.findById(dto.getPlayerID()).get();
                if (player.getMoney() > INVESTMENTCOST) {
                    player.setMoney(player.getMoney() - INVESTMENTCOST);
                    player.setMarriedPath(true);
                    repository.save(player);
                }
            }
        }
    }

    @Override
    public void chooseGrowFamilyPath(PlayerDTO dto) {
        if(dto.isGrowFamiliePath()) {
            if (repository.findById(dto.getPlayerID()).isPresent()) {
                Player player = repository.findById(dto.getPlayerID()).get();
                if (player.getMoney() > INVESTMENTCOST && dto.isGrowFamiliePath()) {
                    player.setMoney(player.getMoney() - INVESTMENTCOST);
                    player.setGrowFamiliePath(true);
                    repository.save(player);
                }
            }

        }
    }

    @Override
    public void midLifeCrisisPath(PlayerDTO dto) {
        if(dto.isHasMidlifeCrisis()) {
            if (repository.findById(dto.getPlayerID()).isPresent()) {
                Player player = repository.findById(dto.getPlayerID()).get();
                player.setHasMidlifeCrisis(true);
                repository.save(player);
            }
        }
    }

    @Override
    public void chooseRetireEarlyPath(PlayerDTO dto) {
        if(dto.isRetireEarlyPath()) {
            if (repository.findById(dto.getPlayerID()).isPresent()) {
                Player player = repository.findById(dto.getPlayerID()).get();
                if (player.getMoney() > INVESTMENTCOST) {
                    player.setMoney(player.getMoney() - INVESTMENTCOST);
                    player.setRetireEarlyPath(true);
                    repository.save(player);
                }
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

            player.setMoney(player.getMoney() - INVESTMENTCOST);
            player.setInvestmentNumber(investmentNumber);

            repository.save(player);
        }
    }

    @Override
    public void collectInvestmentPayout(PlayerDTO dto, Integer spinResult) {
        if (repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();

            if (player.getInvestmentNumber() == spinResult) {
                player.setMoney(player.getInvestmentLevel() * INVESTMENTMULTIPLIER);
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
        if (passedCell instanceof PaydayCell) {
            if (repository.findById(dto.getPlayerID()).isPresent()) {
                Player player = repository.findById(dto.getPlayerID()).get();
                player.setMoney(player.getMoney() + player.getCareerCard().getSalary());

                repository.save(player);
            }
        }
    }

    @Override
    public void spin(PlayerDTO dto) {
        Random spinResult = new Random();
        if(repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            player.setCurrentCellPosition(player.getCurrentCellPosition() + spinResult.nextInt(10));
        }
    }

    @Override
    public void checkCellAndPerformAction(PlayerDTO dto, Cell cell) {
        if(repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            //todo: implement the logic when a player steps on a cell
            if(cell instanceof ActionCell) {

            }
            if(cell instanceof AddPegCell) {

            }
            if(cell instanceof CareerCell) {

            }
            if(cell instanceof FinalRetirementCell) {

            }
            if(cell instanceof HouseCell) {

            }
            if(cell instanceof InvestCell) {

            }
            if(cell instanceof PaydayCell) {

            }
        }
    }

    @Override
    public void checkStopCellAndPerformAction(PlayerDTO dto, StopCell stopCell) {
        if(repository.findById(dto.getPlayerID()).isPresent()) {
            Player player = repository.findById(dto.getPlayerID()).get();
            if(stopCell instanceof SpinToGraduateStopCell) {
                stopCell.performAction(player);
            }
            if(stopCell instanceof GetMarriedStopCell) {
               //todo: logic of function not yet implemented
                stopCell.performAction(player);
            }
            //todo: rest of stop cells not yet implemented
        }
    }


}
