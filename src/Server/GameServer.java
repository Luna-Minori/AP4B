package Server;

import Server.Back_end.Model.Model;
import Server.Back_end.Model.Player;
import Common.DTO.GameState;
import Common.NetworkMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private ServerSocket serverSocket;
    private static Model model;
    private static GameState info;
    private List<ClientHandler> clients = new ArrayList<>(); // Liste des joueurs connectés

    public static void main(String[] args) throws IOException {
        new GameServer().start(12345); // On démarre sur le port 12345
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Serveur en attente de joueurs...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Un nouveau joueur est arrivé !");
            ClientHandler client = new ClientHandler(socket, this);
            clients.add(client);
            new Thread(client).start();
        }
    }

    public synchronized void broadcast(String message) {
        NetworkMessage msg = new NetworkMessage(message, info);
        for (ClientHandler client : clients) {
            client.sendMessage(msg);
        }
    }

    public synchronized void broadcastStartGame() {
        String title = "GAME_START";
        NetworkMessage msg = new NetworkMessage(title, info);
    }

    public synchronized void broadcastState() {
        String title = "GAME_UPDATE";
        for(ClientHandler client : clients){
            updateGameState(client.getId());
            NetworkMessage msg = new NetworkMessage(title, info);
            client.sendMessage(msg);
        }
    }

    public synchronized void broadcastLobbyState() {
        String title = "LOBBY_UPDATE";
        NetworkMessage msg = new NetworkMessage(title, info);
    }

    public synchronized void handleAction(String action, Object data) {
        if (action.equals("PLAYER_READY")) { checkForStart();}
        if (action.equals("ASK_LOWEST")) { actionLowestCard(data);}
        if (action.equals("ASK_HIGHEST")) { actionHighestCard(data);}

        broadcastState();
    }

    private void actionLowestCard(Object data) {
        int playerId = (int) data;
        Player player = model.getPlayerById(playerId);
        Player currentPlayer = model.getCurrentPlayer();

        if(player == null){throw new IllegalArgumentException("Player not found: ");
        } else if (player == currentPlayer) {
            currentPlayer.playLowestCard();
            //boardPanel.flipLowestCardOfPlayer(playerId);
        }
        else{
            currentPlayer.askLowestCard(player);
            //boardPanel.flipLowestCardOfPlayer(playerId);
        }

//        if(!board.stateOfTurn()){ // si faux alors on change de joueur
//            nextPlayer();
//        }
        System.out.println("Lowest Card Played");
    }

    private void actionHighestCard(Object data) {
        int playerId = (int) data;
        Player player = model.getPlayerById(playerId);
        Player currentPlayer = model.getCurrentPlayer();

        if(player == null){throw new IllegalArgumentException("Player not found: ");
        } else if (player == currentPlayer) {
            currentPlayer.playHigtestCard();
            //boardPanel.flipLowestCardOfPlayer(playerId);
        }
        else{
            currentPlayer.askHighestCard(player);
            //boardPanel.flipHighestCardOfPlayer(playerId);
        }

//        if(!board.stateOfTurn()){ // si faux alors on change de joueur
//            nextPlayer();
//        }
        System.out.println("H Card Played");
    }

    public synchronized void handlePlayerReady(int playerId) {
        ClientHandler client = clients.get(playerId);
        client.setReady(true);
        broadcastLobbyState();
        checkForStart();
    }

    private void checkForStart() {
        if (clients.size() < 2) return;
        for (ClientHandler client : clients) {
            if (!client.isReady()) return;
        }
        startGame();
    }

    private void startGame() {
        System.out.println("Lancement de la partie avec " + clients.size() + " joueurs !");
        model = new Model();

        for (ClientHandler c : clients) {
            model.addPlayer(new Player("Joueur " + c.getId(), c.getId(), true));
        }
//
//        for (int j = 0; j < nbBots; j++) {
//            board.addPlayer(new Player("Bot", i, false));
//            i++;
//        }
        model.newGame();
        broadcastStartGame();
    }

    private static void updateGameState(int clientId) {
        info = model.getGameState(clientId);
    }
}