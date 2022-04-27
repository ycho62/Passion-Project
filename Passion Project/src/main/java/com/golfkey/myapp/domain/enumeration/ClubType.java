package com.golfkey.myapp.domain.enumeration;

/**
 * The ClubType enumeration.
 */
public enum ClubType {
    DRIVER("Driver"),
    SIXTYWEDGE("60 Degree Wedge"),
    FIFTYEIGHTWEDGE("58 Degree Wedge"),
    SANDWEDGE("Sand Wedge"),
    PWEDGE("Pitching Wedge"),
    NINEIRON("9 Iron"),
    EIGHTIRON("9 Iron"),
    SEVENIRON("9 Iron"),
    SEVENHYBRID("7 Hybrid"),
    SEVENWOOD("7 Wood"),
    SIXIRON("9 Iron"),
    SIXHYBRID("7 Hybrid"),
    SIXWOOD("6 Wood"),
    FIVEIRON("9 Iron"),
    FIVEHYBRID("7 Hybrid"),
    FIVEWOOD("5 Wood"),
    FOURIRON("9 Iron"),
    THREEIRON("9 Iron"),
    THREEHYBRID("3 Hybrid"),
    THREEWOOD("3 Wood");

    private final String value;

    ClubType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
