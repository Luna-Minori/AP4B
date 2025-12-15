package Client;

import Common.DTO.GameState;
import Common.DTO.LobbyState;
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

    public void send(String title, String subTitle, Object content) {
        try {
            out.writeObject(new NetworkMessage(title, subTitle, content));
            out.flush();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        try {
            while (true) {
                NetworkMessage msg = (NetworkMessage) in.readObject();
                if(msg.title.equals("LOBBY")){
                    if(msg.subTitle.equals("UPDATE_STATE")) {
                        LobbyState newState = (LobbyState) msg.content;
                        for(int i = 0; i < newState.getMaxPlayers();i++) {
                            System.out.println("lobby updated" + newState.getIsHuman().get(i));
                        }
                        Platform.runLater(() -> {
                            controler.updatelobby(newState);
                        });
                    }
                }
                if(msg.title.equals("GAME")) {
                    if (msg.subTitle.equals("UPDATE")) {
                        GameState newState = (GameState) msg.content;
                        Platform.runLater(() -> {
                            controler.updateFromNetwork(msg);
                        });
                    }
                    if (msg.subTitle.equals("START")) {
                        GameState newState = (GameState) msg.content;
                        System.out.println("Ouiiiiiiiiiiiiiiiiiiiiiiiii");
                        Platform.runLater(() -> {
                            controler.handleGameStart(newState);
                        });
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}