package raytracer;

import math.Shape;
import math.Vector3;

public class WorldObject {

	private Shape shape;
	private Vector3 pos;
	
	public WorldObject(Shape shape, Vector3 pos) {
		this.shape = shape;
		this.pos = pos;
	}
}
