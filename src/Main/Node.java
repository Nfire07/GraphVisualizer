package Main;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	NodeData data;
	HashMap<Node,Arch> links;
	
	public Node(NodeData data, HashMap<Node,Arch> links) {
		this.data = data;
		this.links = links;
	}	
	
}
