package Front_end;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HandPanel extends JPanel {
    private final ArrayList<CardPanel> cardPanels; // List of card panels
    private boolean mainPlayer; // Flag to check if the player is the main player (used for animation)

    /**
     * Constructor for the HandPanel class.
     * Initializes the card panels and sets up the timer to animate the cards.
     * @param hand List of card panels representing the player's hand
     * @param isMainPlayer Flag indicating if this hand belongs to the main player
     */
    public HandPanel(ArrayList<CardPanel> hand, boolean isMainPlayer) {
        this.cardPanels = new ArrayList<>();
        this.mainPlayer = isMainPlayer;
        setLayout(null); // Allows for custom card placement
        setOpaque(false); // Makes the panel transparent

        // Start adding cards with a delay (for animation)
        int[] i = {0};
        new Timer(300, e -> {
            if (i[0] < hand.size()) {
                // Create a new card from the CardPanel object
                CardPanel card = hand.get(i[0]);
                // Wait for the panel's size to be calculated
                cardPanels.add(card);
                card.setBounds(calculateCardPosition(i[0])); // Calculate position for the fan-style layout
                add(card); // Add the card to the panel
                card.startAnimation(); // Start the card animation
                i[0]++;
                repaint();
            } else {
                ((Timer) e.getSource()).stop(); // Stop the timer once all cards are added
            }
        }).start();
    }

    /**
     * Calculates the position of each card to display them in a fan layout.
     * @param index The index of the card in the hand
     * @return The Rectangle representing the card's position and size
     */
    private Rectangle calculateCardPosition(int index) {
        // Calculate x position for the card based on the index
        int x = (int) (index * getWidth() * 0.1);
        int endX = x + 100; // End x position to check if the card exceeds panel width

        // Adjust card position if it exceeds the panel's width
        if (endX >= getWidth()) {
            x = (int) (((double) (index % getWidth()) / x) * getWidth() * 0.1);

            // Different positions depending on whether it's the main player
            if (mainPlayer) {
                return new Rectangle(x, (int) (getHeight() * 0.1), 1, 200);
            }
            return new Rectangle(x, (int) -(getHeight() * 0.3), 1, 200);
        }

        // If card fits within the panel width, position it in the first column
        if (mainPlayer) {
            return new Rectangle(x, (int) (getHeight() * 0.25), 1, 200);
        }

        // Otherwise, position it for the non-main player
        return new Rectangle(x, (int) -(getHeight() * 0.5), 1, 200);
    }

    /**
     * Gets the list of card panels.
     * @return The list of card panels
     */
    protected ArrayList<CardPanel> getCardPanels() {
        return cardPanels;
    }

    /**
     * Updates the hand of cards and animates them, with an option for the main player.
     * @param hand The updated list of card panels representing the player's hand
     * @param isMainPlayer Flag indicating if this hand belongs to the main player
     */
    protected void update(ArrayList<CardPanel> hand, boolean isMainPlayer) {
        this.mainPlayer = isMainPlayer;
        for (int i = 0; i < hand.size(); i++) {
            // Create a new card from the CardPanel object
            CardPanel card = hand.get(i);
            // Wait for the panel's size to be calculated
            cardPanels.add(card); // Add card to the list
            card.setBounds(calculateCardPosition(i)); // Calculate position for the fan layout
            add(card); // Add the card to the panel
            repaint();
        }
    }

    /**
     * Clears the hand by removing all cards from the panel and clearing the card list.
     */
    protected void clear() {
        for (CardPanel card : cardPanels) {
            this.remove(card); // Remove the card from the panel
        }
        cardPanels.clear(); // Clear the list of card panels
        repaint(); // Repaint the panel to reflect the changes
    }
}
