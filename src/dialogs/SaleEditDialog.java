
package dialogs;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.*;

import myLibrary.Methods;
import basic.*;
import editPanels.SaleEditPanel;


public class SaleEditDialog extends JDialog {
	
	JPanel mainPanel = new JPanel();
		JPanel leftBtnPanel = new JPanel();
			JButton okBtn = new JButton( "OK" );
			JButton cancelBtn = new JButton( "Cancel" );
			JButton expandBtn = new JButton( ">>>" );
		JPanel rightBtnPanel = new JPanel();
			JButton removeBtn = new JButton( "Remove" );
		SaleEditPanel sep;
	
	boolean hitCancel = false;
	
	public void build(){
		
		okBtn.setOpaque( false );
		cancelBtn.setOpaque( false );
		okBtn.addActionListener( new BtnListener() );
		cancelBtn.addActionListener( new BtnListener() );
		expandBtn.addActionListener( new BtnListener() );
		leftBtnPanel.add( okBtn );
		leftBtnPanel.add( cancelBtn );
		leftBtnPanel.add( expandBtn );
		leftBtnPanel.setBorder( BorderFactory.createEmptyBorder( 5,10,10,10 ) );
		leftBtnPanel.setOpaque( false );
		
		removeBtn.addActionListener( new BtnListener() );
		rightBtnPanel.add( removeBtn );
		rightBtnPanel.setBorder( BorderFactory.createEmptyBorder( 5,10,10,10 ) );
		rightBtnPanel.setOpaque( false );
		
		//sep = new SaleEditPanel( sale );
		sep = new SaleEditPanel();
		sep.setOpaque( false );
		sep.setBorder( BorderFactory.createEmptyBorder( 10,10,0,10 ) );
		
		
		mainPanel.setLayout( new BorderLayout() );
		sep.leftPanel.add( leftBtnPanel , BorderLayout.SOUTH );
		sep.rightPanel.add( rightBtnPanel , BorderLayout.SOUTH );
		mainPanel.add( sep , BorderLayout.CENTER );
		this.add( mainPanel );
		
		this.setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
		this.addWindowListener( new MyWindowListener() );
		
		this.setLocationRelativeTo( null );
		pack();
		
		
	}
	
	public SaleEditDialog( ArrayList<Product> products , String title ){
		
		this( new Sale(products) , title );
		
	}
	public SaleEditDialog( Sale sale , String title ) {
		
		build();
		
		sep.setSale( sale );
		this.setTitle( title );
		
	}
	public SaleEditDialog( Sale sale , ArrayList<Product> productsToAdd , String title ){
		
		build();
		
		sep.setSale( sale , productsToAdd );
		expandPushed();
		
		this.setTitle( title );
		
	}
	
	public Sale getSale(){
		
		if( hitCancel )
		{
			return null;
		}
		else
		{
			return sep.getSale();
		}
		
		
	}
	
	public void okPushed(){
		
		if( sep.isValidInput() )
		{
			dispose();
		}
		
	}
	public void cancelPushed(){
		
		hitCancel = true;
		dispose();
		
	}
	public void expandPushed(){
		
				
		
		sep.toggleExpand();
		
		if( sep.isExpanded() )
		{
			expandBtn.setText( "<<<" );
			int w = 700;
			int h = this.getHeight();
			this.setSize( w , h );
		}
		else
		{
			expandBtn.setText( ">>>" );
			int w = 419;
			int h = this.getHeight();
			this.setSize( w , h );
		}
		
	}
	public void removePushed(){
		
		sep.removeSelected();
		
	}
	
	private class BtnListener implements ActionListener{

		
		public void actionPerformed( ActionEvent ae ) {
			
			if( ae.getSource() == okBtn )
			{
				okPushed();
			}
			else if( ae.getSource() == cancelBtn )
			{
				cancelPushed();			
			}
			else if( ae.getSource() == expandBtn )
			{
				expandPushed();			
			}
			else if( ae.getSource() == removeBtn )
			{
				removePushed();			
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
	
	
	public static Sale showDialog( Sale sale , String title ){
		
		SaleEditDialog dialog = new SaleEditDialog( sale , title );
		
		dialog.setLocationRelativeTo( null );
		dialog.setModal( true );
		dialog.setVisible( true );
		
		
		
		while( dialog.isVisible() )
		{
			
		}
		
		try
		{
			return dialog.getSale();
		} 
		catch ( NumberFormatException ex )
		{
			//will never happen since BtnListener for the okBtn will stop it first
			ex.printStackTrace();
			return null;
		} 
		
		
		
	}
	public static Sale showDialog( ArrayList<Product> products , String title ){
		
		return showDialog( new Sale(products) , title );
		
	}
	public static Sale showDialog( Sale sale , ArrayList<Product> productsToAdd , String title ){
		
		SaleEditDialog dialog = new SaleEditDialog( sale , productsToAdd , title );
		
		dialog.setLocationRelativeTo( null );
		dialog.setModal( true );
		dialog.setVisible( true );
		
		
		
		while( dialog.isVisible() )
		{
			
		}
		
		try
		{
			return dialog.getSale();
		} 
		catch ( NumberFormatException ex )
		{
			//will never happen since BtnListener for the okBtn will stop it first
			ex.printStackTrace();
			return null;
		} 
		
		
	}
	
	public static void main( String[] args ) {
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		Sale dummy = new Sale( new Product(new Purchase( "" , 100 , "" , 100 , new GregorianCalendar(1,1,1) , new GregorianCalendar(1,1,1) , "" )) );
		SaleEditDialog.showDialog( dummy , "Edit Sale" );
		
	}
	
	
	
}
