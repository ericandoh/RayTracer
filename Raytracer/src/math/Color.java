package math;

public class Color extends Vector3{
	
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);
	
	public Color(float r, float g, float b) {
		super(r, g, b);
	}
	public Color() {
		super();
	}
	
	public void setBlack() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}
}
