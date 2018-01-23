
package tables;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import myLibrary.Methods;
import table.MyTable;
import table.MyTableModel;
import basic.Product;
import basic.Purchase;

public class ProductTable extends MyTable {
	
	Product dummy = new Product( new Purchase() );
	public MyTableModel model = new MyTableModel( dummy );
	
	
	private boolean showGroupCount = true;
	
	public ProductTable(){
		
		
		this.setModel( model );
		this.updateColumnHeaders();
		this.setHeaderHeight( 26 );
		this.getTableHeader().setOpaque( false );
		
		
		this.getColumnModel().getColumn( 0 ).setPreferredWidth( 30 );
		this.getColumnModel().getColumn( 1 ).setPreferredWidth( 170 );
		this.getColumnModel().getColumn( 2 ).setPreferredWidth( 70 );
		this.getColumnModel().getColumn( 3 ).setPreferredWidth( 70 );
		this.getColumnModel().getColumn( 4 ).setPreferredWidth( 70 );
		this.getColumnModel().getColumn( 5 ).setPreferredWidth( 70 );
		this.getColumnModel().getColumn( 6 ).setPreferredWidth( 70 );
		this.getColumnModel().getColumn( 7 ).setPreferredWidth( 70 );
		this.getColumnModel().getColumn( 8 ).setPreferredWidth( 122 );//bought from
		this.getColumnModel().getColumn( 9 ).setPreferredWidth( 150 );//sold to
		
		
		
		
		//this.setRowHeight( 20 );//default = 16
		
		
		this.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		this.setFocusColor( new Color(0,0,0,0) );
		
		
	}
	
	public boolean showGroupCount(){
		
		return this.showGroupCount;
		
	}
	public void setShowGroupCount( boolean showGroupCount ){
		
		this.showGroupCount = showGroupCount;
		
	}
	
	public void addProducts( ArrayList<Product> products ){
		
		
		for( int actualR=0; actualR<products.size(); actualR++ )
		{
			model.addRow( products.get( actualR ) );
		}
		
	}
	public void setProducts( ArrayList<Product> products ){
		
		model.removeAllRows();
		for( int actualR=0; actualR<products.size(); actualR++ )
		{
			model.addRow( products.get( actualR ) );
		}
		
		
		
		
	}
	public void removeProducts( ArrayList<Product> products ){
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			this.model.removeRow( product );
		}
		
	}
	public ArrayList<Product> getAllProducts(){
		
		ArrayList<Product> products = new ArrayList<Product>();
		for( int actualR=0; actualR<this.model.getActualRowCount(); actualR++ )
		{
			Product product = (Product) this.model.getActualRowData( actualR );
			products.add( product );
		}
		
		
		return products;
		
	}
	public ArrayList<Product> getSelectedProducts(){
		
		ArrayList<Product> products = new ArrayList<Product>();
		int[] displayedRows = this.getSelectedRows();
		for( int i=0; i<displayedRows.length; i++ )
		{
			int displayedR = displayedRows[i];
			Product product = (Product) model.getDisplayedRowData( displayedR );
			products.add( product );
		}
		
		
		return products;
		
		
		
		
	}
	public ArrayList<Product> getCheckedProducts(){
				
		
		ArrayList<Product> products = new ArrayList<Product>();
		for( int actualR=0; actualR<model.getActualRowCount(); actualR++ )
		{
			Product product = (Product) model.getActualRowData( actualR );
			if( product.isChecked() )
			{
				products.add( product );
			}
		}
		
		
		return products;
		
	}
	
	public void uncheckAll(){
		
		ArrayList<Product> checkedProducts = this.getCheckedProducts();
		for( int i=0; i<checkedProducts.size(); i++ )
		{
			Product product = checkedProducts.get( i );
			product.setChecked( false );
		}
		
	}
	
	public ArrayList<Integer> getActualRows( ArrayList<Product> products ){
		
		ArrayList<Integer> actualRows = new ArrayList<Integer>();
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			int displayedRow = model.getActualIndexOf( product );
			actualRows.add( displayedRow );
			
		}
		
		return actualRows;
	}
	
	public String[] getProductNameList(){
		
		ArrayList<String> nameList = new ArrayList<String>();
		for( int actualR=0; actualR<this.model.getActualRowCount(); actualR++ )
		{
			Product product = (Product) this.model.getActualRowData( actualR );
			String newName = product.getName();
			if( nameList.indexOf( newName ) == -1 )
			{
				nameList.add( newName );
			}
		}
		
		String[] nameArray = new String[nameList.size()];
		nameList.toArray( nameArray );		
		
		return nameArray;
		
	}
	
	
	
	
	
	public TableCellRenderer getCellRenderer( int displayedR , int displayedC ) {
		
		int actualC = model.getActualC( displayedC );
		
		
		if( actualC == 0 )			return new BooleanRenderer( true );
		else if( actualC == 1 )		return new ProductNameRenderer();//name
		else if( actualC == 2 )		return new NumberRenderer( SwingConstants.CENTER , "$#,##0.00" );//purchase price
		else if( actualC == 3 )		return super.getCellRenderer( displayedR , displayedC );//purchase date
		else if( actualC == 4 )		return super.getCellRenderer( displayedR , displayedC );//received date
		else if( actualC == 5 )		return super.getCellRenderer( displayedR , displayedC );//sale date
		else if( actualC == 6 )		return super.getCellRenderer( displayedR , displayedC );//status
		else if( actualC == 7 )		return super.getCellRenderer( displayedR , displayedC );//category
		else if( actualC == 8 )		return new StringRenderer( this.getAlignment() , "????" , Color.red , new Color( 170,0,0 ) );//seller
		else if( actualC == 9 )		return new StringRenderer( this.getAlignment() , "????" , Color.red , new Color( 170,0,0 ) );//buyer
		else						return super.getCellRenderer( displayedR , displayedC );
		
		
		
//		if( actualC == 0 )			return booleanRenderer;
//		else if( actualC == 1 )		return stringRenderer;//name
//		else if( actualC == 2 )		return numberRenderer;//purchase price
//		else if( actualC == 3 )		return super.getCellRenderer( displayedR , displayedC );//purchase date
//		else if( actualC == 4 )		return super.getCellRenderer( displayedR , displayedC );//received date
//		else if( actualC == 5 )		return super.getCellRenderer( displayedR , displayedC );//sale date
//		else if( actualC == 6 )		return super.getCellRenderer( displayedR , displayedC );//status
//		else if( actualC == 7 )		return super.getCellRenderer( displayedR , displayedC );//category
//		else						return super.getCellRenderer( displayedR , displayedC );
		
		
	}
	private class ProductNameRenderer implements TableCellRenderer{

		
		public Component getTableCellRendererComponent( JTable table , Object value , boolean isSelected , boolean hasFocus , int displayedR , int displayedC  ) {
			
			
			ProductTable productTable = (ProductTable) table;
			StringRenderer stringRenderer = new StringRenderer( productTable.getAlignment() , "????" , Color.red , new Color( 170,0,0 ) );
			JLabel label = (JLabel) stringRenderer.getTableCellRendererComponent( table , value , isSelected , hasFocus , displayedR  , displayedC  );
			
			Product product = (Product) productTable.model.getDisplayedRowData( displayedR  );
			
			//System.out.println( "showGroupCount = " + productTable.showGroupCount() );
			if( !productTable.showGroupCount() )
			{
				label.setText( product.getName() );
			}
			
			return label;
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		
		
        ProductTable table = new ProductTable();
		
		
		
		
        
		
        
		
        JFrame frame = new JFrame();
		
		JScrollPane sp = new JScrollPane( table );
		
        frame.add( sp , BorderLayout.CENTER );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		frame.setLocationRelativeTo( null );
        frame.setVisible(true);

        


    }
	
	
	
	
	
	
}









