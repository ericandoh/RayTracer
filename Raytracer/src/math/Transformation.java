package math;

public class Transformation {
	public Matrix transform;
	public Matrix inverse;
	public Transformation() {
		transform = new Matrix();
		inverse = new Matrix();
	}
	public Transformation(Matrix transform, Matrix inverse) {
		this.transform = transform;
		this.inverse = inverse;
	}
	
	public void translate(float tx, float ty, float tz) {
		
	}
	public void rotate(float rx, float ry, float rz) {
		
	}
	public void scale(float sx, float sy, float sz) {
		
	}
	public void reset() {
		
	}
}
