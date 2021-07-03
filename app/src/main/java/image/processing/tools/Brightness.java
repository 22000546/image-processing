package image.processing.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Brightness extends JPanel {
	
	BufferedImage image;
	BufferedImage newImage;
	
	static int red;
	static int green;
	static int blue;
	
	int brightness;
	
	public Brightness(BufferedImage image, int brightness) {
		
		this.image = image;
		this.brightness = brightness;
		
	}
	
	public BufferedImage paintBrightness() {
		
		newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++) {
				
				Color color = new Color(image.getRGB(i, j));
				
				if(color.getRed() + brightness > 255) {
					red = 255;
				} else if(color.getRed() + brightness < 0) {
					red = 0;
				} else {
					red = (int) (color.getRed() + brightness);
				}
				
				if(color.getGreen() + brightness > 255) {
					green = 255;
				} else if(color.getGreen() + brightness < 0) {
					green = 0;
				} else {
					green = (int) (color.getGreen() + brightness);
				}
				
				if(color.getBlue() + brightness > 255) {
					blue = 255;
				} else if(color.getBlue() + brightness < 0) {
					blue = 0;
				} else {
					blue = (int) (color.getBlue() + brightness);
				}
				
				Color newColor = new Color(red, green, blue);
				newImage.setRGB(i, j, newColor.getRGB());
				
			}
		}
		
		return newImage;
		
	}

}
