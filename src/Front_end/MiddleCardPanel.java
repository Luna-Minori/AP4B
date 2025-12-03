package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MiddleCardPanel extends JPanel {
    private ArrayList<CardPanel> middleCard = new ArrayList<>();
    private Consumer<Integer> clicked;

    public MiddleCardPanel(ArrayList<CardPanel> middleCards) {
        setLayout(null);
        setOpaque(true);
        setBackground(new Color(0,255,0));

        int[] i = {0}; // Counter to iterate over the deck
        Timer timer = new Timer(50, e -> {
            if (i[0] < middleCards.size()) {
                CardPanel card = middleCards.get(i[0]);
                Rectangle bounds = calculateCardPosition(i[0], middleCards.size());
                card.setBounds(bounds); // Set the card's position
                this.middleCard.add(card);
                this.add(card);
                this.repaint();

                // Add click handler on the first card
                if (i[0] == 0) {
                    card.setOnCardClicked((v) -> {
                        if (clicked != null) {
                            System.out.println("Draw panel " + v);
                            clicked.accept(v);
                        }
                    });
                }
                i[0]++;
                addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        updateCardPositions(); // Update card positions when resized
                    }
                });
            } else {
                ((Timer) e.getSource()).stop(); // Stop the timer when all cards are drawn
            }
        });
        timer.start(); // Start the timer to animate card drawing
    }

    /**
     * Calcule la position d'une carte dans un arrangement en éventail ou en colonne.
     *
     * @param index L'index de la carte à positionner.
     * @return Un rectangle représentant la position et la taille de la carte.
     */
    private Rectangle calculateCardPosition(int index, int numberOfCards) {
        int w = getWidth();
        int h = getHeight();

        // Largeur disponible pour une carte
        int cardWidth = w / numberOfCards;
       // System.out.println("Card " + index + " width: " + w + " / " + numberOfCards + " = " + cardWidth);
        int cardHeight = (int) (h * 0.8f); // hauteur occupée par la carte

        // Position X basée sur l'index
        int x = index * cardWidth;

        // Centrage vertical
        int y = (h - cardHeight) / 2;
        //System.out.println("Card " + index + " position: (" + x + ", " + y + "), size: (" + cardWidth + ", " + cardHeight + ")");
        return new Rectangle(x, y, cardWidth, cardHeight);
    }

    /**
     * Updates the positions of all cards in the panel.
     */
    private void updateCardPositions() {
        for (int i = 0; i < middleCard.size(); i++) {
            CardPanel card = middleCard.get(i);
            Rectangle bounds = calculateCardPosition(i, middleCard.size()); // Calculate new position
            card.setBounds(bounds); // Set the card's new bounds
        }
        revalidate(); // Revalidate the layout
        repaint(); // Repaint the panel
    }

    /**
     * Sets the callback for when a card in the draw panel is clicked.
     *
     * @param listener The listener to handle the click event.
     */
    public void drawClicked(Consumer<Integer> listener) {
        this.clicked = listener;
    }

    protected void update(ArrayList<Integer> deck) {
        for (CardPanel card : middleCard) {
            this.remove(card); // Remove each card from the panel
        }
        middleCard.clear(); // Clear the list of card panels
        for (int i = 0; i < deck.size(); ++i) {
            CardPanel card = new CardPanel(deck.get(i), false, false, false);
            Rectangle bounds = calculateCardPosition(i, middleCard.size());
            card.setBounds(bounds); // Set the card's position
            if (i == 0) {
                card.setOnCardClicked((v) -> {
                    if (clicked != null) {
                        System.out.println("Draw panel " + v);
                        clicked.accept(v);
                    }
                });
            }
            this.middleCard.add(card);
            this.add(card);
        }
        revalidate(); // Revalidate the layout
        repaint(); // Repaint the panel
    }

    /**
     * Updates the draw panel by removing the first card and setting a new click listener if available.
     */
    protected void updateDraw() {
        if (!middleCard.isEmpty()) {  // Check if the list is not empty
            middleCard.remove(0);  // Remove the first card from the list
            if (!middleCard.isEmpty()) {  // Check again if the list is not empty
                middleCard.get(0).setOnCardClicked((v) -> {
                    if (clicked != null) {
                        System.out.println("Draw panel " + v);
                        clicked.accept(v);
                    }
                });
            }
        }
    }
}
