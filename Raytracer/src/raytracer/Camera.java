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
		
	}
	
	//x, y: floats 0.0-1.0 describing where on screen
	//src: Ray to set values in
	public Ray generateRay(Ray src, float x, float y) {
		Point pt = imagePlane.get(x, y);
		Vector3 dir = new Vector3();
		pt.subtract(dir, cameraCenter);
		src.point = cameraCenter;
		src.direction = dir;
		//probably need to set tvalues such that tmin gets you near and tmax gets you far
		return src;
	}
}
