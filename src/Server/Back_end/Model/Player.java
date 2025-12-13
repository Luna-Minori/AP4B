package Server.Back_end.Model;

import java.util.ArrayList;

public class Player {
    private int Id;
    private ArrayList<PlayerCardRequest> askedCards = new ArrayList<>();
    private int pointGame = 0;  // Total points accumulated across all rounds
    private ArrayList<Card> hand = new ArrayList<>(); // Cards held by the player
    private final String name;  // Player's name
    private boolean human = true; // Whether the player is a human (as opposed to a bot)

    /**
     * Constructor for the Player class.
     *
     * @param name The name of the player.
     * @param hum  Whether the player is human or a bot.
     */
    public Player(String name, int Id, boolean hum) {
        this.name = name;
        this.human = hum;
        this.Id = Id;
    }

    public int getId(){
        return Id;
    }

    public ArrayList<PlayerCardRequest> getAskedCards() {
        return askedCards;
    }

    public void playLowestCard() {
        Card card = askedLowestCard();
        PlayerCardRequest askedCard = new PlayerCardRequest(this, card);
        askedCards.add(askedCard);
    }

    public void playHigtestCard() {
        Card card = askedHigtestCard();
        PlayerCardRequest askedCard = new PlayerCardRequest(this, card);
        askedCards.add(askedCard);
    }

    public void askLowestCard (Player p) {
        Card card = askedHigtestCard();
        PlayerCardRequest askedCard = new PlayerCardRequest(p, card);
        askedCards.add(askedCard);
    }

    public void askHighestCard (Player p) {
        Card card = askedHigtestCard();
        PlayerCardRequest askedCard = new PlayerCardRequest(p, card);
        askedCards.add(askedCard);
    }

    protected Card askedLowestCard() {
        if (hand.isEmpty()) return null;
        for (Card card : hand) {
            if(!card.isRevealed()){
                card.reveal();
                return card;
            }
        }
        return null;
    }

    protected Card askedHigtestCard() {
        if (hand.isEmpty()) return null;
        for (int i = hand.size() - 1; i > 0; i--) {
            Card card = hand.get(i);
            if(!card.isRevealed()){
                card.reveal();
                return card;
            }
        }
        return null;
    }

    public void addAskedCard(Card card, Model b) {askedCards.add(new PlayerCardRequest(b, card));}

    /**
     * Returns whether the player is human.
     *
     * @return true if the player is human, false if the player is a bot.
     */
    public boolean getHuman() {
        return this.human;
    }

    /**
     * Returns the total points accumulated by the player in the game.
     *
     * @return The total points of the player in the game.
     */
    public int getPointGame() {
        return pointGame;
    }
    /**
     * Returns the player's name.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the player's hand (the cards they currently hold).
     *
     * @return The list of cards in the player's hand.
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Sets the player's hand to a given list of cards.
     *
     * @param hand The new hand of cards to assign to the player.
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
        this.hand.sort(null);
    }

    /**
     * Returns the card at a specified index from the player's hand.
     *
     * @param index The index of the card in the hand.
     * @return The card at the specified index.
     */
    public Card getCard(int index) {
        return this.hand.get(index);
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to draw (add) to the player's hand.
     */
    public void drawCard(Card card) {
        this.hand.add(card);
    }

    /**
     * Checks if a value already exists in the provided array.
     *
     * @param storage The array to check.
     * @param val The value to check for.
     * @return true if the value exists in the array, false otherwise.
     */
    private boolean contains(int[] storage, int val) {
        for (int a : storage) {
            if (val == a) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the player's total points for the game by adding the points from the current round,
     * and then resets the points for the current round.
     */
    protected void updatePointGame() {
        this.pointGame += 1;
    }

    public boolean cardPlayble(int cardIndex){
        if(cardIndex == 0 || cardIndex == hand.size() - 1){ // taille n+1
            return true;
        }
        return false;
    }

    public void affHand (){
        for(int i = 0; i < hand.size(); i++){
            System.out.println(i + " : " + hand.get(i).getValue().toString());
        }
    }

    public Card showCard (int index) {
        return hand.get(index);
    }
}
