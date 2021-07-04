package image.processing.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Hue extends JPanel {
	
	BufferedImage image;
	BufferedImage newImage;
	
	static int red;
	static int green;
	static int blue;
	
	int hue;
	
	public Hue(BufferedImage image, int hue) {
		this.image = image;
		this.hue = hue;
	}
	
	public BufferedImage paintHue() {
		
		newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++) {
				
				Color color = new Color(image.getRGB(i, j));
				
				float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
				
				if(hue != 0) {
					hsb[0] = (float) (hsb[0] * (hue+100) * 0.008);
				}
				
				if(hsb[0] > 100) {
					hsb[0] = 100;
				} else if(hsb[0] < 0) {
					hsb[0] = 0;
				}
				
				newImage.setRGB(i, j, Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
				
			}
		}
		
		return newImage;
		
	}

}
