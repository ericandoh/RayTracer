package raytracer;
import math.Color;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector3;

public class DirectionalLight extends Light {
	
	
	protected Vector3 dir;
	
	public DirectionalLight(Vector3 dir, Color color) {
		super(color);
		this.dir = dir;
		this.dir.normalize(this.dir);
	}
	
	@Override
	public void setTransformation(Transformation x) {
		super.setTransformation(x);
		if (x == null)
			return;
		x.applyToDirection(this.dir, this.dir);
		this.dir.normalize(this.dir);
	}
	
	@Override
	public void generateLightRay(Ray src, Color c, Point pos){
		src.point.set(pos);
		src.direction.set(this.dir);
		src.direction.scale(src.direction, -1.0f);
		c.set(color);
	}
}