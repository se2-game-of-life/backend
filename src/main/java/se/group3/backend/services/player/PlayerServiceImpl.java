package se.group3.backend.services.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.game.Cell;
import se.group3.backend.domain.player.Player;
import se.group3.backend.repositories.player.PlayerRepository;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.domain.cards.CareerCard;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

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
}
