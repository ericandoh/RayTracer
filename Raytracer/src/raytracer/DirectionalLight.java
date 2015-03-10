package raytracer;
import math.Color;
import math.Point;
import math.Ray;
import math.Vector3;

public class DirectionalLight extends Light {
	
	protected Vector3 pos;
	
	public DirectionalLight(Vector3 pos, Color color) {
		super(color);
		this.pos = pos;
	}
	
	@Override
	public void generateLightRay(Ray src, Color c, Point pos){
		src.point.set(pos);
		src.direction.set((Vector3)pos);
		c.set(color);
	}
}