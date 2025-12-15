package Server.Back_end.Model;

import Common.DTO.PlayerInfo;
import Common.DTO.GameState;
import java.util.ArrayList;

public class DTOMapper {

    public GameState createSecureGameState(Model board, int targetClientId) {
        ArrayList<PlayerInfo> listPlayersInfo = new ArrayList<>();

        for (Player p : board.getPlayers()) {
                ArrayList<Integer> handInfo = new ArrayList<>();
                System.out.println("Target client ID: " + targetClientId + ", Processing player ID: " + p.getId());
                for (Card c : p.getHand()) {

                    if (c.isRevealed() || p.getId() == targetClientId) {
                        handInfo.add(c.getIntValue());
                    } else {
                        handInfo.add(null); // -1 pour cacher la valeur pas de triche si un client regarde les infos
                    }
                }

                for (Integer i : handInfo) {
                    System.out.println("hand card value sent to client: " + i);
                }

                boolean isActive = (board.getCurrentPlayerIndex() == p.getId());

                listPlayersInfo.add(new PlayerInfo(p.getName(), p.getId(), handInfo, isActive));
        }
        ArrayList<Integer> midCards = new ArrayList<>();

        for(Card c : board.getMiddleCard()) {
            if (c.isRevealed()) {
                midCards.add(c.getIntValue());
            } else {
                midCards.add(null); // -1 pour cacher la valeur pas de triche si un client regarde les infos
            }
        }

        for(Integer i : midCards){
            System.out.println("Middle card value sent to client: " + i);
        }
        return new GameState(listPlayersInfo, midCards);
    }
}
