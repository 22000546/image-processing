package image.processing.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Temperature extends JPanel {
	
	BufferedImage image;
	BufferedImage newImage;
	
	static int red;
	static int green;
	static int blue;
	
	int temperature;
	
	public Temperature(BufferedImage image, int temperature) {
		this.image = image;
		this.temperature = temperature;
	}
	
	public BufferedImage paintTemperature() {
		
		newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++) {
				
				Color color = new Color(image.getRGB(i, j));
				
				if((color.getRed() + (temperature / 5.0)) > 255) {
					red = 255;
				} else if((color.getRed() + (temperature / 5.0)) < 0) {
					red = 0;
				} else {
					red = (int) (color.getRed() + (temperature / 5.0));
				}
				
				green = color.getGreen();
				
				if((color.getBlue() - (temperature / 5.0)) > 255) {
					blue = 255;
				} else if ((color.getBlue() - (temperature / 5.0)) < 0) {
					blue = 0;
				} else {
					blue = (int) (color.getBlue() - (temperature / 5.0));
				}
				
				Color newColor = new Color(red, green, blue);
				newImage.setRGB(i, j, newColor.getRGB());
				
			}
		}
		
		return newImage;
		
	}

}
