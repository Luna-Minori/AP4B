package Back_end.DTO;

import Back_end.Model.Card;
import Back_end.Model.PlayerCardRequest;

import java.util.ArrayList;

public class PlayerInfo {
    public final String name;
    public final int pointGame;
    public ArrayList<Integer> hand = new ArrayList<>(); // Cards held by the player


    public PlayerInfo(String name, int pointGame, ArrayList<Card> cards) {
        this.name = name;
        this.pointGame = pointGame;
        for(Card card : cards){
            this.hand.add(card.getIntValue());
        }
    }

    public String getName(){return name;}
    public int getPointGame(){return pointGame;}
    public ArrayList<Integer> getHand(){return hand;}
}