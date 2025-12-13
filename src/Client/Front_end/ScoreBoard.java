//package Client.Front_end;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.JTableHeader;
//import java.awt.*;
//import java.util.ArrayList;
//
///**
// * ScoreBoard is a graphical component that displays a table
// * showing player names and their scores. Supports dynamic updates and sorting.
// */
//public class ScoreBoard extends JPanel {
//    private JTable table;
//    private DefaultTableModel model;
//    private ArrayList<String> names;
//    private ArrayList<Integer> scores;
//
//    /**
//     * Constructs a scoreboard with initial scores and names.
//     *
//     * @param scores List of player scores
//     * @param names  List of player names
//     */
//    public ScoreBoard(ArrayList<Integer> scores, ArrayList<String> names) {
//        if (names.size() != scores.size()) {
//            throw new IllegalArgumentException("Names and scores must be the same size");
//        }
//        this.scores = scores;
//        this.names = names;
//        setOpaque(false);
//        setBounds(0, 0, 400, 300);
//        createTable();
//
//        // Create and configure JTable
//        table = new JTable(model);
//        table.setFillsViewportHeight(true);
//        table.getTableHeader().setReorderingAllowed(false);
//        table.getColumnModel().getColumn(1).setPreferredWidth(30);
//
//        JScrollPane scrollPane = new JScrollPane(table);
//        setLayout(new BorderLayout());
//        add(scrollPane, BorderLayout.CENTER);
//
//        // Center text in both columns
//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
//        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
//        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
//
//        // Appearance customization
//        table.setShowGrid(false);
//        table.setIntercellSpacing(new Dimension(0, 0));
//        table.setBackground(new Color(0, 0, 0, 0));
//        table.setOpaque(false);
//        table.setFont(new Font("SansSerif", Font.PLAIN, 24));
//        table.setForeground(Color.WHITE);
//
//        table.setBorder(BorderFactory.createEmptyBorder());
//        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        scrollPane.getViewport().setBorder(null);
//        scrollPane.setOpaque(false);
//        scrollPane.getViewport().setOpaque(false);
//
//        JTableHeader header = getCustomTableHeader();
//        table.setTableHeader(header);
//
//        repaint();
//    }
//
//    /**
//     * Creates and returns a fully transparent custom JTableHeader.
//     *
//     * @return Transparent JTableHeader with centered white text
//     */
//    private JTableHeader getCustomTableHeader() {
//        JTableHeader header = new JTableHeader(table.getColumnModel()) {
//            @Override
//            protected void paintComponent(Graphics g) {
//                Graphics2D g2 = (Graphics2D) g;
//                g2.setComposite(AlphaComposite.Clear);
//                super.paintComponent(g2);
//                g2.setComposite(AlphaComposite.SrcOver);
//            }
//        };
//
//        header.setOpaque(false);
//        header.setBackground(new Color(0, 0, 0, 0));
//        header.setForeground(Color.WHITE);
//
//        header.setDefaultRenderer(new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value,
//                                                           boolean isSelected, boolean hasFocus,
//                                                           int row, int column) {
//                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                label.setOpaque(false);
//                label.setForeground(Color.WHITE);
//                label.setBackground(new Color(0, 0, 0, 0));
//                label.setHorizontalAlignment(SwingConstants.CENTER);
//                return label;
//            }
//        });
//
//        return header;
//    }
//
//    /**
//     * Initializes the JTable model with the initial player names and scores.
//     */
//    private void createTable() {
//        String[] columnNames = {"Players", "Scores"};
//        model = new DefaultTableModel(columnNames, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//
//        for (int i = 0; i < names.size(); i++) {
//            Object[] row = {names.get(i), scores.get(i)};
//            model.addRow(row);
//        }
//    }
//
//    /**
//     * Updates the scoreboard with new player data (order preserved).
//     *
//     * @param newScores List of new scores
//     * @param newNames  List of corresponding player names
//     */
//    public void updateScores(ArrayList<Integer> newScores, ArrayList<String> newNames) {
//        if (newNames.size() != newScores.size()) {
//            throw new IllegalArgumentException("Mismatched name/score list sizes");
//        }
//        model.setRowCount(0);
//        for (int i = 0; i < newNames.size(); i++) {
//            model.addRow(new Object[]{newNames.get(i), newScores.get(i)});
//        }
//    }
//
//    /**
//     * Updates the score of a specific player or adds them if they don't exist.
//     *
//     * @param playerName Player's name
//     * @param score      New score to set
//     */
//    public void updateScore(String playerName, int score) {
//        boolean updated = false;
//        for (int i = 0; i < model.getRowCount(); i++) {
//            if (model.getValueAt(i, 0).equals(playerName)) {
//                model.setValueAt(score, i, 1);
//                updated = true;
//                break;
//            }
//        }
//        if (!updated) {
//            model.addRow(new Object[]{playerName, score});
//        }
//    }
//
//    /**
//     * Clears all entries from the scoreboard.
//     */
//    public void resetScores() {
//        model.setRowCount(0);
//    }
//
//    /**
//     * Updates and sorts the scoreboard by score in ascending order.
//     *
//     * @param names  List of player names
//     * @param scores List of corresponding scores
//     */
//    public void update(ArrayList<String> names, ArrayList<Integer> scores) {
//        if (names.size() != scores.size()) {
//            throw new IllegalArgumentException("Names and scores must have the same size");
//        }
//
//        ArrayList<Integer> indices = new ArrayList<>();
//        for (int i = 0; i < scores.size(); i++) {
//            indices.add(i);
//        }
//
//        indices.sort((i1, i2) -> Integer.compare(scores.get(i1), scores.get(i2)));
//
//        model.setRowCount(0);
//        this.names = new ArrayList<>();
//        this.scores = new ArrayList<>();
//        for (int i : indices) {
//            String name = names.get(i);
//            int score = scores.get(i);
//            this.names.add(name);
//            this.scores.add(score);
//            model.addRow(new Object[]{name, score});
//        }
//    }
//}
