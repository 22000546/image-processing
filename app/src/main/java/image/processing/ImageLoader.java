package image.processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageLoader extends JFrame {
	
	public static String loadImage() {
		
		String filepath = "";
		JFileChooser chooser = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "jpg", "gif", "jpeg", "png");
		chooser.setFileFilter(filter);
		
		int chosen = chooser.showOpenDialog(null);
		if(chosen != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			return filepath;
		}
		
		filepath = chooser.getSelectedFile().getPath();
		
		return filepath;
		
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
