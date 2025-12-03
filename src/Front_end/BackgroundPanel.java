package Front_end;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private final Image backgroundImage; // The image to be used as the background

    /**
     * Constructor for the BackgroundPanel class.
     *
     * @param image The image to be set as the background.
     */
    public BackgroundPanel(Image image) {
        this.backgroundImage = image;
        this.setLayout(null); // Set layout to null for manual placement of components
        if (backgroundImage != null) {
            setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this))); // Set preferred size based on the image's dimensions
        }
    }

    /**
     * Paints the component with the background image.
     *
     * @param g The Graphics object used for painting the component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensure proper painting of the panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the image to fill the entire panel
        }
    }
}
