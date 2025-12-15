package Client.Front_end;/*

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Création du menu
        Menu menu = new Menu(stage);

        // Scene principale avec le root du menu
        Scene scene = new Scene(menu.getRoot(), 0, 0);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Front_end/style.css")).toExternalForm());
        stage.setFullScreen(true);
        stage.setResizable(false);
        // Configuration de la fenêtre
        stage.setTitle("Trio UTBM");
        stage.setScene(scene);

        //Image gif = new Image(getClass().getResource("/Front_end/Client.Front_end.assets/animated.gif").toExternalForm());
        //ImageView gifView = new ImageView((Element) gif);


        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/