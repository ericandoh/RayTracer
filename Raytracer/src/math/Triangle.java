package math;

public class Triangle extends Shape {
	
	public Point p0;
	public Point p1;
	public Point p2;
	public Vector3 normal;
	
	public Triangle(Point p0, Point p1, Point p2) {
		//three points define a triangle
		this.p0 = new Point();
		this.p1 = new Point();
		this.p2 = new Point();
		normal = new Vector3();
		this.p0.set(p0);
		this.p1.set(p1);
		this.p2.set(p2);
		Vector3 p1p0 = new Vector3();
		Vector3 p2p0 = new Vector3();
		p1.subtract(p1p0, p0);
		p2.subtract(p2p0, p0);
		p1p0.crossProd(this.normal, p2p0);
		this.normal.normalize(this.normal);
	}
	
	@Override
	public Intersection getIntersection(Intersection src, Ray eye) {
		float a = p0.x - p1.x;
		float b = p0.y - p1.y;
		float c = p0.z - p1.z;
		float d = p0.x - p2.x;
		float e = p0.y - p2.y;
		float f = p0.z - p2.z;
		float g = eye.direction.x;
		float h = eye.direction.y;
		float i = eye.direction.z;
		float j = p0.x - eye.point.x;
		float k = p0.y - eye.point.y;
		float l = p0.z - eye.point.z;
		float m = a*(e*i - h*f) + b*(g*f - d*i) + c*(d*h - e*g);
		if(m == 0) { // ray is parallel to the plane containing the triangle
			return src.invalidate();
		}
		float t = -1/m * (f*(a*k-j*b) + e*(j*c - a*l) + d*(b*l - k*c));
		if(t < eye.tmin || t > eye.tmax) { // intersection isn't within the bounds
			return src.invalidate();
		}
		float gamma = (i*(a*k - j*b) + h*(j*c - a*l) + g*(b*l - k*c)) * 1/m;
		if(gamma < 0 || gamma > 1) { // ray doesn't intersect inside the triangle
			return src.invalidate();
		}
		float beta = (j*(e*i - h*f) + k*(g*f - d*i) + l*(d*h - e*g)) * 1/m;
		if(beta < 0 || beta > 1 - gamma) { // ray doesn't intersect inside the triangle
			return src.invalidate();
		}
		else {
			eye.getPointAt(src.intersection, t);
			src.intersects = true;
			src.normal.set(this.normal);
			src.t = t;
			return src;
		}
	}
	
	public static void main(String[] args) {
		Point p0 = new Point(0.0f, 0.0f, 0.0f);
		Point p1 = new Point(1.0f, 0.0f, 0.0f);
		Point p2 = new Point(0.0f, 1.0f, 0.0f);
		Triangle t = new Triangle(p0, p1, p2);
		
		Point start = new Point(0.5f, 0.0f, 0.5f);
		Vector3 dir = new Vector3(0f, 0f, -1f);
		Ray r = new Ray(start, dir);
		System.out.println(t.getIntersection(new Intersection(), r));
	}
}
