package image.processing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import image.processing.tools.Brightness;
import image.processing.tools.Contrast;
import image.processing.tools.GrayScale;
import image.processing.tools.Hue;
import image.processing.tools.Saturation;
import image.processing.tools.Temperature;

public class Main extends JFrame implements MouseMotionListener {
	
	JFrame frame;
	JLabel mag;
	
	BufferedImage originalImage;
	
	OriginalImage original;
	EditedImage  edited;
	
	static final int WIDTH = 1400;
	static final int HEIGHT = 700;
	public static final int PANEL_WIDTH = 500;
	public static final int PANEL_HEIGHT = 600;
	
	
	static int sliderValue;
	static int buttonMode = 0;
	static final int BRIGHTNESS = 1;
	static final int CONTRAST = 2;
	static final int SATURATION = 3;
	static final int HUE = 4;
	static final int TEMPERATURE = 5;
	static final int MAGNIFIED = 6;
	
	Graphics g;
	
	/*
	 * 
	 */
	String imagePath;
	
	private static ArrayList<BufferedImage> undoList = new ArrayList<>();
	private static ArrayList<BufferedImage> redoList = new ArrayList<>();
	
	public static void main(String args[]) {
		
		new Main();
		
	}
	
	public Main() {
		
		frame = new JFrame();
		frame.setBounds(25, 50, WIDTH, HEIGHT);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		initialize();
		
	}
	
	private void initialize() {
		
		// Panels
		
		original = new OriginalImage();
		original.addMouseMotionListener(this);
		edited = new EditedImage();	
		edited.addMouseMotionListener(this);
		EditedImage temp = new EditedImage();
				
		JPanel top = new JPanel();
		top.setBounds(0, 0, WIDTH, HEIGHT);
		top.setLayout(null);
		top.setBackground(new Color(175, 162, 186));
		top.add(original);
		top.add(edited);
		top.add(temp);
		frame.getContentPane().add(top);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(1075, 0, 1, Main.HEIGHT);
		top.add(panel);
		
		File images = new File("./images/default.jpeg");
		BufferedImage defaultImage = null;
		try {
			defaultImage = ImageIO.read(images);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		original.setImage(defaultImage);
		edited.setImage(defaultImage);
		
		// Magnifier
		mag = new JLabel();
		mag.setBounds(1112, 375, 250, 250);
		mag.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		mag.setOpaque(true);
		mag.setBackground(Color.WHITE);
		top.add(mag);
		
		
		// Menu Bar
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuBar.setBounds(0, 0, WIDTH, 30);
		
		JMenu file = new JMenu("파일");
		menuBar.add(file);
		
		JMenu edit = new JMenu("편집");
		menuBar.add(edit);
		
		JMenuItem load = new JMenuItem("불러오기");
		load.setAccelerator(KeyStroke.getKeyStroke('L' ,KeyEvent.VK_CONTROL));
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				originalImage = ImageLoader.loadImage();
				if(originalImage != null) {
					original.setImage(originalImage);
					original.repaint();
					edited.setImage(originalImage);;
					edited.repaint();
					
					undoList.clear();
					redoList.clear();
					undoList.add(edited.getImage());
				}
			}
		});
		file.add(load);
		
		JMenuItem save = new JMenuItem("저장하기");
		save.setAccelerator(KeyStroke.getKeyStroke('S' ,KeyEvent.VK_CONTROL));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ImageLoader.saveImage(edited.getImage());
				} catch (IOException e1) {
					e1.getMessage();
				}
			}
		});
		file.add(save);
		
		JMenuItem exit = new JMenuItem("종료");
		file.add(exit);
		exit.setAccelerator(KeyStroke.getKeyStroke('q' ,KeyEvent.VK_CONTROL));
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem undo = new JMenuItem("뒤로 가기");
		undo.setAccelerator(KeyStroke.getKeyStroke('Z' ,KeyEvent.VK_CONTROL));
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(undoList.isEmpty() || undoList.size() == 1) {
					JOptionPane.showMessageDialog(null, "뒤로 갈 항목이 없습니다.");	
				} else if(buttonMode != 0) {
					JOptionPane.showMessageDialog(null, "버튼 모드를 해제하고 사용해주십시오. ");
				} else {
					redoList.add(undoList.remove(undoList.size()-1));
					BufferedImage img = undoList.get(undoList.size()-1);
					edited.setImage(img);
					edited.repaint();
				}
			}
		});
		edit.add(undo);
		
		JMenuItem redo = new JMenuItem("앞으로 가기");
		redo.setAccelerator(KeyStroke.getKeyStroke('R' ,KeyEvent.VK_CONTROL));
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(redoList.isEmpty() || redoList.size() == 1) {
					JOptionPane.showMessageDialog(null, "앞으로 갈 항목이 없습니다.");
				} else {
					undoList.add(redoList.remove(redoList.size()-1));
					BufferedImage img = redoList.get(redoList.size()-1);
					edited.setImage(img);
					edited.repaint();
				}
			}
		});
		edit.add(redo);		
		
		// Slider
		JSlider slider = new JSlider(-100, 100, 0);
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBounds(1138, 20, 200, 50);
		slider.setBackground(Color.WHITE);
		slider.setVisible(true);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
                 sliderValue = slider.getValue();
                 BufferedImage tmp = null;
                 if(buttonMode == BRIGHTNESS) {
                	 Brightness bright = new Brightness(edited.getImage(), sliderValue);
                	 tmp = bright.paintBrightness();
                 } else if(buttonMode == CONTRAST) {
                	 Contrast con = new Contrast(edited.getImage(), sliderValue);
                	 tmp = con.paintContrast();
                 } else if(buttonMode == TEMPERATURE) {
                	 Temperature t = new Temperature(edited.getImage(), sliderValue);
                	 tmp = t.paintTemperature();
                 } else if(buttonMode == SATURATION) {
                	 Saturation s = new Saturation(edited.getImage(), sliderValue);
                	 tmp = s.paintSaturation();
                 } else if(buttonMode == HUE) {
                	 Hue h = new Hue(edited.getImage(), sliderValue);
                	 tmp = h.paintHue();
                 } else if(buttonMode == MAGNIFIED) {
                	 //
                 }
                 temp.setImage(tmp);
            	 temp.repaint();
            }
		});
		top.add(slider);
		
		// Buttons
		// 흑백 , 대비 , 밝기 , 채도 , 색조
		
		JButton blackNwhite = new JButton("흑백");
		blackNwhite.setBounds(1126, 115, 100, 50);
		blackNwhite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(undoList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "이미지가 로드되지 않았습니다.");
				} else {
					GrayScale gray = new GrayScale(edited.getImage());
					BufferedImage tmp = gray.paintGray();
					undoList.add(tmp);
					edited.setImage(tmp);
					edited.repaint();
				}
			}
		});
		top.add(blackNwhite);
		
		JButton saturation = new JButton("채도 조절");
		saturation.setBounds(1251, 115, 100, 50);
		saturation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buttonMode == 0) {
					if(undoList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "이미지가 로드되지 않았습니다.");
					} else {
						buttonMode = SATURATION;
						slider.setValue(0);
						saturation.setForeground(new Color(175, 162, 186));
					}
				} else if(buttonMode == SATURATION) {
					buttonMode = 0;
					saturation.setForeground(Color.BLACK);
					Saturation sat = new Saturation(edited.getImage(), sliderValue);
					BufferedImage tmp = sat.paintSaturation();
					undoList.add(tmp);
					edited.setImage(tmp);
					edited.repaint();
				}
			}
		});
		top.add(saturation);
		
		JButton bright = new JButton("밝기 조절");
		bright.setBounds(1126, 180, 100, 50);
		bright.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buttonMode == 0) {
					if(undoList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "이미지가 로드되지 않았습니다.");
					} else {
						buttonMode = BRIGHTNESS;
						slider.setValue(0);
						bright.setForeground(new Color(175, 162, 186));
					}
				} else if(buttonMode == BRIGHTNESS) {
					buttonMode = 0;
					bright.setForeground(Color.BLACK);
					Brightness bright = new Brightness(edited.getImage(), sliderValue);
					BufferedImage tmp = bright.paintBrightness();
					undoList.add(tmp);
					edited.setImage(tmp);
					edited.repaint();
				}
			}
		});
		top.add(bright);
		
		JButton contrast = new JButton("대비 조절");
		contrast.setBounds(1251, 180, 100, 50);
		contrast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buttonMode == 0) {
					if(undoList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "이미지가 로드되지 않았습니다.");
					} else {
						buttonMode = CONTRAST;
						slider.setValue(0);
						contrast.setForeground(new Color(175, 162, 186));
					}
				} else if(buttonMode == CONTRAST) {
					buttonMode = 0;
					contrast.setForeground(Color.BLACK);
					Contrast con = new Contrast(edited.getImage(), sliderValue);
					BufferedImage tmp = con.paintContrast();
					undoList.add(tmp);
					edited.setImage(tmp);
					edited.repaint();
				}
			}
		});
		top.add(contrast);
		
		JButton color = new JButton("색상 조절");
		color.setBounds(1126, 245, 100, 50);
		color.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buttonMode == 0) {
					if(undoList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "이미지가 로드되지 않았습니다.");
					} else {
						buttonMode = HUE;
						slider.setValue(0);
						color.setForeground(new Color(175, 162, 186));
					}
				} else if(buttonMode == HUE) {
					buttonMode = 0;
					color.setForeground(Color.BLACK);
					Hue t = new Hue(edited.getImage(), sliderValue);
					BufferedImage tmp = t.paintHue();
					undoList.add(tmp);
					edited.setImage(tmp);
					edited.repaint();
				}
			}
		});
		top.add(color);
		
		JButton warm = new JButton("온도 조절");
		warm.setBounds(1251, 245, 100, 50);
		warm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buttonMode == 0) {
					if(undoList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "이미지가 로드되지 않았습니다.");
					} else {
						buttonMode = TEMPERATURE;
						slider.setValue(0);
						warm.setForeground(new Color(175, 162, 186));
					}
				} else if(buttonMode == TEMPERATURE) {
					buttonMode = 0;
					warm.setForeground(Color.BLACK);
					Temperature t = new Temperature(edited.getImage(), sliderValue);
					BufferedImage tmp = t.paintTemperature();
					undoList.add(tmp);
					edited.setImage(tmp);
					edited.repaint();
				}
			}
		});
		top.add(warm);
		
		JButton magnifier = new JButton("돋보기");
		magnifier.setBounds(1126, 310, 100, 50);
		magnifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buttonMode == 0) {
					if(undoList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "이미지가 로드되지 않았습니다.");
					} else {
						buttonMode = MAGNIFIED;
						magnifier.setForeground(new Color(175, 162, 186));
					}
				} else if(buttonMode == MAGNIFIED) {
					buttonMode = 0;
					magnifier.setForeground(Color.BLACK);
					mag.setIcon(new ImageIcon());
				}
			}
		});
		top.add(magnifier);
		
		
		
		frame.setVisible(true);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(buttonMode == MAGNIFIED) {

	        int x = e.getX();
	        int y = e.getY();
	         
	        try{
	        	if(e.getSource() == original) {
	        		mag.setIcon(new ImageIcon(original.getImage().getSubimage(x-25,y-25,50,50).getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
	        	} else {
	        		mag.setIcon(new ImageIcon(edited.getImage().getSubimage(x-25,y-25,50,50).getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
	        	}
	        }catch(Exception a) {
	           
	        }
		}
		
	}
}
