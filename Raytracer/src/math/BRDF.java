package math;

import raytracer.Light;

public class BRDF {
	public Color ka;
	public Color kd;
	public Color ks;
	
	public float ksp;
	
	public Color kr;
	
	private Vector3 rm = new Vector3();
	
	public BRDF() {
		ka = new Color();
		kd = new Color();
		ks = new Color();
		ksp = 1.0f;
		kr = new Color();
	}
	public BRDF(Color ka, Color kd, Color ks, float ksp, Color kr) {
		this.ka = ka;
		this.kd = kd;
		this.ks = ks;
		this.ksp = ksp;
		this.kr = kr;
	}
	//phong shading algorithm goes here
	public void addShading(Color src, Intersection intersection, Light light, Ray lightRay, Ray viewRay) {
		//handle ka
		src.addProduct(ka, light.color);
		//handle kd
		//norm(l)*norm(n)
		float ln = Vector3.normProd(lightRay.direction, intersection.normal);
		src.addProductScale(kd, light.color, ln);
		//handle ks
		intersection.normal.scale(rm, 
				Vector3.normProd(lightRay.direction, intersection.normal) * 2.0f);
		rm.subtract(rm, lightRay.direction);
		float rv = Vector3.normProd(rm, viewRay.direction);
		rv = (float)Math.pow(rv, ksp);
		src.addProductScale(ks, light.color, rv);
	}
}
