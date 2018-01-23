
package panels;

import gui.CustomToggleButton;
import gui.GradientPanel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import myLibrary.Methods;
import table.MyTable;
import tables.ProductTable;
import basic.Product;


public class ProductPanel extends GradientPanel {
	
	JPanel btnPanel = new JPanel();
		JButton sellBtn = new JButton();
		JButton viewPurchaseBtn = new JButton();
		JButton viewSaleBtn = new JButton();
		JComboBox statusFilterBox = new JComboBox();
		public CategoryFilterPanel categoryFilterPanel = new CategoryFilterPanel();
			
			
			
	JScrollPane tablePanel = new JScrollPane();
		public ProductTable productTable = new ProductTable();
	
	
	
		
	public ProductPanel(){
		
		
		build();
		
	}
	public void build(){
		
		String[] statuses = { "All" , "Ord. & Rec." , "Ordered" , "Received" , "Sold" };
		statusFilterBox = new JComboBox( statuses );
		statusFilterBox.setBorder( null );
		
		
		
		
		//tablePanel = MyTable.createStripedJScrollPane( productTable );
		tablePanel = new JScrollPane(){
			
			public Dimension getPreferredSize(){
				
				return productTable.getMyPreferredSize( ProductPanel.this.getWidth() , ProductPanel.this.getHeight() );
				
			}
		
		
		};
		tablePanel.setCorner( JScrollPane.UPPER_RIGHT_CORNER , MyTable.createCornerComponent( productTable ) );
		tablePanel.setViewportView( productTable );
		tablePanel.getViewport().setOpaque( false );
		tablePanel.setOpaque( false );
		
		//tablePanel.getViewport().putClientProperty("EnableWindowBlit", Boolean.TRUE );
		
		
		productTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		productTable.setFillsViewportHeight( false );
		//productTable.setFillsViewportWidth( true );
		
		//productTable.getTableHeader().addMouseListener( new ColumnPopupListener( productTable ) );
		
		
		
		
		//this.setOpaque( false );
		productTable.setOpaque( false );
		
		btnPanel.setOpaque( false );
		sellBtn.setOpaque( false );
		viewPurchaseBtn.setOpaque( false );
		viewSaleBtn.setOpaque( false );
		statusFilterBox.setOpaque( false );
		
		
		
		int h = 27;
		//sellBtn.setPreferredSize( new Dimension( sellBtn.getPreferredSize().width , h+2 ) );
		//viewPurchaseBtn.setPreferredSize( new Dimension( viewPurchaseBtn.getPreferredSize().width , h+2 ) );
		//viewSaleBtn.setPreferredSize( new Dimension( viewSaleBtn.getPreferredSize().width , h+2 ) );
		statusFilterBox.setPreferredSize( new Dimension( statusFilterBox.getPreferredSize().width , h ) );
		categoryFilterPanel.setPreferredWidthButtons( h );
		
		
		btnPanel.add( sellBtn );
		btnPanel.add( viewPurchaseBtn );
		btnPanel.add( viewSaleBtn );
		btnPanel.add( statusFilterBox );
		btnPanel.add( categoryFilterPanel );
		
		
		tablePanel.setBorder( BorderFactory.createEmptyBorder() );
		
		
		
		setLayout( new BorderLayout() );
		add( btnPanel , BorderLayout.NORTH );
		add( tablePanel , BorderLayout.WEST );
		
		
		
		
		
	}
	
	
	
	public void addProducts( ArrayList<Product> products ){
		
		productTable.addProducts( products );
		
	}
	public void setProducts( ArrayList<Product> products ){
		
		productTable.setProducts( products );
		
	}
	
	
	
	public class CategoryFilterPanel extends JPanel {
		
		
		public CustomToggleButton allBtn = new CustomToggleButton( "All" );
		public CustomToggleButton baseButton = new CustomToggleButton( "Base" );
		public CustomToggleButton modBtn = new CustomToggleButton( "Mod" );
		public CustomToggleButton accessoryBtn = new CustomToggleButton( "Accessory" );
		public CustomToggleButton extraBtn = new CustomToggleButton( "Exra" );
		public CustomToggleButton otherBtn = new CustomToggleButton( "Other" );
		
		CustomToggleButton[] buttons = { allBtn , baseButton , modBtn , accessoryBtn , extraBtn , otherBtn };
		ButtonGroup buttonGroup = new ButtonGroup();
		
		public CategoryFilterPanel(){
			
			allBtn.setSelected( true );
			this.setOpaque( false );
			this.setLayout( new FlowLayout(FlowLayout.CENTER , 0,0) );
			
			for( int i=0; i<buttons.length; i++ )
			{
				buttons[i].setColumnHeader( true, i==0 );
				
				
				buttons[i].setFocusable( false );
				this.add( buttons[i] );
				buttonGroup.add( buttons[i] );
			}
			
			
			
			
		}
		
		public void addActionListenerToButtons( ActionListener listener ){
			
			for( int i=0; i<buttons.length; i++ )
			{
				buttons[i].addActionListener( listener );
			}
			
		}
		public void setPreferredWidthButtons( int h ){
			
			for( int i=0; i<buttons.length; i++ )
			{
				int w = buttons[i].getPreferredSize().width;
				buttons[i].setPreferredSize( new Dimension(w,h) );
			}
			
		}
		
		
		
	}
	
	
	
	public static void main( String[] args ) {
		
		JFrame dialog = new JFrame();
		dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		dialog.setSize( 500,400 );
		dialog.add( new ProductPanel() , BorderLayout.NORTH );
		dialog.setLocationRelativeTo( null );
		dialog.setVisible( true );
		
		
		
	}
	
	
	
	
	
}







