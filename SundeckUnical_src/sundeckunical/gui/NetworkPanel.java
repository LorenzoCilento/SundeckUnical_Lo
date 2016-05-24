package sundeckunical.gui;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NetworkPanel extends JPanel {
	private JDialog dialog;

	private final JTextField ipTextField;

	private final MainFrame mainFrame;

	private final JTextField nameTextField;

	private final JTextField portTextField;

	public NetworkPanel(final MainFrame mainFrame, JDialog dialog) {
		super(new BorderLayout());
		this.mainFrame = mainFrame;
		this.dialog = dialog;
		final JPanel content = new JPanel(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(20, 20, 20, 20);
		constraints.gridx = 0;
		constraints.gridy = 0;
		content.add(new JLabel("Server IP"), constraints);
		ipTextField = new JTextField(20);
		ipTextField.setText("127.0.0.1");
		constraints.gridx++;
		content.add(ipTextField, constraints);
		constraints.gridy++;
		constraints.gridx = 0;
		content.add(new JLabel("Server Port"), constraints);
		portTextField = new JTextField(20);
		portTextField.setText("2727");
		constraints.gridx++;
		content.add(portTextField, constraints);
		constraints.gridy++;
		constraints.gridx = 0;
		content.add(new JLabel("Name"), constraints);
		nameTextField = new JTextField(20);
		nameTextField.setText("pippo");
		constraints.gridx++;
		content.add(nameTextField, constraints);
		constraints.gridy++;
		constraints.gridx = 0;

		final JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					connectoToServer();
					connectButton.setEnabled(false);
				} catch (final Exception e1) {
					JOptionPane.showMessageDialog(connectButton,
							"Impossible to connect to " + ipTextField.getText()
									+ ":" + portTextField.getText(),
							"Network Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		content.add(connectButton, constraints);
		add(content, BorderLayout.CENTER);
	}

	protected void connectoToServer() throws Exception {
		final Socket socket = new Socket(ipTextField.getText(),
				Integer.parseInt(portTextField.getText()));
		final NetworkManager networkManager = new NetworkManager(
				socket, nameTextField.getText(), mainFrame);
		new Thread(networkManager, "Connection Manager").start();
		dialog.dispose();
	}
}
