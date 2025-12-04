package org.servlet.ex.visitrecord.domain;


import lombok.Getter;

@Getter
public enum VisitPriority {
    NO_PRIORITY(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    URGENT(4);

    private final int value;

    VisitPriority(int value) { this.value = value; }

    public static VisitPriority fromValue(int v) {
        for (VisitPriority p : values()) {
            if (p.value == v) return p;
        }
        throw new IllegalArgumentException("Unknown priority value: " + v);
    }
}
