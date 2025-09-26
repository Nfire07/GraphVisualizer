package Main;

import java.awt.Point;

public class Arch {
    public final Node from;
    public final Node to;
    public final String length;

    public Arch(Node from, Node to, String length) {
        this.from = from;
        this.to = to;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Arch[from=(" + from.data.x + "," + from.data.y + "), to=(" + to.data.x + "," + to.data.y + "), length=" + length + "]";
    }
    
}
