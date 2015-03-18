package raytracer;

import math.Color;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector3;

public class PointLight extends Light {
	
	protected Point pos;
	
	public PointLight(Point pos, Color color) {
		super(color);
		this.pos = pos;
	}
	
	@Override
	public void setTransformation(Transformation x) {
		super.setTransformation(x);
		if (x == null)
			return;
		x.applyToPoint(this.pos, this.pos);
	}
	
	@Override
	public void generateLightRay(Ray src, Color c, Point pos){
		src.point.set(pos);
		this.pos.subtract(src.direction, (Vector3)(pos));
		c.set(color);
	}
}
