package math;

public class Ellipsoid extends Shape {

	private Point center;
	private Vector3 dimensions;
	
	public Ellipsoid() {
		this(new Point(0, 0, 0));
	}
	
	public Ellipsoid(Point center) {
		this(center, new Vector3(1, 1, 1));
	}
	public Ellipsoid(Point center, float dim) {
		this.center = center;
		this.dimensions = new Vector3(dim, dim, dim);
	}
	
	public Ellipsoid(Point center, Vector3 dimensions) {
		this.center = center;
		this.dimensions = dimensions;
	}
	
	@Override
	public Intersection getIntersection(Intersection src, Ray eye) {
		
		//no need for AABB - shape is simple enough
		
		//(ray-C)^2=dimensions^2
		//x^2/a^2+y^2/b^2+z^2/c^2 = 1
		//ray is xr=x0+x1t
		//(x0+x1t-xc)^2/a^2+...
		Quadratic.reset();
		
		Quadratic.addTermSquared(eye.direction.x / dimensions.x, (eye.point.x - center.x) / dimensions.x);
		Quadratic.addTermSquared(eye.direction.y / dimensions.y, (eye.point.y - center.y) / dimensions.y);
		Quadratic.addTermSquared(eye.direction.z / dimensions.z, (eye.point.z - center.z) / dimensions.z);
		Quadratic.addTerm(0, 0, -1);
		
		float[] sol = Quadratic.solve();
		if (sol.length == 0) {
			return src.invalidate();
		}
		float ans;
		if (sol[0] < 0) {
			if (sol.length == 2 && sol[1] >= 0) {
				ans = sol[1];
			}
			else {
				return src.invalidate();
			}
		}
		else {
			ans = sol[0];
		}
		eye.getPointAt(src.intersection, ans);
		src.intersects = true;
		src.intersection.subtract(src.normal, this.center);
		src.normal.normalize(src.normal);
		src.t = ans;
		return src;
	}
	
	public static void main(String[] args) {
		
		Intersection result = new Intersection();
		
		//eye ray along x axis
		Ray alongX = new Ray(new Point(-10.0f, 0.0f, 0.0f), new Vector3(1.0f, 0.0f, 0.0f));
		//eye ray from (10, 10, 10) looking at origin
		Ray diagonalRay = new Ray(new Point(10.0f, 10.0f, 10.0f), new Vector3(-1.0f, -1.0f, -1.0f));
		
		//sphere at origin of radius 1
		Ellipsoid originSphere = new Ellipsoid(new Point(0, 0, 0), new Vector3(1, 1, 1));
		//ellipse at origin with dimensions (2, 3, 4)
		Ellipsoid originEllipsoid = new Ellipsoid(new Point(0, 0, 0), new Vector3(2, 3, 4));
		//sphere at (1, 1, 1) of radius 3
		Ellipsoid movedSphere = new Ellipsoid(new Point(1, 1, 1), new Vector3(3, 3, 3));
		//sphere at (3, 0, 3) of radius 2.5
		Ellipsoid movedSphere2 = new Ellipsoid(new Point(3, 0, 3), new Vector3(2.5f, 2.5f, 2.5f));
				
		
		originSphere.getIntersection(result, alongX);
		System.out.println("Sphere along x: " + result);
		
		originSphere.getIntersection(result, diagonalRay);
		System.out.println("Sphere along diagonal: " + result);
		
		originEllipsoid.getIntersection(result, alongX);
		System.out.println("Ellipsoid along x: " + result);
		
		originEllipsoid.getIntersection(result, diagonalRay);
		System.out.println("Ellipsoid along diagonal: " + result);
		
		movedSphere.getIntersection(result, alongX);
		System.out.println("Offset sphere along x: " + result);
		
		movedSphere.getIntersection(result, diagonalRay);
		System.out.println("Offset sphere along diagonal: " + result);
		
		movedSphere2.getIntersection(result, alongX);
		System.out.println("Offset sphere 2 along x: " + result);
		
		movedSphere2.getIntersection(result, diagonalRay);
		System.out.println("Offset sphere 2 along diagonal: " + result);
	}
}
