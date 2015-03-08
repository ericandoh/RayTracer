package raytracer;

import java.util.ArrayList;

import math.Ellipsoid;
import math.Ray;
import math.Shape;

/*
 * Contains all the objects in the world
 */

public class World {
	private ArrayList<Shape> shapes;
	
	public World() {
		shapes = new ArrayList<Shape>();
	}
	public void addShape(Shape shape) {
		shapes.add(shape);
	}
	public void defaultScene() {
		shapes.add(new Ellipsoid());
	}
	
	public Shape getIntersectingObject(Ray eye) {
		
		float minDst = Float.MAX_VALUE;
		float dst = 0.0f;
		Shape minShape = null;
		
		for (Shape shp: shapes) {
			//dst = shp.getDst(eye)
			if (dst < minDst && dst >= 0) {
				minDst = dst;
				minShape = shp;
			}
		}
		return minShape;
	}
}
