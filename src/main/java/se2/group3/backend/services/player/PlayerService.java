package se2.group3.backend.services.player;

import org.springframework.stereotype.Service;
import se2.group3.backend.domain.cards.CareerCard;
import se2.group3.backend.domain.cells.Cell;

@Service
public interface PlayerService {

    //different Path Options
    void chooseCollagePath(String playerID);

    void chooseMarryPath(String playerID);

    void chooseGrowFamilyPath(String playerID);

    void midLifeCrisisPath(String playerID);

    void chooseRetireEarlyPath(String playerID);


    void increaseNumberOfPegs(String playerID, Integer amount);

    void invest(String playerID, Integer investmentNumber);

    void collectInvestmentPayout(String playerID, Integer spinResult);

    void setOrUpdateCareer(String playerID, CareerCard careerCards);

    void getPayOut(String playerID, Cell passedCell);

    void spin(String playerID);

}
