package Server;

import Common.NetworkMessage;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private GameServer server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private LobbyManager lobbyManager;
    private GameManager gameManager;
    private int id;
    private String playerName;
    private boolean isReady = false;
    private boolean isHuman;


    public ClientHandler(Socket socket, GameServer server, LobbyManager lobbyManager, GameManager gameManager, int id) {
        this.server = server;
        this.id = id;
        this.lobbyManager = lobbyManager;
        this.gameManager = gameManager;

        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        try {
            while (true) {
                NetworkMessage msg = (NetworkMessage) in.readObject();
                System.out.println(msg.title + " " + msg.subTitle);
                if (msg.title.equals("LOBBY")){
                    System.out.println("if Lobby " + msg.title + " " + msg.subTitle);
                    lobbyManager.handleLobbyAction(this, msg.subTitle, msg.content);
                }
                if(msg.title.equals("GAME")){
                    gameManager.handleGameAction(this, msg.subTitle, msg.content);
                }

            }
        } catch (Exception e) {
            System.out.println("Joueur déconnecté" + e );
        }
    }

    public void sendMessage(NetworkMessage msg) {
        try {
            out.writeObject(msg);
            out.flush();
            out.reset();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }

    public boolean isReady() {
        return isReady;
    }

    public int getId() { return id; }

    public String getPlayerName() { return playerName; }

    public void setPlayerName(String name) { this.playerName = name; }

    public Boolean isHuman() {
        return true;
    }
}