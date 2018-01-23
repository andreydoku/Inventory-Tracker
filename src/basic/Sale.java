
package basic;


import basic.Product;
import table.RowData;
import exceptions.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import myLibrary.ArrayToString;
import myLibrary.Methods;


public class Sale implements RowData {
	
	
	private ArrayList<Product> products;
	private double salePrice;
	private double shippingPrice;
	private double modPrice;
	private String buyer;
	private GregorianCalendar saleDate;
	private String saleID;
	private String trackingNumber;
	private boolean ebay;
	
	
	public Sale( Product product ){
		
		this.products = new ArrayList<Product>();
			products.add( product );
		this.salePrice = 100;
		this.shippingPrice = 0;
		this.modPrice = 0;
		this.buyer = null;
		
		this.saleDate = (GregorianCalendar) GregorianCalendar.getInstance();
		GregorianCalendar today = (GregorianCalendar) GregorianCalendar.getInstance();
		GregorianCalendar latest = product.getReceivedDate();
		if( Methods.isOnLaterDayThan( latest , today ) )
		{
			int year = latest.get( GregorianCalendar.YEAR );
			int month = latest.get( GregorianCalendar.MONTH );
			int day = latest.get( GregorianCalendar.DAY_OF_MONTH );
			this.saleDate = new GregorianCalendar( year , month , day );
		}
		else
		{
			int year = today.get( GregorianCalendar.YEAR );
			int month = today.get( GregorianCalendar.MONTH );
			int day = today.get( GregorianCalendar.DAY_OF_MONTH );
			this.saleDate = new GregorianCalendar( year , month , day );
			
		}
		
		
		this.saleID = null;
		this.trackingNumber = null;
		this.ebay = true;
		
		
		
		
		
		
	}
	public Sale( ArrayList<Product> products ){
		
		this.products = products;
		this.salePrice = 100;
		this.shippingPrice = 0;
		this.modPrice = 0;
		this.buyer = null;
		
		
		GregorianCalendar today = (GregorianCalendar) GregorianCalendar.getInstance();
		GregorianCalendar latest = getLatestReceivedDate( products );
		if( Methods.isOnLaterDayThan( latest , today ) )
		{
			int year = latest.get( GregorianCalendar.YEAR );
			int month = latest.get( GregorianCalendar.MONTH );
			int day = latest.get( GregorianCalendar.DAY_OF_MONTH );
			this.saleDate = new GregorianCalendar( year , month , day );
		}
		else
		{
			int year = today.get( GregorianCalendar.YEAR );
			int month = today.get( GregorianCalendar.MONTH );
			int day = today.get( GregorianCalendar.DAY_OF_MONTH );
			this.saleDate = new GregorianCalendar( year , month , day );
			
		}
		
		this.saleID = null;
		this.trackingNumber = null;
		this.ebay = true;
		
		
		
		
		
		
		
		
		
		
	}
	public Sale( Sale sale ){
		
		this.products = new ArrayList<Product>();
		try
		{
			//this.setProducts( sale.getProducts() );
			for( int i=0; i<sale.getProducts().size(); i++ )
			{
				Product product = sale.getProducts().get( i );
				Product newProduct = new Product( product );
				newProduct.setSale( this );
				this.products.add( newProduct );
			}
			
			this.setSalePrice( sale.getSalePrice() );
			this.setShippingPrice( sale.getShippingPrice() );
			this.setModPrice( sale.getModPrice() );
			this.setBuyer( sale.getBuyer() );
			this.setSaleDate( sale.getSaleDate() );
			this.setSaleID( sale.getSaleID() );
			this.setTrackingNumber( sale.getTrackingNumber() );
			this.setEbay( sale.getEbay() );
			
		} 
		catch ( NotReceivedButSoldException ex )
		{
			ex.printStackTrace();
		} 
		catch ( ReceivedAfterSoldException ex )
		{
			ex.printStackTrace();
		}
		
	}
	public Sale( Sale sale , ArrayList<Product> productsToAdd ){
		
		this.products = new ArrayList<Product>();
		try
		{
			//this.setProducts( sale.getProducts() );
			for( int i=0; i<sale.getProducts().size(); i++ )
			{
				Product product = sale.getProducts().get( i );
				Product newProduct = new Product( product );
				newProduct.setSale( this );
				this.products.add( newProduct );
			}
			
			for( int i=0; i<productsToAdd.size(); i++ )
			{
				Product product = productsToAdd.get( i );
				Product newProduct = new Product( product );
				newProduct.setSale( this );
				this.products.add( newProduct );
			}
			
			this.setSalePrice( sale.getSalePrice() );
			this.setShippingPrice( sale.getShippingPrice() );
			this.setModPrice( sale.getModPrice() );
			this.setBuyer( sale.getBuyer() );
			this.setSaleDate( sale.getSaleDate() );
			this.setSaleID( sale.getSaleID() );
			this.setTrackingNumber( sale.getTrackingNumber() );
			this.setEbay( sale.getEbay() );
			
		} 
		catch ( NotReceivedButSoldException ex )
		{
			ex.printStackTrace();
		} 
		catch ( ReceivedAfterSoldException ex )
		{
			ex.printStackTrace();
		}
		
	}
	public Sale( String saleText , ArrayList<Product> allProducts ) throws FailedToLoadException{
		
		this.salePrice = 100;
		this.shippingPrice = 0;
		this.modPrice = 0;
		this.buyer = null;
		
		this.saleDate = (GregorianCalendar) GregorianCalendar.getInstance();
		
		
		this.saleID = null;
		this.trackingNumber = null;
		this.ebay = true;
		
		
		
		applySaveString( saleText , allProducts );
		
	}
	
	public void add( Product product ){
		
		products.add( product );
		
	}
	public void remove( Product product ){
		
		/*boolean removed = */products.remove( product );
		
	}
	public void removeAllProducts(){
		
		products.clear();
		
	}
	public void replace( int index , Product product ){
		
		products.set( index , product );
		
	}
	public boolean contains( Product product ){
		
		return products.contains( product );
		
	}
	
	public static GregorianCalendar getLatestReceivedDate( ArrayList<Product> products ){
		
		if( products.isEmpty() )
		{
			return null;
		}
		
		GregorianCalendar latest = null;
		for( int i=0; i<products.size(); i++ )
		{
			
			GregorianCalendar current = products.get( i ).getReceivedDate();
			
			if( i == 0 )
			{
				latest = current;
			}
			
			if( Methods.isOnLaterDayThan( current , latest ) )
			{
				latest = current;
			}
		}
		
		
		return latest;	
	}
	
	//get
	public ArrayList<Product> getProducts(){
		
		return this.products;
		
	}
	public int getProductCount(){
		
		return products.size();
		
	}
	public double getSalePrice(){
		
		return this.salePrice;
		
	}
	public double getShippingPrice(){
		
		return this.shippingPrice;
		
	}
	public double getModPrice(){
		
		return this.modPrice;
		
	}
	public String getBuyer(){
		
		return this.buyer;
		
	}
	public GregorianCalendar getSaleDate(){
		
		return this.saleDate;
		
	}
	public String getSaleID(){
		
		return this.saleID;
		
	}
	public String getTrackingNumber(){
		
		return this.trackingNumber;
		
	}
	public boolean getEbay(){
		
		return this.ebay;
		
	}
	
	
	
	
	public double getTotalPurchasePrice(){
		
		double totalPurchasePrice = 0;
		
		for( int i=0; i<products.size(); i++ )
		{
			totalPurchasePrice += products.get( i ).getPurchasePrice();
		}
		
		return totalPurchasePrice;
		
	}
	public double getRevenue(){
		
		
		return Sale.getRevenue( salePrice , shippingPrice, modPrice , getTotalPurchasePrice() , ebay );
		
		
		/*
		double salePrice = getSalePrice();
		
			
		
		double paypalFee = 0.029*salePrice;
		
		double revenue = getSalePrice() - getShippingPrice() - paypalFee - getTotalPurchasePrice() - getModPrice();
		
		if( ebay )
		{
			
			double ebayFee = 0;
			
			if( salePrice >= 0.99 && salePrice <= 50.00 )
			{
				ebayFee = 0.07*salePrice;
			}
			else if( salePrice > 50.00 && salePrice <= 1000.00 )
			{
				ebayFee = 0.07*50 + 0.05*(salePrice-50);
			}
			else if( salePrice > 1000 )
			{
				ebayFee = 0.07*50 + 0.05*(1000-50.00) + .02*(salePrice-1000);
			}
			
			
			revenue -= ebayFee;
			
			
		}
		
		return revenue;
		*/
		
		
	}
	
	public static double getRevenue( double salePrice , double shippingPrice , double modPrice , double purchasePrice , boolean ebay ){
		
		
		
		double paypalFee = 0.029*salePrice;
		
		double revenue = salePrice - shippingPrice - modPrice - purchasePrice - paypalFee;
		
		if( ebay )
		{
			
			double ebayFee = 0;
			
			if( salePrice >= 0.99 && salePrice <= 50.00 )
			{
				ebayFee = 0.07*salePrice;
			}
			else if( salePrice > 50.00 && salePrice <= 1000.00 )
			{
				ebayFee = 0.07*50 + 0.05*(salePrice-50);
			}
			else if( salePrice > 1000 )
			{
				ebayFee = 0.07*50 + 0.05*(1000-50.00) + .02*(salePrice-1000);
			}
			
			
			revenue -= ebayFee;
			
			
		}
		
		return revenue;
		
	}
	public static double getSalePrice( double revenue , double shippingPrice , double modPrice , double purchasePrice , boolean ebay ){
		
		
		
		double blah = shippingPrice + modPrice + purchasePrice;
		
		if( revenue <= .96129-blah || !ebay )
		{
			return ( revenue + blah ) / .971 ;
		}
		else if( revenue > .96129-blah && revenue <= 56.05-blah )
		{
			return ( revenue + blah ) / .901 ; 
		}
		else if( revenue > 56.05-blah && revenue <= 920-blah )
		{
			return ( revenue + blah + 1 ) / .921 ;
		}
		else
		{
			return ( revenue + blah + 31 ) / .951 ;
		}
		
		
		
		
	}
	
	
	
	//set
	public void setProducts( ArrayList<Product> products ){
		
		this.products = new ArrayList<Product>();
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			product.setSale( this );
			this.products.add( product );
			
		}
		
		
		
		
	}
	
	public void setSalePrice( double productsPrice ){
		
		this.salePrice = productsPrice;
		
	}
	public void setShippingPrice( double shippingPrice ){
		
		this.shippingPrice = shippingPrice;
		
	}
	public void setModPrice( double modPrice ){
		
		this.modPrice = modPrice;
		
	}
	public void setBuyer( String buyer ){
		
		this.buyer = buyer;
		
	}
	public void setSaleDate( GregorianCalendar newSaleDate ) throws NotReceivedButSoldException, ReceivedAfterSoldException {
		
		if( newSaleDate == null )
		{
			this.saleDate = null;
		}
		else
		{
			for( int i=0; i<products.size(); i++ )
			{
				Product product = products.get( i );
				if( ! product.isReceived() )
				{
					throw new NotReceivedButSoldException();
				}
				
				if( Methods.isOnLaterDayThan( product.getReceivedDate() , newSaleDate ) )
				{
					throw new ReceivedAfterSoldException();
				}
			}

			this.saleDate = newSaleDate;
			for( int i=0; i<products.size(); i++ )
			{
				Product product = products.get( i );
				product.setSale( this );
			}

		}
		
			
	}
	public void setSaleID( String saleID ){
		
		this.saleID = saleID;
		
	}
	public void setTrackingNumber( String trackingNumber ){
		
		this.trackingNumber = trackingNumber;
		
	}
	public void setEbay( boolean ebay ){
		
		this.ebay = ebay;
		
	}
	
	
	
	
	public String toString(){
		
		DecimalFormat df = new DecimalFormat("$#,##0.00");
		String nl = "\n";
		
		String output = "";
		output += "Products: " + ArrayToString.getString( products , ", " ) + nl;
		output += "Sale Price: " + df.format( salePrice ) + nl;
		output += "Shipping Price: " + df.format( shippingPrice ) + nl;
		output += "Mod Price: " + df.format( modPrice ) + nl;
		output += "Buyer: " + buyer + nl;
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		if( saleDate != null )
		{
			output += "Sale Date: " + sdf.format( saleDate.getTime() ) + nl;
		}
		else
		{
			output += "Sale Date: " + "---" + nl;
		}
		output += "Sale ID: " + saleID + nl;
		output += "Tracking #: " + trackingNumber + nl;
		output += "Ebay: " + ebay + nl;
		
		
		
		
		
		return output;		
		
	}
	
	
	
	
	
	public String toSaveString( ArrayList<Product> allProducts ){
		
		String output = "";
		String nl = "\n";
		
		/*
		private ArrayList<Product> products;
		
		private double productsPrice;
		private double shippingPrice;
		private String buyer;
		private GregorianCalendar saleDate;
		private String miscInfo;
		private String saleID;
		private String trackingNumber;
		*/
		
		
		output += "Products Price: " + new DecimalFormat("$#,##0.00").format( this.salePrice ) + nl;
		output += "Shipping Price: " + new DecimalFormat("$#,##0.00").format( this.shippingPrice ) + nl;
		output += "Mod Price: " + new DecimalFormat("$#,##0.00").format( this.modPrice ) + nl;
		output += "Buyer: " + (( buyer == null ) ? "" : buyer.toString()) + nl;
		output += "Sale Date: " + new SimpleDateFormat("MM/dd/yy").format( this.saleDate.getTime() ) + nl;
		output += "Sale ID: " + (( saleID == null ) ? "" : saleID.toString()) + nl;
		output += "Tracking #: " + (( trackingNumber == null ) ? "" : trackingNumber.toString()) + nl;
		output += "Ebay: " + ebay + nl;
		
		
		String productIndexes = "Product Indexes: ";
		for( int i=0; i<this.products.size(); i++ )
		{
			Product product = this.products.get( i );
			int index = allProducts.indexOf( product );
			productIndexes += index + ",";
			
		}
		if( productIndexes.endsWith( "," ) )
		{
			productIndexes = productIndexes.substring( 0 , productIndexes.length()-1 );
		}
		output += productIndexes + nl;
		
		return output;
		
		
	}
	public void applySaveString( String saveString , ArrayList<Product> allProducts ) throws FailedToLoadException{
		
				
		ArrayList<String> pieces = Methods.split( saveString, "\n", false , true , false );
		for( int i=0; i<pieces.size(); i++ )
		{
			String piece = pieces.get( i );
			this.applyPiece( piece , allProducts );
		}
		
		
		
		
		
	}
	public void applyPiece( String piece , ArrayList<Product> allProducts ) throws FailedToLoadException {
		
		//System.out.println( "applyPiece: " + piece );
		
		String delim = ":";
		int delimIndex = piece.indexOf( delim );
		
		if( delimIndex == -1 )
		{
			throw new FailedToLoadException( "Failed to load sale data (1)"   );
						
		}
		
		String parameter = piece.substring( 0 , delimIndex );
		String value = piece.substring( delimIndex + delim.length() ).trim();
		
		
		
		/*
		output += "Products Price: " + new DecimalFormat("$#,##0.00").format( this.productsPrice ) + nl;
		output += "Shipping Price: " + new DecimalFormat("$#,##0.00").format( this.shippingPrice ) + nl;
		output += "Buyer: " + this.buyer;
		output += "Sale Date: " + new SimpleDateFormat("MM/dd/yy").format( this.saleDate.getTime() ) + nl;
		output += "Misc Info: " + this.miscInfo + nl;
		output += "Sale ID: " + this.saleID + nl;
		output += "Tracking #: " + this.trackingNumber + nl;
		
		String productIndexes = "Product Indexes: ";
		*/
		
		if( parameter.equals( "Product Indexes" ) )
		{
			ArrayList<Product> newProducts = new ArrayList<Product>();
			
			ArrayList<String> indexStrings = Methods.split( value , "," , false , true , false );
			for( int i=0; i<indexStrings.size(); i++ )
			{
				String indexString = indexStrings.get( i );
				try
				{
					int index = Integer.parseInt( indexString );
					Product product = allProducts.get( index );
					newProducts.add( product );
				}
				catch( NumberFormatException nfe )
				{
					throw new FailedToLoadException( "Failed to load purchase data - Invalid indexes" );
					
				}
				catch( ArrayIndexOutOfBoundsException aiobe )
				{
					throw new FailedToLoadException( "Failed to load purchase data - Invalid indexes" );
				}
				
				
			}
			
			this.setProducts( newProducts );
			
		}
		else if( parameter.equals( "Products Price" ) )
		{
			try
			{
				value = value.replace( "$" , "" );
				value = value.replace( "," , "" );
				
				double price = Double.parseDouble( value );
				if( price <0 )
				{
					throw new FailedToLoadException( "Failed to load sale data - Invalid products price" );
				}
				this.salePrice = price;
				
			}
			catch( NumberFormatException nfe )
			{
				throw new FailedToLoadException( "Failed to load sale data - Invalid products price" );
			}
			//this.quantity = quantity;
		}
		else if( parameter.equals( "Shipping Price" ) )
		{
			try
			{
				value = value.replace( "$" , "" );
				value = value.replace( "," , "" );
				
				double price = Double.parseDouble( value );
				if( price < 0 )
				{
					throw new FailedToLoadException( "Failed to load sale data - Invalid shipping price" );
				}
				this.shippingPrice = price;
				
			}
			catch( NumberFormatException nfe )
			{
				throw new FailedToLoadException( "Failed to load sale data - Invalid shipping price"   );
			}
			//this.quantity = quantity;
		}
		else if( parameter.equals( "Mod Price" ) )
		{
			try
			{
				value = value.replace( "$" , "" );
				value = value.replace( "," , "" );
				
				double price = Double.parseDouble( value );
				if( price < 0 )
				{
					throw new FailedToLoadException( "Failed to load sale data - Invalid mod price"   );
				}
				this.modPrice = price;
				
			}
			catch( NumberFormatException nfe )
			{
				throw new FailedToLoadException( "Failed to load sale data - Invalid shipping price"   );
			}
			//this.quantity = quantity;
		}
		else if( parameter.equals( "Buyer" ) )
		{
			this.buyer = value;
		}
		else if( parameter.equals( "Sale Date" ) )
		{
			try
			{
				GregorianCalendar newPurchaseDate = (GregorianCalendar) GregorianCalendar.getInstance();
				Date date = new SimpleDateFormat("MM/dd/yy").parse( value );
				newPurchaseDate.setTime( date );
				
				
				this.saleDate = newPurchaseDate;
				
			} 
			catch ( ParseException ex )
			{
				throw new FailedToLoadException( "Failed to load sale data - Invalid sale date"   );
			}
		}
		
		else if( parameter.equals( "Sale ID" ) )
		{
			this.saleID = value;
		}
		else if( parameter.equals( "Tracking #" ) )
		{
			this.trackingNumber = value;
		}
		else if( parameter.equals( "Ebay" ) )
		{
			if( value.equals( "true" ) )
			{
				this.ebay = true;
			}
			else
			{
				this.ebay = false;
			}
				
		}
		else if( parameter.equals( "Misc Info" ) )
		{
			
		}
		else
		{
			throw new FailedToLoadException( "Failed to load sale data, Unknown parameter: " + parameter );
		}
		
		
		
	}
	
	public static ArrayList<Sale> getSales( ArrayList<String> saleTextList , ArrayList<Product> products ) throws FailedToLoadException{
		
		ArrayList<Sale> sales = new ArrayList<Sale>();
		for( int i=0; i<saleTextList.size(); i++ )
		{
			String saleText = saleTextList.get( i );
			Sale sale = new Sale( saleText , products );
			sales.add( sale );
		}
		
		
		return sales;
		
	}
	
	
	
	
	
	
	
	
	//RowData
	public Object getValueAt( int column ) {
		
		if( column == 0 )
		{
			return this.products;
		}
		else if( column == 1 )
		{
			return this.getSalePrice();
		}
		else if( column == 2 )
		{
			return this.getShippingPrice();
		}
		else if( column == 3 )
		{
			return this.getModPrice();
		}
		else if( column == 4 )
		{
			return this.getTotalPurchasePrice();
		}
		else if( column == 5 )
		{
			return this.getRevenue();
		}
		else if( column == 6 )
		{
			return this.getSaleDate();
		}
		else if( column == 7 )
		{
			return this.getBuyer();
		}
		else if( column == 8 )
		{
			return this.getSaleID();
		}
		else if( column == 9 )
		{
			return this.getTrackingNumber();
		}
		else
		{
			throw new IllegalArgumentException();
		}
		
	}
	public void setValueAt( int column , Object object ) {
		throw new UnsupportedOperationException( "Not supported yet." );
	}
	public int getColumnCount() {
		
		return 10;
		
	}
	public ArrayList<String> getColumnNames() {
		
		ArrayList<String> names = new ArrayList<String>();
		
		names.add( "Products" );
		names.add( "Sale $" );
		names.add( "Shipping $" );
		names.add( "Mod $" );
		names.add( "Purchase $" );
		names.add( "Revenue $" );
		names.add( "Sale Date" );
		names.add( "Sold To" );
		names.add( "Sale ID" );
		names.add( "Tracking #" );
		
		return names;
		
	}
	public Class<?> getColumnClass( int column ) {
		
		if( column == 0 )		return new ArrayList<Product>().getClass();
		else if( column == 1 )	return Double.class;
		else if( column == 2 )	return Double.class;
		else if( column == 3 )	return Double.class;
		else if( column == 4 )	return Double.class;
		else if( column == 5 )	return Double.class;
		else if( column == 6 )	return GregorianCalendar.class;
		else if( column == 7 )	return String.class;
		else if( column == 8 )	return String.class;
		else if( column == 9 )	return String.class;
		else					throw new IllegalArgumentException( "purchase.getColumnClass" );
		
	}
	public boolean isCellEditable( int column ) {
		
		return false;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}








