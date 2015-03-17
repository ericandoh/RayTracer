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
		this.pos.normalize(this.pos);
		System.out.println("find point at infinity from where this light comes from");
		
	}
	
	@Override
	public void generateLightRay(Ray src, Color c, Point pos){
		src.point.set(pos);
		src.direction.set(this.pos);
		src.direction.scale(src.direction, -1.0f);
		c.set(color);
	}
}