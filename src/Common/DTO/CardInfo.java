package Common.DTO;

import java.io.Serializable;

public class CardInfo implements Serializable {
    public Integer value;
    public boolean revealed;

    public CardInfo(Integer value, boolean revealed) {
        this.value = value;
        this.revealed = revealed;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isRevealed() {
        return revealed;
    }
}