package Main;

import java.awt.Point;

public class Arch {
    public final Point from;
    public final Point to;
    public final String length;

    public Arch(Point from, Point to, String length) {
        this.from = from;
        this.to = to;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Arch[from=(" + from.x + "," + from.y + "), to=(" + to.x + "," + to.y + "), length=" + length + "]";
    }
}
