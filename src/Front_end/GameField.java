package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class GameField extends JPanel {
    private MiddleCardPanel middleCardPanel; // Panel for the card bin (e.g., to show the remaining rounds)
    private Consumer<Integer> onDrawClicked; // Consumer for handling card draw clicks
    private Consumer<Integer> handDownClicked; // Consumer for handling "hand down" clicks (if applicable)

    /**
     * Constructor for the GameField class.
     * Initializes the panels and handles component resize events.
     */
    public GameField(ArrayList<Integer> middleCard) {
        setLayout(null);
        setOpaque(true);
        //setBackground(new Color(255, 255, 255));

        createMiddleCardPanelPanel(middleCard); // Initialize the draw panel
        add(middleCardPanel); // Add the bin card panel to the main panel

        // Wait for the GameField to be displayed before adjusting sizes
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                responsiveLayout(); // Adjust layout when resized
            }
        });
        responsiveLayout();
    }

    /**
     * Creates the Draw Panel, which will hold all the cards of the player.
     * Depending on the size of the deck, it will create a different number of cards.
     * @param deck The list of card values to display in the draw panel
     */
    private void createMiddleCardPanelPanel(ArrayList<Integer> deck) {
        ArrayList<CardPanel> middleCards = new ArrayList<>();
        for (Integer cardValue : deck) {
            //System.out.println("Carte : " + cardValue);
            middleCards.add(new CardPanel(cardValue, true, true, true)); // Create a CardPanel for each card in the deck
            System.out.println("Carte : " + middleCards.getLast().toString());
        }
        middleCardPanel = new MiddleCardPanel(middleCards); // Initialize the draw panel with the list of card panels
    }

    /**
     * Handles the layout and positioning of the panels in response to a resize event.
     * Adjusts the size and position of the Bin Card Panel and Draw Panel.
     */
    private void responsiveLayout() {
        int panelWidth = (int) (getWidth());
        int panelHeight = (int) (getHeight());
        float ratioMarginX = 0.01f;
        int marginX = (int) (panelWidth * ratioMarginX);
        int sizeXofMiddleCardPanelWidth = panelWidth - 2 * marginX;

        float ratioMarginY = 0.05f;
        int marginY = (int) (panelHeight * ratioMarginY);
        int sizeYofMiddleCardPanelWidth = panelHeight - 2 * marginY;
        middleCardPanel.setBounds(marginX, marginY, sizeXofMiddleCardPanelWidth, sizeYofMiddleCardPanelWidth);
    }

    /**
     * Sets the callback listener for the draw panel card click event.
     * @param listener The listener to be called when a card is clicked in the draw panel
     */
    public void drawClick(Consumer<Integer> listener) {
        this.onDrawClicked = listener;
    }

    /**
     * Sets the callback listener for the "hand down" click event (if applicable).
     * @param listener The listener to be called when the "hand down" action occurs
     */
    public void handDownClicked(Consumer<Integer> listener) {
        this.handDownClicked = listener;
    }

    /**
     * Updates the Draw panel, specifically removing the first card and resetting the click listener.
     */
    protected void updateMiddleCardPanel() {
        middleCardPanel.revalidate(); // Revalidate the component to update its layout
        middleCardPanel.repaint(); // Repaint the component to reflect the changes
    }
}
