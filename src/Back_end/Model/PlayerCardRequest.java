package Back_end.Model;

public class PlayerCardRequest {
    private final Player player;
    private final Card card;
    private final Model board;

    public PlayerCardRequest(Player player, Card card) {
        this.player = player;
        this.card = card;
        this.board = null;
    }

    public PlayerCardRequest(Model b, Card card) {
        this.board = b;
        this.card = card;
        this.player = null;
    }

    public Player getPlayerId() {
        return player;
    }

    public Card getCard() {
        return card;
    }
}
