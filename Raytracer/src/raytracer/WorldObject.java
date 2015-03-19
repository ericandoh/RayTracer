package raytracer;

import math.BRDF;
import math.Color;
import math.Intersection;
import math.Ray;
import math.Shape;
import math.Transformation;
import math.Vector3;

public class WorldObject {

	public Shape shape;
	
	//private BRDF brdf; <-- BRDF needs to get accessed by raytracer to do recursive reflection calls
	public BRDF brdf;
	
	public Transformation transformation;
	
	private Ray tempEye = new Ray();
	
	public WorldObject(Shape shape, BRDF brdf) {
		this.shape = shape;
		this.brdf = brdf;
	}
	
	public void setTransformation(Transformation x) {
		if (x == null)
			return;
		this.transformation = x;
		//tell this transformation to calculate its inverse once and just once
		this.transformation.apply();
	}
	
	public Intersection getIntersection(Intersection src, Ray eye) {
		if (this.transformation != null) {
			//transform eye ray by inverse transform
			this.transformation.applyInverseToDirection(tempEye.direction, eye.direction);
			this.transformation.applyInverseToPoint(tempEye.point, eye.point);
			tempEye.tmin = eye.tmin;
			tempEye.tmax = eye.tmax;
			shape.getIntersection(src, tempEye);
			this.transformation.applyInverseTransposeToDirection(src.normal, src.normal);
			this.transformation.applyToPoint(src.intersection, src.intersection);
			return src;
		}
		else {
			return shape.getIntersection(src, eye);
		}
	}
	public void addAmbient(Color src, Light light) {
		brdf.addAmbient(src, light);
	}
	public void addShading(Color src, Intersection intersection, Light light, Vector3 lightDir, Vector3 viewRayDirection) {
		brdf.addShading(src, intersection, light, lightDir, viewRayDirection);
	}
}
