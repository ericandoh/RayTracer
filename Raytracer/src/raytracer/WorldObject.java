package raytracer;

import math.BRDF;
import math.Color;
import math.Intersection;
import math.Point;
import math.Ray;
import math.Shape;
import math.Vector3;

public class WorldObject {

	public Shape shape;
	
	//private BRDF brdf; <-- BRDF needs to get accessed by raytracer to do recursive reflection calls
	public BRDF brdf;
	
	public WorldObject(Shape shape, BRDF brdf) {
		this.shape = shape;
		this.brdf = brdf;
	}
	
	public Intersection getIntersection(Intersection src, Ray eye) {
		return shape.getIntersection(src, eye);
	}
	
	public void addShading(Color src, Intersection intersection, Light light, Vector3 lightDir, Ray viewRay) {
		brdf.addShading(src, intersection, light, lightDir, viewRay);
	}
}
