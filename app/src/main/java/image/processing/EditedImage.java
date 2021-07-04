package image.processing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class EditedImage extends JPanel {
	
	private BufferedImage image;
	
	public EditedImage() {
		
		setSize(Main.PANEL_WIDTH, Main.PANEL_HEIGHT);
		setLocation(Main.PANEL_WIDTH+50, 25);
		setBackground(Color.WHITE);
		setLayout(null);
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.drawImage(image, 0, 0, null);
		
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
