
package dialogs;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;

import myLibrary.Methods;
import basic.Purchase;
import editPanels.PurchaseEditPanel;
import exceptions.BlankProductNameException;
import exceptions.RemovingSoldProductException;


public class PurchaseEditDialog extends JDialog {
	
	JPanel mainPanel = new JPanel();
		JPanel btnPanel = new JPanel();
			JButton okBtn = new JButton( "OK" );
			JButton cancelBtn = new JButton( "Cancel" );
		PurchaseEditPanel pep;
	
	boolean hitCancel = false;
	
	
	
	public PurchaseEditDialog( String title ){
		
		this( new Purchase() , new String[0] , Purchase.getBasicCategoryList() ,  title );
		
	}
	public PurchaseEditDialog( Purchase purchase , String[] productNameList , String[] categoryList , String title ) {
		
		okBtn.addActionListener( new BtnListener() );
		cancelBtn.addActionListener( new BtnListener() );
		btnPanel.add( okBtn );
		btnPanel.add( cancelBtn );
		
		pep = new PurchaseEditPanel( purchase , productNameList , categoryList );
		//pep.setLabelForeground( Color.white );
		pep.setBorder( BorderFactory.createEmptyBorder( 10,10,0,10 ) );
		btnPanel.setBorder( BorderFactory.createEmptyBorder( 5,10,10,10 ) );
		
		btnPanel.setOpaque( false );
		pep.setOpaque( false );
		okBtn.setOpaque( false );
		cancelBtn.setOpaque( false );
		
		
		mainPanel.setLayout( new BorderLayout() );
		mainPanel.add( btnPanel , BorderLayout.SOUTH );
		mainPanel.add( pep , BorderLayout.CENTER );
		this.add( mainPanel );
		
		this.setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
		this.addWindowListener( new MyWindowListener() );
		this.setTitle( title );
		this.setLocationRelativeTo( null );
		pack();
		
	}
	
	public Purchase getPurchase() throws NumberFormatException, RemovingSoldProductException, BlankProductNameException {
		
		if( hitCancel )
		{
			return null;
		}
		else
		{
			pep.applyFields();
			return pep.getPurchase();
		}
		
		
	}
	
	public void okPushed(){
		
		try
		{
			
			//make sure inputs are valid
			getPurchase();

			dispose();

		} 
		catch ( BlankProductNameException ex )
		{
			JOptionPane.showMessageDialog( null , "Product name may not be blank" );
		}				
		catch ( NumberFormatException ex )
		{
			JOptionPane.showMessageDialog( null , "Invalid entered price" );
		} 
		catch ( RemovingSoldProductException ex )
		{
			JOptionPane.showMessageDialog( null , "Decreasing quantity would have resulted in the removal of products already sold." );
		}
		
	}
	public void cancelPushed(){
		
		hitCancel = true;
		dispose();
		
	}
	
	
	private class BtnListener implements ActionListener {

		
		public void actionPerformed( ActionEvent ae ) {
			
			if( ae.getSource() == okBtn )
			{
				okPushed();
			}
			if( ae.getSource() == cancelBtn )
			{
				cancelPushed();
			}
			
		}
		
		
		
		
	}
	private class MyWindowListener implements WindowListener {

		
		public void windowOpened( WindowEvent we ) {}
		public void windowClosing( WindowEvent we ) {
			
			cancelPushed();
			
		}
		public void windowClosed( WindowEvent we ) {}
		public void windowIconified( WindowEvent we ) {}
		public void windowDeiconified( WindowEvent we ) {}
		public void windowActivated( WindowEvent we ) {}
		public void windowDeactivated( WindowEvent we ) {}
		
		
		
		
	}
	
	
	public static Purchase showDialog( Purchase purchase , String[] productNameList , String[] categoryList , String title ){
		
		PurchaseEditDialog dialog = new PurchaseEditDialog( purchase , productNameList , categoryList , title );
		dialog.pep.productNameBox.getEditor().selectAll();
		dialog.setLocationRelativeTo( null );
		dialog.setModal( true );
		dialog.setVisible( true );
		
		
		
		while( dialog.isVisible() )
		{
			
		}
		
		try
		{
			return dialog.getPurchase();
		} 
		catch ( NumberFormatException ex )
		{
			//will never happen since BtnListener for the okBtn will stop it first
			ex.printStackTrace();
			return null;
		} 
		catch ( RemovingSoldProductException ex )
		{
			//will never happen since BtnListener for the okBtn will stop it first
			ex.printStackTrace();
			return null;
		}
		catch ( BlankProductNameException ex )
		{
			//will never happen since BtnListener for the okBtn will stop it first
			ex.printStackTrace();
			return null;
		}
		
	}
	public static Purchase showDialog( String[] productNameList , String[] categoryList , String title ){
		
		if( productNameList.length > 0 )
		{
			Purchase purchase = new Purchase();
			purchase.setProductName( productNameList[0] );
			return showDialog( purchase , productNameList , categoryList , title );
		}
		else
		{
			return showDialog( new Purchase() , productNameList , categoryList , title );
		}
		
		
		
	}
	
	
	
	
	
	
	
	
}























