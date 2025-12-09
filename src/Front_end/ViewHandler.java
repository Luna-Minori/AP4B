package Front_end;

import java.util.ArrayList;

// Cette interface définit CE QUI peut se passer, mais pas COMMENT
public interface ViewHandler {
    void gameStart(ArrayList<String> playerNames, int nbBots);
    void lowestRequested(String playerName);
    void highestRequested(String playerName);
    void onQuitter();
    void onAttaquer(String ennemiId);
}

