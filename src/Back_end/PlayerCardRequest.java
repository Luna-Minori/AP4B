package Back_end;

public class PlayerCardRequest {
    private final Player player;
    private final Card card;
    private final Board board;

    public PlayerCardRequest(Player player, Card card) {
        this.player = player;
        this.card = card;
        this.board = null;
    }

    public PlayerCardRequest(Board b, Card card) {
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
