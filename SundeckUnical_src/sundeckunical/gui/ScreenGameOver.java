package sundeckunical.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

class ScreenGameOver extends JWindow {
	
	private int duration;
	private CaptureScreen gameOverCapture;
	private Image sfondo;
	
    public ScreenGameOver(int d) {
	   duration = d;
	   gameOverCapture = new CaptureScreen();
	   sfondo= GamePanel.getMyImageProvider().getStreet();
	   JPanel content = (JPanel) getContentPane();
	   content.setBackground(Color.white);
	   int width = 450;
	   int height = 115;
	   int x = (gameOverCapture.getDimension().width - width) / 2;
	   int y = (gameOverCapture.getDimension().height - height) / 2;
	   setBounds(x, y, width, height);
	
	   content.add(new JLabel("GAME OVER"), BorderLayout.CENTER);
	   Color oraRed = new Color(156, 20, 20, 255);
	   content.setBorder(BorderFactory.createLineBorder(oraRed, 10));
	   
	   setVisible(true);
	   try {
	     Thread.sleep(duration);
	   } catch (Exception e) {
	   }
	   gameOverCapture.getCapture().dispose();
	 }
 
 
}