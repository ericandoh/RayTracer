package raytracer;

import java.util.ArrayList;

import math.BRDF;
import math.Color;
import math.Intersection;
import math.Point;
import math.Ray;
import math.Shape;
import math.Transformation;
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
	public void addObjects(ArrayList<WorldObject> objs) {
		shapes.addAll(objs);
	}
	public void addShape(Shape shape, BRDF bird, Transformation transform) {
		WorldObject addMe = new WorldObject(shape, bird);
		addMe.setTransformation(transform);
		shapes.add(addMe);
	}
	public void addLight(Light l) {
		lights.add(l);
	}
	public void defaultScene() {
		//this.addShape(new Ellipsoid(), BRDF.RED_DIFFUSE);
		//this.addShape(new Ellipsoid(new Point(1, 0.3f, -1)), BRDF.BRDF_SPECIAL_ONE);
		//this.addShape(new Ellipsoid(new Point(-1, -0.4f, 1)), BRDF.BRDF_SPECIAL_TWO);
		
		//this.addShape(new Ellipsoid(new Point(1, 0, 0)), BRDF.BRDF_SPECIAL_THREE);
		//this.addShape(new Ellipsoid(new Point(-2, 0, 0)), BRDF.BRDF_SPECIAL_THREE);
		//this.addShape(new Triangle(new Point(4, 4, 4), new Point(4, 0, 4), new Point(4, 0, 0)), BRDF.BRDF_SPECIAL_THREE);
		
		/*
		//box
		this.addShape(new Triangle(new Point(1, -1, 1), new Point(-1, -1, 1), new Point(-1, -1, -1)), BRDF.RED_DIFFUSE);
		this.addShape(new Triangle(new Point(-1, 1, -1), new Point(-1, 1, 1), new Point(1, 1, 1)), BRDF.RED_DIFFUSE);
		this.addShape(new Triangle(new Point(1, 1, -1), new Point(1, 1, 1), new Point(1, -1, 1)), BRDF.RED_DIFFUSE);
		
		this.addShape(new Triangle(new Point(1, 1, 1), new Point(-1, 1, 1), new Point(-1, -1, 1)), BRDF.RED_DIFFUSE);
		this.addShape(new Triangle(new Point(-1, -1, 1), new Point(-1, 1, 1), new Point(-1, 1, -1)), BRDF.RED_DIFFUSE);
		this.addShape(new Triangle(new Point(1, -1, -1), new Point(-1, -1, -1), new Point(-1, 1, -1)), BRDF.RED_DIFFUSE);
		
		this.addShape(new Triangle(new Point(1, -1, -1), new Point(1, -1, 1), new Point(-1, -1, -1)), BRDF.RED_DIFFUSE);
		this.addShape(new Triangle(new Point(1, 1, -1), new Point(-1, 1, -1), new Point(1, 1, 1)), BRDF.RED_DIFFUSE);
		this.addShape(new Triangle(new Point(1, -1, -1), new Point(1, 1, -1), new Point(1, -1, 1)), BRDF.RED_DIFFUSE);
		
		this.addShape(new Triangle(new Point(1, -1, 1), new Point(1, 1, 1), new Point(-1, -1, 1)), BRDF.RED_DIFFUSE);
		this.addShape(new Triangle(new Point(-1, -1, -1), new Point(-1, -1, 1), new Point(-1, 1, -1)), BRDF.RED_DIFFUSE);
		this.addShape(new Triangle(new Point(1, 1, -1), new Point(1, -1, -1), new Point(-1, 1, -1)), BRDF.RED_DIFFUSE);
		*/
		
		
		this.addLight(new PointLight(new Point(5, 5, 10), Color.WHITE));
		this.addLight(new PointLight(new Point(-5, -5, -5), Color.BLUE));
		this.addLight(new DirectionalLight(new Vector3(1, -5, -5), Color.WHITE));
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
