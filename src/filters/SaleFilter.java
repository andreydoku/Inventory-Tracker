
package filters;

import RowFilters.MyRowFilter;
import basic.Sale;
import java.util.GregorianCalendar;
import table.RowData;


public class SaleFilter implements MyRowFilter {
	
	
	private GregorianCalendar date = new GregorianCalendar();
	
	
	public boolean isRowHidden( RowData data ) {
		
		Sale sale = (Sale) data;
		
		int selectedYear = date.get( GregorianCalendar.YEAR );
		int selectedMonth = date.get( GregorianCalendar.MONTH );
		
		int saleYear = sale.getSaleDate().get( GregorianCalendar.YEAR );
		int saleMonth = sale.getSaleDate().get( GregorianCalendar.MONTH );
		
		boolean sameMonthAndYear = selectedMonth == saleMonth && selectedYear == saleYear;
		return !sameMonthAndYear;
		
		//return selectedYear != saleYear && selectedMonth != saleMonth;
		
	}
	
	
	public void setDate( GregorianCalendar date ){
		
		this.date = date;
		
	}
	public GregorianCalendar getDate(){
		
		return this.date;
		
	}
	
	
	
}
