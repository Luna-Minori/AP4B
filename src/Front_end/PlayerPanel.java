package Front_end;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;


public class PlayerPanel extends JPanel {
    private HandPanel handPanel; // Le panneau contenant les cartes du joueur
    private TextPanel namePanel;
    private TextPanel pointTextPanel;
    private JButton setHandDown;
    private ArrayList<Integer> hand;
    private String name;
    private Consumer<Integer> CardClick;
    private Consumer<Boolean> onHandDownClicked;
    private int point;

    // Constructeur
    public PlayerPanel(ArrayList<Integer> hand, String name, int point) {
        setLayout(null);
        setOpaque(true);
        this.setBackground((new Color(255, 0, 0, 255))); // semi-transparent white background
        // create Panel for player
        this.hand = hand;
        this.name = name;
        this.point = point;
        createHandPanel();
        TextPanel();
        createHandDown();
        add(handPanel);
        add(namePanel);
        add(pointTextPanel);
        add(setHandDown);
        adjustTextPanelSize();
        adjustHandPanelSize(); // try to be responsive
        adjustHandDown();
    }

    private void adjustHandDown() {
        // Ajuster la taille et la position du HandPanel
        setHandDown.setBounds((int) (getWidth() * 0.05), (int) (getHeight() * 0.2), (int) (getWidth() * 0.2), (int) (getHeight() * 0.1));
        repaint();
    }

    private void createHandDown() {
        setHandDown = new JButton("HandDown");
        ImageIcon icon = new ImageIcon("./src/Front_end/handDown.png");
        setHandDown.setIcon(icon);
        setHandDown.setHorizontalTextPosition(SwingConstants.CENTER);
        setHandDown.setVerticalTextPosition(SwingConstants.CENTER);
        setHandDown.setFocusPainted(false);
        setHandDown.setBorderPainted(false);
        setHandDown.setContentAreaFilled(false);
        setHandDown.setOpaque(false);

        setHandDown.addActionListener(e -> {
            if (onHandDownClicked != null) {
                onHandDownClicked.accept(true);
            }
        });
        add(setHandDown);
    }

    private void adjustTextPanelSize() {
        namePanel.setBounds((int) (getWidth() * 0.05), (int) (getHeight() * 0.7), namePanel.getText().length() * 30, (int) (getHeight() * 0.2));
        pointTextPanel.setBounds((int) (getWidth() * 0.05), (int) (getHeight() * 0.5), pointTextPanel.getText().length() * 15, (int) (getHeight() * 0.2));
    }

    private void createHandPanel() {
        // Créer une liste de chemins d'images pour les cartes
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(hand.get(i), true, true, true);
            int indexCard = i;
            temp.setOnCardClicked(value -> {
                if (CardClick != null) {
                    System.out.println("Player pannel " + value);
                    CardClick.accept(indexCard);
                    //CardClick = null;
                }
            });
            temp.setLayout(null);
            Card.add(temp);
        }

        // Créer le panneau principal de la main de cartes
        handPanel = new HandPanel(Card, true);
    }

    private void TextPanel() {
        // Exemple de TextPanel pour afficher le nombre de tours restants
        namePanel = new TextPanel(name);
        int w = getWidth();
        int h = getHeight();
        float ratioMarginY = 0.05f;
        int margeHeight = (int) (h * ratioMarginY);
        int margeWidth = (int) (w / 2);

        namePanel.setBounds(margeWidth, margeHeight, 300, 50); // Positionner le TextPanel
        namePanel.setFont(new Font("Arial", Font.PLAIN, 34));
        pointTextPanel = new TextPanel("Hand Points : " + point);
        pointTextPanel.setBounds(10, 270, 220, 50);
        pointTextPanel.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    // Méthode pour ajuster la taille du HandPanel pour qu'il occupe un pourcentage de Front_Player
    private void adjustHandPanelSize() {
        float coefHeightHand = 0.8f; // hand of player use x % of the Panel front player
        int marge = (int) (getWidth() * 0.05);

        int xnamePanel = namePanel.getWidth() + marge;
        int xpointTextPanel = pointTextPanel.getWidth() + marge;

        int x;
        if(xnamePanel > xpointTextPanel) {
            x = (int) (xnamePanel *1.05);
        } else {
            x = (int) (xpointTextPanel*1.05);
        }
        int y = (int) Math.ceil(getHeight() - getHeight() * coefHeightHand - getHeight() * 0.15);
        int w = (int) Math.ceil((getWidth() - namePanel.getWidth() * 1.05) * 0.95);
        int h = (int) Math.ceil(getHeight() * coefHeightHand);
        // Ajuster la taille et la position du HandPanel
        handPanel.setBounds(x, y, w, h);
    }

    // Méthode pour redimensionner le Front_Player et ajuster le HandPanel
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        adjustTextPanelSize();
        adjustHandPanelSize();
        adjustHandDown();
    }

    protected void update(ArrayList<Integer> hand, String name, int point) {
        this.name = name;
        this.hand = hand;
        this.point = point;
        updateTextPanel(point);
        updateHandPanel();
        adjustTextPanelSize();
        adjustHandPanelSize();
        adjustHandDown();
    }


    protected void updateHand(ArrayList<Integer> hand) {
        this.hand = hand;
        updateHandPanel();
        adjustHandPanelSize();
    }

    private void updateHandPanel() {
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(hand.get(i), true, true, true);
            temp.setLayout(null);
            Card.add(temp);
            int indexCard = i;
            temp.setOnCardClicked(value -> {
                if (CardClick != null) {
                    CardClick.accept(indexCard);
                }
            });
        }
        handPanel.clear();
        handPanel.update(Card, true);
    }

    private void updateTextPanel(int point) {
        // Exemple de TextPanel pour afficher le nombre de tours restants
        namePanel.setText(name);
        pointTextPanel.setText("Hand points : " + point);
        repaint();
    }

    public void playCardClick(Consumer<Integer> listener) {
        this.CardClick = listener;
    }

    public void updateHandPoint(int point) {
        updateTextPanel(point);
    }

    protected void setHandDownClicked(Consumer<Boolean> listener) {
        this.onHandDownClicked = listener;
    }
}