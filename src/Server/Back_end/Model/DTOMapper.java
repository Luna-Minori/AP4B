package Server.Back_end.Model;

import Common.DTO.PlayerInfo;
import Common.DTO.CardInfo;
import Common.DTO.GameState;
import java.util.ArrayList;

public class DTOMapper {


    public GameState createSecureGameState(Model board, int targetClientId) {
        ArrayList<PlayerInfo> listPlayersInfo = new ArrayList<>();

        for (Player p : board.getPlayers()) {
            ArrayList<CardInfo> handInfo = new ArrayList<>();

            for (Card c : p.getHand()) {

                if (c.isRevealed() || p.getId() == targetClientId) {
                    handInfo.add(new CardInfo(c.getIntValue(), c.isRevealed()));
                } else {
                    handInfo.add(new CardInfo(null, c.isRevealed())); // NULL pour cacher la valeur pas de triche si un client modifie les infos
                }
            }

            boolean isActive = (board.getCurrentPlayerIndex() == p.getId());

            listPlayersInfo.add(new PlayerInfo(p.getName(), p.getId(), handInfo, isActive));
        }
        ArrayList<CardInfo> midCards = new ArrayList<>();

        for(Card c : board.getMiddleCard()) {
            if (c.isRevealed()) {
                midCards.add(new CardInfo(c.getIntValue(), c.isRevealed()));
            } else {
                midCards.add(new CardInfo(null, c.isRevealed())); // NULL pour cacher la valeur pas de triche si un client modifie les infos
            }
        }
        return new GameState(listPlayersInfo, midCards);
    }
}
