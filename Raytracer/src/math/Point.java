package math;

public class Point extends Vector3 {
	public Point(float x, float y, float z) {
		super(x, y, z);
	}
	public Point() {
		super();
	}
	public Vector3 subtract(Vector3 src, Point b) {
		return super.subtract(src, b);
	}
	public void set(Point p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
}