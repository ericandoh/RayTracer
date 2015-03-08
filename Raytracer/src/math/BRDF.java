package math;

public class BRDF {
	public Color ka;
	public Color kd;
	public Color ks;
	public float kr;
	public BRDF() {
		ka = new Color();
		kd = new Color();
		ks = new Color();
		kr = 0.0f;
	}
	public BRDF(Color ka, Color kd, Color ks, float kr) {
		this.ka = ka;
		this.kd = kd;
		this.ks = ks;
		this.kr = kr;
	}
}
