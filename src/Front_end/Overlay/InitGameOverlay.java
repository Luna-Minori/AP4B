package Front_end.Overlay;

import Controler.Controler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import Front_end.ViewHandler;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class InitGameOverlay {
    private ViewHandler handler;
    private final StackPane overlayRoot;
    private final VBox container;
    public BiConsumer<ArrayList<String>, Integer> onValidated;

    public InitGameOverlay(StackPane parent, ViewHandler handler) {
        this.handler = handler;
        // Création de l'overlay semi-transparent
        overlayRoot = new StackPane();
        overlayRoot.setStyle("-fx-background-color: rgba(0,0,0,0.7);");
        overlayRoot.setVisible(false); // invisible au début
        overlayRoot.prefWidthProperty().bind(parent.widthProperty());
        overlayRoot.prefHeightProperty().bind(parent.heightProperty());

        // Container central
        container = new VBox(15);
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-padding: 20; -fx-background-radius: 10;");
        container.prefWidthProperty().bind(parent.widthProperty().multiply(0.4));

        // Label
        Label title = new Label("Init Game");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // TextFields pour les joueurs
        TextField player1 = new TextField();
        player1.setPromptText("Player 1");
        TextField player2 = new TextField();
        player2.setPromptText("Player 2");

        // TextField pour le nombre de bots
        TextField botsField = new TextField();
        botsField.setPromptText("Number of bots");

        // Bouton de validation
        Button validate = new Button("Start Game");
        validate.setOnAction(e -> {
            ArrayList<String> playerNames = new ArrayList<>();
            if (!player1.getText().isBlank()) playerNames.add(player1.getText());
            if (!player2.getText().isBlank()) playerNames.add(player2.getText());
            int nbBots = 0;
            try {
                nbBots = Integer.parseInt(botsField.getText());
            } catch (NumberFormatException ignored) {}
            if(handler != null) { handler.gameStart(playerNames, nbBots);
            }
            overlayRoot.setVisible(false); // fermer l'overlay
            if (onValidated != null) {
                onValidated.accept(playerNames, nbBots);
            }
        });

        container.getChildren().addAll(title, player1, player2, botsField, validate);
        overlayRoot.getChildren().add(container);

        // Centrer le container
        StackPane.setAlignment(container, Pos.CENTER);

        // Ajouter l'overlay au parent
        parent.getChildren().add(overlayRoot);
    }

    public void show(boolean b) {
        overlayRoot.setVisible(true);
    }

    public void setOnValidated(BiConsumer<ArrayList<String>, Integer> callback) {
        this.onValidated = callback;
    }

    public void setVisible(boolean b) {
        overlayRoot.setVisible(b);
    }
}
