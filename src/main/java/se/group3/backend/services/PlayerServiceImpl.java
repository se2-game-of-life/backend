package se.group3.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.domain.Player;
import se.group3.backend.repositories.PlayerRepository;
import se.group3.backend.domain.cards.CareerCard;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void increaseNumberOfPegs(String playerID) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            player.setNumberOfPegs(player.getNumberOfPegs() + 1);
            repository.save(player);
        }
    }


    @Override
    public void setCareer(String playerID, CareerCard careerCard) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();

            player.setCareerCard(careerCard);
            repository.save(player);
        }
    }

    @Override
    public void getSalary(String playerID) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            player.setMoney(player.getMoney() + player.getCareerCard().getSalary());
            repository.save(player);
        }
    }

    @Override
    public void getSalaryWithBonus(String playerID) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            player.setMoney(player.getMoney() + player.getCareerCard().getBonus());
            repository.save(player);
        }
    }

    @Override
    public void addHouse(String playerID, HouseCard houseCard) {
        if (repository.findById(playerID).isPresent()) {
            Player player = repository.findById(playerID).get();
            player.getHouses().add(houseCard);
            repository.save(player);
        }
    }
}
