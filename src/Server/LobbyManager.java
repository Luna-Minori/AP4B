package Server;

import Common.DTO.GameState;
import Common.DTO.LobbyState;
import Server.Back_end.Model.Model;
import Server.Back_end.Model.Player;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class LobbyManager extends GameServer {
    private ServerSocket serverSocket;
    private static LobbyState lobbyInfo;
    private static List<ClientHandler> clients;
    private GameManager gameManager;
    private static int nbBots;

    public LobbyManager(List<ClientHandler> clientsList, int bots, GameManager gameManager) {
        clients = clientsList;
        nbBots = bots;
        this.gameManager = gameManager;

    }

    public synchronized void handleLobbyAction(ClientHandler client, String action,  Object data) {
        if (action.equals("PLAYER_NAME")) { setPlayerName(client, data);}
        if (action.equals("PLAYER_READY")) { client.setReady(true); checkForStart();}
        if (action.equals("ADD_BOT")) { setBot();}
        if (action.equals("REMOVE_BOT")) { unSetBot();}

        updateLobbyInfo();
        broadcast("LOBBY", "UPDATE_STATE", lobbyInfo);
    }

    private synchronized void setPlayerName(ClientHandler client, Object data) {
        String name = (String) data;
        client.setPlayerName(name);
        System.out.println("Action reçue de : " + client.getId() + " (" + client.getPlayerName() + ")");
    }

    private synchronized  void setBot() {
        if((nbBots+1) < 7){
            nbBots += 1 ;
        }
    }

    private synchronized  void unSetBot() {
        nbBots -= 1;
    }

    public synchronized void handlePlayerReady(int playerId) {
        ClientHandler client = clients.get(playerId);
        client.setReady(true);
        checkForStart();
    }

    private void checkForStart() {
        System.out.println("Checking for start" + clients.size() + "    "+ nbBots);
        if (clients.size() + nbBots < 2) return;
        if (clients.size() + nbBots > 6) return;
        for (ClientHandler client : clients) {
            if (!client.isReady()) return;
        }
        gameManager.startGame(nbBots);
    }

    private static void updateLobbyInfo() {
        ArrayList<String> playerNames = new ArrayList<>();
        ArrayList<Boolean> playerReadyStatus = new ArrayList<>();
        ArrayList<Boolean> isHuman = new ArrayList<>();

        for (ClientHandler client : clients) {
            playerNames.add(client.getPlayerName());
            playerReadyStatus.add(client.isReady());
            isHuman.add(true);
        }

        int maxBots = 6 - clients.size();
        if(maxBots < nbBots){
            nbBots = maxBots;
        }
        for(int i = 0; i < nbBots; i++){
            playerNames.add("Bot" + i);
            playerReadyStatus.add(true);
            isHuman.add(false);
        }

        lobbyInfo = new LobbyState(playerNames, isHuman, playerReadyStatus);
    }
}
