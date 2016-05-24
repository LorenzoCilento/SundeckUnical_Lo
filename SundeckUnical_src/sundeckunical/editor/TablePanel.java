package sundeckunical.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.CellEditor;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class TablePanel extends JPanel{
 	
	private static int row_smallMap=6000;
	private static int row_normalMap=8000;
	private static int row_largeMap=12000;
	
	private JTable table;                // Tabella
	private MyTableModel model;          // Model personalizzato della tabella
	private int corsie;
	private EditorButton buttons;
	
	private String IdObject; 
	private int selectedWidthMap;
	
	public TablePanel(final int width,final int height) {
		
		this.corsie=3;
		this.IdObject="";
		this.selectedWidthMap=0;
	// Pannello contenitore della tabella	
		this.buttons=new EditorButton();
		this.setOpaque(true);
	    this.setBackground(Color.WHITE);
	    
    // Definizione del model della tabella e
    // delle rispettive colonne
	    model = new MyTableModel();
	    model.addColumn("Lunghezza_Corsie");
	    for( int i=1; i<=corsie; i++ )
	    	model.addColumn("Corsia"+i);
	   
	    table = new JTable(model);
	    table.setDefaultRenderer(Object.class, new MyTableCellRenderer());
	  
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setOpaque(true);
	    scrollPane.setBackground(Color.WHITE);
	    table.setBackground(Color.WHITE);
	    
    // Dimensione fissata dello scrollpane
	    scrollPane.setPreferredSize(new Dimension(width,height));
	    scrollPane.setMaximumSize(new Dimension(width,height));
	    
    // Aggiunta scroll pane
	    this.add(scrollPane);
	    
	// Eventi sulla JTable; click del mouse
	    table.addMouseListener(new MouseAdapter() {
	      public void mouseReleased(MouseEvent me) {
	        int row = table.getSelectedRow();
	        int col = table.getSelectedColumn();
	        
	        model.setValueAt(IdObject, row, col);
	       
	      }
	    });
	  
	    
	// Click sul bottone di creazione di una small_map
	    buttons.getSmall_map().addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	  model.removeAllRow();
	    	  setSelectedWidthMap(row_smallMap);
	    	  for(int i=0; i<row_smallMap; i++)
	    		  model.addRow(createRow());
	      }
	    });
	    
	 // Click sul bottone di creazione di una normal_map    
	    buttons.getNormal_map().addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ae) {
		    	  model.removeAllRow();
		    	  setSelectedWidthMap(row_normalMap);
		    	  for(int i=0; i<row_normalMap; i++)
		    		  model.addRow(createRow());
		      }
		});    

	// Click sul bottone di creazione di una large_map    
	    buttons.getLarge_map().addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent ae) {
		    	  model.removeAllRow();
		    	  setSelectedWidthMap(row_largeMap);
		    	  for(int i=0; i<row_largeMap; i++)
		    		  model.addRow(createRow());
		      }
		});    
	    
	    
	// Rimuovo la riga selezionata
	    buttons.getRemove_Row().addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        int index = table.getSelectedRow();
	        if(index < 0) return;
	        model.removeRow(index);
	      }
	    });
	    
	// Rimuovo tutte le righe
	    buttons.getRemove_All().addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	model.removeAllRow();       
	      }
	    });			    
	    		    	        
	}//end Constructor
	
  // Creo una riga vuota; ogni elemento e' una cella
    private Vector<String> createRow() {
	    Vector<String> row = new Vector<String>();
	    row.add("");
	    row.add("");
	    row.add("");
	    row.add("");
	    return row;
	}

    public TablePanel getTablePanel(){
    	return this;
    }
    
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public MyTableModel getModel() {
		return model;
	}

	public void setModel(MyTableModel model) {
		this.model = model;
	}

	public EditorButton getButtonPanel() {
		return buttons;
	}

	public void setButtonPanel(EditorButton buttonPanel) {
		this.buttons = buttonPanel;
	}
	
	public String getIdObject() {
		return IdObject;
	}

	public void setIdObject(String idObject) {
		IdObject = idObject;
	}
	
	public int getSelectedWidthMap() {
		return selectedWidthMap;
	}

	public void setSelectedWidthMap(int selectedWidthMap) {
		this.selectedWidthMap = selectedWidthMap;
	}

	public void saveTable(){
		try{
			final PrintWriter pw;
			pw = new PrintWriter("resources/file/editor.txt");
			pw.append(""+3 + "#"+9000+"#"+""+getSelectedWidthMap());
			pw.println();
			for( int i=0; i<model.getRowCount(); i++ )
				for( int j=1; j<corsie; j++ ){
					if( model.getValueAt(i, j)!="" ){
						pw.append(""+ i + "_" + model.getValueAt(i,j).toString() + j);
			            pw.println();
					}
				}
			pw.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
    
}
