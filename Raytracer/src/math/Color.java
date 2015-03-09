package math;

public class Color {
	
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);
	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f);
	
	public static final Color RED = new Color(1.0f, 0.0f, 0.0f);
	public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f);
	
	
	public float r, g, b;
	
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public Color() {
		super();
	}
	
	//sets color to black (resets it)
	public void setBlack() {
		this.r = 0.0f;
		this.g = 0.0f;
		this.b = 0.0f;
	}
	
	//adds the product of color a * color b to this color
	public Color addProduct(Color a, Color b) {
		this.r += a.r * b.r;
		this.g += a.g * b.g;
		this.b += a.b * b.b;
		return this;
	}
	public Color addProductScale(Color a, Color b, float c) {
		this.r += (a.r * b.r) * c;
		this.g += (a.g * b.g) * c;
		this.b += (a.b * b.b) * c;
		return this;
	}
	public void set(Color other) {
		this.r = other.r;
		this.g = other.g;
		this.b = other.b;
	}
	@Override
	public String toString() {
		return "(" + this.r + "," + this.g + "," + this.b + ")";
	}
}
