package Back_end;

import Back_end.DTO.PlayerInfo;
import Back_end.Model.Model;
import Back_end.Model.Card;
import Back_end.Model.Player;
import java.util.ArrayList;

public class GameState {
    public ArrayList<PlayerInfo> players = new ArrayList<>();;
    public ArrayList<Integer> middleCards = new ArrayList<>();
    public int currentPlayerIndex;

    public GameState(Model board) {
        for(Player p : board.getPlayers()){
            players.add(new PlayerInfo(p.getName(), p.getPointGame(), p.getHand()));
        }
        for(Card card : board.getMiddleCard()){
            middleCards.add(card.getIntValue());
        }

        currentPlayerIndex = board.getCurrentPlayerIndex();
    }

    public ArrayList<PlayerInfo> getPlayers(){return players;}
    public ArrayList<Integer> getMiddleCards(){return middleCards;}
    public int getCurrentPlayerIndex(){return currentPlayerIndex;}
}