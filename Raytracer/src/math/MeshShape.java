package math;

import java.util.ArrayList;

public class MeshShape extends Shape {
	
	public ArrayList<Triangle> triangles;
	
	public MeshShape() {
		//make from some .obj representation
	}
	
	public MeshShape(String name) {
		//with a name
	}
	
	public void addTriangle(Triangle t) {
		triangles.add(t);
	}
	
	@Override
	public Intersection getIntersection(Intersection src, Ray eye) {
		
		//iterate through triangles, find 
		
		for (Triangle triangle: triangles) {
			//lol
		}
		
		return src.invalidate();
	}
	
	public static void main(String[] args) {
		//add test cases here
	}
	
}
