package Client.Front_end;

import Common.DTO.CardInfo;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.function.Consumer;

public class GameField extends Pane {
    private ViewHandler handler;
    private MiddleCardPanel middleCardPanel;

    public GameField(ArrayList<CardInfo> middleCards, ViewHandler viewHandler) {
        handler = viewHandler;
        createMiddleCardPanel(middleCards);
        getChildren().add(middleCardPanel);

        widthProperty().addListener((obs, oldVal, newVal) -> layoutMiddleCard());
        heightProperty().addListener((obs, oldVal, newVal) -> layoutMiddleCard());
    }

    private void createMiddleCardPanel(ArrayList<CardInfo> deck) {
        ArrayList<CardPanel> cardPanels = new ArrayList<>();
        for (CardInfo cardInfo : deck) {
            CardPanel card = new CardPanel(cardInfo.getValue(), cardInfo.isRevealed());
            cardPanels.add(card);
        }
        middleCardPanel = new MiddleCardPanel(cardPanels, handler);
    }

    private void layoutMiddleCard() {
        double w = getWidth();
        double h = getHeight();
        double marginX = w * 0.01;
        double marginY = h * 0.05;
        double panelWidth = w - 2 * marginX;
        double panelHeight = h - 2 * marginY;

        middleCardPanel.setLayoutX(marginX);
        middleCardPanel.setLayoutY(marginY);
        middleCardPanel.setPrefWidth(panelWidth);
        middleCardPanel.setPrefHeight(panelHeight);
    }

    public void updateMiddleCardPanel(ArrayList<Integer> deck) {
        middleCardPanel.updateCards(deck);
    }

    public void show() {
        this.setVisible(true);
    }

    public void update(ArrayList<Integer> middleCards) {
        middleCardPanel.updateCards(middleCards);
    }
}
