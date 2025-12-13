package Client;

import Client.Controler.Controler;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application {

    @Override
    public void start(Stage stage) {
        Controler controler = new Controler(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}