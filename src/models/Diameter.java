package models;

import java.util.Optional;

public enum Diameter {
    MM200(200),
    MM500(500),
    MM700(700);

    private final int millimeters;

    Diameter(int millimeters) {
        this.millimeters = millimeters;
    }

    public int getMillimeters() {
        return millimeters;
    }

    public static Optional<Diameter> fromMillimeters(int value) {
        for (Diameter diameter : values()) {
            if (diameter.millimeters == value) {
                return Optional.of(diameter);
            }
        }

        return Optional.empty();
    }
}