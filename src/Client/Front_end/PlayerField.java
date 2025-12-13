package Client.Front_end;

import Common.DTO.PlayerInfo;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;
import java.util.ArrayList;

public class PlayerField extends TilePane {
    private final ArrayList<PlayerPanel> playerPanels = new ArrayList<>();

    public PlayerField(ArrayList<PlayerInfo> players, ViewHandler viewHandler) {
        int i = 0;
        for (PlayerInfo p : players) {
            PlayerPanel pp;
            pp = new PlayerPanel(p, viewHandler);
            pp.getStyleClass().add("playerPanel");
            playerPanels.add(pp);
            getChildren().add(pp);
            i++;
        }

        widthProperty().addListener((obs, oldVal, newVal) -> layoutPlayers());
        heightProperty().addListener((obs, oldVal, newVal) -> layoutPlayers());
    }

    private void layoutPlayers() {
        int totalPlayers = playerPanels.size();
        int w = (int) getWidth();
        int h = (int) getHeight();
        setTileAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        if (totalPlayers <= 2) { // Setup lA grille
            setPrefColumns(2);
            setPrefRows(1);
        } else if (totalPlayers <= 4) {
            setPrefColumns(2);
            setPrefRows(2);
        } else {
            setPrefColumns(3);
            setPrefRows(2);
        }
        int cols = getPrefColumns();
        int rows = getPrefRows();

        double marginX = w * 0.02;
        double marginY = h * 0.02;
        setHgap(marginX); // marge hori
        setVgap(marginY); // verti

        double totalWidth  = w  - (cols + 1) * marginX;
        double totalHeight = h - (rows + 1) * marginY;
        double cellWidth  = totalWidth / cols; // taille de l'interface d'un joueur
        double cellHeight = totalHeight / rows;

        setPrefTileWidth(cellWidth);
        setPrefTileHeight(cellHeight);

        for (PlayerPanel panel : playerPanels) {
            panel.setPrefWidth(cellWidth);
            panel.setPrefHeight(cellHeight);
            panel.setMaxWidth(cellWidth);
            panel.setMaxHeight(cellHeight);
        }
    }

    public void show() {
        this.setVisible(true);
    }
}
