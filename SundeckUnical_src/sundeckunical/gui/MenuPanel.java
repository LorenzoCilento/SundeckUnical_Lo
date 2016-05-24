package sundeckunical.gui;


import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.BoxView;


public class MenuPanel extends JPanel {
	
	
	
	private Image img;  //immagine di sfondo del MenuPanel
	String pathStartButton = "resources/menuButton/Button_Start.png";
	ImageIcon StartButtonIcon = new ImageIcon(pathStartButton);
	String pathStartButton_ON = "resources/menubutton/Button_Start_ON.png";
	ImageIcon StartButton_ON = new ImageIcon(pathStartButton_ON);
	String pathSettingButton = "resources/menuButton/Button_Setting.png";
	ImageIcon SettingButtonIcon = new ImageIcon(pathSettingButton);
	String pathSettingButton_ON = "resources/menubutton/Button_Setting_ON.png";
	ImageIcon SettingButton_ON = new ImageIcon(pathSettingButton_ON);
	String pathHelpButton = "resources/menuButton/Button_Help.png";
	ImageIcon HelpButtonIcon = new ImageIcon(pathHelpButton);
	String pathHelpButton_ON = "resources/menubutton/Button_Help_ON.png";
	ImageIcon HelpButton_ON = new ImageIcon(pathHelpButton_ON);
	String pathExitButton = "resources/menuButton/Button_Exit.png";
	ImageIcon ExitButtonIcon = new ImageIcon(pathExitButton);
	String pathExitButton_ON = "resources/menubutton/Button_Exit_ON.png";
	ImageIcon ExitButton_ON = new ImageIcon(pathExitButton_ON);
	int startScale = 7;
	int otherScale = 10;
	int width = StartButtonIcon.getIconWidth();
	int startWidth = width/startScale;
	int otherWidth = width/otherScale;
	
	public MenuPanel(MainFrame mainFrame) {
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		
		setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/2));

		//setBackground(Color.WHITE);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		img = kit.createImage("resources/img/sfondoMenu.jpg");
		loadImage(img);
	
	    
/*
 * creo i miei pulsanti e le componenti verticali per distanziare i vari componenti	    
 */
		Component verticalGlue_TopToStart = Box.createVerticalGlue();
		add(verticalGlue_TopToStart);
		
		JButton StartGameButton = new JButton();
		StartGameButton.setName("start");
		StartGameButton.setBorder(null);
		StartGameButton.setContentAreaFilled(false);
		StartGameButton.addMouseListener(new MouseOnTheButton());
		StartGameButton.setAlignmentX(CENTER_ALIGNMENT);	
		StartGameButton.addActionListener(e->mainFrame.startGame());
		StartGameButton.setMnemonic(KeyEvent.VK_N);
		StartGameButton.setToolTipText("Inizia una nuova partita!");
		StartGameButton.setIcon(new ImageIcon(StartButtonIcon.getImage().getScaledInstance(startWidth
				, -1, Image.SCALE_SMOOTH)));
		StartGameButton.setBorder(null);
		StartGameButton.setContentAreaFilled(false);
		add(StartGameButton);
		
		
		
		Component verticalGlue_StartToSettings = Box.createVerticalGlue();
		add(verticalGlue_StartToSettings);
		
		JButton SettingsButton = new JButton();
		SettingsButton.addMouseListener(new MouseOnTheButton());
		SettingsButton.setAlignmentX(CENTER_ALIGNMENT);
		SettingsButton.setMnemonic(KeyEvent.VK_S);
		SettingsButton.setToolTipText("Modifica le impostazioni del gioco");
		SettingsButton.setName("setting");
		SettingsButton.addActionListener(e->mainFrame.setupNetworkGame());
		SettingsButton.setIcon(new ImageIcon(SettingButtonIcon.getImage().getScaledInstance(otherWidth
				, -1, Image.SCALE_SMOOTH)));
		SettingsButton.setBorder(null);
		SettingsButton.setContentAreaFilled(false);
		add(SettingsButton);
		
		Component verticalGlue_SettingsToHelp = Box.createVerticalGlue();
		add(verticalGlue_SettingsToHelp);
	
		JButton HelpButton = new JButton();
		HelpButton.addMouseListener(new MouseOnTheButton());
		HelpButton.setAlignmentX(CENTER_ALIGNMENT);
		HelpButton.setMnemonic(KeyEvent.VK_H);
		HelpButton.setToolTipText("Se ti dovesse servire aiuto");
		HelpButton.setName("help");
		HelpButton.setIcon(new ImageIcon(HelpButtonIcon.getImage().getScaledInstance(otherWidth
				, -1, Image.SCALE_SMOOTH)));
		HelpButton.setBorder(null);
		HelpButton.setContentAreaFilled(false);
		add(HelpButton);
		
		Component verticalGlue_HelpToExit = Box.createVerticalGlue();
		add(verticalGlue_HelpToExit);
		
		JButton ExitButton = new JButton();
		ExitButton.addMouseListener(new MouseOnTheButton());
		ExitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ExitButton.addActionListener(e->showExitConfirm());
		ExitButton.setMnemonic(KeyEvent.VK_E);
		ExitButton.setToolTipText("Abbandona il gioco");
		ExitButton.setName("exit");
		ExitButton.setIcon(new ImageIcon(ExitButtonIcon.getImage().getScaledInstance(otherWidth
				, -1, Image.SCALE_SMOOTH)));
		ExitButton.setBorder(null);
		ExitButton.setContentAreaFilled(false);
		add(ExitButton);
		
		Component verticalGlue_ExitToDown = Box.createVerticalGlue();
		add(verticalGlue_ExitToDown);
	}
	
/**
 *       
 * Classe che permette di associare ad un JButton qualsiasi l'ingrandimento al passaggio 
 * del mouse dello stesso e il ritorno alla dimensione normale quando il puntatore esce dal suo interno.
 *
 */
	private class MouseOnTheButton extends MouseAdapter{

		@Override
		public void mouseEntered(MouseEvent e) {
			if( (e.getSource()) instanceof JButton) {
			JButton button =((JButton) e.getSource() );
				button.setBounds(button.getX()-10, button.getY()-10,
				button.getWidth()+20, button.getHeight()+20);
				String buttonON_Path = "resources/menubutton/Button_Start_ON.png";
				ImageIcon buttonON = new ImageIcon(buttonON_Path);
				if(button.getName() == "start") {
					button.setBorderPainted(true);
					button.setIcon(new ImageIcon(StartButton_ON.getImage().getScaledInstance(startWidth
							, -1, Image.SCALE_SMOOTH)));
					button.setBorder(null);
					button.setContentAreaFilled(false);
				}
				if(button.getName() == "setting") {
					button.setBorderPainted(true);
					button.setIcon(new ImageIcon(SettingButton_ON.getImage().getScaledInstance(otherWidth
							, -1, Image.SCALE_SMOOTH)));
					button.setBorder(null);
					button.setContentAreaFilled(false);
				}
				if(button.getName() == "help") {
					button.setBorderPainted(true);
					button.setIcon(new ImageIcon(HelpButton_ON.getImage().getScaledInstance(otherWidth
							, -1, Image.SCALE_SMOOTH)));
					button.setBorder(null);
					button.setContentAreaFilled(false);
				}
				if(button.getName() == "exit") {
					button.setBorderPainted(true);
					button.setIcon(new ImageIcon(ExitButton_ON.getImage().getScaledInstance(otherWidth
							, -1, Image.SCALE_SMOOTH)));
					button.setBorder(null);
					button.setContentAreaFilled(false);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if( (e.getSource()) instanceof JButton) {
				JButton button =((JButton) e.getSource() );
					if(button.getName() == "start") {
						button.setIcon(new ImageIcon(StartButtonIcon.getImage().getScaledInstance(startWidth
								, -1, Image.SCALE_SMOOTH)));
						button.setBorder(null);
						button.setContentAreaFilled(false);
					}
					if(button.getName() == "setting") {
						button.setIcon(new ImageIcon(SettingButtonIcon.getImage().getScaledInstance(otherWidth
								, -1, Image.SCALE_SMOOTH)));
						button.setBorder(null);
						button.setContentAreaFilled(false);
					}
					if(button.getName() == "help") {
						button.setIcon(new ImageIcon(HelpButtonIcon.getImage().getScaledInstance(otherWidth
								, -1, Image.SCALE_SMOOTH)));
						button.setBorder(null);
						button.setContentAreaFilled(false);
					}
					if(button.getName() == "exit") {
						button.setIcon(new ImageIcon(ExitButtonIcon.getImage().getScaledInstance(otherWidth
								, -1, Image.SCALE_SMOOTH)));
						button.setBorder(null);
						button.setContentAreaFilled(false);
					}
				}
		}
		
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
