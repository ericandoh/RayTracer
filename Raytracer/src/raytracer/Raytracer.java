package raytracer;

import math.Color;
import math.Intersection;
import math.Ray;
import math.Vector3;

public class Raytracer {
	
	public static final int MAX_DEPTH_RENDER = 10;
	
	private World world;
	
	public Raytracer(World world) {
		//do any setup things we need to do - like setting temp variables and stuff
		this.world = world;
	}
	
	//the main ray tracing algorithm
	public Color trace(Color src, Ray ray, int depth, WorldObject exclude) {
		//do not trace if too many reflective rays
		if (depth >= MAX_DEPTH_RENDER) {
			src.setBlack();
			return src;
		}
		//see which object this ray hits
		Intersection inter = new Intersection();
		WorldObject hit = world.getIntersectingObject(inter, ray, exclude);
		if (!inter.intersects) {
			//no object hit - black space
			//change color here to sky blue if you want sky blue background
			src.setBlack();
			return src;
		}
		Color c = new Color();
		Ray lightRay = new Ray();
		Intersection isBlocked = new Intersection();
		for (Light light: world.getLights()) {
			//make light ray
			light.generateLightRay(lightRay, c, inter.intersection);
			
			//see if light is blocked from light=>this point
			world.getIntersectingObject(isBlocked, lightRay, hit);
			if (!isBlocked.intersects) {
				//not blocked, so do shading calculations for this light ray
				hit.addShading(src, inter, light, lightRay, ray);
			}
		}
		if(hit.brdf.kr.r > 0 && hit.brdf.kr.g > 0 && hit.brdf.kr.b > 0) {
			Ray reflectRay = new Ray();
			Vector3 norm = new Vector3();
			inter.normal.normalize(norm); //create a unit normal vector
			ray.reflect(reflectRay, norm, inter.intersection);
			Color ref = new Color();
			trace(ref, reflectRay, depth + 1, hit);
			src.addProduct(ref, hit.brdf.kr); // <-- not sure if this is right
		}
		
		
		return src;
	}
}
