package se2.group3.backend.services.player;

import org.springframework.stereotype.Service;
import se2.group3.backend.DTOs.PlayerDTO;
import se2.group3.backend.domain.cards.Card;
import se2.group3.backend.domain.cards.CareerCard;
import se2.group3.backend.domain.cells.Cell;
import se2.group3.backend.domain.cells.StopCell;

import java.util.List;

@Service
public interface PlayerService {

    //different Path Options
    void chooseCollagePath(PlayerDTO dto);

    void chooseMarryPath(PlayerDTO dto);

    void chooseGrowFamilyPath(PlayerDTO dto);

    void midLifeCrisisPath(PlayerDTO dto);

    void chooseRetireEarlyPath(PlayerDTO dto);

    void increaseNumberOfPegs(PlayerDTO dto);

    void invest(PlayerDTO dto, Integer investmentNumber);

    void collectInvestmentPayout(PlayerDTO dto, Integer spinResult);

    void setOrUpdateCareer(PlayerDTO dto, CareerCard careerCards);

    void getPayOut(PlayerDTO dto, Cell passedCell);

    void spin(PlayerDTO dto);

    void checkCellAndPerformAction(PlayerDTO dto, Cell cell);

    void checkStopCellAndPerformAction(PlayerDTO dto, StopCell stopCell);

}