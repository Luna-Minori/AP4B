package Front_end;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MiddleCardPanel extends Pane {
    private ViewHandler handler;
    private final ArrayList<CardPanel> middleCards = new ArrayList<>();
    private Consumer<Integer> onCardClicked;

    public MiddleCardPanel(ArrayList<CardPanel> cards, ViewHandler viewHandler) {
        handler = viewHandler;
        // Ajouter les cartes progressivement avec animation
        this.getStyleClass().add("middleCardPanel");
        Timeline timeline = new Timeline();
        for (int i = 0; i < cards.size(); i++) {
            final int index = i;
            KeyFrame kf = new KeyFrame(Duration.millis(50 * i), e -> {
                CardPanel card = cards.get(index);
                middleCards.add(card);
                getChildren().add(card);
                layoutCards(); // Positionne toutes les cartes

                // Ajouter le listener sur la première carte
                if (index == 0) {
                    /*card.setOnCardClicked(v -> {
                        if (onCardClicked != null) {
                            System.out.println("Draw panel " + v);
                            onCardClicked.accept(v);
                        }
                    });*/
                }
            });
            timeline.getKeyFrames().add(kf);
        }
        timeline.play();

        // Redimension dynamique
        widthProperty().addListener((obs, oldVal, newVal) -> layoutCards());
        heightProperty().addListener((obs, oldVal, newVal) -> layoutCards());
    }

    private void layoutCards() {
        double w = getWidth();
        double h = getHeight();
        int n = middleCards.size();
        if (n == 0) return;

        // 1. Taille des cartes
        // Au lieu de w/n, on utilise un ratio basé sur la hauteur pour ne pas déformer l'image
        double cardHeight = h * 0.8;
        double cardWidth = cardHeight * 0.7; // Ratio standard (0.7 environ pour une carte de jeu)

        // 2. Calcul du décalage (offset) entre les cartes
        double offset = cardWidth; // Ton paramètre d'origine

        // 3. Calcul de la largeur TOTALE du groupe de cartes
        // Formule : (NbCartes - 1) * Décalage + LargeurDernièreCarte
        double totalGroupWidth = ((n - 1) * offset) + cardWidth;

        // (Optionnel) Sécurité : Si le groupe est plus large que l'écran, on réduit l'offset
        if (totalGroupWidth > w) {
            offset = (w - cardWidth - 40) / (n - 1); // On compresse pour que ça rentre
            totalGroupWidth = ((n - 1) * offset) + cardWidth;
        }

        // 4. Calcul du point de départ (X) pour centrer
        double startX = (w - totalGroupWidth) / 2;

        // 5. Placement
        for (int i = 0; i < n; i++) {
            CardPanel card = middleCards.get(i);
            card.setPrefWidth(cardWidth);
            card.setPrefHeight(cardHeight);

            // Position X = Point de départ + (index * décalage)
            card.setLayoutX(startX + (i * offset));

            // Centrage vertical (déjà bon dans ton code)
            card.setLayoutY((h - cardHeight) / 2);
        }
    }

    public void setOnCardClicked(Consumer<Integer> listener) {
        this.onCardClicked = listener;
    }

    public void updateCards(ArrayList<Integer> deck) {
        getChildren().clear();
        middleCards.clear();

        for (int i = 0; i < deck.size(); i++) {
            CardPanel card = new CardPanel(deck.get(i), false, true);
            middleCards.add(card);
            getChildren().add(card);
        }
        layoutCards();
    }

    public void updateDraw() {
        if (!middleCards.isEmpty()) {
            middleCards.remove(0);
            getChildren().remove(0);
            /*
            if (!middleCards.isEmpty()) {
                middleCards.get(0).setOnCardClicked(v -> {
                    if (onCardClicked != null) onCardClicked.accept(v);
                });
            }*/
            layoutCards();
        }
    }

    public void show(){
        this.setVisible(true);
    }
}
