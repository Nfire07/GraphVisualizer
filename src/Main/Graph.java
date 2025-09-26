package Main;

import java.util.HashSet;

public class Graph {
	HashSet<Node> nodes;
	
	public Graph() {
		nodes = new HashSet<>();
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	public void addArch(Node n1,Node n2,int dist) {
		n1.addAdiacent(n2, dist);
		n2.addAdiacent(n1, dist);
	}
		
}	
