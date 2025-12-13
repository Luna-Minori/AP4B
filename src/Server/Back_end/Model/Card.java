package Server.Back_end.Model;

import java.util.Comparator;

public class Card implements Comparator<Card> {
    enum Event {
        Convention_du_troll_penchee, // fevrier
        Welcome_week_primtemps, // fin fevrier
        Semaine_de_mars, // en mars
        Crunch_Club, // en mars aussi
        Festivut, // en avril
        F1JJ, // en mai
        FIMU, // en juin
        Welcome_week_automne, // fin aout
        Intégration, // septembre
        Congrés, // octobre
        Gala, // novembre
        Tribut, // décembre
    }
    public Event value;
    public boolean isRevealed = false;
    public Card(Event value) {
        this.value = value;
    }

    public Event getValue() {
        return value;
    }

    public int getIntValue() {
        return value.ordinal() + 1;
    }

    public void reveal() {
        isRevealed = true;
    }

    public void hide() {
        isRevealed = false;
    }
    public boolean isRevealed() {
        return isRevealed;
    }

    @Override
    public int compare(Card c1, Card c2) { return Integer.compare(c1.value.ordinal(), c2.value.ordinal());
    }

    public int compareTo(Card other) {
        return Integer.compare(this.value.ordinal(), other.value.ordinal());
    }

    public String toString() {
        return String.format("La valeur de ta carte est %d ", value.ordinal());
    }
}
