package Client.Front_end.Menu;

import Client.Front_end.ViewHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AskNameOverlay extends StackPane {
    private final TextField playerName;

    public AskNameOverlay(ViewHandler handler) {
        setId("askNameOverlay");

        Label title = new Label("Your name :");
        title.getStyleClass().add("titleLabel");

        playerName = new TextField();
        playerName.setPromptText("Votre nom");
        playerName.getStyleClass().add("setNameInputText");
        playerName.maxWidthProperty().bind(playerName.prefWidthProperty());
        playerName.prefWidthProperty().bind(this.widthProperty().multiply(0.5));

        Button setName = new Button("Set Name");
        setName.getStyleClass().add("setNameButton");
        setName.setOnAction(e -> {
            if(handler != null && !playerName.getText().isEmpty()) {
                handler.setName(playerName.getText());
                setVisible(false);
            }
        });

        VBox container = new VBox(50);
        container.setAlignment(Pos.TOP_CENTER);;
        container.getChildren().addAll(title, playerName, setName);
        getChildren().addAll(container);
    }

    public void setVisibble(boolean b) {
        setVisible(b);
    }
}
