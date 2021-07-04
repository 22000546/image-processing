package image.processing;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageLoader extends JFrame {
	
	public static BufferedImage loadImage() {
		
		BufferedImage image = null;
		Image tmp = null;
		
		JFileChooser chooser = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "jpg", "gif", "jpeg", "png");
		chooser.setFileFilter(filter);
		
		int chosen = chooser.showOpenDialog(null);
		if(chosen != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			return image;
		}
		
		String filepath = chooser.getSelectedFile().getPath();
		
		File input = new File(filepath);
		
		try {
			image = ImageIO.read(input);
		} catch (IOException e1) {
			e1.getMessage();
		}
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		int newWidth = 0;
		int newHeight = 0;
		
		if(width < height) {
			newHeight = Main.PANEL_HEIGHT;
			newWidth = (int) (width / (float) (height / (float) Main.PANEL_HEIGHT));
		} else if(width > height) {
			newWidth = Main.PANEL_WIDTH;
			newHeight = (int) (height / (float) (width / (float) Main.PANEL_WIDTH));
		} else {
			newWidth = Main.PANEL_WIDTH;
			newHeight = Main.PANEL_WIDTH;
		}
		
		tmp = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	    image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = image.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
		
		return image;
		
	}
	
	public static void saveImage(BufferedImage image) throws IOException {
		
		JFileChooser directoryChooser = new JFileChooser();
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int chosen = directoryChooser.showSaveDialog(null);
		
		if(chosen != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "폴더가 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String filepath = directoryChooser.getSelectedFile().getPath();
						
		if(ImageIO.write(image, "png", new File(filepath + ".png"))) {
			JOptionPane.showMessageDialog(null, "파일이 정상적으로 저장되었습니다.");
		}
		
		
	}
	

}
