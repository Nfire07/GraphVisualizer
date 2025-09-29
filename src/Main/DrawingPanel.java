package Main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;

public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

    private final Rectangle mouseHitbox = new Rectangle(0, 0, 1, 1);
    
    public static Node click1 = null;
    public static Node click2 = null;
    
    public static int clickCounter = 0;
    
    public static Graph graph = new Graph();
    public static ArrayList<Arch> arches = new ArrayList<>();
    
    private Point dragStartPoint = null;
    private final Point offset = new Point(0, 0);
    
    public static boolean pathFinderMode = false;

    public DrawingPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void drawNode(Graphics2D g2d, Node node, Color nodeBackgroundColor, Color nodeForegroundColor) {
        int size = 30;
        int x = node.data.x + offset.x;
        int y = node.data.y + offset.y;

        g2d.setColor(nodeForegroundColor);
        g2d.drawOval(x, y, size, size);

        g2d.setColor(nodeBackgroundColor);
        g2d.fillOval(x + 1, y + 1, size - 1, size - 1);

        g2d.setColor(nodeForegroundColor);
        g2d.drawString(node.data.label, x + 12, y + 20);
    }

    public void drawHitbox(Graphics2D g2d, Rectangle rec) {
        g2d.drawRect(rec.x, rec.y, rec.width, rec.height);
    }

    public void drawArches(Graphics2D g2d, Color archColor, Color textColor) {
        for (Arch arch : arches) {
            int x1 = arch.from.data.x + 15 + offset.x;
            int y1 = arch.from.data.y + 15 + offset.y;
            int x2 = arch.to.data.x + 15 + offset.x;
            int y2 = arch.to.data.y + 15 + offset.y;

            g2d.setColor(archColor);
            g2d.drawLine(x1, y1, x2, y2);

            String lengthStr = arch.length;
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;

            FontMetrics metrics = g2d.getFontMetrics();
            int textWidth = metrics.stringWidth(lengthStr);
            g2d.setColor(textColor);
            g2d.drawString(lengthStr, midX - textWidth / 2, midY - 5);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2d.setColor(Color.decode("#fefefe"));
        drawHitbox(g2d, mouseHitbox);

        drawArches(g2d, Color.decode("#195698"), Color.decode("#fefefe"));

        for (Node node : graph.nodes) {
            drawNode(g2d, node, Color.decode("#10280b"), Color.decode("#fefefe"));
        }

        g2d.setColor(Color.decode("#ff0044"));
        for (Node node : graph.nodes) {
            Rectangle nodeHitbox = new Rectangle(node.data.x + offset.x, node.data.y + offset.y, 30, 30);
            if (nodeHitbox.contains(mouseHitbox.getLocation())) {
                drawHitbox(g2d, nodeHitbox);
            }
        }
        
        g2d.setColor(Color.decode("#fefefe"));
        g2d.setFont(new Font("Arial",Font.BOLD,15));
        g2d.drawString("PathFinderMode="+pathFinderMode,15,20);
    }

    private boolean archExists(Node n1, Node n2) {
        Point p1 = new Point(n1.data.x, n1.data.y);
        Point p2 = new Point(n2.data.x, n2.data.y);
        for (Arch arch : arches) {
        	boolean same = arch.from.equals(n1) && arch.to.equals(n2);
        	boolean reverse = arch.from.equals(n2) && arch.to.equals(n1);
            if (same || reverse)
                return true;
        }
        return false;
    }

    private String blockUserToSelectLength() {
        while (true) {
            String input = JOptionPane.showInputDialog(this, "Insert a length for the arch:");

            if (input == null || input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Length cannot be empty or cancelled. Please try again.");
                continue;
            }
            	

            return input.trim();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            dragStartPoint = e.getPoint();
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            return;
        }

        if (e.getButton() != MouseEvent.BUTTON1)
            return;

        Point clickPoint = new Point(e.getX() - offset.x, e.getY() - offset.y);
        boolean clickedOnNode = false;

        for (Node node : graph.nodes) {
            Rectangle nodeHitbox = new Rectangle(node.data.x, node.data.y, 30, 30);
            if (nodeHitbox.contains(clickPoint)) {
                clickedOnNode = true;

                if (clickCounter == 0) {
                    click1 = node;
                    clickCounter = 1;
                    System.out.println("Selected node 1: " + click1.data.label);
                }	
                else if (clickCounter == 1) {
                    click2 = node;
                    
                    if(pathFinderMode) {
                    	System.out.println(GraphAlgoritms.dijkstra(graph, click1,click2));
                    	click1 = null;
                    	click2 = null;
                    	clickCounter = 0;
                        return;
                    }
                    
                    if (!click1.equals(click2)) {
                        if (!archExists(click1, click2)) {
                            String length = blockUserToSelectLength();
                            if (length != null) {
                                arches.add(new Arch(click1,click2,length));
                                graph.addArch(click1, click2, Integer.parseInt(length));
                                System.out.println("Created arch between " + click1.data.label + " and " + click2.data.label);
                            }
                        } else {
                            System.out.println("Arch already exists between " + click1.data.label + " and " + click2.data.label);
                        }
                    } else {
                        System.out.println("Clicked same node twice, ignoring.");
                    }

                    click1 = null;
                    click2 = null;
                    clickCounter = 0;
                }
                
                break;
            }
        }

        if (!clickedOnNode) {
        	graph.addNode(new Node(new NodeData(clickPoint.x, clickPoint.y, Main.letter + "")));
            Main.letter++;
            System.out.println("Created new node at {" + clickPoint.x + ";" + clickPoint.y + "}");
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            dragStartPoint = null;
            setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0 && dragStartPoint != null) {
            Point currentPoint = e.getPoint();
            int dx = currentPoint.x - dragStartPoint.x;
            int dy = currentPoint.y - dragStartPoint.y;
            offset.translate(dx, dy);
            dragStartPoint = currentPoint;
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseHitbox.setLocation(e.getPoint());
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        repaint();
    }
}
