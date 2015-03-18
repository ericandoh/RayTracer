package raytracer;

import math.Color;
import math.Point;
import math.Ray;
import math.Transformation;

public class Light {
	
	public Color color;
	
	public Light(Color color) {
		this.color = color;
	}
	
	public void setTransformation(Transformation x) {
		if (x == null)
			return;
		//tell this transformation to calculate its inverse once and just once
		x.apply();
	}
	
	public void generateLightRay(Ray src, Color c, Point pos) {
		//set src, c
	}
	
	public boolean isAmbient() {
		return false;
	}
}
