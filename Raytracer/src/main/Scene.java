package main;

import java.awt.image.BufferedImage;

import raytracer.Camera;
import raytracer.Light;
import raytracer.Raytracer;
import raytracer.World;
import math.Ray;
import math.Shape;
import math.Color;
import math.Vector3;

public class Scene {
	
	private Color[][] screenColors;
	
	private World world;
	
	private Camera cam;
	private Raytracer rayTracer;
	
	private int width, height;
	
	//temp variables
	//for multithreading, give each thread its own temp values
	private Ray tempRay = new Ray();
	
	public Scene() {
		//initializes a scene which can have objects
		cam = new Camera();
		world = new World();
		rayTracer = new Raytracer(world);
	}
	
	public void addShape(Shape shape, Vector3 pos) {
		world.addShape(shape, pos);
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
		for (int x = 0; x < screenColors.length; x++) {
			for (int y = 0; y < screenColors[0].length; y++) {
				screenColors[x][y].setBlack();
				paintAtPixel(screenColors[x][y], (float)(x) / width, (float)(y) / height);
			}
		}
	}
	
	public Color paintAtPixel(Color src, float x, float y) {
		cam.generateRay(tempRay, x, y);
		rayTracer.trace(src, tempRay, 0);
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