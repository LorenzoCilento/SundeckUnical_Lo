package sundeckunical.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.ImageIcon;

class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image img;  //immagine di sfondo del SettingsPanel
	
	public SettingsPanel(MainFrame mainFrame) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		
		setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));
		
		img = kit.createImage("resources/img/sfondoMenu.jpg");
		loadImage(img);
		setLayout(null);
		
		JSlider slider = new JSlider();
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMaximum(5);
		slider.setPaintLabels(true);
		slider.setBounds(33, 110, 190, 29);
		add(slider);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setIcon(new ImageIcon(SettingsPanel.class.getResource("/settingButton/backButton.png")));
		btnNewButton.setBounds(26, 270, 150, 112);
		add(btnNewButton);
	}

    private void loadImage(Image img) {
	    try {
	      MediaTracker track = new MediaTracker(this);
	      track.addImage(img, 0);
	      track.waitForID(0);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	  }
	
    @SuppressWarnings("unused")
	private void showExitConfirm() {
    	int option = JOptionPane.showConfirmDialog(this,
    			"Do you really want to exit?", "Exit",
    			JOptionPane.YES_NO_OPTION);
    	if (option == JOptionPane.YES_OPTION) {
    		System.exit(0);
    	}
    }
    
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(),null);
	    				
	  }
}
