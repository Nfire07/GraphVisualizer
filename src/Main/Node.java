package Main;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	NodeData data;
	ArrayList<Adiacent> links;
	
	public Node(NodeData data) {
		this.data = data;
		this.links = new ArrayList<>();
	}	
	
	public void addAdiacent(Node node,int len) {
		links.add(new Adiacent(node,len));
	}
	
	
	
	
}
