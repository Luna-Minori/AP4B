package Controler;

import Back_end.GameState;
import Back_end.Model.Model;
import Back_end.Model.Card;
import Back_end.Model.Player;
import Front_end.BoardPanel;
import Front_end.Menu;
import Front_end.ViewHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Main game controller class that initializes and manages the game loop,
 * handles player turns (human and bot), and updates the front-end accordingly.
**/
public class Controler implements ViewHandler {
    private static Player currentPlayer;
    private static Model board = new Model();
    private static Stage stage;
    private static GameState info;
    private static Scene menuScene;
    private static Scene boardScene;
    private static String cssPath = Objects.requireNonNull(Controler.class.getResource("/Front_end/style.css")).toExternalForm();
    private static boolean played;
    private static boolean ManyHuman = false;
    private static boolean noHuman = false;
    private static boolean end = false;

    /**
     * Main entry point of the game. Initializes players, starts the game loop,
     * and manages the turn logic.
     **/
    public Controler(Stage st) {
        stage = st;
        Menu menu = new Menu(this);
        menuScene = new Scene(menu, 1280, 720);
        menuScene.getStylesheets().add(cssPath);
        stage.setTitle("Trio UTBM");
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(menuScene);
        stage.show();


        //Menu menu = new Menu();
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

        ArrayList<ArrayList<Integer>> hands = createHandsFront(board);
        ArrayList<Integer> middleCard = createMiddleCardFront(board);
        ArrayList<Integer> points = createOfPoint(board);
        GameState info = board.getGameState();

        //startView(stage, info);

        /*
        try {
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        */
    }

    @Override
    public void gameStart(ArrayList<String> playerNames, int nbBots) {
        System.out.println("Game started with players: " + playerNames + " and " + nbBots + " bots.");
        for (String name : playerNames) {
            board.addPlayer(new Player(name, true));
        }
        for (String name : playerNames) {
            board.addPlayer(new Player("Bot", false));
        }
        board.new_turn();
        updateInfo();
        BoardPanel boardPanel = new BoardPanel(info, this);
        boardScene = new Scene(boardPanel, 0, 0);
        boardScene.getStylesheets().add(cssPath);
        stage.setScene(boardScene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void lowestRequested(String playerName) {
        System.out.println("Lowest Card Played");
    }

    @Override
    public void highestRequested(String playerName) {
        System.out.println("Higtest Card Played");


    }


    @Override
    public void onQuitter() {

    }

    @Override
    public void onAttaquer(String ennemiId) {

    }
    public void onUserAction(String actionId) {
        switch (actionId) {
            case "lowest" -> System.out.println("Lowest Card");
            case "highest" -> System.out.println("Highest Card");
            default -> System.out.println("Action inconnue : " + actionId);
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

    private static void updateInfo() {
        info = board.getGameState();
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