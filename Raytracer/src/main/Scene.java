package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import raytracer.Camera;
import raytracer.Raytracer;
import math.Ellipsoid;
import math.Ray;
import math.Shape;
import math.Vector3;

public class Scene {
	
	private Vector3 [][] screen;
	
	private ArrayList<Shape> shapes;
	
	private Camera cam;
	private Raytracer rayTracer;
	
	public Scene() {
		//initializes a scene which can have objects
		shapes = new ArrayList<Shape>();
		
		cam = new Camera();
		rayTracer = new Raytracer();
	}
	
	public void addShape(Shape shape) {
		shapes.add(shape);
	}
	public void defaultScene() {
		shapes.add(new Ellipsoid());
	}
	
	public void paintScene(int width, int height) {
		
		//draws on screen
		
		screen = new Vector3[width][height];
		
		//all the magic happens here
		
		for (int x = 0; x < screen.length; x++) {
			for (int y = 0; y < screen[0].length; y++) {	
				screen[x][y] = paintAtPixel((float)(x) / width, (float)(y) / height);
			}
		}
	}
	
	public Vector3 paintAtPixel(float x, float y) {
		//oeijfpdijpd
		Ray src = new Ray();
		cam.generateRay(x, y, src);
		//rayTracer.trace(src, color);
		return new Vector3(true);
	}
	
	//just for testing
	public void fillImage(BufferedImage img) {
		for (int x = 0; x < screen.length; x++) {
			for (int y = 0; y < screen[0].length; y++) {
				img.setRGB(x, y, new Color(screen[x][y].x, screen[x][y].y, screen[x][y].z).getRGB());
			}
		}
	}
	
}
