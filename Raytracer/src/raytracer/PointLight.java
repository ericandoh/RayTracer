package raytracer;

import math.Color;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector3;

public class PointLight extends Light {
	
	protected Point pos;
	public int falloff;
	
	public PointLight(Point pos, Color color, int falloff) {
		super(color);
		this.pos = pos;
		this.falloff = falloff;
	}
	
	public PointLight(Point pos, Color color) {
		super(color);
		this.pos = pos;
		this.falloff = 0;
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
