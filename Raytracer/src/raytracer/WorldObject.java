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
	private Vector3 pos;
	
	private BRDF brdf;
	
	public WorldObject(Shape shape, Vector3 pos) {
		this.shape = shape;
		this.pos = pos;
	}
	
	public Intersection getIntersection(Intersection src, Ray eye) {
		
		if (false) {
			return src.invalidate();
		}
		
		return src;
	}
	
	public void addShading(Color src, Intersection intersection, Light light, Ray lightRay, Ray viewRay) {
		brdf.addShading(src, intersection, light, lightRay, viewRay);
	}
}
