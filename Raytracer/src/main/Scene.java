package main;

import java.awt.Color;
import java.awt.image.BufferedImage;

import math.Vector3;

public class Scene {
	
	private Vector3 [][] screen;
	
	public Scene(int width, int height) {
		//here we fill up screen[][]
		screen = new Vector3[width][height];
		
		//all the magic happens here
		
		for (int x = 0; x < screen.length; x++) {
			for (int y = 0; y < screen[0].length; y++) {	
				screen[x][y] = new Vector3(true);
			}
		}
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
