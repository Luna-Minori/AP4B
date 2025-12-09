package Front_end;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class HandPanel extends Pane {
    private final ArrayList<CardPanel> cardPanels = new ArrayList<>();
    private boolean mainPlayer;

    public HandPanel(ArrayList<CardPanel> hand, boolean isMainPlayer) {
        this.mainPlayer = isMainPlayer;
        setPickOnBounds(false); // Permet clic sur transparence

        Timeline timeline = new Timeline();
        for (int i = 0; i < hand.size(); i++) {
            final int index = i;
            KeyFrame kf = new KeyFrame(Duration.millis(500 * i), e -> {
                CardPanel card = hand.get(index);
                cardPanels.add(card);
                getChildren().add(card);
                layoutCards();
                //card.startAnimation(); // Animation de scale ou apparition
            });
            timeline.getKeyFrames().add(kf);
        }
        timeline.play();

        // Redimension dynamique
        widthProperty().addListener((obs, oldV, newV) -> layoutCards());
        heightProperty().addListener((obs, oldV, newV) -> layoutCards());
    }

    private void layoutCards() {
        double w = getWidth();
        double h = getHeight();
        int n = cardPanels.size();
        if (n == 0) return;
        double cardHeight = h * 0.90;
        double spacing = w * 0.1; // Espacement entre cartes
        for (int i = 0; i < n; i++) {
            CardPanel card = cardPanels.get(i);
            double x = i * spacing;
            double y = (h - cardHeight) / 2;
            card.setLayoutY(y);
            card.setLayoutX(x);
            card.setPrefHeight(cardHeight);
        }
    }

    public void updateHand(ArrayList<CardPanel> hand, boolean isMainPlayer) { /*
        this.mainPlayer = isMainPlayer;
        getChildren().clear();
        cardPanels.clear();

        for (int i = 0; i < hand.size(); i++) {
            CardPanel card = hand.get(i);
            cardPanels.add(card);
            getChildren().add(card);
        }
        layoutCards(); */
    }

    public void clearHand() {
        getChildren().clear();
        cardPanels.clear();
    }

    public ArrayList<CardPanel> getCardPanels() {
        return cardPanels;
    }
}
