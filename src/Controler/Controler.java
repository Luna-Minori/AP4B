package Controler;

import Back_end.GameState;
import Back_end.Model.Model;
import Back_end.Model.Card;
import Back_end.Model.Player;
import Front_end.BoardPanel;
import Front_end.Menu;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Main game controller class that initializes and manages the game loop,
 * handles player turns (human and bot), and updates the front-end accordingly.
**/
public class Controler {
    private static int currentPlayerIndex;
    private static Player currentPlayer;
    private static Model board;
    private static BoardPanel pB;
    private static boolean played;
    private static boolean ManyHuman = false;
    private static boolean noHuman = false;
    private static boolean end = false;

    /**
     * Main entry point of the game. Initializes players, starts the game loop,
     * and manages the turn logic.
     **/

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        Menu menu = new Menu();
        board = new Model();
        /*
        menu.initGameReady((playerNames, nbBots) -> {
            for (String name : playerNames) {
                board.addPlayer(new Player(name, true));
            }
            if (playerNames.size() > 1) {
                ManyHuman = true;
            }
            if (playerNames.size() == 0) {
                noHuman = true;
            }
            for (int i = 0; i < nbBots; i++) {
                board.addPlayer(new Player("Bot" + i, false));
            }
            latch.countDown();
        }); */
        ArrayList<String> playerNames = new ArrayList<String>();
        playerNames.add("Player1");
        playerNames.add("Player2");
        playerNames.add("Player3");
        playerNames.add("Player4");
        playerNames.add("Player5");
        playerNames.add("Player6");
        for (String name : playerNames) {
            board.addPlayer(new Player(name, true));
        }
        board.new_turn();

        ArrayList<ArrayList<Integer>> hands = createHandsFront(board);
        ArrayList<Integer> middleCard = createMiddleCardFront(board);
        ArrayList<Integer> points = createOfPoint(board);
        GameState info = board.getGameState();
        BoardPanel pB = new BoardPanel(info);

        try {
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static ArrayList<ArrayList<Integer>> createHandsFront(Model b) {
        ArrayList<ArrayList<Integer>> hands = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            hands.add(createHandFront(p));
        }
        return hands;
    }

    private static ArrayList<Integer> createHandFront(Player p) {
        ArrayList<Integer> handValues = new ArrayList<>();
        for (Card h : p.getHand()) {
            handValues.add(h.getIntValue());
        }
        return handValues;
    }

    private static ArrayList<Integer> createMiddleCardFront(Model b) {
        ArrayList<Integer> deck = new ArrayList<>();
        for (Card c : b.getMiddleCard()) {
            deck.add(c.getIntValue());
        }
        return deck;
    }

    private static ArrayList<Integer> createOfPoint(Model b) {
        ArrayList<Integer> points = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            points.add(p.getPointGame());
        }
        return points;
    }
}


    /**
     * Handles the current human player's turn and waits for user interaction.

    private static void playerTurn() {
        played = false;
        CountDownLatch latch = new CountDownLatch(1);

        pB.setShowCard(index -> {
            if (played) return;
            try {
                currentPlayer.cardPlayble(index);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return;
            }
            played = true;
            latch.countDown();
        });

        pB.setRequestCard

        try {
            latch.await();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        pB.setOnDrawRequested(null);
        pB.setOnPlayCardRequested(null);
        pB.setOnHandDownRequested(null);
        pB.updateHandPoint(currentPlayer.getHandValue());
    }

    /**
     * Manages the turn logic for a bot (AI player).

    private static void iaTurn() {
        ArrayList<Integer> cardValue = new ArrayList<>();
        ArrayList<Integer> cardIndex = new ArrayList<>();

        for (int j = 0; j < currentPlayer.getHand().size(); j++) {
            Card card = currentPlayer.getCard(j);
            if (card.getValue() >= board.getBin().getValue() || (board.getBin().getValue() == 7 && card.getValue() == 1)) {
                cardValue.add(card.getValue());
                cardIndex.add(j);
            }
        }

        if (!cardValue.isEmpty() && !board.getDeck() .isEmpty()){
            try {
                board.cardPlayble(cardIndex.get(bestCard(cardValue)), currentPlayerIndex);
                pB.updateBin(board.getBin().getValue());
            } catch (IllegalArgumentException ignored) {
                currentPlayer.drawCard(board.getDeck().draw());
                pB.updateDraw();
            }
        } else {
            try {
                if (currentPlayer.getHandValue() <= 5) {
                    currentPlayer.setHandDown(true);
                } else {
                    currentPlayer.drawCard(board.getDeck().draw());
                    pB.updateDraw();
                }
            } catch (IllegalArgumentException e) {
                currentPlayer.setHandDown(true);
            }
        }
    }

    /**
     * Updates the front-end with current game state if the player is human.

    private static void updateFront(boolean force) {
        if (force) {
            pB.update(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), currentPlayerIndex, createListHandPoint(board), createOfPoint(board));
            return;
        } else {
            if (!currentPlayer.getHuman()) return;
            pB.update(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), currentPlayerIndex, createListHandPoint(board), createOfPoint(board));
        }
    }

    private static void updateFront() {
        if (!currentPlayer.getHuman()) return;
        pB.update(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), currentPlayerIndex, createListHandPoint(board), createOfPoint(board));
    }

    private static ArrayList<ArrayList<Integer>> createHandsFront(Board b) {
        ArrayList<ArrayList<Integer>> hands = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            hands.add(createHandFront(p));
        }
        return hands;
    }

    private static ArrayList<Integer> createHandFront(Player p) {
        ArrayList<Integer> handValues = new ArrayList<>();
        for (Card h : p.getHand()) {
            handValues.add(h.getValue());
        }
        return handValues;
    }

    private static ArrayList<Integer> createDeckFront(Board b) {
        ArrayList<Integer> deck = new ArrayList<>();
        for (Card c : b.getDeck()) {
            deck.add(c.getValue());
        }
        return deck;
    }

    private static ArrayList<String> createNamesFront(Board b) {
        ArrayList<String> names = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            names.add(p.getName());
        }
        return names;
    }

    /**
     * Returns the index of the best (lowest) card value in the list.

    private static int bestCard(ArrayList<Integer> list) {
        int minIndex = 0;
        int minValue = list.getFirst();
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < minValue) {
                minValue = list.get(i);
                minIndex = i;
            }
        }
        return minIndex;
    }

    private static ArrayList<Integer> createListHandPoint(Board b) {
        ArrayList<Integer> points = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            points.add(p.getHandValue());
        }
        return points;
    }

    private static ArrayList<Integer> createOfPoint(Board b) {
        ArrayList<Integer> points = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            points.add(p.getPointGame());
        }
        return points;
    }

    public static void restartGame() {
        SwingUtilities.invokeLater(() -> {
            try {
                main(new String[]{});  // Relance le jeu depuis le début
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}*/