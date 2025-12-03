package Front_end;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class InfoPanel extends JPanel {

    private final ArrayList<InfoPlayer> infoPlayers; // List of InfoPlayer panels representing other players' info

    /**
     * Constructor for the InfoPanel class.
     * Initializes the info panels for each player (except the main player) and sets up resizing behavior.
     * @param hands List of hands, each representing the cards of a player
     * @param names List of player names
     * @param mainPlayerIndex Index of the main player
     */
    public InfoPanel(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int mainPlayerIndex, ArrayList<Integer> PlayerPoints) {
        setLayout(null); // Allows for custom positioning of the panels
        setOpaque(false); // Makes the panel transparent
        infoPlayers = new ArrayList<>();
        int j = 0;

        // Add an InfoPlayer panel for each player except the main player
        for (int i = 0; i < hands.size(); i++) {
            if (mainPlayerIndex != i) {
                infoPlayers.add(new InfoPlayer(hands.get(i), hands.size(), names.get(i), PlayerPoints.get(i)));
                add(infoPlayers.get(j));
                j++;
            }
        }

        // Add component listener to adjust positioning when the panel is resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                positionInfoPlayers(hands.size());
            }
        });
    }

    /**
     * Positions the InfoPlayer panels based on the number of players.
     * @param numPlayers The number of players (used to distribute the panels evenly)
     */
    private void positionInfoPlayers(int numPlayers) {
        int margin = 50; // Space between the panels
        int width = (getWidth() - margin) / numPlayers; // Calculate the width of each InfoPlayer panel
        int height = (int) (getHeight() / 1.5f); // Set the height of each InfoPlayer panel

        // Set bounds for each InfoPlayer panel
        for (int i = 0; i < infoPlayers.size(); i++) {
            infoPlayers.get(i).setBounds(width * i + margin / 2, 0, width, height);
        }
    }

    /**
     * Updates the InfoPlayer panels with new hand data, names, and points.
     * @param hands The updated list of hands for each player
     * @param names The updated list of names for each player
     * @param mainPlayerIndex The index of the main player
     * @param gamePoints The updated game points for each player
     */
    protected void update(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int mainPlayerIndex, ArrayList<Integer> gamePoints) {
        int infoIndex = 0;

        // Update each InfoPlayer (except for the main player)
        for (int i = 0; i < hands.size(); i++) {
            if (i != mainPlayerIndex) {
                infoPlayers.get(infoIndex).update(hands.get(i), names.get(i), gamePoints.get(i));
                infoIndex++;
            }
        }

        // Reposition the panels after the update
        positionInfoPlayers(hands.size());
    }
}
