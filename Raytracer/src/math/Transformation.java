package math;

public class Transformation {
	public Matrix transform;
	public Matrix inverse;
	
	private Matrix temp = new Matrix(4);
	private Matrix temp2 = new Matrix(4);
	private Matrix rax = new Matrix(4);
	private Vector3 rCarrot = new Vector3();
	
	public Transformation() {
		transform = new Matrix();
		inverse = new Matrix();
		transform.setIdentity();
	}
	public Transformation(Matrix transform, Matrix inverse) {
		this.transform = transform;
		this.inverse = inverse;
	}
	
	public void translate(float tx, float ty, float tz) {
		temp.setIdentity();
		temp.matrix[0][3] = tx;
		temp.matrix[1][3] = ty;
		temp.matrix[2][3] = tz;
		//transform = transform * temp
		transform.multiply(transform, temp);
	}
	public void rotate(float rx, float ry, float rz) {
		rCarrot.set(rx, ry, rz);
		//theta is in degrees (0-360)
		float theta = (float)(rCarrot.magnitude() / 360  * 2 * Math.PI);
		rCarrot.normalize(rCarrot);
		//rodriguez formula
		//R = ((r rt)+sin(theta)(rx)-cos(theta)(rx)(rx))
		//OR: R = I + (sin0)K+(1-cos0)K^2
		rax.clear();
		rax.matrix[0][1] = -rCarrot.z;
		rax.matrix[0][2] = rCarrot.y;
		rax.matrix[1][0] = rCarrot.z;
		rax.matrix[1][2] = -rCarrot.x;
		rax.matrix[2][0] = -rCarrot.y;
		rax.matrix[2][1] = rCarrot.x;
		//rax.matrix[3][3] = 1;
		
		float sint = (float) Math.sin(theta);
		float oneMinusCost = 1.0f - (float)(Math.cos(theta));
		
		//temp = I
		temp.setIdentity();
		//temp2 = sint*(Rx)
		rax.scale(temp2, sint);
		//temp = I + sint*(Rx)
		temp.add(temp, temp2);
		
		//temp2 = (Rx)(Rx)
		rax.multiply(temp2, rax);
		//temp2 = (1-cost)*(Rx)^2
		temp2.scale(temp2, oneMinusCost);
		//temp = I + sint*(Rx) + (1-cost)*(Rx)^2
		temp.add(temp, temp2);
		
		//transform = transform * temp
		transform.multiply(transform, temp);
	}
	public void scale(float sx, float sy, float sz) {
		temp.setIdentity();
		temp.matrix[0][0] = sx;
		temp.matrix[1][1] = sy;
		temp.matrix[2][2] = sz;
		//transform = transform * temp
		transform.multiply(transform, temp);
	}
	
	//applies the transformation to this point
	public Point applyToPoint(Point src, Point a) {
		return this.transform.multiply(src, a);
	}
	
	public void apply() {
		//finalizes this matrix - by finding the inverse matrix!
		
	}
	public void reset() {
		transform.setIdentity();
	}
	
	public static void main(String[] args) {
		System.out.println("Testing transformations");
		
		Point p = new Point(4, 3, 3);
		Point ans = new Point();
		
		Transformation trans = new Transformation();
		trans.translate(3, 4, 5);
		System.out.println("Translate");
		System.out.println(trans.transform);
		trans.applyToPoint(ans, p);
		System.out.println(ans);
		
		
		trans.reset();
		trans.scale(5, 6, 7);
		System.out.println("Scale");
		System.out.println(trans.transform);
		trans.applyToPoint(ans, p);
		System.out.println(ans);
		
		trans.reset();
		trans.rotate(45, 0, 0);
		System.out.println("Rotate");
		System.out.println(trans.transform);
		trans.applyToPoint(ans, p);
		System.out.println(ans);
	}
}
