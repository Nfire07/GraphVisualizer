package Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static ArrayList<Node> nodes = new ArrayList<>();
    public static char letter = 'A';

    public static void main(String[] args) {
        JFrame window = new JFrame("GraphVisualizer");
        window.setBounds(screenSize.width / 2 - 500, screenSize.height / 2 - 300, 1000, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#121212"));

        DrawingPanel drawing = new DrawingPanel();
        drawing.setBackground(Color.decode("#121212"));
        drawing.setBounds(0, 0, window.getWidth(), window.getHeight());

        window.add(drawing);
        window.setVisible(true);

        Timer timer = new Timer(16, e -> drawing.repaint());
        timer.start();
    }
}
