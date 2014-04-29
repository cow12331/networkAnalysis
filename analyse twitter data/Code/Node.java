
public class Node {
	private String id;
	private double x;
	private double y;
	
	Node(String id) {
		this.id = id;
	}
	
	Node(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	String getId() {
		return id;
	}
	
	double getX() {
		return x;
	}
	
	void setX(double i) {
		this.x = i;
	}
	
	double getY() {
		return y;
	}
	
	void setY(double i) {
		this.y = i;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
