package Common.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerInfo implements Serializable {
    public String name;
    public int id;
    public ArrayList<CardInfo> hand;
    public boolean isCurrentPlayer;
    public PlayerInfo(String name, int id, ArrayList<CardInfo> hand, boolean isCurrentPlayer) {
        this.name = name;
        this.id = id;
        this.hand = hand;
        this.isCurrentPlayer = isCurrentPlayer;
    }

    public int getId() {
        return id;
    }
}