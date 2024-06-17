package se.group3.backend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.group3.backend.domain.cards.ActionCard;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;

import java.util.*;

@Slf4j
@Getter
@Setter
@Document(collection = "lobby")
public class Lobby {

    @Id
    private long lobbyID;
    private static final Short MAXIMUM_PLAYER_COUNT = 4;
    private List<Player> players;
    private Player currentPlayer;
    private boolean hasDecision;
    private List<ActionCard> actionCards;
    private List<CareerCard> careerCards;
    private List<HouseCard> houseCards;
    private int spunNumber;
    private boolean hasStarted;

    private ArrayList<Player> queue;

    public Lobby(Long lobbyID, Player currentPlayer) {
        this.lobbyID = lobbyID;
        this.currentPlayer = currentPlayer;
        players = new ArrayList<>(MAXIMUM_PLAYER_COUNT);
        queue = new ArrayList<>(MAXIMUM_PLAYER_COUNT);
        actionCards = new ArrayList<>(2);
        careerCards = new ArrayList<>(2);
        houseCards = new ArrayList<>(2);
        hasStarted = false;
        hasDecision = false;
        spunNumber = 0;
        addPlayer(currentPlayer);
    }

    public void addPlayer(Player player) {
       players.add(player);
       queue.add(player);
    }

    public void removePlayer(String uuid) {
        players.removeIf(player -> player.getPlayerUUID().equals(uuid));
    }

    public void nextPlayer() {
        queue.remove(0);
        queue.add(currentPlayer);
        currentPlayer = queue.get(0);
    }


    public void nextPlayerRetired() {
        if (!queue.isEmpty()) {
            queue.remove(0);
            if (!queue.isEmpty()) {
                currentPlayer = queue.get(0);
            } else {
                hasStarted = false;
            }
        }
    }

    public void updatePlayerInLobby(Player player){
        List<Player> updatesPlayers = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPlayerUUID().equals(player.getPlayerUUID())){
                updatesPlayers.add(i, player);
            } else{
                updatesPlayers.add(i, players.get(i));
            }
            if(updatesPlayers.get(i).getPlayerUUID().equals(getCurrentPlayer().getPlayerUUID())){
                setCurrentPlayer(updatesPlayers.get(i));
            }
        }
       setPlayers(updatesPlayers);
    }

    public boolean isFull() {
        return players.size() >= MAXIMUM_PLAYER_COUNT;
    }

}
