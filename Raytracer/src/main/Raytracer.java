package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Raytracer {
	
	public static void main(String[] args) {
		
		int width = 800;
		int height = 600;
		
		Scene scene = new Scene(width, height);
		
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
