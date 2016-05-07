package robert.model;

/**
 * Created by student on 2016-05-05.
 */
public enum Neighbourhood {

    MOORE("MOORE"),
    VON_NEUMAN("VON_NEUMAN"),
    PENTAGONAL_LEFT("PENTAGONAL_LEFT"),
    PENTAGONAL_RIGHT("PENTAGONAL_RIGHT"),
    PENTAGONAL_UP("PENTAGONAL_UP"),
    PENTAGONAL_DOWN("PENTAGONAL_DOWN"),
    HEXAGONAL_A("HEXAGONAL_A"),
    HEXAGONAL_B("HEXAGONAL_B");

    private final String text;

    Neighbourhood(final String text) {
        this.text = text;
    }

    public static Neighbourhood fromString(String text) {
        if (text != null) {
            for (Neighbourhood n : Neighbourhood.values()) {
                if (text.equalsIgnoreCase(n.text)) {
                    return n;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return text;
    }
}
