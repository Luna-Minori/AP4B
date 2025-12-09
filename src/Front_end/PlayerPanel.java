package Front_end;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.swing.text.View;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PlayerPanel extends VBox {
    private HandPanel handPanel;
    private ViewHandler handler;
    private StackPane nameLabelContainer;
    private Label pointLabel;
    private HBox buttons;
    private StackPane handContainer;
    private ArrayList<Integer> hand;
    private String name;
    private int point;
    private Consumer<Integer> onCardClick;
    private Consumer<Boolean> onHandDownClicked;
    private boolean mainPlayer;

    public PlayerPanel(ArrayList<Integer> hand, String name, int point, boolean mainPlayer, ViewHandler viewHandler) {
        handler = viewHandler;
        this.hand = hand;
        this.name = name;
        this.point = point;
        this.mainPlayer = mainPlayer;

        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("playerNameContainer");
        nameLabelContainer = new StackPane(nameLabel);

        // Bouton HandDown
        Button lowestButton = new Button("lowest");
        lowestButton.setId("lowest");
        lowestButton.getStyleClass().add("askButtonCard");

        Button highestButton = new Button("highest");
        highestButton.setId("highest");
        highestButton.getStyleClass().add("askButtonCard");

        buttons = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        buttons.getChildren().addAll(lowestButton, spacer, highestButton);

        lowestButton.setOnAction(e -> {if (handler != null) { handler.lowestRequested(name);}});
        highestButton.setOnAction(e -> {if (handler != null) {handler.highestRequested(name);}});

        // Créer la main du joueur
        ArrayList<CardPanel> cards = new ArrayList<>();
        for (Integer integer : hand) {
            System.out.println("Player " + name + " is the mainPlayer : " + mainPlayer);
            CardPanel card;
            if (mainPlayer) {
                card = new CardPanel(integer, true, true);
            } else {
                card = new CardPanel(integer, false, true);
            }
            cards.add(card);
        }
        handPanel = new HandPanel(cards, mainPlayer);
        handContainer = new StackPane();
        handContainer.getChildren().add(handPanel);
        handContainer.getStyleClass().add("handPanel");
        handContainer.prefWidthProperty().bind(widthProperty());
        handContainer.prefHeightProperty().bind(heightProperty().multiply(0.7));

        getChildren().addAll(nameLabelContainer, buttons, handContainer);

        widthProperty().addListener((obs, oldV, newV) -> resize());
        heightProperty().addListener((obs, oldV, newV) -> resize());

        URL url = getClass().getResource("/Front_end/assets/Player_back.png");
        if (url != null) {
            System.out.println("Image trouvée : " + url.toExternalForm());
        } else {
            System.out.println("Image introuvable !");
        }
    }

    private void resize() {
        double w = getWidth();
        double h = getHeight();

        nameLabelContainer.setPrefWidth(w);
        nameLabelContainer.setLayoutY(h * 0.05);
        nameLabelContainer.setAlignment(Pos.CENTER);
    }

    public void updateHand(ArrayList<Integer> newHand) {
        this.hand = newHand;
        ArrayList<CardPanel> cards = new ArrayList<>();
        for (int i = 0; i < newHand.size(); i++) {
            if(mainPlayer) {
                CardPanel card = new CardPanel(hand.get(i).intValue(), true, true);
                cards.add(card);
            }
            else{
                CardPanel card = new CardPanel(hand.get(i).intValue(), false, true);
                cards.add(card);
            }
        }
        handPanel.updateHand(cards, mainPlayer);
    }

    public void updatePoints(int point) {
        this.point = point;
        pointLabel.setText("Hand points: " + point);
    }

    public void setOnCardClick(Consumer<Integer> listener) {
        this.onCardClick = listener;
    }

    public void setOnHandDownClicked(Consumer<Boolean> listener) {
        this.onHandDownClicked = listener;
    }
}
