package raytracer;

import java.util.ArrayList;

import math.Color;
import math.Ellipsoid;
import math.Ray;
import math.Shape;
import math.Vector3;

/*
 * Contains all the objects in the world
 */

public class World {
	private ArrayList<WorldObject> shapes;
	private ArrayList<Light> lights;
	
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
	
	public WorldObject getIntersectingObject(Ray eye) {
		
		float minDst = Float.MAX_VALUE;
		float dst = 0.0f;
		WorldObject minShape = null;
		
		for (WorldObject shp: shapes) {
			//dst = shp.getDst(eye)
			if (dst < minDst && dst >= 0) {
				minDst = dst;
				minShape = shp;
			}
		}
		return minShape;
	}
	//used by raytracer
	public ArrayList<Light> getLights() {
		return lights;
	}
}
