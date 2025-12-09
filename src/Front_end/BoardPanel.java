package Front_end;

import Back_end.DTO.PlayerInfo;
import Back_end.GameState;
//import Front_end.assets.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
// --- Background ---


// --- Fields ---

//playersField = new PlayerField(info.getPlayers());
/*
        VBox layout = new VBox();
        layout.setAlignment(Pos.TOP_CENTER);
        layout.spacingProperty().bind(root.heightProperty().multiply(0.05));

        layout.getChildren().addAll(middleCardField ); // playersField
        root.getChildren().add(layout);

        // --- Settings button ---
        ImageView settingsIcon = new ImageView(
                new Image(getClass().getResource("/Front_end/assets/Card_Back.png").toExternalForm())
        );
        settingsIcon.setFitWidth(50);
        settingsIcon.setFitHeight(50);
        settingsButton = new Button();
        settingsButton.setGraphic(settingsIcon);
        settingsButton.setStyle("-fx-background-color: transparent;");
        StackPane.setAlignment(settingsButton, Pos.TOP_RIGHT);
        root.getChildren().add(settingsButton);

        // --- Callbacks ---
       // middleCardField.setOnDrawClicked(onDrawClicked);
        //playersField.setOnPlayCardClicked(onPlayCardClicked);
*/
// --- Stage ---


public class BoardPanel extends VBox {
    private ViewHandler handler;
    private GameField middleCardField;
    private PlayerField playersField;
    //private final Button settingsButton;

    public BoardPanel(GameState info, ViewHandler viewHandler) {
        handler = viewHandler;

        URL bgUrl = getClass().getResource("/Front_end/assets/Back_espace.jpeg");

        if (bgUrl != null) {
            String bgPath = bgUrl.toExternalForm();
            Image bg = new Image(bgPath, true);

            BackgroundSize bgSize = new BackgroundSize(
                    100, 100,
                    true, true,
                    false, true
            );

            BackgroundImage bgImg = new BackgroundImage(
                    bg,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    bgSize
            );

            setBackground(new Background(bgImg));
        } else { // sécurité si pas d'assets
            setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);");
        }
        setUpGameField(info);
        setUpPlayersField(info);
        getChildren().add(middleCardField);
        getChildren().add(playersField);
    }

    private void setUpGameField(GameState info) {
        middleCardField = new GameField(info.getMiddleCards(), handler);
        middleCardField.setId("middle-card-field");
        VBox.setVgrow(middleCardField, Priority.ALWAYS);
        middleCardField.maxWidthProperty().bind(this.widthProperty().multiply(0.98));
        middleCardField.maxHeightProperty().bind(this.heightProperty().multiply(0.3));
        middleCardField.show();
    }

    private void setUpPlayersField(GameState info) {
        playersField = new PlayerField(info.getPlayers(), info.getCurrentPlayerIndex(), handler);
        playersField.setId("players-field");
        VBox.setVgrow(playersField, Priority.ALWAYS);
        playersField.maxWidthProperty().bind(this.widthProperty().multiply(0.98));
        playersField.maxHeightProperty().bind(this.heightProperty().multiply(0.65));
        playersField.show();
    }
}
