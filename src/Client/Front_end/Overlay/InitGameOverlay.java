package Client.Front_end.Overlay;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import Client.Front_end.ViewHandler;
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
        Label title = new Label("Lobby");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // TextFields pour les joueurs
        TextField playerName = new TextField();
        playerName.setPromptText("Votre nom");

        // Bouton de validation
        Button validate = new Button("Start Game");
        validate.setOnAction(e -> {
            if(handler != null) { handler.Ready(playerName.getText());
            }
            overlayRoot.setVisible(false);
        });

        container.getChildren().addAll(title, playerName, validate);
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
