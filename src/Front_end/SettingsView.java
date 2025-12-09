package Front_end;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.function.BiConsumer;

public class SettingsView extends VBox {
    private static ViewHandler handler;
    private BiConsumer<Integer, Integer> callback;

    public SettingsView(ViewHandler viewHandler) {
        handler = viewHandler;
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setStyle("-fx-background-color: rgba(0,0,0,0.6);");

        TextField widthField = new TextField();
        widthField.setPromptText("Width");

        TextField heightField = new TextField();
        heightField.setPromptText("Height");

        Button validate = new Button("Validate");

        getChildren().addAll(new Label("Settings"), widthField, heightField, validate);

        validate.setOnAction(e -> {
            int w = Integer.parseInt(widthField.getText());
            int h = Integer.parseInt(heightField.getText());
            if (callback != null) callback.accept(w, h);
        });
    }

    public void onValidated(BiConsumer<Integer, Integer> callback) {
        this.callback = callback;
    }
}
