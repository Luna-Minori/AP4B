package Common.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class LobbyState implements Serializable {
    private final ArrayList<String> name;
    private final ArrayList<Boolean> ready;
    private final ArrayList<Boolean> isHuman;
    private final int maxPlayers;

    public LobbyState(ArrayList<String> name, ArrayList<Boolean> isHuman, ArrayList<Boolean> ready) {
        this.name = name;
        this.maxPlayers = name.size();
        this.ready = ready;
        this.isHuman = isHuman;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public ArrayList<Boolean> isReady() {
        return ready;
    }

    public ArrayList<Boolean> getIsHuman() {
        return isHuman;
    }
}
