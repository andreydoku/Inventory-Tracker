
package dialogs;

import basic.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import myLibrary.Methods;


public class DialogTest extends JFrame {
	
	
	Purchase purchase = new Purchase( "iPhone" , 3 , "" , 400 , (GregorianCalendar)GregorianCalendar.getInstance() , "some seller" );
	JTextArea textArea = new JTextArea(10,10);
	JButton btn = new JButton( "Edit" );
	
	public DialogTest(){
		
		this.setSize( 500,500 );
		this.setLocationRelativeTo( null );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		
		
		this.add( textArea , BorderLayout.CENTER );
		this.add( btn , BorderLayout.NORTH );
		
		btn.addActionListener( new BtnListener() );
		
		textArea.setText( purchase.toString() );
		
		this.setVisible( true );
		
	}
	
	private class BtnListener implements ActionListener {
		
		public void actionPerformed( ActionEvent ae ) {

			purchase = PurchaseEditDialog.showDialog( purchase , new String[0] , Purchase.getBasicCategoryList() , "" );
			textArea.setText( purchase.toString() );
			textArea.repaint();
			
		}
		
		
	}
	
	
	public static void main( String[] args ) {
		
		new DialogTest();
		
		
	}
	
	
		
		
		
	
	
	
	
	
}
