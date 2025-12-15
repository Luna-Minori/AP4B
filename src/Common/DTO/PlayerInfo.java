package Common.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerInfo implements Serializable {
    public String name;
    public int id;
    public ArrayList<Integer> hand;
    public boolean isCurrentPlayer;
    public PlayerInfo(String name, int id, ArrayList<Integer> hand, boolean isCurrentPlayer) {
        this.name = name;
        this.id = id;
        this.hand = hand;
        this.isCurrentPlayer = isCurrentPlayer;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }
}