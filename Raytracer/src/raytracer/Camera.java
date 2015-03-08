package raytracer;

import math.Plane;
import math.Point;
import math.Ray;

public class Camera {
	
	private float near, far;
	private Point cameraCenter;
	
	private Plane imagePlane;
	
	public Camera() {
		
	}
	
	//x, y: floats 0.0-1.0 describing where on screen
	//src: Ray to set values in
	public Ray generateRay(float x, float y, Ray src) {
		
		//WORK
		
		return src;
	}
}
