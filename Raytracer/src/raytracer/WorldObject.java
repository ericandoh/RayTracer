package raytracer;

import math.BRDF;
import math.Color;
import math.Intersection;
import math.Point;
import math.Ray;
import math.Shape;
import math.Vector3;

public class WorldObject {

	private Shape shape;
	
	//private BRDF brdf; <-- BRDF needs to get accessed by raytracer to do recursive reflection calls
	public BRDF brdf;
	
	public WorldObject(Shape shape, Vector3 pos) {
		this.shape = shape;
	}
	
	public Intersection getIntersection(Intersection src, Ray eye) {
		return shape.getIntersection(src, eye);
	}
	
	public void addShading(Color src, Intersection intersection, Light light, Ray lightRay, Ray viewRay) {
		brdf.addShading(src, intersection, light, lightRay, viewRay);
	}
}
