
package editPanels;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.*;

import table.MyTable;
import tables.ProductTable;
import basic.*;
import date.CalendarDialog;
import dialogs.SaleEditDialog;
import exceptions.NotReceivedButSoldException;
import exceptions.ReceivedAfterSoldException;


public class SaleEditPanel extends JPanel {
	
	
	public JPanel leftPanel = new JPanel();
	public JPanel fieldsPanel = new JPanel();
	
	
	JLabel salePriceLabel = new JLabel( "Sale Price" );
	JLabel shippingPriceLabel = new JLabel( "Shipping Price" );
	JLabel modPriceLabel = new JLabel( "Mod Price" );
	JLabel purchasePriceLabel = new JLabel( "Purchase Price" );
	JLabel profitLabel = new JLabel( "Revenue" );
	JLabel saleDateLabel = new JLabel( "Sale Date" );
	JLabel buyerLabel = new JLabel( "Buyer" );
	JLabel saleIdLabel = new JLabel( "Sale ID" );
	JLabel trackingNumberLabel = new JLabel( "Tracking #" );
	JLabel ebayLabel = new JLabel( "Ebay" );
	
	JTextField salePriceField = new JTextField( 10 );
	JTextField shippingPriceField = new JTextField( 10 );
	JTextField modPriceField = new JTextField( 10 );
	JTextField purchasePriceField = new JTextField( 10 );
	JTextField profitField = new JTextField( 10 );
	JPanel saleDatePanel = new JPanel();
		JTextField saleDateField = new JTextField();
		JButton editSaleDateBtn = new JButton("...");
	JTextField buyerField = new JTextField( 10 );
	JTextField saleIdField = new JTextField( 10 );
	JTextField trackingNumberField = new JTextField( 10 );
	JCheckBox ebayCheckBox = new JCheckBox();
	
//	JComponent[][] components = { { salePriceLabel , shippingPriceLabel , modPriceLabel , purchasePriceLabel , profitLabel , saleDateLabel , buyerLabel , saleIdLabel , trackingNumberLabel } , 
//								  {	salePriceField , shippingPriceField , modPriceField , purchasePriceField , profitField , saleDatePanel , buyerField , saleIdField , trackingNumberField } };
	
	JComponent[][] components = { { salePriceLabel , shippingPriceLabel , modPriceLabel , purchasePriceLabel , profitLabel } , 
								  {	salePriceField , shippingPriceField , modPriceField , purchasePriceField , profitField } ,
								  { saleDateLabel , buyerLabel , saleIdLabel , trackingNumberLabel , ebayLabel } ,
								  { saleDatePanel , buyerField , saleIdField , trackingNumberField , ebayCheckBox } };
	
	
	
	public JPanel rightPanel = new JPanel();
		JScrollPane tablePanel = new JScrollPane();
			public ProductTable productTable = new ProductTable();
	
	
	private Sale sale;
	private boolean validInput = true;
	private boolean expanded = false;
	
	
	
	public SaleEditPanel(){
		
		build();
		
	}
	public SaleEditPanel( Sale sale ){
		
		build();
		setSale( sale );
		
	}
	public SaleEditPanel( Sale sale , ArrayList<Product> productsToAdd ){
		
		build();
		setSale( sale , productsToAdd );
		
		
	}
	public void build(){
		
		buildLeft();
		buildRight();
		setLayout( new BorderLayout() );
		add( leftPanel , BorderLayout.WEST );
		
	}
	public void buildLeft(){
		
		salePriceField.setHorizontalAlignment( SwingConstants.CENTER );
		shippingPriceField.setHorizontalAlignment( SwingConstants.CENTER );
		modPriceField.setHorizontalAlignment( SwingConstants.CENTER );
		purchasePriceField.setHorizontalAlignment( SwingConstants.CENTER );
		purchasePriceField.setEditable( false );
		purchasePriceField.setBorder( salePriceField.getBorder() );
		purchasePriceField.setBackground( salePriceField.getBackground() );
		profitField.setHorizontalAlignment( SwingConstants.CENTER );
		profitField.setEditable( false );
		profitField.setBorder( salePriceField.getBorder() );
		profitField.setBackground( salePriceField.getBackground() );
		buyerField.setHorizontalAlignment( SwingConstants.CENTER );
		saleIdField.setHorizontalAlignment( SwingConstants.CENTER );
		trackingNumberField.setHorizontalAlignment( SwingConstants.CENTER );
		
		
		saleDatePanel.add( saleDateField );
		saleDatePanel.add( editSaleDateBtn );
		saleDatePanel.setOpaque( false );
		
		editSaleDateBtn.setMargin( new Insets(0,0,0,0) );
		editSaleDateBtn.setPreferredSize( new Dimension(22,22) );
		editSaleDateBtn.addActionListener( new EditSaleDateListener() );
		editSaleDateBtn.setOpaque( false );
		saleDatePanel.setBorder( BorderFactory.createEmptyBorder() );
		saleDateField.setEditable( false );
		saleDateField.setBorder( shippingPriceField.getBorder() );
		saleDateField.setBackground( Color.white );
		saleDateField.setForeground( Color.black );
		saleDateField.setOpaque( true );
		saleDateField.setHorizontalAlignment( SwingConstants.CENTER );
		saleDateField.setMargin( new Insets(0,0,0,0) );
		saleDateField.setPreferredSize( new Dimension(89,20) );
		saleDateField.setFont( shippingPriceField.getFont() );
		ebayCheckBox.setMargin( new Insets(0,0,0,0) );
		ebayCheckBox.setOpaque( false );
				
		fieldsPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets( 5 , 5 , 5 , 5 );
		
		for( int x=0; x<components.length; x++ )
		{
			for( int y=0; y<components[x].length; y++ )
			{
				c.gridx = x;
				c.gridy = y;
				
				if( components[x][y] == saleDatePanel )
				{
					c.insets = new Insets( 0 , 0 , 0 , 0 );
				}
				else if( x == 1 )
				{
					c.insets = new Insets( 5 , 5 , 5 , 25 );
				}
				else
				{
					c.insets = new Insets( 5 , 5 , 5 , 5 );
				}
				
				fieldsPanel.add( components[x][y] , c );
			}
		}
		
		FieldListener fieldListener = new FieldListener();
		
		salePriceField.addActionListener( fieldListener );
		salePriceField.addFocusListener( fieldListener );
		
		shippingPriceField.addActionListener( fieldListener );
		shippingPriceField.addFocusListener( fieldListener );
		
		modPriceField.addActionListener( fieldListener );
		modPriceField.addFocusListener( fieldListener );
		
		buyerField.addActionListener( fieldListener );
		buyerField.addFocusListener( fieldListener );
		
		saleIdField.addActionListener( fieldListener );
		saleIdField.addFocusListener( fieldListener );
		
		trackingNumberField.addActionListener( fieldListener );
		trackingNumberField.addFocusListener( fieldListener );
		
		ebayCheckBox.addActionListener( fieldListener );
		
		leftPanel.setLayout( new BorderLayout() );
		leftPanel.add( fieldsPanel , BorderLayout.CENTER );
		
		
		
		
	}
	public void buildRight(){
		
		productTable.setShowGroupCount( false );
		
		
		tablePanel = new JScrollPane(){
			
			public Dimension getPreferredSize(){
				
				return productTable.getMyPreferredSize( this.getWidth() , this.getHeight() );
				
			}
		
		
		};
		tablePanel.setCorner( JScrollPane.UPPER_RIGHT_CORNER , MyTable.createCornerComponent( productTable ) );
		tablePanel.setViewportView( productTable );
		tablePanel.getViewport().setOpaque( false );
		tablePanel.setOpaque( false );
		this.setOpaque( false );
		productTable.setOpaque( false );
		
		
		
		
		productTable.setAutoResizeMode( JTable.AUTO_RESIZE_LAST_COLUMN );
		productTable.setFillsViewportHeight( false );
		//productTable.setFillsViewportWidth( false );
		
		//productTable.getTableHeader().addMouseListener( new ColumnPopupListener( productTable ) );
		
		tablePanel.setBorder( BorderFactory.createEmptyBorder( 6,25,5,5 ) );
		rightPanel.setLayout( new BorderLayout() );
		rightPanel.add( tablePanel , BorderLayout.CENTER );
		
		productTable.model.setActualColumnHidden( 0 , true );
		productTable.model.setActualColumnHidden( 2 , true );
		productTable.model.setActualColumnHidden( 3 , true );
		productTable.model.setActualColumnHidden( 4 , true );
		productTable.model.setActualColumnHidden( 5 , true );
		productTable.model.setActualColumnHidden( 6 , true );
		productTable.model.setActualColumnHidden( 7 , true );
		
		
	}
	
	public void setSale( Sale sale ){
		
		this.sale = new Sale( sale );
		updateFields();
		updateTable();
		
		
	}
	public void setSale( Sale sale , ArrayList<Product> productsToAdd ){
		
		this.sale = new Sale( sale , productsToAdd );
		updateFields();
		updateTable();
		
		//highlight added products
		for( int actualR=sale.getProductCount(); actualR<this.sale.getProductCount(); actualR++ )
		{
			int displayedR = productTable.model.getDisplayedR( actualR );
			productTable.addRowSelectionInterval( displayedR , displayedR );
		}
		
	}
	
	private void updateFields(){
		
		
		
		setSalePrice( this.sale.getSalePrice() );
		setShippingPrice( this.sale.getShippingPrice() );
		setModPrice( this.sale.getModPrice() );
		setPurchasePrice( this.sale.getTotalPurchasePrice() );
		setRevenue( this.sale.getRevenue() );
		setSaleDate( this.sale.getSaleDate() );
		setBuyer( this.sale.getBuyer() );
		setSaleID( this.sale.getSaleID() );
		setTrackingNumber( this.sale.getTrackingNumber() );
		setEbay( this.sale.getEbay() );
		
	}
	private void setSalePrice( double productsPrice ){
		
		String text = new DecimalFormat("$#,##0.00").format( productsPrice );
		salePriceField.setText( text );
		
	}
	private void setShippingPrice( double shippingPrice ){
		
		String text = new DecimalFormat("$#,##0.00").format( shippingPrice );
		shippingPriceField.setText( text );
		
	}
	private void setModPrice( double modPrice ){
		
		String text = new DecimalFormat("$#,##0.00").format( modPrice );
		modPriceField.setText( text );
		
	}
	private void setPurchasePrice( double purchasePrice ){
		
		String text = new DecimalFormat("$#,##0.00").format( purchasePrice );
		purchasePriceField.setText( text );
		
	}
	private void setRevenue( double revenue ){
		
		String text = new DecimalFormat("$#,##0.00").format( revenue );
		profitField.setText( text );
		
	} 
	private void setSaleDate( GregorianCalendar saleDate ){
		
		String text = new SimpleDateFormat("MM/dd/yy").format( saleDate.getTime() );
		saleDateField.setText( text );
		
	}
	private void setBuyer( String buyer ){
		
		buyerField.setText( buyer );
		
	}
	private void setSaleID( String saleID ){
		
		saleIdField.setText( saleID );
		
	}
	private void setTrackingNumber( String trackingNumber ){
		
		trackingNumberField.setText( trackingNumber );
		
	}
	private void setEbay( boolean ebay ){
		
		ebayCheckBox.setSelected( ebay );
		
	}
	
	private void updateTable(){
		
		ArrayList<Product> products = this.sale.getProducts();
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			productTable.model.addRow( product );
		}
		
		
//		for( int i=0; i<products.size(); i++ )
//		{
//			Product product = products.get( i );
//			int index = productTable.model.getActualIndexOf( product );
//		}
		
	}
	
	public boolean isValidInput(){
		
		return this.validInput;
		
	}
	
	public Sale getSale(){
		
		return sale;
		
	}
	
	
	private void tryApplyProductsPrice(){
		
		try
		{
			double newProductsPrice = getSalePrice();
			sale.setSalePrice( newProductsPrice );
		}
		catch( NumberFormatException nfe )
		{
			validInput = false;
			JOptionPane.showMessageDialog( null , "Invalid products price input" );
			validInput = true;
		}
		
	}
	private void tryApplyShippingPrice(){
		
		
		try
		{
			double newShippingPrice = getShippingPrice();
			sale.setShippingPrice( newShippingPrice );
		}
		catch( NumberFormatException nfe )
		{
			validInput = false;
			JOptionPane.showMessageDialog( null , "Invalid shipping price input" );
			validInput = true;
		}
		
		
	}
	private void tryApplyModPrice(){
		
		
		try
		{
			double newModPrice = getModPrice();
			sale.setModPrice( newModPrice );
		}
		catch( NumberFormatException nfe )
		{
			validInput = false;
			JOptionPane.showMessageDialog( null , "Invalid mod price input" );
			validInput = true;
		}
		
		
	}
	private void applyBuyer(){
		
		String newBuyer = getBuyer();
		sale.setBuyer( newBuyer );
		
	}
	private void applySaleID(){
		
		String newSaleID = getSaleID();
		sale.setSaleID( newSaleID );
		
	}
	private void applyTrackingNumber(){
		
		String newTrackingNumber = getTrackingNumber();
		sale.setTrackingNumber( newTrackingNumber );
		
	}
	private void applyEbay(){
		
		boolean newEbay = getEbay();
		sale.setEbay( newEbay );
		
		
	}
	
	
	private double getSalePrice() throws NumberFormatException {
		
		String inputText = salePriceField.getText();
		
		inputText = inputText.replace( "$" , "" );
		inputText = inputText.replace( "," , "" );
		
		return Double.parseDouble( inputText );
		
	}
	private double getShippingPrice() throws NumberFormatException {
		
		String inputText = shippingPriceField.getText();
		
		inputText = inputText.replace( "$" , "" );
		inputText = inputText.replace( "," , "" );
		
		return Double.parseDouble( inputText );
		
	}
	private double getModPrice() throws NumberFormatException {
		
		String inputText = modPriceField.getText();
		
		inputText = inputText.replace( "$" , "" );
		inputText = inputText.replace( "," , "" );
		
		return Double.parseDouble( inputText );
		
	}
	private String getBuyer(){
		
		return buyerField.getText();
		
	}
	private String getSaleID(){
		
		return saleIdField.getText();
		
	}
	private String getTrackingNumber(){
		
		return trackingNumberField.getText();
		
	}
	private boolean getEbay(){
		
		return ebayCheckBox.isSelected();
		
	}
	
	
	public void expand(){
		
		add( rightPanel , BorderLayout.CENTER );
		revalidate();
		expanded = true;
		
	}
	public void collapse(){
		
		remove( rightPanel );
		revalidate();
		expanded = false;
		
	}
	public void toggleExpand(){
		
		if( expanded )
		{
			collapse();
		}
		else
		{
			expand();
		}
		
	}
	public boolean isExpanded(){
		
		return this.expanded;
		
	}
	
	public void removeSelected(){
		
		
		ArrayList<Product> selectedProducts = productTable.getSelectedProducts();
		
		if( sale.getProductCount() == selectedProducts.size() )//removing all
		{
			JOptionPane.showMessageDialog( null , "Can't remove all. Instead, delete the whole sale" );
		}
		else if( selectedProducts.isEmpty() )//removing all
		{
			JOptionPane.showMessageDialog( null , "Select product(s) to remove" );
		}
		else
		{
			
			for( int i=0; i<selectedProducts.size(); i++ )
			{
				Product product = selectedProducts.get( i );
				sale.remove( product );
				product.setSale( null );
				
				productTable.model.removeRow( product );
				productTable.model.updateDisplayedRows( false );
				
				updateFields();
			}
		}
		
			
		
		
	}
	
	
	
	
	private class FieldListener implements ActionListener , FocusListener {

		
		
		public void focusGained( FocusEvent fe ) {
			
			
			if( fe.getSource() == salePriceField )
			{
				salePriceField.selectAll();
			}
			else if( fe.getSource() == shippingPriceField )
			{
				shippingPriceField.selectAll();
			}
			else if( fe.getSource() == modPriceField )
			{
				modPriceField.selectAll();
			}
			else if( fe.getSource() == buyerField )
			{
				buyerField.selectAll();
			}
			else if( fe.getSource() == saleIdField )
			{
				saleIdField.selectAll();
			}
			else if( fe.getSource() == trackingNumberField )
			{
				trackingNumberField.selectAll();
			}
			
		}
		public void focusLost( FocusEvent fe ) {
			
			if( fe.getSource() == salePriceField )
			{
				tryApplyProductsPrice();
				updateFields();
				salePriceField.selectAll();
			}
			else if( fe.getSource() == shippingPriceField )
			{
				tryApplyShippingPrice();
				updateFields();
				shippingPriceField.selectAll();
			}
			else if( fe.getSource() == modPriceField )
			{
				tryApplyModPrice();
				updateFields();
				modPriceField.selectAll();
			}
			else if( fe.getSource() == buyerField )
			{
				applyBuyer();
				updateFields();
				buyerField.selectAll();
			}
			else if( fe.getSource() == saleIdField )
			{
				applySaleID();
				updateFields();
				saleIdField.selectAll();
			}
			else if( fe.getSource() == trackingNumberField )
			{
				applyTrackingNumber();
				updateFields();
				trackingNumberField.selectAll();
			}

			
			
			
		}

		public void actionPerformed( ActionEvent ae ) {
			
			if( ae.getSource() == salePriceField )
			{
				tryApplyProductsPrice();
				updateFields();
				salePriceField.selectAll();
			}
			else if( ae.getSource() == shippingPriceField )
			{
				tryApplyShippingPrice();
				updateFields();
				shippingPriceField.selectAll();
			}
			else if( ae.getSource() == shippingPriceField )
			{
				tryApplyModPrice();
				updateFields();
				modPriceField.selectAll();
			}
			else if( ae.getSource() == buyerField )
			{
				applyBuyer();
				updateFields();
				buyerField.selectAll();
			}
			else if( ae.getSource() == saleIdField )
			{
				applySaleID();
				updateFields();
				saleIdField.selectAll();
			}
			else if( ae.getSource() == trackingNumberField )
			{
				applyTrackingNumber();
				updateFields();
				trackingNumberField.selectAll();
			}
			
			else if( ae.getSource() == ebayCheckBox )
			{
				applyEbay();
				updateFields();
			}
			
			
		}
		
		
		
		
	}
	private class EditSaleDateListener implements ActionListener {

		
		public void actionPerformed( ActionEvent ae ) {
			
			
			
			try
			{
				GregorianCalendar newSaleDate = CalendarDialog.showDialog( sale.getSaleDate() , "Sale Date" );
				
				
				sale.setSaleDate( newSaleDate );
				setSaleDate( newSaleDate );
				
				
			} 
			catch ( NotReceivedButSoldException ex )
			{
				JOptionPane.showMessageDialog( null , "Cannot sell products that have not been received" );
			}
			catch ( ReceivedAfterSoldException ex )
			{
				JOptionPane.showMessageDialog( null , "Cannot set sell date later than received date" );
			}
				
						
			
			
				
			
		}
		
		
		
	}
	
	
	
	
	
	public static void main( String[] args ) {
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		
		Sale dummy = new Sale( new Product(new Purchase( "" , 100 , "" , 100 , new GregorianCalendar(1,1,1) , new GregorianCalendar(1,1,1) , "" )) );
		SaleEditDialog.showDialog( dummy , "Edit Sale" );
		
	}
	
	
	
}





