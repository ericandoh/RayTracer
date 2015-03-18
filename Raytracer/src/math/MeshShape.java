package math;

import java.util.ArrayList;

public class MeshShape extends Shape {
	
	public ArrayList<Triangle> triangles;
	
	private Intersection temp0 = new Intersection();
	
	private String name;
	
	public MeshShape() {
		//make from some .obj representation
		this("Unnamed");
	}
	
	public MeshShape(String name) {
		//with a name
		triangles = new ArrayList<Triangle>();
		this.name = name;
	}
	
	public void addTriangle(Triangle t) {
		triangles.add(t);
		box.update(t);
	}
	public String toString() {
		return name;
	}
	
	@Override
	public Intersection getIntersection(Intersection src, Ray eye) {
		
		//AABB bounding box check here (if wanted)
		if (!box.intersects(eye)) {
			return src.invalidate();
		}
		
		//iterate through triangles, find 
		float minDst = Float.MAX_VALUE;
		float dst = 0.0f;
		src.invalidate();
		
		for (Triangle triangle: triangles) {
			triangle.getIntersection(temp0, eye);
			dst = temp0.getT();
			if (temp0.intersects && dst < minDst && dst >= 0) {
				minDst = dst;
				src.set(temp0);
			}
		}
		return src;
	}
	
	public static void main(String[] args) {
		//add test cases here
	}
	
}
