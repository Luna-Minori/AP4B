package Back_end.Model;

import Back_end.GameState;

import java.util.ArrayList;

public class Model {

    private final ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> middleCard = new ArrayList<>(); // Cards in the middle of the table
    private int currentPlayerIndex = 0; // Index of the current player

    /**
     * Adds a player to the board.
     *
     * @param player The player to be added.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }
    public int getCurrentPlayerIndex() {return currentPlayerIndex;}

    /**
     * Gets the list of players currently in the game.
     *
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getMiddleCard() {return middleCard ;}

    public void addMiddleCard(Card middleCard) { this.middleCard.add(middleCard) ;}

    public boolean stateOfGame() {
        boolean test = false;
        int i = 0; // false = game continues, true = game over
        for (Player player : players) {
            if(player.getHand().isEmpty()) {
                i += i;
            }
            if(player.getPointGame() == 3 ){
                return true;
            }
        }
        if(middleCard.isEmpty() && i == players.size()){
            test = true;
        }
        return test;
    }

    public void new_turn() {
        Deck deck = new Deck();
        middleCard.clear();
        for (Player player : players) {
            player.getHand().clear();
            for (int i = 0; i < deck.size() / players.size() + 1; i++) {
                player.drawCard(deck.draw());
            }
        }
        while(!deck.isEmpty()){
            middleCard.add(deck.draw());
        }
    }

    public GameState getGameState() {
        return new GameState(this);
    }
}
