
package editPanels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.*;

import basic.Purchase;
import date.CalendarDialog;
import exceptions.*;


public class PurchaseEditPanel extends JPanel {
	
	
	
	
	JLabel productNameLabel = new JLabel( "Product" );
	JLabel quantityLabel = new JLabel( "Quantity" );
	JLabel categoryLabel = new JLabel( "Category" );
	JLabel purchasePriceLabel = new JLabel( "Total Cost" );
	JLabel purchaseDateLabel = new JLabel( "Purchase Date" );
	JLabel sellerLabel = new JLabel( "Seller" );
	JLabel purchaseIdLabel = new JLabel( "Purchase ID" );
	JLabel groupedLabel = new JLabel( "Grouped" );
	
	public JComboBox productNameBox = new JComboBox();
	JSpinner quantitySpinner = new JSpinner( new SpinnerNumberModel( 1 , 1 , 1000 , 1 ) );
	JComboBox categoryBox = new JComboBox();
	JTextField purchasePriceField = new JTextField( 10 );
	JPanel purchaseDatePanel = new JPanel();
		JLabel purchaseDateField = new JLabel();
		JButton editPurchaseDateBtn = new JButton("...");
	JTextField sellerField = new JTextField( 10 );
	JTextField purchaseIdField = new JTextField( 10 );
	JCheckBox groupedBox = new JCheckBox();
	
	JComponent[][] components = { { productNameLabel , quantityLabel   , categoryLabel , purchasePriceLabel , purchaseDateLabel , sellerLabel , purchaseIdLabel , groupedLabel } , 
								  {	productNameBox   , quantitySpinner , categoryBox   , purchasePriceField , purchaseDatePanel , sellerField , purchaseIdField , groupedBox   } };
	
	
	
	
	Purchase purchase;
	
	public PurchaseEditPanel( Purchase purchase , String[] productNameList , String[] categoryList ) {
		
		build( productNameList , categoryList );
		set( purchase );
		
		
		
		
	}
	public void build( String[] productNameList , String[] categoryList ){
		
				
		for( int i=0; i<productNameList.length; i++ )
		{
			productNameBox.addItem( productNameList[i] );
		}
		productNameBox.setEditable( true );
		
		
		for( int i=0; i<categoryList.length; i++ )
		{
			categoryBox.addItem( categoryList[i] );
		}
		categoryBox.setEditable( true );
		
		JSpinner.NumberEditor field = (JSpinner.NumberEditor) quantitySpinner.getEditor();
		JFormattedTextField blah = field.getTextField();
		blah.setHorizontalAlignment( SwingConstants.CENTER );
		
		purchasePriceField.setFont( purchaseDateField.getFont() );
		purchasePriceField.setHorizontalAlignment( SwingConstants.CENTER );
		purchaseDatePanel.add( purchaseDateField );
		purchaseDatePanel.add( editPurchaseDateBtn );
		purchaseDatePanel.setOpaque( false );
		editPurchaseDateBtn.setMargin( new Insets(0,0,0,0) );
		editPurchaseDateBtn.setOpaque( false );
		purchaseDateField.setBorder( sellerField.getBorder() );
		purchaseDateField.setBackground( Color.white );
		purchaseDateField.setOpaque( true );
		purchaseDateField.setHorizontalAlignment( SwingConstants.CENTER );
		sellerField.setHorizontalAlignment( SwingConstants.CENTER );
		purchaseIdField.setHorizontalAlignment( SwingConstants.CENTER );
		
		
		editPurchaseDateBtn.addActionListener( new EditPurchaseDateListener() );
		
		setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		 
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets( 5 , 5 , 5 , 5 );
		
		for( int x=0; x<components.length; x++ )
		{
			for( int y=0; y<components[0].length; y++ )
			{
				c.gridx = x;
				c.gridy = y;
				
				c.insets = new Insets( 5 , 5 , 5 , 5 );
				if( components[x][y] == purchaseDatePanel )
				{
					c.insets = new Insets( 0 , 0 , 0 , 0 );
				}
				
				c.fill = GridBagConstraints.HORIZONTAL;
				if( components[x][y] == quantitySpinner  )
				{
					c.fill = GridBagConstraints.NONE;
				}
				
						
				add( components[x][y] , c );
			}
		}
		
		//System.out.println( productNameBox.getPreferredSize() );
		
		int h = purchasePriceField.getPreferredSize().height + 2;
		int w = productNameBox.getPreferredSize().width - h - editPurchaseDateBtn.getMargin().left - editPurchaseDateBtn.getMargin().right;
		
		purchaseDateField.setPreferredSize( new Dimension( w , h-2 ) );
		editPurchaseDateBtn.setPreferredSize( new Dimension( h , h ) );
		quantitySpinner.setPreferredSize( new Dimension( 40 , quantitySpinner.getPreferredSize().height ) );
		
		
//		productNameBox.setPreferredSize( new Dimension(114,26) );
//		quantitySpinner.setPreferredSize( new Dimension(40,26) );
//		purchasePriceField.setPreferredSize( new Dimension(40,26) );
//		sellerField.setPreferredSize( new Dimension(40,26) );
//		purchaseDateField.setPreferredSize( new Dimension(82,26) );
//		editPurchaseDateBtn.setPreferredSize( new Dimension(26,25) );
		
		
		
		
		
		
		
		
	}
	
	
	public void setLabelForeground( Color color ){
		
		for( int i=0; i<components[0].length; i++ )
		{
			components[0][i].setForeground( Color.white );
		}
		
	}
	
	
	
	public void set( Purchase purchase ) {
		
		this.purchase = new Purchase( purchase );
		setProductName( purchase.getProductName() );
		setQuantity( purchase.getQuantity() );
		setCategory( purchase.getCategory() );
		setPurchasePrice( purchase.getTotalPurchasePrice() );
		setPurchaseDate( purchase.getPurchaseDate() );
		setSeller( purchase.getSeller() );
		setPurchaseID( purchase.getPurchaseID() );
		setGrouped( purchase.isGrouped() );
		
	}
	private void setProductName( String name ){
		
		//productNameBox.addItem( name );
		
		
		productNameBox.setSelectedItem( name );
		
		if( productNameBox.getSelectedIndex() == -1 )
		{
			productNameBox.addItem( name );
			productNameBox.setSelectedItem( name );
		}
		
		
	}
	private void setQuantity( int quantity ){
		
		quantitySpinner.setValue( quantity );
		
	}
	private void setCategory( String category ){
		
		categoryBox.setSelectedItem( category );
		if( categoryBox.getSelectedIndex() == -1 )
		{
			categoryBox.addItem( category );
			categoryBox.setSelectedItem( category );
		}
		
	}
	private void setPurchasePrice( double purchasePrice ){
		
		String text = new DecimalFormat("$#,##0.00").format( purchasePrice );
		purchasePriceField.setText( text );
		
	}
	private void setPurchaseDate( GregorianCalendar purchaseDate ){
		
		String text = new SimpleDateFormat("MM/dd/yy").format( purchaseDate.getTime() );
		purchaseDateField.setText( text );
		
	}
	private void setSeller( String seller ){
		
		sellerField.setText( seller );
		
	}
	private void setPurchaseID( String purchaseID ){
		
		this.purchaseIdField.setText( purchaseID );
		
	} 
	private void setGrouped( boolean grouped ){
		
		groupedBox.setSelected( grouped );
		
	}
	
	public Purchase getPurchase(){
		
		return this.purchase;
		
	}
	
	public void applyFields() throws RemovingSoldProductException, NumberFormatException, BlankProductNameException {
		
		String newProductName = getProductName();
		purchase.setProductName( newProductName );
		
		int newQuantity = getQuantity();
		purchase.setQuantity( newQuantity );
		
		String newCategory = getCategory();
		purchase.setCategory( newCategory );
		
		double newPurchasePrice = getPurchasePrice();
		purchase.setPurchasePrice( newPurchasePrice );
		
		//purchase date taken care of by listener
		
		String newSeller = this.getSeller();
		purchase.setSeller( newSeller );
		
		String newPurchaseID = this.getPurchaseID();
		purchase.setPurchaseID( newPurchaseID );
		
		boolean newGrouped = this.getGrouped();
		purchase.setGrouped( newGrouped );
		
	}
	private String getProductName() throws BlankProductNameException {
		
		String productName = (String) productNameBox.getSelectedItem();
		if( productName.isEmpty() )
		{
			throw new BlankProductNameException( "Product may not have a blank name" );
		}
		
		return productName;
		
	}
	private int getQuantity(){
		
		return (Integer)quantitySpinner.getValue();
		
	}
	private String getCategory(){
		
		String category = (String) categoryBox.getSelectedItem();
		return category;
		
	}
	private double getPurchasePrice() throws NumberFormatException {
		
		String inputText = purchasePriceField.getText();
		
		inputText = inputText.replace( "$" , "" );
		inputText = inputText.replace( "," , "" );
		
		return Double.parseDouble( inputText );
		
	}
	private String getSeller(){
		
		return sellerField.getText();
		
	}
	private String getPurchaseID(){
		
		return purchaseIdField.getText();
		
	}
	private boolean getGrouped(){
		
		return groupedBox.isSelected();
		
	}
	
	
	private class EditPurchaseDateListener implements ActionListener {

		
		public void actionPerformed( ActionEvent ae ) {
			
			GregorianCalendar newPurchaseDate = CalendarDialog.showDialog( purchase.getPurchaseDate() , "Purchase Date" );
			
			try
			{
				purchase.setPurchaseDate( newPurchaseDate );
				setPurchaseDate( newPurchaseDate );
				
			}
			catch( PurchaseAfterReceivedException ipde )
			{
				JOptionPane.showMessageDialog( null , "Purchase date cannot be after received date" );
			}
			
			
				
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}








