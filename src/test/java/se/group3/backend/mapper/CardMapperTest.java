package se.group3.backend.mapper;



import org.junit.jupiter.api.Test;
import se.group3.backend.domain.cards.ActionCard;
import se.group3.backend.domain.cards.Card;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.dto.ActionCardDTO;
import se.group3.backend.dto.CardDTO;
import se.group3.backend.dto.CareerCardDTO;
import se.group3.backend.dto.HouseCardDTO;
import se.group3.backend.dto.mapper.CardMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CardMapperTest {

    private final ActionCard actioncard1 = new ActionCard("123", "actioncard", "describtion", true, false, false, 100);
    private final ActionCard actioncard2 = new ActionCard("123", "actioncard2", "describtion", true, false, false, 100);
    private final ActionCardDTO actioncardDTO1 = new ActionCardDTO("actioncard", "describtion", true, false, false, 100);
    private final ActionCardDTO actioncardDTO2 = new ActionCardDTO("actioncard2", "describtion", true, false, false, 100);
    private final HouseCard houseCard1 = new HouseCard("123", "house", 100, 100, 100);
    private final HouseCard houseCard2 = new HouseCard("123", "house2", 100, 100, 100);
    private final HouseCardDTO houseCardDTO1 = new HouseCardDTO("house", 100, 100, 100);
    private final HouseCardDTO houseCardDTO2 = new HouseCardDTO("house2", 100, 100, 100);
    private final CareerCard careerCard1 = new CareerCard("123", "career", 100, 100, true);
    private final CareerCard careerCard2 = new CareerCard("123", "career2", 100, 100, true);
    private final CareerCardDTO careerCardDTO1 = new CareerCardDTO("career", 100, 100, true);
    private final CareerCardDTO careerCardDTO2 = new CareerCardDTO("career2", 100, 100, true);


    @Test
    void testToActionCardDTO_null(){
        ActionCard actioncard = null;
        ActionCardDTO dto = CardMapper.toActionCardDTO(actioncard);
        assertNull(dto);
    }

    @Test
    void testToActionCardDTO_notNull(){
        ActionCard actioncard = new ActionCard("123", "actioncard", "describtion", true, false, false, 100);
        ActionCardDTO dto = CardMapper.toActionCardDTO(actioncard);
        assertEquals(actioncard.getName(), dto.getName());
        assertEquals(actioncard.getMoneyAmount(), dto.getMoneyAmount());
    }

    @Test
    void testToActionCardDTOList(){
        List<ActionCard> actionCards = List.of(actioncard1, actioncard2);
        List<ActionCardDTO> dtos = CardMapper.toActionCardDTOList(actionCards);

        assertEquals(actionCards.get(0).getName(), dtos.get(0).getName());
        assertEquals(actionCards.get(1).getName(), dtos.get(1).getName());
        assertEquals(actionCards.get(0).getMoneyAmount(), dtos.get(0).getMoneyAmount());
        assertEquals(actionCards.get(1).getMoneyAmount(), dtos.get(1).getMoneyAmount());
    }

    @Test
    void testToActionCardList(){
        List<ActionCardDTO> dto = List.of(actioncardDTO1, actioncardDTO2);
        List<ActionCard> actioncards = CardMapper.toActionCardList(dto);

        assertEquals(dto.get(0).getName(), actioncards.get(0).getName());
        assertEquals(dto.get(1).getName(), actioncards.get(1).getName());
        assertEquals(dto.get(0).getMoneyAmount(), actioncards.get(0).getMoneyAmount());
        assertEquals(dto.get(1).getMoneyAmount(), actioncards.get(1).getMoneyAmount());
    }

    @Test
    void testToHouseCardDTO_null(){
        HouseCard houseCard = null;
        HouseCardDTO dto = CardMapper.toHouseCardDTO(houseCard);
        assertNull(dto);
        assertNull(CardMapper.toHouseCardDTO(houseCard));
    }

    @Test
    void testToHouseCardDTO_notNull(){
        HouseCardDTO dto = CardMapper.toHouseCardDTO(houseCard1);
        assertEquals(houseCard1.getName(), dto.getName());
        assertEquals(houseCard1.getPurchasePrice(), dto.getPurchasePrice());
    }

    @Test
    void testToHouseCardDTOList(){
        List<HouseCard> houseCards = List.of(houseCard1, houseCard2);
        List<HouseCardDTO> dtos = CardMapper.toHouseCardDTOList(houseCards);

        assertEquals(houseCards.get(0).getName(), dtos.get(0).getName());
        assertEquals(houseCards.get(1).getName(), dtos.get(1).getName());
        assertEquals(houseCards.get(0).getPurchasePrice(), dtos.get(0).getPurchasePrice());
        assertEquals(houseCards.get(1).getPurchasePrice(), dtos.get(1).getPurchasePrice());
    }

    @Test
    void testToHouseCardList(){
        List<HouseCardDTO> dto = List.of(houseCardDTO1, houseCardDTO2);
        List<HouseCard> housecards = CardMapper.toHouseCardList(dto);

        assertEquals(dto.get(0).getName(), housecards.get(0).getName());
        assertEquals(dto.get(1).getName(), housecards.get(1).getName());
        assertEquals(dto.get(0).getPurchasePrice(), housecards.get(0).getPurchasePrice());
        assertEquals(dto.get(1).getPurchasePrice(), housecards.get(1).getPurchasePrice());
    }

    @Test
    void testToCareerCardDTO_null(){
        CareerCard careerCard = null;
        CareerCardDTO dto = CardMapper.toCareerCardDTO(careerCard);
        assertNull(dto);
        assertNull(CardMapper.toCareerCardDTO(careerCard));
    }

    @Test
    void testToCareerCardDTO_notNull(){
        CareerCardDTO dto = CardMapper.toCareerCardDTO(careerCard1);
        assertEquals(careerCard1.getName(), dto.getName());
        assertEquals(careerCard1.getBonus(), dto.getBonus());
    }

    @Test
    void testToCareerCardDTOList(){
        List<CareerCard> careerCards = List.of(careerCard1, careerCard2);
        List<CareerCardDTO> dtos = CardMapper.toCareerCardDTOList(careerCards);

        assertEquals(careerCards.get(0).getName(), dtos.get(0).getName());
        assertEquals(careerCards.get(1).getName(), dtos.get(1).getName());
        assertEquals(careerCards.get(0).getBonus(), dtos.get(0).getBonus());
        assertEquals(careerCards.get(1).getBonus(), dtos.get(1).getBonus());
    }

    @Test
    void testToCareerCardList(){
        List<CareerCardDTO> dto = List.of(careerCardDTO1, careerCardDTO2);
        List<CareerCard> careerCards = CardMapper.toCareerCardList(dto);

        assertEquals(dto.get(0).getName(), careerCards.get(0).getName());
        assertEquals(dto.get(1).getName(), careerCards.get(1).getName());
        assertEquals(dto.get(0).getBonus(), careerCards.get(0).getBonus());
        assertEquals(dto.get(1).getBonus(), careerCards.get(1).getBonus());
    }

    @Test
    void testToCardDTO(){
        List<Card> cards = List.of(careerCard1, actioncard1, houseCard1);
        List<CardDTO> dto = CardMapper.toCardDTO(cards);
        CareerCard card = (CareerCard) cards.get(0);
        CareerCardDTO careerCardDTO = (CareerCardDTO) dto.get(0);
        HouseCard houseCard = (HouseCard) cards.get(2);
        HouseCardDTO houseCardDTO = (HouseCardDTO) dto.get(2);
        ActionCard actionCard = (ActionCard) cards.get(1);
        ActionCardDTO actionCardDTO = (ActionCardDTO) dto.get(1);

        assertEquals(card.getBonus(), careerCardDTO.getBonus());
        assertEquals(houseCard.getPurchasePrice(), houseCardDTO.getPurchasePrice());
        assertEquals(actionCard.getMoneyAmount(), actionCardDTO.getMoneyAmount());
    }

    @Test
    void testToCard(){
        List<CardDTO> dtos = List.of(careerCardDTO1, actioncardDTO1, houseCardDTO1);
        List<Card> cards = CardMapper.toCard(dtos);
        CareerCard card = (CareerCard) cards.get(0);
        CareerCardDTO careerCardDTO = (CareerCardDTO) dtos.get(0);
        HouseCard houseCard = (HouseCard) cards.get(2);
        HouseCardDTO houseCardDTO = (HouseCardDTO) dtos.get(2);
        ActionCard actionCard = (ActionCard) cards.get(1);
        ActionCardDTO actionCardDTO = (ActionCardDTO) dtos.get(1);


        assertEquals(careerCardDTO.getBonus(), card.getBonus());
        assertEquals(houseCardDTO.getPurchasePrice(), houseCard.getPurchasePrice());
        assertEquals(actionCardDTO.getMoneyAmount(), actionCard.getMoneyAmount());
    }


}
