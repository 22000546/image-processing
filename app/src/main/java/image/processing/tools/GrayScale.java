package image.processing.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import image.processing.EditedImage;
import image.processing.Main;

public class GrayScale extends JPanel {
	
	BufferedImage image;
	BufferedImage newImage;
	
	static int red;
	static int green;
	static int blue;
	
	public GrayScale(BufferedImage image) {
		
		this.image = image;
		
	}
	
	public BufferedImage paintGray() {
		
		newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++) {
				
				Color color = new Color(image.getRGB(i, j));
				red = (int) (color.getRed() * 0.299);
				green = (int) (color.getGreen() * 0.587);
				blue = (int) (color.getBlue() * 0.114);
				
				Color newColor = new Color(red+green+blue, red+green+blue, red+green+blue);
				newImage.setRGB(i, j, newColor.getRGB());
				
			}
		}
		
		return newImage;
		
	}

}
