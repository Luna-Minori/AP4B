package Client.Front_end.Menu;

import Client.Front_end.Overlay.*;
import Client.Front_end.SettingsView;
import Client.Front_end.ViewHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Menu JavaFX — conversion propre du menu Swing.
 */

// --- Background ---
// Charge l'image depuis resources: /Front_end/assets/Back.png /*         String bgPath = getClass().getResource("/Front_end/assets/BackGroundMenu.mp4") != null
//                ? getClass().getResource("/Front_end/assets/BackGroundMenu.mp4").toExternalForm()
//                : null;
//
//        if (bgPath != null) {
//            Media media = new Media(bgPath);
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // boucle infinie
//            mediaPlayer.setAutoPlay(true);
//
//            MediaView mediaView = new MediaView(mediaPlayer);
//            mediaView.setPreserveRatio(false); // remplissage complet
//            mediaView.fitWidthProperty().bind(root.widthProperty());
//            mediaView.fitHeightProperty().bind(root.heightProperty());
//
//            // Ajouter la vidéo en premier dans un StackPane pour être en fond
//            root.getChildren().add(0, mediaView); // index 0 pour être derrière les autres nodes
//        } else {
//            root.setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);");
//        }
public class Menu extends BorderPane {
    private ViewHandler handler;
    private final SettingsView settingsView;
    private final InitGameOverlay initGameView;
    private final StackPane centerStack = new StackPane();

    public Menu(ViewHandler viewHandler) {
        handler = viewHandler;
        String bgPath = getClass().getResource("/Front_end/assets/Back.png") != null
                ? getClass().getResource("/Front_end/assets/Back.png").toExternalForm()
                : null;

        if (bgPath != null) {
            Image bg = new Image(bgPath, true);

            BackgroundSize bgSize = new BackgroundSize(
                    100, 100,
                    true, true,
                    true, false
            );

            BackgroundImage bgImg = new BackgroundImage(
                    bg,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    bgSize
            );

            setBackground(new Background(bgImg));
        } else { //Safe si pas assets
            setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);");
        }

        setCenter(centerStack);

        VBox menuBox = new VBox(50);
        menuBox.setId("menuBox");
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(0,0,0,0.6);");
        menuBox.prefWidthProperty().bind(widthProperty().multiply(0.2));

        Button play = createMenuButton("Play");
        Button settings = createMenuButton("Settings");
        Button quit = createMenuButton("Quit");

        play.getStyleClass().add("menu-button");
        settings.getStyleClass().add("menu-button");
        quit.getStyleClass().add("menu-button");

        menuBox.getChildren().addAll(play, settings, quit);
        setLeft(menuBox);

        settingsView = new SettingsView(handler);
        initGameView = new InitGameOverlay(centerStack, handler);

        centerStack.getChildren().addAll(settingsView);
        settingsView.setVisible(false);
        initGameView.setVisible(false);
        setCenter(centerStack);

        quit.setOnAction(e -> System.exit(0));

        play.setOnAction(event -> {
            menuBox.setVisible(false);
            menuBox.setManaged(false);
            settingsView.setVisible(false);
            initGameView.show(false);
        });

        settings.setOnAction(event -> {
            menuBox.setVisible(false);
            initGameView.show(false);
            settingsView.setVisible(true);

            settingsView.onValidated((w, h) -> {
                setWidth(w);
                setHeight(h);
                settingsView.setVisible(false);
                menuBox.setVisible(true);
            });
        });
    }

    private Button createMenuButton(String text) {
        Button b = new Button(text);
        b.setAlignment(Pos.CENTER);
        b.setMaxWidth(Double.MAX_VALUE);
        return b;
    }
}
