
package filters;

import RowFilters.MyRowFilter;
import basic.Product;
import basic.Purchase;
import table.RowData;


public class ProductFilter implements MyRowFilter {

	//status
	final static int ALL = 0;
	final static int ORDERED_AND_RECEIVED = 1;
	final static int ORDERED = 2;
	final static int RECEIVED = 3;
	final static int SOLD = 4;
	
	//category
	//final static int ALL = 0;
	final static int BASE = 1;
	final static int MOD = 2;
	final static int ACCESSORY = 3;
	final static int EXTRA = 4;
	final static int OTHER = 5;
	
	
	private int statusType = ALL;
	private int categoryType = ALL;
	
	public void setStatusType( int statusType ){
		
		if( statusType != ALL && statusType != ORDERED_AND_RECEIVED && statusType != ORDERED && statusType != RECEIVED && statusType != SOLD )
		{
			throw new IllegalArgumentException( "Illegal status: " + statusType );
		}
		this.statusType = statusType;
		
	}
	public int getStatusType(){
		
		return this.statusType;
		
	}
	
	public void setCategoryType( int categoryType ){
		
		if( categoryType != ALL && categoryType != BASE && categoryType != MOD && categoryType != ACCESSORY && categoryType != EXTRA && categoryType != OTHER )
		{
			throw new IllegalArgumentException( "Illegal category: " + categoryType );
		}
		
		this.categoryType = categoryType;
		
	}
	public int getCategoryType(){
		
		return this.categoryType;
		
	}
	
	
	
	public boolean isRowHidden( RowData data ) {
		
		Product product = (Product) data;
		
		
		return failsStatusTypeFilter( product ) || failsCategoryFilter( product ) || failsGroupFilter( product ) ;
		
		
		
	}
	public boolean failsStatusTypeFilter( Product product ){
		
		
		
		if( this.statusType == ALL )
		{
			return false;
		}
		else if( this.statusType == ORDERED_AND_RECEIVED )
		{
			if( product.getStatus().equals( "Ordered" ) || product.getStatus().equals("Received") )
			{
				return false;
			}
		}
		else if( this.statusType == ORDERED && product.getStatus().equals( "Ordered" ) )
		{
			return false;
		}
		else if( this.statusType == RECEIVED && product.getStatus().equals("Received") )
		{
			return false;
		}
		else if( this.statusType == SOLD && product.getStatus().equals("Sold") )
		{
			return false;
		}
		
		//System.out.println( "status filter, " + "filter = " + this.status + ", status = " + product.getStatus() + ",   fail" );
		return true;
		
	}
	public boolean failsCategoryFilter( Product product ){
		
		if( this.categoryType == ALL )
		{
			return false;
		}
		
		if( product.getCategory().equals("Base") )
		{
			if( this.categoryType == BASE )
			{
				return false;
			}
		}
		else if( product.getCategory().equals("Mod") )
		{
			if( this.categoryType == MOD )
			{
				return false;
			}
		}
		else if( product.getCategory().equals("Accessory") )
		{
			if( this.categoryType == ACCESSORY )
			{
				return false;
			}
		}
		else if( product.getCategory().equals("Extra") )
		{
			if( this.categoryType == EXTRA )
			{
				return false;
			}
		}
		else
		{
			if( this.categoryType == OTHER )
			{
				return false;
			}
		}
		
		return true;
		
	}
	public boolean failsGroupFilter( Product product ){
		
		
		Purchase purchase = product.getPurchase();
		if( !purchase.isGrouped() )
		{
			return false;
		}
		
		if( product == purchase.getRepresentative() )
		{
			return false;
		}
		
		return true;
		
		
	}
	
	
	
	public static String getStatusString( int status ){
		
		if( status == ALL )							return "All";
		else if( status == ORDERED_AND_RECEIVED )	return "Ordered and Received";
		else if( status == ORDERED )				return "Ordered";
		else if( status == RECEIVED )				return "Received";
		else if( status == SOLD )					return "Sold";
		else										throw new IllegalArgumentException( "Illegal status type: " + status );
		
	}
	public static String getCategoryString( int category ){
		
		if( category == ALL )				return "All";
		else if( category == BASE )			return "Base";
		else if( category == MOD )			return "Mod";
		else if( category == ACCESSORY )	return "Accessory";
		else if( category == EXTRA )		return "Extra";
		else if( category == OTHER )		return "Other";
		else								throw new IllegalArgumentException( "Illegal category type: " + category );
		
	}
	
	
	
	
	
}











