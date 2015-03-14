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
		while(count < args.length) {
			head = args[count++];
			if (head.equals("obj")) {
				scene.addObjects(ObjReader.readObj(args[count++]));
			}
		}
		
		//give scene some objects
		scene.defaultScene();
		
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
