package Front_end;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.function.Consumer;

public class GameField extends Pane {
    private ViewHandler handler;
    private MiddleCardPanel middleCardPanel;
    private Consumer<Integer> onDrawClicked;

    public GameField(ArrayList<Integer> middleCards, ViewHandler viewHandler) {
        // Créer le panel central avec les cartes
        handler = viewHandler;
        createMiddleCardPanel(middleCards);
        getChildren().add(middleCardPanel);

        // Redimension dynamique
        widthProperty().addListener((obs, oldVal, newVal) -> layoutMiddleCard());
        heightProperty().addListener((obs, oldVal, newVal) -> layoutMiddleCard());
    }

    private void createMiddleCardPanel(ArrayList<Integer> deck) {
        ArrayList<CardPanel> cardPanels = new ArrayList<>();
        for (Integer value : deck) {
            CardPanel card = new CardPanel(value, false, true);
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

    public void setOnDrawClicked(Consumer<Integer> listener) {
        this.onDrawClicked = listener;
    }

    public void updateMiddleCardPanel(ArrayList<Integer> deck) {
        middleCardPanel.updateCards(deck);
    }

    public void show() {
        this.setVisible(true);
    }
}
