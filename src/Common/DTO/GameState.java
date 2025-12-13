package Common.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    public ArrayList<PlayerInfo> players;
    public ArrayList<CardInfo> middleCards;

    public GameState(ArrayList<PlayerInfo> players, ArrayList<CardInfo> middleCards) {
        this.players = players;
        this.middleCards = middleCards;
    }

    public ArrayList<CardInfo> getMiddleCards() {
        return middleCards;
    }

    public ArrayList<PlayerInfo> getPlayers() {
        return players;
    }
}