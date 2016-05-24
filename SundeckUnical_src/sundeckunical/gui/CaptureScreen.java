package sundeckunical.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

class CaptureScreen {

	private JFrame capture;
	private Dimension dimension;
	private JPanel panel;
	private BufferedImage image;
	
	public CaptureScreen() {
		capture=new JFrame();
		capture.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
	    dimension = kit.getScreenSize();
	    capture.setSize(dimension);
	    Rectangle rect = new Rectangle(dimension);
	    try{
	      Robot robot = new Robot();
	      image = robot.createScreenCapture(rect);
	      image.flush();
	      panel = new JPanel(){
	    	  public void paintComponent(Graphics g){
	    		  g.drawImage(image, 0, 0, dimension.width, dimension.height, this);	    		  
	    	  }
	      };
	      panel.setOpaque(false);
	      panel.prepareImage(image, panel);
	      panel.repaint();
	      capture.getContentPane().add(panel);
		  capture.setUndecorated(true);
		  capture.setVisible(true);
	    } catch(Exception e){}
	}

	public JFrame getCapture() {
		return capture;
	}

	public void setCapture(JFrame capture) {
		this.capture = capture;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	    
}