package Server.Back_end.Model;

import Common.DTO.GameState;

import java.util.ArrayList;

public class Model {
    private final DTOMapper info;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Card> middleCard = new ArrayList<>(); // Cards in the middle of the table
    private int currentPlayerIndex = 0; // Index of the current player

    public Model() {
        this.info = new DTOMapper();
    }

    /**
     * Adds a player to the board.
     *
     * @param player The player to be added.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }
    public int getCurrentPlayerIndex() {return currentPlayerIndex;}
    public Player getPlayerById(int id) {
        for (Player player : players) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }
    /**
     * Gets the list of players currently in the game.
     *
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer(){ return players.get(currentPlayerIndex);}

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

    public boolean stateOfTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        ArrayList<PlayerCardRequest> askedCards = currentPlayer.getAskedCards();

        switch (askedCards.size()) {
            case 1:
                return true;
            case 2:
                return askedCards.getFirst() == askedCards.getLast();
            case 3:
                return askedCards.get(0) == askedCards.get(1) && askedCards.get(1) == askedCards.get(2);
        }
        return false;
    }

    public void newTurn() {
        currentPlayerIndex++;
    }

    public void newGame() {
        Deck deck = new Deck();
        middleCard.clear();
        int cartesParJoueur = deck.size() / (players.size() + 1);

        for (Player player : players) {
            player.getHand().clear();
            for (int i = 0; i < cartesParJoueur; i++) {
                player.drawCard(deck.draw());
            }
        }
        while(!deck.isEmpty()){
            middleCard.add(deck.draw());
        }
    }

    public GameState getGameState(int clientId) {
        return info.createSecureGameState(this, clientId);
    }
}
