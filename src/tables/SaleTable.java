
package tables;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import myLibrary.ArrayToString;
import myLibrary.Methods;
import table.MyTable;
import table.MyTableModel;
import basic.*;


public class SaleTable extends MyTable {
	
	
	
	private static Sale dummy = new Sale( new Product(new Purchase( "" , 0 , "" , 0 , new GregorianCalendar(1,1,1) , new GregorianCalendar(1,1,1) , "" )) );
	public MyTableModel model = new MyTableModel( dummy );
	
	public SaleTable(){
		
		
		this.setModel( model );
		this.updateColumnHeaders();
		//this.setRowHeight( 16 );
		
		// 200 , 75 , 75 , 75 , 75 , 75
		this.getColumnModel().getColumn( 0 ).setPreferredWidth( 200 );//products
		this.getColumnModel().getColumn( 1 ).setPreferredWidth( 75 );//sale $
		this.getColumnModel().getColumn( 2 ).setPreferredWidth( 75 );//shipping $
		this.getColumnModel().getColumn( 3 ).setPreferredWidth( 75 );//Mod $
		this.getColumnModel().getColumn( 4 ).setPreferredWidth( 75 );//Purchase $
		this.getColumnModel().getColumn( 5 ).setPreferredWidth( 75 );//Revenue $
		this.getColumnModel().getColumn( 6 ).setPreferredWidth( 75 );//Sale Date
		this.getColumnModel().getColumn( 7 ).setPreferredWidth( 110 );//Sold To
		this.getColumnModel().getColumn( 8 ).setPreferredWidth( 75 );//Sale ID
		this.getColumnModel().getColumn( 9 ).setPreferredWidth( 75 );//Tracking #
		
		this.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		//this.setFocusColor( this.getSelectionBackground() );
		this.setFocusColor( new Color(0,0,0,0) );
		
		this.setHeaderHeight( 26 );
		
	}
	public TableCellRenderer getCellRenderer( int displayedR , int displayedC ) {
		
		
		
		int actualC = model.getActualC( displayedC );
		
		if( actualC == 0 )
		{
			return new ProductListRenderer();
		}
		else if( actualC == 1 || actualC == 2 || actualC == 3 || actualC == 4 || actualC == 5 )
		{
			return new NumberRenderer( SwingConstants.CENTER , "$#,##0.00" );
		}
		else if( actualC == 7 || actualC == 8 || actualC == 9 )
		{
			return new StringRenderer( SwingConstants.CENTER , "????" , new Color( 250,0,0 ) , new Color( 150,0,0 ) );
		}
		else
		{
			return super.getCellRenderer( displayedR , displayedC );
		}
		
		
	}
	
	
	
	public Sale getSelectedSale(){
		
		int displayedR = this.getSelectedRow();
		if( displayedR != -1 )
		{
			return (Sale) this.model.getDisplayedRowData( displayedR );
		}
		else
		{
			return null;
		}
		
		
	}
	public ArrayList<Sale> getAllSales(){
		
		ArrayList<Sale> sales = new ArrayList<Sale>();
		for( int actualR=0; actualR<this.model.getActualRowCount(); actualR++ )
		{
			Sale sale = (Sale) this.model.getActualRowData( actualR );
			sales.add( sale );
		}
		
		
		return sales;
		
	}
	public double getTotalRevenue(){
		
		double totalRevenue = 0;
		for( int actualR=0; actualR<this.model.getActualRowCount(); actualR++ )
		{
			Sale sale = (Sale) this.model.getActualRowData( actualR );
			totalRevenue += sale.getRevenue();
		}
		
		return totalRevenue;
		
	}
	public double getMonthlyRevenue( GregorianCalendar date ){
		
		
		
		double totalRevenue = 0;
		for( int actualR=0; actualR<this.model.getActualRowCount(); actualR++ )
		{
			Sale sale = (Sale) this.model.getActualRowData( actualR );
			if( Methods.isSameMonth( date , sale.getSaleDate() )  )
			{
				totalRevenue += sale.getRevenue();
			}
			
		}
		
		return totalRevenue;
		
	}
	
	private class ProductListRenderer implements TableCellRenderer {
		
		
		
		public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
			
			
			
			ArrayList<Product> products = (ArrayList<Product>) value;
			String string = ArrayToString.getString( products, ", " );
			
			StringRenderer sr = new StringRenderer( SwingConstants.CENTER );
			JLabel label = (JLabel) sr.getTableCellRendererComponent( table , string , isSelected , hasFocus , row , column );
			
			return label;
			
			
			
		}
		
		
			
		
		
		
	}
	
	
	
	
	
	
	
}











