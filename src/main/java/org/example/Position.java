package org.example;


public record Position(int x, int y) {


    public boolean equals(Object obj) {
        if (obj instanceof Position diffPositions) {
            return x == diffPositions.x()
                    && y == diffPositions.y();
        } else {
            return false;
        }
    }


    public String toString() {
        return x + "," + y;
    }

    public int hashCode() {
        return (x << 16) + y;
    }
}