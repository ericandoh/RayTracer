package raytracer;

import java.util.ArrayList;

import math.Color;
import math.Ellipsoid;
import math.Intersection;
import math.Ray;
import math.Shape;
import math.Vector3;

/*
 * Contains all the objects in the world
 */

public class World {
	private ArrayList<WorldObject> shapes;
	private ArrayList<Light> lights;
	
	private Intersection temp0 = new Intersection();
	private Intersection temp1 = new Intersection();
	
	public World() {
		shapes = new ArrayList<WorldObject>();
		lights = new ArrayList<Light>();
	}
	public void addShape(Shape shape, Vector3 pos) {
		shapes.add(new WorldObject(shape, pos));
	}
	public void addLight(Light l) {
		lights.add(l);
	}
	public void defaultScene() {
		this.addShape(new Ellipsoid(), new Vector3(0, 0, 0));
		
		this.addLight(new PointLight(new Vector3(5, 5, 5), Color.WHITE));
	}
	
	public WorldObject getIntersectingObject(Intersection src, Ray eye) {
		
		float minDst = Float.MAX_VALUE;
		float dst = 0.0f;
		WorldObject minShape = null;
		temp1.invalidate();
		
		for (WorldObject shp: shapes) {
			shp.getIntersection(temp0, eye);
			dst = temp0.getT();
			if (temp0.intersects && dst < minDst && dst >= 0) {
				minDst = dst;
				minShape = shp;
				temp1.set(temp0);
			}
		}
		src.set(temp1);
		return minShape;
	}
	//used by raytracer
	public ArrayList<Light> getLights() {
		return lights;
	}
}
