package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class InfoPlayer extends JPanel {
    private TextPanel nameTextPanel; // Panel to display the player's name
    private TextPanel gamePointsTextPanel; // Panel to display the player's game points
    private HandPanel handPanel; // Panel to display the player's hand of cards

    /**
     * Constructor for the InfoPlayer class.
     * Initializes the components that display the player's name, points, and hand.
     * @param hand List of integers representing the player's hand
     * @param totalPlayers Total number of players in the game
     * @param name The player's name
     * @param point The player's current game points
     */
    public InfoPlayer(ArrayList<Integer> hand, int totalPlayers, String name, int point) {
        setLayout(null); // Allows custom positioning of components
        setOpaque(false); // Makes the panel transparent

        // Create the hand panel and the text panels
        createHandPanel(hand, totalPlayers);
        createTextPanel(name, point);

        // Add components to the panel
        add(handPanel);
        add(nameTextPanel);
        add(gamePointsTextPanel);

        // Adjust the sizes of the panels
        adjustHandPanelSize();
        adjustTextPanelSize();

        // Add component listener to adjust sizes on resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustHandPanelSize();
                adjustTextPanelSize();
            }
        });
    }

    /**
     * Creates the HandPanel for the player based on their hand of cards.
     * @param hand The player's hand (list of card values)
     * @param totalPlayers The total number of players in the game
     */
    private void createHandPanel(ArrayList<Integer> hand, int totalPlayers) {
        ArrayList<CardPanel> cards = new ArrayList<>();
        for (int i = 0; i < hand.size(); ++i) {
            // Create a CardPanel for each card in the hand
            CardPanel card = new CardPanel(hand.get(i), totalPlayers, false, true, false, false);
            card.setLayout(null); // Allow custom positioning inside the CardPanel
            cards.add(card);
        }
        // Create the HandPanel using the list of CardPanels
        handPanel = new HandPanel(cards, false);
    }

    /**
     * Creates the TextPanels for the player's name and game points.
     * @param name The player's name
     * @param point The player's current game points
     */
    private void createTextPanel(String name, int point) {
        nameTextPanel = new TextPanel(name);
        nameTextPanel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font for the name
        gamePointsTextPanel = new TextPanel("Game Points : " + point);
        gamePointsTextPanel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for game points
    }

    /**
     * Adjusts the size and position of the hand panel within the InfoPlayer panel.
     */
    private void adjustHandPanelSize() {
        float handPanelHeightCoefficient = 0.9f; // Hand occupies 90% of the panel height

        // Adjust the bounds of the hand panel
        handPanel.setBounds((int) Math.ceil(nameTextPanel.getWidth() * 1.05), 0, (int) Math.ceil(getWidth() * 0.75), (int) Math.ceil(getHeight() * handPanelHeightCoefficient));
    }

    /**
     * Adjusts the size and position of the text panels (name and game points).
     */
    private void adjustTextPanelSize() {
        nameTextPanel.setBounds(0, 0, nameTextPanel.getText().length() * 20, (int) (getHeight() * 0.5));
        gamePointsTextPanel.setBounds(0, (int) (getHeight() * 0.5), gamePointsTextPanel.getText().length() * 20, (int) (getHeight() * 0.3));
    }

    /**
     * Called when the panel is resized, adjusts the text panel and hand panel sizes accordingly.
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        // Adjust the sizes whenever the panel is resized
        adjustTextPanelSize();
        adjustHandPanelSize();
    }

    /**
     * Updates the player's hand of cards and adjusts the hand panel size.
     * @param hand The updated list of card values for the player
     */
    protected void updateHandPanel(ArrayList<Integer> hand) {
        ArrayList<CardPanel> cards = new ArrayList<>();
        for (int i = 0; i < hand.size(); ++i) {
            // Create a CardPanel for each updated card in the hand
            CardPanel card = new CardPanel(hand.get(i), false, true, false);
            card.setLayout(null); // Allow custom positioning inside the CardPanel
            cards.add(card);
        }
        // Clear and update the hand panel with the new cards
        handPanel.clear();
        handPanel.update(cards, false);
    }

    /**
     * Updates the player's information (hand, name, and points).
     * @param hand The updated list of card values for the player
     * @param name The updated name of the player
     * @param point The updated game points of the player
     */
    protected void update(ArrayList<Integer> hand, String name, int point) {
        updateHandPanel(hand); // Update the player's hand
        nameTextPanel.setText(name); // Update the name
        gamePointsTextPanel.setText("Game Points : " + point); // Update the game points
        adjustTextPanelSize(); // Adjust the size of the text panels
        adjustHandPanelSize(); // Adjust the size of the hand panel
    }
}
