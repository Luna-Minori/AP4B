package Client.Front_end;

public interface ViewHandler {

    // Menu
    void startGame();
    void onQuitter();

    // Lobby
    void startLobby();
    void setName(String name);
    void Ready();
    void addBot();
    void removeBot();
    // Jeu
    void lowestRequested(int playerId);
    void highestRequested(int playerId);
    void middleCardRequested();


}

