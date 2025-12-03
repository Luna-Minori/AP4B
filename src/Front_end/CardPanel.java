package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class CardPanel extends JPanel {
    private Image FrontCard;
    private final Image BackCard;
    private boolean front;
    private float scale = 0.0f; // Starts at 0% of its size
    private Timer Animation;
    private final boolean animateOnCreate;
    private final int value;
    private Consumer<Integer> onCardClicked;
    private final boolean handcard;
    private final int totalPlayers; // For responsive resizing

    /**
     * Constructor for the CardPanel class.
     * Initializes the card with front/back images, animations, and click handling.
     *
     * @param value              The value of the card (used for the front image).
     * @param totalPlayers       The total number of players (for responsive resizing).
     * @param front              Boolean indicating if the front of the card is visible.
     * @param playanimation      Whether the card should have an animation when shown.
     * @param needclicked        Whether the card should be clickable.
     * @param animateOnCreate    Whether the card should animate on creation.
     */
    public CardPanel(int value, int totalPlayers, boolean front, boolean playanimation, boolean needclicked, boolean animateOnCreate) {
        this.front = front;
        this.handcard = playanimation;
        this.animateOnCreate = animateOnCreate;
        this.totalPlayers = totalPlayers;
        this.value = value;

        // Load the card images
        ImageIcon FrontC = new ImageIcon("./src/Front_end/Card_" + 1 + ".png");
        ImageIcon BackC = new ImageIcon("./src/Front_end/Card_Back.png");
        this.FrontCard = FrontC.getImage();
        this.BackCard = BackC.getImage();
        setOpaque(false);

        if (handcard) {
            int y = 25;
            MouseAdapter hoverEffect = new MouseAdapter() {
                private int originalY;

                @Override
                public void mouseEntered(MouseEvent e) {
                    CardPanel card = (CardPanel) e.getSource();
                    originalY = card.getY();
                    if (front) {
                        card.setLocation(card.getX(), originalY - y); // Lift the card
                    } else {
                        card.setLocation(card.getX(), originalY + y / 4);
                    }
                    card.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    CardPanel card = (CardPanel) e.getSource();
                    card.setLocation(card.getX(), originalY); // Reset card position
                    card.repaint();
                }
            };
            this.addMouseListener(hoverEffect);
        }

        if (needclicked) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleCardClick();
                }
            });
        }

        if (animateOnCreate) {
            startAnimation();
        } else {
            scale = 1.0f; // Set scale to 100% to avoid staying invisible
        }
    }

    /**
     * Secondary constructor for the CardPanel.
     * This constructor disables the animation on create by default.
     *
     * @param value          The value of the card.
     * @param front          Boolean indicating if the front is visible.
     * @param playanimation  Whether to animate the card.
     * @param needclicked    Whether the card should be clickable.
     */
    public CardPanel(int value, boolean front, boolean playanimation, boolean needclicked) {
        this(value, 1, front, playanimation, needclicked, false);
    }

    /**
     * Handles the click on the card and triggers the callback if defined.
     */
    private void handleCardClick() {
        if (onCardClicked != null) {
            onCardClicked.accept(this.value);
        }
    }

    /**
     * Resizes the card based on the parent container size.
     *
     * @param parent The parent container of the card.
     */
    public void resizeWithin(Container parent) {
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();
        double frontAspectRatio = (double) FrontCard.getWidth(null) / FrontCard.getHeight(null);
        double backAspectRatio = (double) BackCard.getWidth(null) / BackCard.getHeight(null);

        if (handcard) {
            if (front && FrontCard != null) {
                int w = (int) (parentWidth * 0.8);
                int h = (int) (w / frontAspectRatio);

                if (h > parentHeight) {
                    h = (int) (parentHeight * 0.8);
                    w = (int) (h * frontAspectRatio);
                }
                setSize(w, h); // Adjust size but do not set bounds
            }

            if (!front && BackCard != null) {
                if (parent instanceof HandPanel) {
                    int h = (int) (parentWidth * 0.4);
                    int w = (int) (h * frontAspectRatio);
                    setSize(w, h);
                }
            }
        } else {
            if (front && FrontCard != null) {
                int w = parentWidth / 6;
                int h = (int) (w / frontAspectRatio);
                setSize(w, h);
            }

            if (!front && BackCard != null) {
                int w = parentWidth;
                int h = (int) (w / backAspectRatio);

                if (h > parentHeight) {
                    h = parentHeight;
                    w = (int) (h * backAspectRatio);
                }

                setSize(w, h); // Adjust size but do not set bounds
            }
        }
    }

    /**
     * Flips the card to show the opposite side.
     */
    private void flip() {
        front = !front;
        repaint();
    }

    @Override
    /**
     * Paints the card image onto the panel.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (front && FrontCard != null) {
            resizeWithin(getParent());
            Graphics2D g2d = (Graphics2D) g;
            int width = (int) (getWidth() * 0.95);
            int height = (int) (getHeight() * 0.95);
            int scaledW = (int) (width * scale);
            int scaledH = (int) (height * scale);
            int x = (width - scaledW) / 2;
            int y = (height - scaledH) / 2;
            g2d.drawImage(FrontCard, x, y, scaledW, scaledH, this);
        }

        if (!front && BackCard != null) {
            resizeWithin(getParent());
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            if (handcard) {
                g2d.drawImage(BackCard, 0, 0, width, height, this);
            } else {
                g2d.drawImage(BackCard, 15, 0, (int) (width * 0.9), (int) (height * 0.9), this);
            }
        }
    }

    /**
     * Sets the callback for when the card is clicked.
     *
     * @param listener The listener to handle the click event.
     */
    public void setOnCardClicked(Consumer<Integer> listener) {
        this.onCardClicked = listener;
    }

    /**
     * Updates the card with new value and visibility.
     *
     * @param value The new value of the card.
     * @param front Boolean indicating if the front is visible.
     */
    protected void update(int value, boolean front) {
        ImageIcon FrontC = new ImageIcon("./src/Front_end/Card_" + value + ".png");
        this.FrontCard = FrontC.getImage();
        startAnimation();
        this.front = front;
    }

    /**
     * Starts the animation for the card (scale up effect).
     */
    protected void startAnimation() {
        scale = 0.0f;
        Timer timer = new Timer(40, null);
        timer.addActionListener(e -> {
            scale += 0.1f;
            if (scale >= 1.0f) {
                scale = 1.0f;
                timer.stop();
            }
            repaint();
        });
        timer.start();
    }
}
