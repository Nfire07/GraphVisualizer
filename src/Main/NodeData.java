package Main;

public class NodeData {
	int x,y;
	String label;
	
	public NodeData(int x, int y, String label) {
		this.x = x;
		this.y = y;
		this.label = label;
	}
	
	@Override
	public String toString() {
	    return "NodeData{" +
	            "x=" + x +
	            ", y=" + y +
	            ", label='" + label + '\'' +
	            '}';
	}

}
