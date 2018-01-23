
package tables;

import java.awt.Color;
import java.util.*;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import table.MyTable;
import table.MyTableModel;
import basic.Product;
import basic.Purchase;


public class PurchaseTable extends MyTable {
	
	private static Purchase dummy = new Purchase( "" , 0 , "" , 0 , new GregorianCalendar(1,1,1) , new GregorianCalendar(1,1,1) , "" );
	public MyTableModel model = new MyTableModel( dummy );
	
	
	
	
	public PurchaseTable(){
		
		
		this.setModel( model );
		this.updateColumnHeaders();
		this.setHeaderHeight( 26 );
		//this.setRowHeight( 16 );
		
		
		// 70 , 170 , 35 , 70 , 110 , 70 , 100
		this.getColumnModel().getColumn( 0 ).setPreferredWidth( 70 );//ordered date
		this.getColumnModel().getColumn( 1 ).setPreferredWidth( 170 );//product name
		this.getColumnModel().getColumn( 2 ).setPreferredWidth( 35 );//#
		this.getColumnModel().getColumn( 3 ).setPreferredWidth( 70 );//category
		this.getColumnModel().getColumn( 4 ).setPreferredWidth( 70 );//price
		this.getColumnModel().getColumn( 5 ).setPreferredWidth( 110 );//seller
		this.getColumnModel().getColumn( 6 ).setPreferredWidth( 70 );//received date
		this.getColumnModel().getColumn( 7 ).setPreferredWidth( 110 );//purchase ID
		this.getColumnModel().getColumn( 8 ).setPreferredWidth( 50 );//all sold
		
		
		this.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.setFocusColor( new Color(0,0,0,0) );
		
		
		
		
	}
	
	public TableCellRenderer getCellRenderer( int displayedR , int displayedC ) {
		
		int actualC = model.getActualC( displayedC );
		
		if( actualC == 0 )		return super.getCellRenderer( displayedR , displayedC );//ordered date
		else if( actualC == 1 )	return new StringRenderer( this.getAlignment() , "????" , Color.red , new Color( 170,0,0 ) );//product name
		else if( actualC == 2 )	return super.getCellRenderer( displayedR , displayedC );//#
		else if( actualC == 3 )	return new StringRenderer( this.getAlignment() , "????" , Color.red , new Color( 170,0,0 ) );//Category
		else if( actualC == 4 )	return new NumberRenderer( SwingConstants.CENTER , "$#,##0.00" );//Price
		else if( actualC == 5 )	return new StringRenderer( this.getAlignment() , "????" , Color.red , new Color( 170,0,0 ) );//Seller
		else if( actualC == 6 )	return super.getCellRenderer( displayedR , displayedC );//Received date
		else if( actualC == 7 )	return new StringRenderer( this.getAlignment() , "????" , Color.red , new Color( 170,0,0 ) );//purchase ID
		else if( actualC == 8 )	return new BooleanRenderer( false );//All sold
		else					throw new IllegalArgumentException("getCellRenderer");
		
	}
	
	
	public void add( Purchase purchase ){
		
		model.addRow( purchase );
		
	}
	
	public Purchase getActualPurchase( int actualR ){
		
		return (Purchase) model.getActualRowData( actualR );
		
	}
	public ArrayList<Purchase> getAllPurchases(){
		
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		
		for( int actualR=0; actualR<model.getActualRowCount(); actualR++ )
		{
			purchases.add( (Purchase)model.getActualRowData( actualR ) );
		}
		
		return purchases;
		
	}
	public ArrayList<Product> getAllProducts(){
		
		ArrayList<Product> allProducts = new ArrayList<Product>();
		
		for( int actualR=0; actualR<model.getActualRowCount(); actualR++ )
		{
			Purchase purchase = (Purchase)model.getActualRowData( actualR );
			allProducts.addAll( purchase.getProducts() );
		}
		
		return allProducts;
		
	}
	public Purchase getSelectedPurchase(){
		
		int displayedR = this.getSelectedRow();
		if( displayedR != -1 )
		{
			return (Purchase) this.model.getDisplayedRowData( displayedR );
		}
		else
		{
			return null;
		}
		
		
	}
	
	
	
	public String[] getCategoryList(){
		
		ArrayList<String> categoryList = new ArrayList<String>();
		String[] basicCategoryList = Purchase.getBasicCategoryList();
		categoryList.addAll( Arrays.asList( basicCategoryList ) );
		
		
		for( int actualR=0; actualR<this.model.getActualRowCount(); actualR++ )
		{
			Purchase purchase = (Purchase) this.model.getActualRowData( actualR );
			String newCategory = purchase.getCategory();
			if( categoryList.indexOf( newCategory ) == -1 )
			{
				categoryList.add( newCategory );
			}
		}
		
		String[] categoryArray = new String[ categoryList.size() ];
		categoryList.toArray( categoryArray );		
		
		return categoryArray;
		
	}
	
	
	
	
	
}







