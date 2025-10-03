package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphAlgoritms {	
	
	public static String dijkstra(Graph graph, Node start, Node end) {
	    if (start == null || end == null) return "";

	    HashMap<Node, Properties> table = new HashMap<>();

	    for (Node node : graph.nodes) {
	        table.put(node, new Properties(null, Integer.MAX_VALUE, false));
	    }

	    table.get(start).len = 0;

	    while (!allViewed(table)) {
	        Node nonVisited = getNonVisitedNode(table);
	        if (nonVisited == null) break;

	        Properties nonVisitedProperties = table.get(nonVisited);

	        for (Adiacent adiacent : nonVisited.links) {
	            int newDistance = nonVisitedProperties.len + adiacent.len;

	            if (newDistance < table.get(adiacent.node).len) {
	            	table.get(adiacent.node).len = newDistance;
	            	table.get(adiacent.node).prev = nonVisited;
	            }
	        }

	        nonVisitedProperties.viewed = true;
	    }

	    StringBuilder path = new StringBuilder();
	    Node current = end;

	    if (table.get(current).prev == null && current != start) {
	        return "No path found.";
	    }

	    if (table.get(current).len == Integer.MAX_VALUE) {
	        return "No path found from " + start.data.label + " to " + end.data.label;
	    }

	    ArrayList<String> labels = new ArrayList<>();
	    while (current != null) {
	        labels.add(0, current.data.label); 
	        current = table.get(current).prev;
	    }

	    path.append("Shortest path from ")
	        .append(start.data.label)
	        .append(" to ")
	        .append(end.data.label)
	        .append(": ");

	    path.append(String.join(" -> ", labels));
	    path.append("\nTotal distance: ").append(table.get(end).len);

	    return path.toString();
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
	
	public static boolean allViewed(HashMap<Node,Properties> table) {
		for(Map.Entry<Node,Properties> entry : table.entrySet()) {
			if(!entry.getValue().viewed) {
				return false;
			}
		}
		return true;
	}

}
