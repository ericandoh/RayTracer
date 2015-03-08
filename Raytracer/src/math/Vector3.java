package math;

public class Vector3 {
	public float x, y, z;
	//kek
	
	public Vector3(boolean isRandom) {
		if (isRandom) {
			x = (float) Math.random();
			y = (float) Math.random();
			z = (float) Math.random();
		}
	}
}
