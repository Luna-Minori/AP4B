package Client.Controler;

import Client.Front_end.Menu.Lobby;
import Client.NetworkClient;
import Common.DTO.GameState;
import Common.DTO.LobbyState;
import Common.DTO.PlayerInfo;
import Common.NetworkMessage;
import Client.Front_end.BoardPanel;
import Client.Front_end.Menu.Menu;
import Client.Front_end.ViewHandler;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


/**
 * Main game controller class that initializes and manages the game loop,
 * handles player turns (human and bot), and updates the front-end accordingly.
**/
public class Controler implements ViewHandler {
    private NetworkClient network;
    private String ip = "127.1.0.0";
    private int port = 12345;
    private final Stage stage;
    private static GameState gameInfo;
    private static LobbyState lobbyInfo;
    private BoardPanel boardPanel;
    private Lobby lobbyPanel;
    private static Scene menuScene;
    private static final String cssPath = Objects.requireNonNull(Controler.class.getResource("/Client/Front_end/style.css")).toExternalForm();
//    private static boolean played;
//    private static boolean ManyHuman = false;
//    private static boolean noHuman = false;
//    private static boolean end = false;

    /**
     * Main entry point of the game. Initializes players, starts the game loop,
     * and manages the turn logic.
     **/
    public Controler(Stage stage) {
        this.stage = stage;
        // Menu
        Menu root = new Menu(this);
        Scene scene = new Scene(root, 800, 600);
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        stage.setTitle("Trio UTBM");
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

    }
    // Lobby
    @Override
    public void setName(String name) {
        System.out.println("Sending name to server: " + name);
        network.send("LOBBY", "PLAYER_NAME", name);
    }

    @Override
    public void Ready(){
        network.send("LOBBY", "PLAYER_READY", null);
    }

    @Override
    public void addBot(){
        network.send( "LOBBY","ADD_BOT", null);
    }

    @Override
    public void removeBot(){
        network.send( "LOBBY","REMOVE_BOT", null);
    }

    @Override
    public void startLobby() {
        //Network
        network = new NetworkClient(ip, port, this);

        lobbyPanel = new Lobby(this);
        Scene lobbyScene = new Scene(lobbyPanel, 0, 0);

        // Gestion du CSS
        if (cssPath != null) {
            lobbyScene.getStylesheets().add(cssPath);
        }

        stage.setScene(lobbyScene);
        stage.setFullScreen(true);
        stage.setResizable(true);
        stage.show();
    }

    public void updatelobby(LobbyState lobbyState) {
        System.out.println("Updating lobby: " + lobbyState);
        for(String n : lobbyState.getName()){
            System.out.println("Player in lobby: " + n);
        }
        lobbyInfo = lobbyState;
        lobbyPanel.update(lobbyInfo);
    }

    // Game
    public void handleGameStart(GameState receivedState) {
        gameInfo = receivedState;

        for(PlayerInfo p : gameInfo.getPlayers()){
            System.out.println("Player in game: " + p.getName());
            for(Integer c : p.getHand()){
                System.out.println("Card in hand: " + c);
            }
        }
        Platform.runLater(() -> {
            boardPanel = new BoardPanel(gameInfo, this);
            Scene boardScene = new Scene(boardPanel, 0, 0);

            // Gestion du CSS
            if (cssPath != null) {
                boardScene.getStylesheets().add(cssPath);
            }

            stage.setScene(boardScene);
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.show();
        });
    }

    public void updateFromNetwork(NetworkMessage msg) {
        System.out.println("Mise à jour reçue du serveur !");
        if (msg.title.equals("UPDATE")) { // C'est le jeu qui commence !
            GameState state = (GameState) msg.content;
            boardPanel.update(state); // On bascule sur le plateau de jeu
        }
    }

    @Override
    public void lowestRequested(int playerId) {
        network.send("GAME", "ASK_LOWEST", playerId);
    }

    @Override
    public void highestRequested(int playerId) {
        network.send("GAME","ASK_HIGHEST", playerId);
    }

    @Override
    public void middleCardRequested() {
        /*currentPlayer.addAskedCard(currentPlayer.playMiddleCard(board), board);
        System.out.println("Middle Card Played");*/
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onQuitter() {


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