package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.cards.CareerCard;

import java.util.List;

public interface CareerCardRepository extends MongoRepository<CareerCard, String> {
    // Add custom query methods if needed
    @Aggregation(pipeline = { "{$sample: {size: 1}}" })
    CareerCard findRandomCareerCard();

    default CareerCard findCareerCardNoDiploma(){
        List<CareerCard> cardList = findAll();
        CareerCard careerCard = findRandomCareerCard();

        for (CareerCard card : cardList){
            if (!card.needsDiploma()){
                careerCard = card;
            }
        }

        return careerCard;
    }

    default CareerCard findCareerCardDiploma(){
        List<CareerCard> cardList = findAll();
        CareerCard careerCard = findRandomCareerCard();

        for (CareerCard card : cardList){
            if (card.needsDiploma()){
                careerCard = card;
            }
        }

        return careerCard;
    }


}
