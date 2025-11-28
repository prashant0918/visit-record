package org.servlet.ex.visitrecord.domain;
public enum VisitPriority {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int value;

    VisitPriority(int value) { this.value = value; }

    public int getValue() { return value; }

    public static VisitPriority fromValue(int v) {
        for (VisitPriority p : values()) if (p.value == v) return p;
        throw new IllegalArgumentException("Unknown priority value: " + v);
    }
}
