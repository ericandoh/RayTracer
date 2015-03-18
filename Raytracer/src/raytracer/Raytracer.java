package raytracer;

import math.Color;
import math.Intersection;
import math.Ray;
import math.Vector3;

public class Raytracer {
	
	public static final int MAX_DEPTH_RENDER = 4;
	
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
		Vector3 normalizedLightDirection = new Vector3();
		Intersection isBlocked = new Intersection();
		for (Light light: world.getLights()) {
			
			if (light.isAmbient()) {
				hit.addAmbient(src, light);
				continue;
			}
			
			//make light ray
			light.generateLightRay(lightRay, c, inter.intersection);
			lightRay.direction.normalize(normalizedLightDirection);
			//see if light is blocked from light=>this point
			world.getIntersectingObject(isBlocked, lightRay, hit);
			if (!isBlocked.intersects) {
				//not blocked, so do shading calculations for this light ray
				//src, intersection (for normal), light (for color), 
				//normalizedLightDir = dir, ray.direction = view ray direction
				hit.addShading(src, inter.normal, light, normalizedLightDirection, ray.direction);
			}
		}
		if(hit.brdf.kr.dot(hit.brdf.kr) > 0) {
			Ray reflectRay = new Ray();
			Vector3 norm = new Vector3();
			inter.normal.normalize(norm); //create a unit normal vector
			ray.reflect(reflectRay, norm, inter.intersection);
			
			//send out multiple reflected rays in a cone instead of just one
			Vector3 ref_dir = reflectRay.direction;
			Vector3 perp1 = new Vector3(); //create 2 perpendicular vectors to scale
			Vector3 perp2 = new Vector3();
			perp1.x = ref_dir.z;
			perp1.y = 0.0f;
			perp1.z = -1.0f*ref_dir.x;
			ref_dir.crossProd(perp2, perp1);
			Color avg = new Color();
			int num = 64 / (depth + 1);
			for(int i = 0; i < num; i++) {
				float x, y;
				do {
					x = (float)Math.random() * hit.brdf.kr.dot(hit.brdf.kr);
					y = (float)Math.random() * hit.brdf.kr.dot(hit.brdf.kr);
				} while((x * x + y * y) > hit.brdf.kr.dot(hit.brdf.kr));
				Vector3 new_dir = new Vector3();
				perp1.scale(perp1, x);
				ref_dir.add(new_dir, perp1);
				perp2.scale(perp2, y);
				new_dir.add(new_dir, perp2);
				new_dir.normalize(new_dir);
				Color ref = new Color();
				Ray new_ray = new Ray();
				new_ray.point = reflectRay.point;
				new_ray.direction = new_dir;
				trace(ref, new_ray, depth + 1, hit);
				avg.add(ref);
				//src.addProduct(ref, hit.brdf.kr);
			}
			avg.scale(1.0f/num);
			src.addProduct(avg, hit.brdf.kr);
			//Color ref = new Color();
			//trace(ref, reflectRay, depth + 1, hit);
			//src.addProduct(ref, hit.brdf.kr); // <-- not sure if this is right
		}
		return src;
	}
}
