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
}
