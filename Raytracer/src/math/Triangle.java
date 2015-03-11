package math;

public class Triangle extends Shape {
	
	
	public Triangle(Point p0, Point p1, Point p2) {
		//three points define a triangle
	}
	
	@Override
	public Intersection getIntersection(Intersection src, Ray eye) {
		//this needs work
		return src.invalidate();
	}
	
	public static void main(String[] args) {
		//add test cases here
	}
}
