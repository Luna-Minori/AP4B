package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Consumer;

public class DrawPanel extends JPanel {
    private final ArrayList<CardPanel> cardPanels; // List of card panels
    private final int spacingY = 2; // Vertical spacing between cards
    private final int spacingX = -1; // Horizontal spacing between cards
    private Consumer<Integer> onDrawClicked; // Callback for card clicks

    /**
     * Constructor for the DrawPanel class.
     * Initializes the panel and starts animating the card drawing process.
     *
     * @param deck The list of CardPanel objects to be drawn.
     */
    public DrawPanel(ArrayList<CardPanel> deck) {
        this.cardPanels = new ArrayList<>();
        setLayout(null);
        setOpaque(false);

        int[] i = {0}; // Counter to iterate over the deck
        Timer timer = new Timer(50, e -> {
            if (i[0] < deck.size()) {
                CardPanel card = deck.get(i[0]);
                Rectangle bounds = calculateCardPosition(i[0]);
                card.setBounds(bounds); // Set the card's position
                this.cardPanels.add(card);
                this.add(card);
                this.repaint();

                // Add click handler on the first card
                if (i[0] == 0) {
                    card.setOnCardClicked((v) -> {
                        if (onDrawClicked != null) {
                            System.out.println("Draw panel " + v);
                            onDrawClicked.accept(v);
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
     * Calculates the position of a card in a fan or column arrangement.
     *
     * @param index The index of the card to position.
     * @return A rectangle representing the position and size of the card.
     */
    private Rectangle calculateCardPosition(int index) {
        int cardWidth = getWidth();  // Get panel's width
        int cardHeight = getHeight(); // Get panel's height
        return new Rectangle(spacingX * index, spacingY * index, cardWidth, cardHeight);
    }

    /**
     * Updates the positions of all cards in the panel.
     */
    private void updateCardPositions() {
        for (int i = 0; i < cardPanels.size(); i++) {
            CardPanel card = cardPanels.get(i);
            Rectangle bounds = calculateCardPosition(i); // Calculate new position
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
        this.onDrawClicked = listener;
    }

    protected void update(ArrayList<Integer> deck) {
        for (CardPanel card : cardPanels) {
            this.remove(card); // Remove each card from the panel
        }
        cardPanels.clear(); // Clear the list of card panels
        for (int i = 0; i < deck.size(); ++i) {
            CardPanel card = new CardPanel(deck.get(i), false, false, false);
            Rectangle bounds = calculateCardPosition(i);
            card.setBounds(bounds); // Set the card's position
            if (i == 0) {
                card.setOnCardClicked((v) -> {
                    if (onDrawClicked != null) {
                        System.out.println("Draw panel " + v);
                        onDrawClicked.accept(v);
                    }
                });
            }
            this.cardPanels.add(card);
            this.add(card);
        }
        revalidate(); // Revalidate the layout
        repaint(); // Repaint the panel
    }

    /**
     * Updates the draw panel by removing the first card and setting a new click listener if available.
     */
    protected void updateDraw() {
        if (!cardPanels.isEmpty()) {  // Check if the list is not empty
            cardPanels.remove(0);  // Remove the first card from the list
            if (!cardPanels.isEmpty()) {  // Check again if the list is not empty
                cardPanels.get(0).setOnCardClicked((v) -> {
                    if (onDrawClicked != null) {
                        System.out.println("Draw panel " + v);
                        onDrawClicked.accept(v);
                    }
                });
            }
        }
    }
}
