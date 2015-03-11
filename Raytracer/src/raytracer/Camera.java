package raytracer;

import math.Plane;
import math.Point;
import math.Ray;
import math.Vector3;

public class Camera {
	
	private float near, far; // <-- look at this
	private Point cameraCenter;
	private float distanceToImagePlane;
	private Plane imagePlane;
	
	public Camera() {
		cameraCenter = new Point(0.0f, 0.0f, 5.0f);
		imagePlane = new Plane(new Point(-1, 1, 4), new Point(-1, -1, 4), new Point(1, 1, 4), new Point(1, -1, 4));
	}
	
	public void displace(Vector3 step) {
		cameraCenter.add(cameraCenter, step);
	}
	
	//x, y: floats 0.0-1.0 describing where on screen
	//src: Ray to set values in
	public Ray generateRay(Ray src, float x, float y) {
		Point pt = imagePlane.interpolate(x, y);
		Vector3 dir = new Vector3();
		pt.subtract(dir, cameraCenter);
		src.point = cameraCenter;
		src.direction = dir;
		//probably need to set tvalues such that tmin gets you near and tmax gets you far
		return src;
	}
	
	public static void main(String[]args) {
		Camera test = new Camera();
		Ray ray = new Ray();
		test.generateRay(ray, 0.5f, 0.5f);
		System.out.println(ray.point.x);
		System.out.println(ray.point.y);
		System.out.println(ray.point.z);
		System.out.println(ray.direction.x);
		System.out.println(ray.direction.y);
		System.out.println(ray.direction.z);
	}
}
