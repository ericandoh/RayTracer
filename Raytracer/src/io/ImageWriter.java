package io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageWriter {
	public void writeImage(String fileName, BufferedImage img) {
		File output = new File(System.getProperty("user.dir") + "\\" + fileName);
		try {
			ImageIO.write(img, "png", output);
		} catch (IOException e) {
			System.out.println("Error writing image: "+e.getMessage());
		}
	}
}
