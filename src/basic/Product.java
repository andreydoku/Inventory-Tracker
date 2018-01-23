
package basic;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import myLibrary.Methods;
import table.RowData;
import exceptions.FailedToLoadException;


public class Product implements RowData {
	
	
	private String name;
	private boolean checked;
	
	private Purchase purchase;
	private Sale sale;
	
	
	//constructors
	public Product( Purchase purchase ){
		
		if( purchase != null )
		{
			this.name = purchase.getProductName();
			this.purchase = purchase;
			this.sale = null;
			this.checked = false;
		}
			
		
	}
	public Product( Product product ){
		
		this.name = product.name;
		this.checked = product.isChecked();
		
		this.purchase = product.purchase;
		this.sale = product.sale;
		
	}
	public Product( String saveString ) throws FailedToLoadException{
		
		applySaveString( saveString );
		
		//purchase and sale will be set when they are loaded, products are loaded first
		purchase = null;
		sale = null;
		this.checked = false;
		
	} 
	
	//get
	public String getName(){
		
		return this.name;
		
	}
	public Purchase getPurchase(){
		
		return this.purchase;
		
	}
	public Sale getSale(){
		
		return this.sale;
		
	}
	public boolean isChecked(){
		
		return this.checked;
		
	}
	
	public double getPurchasePrice(){
		
		return purchase.getIndividualPurchasePrice();
		
	}
	public GregorianCalendar getPurchaseDate(){
		
		return purchase.getPurchaseDate();
		
	}
	public GregorianCalendar getReceivedDate(){
		
		return purchase.getReceivedDate();
		
	}
	public GregorianCalendar getSaleDate(){
		
		if( isSold() )
		{
			return sale.getSaleDate();
		}
		else
		{
			return null;
			
		}
		
		
	}
	public String getBuyer(){
		
		if( sale == null )
		{
			return null;
		}
		
		return sale.getBuyer();
		
	}
	public String getSeller(){
		
		return purchase.getSeller();
		
	}
	
	
	//set
	public void setName( String name ){
		
		this.name = name;
		
	}
	public void setPurchase( Purchase purchase ){
		
		this.purchase = purchase;
		
	}
	public void setSale( Sale sale ){
		
		this.sale = sale;
		
	}
	public void setChecked( boolean selected ){
		
		this.checked = selected;
		
	}
	
	
	public boolean isReceived(){
		
		return this.getReceivedDate() != null;
		
	}
	public boolean isSold(){
		
		return this.sale != null;
		
	}
	public String getStatus(){
		
		if( !this.isReceived() )
		{
			return "Ordered";
		}
		else if( this.isReceived() && !this.isSold() )
		{
			return "Received";
		}
		else
		{
			return "Sold";
		}
				
		
	}
	public String getCategory(){
		
		return this.purchase.getCategory();
		
	}
	
	
	
	
	
	public static ArrayList<String> getNameList( ArrayList<Product> products ){
		
		ArrayList<String> names = new ArrayList<String>();
		
		for( int i=0; i<products.size(); i++ )
		{
			String name = products.get( i ).getName();
			
			if( names.indexOf( name ) == -1 )
			{
				names.add( name );
			}
			
			
		}
		
		return names;
		
		
	}
	
	
	public String toString(){
		
		return name;
		
	}
	
	
	public String toSaveString( int index ){
		
		String output = "";
		String nl = "\n";
		
		output += "Index: " + index + nl;
		output += "Name: " + this.getName() + nl;
		
		return output;
		
		
	}
	public void applySaveString( String saveString ) throws FailedToLoadException{
		
				
		ArrayList<String> pieces = Methods.split( saveString, "\n", false , true , false );
		for( int i=0; i<pieces.size(); i++ )
		{
			String piece = pieces.get( i );
			this.applyPiece( piece );
		}
		
		
		
		
		
	}
	public void applyPiece( String piece ) throws FailedToLoadException{
		
		
		String delim = ":";
		int delimIndex = piece.indexOf( delim );
		
		if( delimIndex == -1 )
		{
			throw new FailedToLoadException( "Failed to load product data (1)"   );
						
		}
		
		String parameter = piece.substring( 0 , delimIndex );
		String value = piece.substring( delimIndex + delim.length() ).trim();
		
		if( parameter.equals( "Index" ) )
		{
			// do nothing
		}
		else if( parameter.equals( "Name" ) )
		{
			this.setName( value );
		}
		else if( parameter.equals( "Misc Info" ) )
		{
			
		}
		else
		{
			throw new FailedToLoadException( "Failed to load product data, Unknown parameter: " + parameter );
		}
		
		
		
	}
	
	
	
	//RowData
	public Object getValueAt( int actualC ) {
		
		
		
		if( actualC == 0 ){
			
			return this.checked;
		}
		else if( actualC == 1 ){
			
			//return this.name;
			
			if( purchase.isGrouped() )
			{
				return this.name + " (" + purchase.getReceivedCount() + "/" + purchase.getQuantity() + ")";
			}
			else
			{
				return this.name;
			}
			
			
		}
		else if( actualC == 2 ){
			
			return this.getPurchasePrice();
			
		}
		else if( actualC == 3 ){
			
			return this.getPurchaseDate();
			
		}
		else if( actualC == 4 ){
			
			return this.getReceivedDate();
			
		}
		else if( actualC == 5 ){
			
			if( this.getPurchase().isGrouped() )
			{
				return null;
			}
			else
			{
				return this.getSaleDate();
			}
			
		}
		else if( actualC == 6 ){
			
			if( this.getPurchase().isGrouped() )
			{
				return "----";
			}
			else
			{
				return this.getStatus();
			}
		}
		else if( actualC == 7 ){
			
			return this.getCategory();
		}
		else if( actualC == 8 ){
			
			return this.getSeller();
			
		}
		else if( actualC == 9 ){
			
			if( this.getPurchase().isGrouped() )
			{
				return "----";
			}
			
			if( !this.isSold() )
			{
				return "----";
			}
			else
			{
				return this.getBuyer();
			}
			
		}
		else
		{
			throw new IllegalArgumentException( "product.getValueAt()" );
		}
		
	}
	public void setValueAt( int actualC , Object value ) {
		
		if( actualC == 0 )
		{
			boolean s = (Boolean)value;
			this.setChecked( s );
			
		}
		else
		{
			throw new UnsupportedOperationException( "Not supported yet." );
		}
		
	}
	public int getColumnCount() {
		
		return 10;
		
	}
	public ArrayList<String> getColumnNames() {
		
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add( "" );
		columnNames.add( "Product Name" );
		columnNames.add( "Buy $" );
		columnNames.add( "Ordered" );
		columnNames.add( "Received" );
		columnNames.add( "Sold" );
		columnNames.add( "Status" );
		columnNames.add( "Category" );
		columnNames.add( "Bought From" );
		columnNames.add( "Sold To" );
		
		
		return columnNames;
		
	}
	public Class<?> getColumnClass( int column ) {
		
		if( column == 0 )		return Boolean.class;
		else if( column == 1 )	return String.class;
		else if( column == 2 )	return Double.class;
		else if( column == 3 )	return GregorianCalendar.class;
		else if( column == 4 )	return GregorianCalendar.class;
		else if( column == 5 )	return GregorianCalendar.class;
		else if( column == 6 )	return String.class;
		else if( column == 7 )	return String.class;
		else if( column == 8 )	return String.class;
		else if( column == 9 )	return String.class;
		else					throw new IllegalArgumentException( "product.getColumnClass(" + column + ")" + "  column must be 0-" + String.valueOf(this.getColumnCount()-1) );
		
	}
	public boolean isCellEditable( int column ) {
		
		return column == 0 && !this.isSold() && this.isReceived();
		
	}
	
	
	
	
	public static ArrayList<Product> getProducts( ArrayList<String> productTextList ) throws FailedToLoadException{
		
		ArrayList<Product> products = new ArrayList<Product>();
		for( int i=0; i<productTextList.size(); i++ )
		{
			String productText = productTextList.get( i );
			Product product = new Product( productText );
			products.add( product );
		}
		
		
		return products;
		
	}

	
	
	
	
	
	
	
	
	
}













