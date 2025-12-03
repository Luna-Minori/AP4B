package Front_end;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Back_end.DTO.PlayerInfo;

public class PlayerField extends JPanel {
    private final ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
    private ArrayList<PlayerInfo> playersInfo;

    public PlayerField(ArrayList<PlayerInfo> players) {
        this.playersInfo = players;
        setLayout(null);
        setOpaque(true);
        setBackground(Color.BLUE);

        for (PlayerInfo p : players) {
            PlayerPanel pp = new PlayerPanel(p.getHand(), p.getName(), p.getPointGame());
            playerPanels.add(pp);
            add(pp);
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();

        int totalPlayers = playerPanels.size();

        for (int i = 0; i < totalPlayers; i++) {
            Rectangle r = calculatePlayerPanelPosition(i, totalPlayers);
            playerPanels.get(i).setBounds(r);
        }
    }

    private Rectangle calculatePlayerPanelPosition(int index, int totalPlayers) {
        int w = getWidth();
        int h = getHeight();
        float ratioMarginX = 0.01f;
        float ratioMarginY = 0.02f;
        int marginX = (int) (w * ratioMarginX);
        int marginY = (int) (h * ratioMarginY);

        int cols, rows;

        if (totalPlayers <= 2) {
            cols = 2;
            rows = 1;
        } else if (totalPlayers <= 4) {
            cols = 2;
            rows = 2;
        } else {
            cols = 3;
            rows = 2;
        }

        int usableWidth  = w - (marginX * (cols + 1));
        int usableHeight = h - (marginY * (rows + 1));

        int cellWidth  = usableWidth / cols;
        int cellHeight = usableHeight / rows;

        int col = index % cols;
        int row = index / cols;

        int x = marginX + col * (cellWidth + marginX);
        int y = marginY + row * (cellHeight + marginY);
        /*
        //int cellWidth = w / cols - marginX;
        //int cellHeight = (h - marginY) / rows;

        int col = index % cols;
        int row = index / cols;
        System.out.println("row: " + row + ", col: " + col);

        int x, y;
        if(col == 0){
            x = col * cellWidth + marginX;
        }
        else if(col == 1){
            x = col * cellWidth + marginX * cols;
        }
        else{
            x = col * cellWidth + marginX * cols;
        }

        if(rows == 1){
            y = row * cellHeight + marginY;
        }
        else{
            y = row * cellHeight + marginY * rows;
        }
*/

        System.out.println("player " + index + " position: (" + x + ", " + y + "), size: (" + cellWidth + ", " + cellHeight + ")");
        return new Rectangle(x, y, cellWidth, cellHeight);
    }

    public void update(ArrayList<PlayerInfo> players) {
        this.playersInfo = players;
        // tu peux aussi mettre à jour les panels existants
        revalidate();
        repaint();
    }
}
