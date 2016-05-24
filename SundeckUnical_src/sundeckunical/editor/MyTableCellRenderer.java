package sundeckunical.editor;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;


public class MyTableCellRenderer extends JLabel implements TableCellRenderer {
  
  MyTableCellRenderer() {
    setOpaque(true);
  }

  
  public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected,boolean hasFocus,int row,int column) {
    if((row % 2) == 0) setBackground(new Color(224,249,231,255));
    else setBackground(Color.WHITE);
    
    // Tutto centrato
    setVerticalAlignment(CENTER);
    setHorizontalAlignment(CENTER);
    
    // Occupiamoci ora del contenuto delle celle
    if(value != null) {
      setText((String) value);
      
      // Verifichiamo se e' la colonna 0
      if(column == 0) {
        setText(String.valueOf(row));
        setForeground(Color.BLACK);
        setFont(new Font("Helvetica",Font.BOLD,14));
        setBackground(Color.LIGHT_GRAY);
        setBorder(null);
        return this;
      } // Settandolo a null utilizzo il font standard
      else { 
        setFont(null);
      }
    }
    
    // Se la cella e' clickata setto lo stesso colore dato inizialmente,
    // e se e' la riga del colore differente utilizzo il canale Alpha
    // per rendere piu' chiara la cella
    if(hasFocus) {
      if(row %2 == 0) setBackground(new Color(224, 249, 231,100));
      setBorder(BorderFactory.createRaisedSoftBevelBorder());
    } else {
      setBorder(null);
    }
    
    return this;
  }
}