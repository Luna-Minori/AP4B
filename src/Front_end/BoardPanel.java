package Front_end;

import Back_end.DTO.PlayerInfo;
import Back_end.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class BoardPanel {
    private final GameField middleCardField;
    private final Overlay overlay;
    private JFrame frame;
    private final PlayerField playersfield; // Panel representing the player's part
    private JButton settingsButton;
    private Consumer<Integer> onDrawClicked;
    private Consumer<Integer> playCardClick;
    private Settings settings;

    public BoardPanel(GameState info) {
        // Initialize main game window (board)
        frame = new JFrame();
        frame.setTitle("Trio - Board");
        frame.setSize(1920, 1080); // Set board size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window
        //frame.setUndecorated(true);

        ImageIcon bgIcon = new ImageIcon("./src/Front_end/Back.png");
        Image bgImage = bgIcon.getImage();
        BackgroundPanel background = new BackgroundPanel(bgImage);
        background.setLayout(null);

        settings = new Settings(); // 1 seule fois
        settings.setBounds(50, 50, 300, 300); // Position/taille adaptées à ton layout
        settings.setVisible(false);
        background.add(settings);

        frame.setContentPane(background);

        playersfield = new PlayerField(info.getPlayers());
        middleCardField = new GameField(info.getMiddleCards());
        setSettingButton();

        middleCardField.drawClick((Integer value) -> {
            if (onDrawClicked != null) {
                onDrawClicked.accept(value);
            }
        });
        /*
        playersfield.playCardClick((indexCard) -> {
            if (playCardClick != null) {
                playCardClick.accept(indexCard);
            }
        })*/

        overlay = new Overlay(true, frame.getWidth(), frame.getHeight(), info.getPlayers());
        overlay.setVisible(false); // Initially hidden
        overlay.setLayout(null); // Needed for absolute positioning
        overlay.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(overlay);

        overlay.setPauseClicked((value) -> {
            switch (value) {
                case 1:
                    overlay.setVisible(false);
                    playersfield.setVisible(true);
                    middleCardField.setVisible(true);
                    settings.setVisible(false);
                    break;

                case 2:
                    overlay.setVisible(false);
                    playersfield.setVisible(false);
                    middleCardField.setVisible(false);
                    settings.setVisible(true);
                    settings.settingsValidated((w, h) -> {
                        frame.setSize(w, h);
                        overlay.setVisible(true);
                        settings.setVisible(false);
                        frame.revalidate();
                        frame.repaint();
                    });
                    break;
            }
        });

        overlay.setNextRoundClicked((value) -> {
            if (value == 1) {
                overlay.setVisible(false);
            }
        });

        // Add panels to the frame
        //frame.add(settingsButton);
        frame.add(middleCardField);
        frame.add(playersfield);
        frame.repaint();

        // Resize listener to dynamically adjust sizes
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = frame.getWidth();
                int h = frame.getHeight();
                float ratioMarginX = 0.03f; // % de marge horizontale
                int marginWidth = (int) (w * ratioMarginX);
                int sizeOfBoard = w - 2 * marginWidth; // lib nul ca centre pas
                float sizeOfMiddleCardField = 0.3f;

                int middleCardFieldHeight = (int) (h * sizeOfMiddleCardField);
                middleCardField.setBounds(marginWidth,0, sizeOfBoard, middleCardFieldHeight);
                middleCardField.revalidate();
                middleCardField.repaint();

                int playersFieldY = (int) (h * sizeOfMiddleCardField);
                int playerFieldHeight = (int) (h - playersFieldY);
                playersfield.setBounds(marginWidth, playersFieldY, sizeOfBoard, playerFieldHeight);
                playersfield.revalidate();
                playersfield.repaint();

                settingsButton.setBounds(w - 100, 0, 50, 50);
                frame.repaint();
                frame.revalidate();
            }
        });

        frame.setVisible(true);
    }

    /**
     * Sets up the settings button with an icon.
     */
    private void setSettingButton() {
        ImageIcon icon = new ImageIcon("./src/Front_end/Setting.png");
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        settingsButton = new JButton(icon);
        settingsButton.setToolTipText("Settings");
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setOpaque(false);

        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                overlay.flipMode(false); // pause menu
                overlay.setVisible(true);
            }
        });
    }

    /**
     * Sets the callback for when a draw is requested.
     *
     * @param listener The listener to handle the draw request.
     */
    public void setOnDrawRequested(Consumer<Integer> listener) {
        this.onDrawClicked = listener;
    }

    /**
     * Sets the callback for when a card is played.
     *
     * @param listener The listener to handle the card play request.
     */
    public void setOnPlayCardRequested(Consumer<Integer> listener) {
        this.playCardClick = listener;
    }


    public void update(GameState info) {
        updateFrontPlayer(info.getPlayers());
    }


    public void updateFrontPlayer(ArrayList<PlayerInfo> playersInfo) {
        playersfield.update(playersInfo);
    }

    /**
     * Shows the overlay (e.g., pause or game round details).
     */
    public void showOverlay() {
        overlay.showOverlay(this);
    }

    /**
     * Updates the overlay with new game data.
     *
     * @param names The updated names of players.
     * @param score The updated scores of players.
     */
    public void overlayUpdate(ArrayList<String> names, ArrayList<Integer> score) {
        overlay.update(names, score);
    }

    /**
     * Clears the visibility of all components on the board.
     */
    public void dispose() {
        playersfield.setVisible(false);
        middleCardField.setVisible(false);
        settingsButton.setVisible(false);
        frame.dispose();
        System.exit(0);
    }
}