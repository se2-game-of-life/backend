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
        ActionCardDTO dto = new ActionCardDTO(
                actionCard.getName(),
                actionCard.getDescription(),
                actionCard.isAffectOnePlayer(),
                actionCard.isAffectAllPlayers(),
                actionCard.isAffectBank(),
                actionCard.getMoneyAmount()
        );
        dto.setId(actionCard.getId());
        return dto;
    }

    public static List<ActionCardDTO> toActionCardDTOList(List<ActionCard> list){
        List<ActionCardDTO> actionCardDTOS = new ArrayList<>();
        for(ActionCard actionCard : list){
            actionCardDTOS.add(toActionCardDTO(actionCard));
        }
        return actionCardDTOS;
    }

    public static ActionCard toActionCard(ActionCardDTO actionCardDTO) {
        if(actionCardDTO == null) return null;
        return new ActionCard(
                actionCardDTO.getId(),
                actionCardDTO.getName(),
                actionCardDTO.getDescription(),
                actionCardDTO.isAffectOnePlayer(),
                actionCardDTO.isAffectAllPlayers(),
                actionCardDTO.isAffectBank(),
                actionCardDTO.getMoneyAmount()
        );
    }

    public static List<ActionCard> toActionCardList(List<ActionCardDTO> list){
        List<ActionCard> actionCards = new ArrayList<>();
        for(ActionCardDTO dto : list){
            actionCards.add(toActionCard(dto));
        }
        return actionCards;
    }

    public static HouseCardDTO toHouseCardDTO(HouseCard houseCard) {
        if(houseCard == null) return null;
        HouseCardDTO dto =  new HouseCardDTO(
                houseCard.getName(),
                houseCard.getPurchasePrice(),
                houseCard.getRedSellPrice(),
                houseCard.getBlackSellPrice()
        );
        dto.setId(houseCard.getId());
        return dto;
    }

    public static List<HouseCardDTO> toHouseCardDTOList(List<HouseCard> cards){
        ArrayList<HouseCardDTO> houseCardDTOS = new ArrayList<>();
        for(HouseCard houseCard : cards){
            houseCardDTOS.add((toHouseCardDTO(houseCard)));
        }
        return houseCardDTOS;
    }

    public static List<HouseCard> toHouseCardList(List<HouseCardDTO> cards){
        ArrayList<HouseCard> houseCards = new ArrayList<>();
        for(HouseCardDTO dto : cards){
            houseCards.add((toHouseCard(dto)));
        }
        return houseCards;
    }

    public static List<CareerCardDTO> toCareerCardDTOList (List<CareerCard> cards){
        ArrayList<CareerCardDTO> careerCardDTOS = new ArrayList<>();
        for(CareerCard careerCard : cards){
            careerCardDTOS.add((toCareerCardDTO(careerCard)));
        }
        return careerCardDTOS;
    }

    public static List<CareerCard> toCareerCardList (List<CareerCardDTO> cards){
        ArrayList<CareerCard> careerCards = new ArrayList<>();
        for(CareerCardDTO dto : cards){
            careerCards.add((toCareerCard(dto)));
        }
        return careerCards;
    }

    public static HouseCard toHouseCard(HouseCardDTO houseCardDTO) {
        if(houseCardDTO == null) return null;
        return new HouseCard(
                houseCardDTO.getId(),
                houseCardDTO.getName(),
                houseCardDTO.getPurchasePrice(),
                houseCardDTO.getRedSellPrice(),
                houseCardDTO.getBlackSellPrice());
    }

    public static CareerCardDTO toCareerCardDTO(CareerCard careerCard) {
        if(careerCard == null) return null;
        CareerCardDTO dto = new CareerCardDTO(
                careerCard.getName(),
                careerCard.getSalary(),
                careerCard.getBonus(),
                careerCard.needsDiploma()
        );
        dto.setId(careerCard.getId());
        return dto;
    }

    public static CareerCard toCareerCard(CareerCardDTO careerCardDTO) {
        if(careerCardDTO == null) return null;
        return new CareerCard(
                careerCardDTO.getId(),
                careerCardDTO.getName(),
                careerCardDTO.getSalary(),
                careerCardDTO.getBonus(),
                careerCardDTO.needsDiploma()
        );
    }
}
