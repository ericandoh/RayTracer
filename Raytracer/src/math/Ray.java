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
	public Ray(Point p, Vector3 dir) {
		this.point = p;
		this.direction = dir;
		tmin = 0.0f;
		tmax = Float.POSITIVE_INFINITY;
	}
	
	public Ray reflect(Ray src, Vector3 norm, Point pt) {
		src.point.set(pt); //set the original point of the ray to be the point of intersection
		
		Vector3 origDir = new Vector3();
		direction.normalize(origDir);
		float dot = Vector3.normProd(origDir, norm);
		Vector3 temp = new Vector3();
		norm.scale(temp, dot * 2.0f);
		origDir.subtract(src.direction,  temp); // Rr = Ri - 2N(Ri dot N)
		return src;
	}
	public Point getPointAt(Point src, float t) {
		direction.scale(src, t);
		src.add(src, this.point);
		return src;
	}
	
	public static void main(String[] args) {
		Ray alongX = new Ray(new Point(-10.0f, 0.0f, 0.0f), new Vector3(1.0f, 0.0f, 0.0f));
		Point test = new Point();
		
		alongX.getPointAt(test, 0);
		System.out.println(test);
		alongX.getPointAt(test, 1);
		System.out.println(test);
		alongX.getPointAt(test, 3.5f);
		System.out.println(test);
		
		//Vector3 vec = new Vector3(0.0f, 1.0f, 0.0f);
		
		
	}
}
