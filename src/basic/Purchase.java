
package basic;

import java.text.*;
import java.util.*;

import myLibrary.Methods;
import table.RowData;
import exceptions.*;


public final class Purchase implements RowData {
	
	
	private ArrayList<Product> products;
	
	private String productName;
	private int quantity;
	private String category;
	private double totalPurchasePrice;
	private GregorianCalendar purchaseDate,receivedDate;
	private String seller;
	private String purchaseID;
	private boolean isGrouped;
	
	
	public Purchase(){
		
		this( "new product" , 1 , Purchase.getBasicCategoryList()[0] , 100 , (GregorianCalendar)GregorianCalendar.getInstance() , null );
		
		
	}
	public Purchase( String productName , int quantity , String category , double purchasePrice , GregorianCalendar purchaseDate , String seller ){
		
		this( productName , quantity , category , purchasePrice , purchaseDate , null , seller );
		
	}
	public Purchase( String productName , int quantity , String category , double purchasePrice , GregorianCalendar purchaseDate , GregorianCalendar receivedDate , String seller ){
		
		
		this.productName = productName;
		this.quantity = quantity;
		this.category = category;
		this.totalPurchasePrice = purchasePrice;
		this.purchaseDate = purchaseDate;
		this.receivedDate = receivedDate;
		this.seller = seller;
		this.isGrouped = false;
		
		products = new ArrayList<Product>();
		for( int i=0; i<quantity; i++ )
		{
			
			Product product = new Product( this );
			this.products.add( product );
			
		}
		
		
	}
	public Purchase( Purchase oldPurchase ) {
		
		
		try
		{
			//this( purchase.getPurchaseDate() , purchase.getProductName() , purchase.getQuantity() , purchase.getTotalPurchasePrice() , purchase.getSeller() , purchase.getReceivedDate() );
			
			//this.products = purchase.products;
			//this.setQuantity( purchase.quantity );
			
			
			//this.setProducts( purchase.products );
			ArrayList<Product> newProducts = new ArrayList<Product>();
			for( int i=0; i<oldPurchase.getProducts().size(); i++ )
			{
				Product oldProduct = oldPurchase.getProducts().get( i );
				Product newProduct = new Product( oldProduct );//newProduct points at oldPurchase and oldSale 
				
				//change sale reference to the new product copy
				if( oldProduct.isSold() )
				{
					Sale sale = oldProduct.getSale();
					int oldProductIndex = sale.getProducts().indexOf( oldProduct );
					sale.getProducts().set( oldProductIndex , newProduct );
				}
				
				newProducts.add( newProduct );
			}
			this.setProducts( newProducts );//points both ways
			
			
			
			
			this.setProductName( oldPurchase.productName );
			this.setCategory( oldPurchase.category );
			this.setPurchasePrice( oldPurchase.totalPurchasePrice );
			this.setPurchaseDate( oldPurchase.purchaseDate );
			this.setReceivedDate( oldPurchase.receivedDate );
			this.setSeller( oldPurchase.seller );
			this.setPurchaseID( oldPurchase.purchaseID );
			this.setGrouped( oldPurchase.isGrouped );
			
			
		} 
		catch ( NotReceivedButSoldException ex )
		{
			ex.printStackTrace();
		}
		catch ( ReceivedAfterSoldException ex )
		{
			ex.printStackTrace();
		}
		catch ( PurchaseAfterReceivedException ex )
		{
			ex.printStackTrace();
		}
		
		
		
		
	}
	public Purchase( String purchaseText , ArrayList<Product> allProducts ) throws FailedToLoadException{
		
		productName = "";
		quantity = 1;
		category = Purchase.getBasicCategoryList()[0];
		totalPurchasePrice = 0;
		purchaseDate = null;
		receivedDate = null;
		seller = "";
		purchaseID = "";
		
		applySaveString( purchaseText , allProducts );
		
		
	}
	
	
	//get methods
	public ArrayList<Product> getProducts(){
		
		return this.products;
		
	}
	public int getProductCount(){
		
		return products.size();
		
	}
	public Product getProduct( int i ){
		
		return products.get( i );
		
	}
	public boolean contains( Product product ){
		
		return products.contains( product );
		
	}
	public int indexOf( Product product ){
		
		return products.indexOf( product );
		
	}
	
	public String getProductName(){
		
		return this.productName;
		
	}
	public int getQuantity(){
		
		return this.quantity;
		
	}
	public String getCategory(){
		
		return this.category;
		
	}
	public double getTotalPurchasePrice(){
		
		return this.totalPurchasePrice;
		
	}
	public GregorianCalendar getPurchaseDate(){
		
		return this.purchaseDate;
		
	}
	public GregorianCalendar getReceivedDate(){
		
		return this.receivedDate;
		
	}
	public String getSeller(){
		
		return this.seller;
		
	}
	public String getPurchaseID(){
		
		return this.purchaseID;
		
	}
	public boolean isGrouped(){
		
		return this.isGrouped;
		
	}
	
	public boolean isAllSold(){
		
		for( int i=0; i<products.size(); i++ )
		{
			if( !products.get( i ).isSold() )
			{
				return false;
			}
		}
		
		return true;
		
	}
	
	
	public int getReceivedCount(){
		
		int count = 0;
		
		for( int i=0; i<products.size(); i++ )
		{
			Product p = products.get( i );
			if( p.isReceived() && !p.isSold() )
			{
				count++;
			}
		}
		
		return count;
		
	}
	public int getUnsoldCount(){
		
		int count = 0;
		
		for( int i=0; i<products.size(); i++ )
		{
			Product p = products.get( i );
			if( !p.isSold() )
			{
				count++;
			}
		}
		
		return count;
		
	}
	public Product getRepresentative(){
		
		for( int i=0; i<products.size(); i++ )
		{
			Product p = products.get( i );
			if( !p.isSold() )
			{
				return p;
			}
		}
		
		return products.get( 0 );
		
	}
	
	//set
	public void setProducts( ArrayList<Product> products ){
		
		this.products = new ArrayList<Product>();
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			product.setPurchase( this );
			this.products.add( product );
			
		}
		
		
		this.quantity = products.size();
		
	}
	public void setProduct( int index , Product product ){
		
		products.set( index , product );
		
	}
	
	public void setProductName( String productName ){
		
		if( !productName.equals( this.productName ) )
		{
			this.productName = productName;
			for( int i=0; i<quantity; i++ )
			{
				products.get( i ).setName( productName );
			}
		}
			
		
	}
	public void setQuantity( int newQuantity ) throws RemovingSoldProductException{
		
		
		if( newQuantity != this.quantity )
		{
			boolean debug = false;

			if( debug ) System.out.println( "setQuantity( " + newQuantity + " )" );
			if( debug ) System.out.println( "---" + this.quantity + " => " + newQuantity );

			if( newQuantity <= 0 )
			{
				throw new IllegalArgumentException( "quantity cannot be <= 0" );
			}

			if( newQuantity > this.quantity )
			{
				if( debug ) System.out.println( "---newQuantity > this.quantity" );
				if( debug ) System.out.println( "---products.size() before: " + products.size() );
				for( int i=0; i<newQuantity-this.quantity; i++ )
				{
					Product product = new Product( this );
					this.products.add( product );
				}
				if( debug ) System.out.println( "---products.size() after: " + products.size() );
			}
			else if( newQuantity < this.quantity )
			{
				if( debug ) System.out.println( "---newQuantity < this.quantity" );
				if( debug ) System.out.println( "---products.size() before: " + products.size() );
				for( int i=this.quantity-1; i>this.quantity-newQuantity; i-- )
				{
					Product product = products.get( i );
					if( product.isSold() )
					{
						throw new RemovingSoldProductException();
					}

					this.products.remove( product );
				}
				if( debug ) System.out.println( "---products.size() after: " + products.size() );
			}
			if( debug ) System.out.println( "end of setQuantity( int newQuantity )" + "\n" );


			this.quantity = newQuantity;
		}
		
			
		
	}
	public void setCategory( String category ){
		
		this.category = category;
		
	}
	public void setPurchasePrice( double purchasePrice ){
		
		this.totalPurchasePrice = purchasePrice;
		
		
		
	}
	public void setPurchaseDate( GregorianCalendar purchaseDate ) throws PurchaseAfterReceivedException{
		
		if( this.purchaseDate == null )
		{
			this.purchaseDate = purchaseDate;
		}
		else
		{
			if( ! Methods.isSameDay( purchaseDate , this.purchaseDate ) )
			{
				if( purchaseDate.after( this.receivedDate ) )
				{
					throw new PurchaseAfterReceivedException();
				}
				else
				{
					this.purchaseDate = purchaseDate;
				}
			}
		}
		
			
		
			
		
	}
	public void setReceivedDate( GregorianCalendar receivedDate ) throws PurchaseAfterReceivedException, ReceivedAfterSoldException, NotReceivedButSoldException{
		
		
		//if( this.purchaseDate.after( receivedDate ) )
		
		if( receivedDate != null )
		{
			
			if( this.isReceived() && Methods.isSameDay( receivedDate, this.receivedDate ) )
			{
				return;
			}
			if( Methods.isOnLaterDayThan( this.purchaseDate , receivedDate  ) )
			{
				throw new PurchaseAfterReceivedException();
			}
			else
			{
				for( int i=0; i<products.size(); i++ )
				{
					Product product = products.get( i );
					if( product.isSold() )
					{
						if( Methods.isOnLaterDayThan( receivedDate , product.getSaleDate()  )   )
						{
							throw new ReceivedAfterSoldException();
						}
					}

				}


				this.receivedDate = receivedDate;
			}
		}
		else
		{
			for( int i=0; i<products.size(); i++ )
			{
				Product product = products.get( i );
				if( product.isSold() )
				{
					throw new NotReceivedButSoldException();
				}
			}
			
			this.receivedDate = null;
			
		}
		
			
		
	}
	public void setSeller( String seller ){
		
		
		this.seller = seller;
		
		
		
	}
	public void setPurchaseID( String purchaseID ){
		
		this.purchaseID = purchaseID;
		
	}
	public void setGrouped( boolean grouped ){
		
		if( this.getProductCount() == 1 && grouped )
		{
			throw new IllegalArgumentException( "Cannot group a purchase with only 1 product" );
		}
		
		this.isGrouped = grouped;
		
		//if changed to be grouped, then uncheck all, and if at least one of them was checked, then check the representative
		if( grouped )
		{
			boolean atLeastOneChecked = false;
			for( int i=0; i<products.size(); i++ )
			{
				Product product = products.get( i );
				if( product.isChecked() )
				{
					product.setChecked( false );
					atLeastOneChecked = true;
				}
			}
			
			if( atLeastOneChecked )
			{
				getRepresentative().setChecked( true );
			}
			
		}
		
			
		
		
	}
	
	
	public boolean isReceived(){
		
		return receivedDate != null;
		
	}
	public double getIndividualPurchasePrice(){
		
		return this.totalPurchasePrice / getQuantity();
		
	}
	
	
	public String toString(){
		
		String nl = "\n";
		
		String output = "";
		output += "Product Name: " + productName + nl;
		output += "Quantity: " + quantity + nl;
		output += "Category: " + category + nl;
		output += "Total Purchase Price: " + new DecimalFormat("$#,##0.00").format( totalPurchasePrice ) + nl;
		//output += "PurchaseDate: " + new SimpleDateFormat("MM/dd/yy").format( purchaseDate.getTime() ) + nl;
		if( purchaseDate != null )
		{
			output += "Purchase Date: " + new SimpleDateFormat("MM/dd/yy").format( purchaseDate.getTime() ) + nl;
		}
		else
		{
			output += "Purchase Date: " + "----" + nl;
		}
		if( receivedDate != null )
		{
			output += "Received Date: " + new SimpleDateFormat("MM/dd/yy").format( receivedDate.getTime() ) + nl;
		}
		else
		{
			output += "Received Date: " + "----" + nl;
		}
		output += "Seller: " + seller + nl;
		output += "Purchase ID: " + purchaseID + nl;
		
		
		
		return output;
		
	}
	
	public String toSaveString( ArrayList<Product> allProducts ){
		
		String output = "";
		String nl = "\n";
		
		/*
		private String productName;
		private int quantity;
		private double totalPurchasePrice;
		private GregorianCalendar purchaseDate,receivedDate;
		private String seller;
		private String miscInfo;
		*/
		
		
		output += "Product Name: " + ( ( productName == null ) ? "" : productName.toString() ) + nl;
		output += "Quantity: " + this.quantity + nl;
		output += "Category: " + ( ( category == null ) ? "" : category.toString() ) + nl;
		output += "Total Purchase Price: " + new DecimalFormat("$#,##0.00").format( this.totalPurchasePrice ) + nl;
		output += "Purchase Date: " + new SimpleDateFormat("MM/dd/yy").format( purchaseDate.getTime() ) + nl;
		if( receivedDate != null )
		{
			output += "Received Date: " + new SimpleDateFormat("MM/dd/yy").format( receivedDate.getTime() ) + nl;
		}
		else
		{
			output += "Received Date: " + "----" + "\n" + nl;
		}
		output += "Seller: "      + ( seller     == null  ? "" : seller.toString())     + nl;
		output += "Purchase ID: " + ( purchaseID == null  ? "" : purchaseID.toString()) + nl;
		output += "Grouped: " + isGrouped + nl;
		
		
		
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
			throw new FailedToLoadException( "Failed to load purchase data (1)"   );
						
		}
		
		String parameter = piece.substring( 0 , delimIndex );
		String value = piece.substring( delimIndex + delim.length() ).trim();
		
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
					throw new FailedToLoadException( "Failed to load purchase data - Invalid indexes"   );
				}
				
				
			}
			
			this.setProducts( newProducts );
			
		}
		else if( parameter.equals( "Product Name" ) )
		{
			this.productName = value;
		}
		else if( parameter.equals( "Quantity" ) )
		{
			try
			{
				int quantity = Integer.parseInt( value );
				if( quantity <0 )
				{
					throw new FailedToLoadException( "Failed to load purchase data - Invalid quantity"   );
				}
				this.quantity = quantity;
				
			}
			catch( NumberFormatException nfe )
			{
				throw new FailedToLoadException( "Failed to load purchase data - Invalid quantity"   );
			}
			
		}
		else if( parameter.equals( "Category" ) )
		{
			this.category = value;
		}
		else if( parameter.equals( "Total Purchase Price" ) )
		{
			try
			{
				value = value.replace( "$" , "" );
				value = value.replace( "," , "" );
				
				
				double price = Double.parseDouble( value );
				if( price < 0 )
				{
					throw new FailedToLoadException( "Failed to load purchase data - Invalid purchase price"   );
				}
				this.totalPurchasePrice = price;
				
			}
			catch( NumberFormatException nfe )
			{
				throw new FailedToLoadException( "Failed to load purchase data - Invalid purchase price"   );
			}
			
		}
		else if( parameter.equals( "Purchase Date" ) )
		{
			try
			{
				GregorianCalendar newPurchaseDate = (GregorianCalendar) GregorianCalendar.getInstance();
				Date date = new SimpleDateFormat("MM/dd/yy").parse( value );
				newPurchaseDate.setTime( date );
				
				
				this.purchaseDate = newPurchaseDate;
				
			} 
			catch ( ParseException ex )
			{
				throw new FailedToLoadException( "Failed to load purchase data - Invalid purchase date"   );
			}
		}
		else if( parameter.equals( "Received Date" ) )
		{
			
			if( value.equals("----") )
			{
				this.receivedDate = null;
			}
			else
			{
				try
				{
					GregorianCalendar newReceivedDate = (GregorianCalendar) GregorianCalendar.getInstance();
					Date date = new SimpleDateFormat("MM/dd/yy").parse( value );
					newReceivedDate.setTime( date );

					this.receivedDate = newReceivedDate;
				} 
				catch ( ParseException ex )
				{
					throw new FailedToLoadException( "Failed to load purchase data - Invalid received date"   );
				}
			}
			
				
		}
		else if( parameter.equals( "Seller" ) )
		{
			this.seller = value;
		}
		else if( parameter.equals( "Purchase ID" ) )
		{
			this.purchaseID = value;
		}
		else if( parameter.equals( "Grouped" ) )
		{
			this.isGrouped = value.equals( "true" );
		}
		else if( parameter.equals( "Misc Info" ) )
		{
			
		}
		else
		{
			throw new FailedToLoadException( "Failed to load purchase data, Unknown parameter: " + parameter );
		}
		
		
		
	}
	
	public static ArrayList<Purchase> getPurchases( ArrayList<String> purchaseTextList , ArrayList<Product> products ) throws FailedToLoadException{
		
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		for( int i=0; i<purchaseTextList.size(); i++ )
		{
			String purchaseText = purchaseTextList.get( i );
			Purchase purchase = new Purchase( purchaseText , products );
			purchases.add( purchase );
		}
		
		
		return purchases;
		
	}
	public static String[] getBasicCategoryList(){
		
		String[] basicCategoryList = { "Base" , "Mod" , "Accessory" , "Extra" };
		return basicCategoryList;
		
	}
	
	
	
	
	
	//RowData
	public Object getValueAt( int column ) {
		
		if( column == 0 )
		{
			return this.purchaseDate;
		}
		else if( column == 1 )
		{
			return getProductName();
		}
		else if( column == 2 )
		{
			return this.getQuantity();
		}
		else if( column == 3 )
		{
			return this.category;
		}
		else if( column == 4 )
		{
			return this.totalPurchasePrice;
		}
		else if( column == 5 )
		{
			return this.seller;
		}
		else if( column == 6 )
		{
			
			if( isReceived() )
			{
				return getReceivedDate();
			}
			else
			{
				return null;
			}
			
		}
		else if( column == 7 )
		{
			return this.purchaseID;
		}
		else if( column == 8 )
		{
			return this.isAllSold();
		}
		else
		{
			throw new IllegalArgumentException( "purchase.getValueAt()" );
		}
		
	}
	public void setValueAt( int column , Object object ) {
		throw new UnsupportedOperationException( "Not supported yet." );
	}
	public int getColumnCount() {
		
		return 9;
		
	}
	public ArrayList<String> getColumnNames() {
		
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add( "Ordered" );
		columnNames.add( "Product" );
		columnNames.add( "#" );
		columnNames.add( "Category" );
		columnNames.add( "Price" );
		columnNames.add( "Seller" );
		columnNames.add( "Received" );
		columnNames.add( "Purchase ID" );
		columnNames.add( "All Sold" );
		
		return columnNames;
		
	}
	public Class<?> getColumnClass( int column ) {
		
		if( column == 0 )		return GregorianCalendar.class;
		else if( column == 1 )	return String.class;
		else if( column == 2 )	return Integer.class;
		else if( column == 3 )	return String.class;
		else if( column == 4 )	return Double.class;
		else if( column == 5 )	return String.class;
		else if( column == 6 )	return GregorianCalendar.class;
		else if( column == 7 )	return String.class;
		else if( column == 8 )	return Boolean.class;
		else					throw new IllegalArgumentException( "purchase.getColumnClass" );
		
	}
	public boolean isCellEditable( int column ) {
		
		return false;
		
	}
	
	
	
}










