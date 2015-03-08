package raytracer;

import math.Color;
import math.Vector3;

public class PointLight extends Light {
	
	protected Vector3 pos;
	
	public PointLight(Vector3 pos, Color color) {
		super(color);
		this.pos = pos;
	}
}
