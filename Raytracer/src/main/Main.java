package main;

import io.ObjReader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import raytracer.AmbientLight;
import raytracer.DirectionalLight;
import raytracer.Light;
import raytracer.PointLight;
import raytracer.WorldObject;
import math.BRDF;
import math.Color;
import math.Ellipsoid;
import math.Point;
import math.Transformation;
import math.Triangle;
import math.Vector3;

public class Main {
	
	public static DrawPanel panel;
	
	public static Scene scene;
	public static BufferedImage canvas;
	
	//whether to interpolate vertex normals (if specified)
	//if false, use Goraud shading
	public static boolean simpleShading = true;
	
	//change this to false for submission
	public static boolean showOutput = true;
	public static String outputFileName = "result.png";
	
	public static int width = 600;
	public static int height = 600;
	
	public static void parseArguments(ArrayList<String> args) {
		
		int count = 0;
		String head;
		BRDF currentBRDF = new BRDF();
		//treat null transformation as identity 
		//(so we don't have to do multiplying by identity matrix in calculations)
		Transformation currentTransform = null;
		while(count < args.size()) {
			head = args.get(count++);
			
			if (head.startsWith("-")) {
				head = head.substring(1);
			}
			
			if (head.equals("default")) {
				//give scene some objects
				scene.defaultScene();
			}
			else if (head.equals("out")) {
				outputFileName = args.get(count++);
			}
			else if (head.equals("dim")) {
				width = Integer.parseInt(args.get(count++));
				height = Integer.parseInt(args.get(count++));
			}
			else if (head.equals("vis")) {
				showOutput = true;
			}
			else if (head.equals("smooth")) {
				simpleShading=false;
			}
			else if (head.equals("obj")) {
				ArrayList<WorldObject> objects = ObjReader.readObj(args.get(count++));
				Collections.reverse(objects);
				
				if (currentTransform != null) {
					for (WorldObject obj: objects) {
						obj.setTransformation(currentTransform);
						currentTransform = Transformation.copyTransform(currentTransform);
					}
				}
				
				if (objects.size() > 0 && objects.get(objects.size() - 1).brdf == null) {
					//copy BRDF/transforms for use in next object
					objects.get(objects.size() - 1).brdf = currentBRDF;
					currentBRDF = new BRDF(currentBRDF);
				}
				scene.addObjects(objects);
			}
			else if (head.equals("cam")) {
				//set up camera
				float ex = Float.parseFloat(args.get(count++));
				float ey = Float.parseFloat(args.get(count++));
				float ez = Float.parseFloat(args.get(count++));
				
				float llx = Float.parseFloat(args.get(count++));
				float lly = Float.parseFloat(args.get(count++));
				float llz = Float.parseFloat(args.get(count++));
				
				float lrx = Float.parseFloat(args.get(count++));
				float lry = Float.parseFloat(args.get(count++));
				float lrz = Float.parseFloat(args.get(count++));
				
				float ulx = Float.parseFloat(args.get(count++));
				float uly = Float.parseFloat(args.get(count++));
				float ulz = Float.parseFloat(args.get(count++));
				
				float urx = Float.parseFloat(args.get(count++));
				float ury = Float.parseFloat(args.get(count++));
				float urz = Float.parseFloat(args.get(count++));
				
				scene.cam.setCenter(new Point(ex, ey, ez));
				scene.cam.setImagePlaneCorners(new Point(llx, lly, llz), 
									new Point(lrx, lry, lrz), 
									new Point(ulx, uly, ulz), 
									new Point(urx, ury, urz));
				
				scene.cam.setTransformation(currentTransform);
				currentTransform = Transformation.copyTransform(currentTransform);
			}
			else if (head.equals("sph")) {
				float cx = Float.parseFloat(args.get(count++));
				float cy = Float.parseFloat(args.get(count++));
				float cz = Float.parseFloat(args.get(count++));
				float r = Float.parseFloat(args.get(count++));
				scene.addShape(new Ellipsoid(new Point(cx, cy, cz), r), currentBRDF, currentTransform);
				//copy BRDF/transforms for use in next object
				currentBRDF = new BRDF(currentBRDF);
				currentTransform = Transformation.copyTransform(currentTransform);
			}
			else if (head.equals("tri")) {
				float ax = Float.parseFloat(args.get(count++));
				float ay = Float.parseFloat(args.get(count++));
				float az = Float.parseFloat(args.get(count++));
				
				float bx = Float.parseFloat(args.get(count++));
				float by = Float.parseFloat(args.get(count++));
				float bz = Float.parseFloat(args.get(count++));
				
				float cx = Float.parseFloat(args.get(count++));
				float cy = Float.parseFloat(args.get(count++));
				float cz = Float.parseFloat(args.get(count++));
				
				scene.addShape(new Triangle(new Point(ax, ay, az), 
										new Point(bx, by, bz), 
										new Point(cx, cy, cz)), currentBRDF, currentTransform);
				//copy BRDF/transforms for use in next object
				currentBRDF = new BRDF(currentBRDF);
				currentTransform = Transformation.copyTransform(currentTransform);
			}
			else if (head.equals("ltp")) {
				float px = Float.parseFloat(args.get(count++));
				float py = Float.parseFloat(args.get(count++));
				float pz = Float.parseFloat(args.get(count++));
				
				float r = Float.parseFloat(args.get(count++));
				float g = Float.parseFloat(args.get(count++));
				float b = Float.parseFloat(args.get(count++));
				Light light = new PointLight(new Point(px, py, pz), new Color(r, g, b));
				
				if (args.get(count).equals("0") 
						|| args.get(count).equals("1") 
						|| args.get(count).equals("2")) {
					//we have a falloff specified
					int falloff = Integer.parseInt(args.get(count++));
					((PointLight)light).falloff = falloff;
				}
				//System.out.println("Add support for falloff");
				light.setTransformation(currentTransform);
				scene.addLight(light);
				currentTransform = Transformation.copyTransform(currentTransform);
			}
			else if (head.equals("ltd")) {
				float dx = Float.parseFloat(args.get(count++));
				float dy = Float.parseFloat(args.get(count++));
				float dz = Float.parseFloat(args.get(count++));
				
				float r = Float.parseFloat(args.get(count++));
				float g = Float.parseFloat(args.get(count++));
				float b = Float.parseFloat(args.get(count++));
				Light light = new DirectionalLight(new Vector3(dx, dy, dz), new Color(r, g, b));
				light.setTransformation(currentTransform);
				scene.addLight(light);
				currentTransform = Transformation.copyTransform(currentTransform);
			}
			else if (head.equals("lta")) {
				float r = Float.parseFloat(args.get(count++));
				float g = Float.parseFloat(args.get(count++));
				float b = Float.parseFloat(args.get(count++));
				
				scene.addLight(new AmbientLight(new Color(r, g, b)));
			}
			else if (head.equals("mat")) {
				
				currentBRDF = new BRDF();
				
				float kar = Float.parseFloat(args.get(count++));
				float kag = Float.parseFloat(args.get(count++));
				float kab = Float.parseFloat(args.get(count++));
				
				float kdr = Float.parseFloat(args.get(count++));
				float kdg = Float.parseFloat(args.get(count++));
				float kdb = Float.parseFloat(args.get(count++));
				
				float ksr = Float.parseFloat(args.get(count++));
				float ksg = Float.parseFloat(args.get(count++));
				float ksb = Float.parseFloat(args.get(count++));
				
				float ksp = Float.parseFloat(args.get(count++));
				
				float krr = Float.parseFloat(args.get(count++));
				float krg = Float.parseFloat(args.get(count++));
				float krb = Float.parseFloat(args.get(count++));
				
				currentBRDF.ka.set(kar, kag, kab);
				currentBRDF.kd.set(kdr, kdg, kdb);
				currentBRDF.ks.set(ksr, ksg, ksb);
				currentBRDF.ksp = ksp;
				currentBRDF.kr.set(krr, krg, krb);
			}
			else if (head.equals("xft")) {
				float tx = Float.parseFloat(args.get(count++));
				float ty = Float.parseFloat(args.get(count++));
				float tz = Float.parseFloat(args.get(count++));
				if (currentTransform == null)
					currentTransform = new Transformation();
				currentTransform.translate(tx, ty, tz);
			}
			else if (head.equals("xfr")) {
				float rx = Float.parseFloat(args.get(count++));
				float ry = Float.parseFloat(args.get(count++));
				float rz = Float.parseFloat(args.get(count++));
				if (currentTransform == null)
					currentTransform = new Transformation();
				currentTransform.rotate(rx, ry, rz);
			}
			else if (head.equals("xfs")) {
				float sx = Float.parseFloat(args.get(count++));
				float sy = Float.parseFloat(args.get(count++));
				float sz = Float.parseFloat(args.get(count++));
				if (currentTransform == null)
					currentTransform = new Transformation();
				currentTransform.scale(sx, sy, sz);
			}
			else if (head.equals("xfz")) {
				if (currentTransform != null)
					currentTransform.reset();
				currentTransform = null;
			}
			else if (head.replaceAll("\t", "").replaceAll(" ", "").isEmpty()) {
				//only has tabs/spaces
				//extraneous argument - ignore
			}
			else {
				System.err.println("Unknown input: " + head);
			}
		}
	}
	
	public static void main(String[] args) {
		
		//read in args here
		
		//make a scene
		scene = new Scene();
		
		ArrayList<String> arrayArgs;
		
		if (args.length == 1) {
			//only one argument - assume its a file name and try reading from it
			//read in file containing objects - if file not found do regular reading
			try {
				arrayArgs = ObjReader.readFile(args[0]);
			} catch (FileNotFoundException e) {
				arrayArgs = new ArrayList<String>(Arrays.asList(args));
			} catch (IOException e) {
				arrayArgs = new ArrayList<String>(Arrays.asList(args));
			}
		}
		else {
			arrayArgs = new ArrayList<String>(Arrays.asList(args));
		}
		parseArguments(arrayArgs);
		
		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		if (showOutput) {
			showFrame(width, height, canvas);
		}
		
		//tell scene to paint itself
		scene.paintScene(width, height, showOutput);
		
		
		scene.fillImage(canvas);
		
		//save image here
		if (!outputFileName.endsWith(".png")) {
			outputFileName = outputFileName + ".png";
		}
		try {
			File outputImg = new File(outputFileName);
			ImageIO.write(canvas, "png", outputImg);
		} catch(IOException e) {
			System.err.printf("Couldn't write to file "+outputFileName+": "+e.getMessage());
		}
	}
	
	public static void repaint(int x, int y, Color c) {
		
		canvas.setRGB(x, y, new java.awt.Color(c.r, c.g, c.b).getRGB());
		
		panel.repaint();
	}
	
	public static void showFrame(int width, int height, BufferedImage img) {
		JFrame frame = new JFrame();
		panel = new DrawPanel(img);
		panel.setPreferredSize(new Dimension(width,height));
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				return;
				/*
				if (arg0.getKeyCode() == KeyEvent.VK_W) {
					System.out.println("W");
					scene.cam.displace(new Vector3(0, 0, -1));
				}
				else if (arg0.getKeyCode() == KeyEvent.VK_S) {
					scene.cam.displace(new Vector3(0, 0, 1));
				}
				else if (arg0.getKeyCode() == KeyEvent.VK_A) {
					scene.cam.displace(new Vector3(1, 0, 0));
				}
				else if (arg0.getKeyCode() == KeyEvent.VK_D) {
					scene.cam.displace(new Vector3(-1, 0, 0));
				}
				else if (arg0.getKeyCode() == KeyEvent.VK_C) {
					scene.cam.displace(new Vector3(0, -1, 0));
				}
				else if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
					scene.cam.displace(new Vector3(0, 1, 0));
				}
				else {
					return;
				}
				scene.repaintScene();
				scene.fillImage(canvas);
				panel.setImg(canvas);
				panel.repaint();*/
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class DrawPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage src;
	public DrawPanel(BufferedImage img) {
		this.src = img;
	}
	public void setImg(BufferedImage img) {
		this.src = img;
	}
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(src, null, null);
    }
}
