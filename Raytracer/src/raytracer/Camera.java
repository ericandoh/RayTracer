package raytracer;

import math.Plane;
import math.Point;
import math.Ray;
import math.Vector3;

public class Camera {
	
	private Point cameraCenter;
	private Plane imagePlane;
	
	public Camera() {
		cameraCenter = new Point(-3.5f, 7.0f, 3.5f);
		//cameraCenter = new Point(-3.5f, 2.0f, 3.5f);
		imagePlane = new Plane();
		
		//lookAt(Point.ORIGIN);
		lookAt(new Point(0, 2, 0));
	}
	
	public void displace(Vector3 step) {
		cameraCenter.add(cameraCenter, step);
		//imagePlane.ll.add(imagePlane.ll, step);
		//imagePlane.lr.add(imagePlane.lr, step);
		//imagePlane.ul.add(imagePlane.ul, step);
		//imagePlane.ur.add(imagePlane.ur, step);
		lookAt(Point.ORIGIN);
	}
	
	public void setCenter(Point p) {
		cameraCenter.set(p);
	}
	public void lookAt(Point c) {
		Vector3 lookDir = new Vector3();
		c.subtract(lookDir, cameraCenter);
		lookDir.normalize(lookDir);
		Point imagePlaneCenter = new Point();
		cameraCenter.add(imagePlaneCenter, lookDir);
		
		//find vector perp to lookDir
		Vector3 sideVector0 = new Vector3(-lookDir.z, 0.0f, lookDir.x);
		sideVector0.normalize(sideVector0);
		Vector3 sideVector1 = new Vector3();
		sideVector0.crossProd(sideVector1, lookDir);
		sideVector1.normalize(sideVector1);
		
		imagePlane.ur.set(imagePlaneCenter);
		imagePlane.ur.add(imagePlane.ur, sideVector0);
		imagePlane.ur.add(imagePlane.ur, sideVector1);
		
		imagePlane.lr.set(imagePlaneCenter);
		imagePlane.lr.add(imagePlane.lr, sideVector0);
		imagePlane.lr.subtract(imagePlane.lr, sideVector1);
		
		imagePlane.ul.set(imagePlaneCenter);
		imagePlane.ul.subtract(imagePlane.ul, sideVector0);
		imagePlane.ul.add(imagePlane.ul, sideVector1);
		
		imagePlane.ll.set(imagePlaneCenter);
		imagePlane.ll.subtract(imagePlane.ll, sideVector0);
		imagePlane.ll.subtract(imagePlane.ll, sideVector1);
	}
	
	public void setImagePlaneCorners(Point ll, Point lr, Point ul, Point ur) {
		imagePlane.ll.set(ll);
		imagePlane.lr.set(lr);
		imagePlane.ul.set(ul);
		imagePlane.ur.set(ur);
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
