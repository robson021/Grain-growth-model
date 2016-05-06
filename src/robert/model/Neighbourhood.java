package robert.model;

/**
 * Created by student on 2016-05-05.
 */
public enum Neighbourhood {

    MOORE("MOORE"),
    VON_NEUMAN("VON_NEUMAN"),
    PENTAGONAL("PENTAGONAL"),
    HEXAGONAL("HEXAGONAL"),
    LEFT("LEFT"),
    RIGHT("RIGHT"),
    UP("UP"),
    DOWN("DOWN");

    private final String text;

    Neighbourhood(final String text) {
        this.text = text;
    }

    public static Neighbourhood fromString(String text) {
        if (text != null) {
            for (Neighbourhood b : Neighbourhood.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
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
