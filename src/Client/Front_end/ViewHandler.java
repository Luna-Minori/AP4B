package Client.Front_end;

public interface ViewHandler {

    // Menu
    void startGame();
    void onQuitter();

    // Lobby
    void Ready(String name);

    // Jeu
    void lowestRequested(int playerId);
    void highestRequested(int playerId);
    void middleCardRequested();
}

