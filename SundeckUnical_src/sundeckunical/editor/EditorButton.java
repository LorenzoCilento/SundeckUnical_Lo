package sundeckunical.editor;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;



class EditorButton {
	
    // Pulsanti
	private JButton small_map,
					normal_map,
					large_map,
					remove_Row,
					remove_All,
					close;
	
	public EditorButton(){
		
		small_map = new JButton("Small_Map");
	    normal_map = new JButton("Normal_Map");
	    large_map = new JButton("Large_Map");
	    remove_Row = new JButton("Remove_Row");
	    remove_All = new JButton("Remove_All");
	    close = new JButton("Close");
	}

	public JButton getSmall_map() {
		return small_map;
	}

	public void setSmall_map(JButton small_map) {
		this.small_map = small_map;
	}

	public JButton getNormal_map() {
		return normal_map;
	}

	public void setNormal_map(JButton normal_map) {
		this.normal_map = normal_map;
	}

	public JButton getLarge_map() {
		return large_map;
	}

	public void setLarge_map(JButton large_map) {
		this.large_map = large_map;
	}

	public JButton getRemove_Row() {
		return remove_Row;
	}

	public void setRemove_Row(JButton remove_Row) {
		this.remove_Row = remove_Row;
	}

	public JButton getRemove_All() {
		return remove_All;
	}

	public void setRemove_All(JButton remove_All) {
		this.remove_All = remove_All;
	}
	
	public JButton getClose(){
		return close;
	}

	public void setClose(JButton close) {
		this.close = close;
	}
	
}
