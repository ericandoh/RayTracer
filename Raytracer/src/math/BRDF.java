package math;

import raytracer.Light;
import raytracer.PointLight;

public class BRDF {
	
	public static final BRDF RED_DIFFUSE = new BRDF(Color.BLACK, Color.RED, Color.BLACK, 1.0f, Color.BLACK);
	public static final BRDF YELLOW_DIFFUSE = new BRDF(Color.BLACK, Color.YELLOW, Color.BLACK, 1.0f, Color.BLACK);
	public static final BRDF BLUE_DIFFUSE = new BRDF(Color.BLACK, Color.BLUE, Color.BLACK, 1.0f, Color.BLACK);
	
	public static final BRDF BRDF_SPECIAL_ONE = new BRDF(Color.BLACK, Color.BLUE, Color.WHITE, 30.0f, Color.BLACK);
	public static final BRDF BRDF_SPECIAL_TWO = new BRDF(Color.BLACK, Color.YELLOW, new Color(0.5f, 0.5f, 0.4f), 10.0f, Color.WHITE);
	public static final BRDF BRDF_SPECIAL_THREE = new BRDF(new Color(0.1f, 0.1f, 0.1f), Color.YELLOW, new Color(0.5f, 0.5f, 0.4f), 10.0f, new Color(0.5f, 0.5f, 0.5f));
	
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
	public void addShading(Color src, Intersection intersection, Light light, Vector3 lightDir, Ray viewRay) {
		//handle ka
		src.addProduct(ka, light.color);
		//handle kd
		//norm(l)*norm(n)
		float ln = Vector3.normProd(lightDir, intersection.normal);
		ln = Math.max(0.0f, ln);
		src.addProductScale(kd, light.color, ln);
		//handle ks
		intersection.normal.scale(rm, 
				Vector3.normProd(lightDir, intersection.normal) * 2.0f);
		rm.subtract(rm, lightDir);
		float rv = -1.0f * Vector3.normProd(rm, viewRay.direction); //viewRay points from pt to eye, not from eye to pt
		rv = Math.max(0.0f, rv);
		rv = (float)Math.pow(rv, ksp);
		src.addProductScale(ks, light.color, rv);
	}
	
	public static void main(String[] args) {
		//testing Phong Shading calculations...
		
		Color red = new Color(1.0f, 0.0f, 0.0f);
		
		Color result = new Color();
		Intersection inter = new Intersection();
		//normal straight up
		inter.normal = new Vector3(0.0f, 1.0f, 0.0f);
		//only the light color used here
		Light light = new PointLight(new Vector3(1.0f, 1.0f, 1.0f), Color.WHITE);
		//light ray is from surface => light
		//light ray straight up in y direction
		Vector3 lightRay = new Vector3(0.0f, 1.0f, 0.0f);
		//view ray from surface => viewpoint
		Ray viewRay = new Ray(new Point(0.0f, 0.0f, 0.0f), new Vector3(0.0f, 1.0f, 0.0f));
		
		//----------AMBIENT TEST----------------
		BRDF model = new BRDF(red, Color.BLACK, Color.BLACK, 1.0f, Color.BLACK);
		result.setBlack();
		model.addShading(result, inter, light, lightRay, viewRay);
		System.out.println("Pure Ambient: " + result);
		//----------DIFFUSE TEST----------------
		//red light straight up
		model = new BRDF(Color.BLACK, red, Color.BLACK, 1.0f, Color.BLACK);
		result.setBlack();
		model.addShading(result, inter, light, lightRay, viewRay);
		System.out.println("Diffuse, Straight: " + result);
		//red light shined at an angle
		lightRay = new Vector3(1.0f, 1.0f, 0.0f);
		result.setBlack();
		model.addShading(result, inter, light, lightRay, viewRay);
		System.out.println("Diffuse, Tilted Light: " + result);
		//red light shined at a sharper angle
		lightRay = new Vector3(8.0f, 1.0f, 0.0f);
		result.setBlack();
		model.addShading(result, inter, light, lightRay, viewRay);
		System.out.println("Diffuse, Tilted Light Extreme: " + result);
		//colored light on colored object
		light = new PointLight(new Vector3(1.0f, 1.0f, 1.0f), new Color(0.5f, 0.4f, 0.3f));
		lightRay = new Vector3(1.0f, 1.0f, 0.0f);
		model = new BRDF(new Color(0.5f, 0.6f, 0.7f), Color.BLACK, Color.BLACK, 1.0f, Color.BLACK);
		result.setBlack();
		model.addShading(result, inter, light, lightRay, viewRay);
		System.out.println("Diffuse, Tilted Colored on Colored: " + result);
		
	}
}
