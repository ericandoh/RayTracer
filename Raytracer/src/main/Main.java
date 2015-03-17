package main;

import io.ObjReader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import raytracer.AmbientLight;
import raytracer.DirectionalLight;
import raytracer.PointLight;
import math.BRDF;
import math.Color;
import math.Ellipsoid;
import math.Point;
import math.Transformation;
import math.Triangle;
import math.Vector3;

public class Main {
	
	public static Scene scene;
	public static BufferedImage canvas;
	
	public static void main(String[] args) {
		
		//read in args here
		
		//width, height
		int width = 600;
		int height = 600;
		
		//make a scene
		scene = new Scene();
		
		int count = 0;
		String head;
		BRDF currentBRDF = new BRDF();
		Transformation currentTransform = null;
		while(count < args.length) {
			head = args[count++];
			if (head.equals("default")) {
				//give scene some objects
				scene.defaultScene();
			}
			else if (head.equals("obj")) {
				scene.addObjects(ObjReader.readObj(args[count++]));
			}
			else if (head.equals("cam")) {
				//set up camera
				float ex = Float.parseFloat(args[count++]);
				float ey = Float.parseFloat(args[count++]);
				float ez = Float.parseFloat(args[count++]);
				
				float llx = Float.parseFloat(args[count++]);
				float lly = Float.parseFloat(args[count++]);
				float llz = Float.parseFloat(args[count++]);
				
				float lrx = Float.parseFloat(args[count++]);
				float lry = Float.parseFloat(args[count++]);
				float lrz = Float.parseFloat(args[count++]);
				
				float ulx = Float.parseFloat(args[count++]);
				float uly = Float.parseFloat(args[count++]);
				float ulz = Float.parseFloat(args[count++]);
				
				float urx = Float.parseFloat(args[count++]);
				float ury = Float.parseFloat(args[count++]);
				float urz = Float.parseFloat(args[count++]);
				
				scene.cam.setCenter(new Point(ex, ey, ez));
				scene.cam.setImagePlaneCorners(new Point(llx, lly, llz), 
									new Point(lrx, lry, lrz), 
									new Point(ulx, uly, ulz), 
									new Point(urx, ury, urz));
			}
			else if (head.equals("sph")) {
				float cx = Float.parseFloat(args[count++]);
				float cy = Float.parseFloat(args[count++]);
				float cz = Float.parseFloat(args[count++]);
				float r = Float.parseFloat(args[count++]);
				scene.addShape(new Ellipsoid(new Point(cx, cy, cz), r), currentBRDF, currentTransform);
				//add transform to this
				//currentBRDF = new BRDF();
				//reset transform for next object...?
				currentTransform = null;
			}
			else if (head.equals("tri")) {
				float ax = Float.parseFloat(args[count++]);
				float ay = Float.parseFloat(args[count++]);
				float az = Float.parseFloat(args[count++]);
				
				float bx = Float.parseFloat(args[count++]);
				float by = Float.parseFloat(args[count++]);
				float bz = Float.parseFloat(args[count++]);
				
				float cx = Float.parseFloat(args[count++]);
				float cy = Float.parseFloat(args[count++]);
				float cz = Float.parseFloat(args[count++]);
				
				scene.addShape(new Triangle(new Point(ax, ay, az), 
										new Point(bx, by, bz), 
										new Point(cx, cy, cz)), currentBRDF, currentTransform);
				//currentBRDF = new BRDF();
				//reset transform for next object...?
				currentTransform = null;
			}
			else if (head.equals("ltp")) {
				float px = Float.parseFloat(args[count++]);
				float py = Float.parseFloat(args[count++]);
				float pz = Float.parseFloat(args[count++]);
				
				float r = Float.parseFloat(args[count++]);
				float g = Float.parseFloat(args[count++]);
				float b = Float.parseFloat(args[count++]);
				
				if (args[count].equals("0") 
						|| args[count].equals("1") 
						|| args[count].equals("2")) {
					//we have a falloff specified
					float falloff = Float.parseFloat(args[count++]);
				}
				System.out.println("Add support for falloff");
				scene.addLight(new PointLight(new Vector3(px, py, pz), new Color(r, g, b)));
			}
			else if (head.equals("ltd")) {
				float dx = Float.parseFloat(args[count++]);
				float dy = Float.parseFloat(args[count++]);
				float dz = Float.parseFloat(args[count++]);
				
				float r = Float.parseFloat(args[count++]);
				float g = Float.parseFloat(args[count++]);
				float b = Float.parseFloat(args[count++]);
				
				scene.addLight(new DirectionalLight(new Vector3(dx, dy, dz), new Color(r, g, b)));
			}
			else if (head.equals("lta")) {
				float r = Float.parseFloat(args[count++]);
				float g = Float.parseFloat(args[count++]);
				float b = Float.parseFloat(args[count++]);
				
				scene.addLight(new AmbientLight(new Color(r, g, b)));
			}
			else if (head.equals("mat")) {
				
				currentBRDF = new BRDF();
				
				float kar = Float.parseFloat(args[count++]);
				float kag = Float.parseFloat(args[count++]);
				float kab = Float.parseFloat(args[count++]);
				
				float kdr = Float.parseFloat(args[count++]);
				float kdg = Float.parseFloat(args[count++]);
				float kdb = Float.parseFloat(args[count++]);
				
				float ksr = Float.parseFloat(args[count++]);
				float ksg = Float.parseFloat(args[count++]);
				float ksb = Float.parseFloat(args[count++]);
				
				float ksp = Float.parseFloat(args[count++]);
				
				float krr = Float.parseFloat(args[count++]);
				float krg = Float.parseFloat(args[count++]);
				float krb = Float.parseFloat(args[count++]);
				
				currentBRDF.ka.set(kar, kag, kab);
				currentBRDF.kd.set(kdr, kdg, kdb);
				currentBRDF.ks.set(ksr, ksg, ksb);
				currentBRDF.ksp = ksp;
				currentBRDF.kr.set(krr, krg, krb);
			}
			else if (head.equals("xft")) {
				float tx = Float.parseFloat(args[count++]);
				float ty = Float.parseFloat(args[count++]);
				float tz = Float.parseFloat(args[count++]);
				if (currentTransform == null)
					currentTransform = new Transformation();
				currentTransform.translate(tx, ty, tz);
			}
			else if (head.equals("xfr")) {
				float rx = Float.parseFloat(args[count++]);
				float ry = Float.parseFloat(args[count++]);
				float rz = Float.parseFloat(args[count++]);
				if (currentTransform == null)
					currentTransform = new Transformation();
				currentTransform.rotate(rx, ry, rz);
			}
			else if (head.equals("xfs")) {
				float sx = Float.parseFloat(args[count++]);
				float sy = Float.parseFloat(args[count++]);
				float sz = Float.parseFloat(args[count++]);
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
		
		
		
		//tell scene to paint itself
		scene.paintScene(width, height);
		
		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		scene.fillImage(canvas);
		
		showFrame(width, height, canvas);
	}
	
	public static void showFrame(int width, int height, BufferedImage img) {
		JFrame frame = new JFrame();
		final DrawPanel panel = new DrawPanel(img);
		panel.setPreferredSize(new Dimension(width,height));
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
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
				panel.repaint();
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
