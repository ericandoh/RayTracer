package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import math.Ellipsoid;
import math.Shape;
import math.Vector3;

public class Scene {
	
	private Vector3 [][] screen;
	
	private ArrayList<Shape> shapes;
	
	public Scene() {
		//initializes a scene which can have objects
		shapes = new ArrayList<Shape>();
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
				screen[x][y] = paintAtPixel(x, y);
			}
		}
	}
	
	public Vector3 paintAtPixel(int x, int y) {
		//oeijfpdijpd
		
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
