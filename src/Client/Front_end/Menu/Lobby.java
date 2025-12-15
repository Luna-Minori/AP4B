package Client.Front_end.Menu;

import Common.DTO.LobbyState;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import Client.Front_end.ViewHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Lobby extends StackPane {
    private final ViewHandler handler;
    private AskNameOverlay askNameOverlay;
    private TextField playerName;

    public Lobby(ViewHandler handler) {
        this.handler = handler;
        setId("lobbyPane");
        createAskNameOverlay();
    }

    private void createAskNameOverlay() {
        askNameOverlay = new AskNameOverlay(handler);

        askNameOverlay.maxWidthProperty().bind(askNameOverlay.prefWidthProperty());
        askNameOverlay.prefWidthProperty().bind(this.widthProperty().multiply(0.15));

        askNameOverlay.maxHeightProperty().bind(askNameOverlay.prefHeightProperty());
        askNameOverlay.prefHeightProperty().bind(this.heightProperty().multiply(0.4));

        askNameOverlay.setAlignment(Pos.CENTER);
        getChildren().add(askNameOverlay);
    }

    private Button createReadyButton(){
        Button readyButton = new Button("Ready");
        readyButton.setId("ReadyButton");
        readyButton.getStyleClass().add("askButtonCard");
        readyButton.setOnAction(e -> {if (handler != null) { handler.Ready();}});
        return readyButton;
    }

    public void update(LobbyState info) {
        getChildren().clear();
        ArrayList<String> names = info.getName();
        ArrayList<Boolean> ready = info.isReady();
        ArrayList<Boolean> human =  info.getIsHuman();
        int numberOfPlayersInColumns = 3;

        Label title = new Label("Salle d'attente (" + info.getMaxPlayers() + "/6)");
        title.getStyleClass().add("lobbyTitle");

        TilePane playersGrid = new TilePane();
        playersGrid.setPrefColumns(numberOfPlayersInColumns);
        playersGrid.setHgap(20);
        playersGrid.setVgap(20);
        playersGrid.setAlignment(Pos.CENTER);

        for (int i = 0; i < info.getMaxPlayers(); i++) {
            playersGrid.getChildren().add(createPlayerCard(names.get(i), human.get(i),  ready.get(i)));
        }

        int emptySlots = 6 - info.getMaxPlayers();
        for (int i = 0; i < emptySlots; i++) {
            playersGrid.getChildren().add(createEmptySlot());
        }

        VBox mainContainer = new VBox(30);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getChildren().addAll(title, playersGrid, createReadyButton());
        getChildren().add(mainContainer);
    }

    private VBox createPlayerCard(String playerName, boolean isHuman, boolean isReady) {
        VBox card = new VBox(10);
        card.setPrefSize(150, 180);
        card.setAlignment(Pos.CENTER);

        String borderColor = isReady ? "#2ecc71" : "#e74c3c";
        card.getStyleClass().add("playerCard");
        card.setStyle("-fx-border-color: " + borderColor); // peut pas faire de if en css

        StackPane avatar = new StackPane();
        Circle bg = new Circle(40, Color.web("#3498db"));

        if(playerName.isEmpty()) {
            playerName = "En train de se connecter...";
        }

        Label initial = new Label(playerName.substring(0, 1).toUpperCase());
        initial.setStyle("-fx-font-size: 30px; -fx-text-fill: white; -fx-font-weight: bold;");
        avatar.getChildren().addAll(bg, initial);

        Label nameLabel = new Label(playerName);
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        Label statusLabel = new Label(isReady ? "PRÊT" : "EN ATTENTE");
        statusLabel.setStyle("-fx-text-fill: " + borderColor + "; -fx-font-size: 12px; -fx-font-weight: bold;");

        System.out.println("Test bot : " + playerName + isHuman);
        if (!isHuman) {
            System.out.println("Ici");
            Button removeBotBtn = new Button("- Bot");
            removeBotBtn.getStyleClass().add("BotButton");

            removeBotBtn.setOnAction(e -> {
                if (handler != null) handler.removeBot();
            });

            StackPane nameArea = new StackPane();

            //Pas visible
            nameLabel.visibleProperty().bind(card.hoverProperty().not());
            avatar.visibleProperty().bind(card.hoverProperty().not());
            statusLabel.visibleProperty().bind(card.hoverProperty().not());

            // pas prit en compte dans le calcule de la postion du bouton
            nameLabel.managedProperty().bind(card.hoverProperty().not());
            avatar.managedProperty().bind(card.hoverProperty().not());
            statusLabel.managedProperty().bind(card.hoverProperty().not());

            removeBotBtn.visibleProperty().bind(card.hoverProperty());
            removeBotBtn.managedProperty().bind(card.hoverProperty());
            nameArea.getChildren().add(removeBotBtn);
            card.getChildren().addAll(avatar, nameLabel, statusLabel, nameArea);
        } else {
            card.getChildren().addAll(avatar, nameLabel, statusLabel);
        }

        return card;
    }

    /**
     * Crée un emplacement vide (gris) pour montrer qu'il y a de la place
     */
    private VBox createEmptySlot() {
        VBox card = new VBox(10);
        card.setPrefSize(150, 180);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("emptyCard");

        Label emptyLabel = new Label("Vide");
        emptyLabel.getStyleClass().add("emptyLabel");
        emptyLabel.setStyle("");

        Button botBoutton = new Button("+ Bot");
        botBoutton.setOnAction(e -> {
            if (handler != null) {
                handler.addBot();
            }});

        //bo :)
        botBoutton.getStyleClass().add("BotButton");
        botBoutton.visibleProperty().bind(card.hoverProperty());
        emptyLabel.visibleProperty().bind(card.hoverProperty().not());
        StackPane content = new StackPane(emptyLabel, botBoutton);

        card.getChildren().add(content);
        return card;
    }


    public void show(boolean b) {
        setVisible(b);
    }
}
