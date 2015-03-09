package math;

/*
 * Defines an arbitrary shape; can have operations done on it
 */

public class Shape {

	public Intersection getIntersection(Intersection src, Ray eye) {
		return src.invalidate();
	}
}
