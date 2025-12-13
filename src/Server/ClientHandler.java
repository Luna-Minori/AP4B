package Server;

import Common.NetworkMessage;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private GameServer server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int id;
    private String playerName;
    private boolean isReady = false;


    public ClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
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
                server.handleAction(msg.title, msg.content);
            }
        } catch (Exception e) {
            System.out.println("Joueur déconnecté");
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
}