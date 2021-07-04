package image.processing.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Contrast extends JPanel {
	
	BufferedImage image;
	BufferedImage newImage;
	
	static int red;
	static int green;
	static int blue;
	
	int contrast;
	
	public Contrast(BufferedImage image, int contrast) {
		this.image = image;
		this.contrast = contrast;
	}
	
	public BufferedImage paintContrast() {
		
		newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int width = image.getWidth();
		int height = image.getHeight();
			
		double newContrast = 0;
		double delta = 0;
		
		if(contrast > 0) {
			delta = 127.0 * contrast / 110.0;
			newContrast = 255.0 / (double) (255 - 2 * delta);
		} else if(contrast < 0) {
			delta = -128.0 * contrast / 110.0;
			newContrast = (256 - 2 * delta) / (double) 255.0;
		} else {
			newContrast = 1;
		}
		
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++) {
				
				Color color = new Color(image.getRGB(i, j));
				
				if(color.getRed() * newContrast > 255) {
					red = 255;
				} else if(color.getRed() * newContrast < 0) {
					red = 0;
				} else {
					red = (int) (color.getRed() * newContrast);
				}
				
				if(color.getGreen() * newContrast > 255) {
					green = 255;
				} else if(color.getGreen() * newContrast < 0) {
					green = 0;
				} else {
					green = (int) (color.getGreen() * newContrast);
				}
				
				if(color.getBlue() * newContrast > 255) {
					blue = 255;
				} else if(color.getBlue() * newContrast < 0) {
					blue = 0;
				} else {
					blue = (int) (color.getBlue() * newContrast);
				}
				
				Color newColor = new Color(red, green, blue);
				newImage.setRGB(i, j, newColor.getRGB());
				
			}
		}
		
		return newImage;
		
	}
	

}
