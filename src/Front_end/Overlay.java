package Front_end;

import Back_end.DTO.PlayerInfo;
import Back_end.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Overlay panel used to display a semi-transparent pause menu or scoreboard over the game.
 */
public class Overlay extends JPanel {

    private Consumer<Integer> onPauseClicked;
    private Consumer<Integer> onNextRoundClicked;
    private TextPanel titlePanel;
    private TextPanel settingsPanel;
    private TextPanel quitPanel;
    private TextPanel playPanel;
    private TextPanel nextRoundButton;
    private ScoreBoard scoreBoardPanel;

    /**
     * Constructs an overlay panel with optional scoreboard display.
     *
     * @param showScore   true to show the scoreboard, false to show the pause menu
     * @param parentWidth width of the parent container
     * @param parentHeight height of the parent container
     */
    public Overlay(boolean showScore, int parentWidth, int parentHeight, ArrayList<PlayerInfo> players) {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 200));
        createPauseMenu();
        setBounds(0, 0, parentWidth, parentHeight);

        addMouseListener(new MouseAdapter() {});
        addMouseMotionListener(new MouseAdapter() {});

        flipMode(showScore);
    }

    /**
     * Constructs an overlay with only scoreboard or pause menu based on showScore.
     *
     * @param showScore   true to show the scoreboard, false to show the pause menu
     * @param parentWidth width of the parent container
     * @param parentHeight height of the parent container
     */
    public Overlay(boolean showScore, int parentWidth, int parentHeight) {
        this(showScore, parentWidth, parentHeight, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Makes the overlay visible.
     *
     * @param rootPane the main game panel
     */
    public void showOverlay(BoardPanel rootPane) {
        setVisible(true);
    }

    /**
     * Hides the overlay from view.
     */
    public void hideOverlay() {
        setVisible(false);
    }

    /**
     * Switches the overlay content to show either scoreboard or pause menu.
     *
     * @param showScore true to display the scoreboard, false to show the pause menu
     */
    protected void flipMode(boolean showScore) {
        removeAll();

        if (showScore) {
            scoreBoardPanel = new ScoreBoard(new ArrayList<>(), new ArrayList<>());
            scoreBoardPanel.setBounds(getWidth() / 4, getHeight() / 4, 500, 300);
            setScoreButton();
            adjustScoreButtonPosition();
            add(scoreBoardPanel);
        } else {
            createPauseMenu();
            adjustPauseMenuPosition();
        }

        revalidate();
        repaint();
    }

    /**
     * Creates and configures the "Next round" button.
     */
    private void setScoreButton() {
        nextRoundButton = new TextPanel("Next round");
        nextRoundButton.setFont(new Font("Uncial Antiqua", Font.BOLD, 30));
        nextRoundButton.setForeground(Color.WHITE);

        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                TextPanel text = (TextPanel) e.getSource();
                Font baseFont = text.getFont();
                Map<TextAttribute, Object> attrs = new HashMap<>(baseFont.getAttributes());
                attrs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                text.setFont(new Font(attrs));
                text.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                TextPanel text = (TextPanel) e.getSource();
                Map<TextAttribute, Object> attrs = new HashMap<>(text.getFont().getAttributes());
                attrs.remove(TextAttribute.UNDERLINE);
                text.setFont(new Font(attrs));
                text.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                hideOverlay();
                click(4);
            }
        };

        add(nextRoundButton);
        nextRoundButton.addMouseListener(hoverEffect);
    }

    /**
     * Creates and configures the pause menu with "Play", "Setting", and "Quit" options.
     */
    private void createPauseMenu() {
        titlePanel = new TextPanel("Pause");
        titlePanel.setFont(new Font("MedievalSharp", Font.BOLD, 35));
        titlePanel.setForeground(Color.WHITE);

        playPanel = new TextPanel("Play");
        settingsPanel = new TextPanel("Setting");
        quitPanel = new TextPanel("Quit");

        Font optionFont = new Font("Uncial Antiqua", Font.BOLD, 30);
        playPanel.setFont(optionFont);
        settingsPanel.setFont(optionFont);
        quitPanel.setFont(optionFont);

        playPanel.setForeground(Color.WHITE);
        settingsPanel.setForeground(Color.WHITE);
        quitPanel.setForeground(Color.WHITE);

        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                TextPanel text = (TextPanel) e.getSource();
                Font baseFont = text.getFont();
                Map<TextAttribute, Object> attrs = new HashMap<>(baseFont.getAttributes());
                attrs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                text.setFont(new Font(attrs));
                text.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                TextPanel text = (TextPanel) e.getSource();
                Map<TextAttribute, Object> attrs = new HashMap<>(text.getFont().getAttributes());
                attrs.remove(TextAttribute.UNDERLINE);
                text.setFont(new Font(attrs));
                text.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == quitPanel) {
                    click(3);
                } else if (e.getSource() == playPanel) {
                    click(1);
                } else if (e.getSource() == settingsPanel) {
                    click(2);
                }
            }
        };

        add(titlePanel);
        add(playPanel);
        add(settingsPanel);
        add(quitPanel);

        playPanel.addMouseListener(hoverEffect);
        settingsPanel.addMouseListener(hoverEffect);
        quitPanel.addMouseListener(hoverEffect);
    }

    /**
     * Adjusts the positioning of the pause menu components on screen.
     */
    private void adjustPauseMenuPosition() {
        int y = getHeight() / 4;
        int height = (int) (getHeight() * 0.1);
        int spacing = 20;

        int titleWidth = titlePanel.getText().length() * 20;
        int settingWidth = settingsPanel.getText().length() * 20;
        int quitWidth = quitPanel.getText().length() * 20;
        int playWidth = playPanel.getText().length() * 20;

        int centerX = getWidth() / 2;

        titlePanel.setBounds(centerX - titleWidth / 2, y, titleWidth, height);
        playPanel.setBounds(centerX - playWidth / 2, y + height + spacing, playWidth, height);
        settingsPanel.setBounds(centerX - settingWidth / 2, y + 2 * (height + spacing), settingWidth, height);
        quitPanel.setBounds(centerX - quitWidth / 2, y + 3 * (height + spacing), quitWidth, height);
    }

    /**
     * Adjusts the positioning of the "Next round" button.
     */
    private void adjustScoreButtonPosition() {
        if (nextRoundButton == null || scoreBoardPanel == null) return;

        int width = nextRoundButton.getText().length() * 20;
        int y = (int) (scoreBoardPanel.getHeight() * 1.1);
        nextRoundButton.setBounds(getWidth() / 2 - width / 2, y, width, (int) (getHeight() * 0.2));
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        adjustPauseMenuPosition();
        adjustScoreButtonPosition();
    }

    /**
     * Handles a click event and dispatches the value to the appropriate listener.
     *
     * @param value the action code (1=Play, 2=Setting, 3=Quit, 4=Next round)
     */
    private void click(int value) {
        if (value == 4 && onNextRoundClicked != null) {
            onNextRoundClicked.accept(value);
        } else if (onPauseClicked != null) {
            onPauseClicked.accept(value);
        }
    }

    /**
     * Sets the listener for pause menu actions (Play, Setting, Quit).
     *
     * @param listener a consumer handling the action codes (1–3)
     */
    public void setPauseClicked(Consumer<Integer> listener) {
        this.onPauseClicked = listener;
    }

    /**
     * Sets the listener for the "Next round" button.
     *
     * @param listener a consumer handling the action code 4
     */
    public void setNextRoundClicked(Consumer<Integer> listener) {
        this.onNextRoundClicked = listener;
    }

    /**
     * Updates the scoreboard with new player names and scores.
     *
     * @param names  the list of player names
     * @param scores the list of corresponding scores
     */
    protected void update(ArrayList<String> names, ArrayList<Integer> scores) {
        scoreBoardPanel.update(names, scores);
        scoreBoardPanel.repaint();
    }
}
