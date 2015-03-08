package math;

public class Ray {
	public Point point;
	public Vector3 direction;
	public float tmin, tmax;
	public Ray() {
		point = new Point(0f, 0f, 0f);
		direction = new Vector3(0f, 0f, 0f);
		tmin = 0f;
		tmax = Float.POSITIVE_INFINITY;
	}
}
