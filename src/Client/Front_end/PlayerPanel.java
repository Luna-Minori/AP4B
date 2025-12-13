package Client.Front_end;

import Common.DTO.CardInfo;
import Common.DTO.PlayerInfo;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class PlayerPanel extends VBox {
    private final int playerId;
    private HandPanel handPanel;
    private final ViewHandler handler;
    private StackPane nameLabelContainer;
    private Label pointLabel;
    private ArrayList<CardInfo> hand;
    private HandPanel askedCards;
    private String name;

    public PlayerPanel(PlayerInfo playerInfo, ViewHandler viewHandler) {
        handler = viewHandler;
        playerId = playerInfo.getId();
        createNameLabel(name);
        createButtons(name);
        createHand(hand);

        widthProperty().addListener((obs, oldV, newV) -> resize());
        heightProperty().addListener((obs, oldV, newV) -> resize());
    }


    public int getPlayerId(){
        return playerId;
    }

    public void update(ArrayList<Integer> hand, ArrayList<Integer> askedCard) {

    }

    public void update(ArrayList<Integer> hand) {

    }

    private void createNameLabel(String name) {
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("playerNameContainer");
        nameLabelContainer = new StackPane(nameLabel);
        getChildren().add(nameLabelContainer);
    }

    private void createHand() {
        createHand(null);
    }

    private void createHand(ArrayList<CardInfo> hand) {
        ArrayList<CardPanel> cards = new ArrayList<>();
        for (CardInfo cardInfo : hand) {
            CardPanel card = new CardPanel(cardInfo.getValue(), cardInfo.isRevealed());
            cards.add(card);
        }

        handPanel = new HandPanel(cards);
        StackPane handContainer = new StackPane();
        handContainer.getChildren().add(handPanel);
        handContainer.getStyleClass().add("handPanel");
        handContainer.prefWidthProperty().bind(widthProperty());
        handContainer.prefHeightProperty().bind(heightProperty().multiply(0.7));

        getChildren().add(handContainer);
    }

    private void createButtons(String name){
        // Bouton HandDown
        Button lowestButton = new Button("lowest");
        lowestButton.setId("lowest");
        lowestButton.getStyleClass().add("askButtonCard");

        Button highestButton = new Button("highest");
        highestButton.setId("highest");
        highestButton.getStyleClass().add("askButtonCard");

        HBox buttons = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        buttons.getChildren().addAll(lowestButton, spacer, highestButton);

        lowestButton.setOnAction(e -> {if (handler != null) { handler.lowestRequested(playerId);}});
        highestButton.setOnAction(e -> {if (handler != null) {handler.highestRequested(playerId);}});
        getChildren().addAll(buttons);
    }

    private void resize() {
        double w = getWidth();
        double h = getHeight();

        nameLabelContainer.setPrefWidth(w);
        nameLabelContainer.setLayoutY(h * 0.05);
        nameLabelContainer.setAlignment(Pos.CENTER);
    }

    public void updateHand(ArrayList<CardInfo> newHand) {
        ArrayList<CardPanel> cards = new ArrayList<>();
        for (CardInfo cardInfo : newHand) {
            CardPanel card = new CardPanel(cardInfo.getValue(), cardInfo.isRevealed());
            cards.add(card);
        }
        handPanel.updateHand(cards);
    }
}
