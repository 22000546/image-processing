package image.processing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class OriginalImage extends JPanel {
	
	BufferedImage img;
		
	public OriginalImage() {
		
		setBounds(25, 25, Main.PANEL_WIDTH, Main.PANEL_HEIGHT);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		this.setVisible(true);
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.drawImage(img, 0, 0, null);
		
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public void setImage(BufferedImage image) {
		this.img = image;
	}

}
