package se.group3.backend.dto.mapper;

import se.group3.backend.domain.cards.ActionCard;
import se.group3.backend.domain.cards.Card;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.dto.ActionCardDTO;
import se.group3.backend.dto.CardDTO;
import se.group3.backend.dto.CareerCardDTO;
import se.group3.backend.dto.HouseCardDTO;

import java.util.ArrayList;
import java.util.List;

public class CardMapper {

    public static List<CardDTO> toCardDTO(List<Card> cards) {
        List<CardDTO> dtoList = new ArrayList<>();
        for(Card card : cards) {
            if (card instanceof ActionCard) {
                dtoList.add(toActionCardDTO((ActionCard) card));
            } else if (card instanceof HouseCard) {
                dtoList.add(toHouseCardDTO((HouseCard) card));
            } else if (card instanceof CareerCard) {
                dtoList.add(toCareerCardDTO((CareerCard) card));
            }

        }
        return dtoList;
    }

    public static List<Card> toCard(List<CardDTO> cardDTOs) {
        List<Card> cardList = new ArrayList<>();
        for(CardDTO cardDTO : cardDTOs) {
            if (cardDTO instanceof ActionCardDTO) {
                cardList.add(toActionCard((ActionCardDTO) cardDTO));
            } else if (cardDTO instanceof HouseCardDTO) {
                cardList.add(toHouseCard((HouseCardDTO) cardDTO));
            } else if (cardDTO instanceof CareerCardDTO) {
                cardList.add(toCareerCard((CareerCardDTO) cardDTO));
            }
        }
        return cardList;
    }

    public static ActionCardDTO toActionCardDTO(ActionCard actionCard) {
        if(actionCard == null) return null;
        return new ActionCardDTO(
                actionCard.getId(),
                actionCard.getName(),
                actionCard.getDescription(),
                actionCard.isAffectOnePlayer(),
                actionCard.isAffectAllPlayers(),
                actionCard.isAffectBank(),
                actionCard.getMoneyAmount()
        );
    }

    public static ActionCard toActionCard(ActionCardDTO actionCardDTO) {
        if(actionCardDTO == null) return null;
        return new ActionCard(
                actionCardDTO.getName(),
                actionCardDTO.getDescription(),
                actionCardDTO.isAffectOnePlayer(),
                actionCardDTO.isAffectAllPlayers(),
                actionCardDTO.isAffectBank(),
                actionCardDTO.getMoneyAmount()
        );
    }

    public static HouseCardDTO toHouseCardDTO(HouseCard houseCard) {
        if(houseCard == null) return null;
        return new HouseCardDTO(
                houseCard.getId(),
                houseCard.getName(),
                houseCard.getPurchasePrice(),
                houseCard.getRedSellPrice(),
                houseCard.getBlackSellPrice()
        );
    }

    public static HouseCard toHouseCard(HouseCardDTO houseCardDTO) {
        if(houseCardDTO == null) return null;
        return new HouseCard(
                houseCardDTO.getName(),
                houseCardDTO.getPurchasePrice(),
                houseCardDTO.getRedSellPrice(),
                houseCardDTO.getBlackSellPrice());
    }

    public static CareerCardDTO toCareerCardDTO(CareerCard careerCard) {
        if(careerCard == null) return null;
        return new CareerCardDTO(
                careerCard.getId(),
                careerCard.getName(),
                careerCard.getSalary(),
                careerCard.getBonus(),
                careerCard.needsDiploma()
        );
    }

    public static CareerCard toCareerCard(CareerCardDTO careerCardDTO) {
        if(careerCardDTO == null) return null;
        return new CareerCard(
                careerCardDTO.getName(),
                careerCardDTO.getSalary(),
                careerCardDTO.getBonus(),
                careerCardDTO.needsDiploma()
        );
    }
}
