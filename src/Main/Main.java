package Main;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

public class Main {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static char letter = 'A';
    
    public static void main(String[] args) {
        JFrame window = new JFrame("GraphVisualizer");
        window.setBounds(screenSize.width / 2 - 500, screenSize.height / 2 - 300, 1000, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawingPanel panel = new DrawingPanel();

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    DrawingPanel.graph.nodes.clear();
                    DrawingPanel.arches.clear();
                    Main.letter = 'A';
                    System.out.println("Reset nodes and arches");
                    panel.repaint();
                }
            }
        });

        panel.setBackground(Color.decode("#121212"));
        window.add(panel);
        window.setVisible(true);

        panel.requestFocusInWindow();

        new Timer(1, e -> {
            panel.setSize(window.getContentPane().getSize());
            panel.repaint();
        }).start();
    }
}
