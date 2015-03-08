package raytracer;

import math.Color;
import math.Ray;
import math.Shape;

public class Raytracer {
	
	public static final int MAX_DEPTH_RENDER = 10;
	
	private World world;
	
	public Raytracer(World world) {
		//do any setup things we need to do - like setting temp variables and stuff
		this.world = world;
	}
	
	public Color trace(Color src, Ray ray, int depth) {
		if (depth >= MAX_DEPTH_RENDER) {
			src.setBlack();
			return src;
		}
		
		Shape hit = world.getIntersectingObject(ray);
		if (hit == null) {
			src.setBlack();
			return src;
		}
		
		
		
		return src;
	}
}
