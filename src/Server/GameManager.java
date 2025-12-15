package Server;

import Common.DTO.GameState;
import Common.DTO.LobbyState;
import Server.Back_end.Model.Model;
import Server.Back_end.Model.Player;

import java.net.ServerSocket;
import java.util.List;

public class GameManager extends GameServer {
    private static Model model = new Model();
    private static GameState gameInfo;
    private static List<ClientHandler> clients;
    private static int nbBots;

    public GameManager(List<ClientHandler> clientsList, int bots) {
        clients = clientsList;
        nbBots = bots;
    }

    protected void startGame(int nbBots) {
        System.out.println("Lancement de la partie avec " + clients.size() + " joueurs !" + nbBots);

        int id = 0;
        for (ClientHandler c : clients) {
            model.addPlayer(new Player(c.getPlayerName(), c.getId(), true));
            id++;
        }

        int maxBots = 6 - clients.size();
        if(maxBots < nbBots){
            nbBots = maxBots;
        }

        for(int i = 0 ; i < nbBots ; i++){
            model.addPlayer(new Player("Bot " + i, id, false));
            id++;
        }

        System.out.println("Nb player " + model.getPlayers().size());
        model.newGame();
        for(ClientHandler c : clients){
            updateGameState(c.getId());
        }
        broadcast("GAME", "START", gameInfo);
    }


    public synchronized void handleGameAction(ClientHandler client, String action,  Object data) {

        System.out.println("Action " + action + "reçue de : " + client.getId() + " (" + client.getPlayerName() + ")");
        if (action.equals("ASK_LOWEST")) { actionLowestCard(client, data);}
        if (action.equals("ASK_HIGHEST")) { actionHighestCard(data);}

        updateGameState(client.getId());
        broadcast("GAME", "UPDATE_STATE", gameInfo);
    }

    private void actionLowestCard(ClientHandler client, Object data) {
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

    private static void updateGameState(int clientId) {
        gameInfo = model.getGameState(clientId);
    }
}
