package robert.model;

/**
 * Created by robert on 07.05.16.
 */
public enum Placement {
    RANDOM("RANDOM"),
    EVENLY("EVENLY"),
    IN_RAY("IN_RAY");

    private final String text;

    Placement(final String text) {
        this.text = text;
    }

    public static Placement fromString(String text) {
        if (text != null) {
            for (Placement n : Placement.values()) {
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
