package sundeckunical.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditorPanel extends JFrame{

	private JPanel buttonPanel;
	private TablePanel tablePanel;
	private ButtonGroup group;
	private JRadioButton aRadioButton;
	private JRadioButton bRadioButton;
		
	EditorPanel(){
	
		this.setTitle("EditorPanel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension fullScreen = this.getToolkit().getScreenSize();
		this.setSize(fullScreen);
		
		tablePanel=new TablePanel(fullScreen.width/2,fullScreen.height-100);
		tablePanel.setVisible(true);
		
	//inizializzo il pannello contenitore dei pulsanti dell'editor	
		buttonPanel=new JPanel();
		Dimension buttonPanelDimension=new Dimension(fullScreen.width/8,fullScreen.height);
		buttonPanel.setPreferredSize(buttonPanelDimension);
		
	//inserisco tutti i pulsanti del tablePanel nel buttonPanel	
		buttonPanel.add(tablePanel.getButtonPanel().getSmall_map());
		buttonPanel.add(tablePanel.getButtonPanel().getNormal_map());
		buttonPanel.add(tablePanel.getButtonPanel().getLarge_map());
		buttonPanel.add(tablePanel.getButtonPanel().getRemove_Row());
		buttonPanel.add(tablePanel.getButtonPanel().getRemove_All());
	
	//inizializzo e inserisco nel buttonPanel i RadioButton
		this.group = new ButtonGroup();
		this.aRadioButton = new JRadioButton("Ostacolo1");
		aRadioButton.setName("Ostacolo1");
		this.bRadioButton = new JRadioButton("Ostacolo2");
		bRadioButton.setName("Ostacolo2");
		
		buttonPanel.add(aRadioButton);
		group.add(aRadioButton);
		buttonPanel.add(bRadioButton);
		group.add(bRadioButton);
		
	
		aRadioButton.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent changEvent) {
			        AbstractButton aButton = (AbstractButton)changEvent.getSource();
			        ButtonModel aModel = aButton.getModel();
			        boolean armed = aModel.isArmed();
			        boolean pressed = aModel.isPressed();
			        boolean selected = aModel.isSelected();
			        
			        if(pressed)
			        	tablePanel.setIdObject(""+aButton.getName());
			       
			      }
			    }
		);
		
		bRadioButton.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent changEvent) {
			        AbstractButton aButton = (AbstractButton)changEvent.getSource();
			        ButtonModel aModel = aButton.getModel();
			        boolean armed = aModel.isArmed();
			        boolean pressed = aModel.isPressed();
			        boolean selected = aModel.isSelected();
			        
			        if(pressed)
			        	tablePanel.setIdObject(""+aButton.getName());
			        
			      }
			    }
		);
	   
	    
	//imposto il pulsante Close per chiudere l'applicazione e subito dopo lo aggiungo al buttonPanel
		tablePanel.getButtonPanel().getClose().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablePanel.saveTable();
				dispose();				
			}
		});
		buttonPanel.add(tablePanel.getButtonPanel().getClose());
		
		buttonPanel.setOpaque(true);
		buttonPanel.setBackground(Color.gray);
		buttonPanel.setVisible(true);
		
		
		this.add(tablePanel,BorderLayout.CENTER);
		this.add(buttonPanel,BorderLayout.WEST);
		
		
		
	    //this.setUndecorated(true);
		this.setVisible(true);
	}
		
	public static void main(String [] args){
		new EditorPanel();
	}
	
}
