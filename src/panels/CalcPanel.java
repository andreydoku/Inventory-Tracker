
package panels;

import gui.GradientPanel;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;

import basic.Sale;


public class CalcPanel extends GradientPanel {
	
	
	private static Color DISABLED_FIELD_BACKGROUND = new Color( 200,200,200 );
	
	
	
	
	
	String[] options = { "Revenue" , "Sale Price" };
	

	JLabel optionLabel = new JLabel( "Calculate" );
	JLabel salePriceLabel = new JLabel( "Sale Price" );
	JLabel shippingPriceLabel = new JLabel( "Shipping Price" );
	JLabel modPriceLabel = new JLabel( "Mod Price" );
	JLabel purchasePriceLabel = new JLabel( "Purchase Price" );
	JLabel profitLabel = new JLabel( "Revenue" );
	JLabel ebayLabel = new JLabel( "Ebay" );
	
	JComboBox optionsBox = new JComboBox( options );
	JTextField salePriceField = new JTextField( 10 );
	JTextField shippingPriceField = new JTextField( 10 );
	JTextField modPriceField = new JTextField( 10 );
	JTextField purchasePriceField = new JTextField( 10 );
	JTextField profitField = new JTextField( 10 );
	JCheckBox ebayCheckBox = new JCheckBox();
	
	JButton clearBtn = new JButton( "Clear" );
	
	JComponent[][] components = { { optionLabel , salePriceLabel , shippingPriceLabel , modPriceLabel , purchasePriceLabel , profitLabel , ebayLabel    } , 
								  {	optionsBox , salePriceField , shippingPriceField , modPriceField , purchasePriceField , profitField , ebayCheckBox } };
	
	
	
	private int option;
	
	
	public CalcPanel(){
		
		build();
		
		setSalePrice( 0 );
		setShippingPrice( 0 );
		setModPrice( 0 );
		setPurchasePrice( 0 );
		setEbay( true );
		
		setOption( 0 );
		updateRevenue();
		
	}
	public void build(){
		
		
		int h = 26;
		optionsBox.setPreferredSize( new Dimension( optionsBox.getPreferredSize().width , h-2 ) );
		salePriceField.setPreferredSize( new Dimension( salePriceField.getPreferredSize().width , h-2 ) );
		shippingPriceField.setPreferredSize( new Dimension( shippingPriceField.getPreferredSize().width , h-2 ) );
		modPriceField.setPreferredSize( new Dimension( modPriceField.getPreferredSize().width , h-2 ) );
		purchasePriceField.setPreferredSize( new Dimension( purchasePriceField.getPreferredSize().width , h-2 ) );		
		profitField.setPreferredSize( new Dimension( profitField.getPreferredSize().width , h-2 ) );		
		clearBtn.setPreferredSize( new Dimension( clearBtn.getPreferredSize().width , h-2 ) );
		
		
		
		optionsBox.setOpaque( false );
		optionsBox.setBorder( null );
		
		
		
		salePriceField.setHorizontalAlignment( SwingConstants.CENTER );
		shippingPriceField.setHorizontalAlignment( SwingConstants.CENTER );
		modPriceField.setHorizontalAlignment( SwingConstants.CENTER );
		purchasePriceField.setHorizontalAlignment( SwingConstants.CENTER );
		profitField.setHorizontalAlignment( SwingConstants.CENTER );
		
		
		ebayCheckBox.setMargin( new Insets(0,0,0,0) );
		ebayCheckBox.setOpaque( false );
				
		setLayout( new GridBagLayout() );
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
				
				if( x == 1 )
				{
					c.insets = new Insets( 5 , 5 , 5 , 25 );
				}
				else
				{
					c.insets = new Insets( 5 , 5 , 5 , 5 );
				}
				
				add( components[x][y] , c );
			}
		}
		
		c.gridx = 0;
		c.gridy = components[0].length;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		add( clearBtn , c );
		clearBtn.setOpaque( false );
		
		
		
		optionsBox.addActionListener( new OptionsListener() );
		
		
		FieldListener fieldListener = new FieldListener();
		
		salePriceField.addActionListener( fieldListener );
		salePriceField.addFocusListener( fieldListener );
		
		shippingPriceField.addActionListener( fieldListener );
		shippingPriceField.addFocusListener( fieldListener );
		
		modPriceField.addActionListener( fieldListener );
		modPriceField.addFocusListener( fieldListener );
		
		purchasePriceField.addActionListener( fieldListener );
		purchasePriceField.addFocusListener( fieldListener );
		
		ebayCheckBox.addActionListener( fieldListener );
		
		profitField.addActionListener( fieldListener );
		profitField.addFocusListener( fieldListener );
		
		clearBtn.addActionListener( fieldListener );
		
		
	}
	public void setOption( int option ){
		
		if( option == 0 )//revenue
		{
			this.salePriceField.setEditable( true );salePriceField.setBackground( Color.white );
			this.profitField.setEditable( false );profitField.setBackground( DISABLED_FIELD_BACKGROUND );
			this.option = 0;
		}
		else if( option == 1 )//sale price
		{
			this.salePriceField.setEditable( false );salePriceField.setBackground( DISABLED_FIELD_BACKGROUND );
			this.profitField.setEditable( true );profitField.setBackground( Color.white );
			this.option = 1;
		}
		else
		{
			throw new IllegalArgumentException( "Illegal option" );
		}
		
	}
	
	
	
	private void setSale( Sale sale ){
		
		setSalePrice( sale.getSalePrice() );
		setShippingPrice( sale.getShippingPrice() );
		setModPrice( sale.getModPrice() );
		setPurchasePrice( sale.getTotalPurchasePrice() );
		setRevenue( sale.getRevenue() );
		setEbay( sale.getEbay() );
		
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
	private void setEbay( boolean ebay ){
		
		ebayCheckBox.setSelected( ebay );
		
	}
	private void clear(){
		
		setSalePrice( 0 );
		setShippingPrice( 0 );
		setModPrice	( 0 );
		setPurchasePrice( 0 );	
		setRevenue( 0 );
				
	}
	
	public void updateRevenue(){
		
		double salePrice,shippingPrice,modPrice,purchasePrice,revenue;
		boolean ebay = getEbay();
		try
		{
			salePrice = getSalePrice();
			
			shippingPrice = getShippingPrice();
			modPrice = getModPrice();
			purchasePrice = getPurchasePrice();
		}
		catch( IllegalInputException iie )
		{
			System.out.println( "updateRevenue()" + "\n" + iie.getMessage() );
			JOptionPane.showMessageDialog( null , "Invalid Input: " + iie.getMessage() );
			return;
		}
		
		revenue = Sale.getRevenue( salePrice , shippingPrice , modPrice , purchasePrice , ebay );
		
		//double salePrice2 = Sale.getSalePrice( revenue , shippingPrice , modPrice , purchasePrice , ebay );
		//System.out.println( salePrice + " , " + salePrice2 );
		
		
		
		setSalePrice( salePrice );
		setShippingPrice( shippingPrice );
		setModPrice( modPrice );
		setPurchasePrice( purchasePrice );
		setRevenue( revenue );
		
	}
	public void updateSalePrice(){
		
		double salePrice,shippingPrice,modPrice,purchasePrice,revenue;
		boolean ebay = getEbay();
		try
		{
			revenue = getRevenue();System.out.println();
			shippingPrice = getShippingPrice();
			modPrice = getModPrice();
			purchasePrice = getPurchasePrice();
		}
		catch( IllegalInputException iie )
		{
			System.out.println( "updateSalePrice()" + "\n" + iie.getMessage() );
			JOptionPane.showMessageDialog( null , "Invalid Input: " + iie.getMessage() );
			return;
		}
		
		salePrice = Sale.getSalePrice( revenue , shippingPrice , modPrice , purchasePrice , ebay );
		
		
		
		
		setSalePrice( salePrice );
		setShippingPrice( shippingPrice );
		setModPrice( modPrice );
		setPurchasePrice( purchasePrice );
		setRevenue( revenue );
		
		
	}
	
	private double getSalePrice() throws IllegalInputException {
		
		String inputText = salePriceField.getText();
		
		inputText = inputText.replace( "$" , "" );
		inputText = inputText.replace( "," , "" );
		
		try
		{
			double salePrice = Double.parseDouble( inputText );
			return salePrice;
		}
		catch( NumberFormatException nfe )
		{
			throw new IllegalInputException( "Illegal Input, sale price field: " + inputText );
		}
		
		
		
	}
	private double getShippingPrice() throws IllegalInputException {
		
		String inputText = shippingPriceField.getText();
		
		inputText = inputText.replace( "$" , "" );
		inputText = inputText.replace( "," , "" );
		
		
		try
		{
			double shippingPrice = Double.parseDouble( inputText );
			return shippingPrice;
		}
		catch( NumberFormatException nfe )
		{
			throw new IllegalInputException( "Illegal Input, shipping price field: " + inputText );
		}
		
		
		
	}
	private double getModPrice() throws IllegalInputException {
		
		String inputText = modPriceField.getText();
		
		inputText = inputText.replace( "$" , "" );
		inputText = inputText.replace( "," , "" );
		
		
		try
		{
			double modPrice = Double.parseDouble( inputText );
			return modPrice;
		}
		catch( NumberFormatException nfe )
		{
			throw new IllegalInputException( "Illegal Input, mod price field: " + inputText );
		}
		
	}
	private double getPurchasePrice() throws IllegalInputException {
		
		String inputText = purchasePriceField.getText();
		
		inputText = inputText.replace( "$" , "" );
		inputText = inputText.replace( "," , "" );
		
		
		try
		{
			double purchasePrice = Double.parseDouble( inputText );
			return purchasePrice;
		}
		catch( NumberFormatException nfe )
		{
			throw new IllegalInputException( "Illegal Input, purchase price field: " + inputText );
		}
		
	}
	private boolean getEbay(){
		
		return ebayCheckBox.isSelected();
		
	}
	private double getRevenue() throws IllegalInputException {
		
		String inputText = profitField.getText();
		
		inputText = inputText.replace( "$" , "" );
		inputText = inputText.replace( "," , "" );
		
		
		try
		{
			double revenue = Double.parseDouble( inputText );
			return revenue;
		}
		catch( NumberFormatException nfe )
		{
			throw new IllegalInputException( "Illegal Input, revenue field: " + inputText );
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
			else if( fe.getSource() == purchasePriceField )
			{
				purchasePriceField.selectAll();
			}
			else if( fe.getSource() == profitField )
			{
				profitField.selectAll();
			}
			
		}
		public void focusLost( FocusEvent fe ) {
			
			if( fe.getSource() == salePriceField )
			{
				updateRevenue();
				
				salePriceField.selectAll();
				
			}
			else if( fe.getSource() == shippingPriceField )
			{
				if( option == 0 )
				{
					updateRevenue();
				}
				else
				{
					updateSalePrice();
				}
				
				shippingPriceField.selectAll();
			}
			else if( fe.getSource() == modPriceField )
			{
				if( option == 0 )
				{
					updateRevenue();
				}
				else
				{
					updateSalePrice();
				}
				
				modPriceField.selectAll();
			}
			else if( fe.getSource() == purchasePriceField )
			{
				if( option == 0 )
				{
					updateRevenue();
				}
				else
				{
					updateSalePrice();
				}
				
				purchasePriceField.selectAll();
			}
			else if( fe.getSource() == profitField )
			{
				updateSalePrice();
				profitField.selectAll();
			}
			
			
			
		}

		public void actionPerformed( ActionEvent ae ) {
			
			if( ae.getSource() == salePriceField )
			{
				updateRevenue();
				salePriceField.selectAll();
			}
			else if( ae.getSource() == shippingPriceField )
			{
				if( option == 0 )
				{
					updateRevenue();
				}
				else
				{
					updateSalePrice();
				}
				
				shippingPriceField.selectAll();
			}
			else if( ae.getSource() == modPriceField )
			{
				if( option == 0 )
				{
					updateRevenue();
				}
				else
				{
					updateSalePrice();
				}
				
				modPriceField.selectAll();
			}
			else if( ae.getSource() == purchasePriceField )
			{
				if( option == 0 )
				{
					updateRevenue();
				}
				else
				{
					updateSalePrice();
				}
				
				purchasePriceField.selectAll();
			}
			else if( ae.getSource() == ebayCheckBox )
			{
				if( option == 0 )
				{
					updateRevenue();
				}
				else
				{
					updateSalePrice();
				}
			}
			else if( ae.getSource() == profitField )
			{
				updateSalePrice();
				profitField.selectAll();
			}
			else if( ae.getSource() == clearBtn )
			{
				clear();
			}
			
		}
		
		
		
		
	}
	private class OptionsListener implements ActionListener {

		
		public void actionPerformed( ActionEvent ae ) {
			
			int option = optionsBox.getSelectedIndex();
			setOption( option );
			
		}
		
		
		
		
		
	}
	
	
	private class IllegalInputException extends IllegalArgumentException{
		
		public IllegalInputException( String message ){
			
			super( message );
			
		}
		
	}
	
	
	
}





