package Controler;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private Controler controler;

    @Override
    public void start(Stage stage) {
        controler = new Controler(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}