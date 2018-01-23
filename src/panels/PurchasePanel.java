
package panels;

import gui.GradientPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;

import myLibrary.Methods;
import table.MyTable;
import tables.PurchaseTable;
import basic.Product;
import basic.Purchase;


public class PurchasePanel extends GradientPanel {
	
	JPanel btnPanel = new JPanel();
		JButton addBtn = new JButton();
		JButton editBtn = new JButton();
		JButton deleteBtn = new JButton();
		JButton receiveBtn = new JButton();
		JButton viewProductsBtn = new JButton();
	JScrollPane tablePanel = new JScrollPane();
		PurchaseTable purchaseTable = new PurchaseTable();
	
	
		
	public PurchasePanel(){
		
		build();
		
		
		
	}
	public void build(){
		
		
//		ImageIcon addIcon = new ImageIcon( "icons/add 20.png" );
//		addBtn.setIcon( addIcon );
//		
//		ImageIcon editIcon = new ImageIcon( "icons/edit 20.png" );
//		editBtn.setIcon( editIcon );
//		
//		ImageIcon deleteIcon = new ImageIcon( "icons/delete 20.png" );
//		deleteBtn.setIcon( deleteIcon );
//		
//		ImageIcon receiveIcon = new ImageIcon( "icons/receive 20.png" );
//		receiveBtn.setIcon( receiveIcon );
//		
//		ImageIcon viewProductsIcon = new ImageIcon( "icons/view products 20.png" );
//		viewProductsBtn.setIcon( viewProductsIcon );
		
		
		//tablePanel = MyTable.createStripedJScrollPane( purchaseTable );
		tablePanel = new JScrollPane(){
			
			public Dimension getPreferredSize(){
				
				return purchaseTable.getMyPreferredSize( PurchasePanel.this.getWidth() , PurchasePanel.this.getHeight() );
				
			}
		
		
		};
		tablePanel.setCorner( JScrollPane.UPPER_RIGHT_CORNER , MyTable.createCornerComponent( purchaseTable ) );
		tablePanel.setViewportView( purchaseTable );
		tablePanel.getViewport().setOpaque( false );
		tablePanel.setOpaque( false );
		//this.setOpaque( false );
		purchaseTable.setOpaque( false );
		
		
		
		purchaseTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		purchaseTable.setFillsViewportHeight( false );
		
		
		
		//purchaseTable.getTableHeader().addMouseListener( new ColumnPopupListener( purchaseTable ) );
		
		
		
		
		btnPanel.setOpaque( false );
		addBtn.setOpaque( false );
		editBtn.setOpaque( false );
		deleteBtn.setOpaque( false );
		receiveBtn.setOpaque( false );
		viewProductsBtn.setOpaque( false );
		
//		int h = 27;
//		addBtn.setPreferredSize( new Dimension( addBtn.getPreferredSize().width , h+2 ) );
//		editBtn.setPreferredSize( new Dimension( editBtn.getPreferredSize().width , h+2 ) );
//		deleteBtn.setPreferredSize( new Dimension( deleteBtn.getPreferredSize().width , h+2 ) );
//		receiveBtn.setPreferredSize( new Dimension( receiveBtn.getPreferredSize().width , h+2 ) );
//		viewProductsBtn.setPreferredSize( new Dimension( viewProductsBtn.getPreferredSize().width , h+2 ) );
		
		btnPanel.add( addBtn );
		btnPanel.add( editBtn );
		btnPanel.add( deleteBtn );
		btnPanel.add( receiveBtn );
		btnPanel.add( viewProductsBtn );
		
		
		tablePanel.setBorder( BorderFactory.createEmptyBorder() );
		
		
		setLayout( new BorderLayout() );
		add( btnPanel , BorderLayout.NORTH );
		add( tablePanel , BorderLayout.WEST );
		
		
		
	}
	
	public void addPurchase( Purchase purchace ){
		
		purchaseTable.add( purchace );
		
	}
	public ArrayList<Product> getAllProducts(){
		
		return purchaseTable.getAllProducts();
		
	}
	public Purchase getSelectedPurchase(){
		
		
		return purchaseTable.getSelectedPurchase();
		
		
				
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void main( String[] args ) {
		
		JFrame frame = new JFrame();
		frame.setSize( 600 , 300 );
		frame.setLocationRelativeTo( null );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		PurchasePanel pp = new PurchasePanel();
		frame.add( pp );
		frame.setVisible( true );
		
		
	}
	
	
	
	
	
}









