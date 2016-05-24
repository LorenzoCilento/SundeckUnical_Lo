
package sundeckunical.gui;


import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import sundekunical.gui.ui3d.GamePanel3d;
import sundeckunical.core.GameManager;


public class MainFrame extends JFrame {
	
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private SettingsPanel settingsPanel;
	
	static int MENU_PANEL = 0;
	static int GAME_PANEL = 1;
	static int GAME_PANEL_3D = 2;
	
	public MainFrame() {
		setTitle("SunDeckUnical");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				showExitConfirm();
			}
		});
		menuPanel=new MenuPanel(this);
//		gamePanel=new GamePanel(this);
		settingsPanel = new SettingsPanel(this);
		setContentPane(menuPanel);
		
		
	//imposto l'icona del gioco e lo sfondo
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		Image iconImage= kit.getImage("Resources/img/UNICAL.jpg");
		setIconImage(iconImage);
		
		//setUndecorated(true);
		pack();
		setLocationRelativeTo(null);
	}

	public void showMenu(final int panel){
		SwingUtilities.invokeLater(()->{
			switch(panel){
			case 0 : 
				setContentPane(menuPanel);
				pack();
				setLocationRelativeTo(null);
				menuPanel.updateUI();
				break;
			case 1:
				setContentPane(gamePanel);
				pack();
				setLocationRelativeTo(null);
				gamePanel.updateUI();
				break;
			}
			
		});
	}
	
	public static void main(String[] args) {
		
		MainFrame frame = new MainFrame();
		frame.setVisible(true);

	}
	
	private void start() {
			gamePanel.requestFocus();
			gamePanel.start();	
	}
		
	public void startGame() {
		gamePanel = new GamePanel(this);
		SwingUtilities.invokeLater(()->{
			setContentPane(gamePanel);
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			pack();
			setLocationRelativeTo(null);
			start();
			gamePanel.updateUI();
		});
	}
	
	public void showSettingPanel() {
		SwingUtilities.invokeLater(()->{
			setContentPane(settingsPanel);
			pack();
			setLocationRelativeTo(null);
		});
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	private void showExitConfirm() {
		int option = JOptionPane.showConfirmDialog(MainFrame.this,
				"Do you really want to exit?", "Exit",
				JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	void setupNetworkGame() {
		JDialog dialog = new JDialog(this);
		dialog.setModal(true);
		dialog.setContentPane(new NetworkPanel(this, dialog));
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);

		SwingUtilities.invokeLater(() -> setContentPane(new JLabel(
				"Please wait...")));
	}

	GameManager startNetworkGame(NetworkManager networkManager) {
		gamePanel = new GamePanel(this);
		SwingUtilities.invokeLater(() -> {
			setContentPane(gamePanel);
			gamePanel.requestFocus();
			gamePanel.updateUI();
		});
		return gamePanel.startNetwork(networkManager);
	}

}
