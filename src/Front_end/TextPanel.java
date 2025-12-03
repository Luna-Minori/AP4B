package Front_end;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    private String text;

    /**
     * Constructor: Initializes the panel with a given text.
     *
     * @param text The text to be displayed on the panel.
     */
    public TextPanel(String text) {
        this.text = text;
        setOpaque(false); // The panel is transparent
    }

    /**
     * Override paintComponent to draw the text on the panel.
     * This method is called automatically when the panel needs to be redrawn.
     *
     * @param g The Graphics object used to paint the component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(getFont()); // Set the font of the text
        g.setColor(getForeground()); // Set the text color
        g.drawString(text, 0, getHeight() / 2); // Draw the text at the position (0, height/2)
    }

    /**
     * Getter for the current text displayed on the panel.
     *
     * @return The current text.
     */
    public String getText() {
        return text;
    }

    /**
     * Setter to update the text and trigger a repaint of the panel.
     *
     * @param newText The new text to be displayed on the panel.
     */
    public void setText(String newText) {
        this.text = newText;
        repaint(); // Redraw the panel with the new text
    }
}
