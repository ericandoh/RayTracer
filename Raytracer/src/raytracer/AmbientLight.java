package raytracer;

import math.Color;

public class AmbientLight extends Light {

	public AmbientLight(Color color) {
		super(color);
	}
	public boolean isAmbient() {
		return true;
	}
}
