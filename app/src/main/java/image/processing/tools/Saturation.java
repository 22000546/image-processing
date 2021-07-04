package image.processing.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Saturation extends JPanel {
	
	BufferedImage image;
	BufferedImage newImage;
	
	static int red;
	static int green;
	static int blue;
	
	int saturation;
	
	public Saturation(BufferedImage image, int saturation) {
		this.image = image;
		this.saturation = saturation;
	}
	
	public BufferedImage paintSaturation() {
		
		newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++) {
				
				Color color = new Color(image.getRGB(i, j));
				
				float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
				
				if(saturation != 0) {
					hsb[1] = (float) (hsb[1] * ((saturation + 100) * 0.008));
				}
				
				if(hsb[1] > 100) {
					hsb[1] = 100;
				} else if(hsb[1] < 0) {
					hsb[1] = 0;
				}
				
				newImage.setRGB(i, j, Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
				
			}
		}
		
		return newImage;
		
	}

}
