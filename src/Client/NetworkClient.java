package Client;

import Common.DTO.GameState;
import Common.NetworkMessage;
import Client.Controler.Controler;
import javafx.application.Platform;
import java.io.*;
import java.net.Socket;

public class NetworkClient implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Controler controler;

    public NetworkClient(String ip, int port, Controler controler) {
        this.controler = controler;
        try {
            socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            new Thread(this).start();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void send(String title, Object content) {
        try {
            out.writeObject(new NetworkMessage(title, content));
            out.flush();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        try {
            while (true) {
                NetworkMessage msg = (NetworkMessage) in.readObject();
                if (msg.title.equals("GAME_UPDATE")) {
                    GameState newState = (GameState) msg.content;
                    Platform.runLater(() -> {
                        controler.updateFromNetwork(msg);
                    });
                }
                if(msg.title.equals("GAME_START")) {
                    GameState newState = (GameState) msg.content;

                    Platform.runLater(() -> {
                        controler.handleGameStart(newState);
                    });
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}