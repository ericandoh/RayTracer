package math;

public class Point extends Vector3 {
	
	public static final Point ORIGIN = new Point(0, 0, 0);
	
	public Point(float x, float y, float z) {
		super(x, y, z);
	}
	public Point() {
		super();
	}
	public Vector3 subtract(Vector3 src, Point b) {
		return super.subtract(src, b);
	}
	public Point set(Point p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
		return this;
	}
}