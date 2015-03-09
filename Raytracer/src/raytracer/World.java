package raytracer;

import java.util.ArrayList;

import math.BRDF;
import math.Color;
import math.Ellipsoid;
import math.Intersection;
import math.Point;
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
	public void addShape(Shape shape, BRDF bird) {
		shapes.add(new WorldObject(shape, bird));
	}
	public void addLight(Light l) {
		lights.add(l);
	}
	public void defaultScene() {
		this.addShape(new Ellipsoid(), BRDF.RED_DIFFUSE);
		this.addShape(new Ellipsoid(new Point(1, 0.3f, 2)), BRDF.YELLOW_DIFFUSE);
		this.addShape(new Ellipsoid(new Point(-1, -0.4f, 3)), BRDF.BLUE_DIFFUSE);
		
		this.addLight(new PointLight(new Vector3(5, 5, 5), Color.WHITE));
	}
	
	public WorldObject getIntersectingObject(Intersection src, Ray eye, WorldObject exclude) {
		
		float minDst = Float.MAX_VALUE;
		float dst = 0.0f;
		WorldObject minShape = null;
		temp1.invalidate();
		
		for (WorldObject shp: shapes) {
			if (shp == exclude)
				continue;
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
