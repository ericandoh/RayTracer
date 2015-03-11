package main;

import io.ObjReader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	
	public static void main(String[] args) {
		
		//read in args here
		
		//width, height
		int width = 600;
		int height = 600;
		
		//make a scene
		Scene scene = new Scene();
		
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
		
		BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		scene.fillImage(canvas);
		
		showFrame(width, height, canvas);
	}
	
	public static void showFrame(int width, int height, BufferedImage img) {
		JFrame frame = new JFrame();
		JPanel panel = new DrawPanel(img);
		panel.setPreferredSize(new Dimension(width,height));
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
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(src, null, null);
    }
}
