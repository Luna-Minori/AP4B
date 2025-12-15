package Server;

import Common.DTO.LobbyState;
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
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new GameServer().start(12345); // On démarre sur le port 12345
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Serveur en attente de joueurs...");
        GameManager gameManager = new GameManager(clients, 0);
        LobbyManager lobbyManager = new LobbyManager(clients, 0, gameManager);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Un nouveau joueur est arrivé !");
            ClientHandler client = new ClientHandler(socket, this, lobbyManager, gameManager, clients.size());
            clients.add(client);
            new Thread(client).start();
        }
    }

    public synchronized void broadcast(String title, String subTitle, Object content) {
        NetworkMessage msg = new NetworkMessage(title, subTitle, content);
        for (ClientHandler client : clients) {
            client.sendMessage(msg);
        }
    }
}