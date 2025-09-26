package Main;

import java.util.HashMap;
import java.util.Map;

public class GraphAlgoritms {	
	
	public static void dijkstra(Graph graph,Node start) {
		// create table for required data
		HashMap<Node,Properties> table = new HashMap<>();
		
		for(Node node : graph.nodes) {
			table.put(node,new Properties(null,Integer.MAX_VALUE, false));
		}
		
		// the starting node is set to 0 in distance
		table.get(start).len = 0;
		
		
		// cicle for each node adiacent to k and assign the distance to adiacent nodes
		
		
	}
	
	public static Node getNonVisitedNode(HashMap<Node,Properties> table) {
		int min = Integer.MAX_VALUE;
		Properties nonVisitedProp = null;
		
		for(Properties prop : table.values()) {
			if(prop.len<min && !prop.viewed) {
				min = prop.len;
				nonVisitedProp = prop;
			}
		}
		
		
        for (Map.Entry<Node, Properties> entry : table.entrySet()) {
            if (entry.getValue().equals(nonVisitedProp)) {
                return entry.getKey();  
            }
        }
        return null;
	}
	
	
	
	
}
