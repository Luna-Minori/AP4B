package Common.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    public ArrayList<PlayerInfo> players;
    public ArrayList<Integer> middleCards;

    public GameState(ArrayList<PlayerInfo> players, ArrayList<Integer> middleCards) {
        this.players = players;
        this.middleCards = middleCards;
    }

    public ArrayList<Integer> getMiddleCards() {
        return middleCards;
    }

    public ArrayList<PlayerInfo> getPlayers() {
        return players;
    }
}