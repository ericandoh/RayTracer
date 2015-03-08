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
}
