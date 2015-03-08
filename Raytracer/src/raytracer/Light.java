package raytracer;

import math.Color;
import math.Point;
import math.Ray;

public class Light {
	
	protected Color color;
	
	public Light(Color color) {
		this.color = color;
	}
	public void generateLightRay(Ray src, Color c, Point pos) {
		//set src, c
	}
}
