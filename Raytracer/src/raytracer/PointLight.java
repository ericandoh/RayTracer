package raytracer;

import math.Color;
import math.Point;
import math.Ray;
import math.Vector3;

public class PointLight extends Light {
	
	protected Vector3 pos;
	
	public PointLight(Vector3 pos, Color color) {
		super(color);
		this.pos = pos;
	}
	
	@Override
	public void generateLightRay(Ray src, Color c, Point pos){
		src.point.set(pos);
		this.pos.subtract(src.direction, (Vector3)(pos));
		c.set(color);
	}
}
