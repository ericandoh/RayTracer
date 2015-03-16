package math;

public class Color {
	
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);
	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f);
	
	public static final Color RED = new Color(0.5f, 0.0f, 0.0f);
	public static final Color YELLOW = new Color(0.5f, 0.5f, 0.0f);
	public static final Color BLUE = new Color(0.0f, 0.0f, 0.5f);
	public static final Color GREEN = new Color(0.0f, 0.5f, 0.0f);
	
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f);
	
	
	
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
	public void set(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public float dot(Color other) {
		return this.r * other.r + this.g * other.g + this.b * other.b;
	}
	
	public void validate() {
		if (this.r < 0)
			this.r = 0;
		if (this.r > 1.0f)
			this.r = 1.0f;
		if (this.g < 0)
			this.g = 0;
		if (this.g > 1.0f)
			this.g = 1.0f;
		if (this.b < 0)
			this.b = 0;
		if (this.b > 1.0f)
			this.b = 1.0f;
		
	}
	
	@Override
	public String toString() {
		return "(" + this.r + "," + this.g + "," + this.b + ")";
	}
}
