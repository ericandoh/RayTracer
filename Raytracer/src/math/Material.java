package math;

public class Material {
	public BRDF constantBRDF;
	public Material() {
		constantBRDF = new BRDF();
	}
	public Material(BRDF brdf) {
		constantBRDF = brdf;
	}
}