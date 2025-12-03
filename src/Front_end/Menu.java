package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 * Menu class representing the main menu of the game.
 * It allows launching the game, accessing settings, or quitting.
 */
public class Menu {

    private InitGame initGame;
    private Settings settings;
    private BackgroundPanel background;
    private int width;
    private int height;
    private BiConsumer<ArrayList<String>, Integer> initGameEndCallback;
    private BiConsumer<ArrayList<String>, Integer> settingsCallback;

    /**
     * Constructor that builds and displays the main menu.
     */
    public Menu() {
        JFrame frame = new JFrame("Lama Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Main Menu");
        frame.setSize(600, 600);

        // Load background image
        ImageIcon bgIcon = new ImageIcon("./src/Front_end/Back.png");
        Image bgImage = bgIcon.getImage();
        background = new BackgroundPanel(bgImage);
        background.setLayout(null);
        frame.setContentPane(background);

        // Settings
        settings = new Settings(); // 1 seule fois
        settings.setBounds(50, 50, 300, 300); // Position/taille adaptées à ton layout
        settings.setVisible(false);
        background.add(settings);

        // Menu panel (semi-transparent)
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(0, 0, 0, 200));
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, FlowLayout.CENTER));
        menuPanel.setOpaque(true);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // "Play" button
        JButton playButton = new JButton("Play");
        configureButton(playButton);
        menuPanel.add(playButton);

        // "Settings" button
        JButton settingsButton = new JButton("Settings");
        configureButton(settingsButton);
        menuPanel.add(settingsButton);

        // "Quit" button
        JButton quitButton = new JButton("Quit");
        configureButton(quitButton);
        menuPanel.add(quitButton);

        // Quit action
        quitButton.addActionListener(e -> System.exit(0));

        // Play action
        playButton.addActionListener(a -> {
            playButton.setEnabled(false);
            frame.dispose(); // Closes current menu
            initGame = new InitGame(); // Launches game setup window
            initGame.setOnValidated((playerNames, nbBots) -> {
                if (initGameEndCallback != null) {
                    initGameEndCallback.accept(playerNames, nbBots);
                    initGameEndCallback = null;
                }
            });
        });

        // Settings action
        settingsButton.addActionListener(e -> {
            settings.settingsValidated((w, h) -> {
                width = w;
                height = h;
                frame.setSize(width, height);
                // Ici, tu peux retourner au menu si tu veux
                menuPanel.setVisible(true);
                settings.setVisible(false);
                frame.revalidate();
                frame.repaint();
            });
            menuPanel.setVisible(false);
            settings.setVisible(true);
            frame.revalidate();
            frame.repaint();
        });



        // Hover effect on buttons
        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                button.setBackground(new Color(0, 0, 0, 50));
                frame.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                button.setBackground(new Color(0, 0, 0, 0));
                frame.repaint();
            }
        };

        playButton.addMouseListener(hoverEffect);
        settingsButton.addMouseListener(hoverEffect);
        quitButton.addMouseListener(hoverEffect);

        background.add(menuPanel);

        // Make the menu responsive to window resize
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int x = (int) (frame.getWidth() * 0.1);
                int y = 0;
                int width = (int) (frame.getWidth() * 0.3);
                int height = frame.getHeight();

                menuPanel.setBounds(x, y, width, height);

                for (Component component : menuPanel.getComponents()) {
                    if (component instanceof JButton) {
                        component.setFont(new Font("Arial", Font.PLAIN, (int) (height * 0.05)));
                        component.setPreferredSize(new Dimension(width, (int) (height * 0.2)));
                    }
                }

                menuPanel.revalidate();
                menuPanel.repaint();
            }
        });

        // Initial position and size of menu
        int x = (int) (frame.getWidth() * 0.1);
        int y = 0;
        int width = (int) (frame.getWidth() * 0.3);
        int height = frame.getHeight();

        menuPanel.setBounds(x, y, width, height);
        for (Component component : menuPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setFont(new Font("Arial", Font.PLAIN, (int) (height * 0.05)));
                component.setPreferredSize(new Dimension(width, (int) (height * 0.2)));
            }
        }

        menuPanel.revalidate();
        menuPanel.repaint();
        frame.setVisible(true);
    }

    /**
     * Configures a button with standard menu styling.
     * @param button The button to configure.
     */
    private void configureButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    /**
     * Sets a callback that will be called once InitGame is finished.
     * @param callback A BiConsumer that receives player names and bot count.
     */
    public void initGameReady(BiConsumer<ArrayList<String>, Integer> callback) {
        this.initGameEndCallback = callback;
    }
}
