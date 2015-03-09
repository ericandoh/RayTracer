package math;

public class Vector3 {
	public float x, y, z;
	
	public Vector3() {
		x = 0f;
		y = 0f;
		z = 0f;
	}
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(boolean isRandom) {
		if (isRandom) {
			x = (float) Math.random();
			y = (float) Math.random();
			z = (float) Math.random();
		}
	}
	
	public Vector3 scale(Vector3 src, float scale) {
		src.x = this.x * scale;
		src.y = this.y * scale;
		src.z = this.z * scale;
		return src;
	}
	
	public Vector3 add(Vector3 src, Vector3 second) {
		src.x = x + second.x;
		src.y = y + second.y;
		src.z = z + second.z;
		return src;
	}
	
	public Vector3 subtract(Vector3 src, Vector3 second) {
		src.x = this.x - second.x;
		src.y = this.y - second.y;
		src.z = this.z - second.z;
		return src;
	}
	
	public Vector3 normalize(Vector3 src) {
		float mag = magnitude();
		src.x = this.x / mag;
		src.y = this.y / mag;
		src.z = this.z / mag;
		return src;
	}
	
	public Vector3 set(Vector3 other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		return this;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector3) {
			return this.x == ((Vector3)obj).x 
					&& this.y == ((Vector3)obj).y 
					&& this.z == ((Vector3)obj).z;
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return "(" + Float.toString(this.x) + "," + Float.toString(this.y) + "," + Float.toString(this.z) + ")";
	}
	
	public static float normProd(Vector3 a, Vector3 b) {
		float normA = a.magnitude();
		float normB = b.magnitude();		
		return (a.x*b.x + a.y*b.y + a.z*b.z) / (normA * normB);
	}
	
	public static void main(String[] args) {
		Vector3 a = new Vector3(1, 2, 3);
		Vector3 b = new Vector3(4, 5, 6);
		Vector3 temp = new Vector3();
		
		temp.set(1, 2, 3);
		System.out.println(a + " ?= " + temp + ": " + a.equals(temp));
		System.out.println(b + " ?= " + temp + ": " + b.equals(temp));
		a.add(temp, b);
		System.out.println(a + " + " + b + " = " + temp);
		a.subtract(temp, b);
		System.out.println(a + " - " + b + " = " + temp);
		a.scale(temp, 2.0f);
		System.out.println(a + " * 2.0 = " + temp);
		System.out.println(a + "^ . " + b + "^ = " + Vector3.normProd(a, b));
		System.out.println("Wolfram Alpha says above is 0.97463184...");
		
		a.scale(temp, 2.0f);
	}
}
