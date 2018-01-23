
package listeners;

import table.MyTable;
import table.MyTableModel;
import java.awt.event.*;



	
	
public class ColumnPopupListener implements MouseListener {
	
	
	MyTable table;
	
	
	public ColumnPopupListener( MyTable table ){
		
		this.table = table;
		
	}

	public void mouseClicked( MouseEvent e ) {


		if ( e.getButton() == MouseEvent.BUTTON3 ) //right click
		{
			MyTableModel model = (MyTableModel) this.table.getModel();
			model.createHideColumnMenu().show(e.getComponent(), e.getX(), e.getY());

		}






	}

	public void mousePressed( MouseEvent e ) {}
	public void mouseReleased( MouseEvent e ) {}
	public void mouseEntered( MouseEvent e ) {}
	public void mouseExited( MouseEvent e ) {}




}
	
	

