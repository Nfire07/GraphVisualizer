package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel implements MouseListener {
    boolean isNode = false;
    Rectangle mouseHitbox = new Rectangle(0, 0, 4, 4);
    boolean encountered1 = false;
    boolean encountered2 = false;
    NodeData click1 = null;
    NodeData click2 = null;
    ArrayList<Point[]> arches = new ArrayList<Point[]>();

    public DrawingPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseHitbox.setLocation(e.getX(), e.getY());
                repaint();
            }
        });
    }

    public static void drawNode(Graphics2D g2d, Node node) {
        g2d.drawOval(node.data.x, node.data.y, 30, 30);
        g2d.drawString(node.data.label, node.data.x + 10, node.data.y + 20);
    }

    public static void drawHitbox(Graphics2D g2d, Rectangle rec) {
        g2d.drawRect(rec.x, rec.y, rec.width, rec.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Point[] arch : arches) {
            g2d.setColor(Color.decode("#fefefe"));
            // from left to right
            if(arch[0].x < arch[1].x)
            	g2d.drawLine(arch[0].x+30, arch[0].y+15, arch[1].x, arch[1].y+15);
            // from right to left
            else
            	g2d.drawLine(arch[1].x+30,arch[1].y+15,arch[0].x,arch[0].y+15);
        }

        for (Node node : Main.nodes) {
            g2d.setColor(Color.decode("#fefefe"));
            drawNode(g2d, node);
        }

        drawHitbox(g2d, mouseHitbox);
        for (Node node : Main.nodes) {
            Rectangle nodeHitbox = new Rectangle(node.data.x, node.data.y, 30, 30);
            if (mouseHitbox.intersects(nodeHitbox)) {
                g2d.setColor(Color.PINK);
                drawHitbox(g2d, nodeHitbox);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                boolean encounteredNode = false;
                for (Node node : Main.nodes) {
                    Rectangle nodeHitbox = new Rectangle(node.data.x, node.data.y, 30, 30);
                    if (mouseHitbox.intersects(nodeHitbox)) {
                        encounteredNode = true;
                        if (!encountered1) {
                            click1 = node.data;
                            encountered1 = true;
                        } else if (!encountered2) {
                            click2 = node.data;
                            encountered2 = true;

                            if (!click1.equals(click2)) {
                                boolean exists = false;
                                for (Point[] arch : arches) {
                                    boolean sameDirection = arch[0].equals(new Point(click1.x, click1.y)) && arch[1].equals(new Point(click2.x, click2.y));
                                    boolean oppositeDirection = arch[0].equals(new Point(click2.x, click2.y)) && arch[1].equals(new Point(click1.x, click1.y));
                                    if (sameDirection || oppositeDirection) {
                                        exists = true;
                                        break;
                                    }
                                }

                                if (!exists) {
                                    Point[] points = new Point[2];
                                    points[0] = new Point(click1.x, click1.y);
                                    points[1] = new Point(click2.x, click2.y);
                                    arches.add(points);
                                    System.out.println("Created arch between " + click1.label + " and " + click2.label);
                                }
                            }
                            else {
                            	click1=null;
                            	click2=null;
                            }

                            encountered1 = false;
                            encountered2 = false;
                        }
                    }
                }

                if (encounteredNode && click1!=null) {
                    System.out.println("Clicked node " + click1.toString());
                } else {
                    Main.nodes.add(new Node(new NodeData(e.getX(), e.getY(), Main.letter + ""), null));
                    Main.letter++;
                    System.out.println("Generated Node to{" + e.getX() + ";" + e.getY() + "}");
                    repaint();
                }
                break;
            case MouseEvent.BUTTON2:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseHitbox.setLocation(e.getX(), e.getY());
        repaint();
    }
    @Override
    public void mouseExited(MouseEvent e) {
        repaint();
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}
