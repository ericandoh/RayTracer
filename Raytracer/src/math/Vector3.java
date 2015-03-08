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
}
