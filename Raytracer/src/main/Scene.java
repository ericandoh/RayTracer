package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import raytracer.Camera;
import raytracer.Light;
import raytracer.Raytracer;
import raytracer.World;
import raytracer.WorldObject;
import math.BRDF;
import math.Ray;
import math.Shape;
import math.Color;
import math.Transformation;
import math.Vector3;

public class Scene {
	
	private Color[][] screenColors;
	
	private World world;
	
	public Camera cam;
	private Raytracer rayTracer;
	
	private int width, height;
	
	private boolean tracing;
	
	//temp variables
	//for multithreading, give each thread its own temp values
	private Ray tempRay = new Ray();
	
	public Scene() {
		//initializes a scene which can have objects
		cam = new Camera();
		world = new World();
		rayTracer = new Raytracer(world);
		tracing = false;
	}
	
	public void addObjects(ArrayList<WorldObject> objs) {
		world.addObjects(objs);
	}
	
	public void addShape(Shape shape, BRDF bird, Transformation transform) {
		world.addShape(shape, bird, transform);
	}
	public void defaultScene() {
		world.defaultScene();
	}
	public void addLight(Light l) {
		world.addLight(l);
	}
	
	public void paintScene(int width, int height) {
		
		//draws on screen
		this.width = width;
		this.height = height;
		screenColors = new Color[width][height];
		
		for (int x = 0; x < screenColors.length; x++) {
			for (int y = 0; y < screenColors[0].length; y++) {	
				screenColors[x][y] = new Color();
			}
		}
		
		repaintScene();
	}
	public void repaintScene() {
		//all the magic happens here
		if (tracing) {
			return;
		}
		tracing = true;
		System.out.println("Repainting");
		int tenth = screenColors.length * screenColors[0].length / 10;
		int count = 0;
		for (int x = 0; x < screenColors.length; x++) {
			for (int y = 0; y < screenColors[x].length; y++) {
				count++;
				screenColors[x][y].setBlack();
				paintAtPixel(screenColors[x][y], (float)(x) / width, (float)(y) / height);
				if (count % tenth == 0)
					System.out.println(count / tenth * 10 + "% done");
			}
		}
		tracing = false;
	}
	
	public Color paintAtPixel(Color src, float x, float y) {
		//anti-aliasing through supersampling
		int rays = 9; //ideally the square of an odd number
		int start = -1*(int)Math.sqrt(rays) / 2;
		int end = (int)Math.sqrt(rays) / 2;
		int offset = (int)Math.sqrt(rays);
		for(int i = start; i <= end; i++) {
			for(int j = start; j <= end; j++) {
				cam.generateRay(tempRay, x + ((float)i)/(offset * width), y + ((float)j)/(offset * height));
				Color temp = new Color();
				rayTracer.trace(temp, tempRay, 0, null);
				src.add(temp);
			}
		}
		src.scale(1.0f/rays);
		src.validate();
		return src;
	}
	
	//just for testing
	public void fillImage(BufferedImage img) {
		for (int x = 0; x < screenColors.length; x++) {
			for (int y = 0; y < screenColors[0].length; y++) {
				img.setRGB(x, y, new java.awt.Color(screenColors[x][y].r, screenColors[x][y].g, screenColors[x][y].b).getRGB());
			}
		}
	}
	
}