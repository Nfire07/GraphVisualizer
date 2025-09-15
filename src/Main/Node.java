package Main;

import java.util.ArrayList;

public class Node {
	NodeData data;
	ArrayList<Node> nodes;
	
	public Node(NodeData data, ArrayList<Node> nodes) {
		this.data = data;
		this.nodes = nodes;
	}	
}
