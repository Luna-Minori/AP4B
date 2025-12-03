package Front_end;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class InitGame extends JFrame {

    private final JPanel inputPanel;
    private final JTextField playerCountField;
    private final JTextField botCountField;
    private final ArrayList<JTextField> nameFields = new ArrayList<>();
    private final ArrayList<String> playerNames = new ArrayList<>();
    private BiConsumer<ArrayList<String>, Integer> onValidated;

    public InitGame() {
        setTitle("Initialisation de la partie");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        // Champs pour le nombre de joueurs
        JLabel players = new JLabel("Nombre de joueurs :");
        playerCountField = new JTextField();
        playerCountField.setMaximumSize(new Dimension(200, 30));

        // Champs pour le nombre de bots
        JLabel bots = new JLabel("Nombre de bots :");
        botCountField = new JTextField();
        botCountField.setMaximumSize(new Dimension(200, 30));

        // Bouton pour générer les champs de noms
        JButton generateFieldsButton = new JButton("Créer les champs");
        generateFieldsButton.addActionListener(e -> createPlayerFields());

        // Ajout des composants initiaux
        inputPanel.add(players);
        inputPanel.add(playerCountField);
        inputPanel.add(bots);
        inputPanel.add(botCountField);
        inputPanel.add(generateFieldsButton);

        // Bouton pour valider l'entrée utilisateur
        JButton validateButton = new JButton("Valider");
        validateButton.addActionListener(e -> handleValidation());

        add(inputPanel, BorderLayout.CENTER);
        add(validateButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Crée dynamiquement les champs de nom selon le nombre de joueurs humains.
     */
    private void createPlayerFields() {
        try {
            int humanCount = Integer.parseInt(playerCountField.getText());
            int botCount = Integer.parseInt(botCountField.getText());
            int totalPlayers = humanCount + botCount;

            if (totalPlayers < 2 || totalPlayers > 6) {
                JOptionPane.showMessageDialog(this, "Total de joueurs (humains + bots) doit être entre 2 et 6.");
                return;
            }

            // Supprimer les anciens champs
            inputPanel.removeAll();
            nameFields.clear();

            // Ajouter de nouveau les champs de base
            inputPanel.add(new JLabel("Nombre de joueurs :"));
            inputPanel.add(playerCountField);
            inputPanel.add(new JLabel("Nombre de bots :"));
            inputPanel.add(botCountField);

            JButton generateButton = new JButton("Créer les champs");
            generateButton.addActionListener(e -> createPlayerFields());
            inputPanel.add(generateButton);

            // Créer un champ pour chaque joueur humain
            for (int i = 1; i <= humanCount; i++) {
                JTextField nameField = new JTextField("Joueur " + i);
                nameField.setMaximumSize(new Dimension(200, 30));
                nameFields.add(nameField);
                inputPanel.add(new JLabel("Nom du joueur " + i + " :"));
                inputPanel.add(nameField);
            }

            inputPanel.revalidate();
            inputPanel.repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres valides.");
        }
    }

    /**
     * Récupère les noms des joueurs et appelle le callback si défini.
     */
    private void handleValidation() {
        playerNames.clear();

        for (JTextField field : nameFields) {
            String name = field.getText().trim();
            if (!name.isEmpty()) {
                playerNames.add(name);
            }
        }

        if (playerNames.size() != nameFields.size()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les noms de joueurs.");
            return;
        }

        if (onValidated != null) {
            try {
                int botCount = getBot();
                onValidated.accept(playerNames, botCount);
                onValidated = null;
                this.dispose(); // Ferme la fenêtre
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nombre de bots invalide.");
            }
        }
    }

    public ArrayList<String> getNamesPlayers() {
        return playerNames;
    }

    public int getBot() throws NumberFormatException {
        return Integer.parseInt(botCountField.getText());
    }

    public void setOnValidated(BiConsumer<ArrayList<String>, Integer> callback) {
        onValidated = callback;
    }
}
