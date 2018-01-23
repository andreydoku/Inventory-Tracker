
package panels;

import gui.GradientPanel;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import myLibrary.ArrowIcon;
import myLibrary.Methods;
import table.MyTable;
import tables.SaleTable;

public class SalePanel extends GradientPanel {
	
	
	public JPanel btnPanel = new JPanel();
		JButton editBtn = new JButton();
		JButton deleteBtn = new JButton();
		JButton addToSaleBtn = new JButton();
		JButton viewProductsBtn = new JButton();
		JPanel datePanel = new JPanel();
			JButton leftArrowBtn = new JButton( new ArrowIcon( SwingConstants.WEST ) );
			JComboBox monthBox = new JComboBox();
			JComboBox yearBox = new JComboBox();
			JButton rightArrowBtn = new JButton( new ArrowIcon( SwingConstants.EAST ) );
		JPanel revenuePanel = new JPanel();
			JLabel monthlyRevenueLabel = new JLabel( "This Month" );
			JTextField monthlyRevenueField = new JTextField( 8 );
			JLabel totalRevenueLabel = new JLabel( "Total" );
			JTextField totalRevenueField = new JTextField( 8 );
	JScrollPane tablePanel = new JScrollPane();
		SaleTable saleTable = new SaleTable();
	
		
	private boolean hidden = false;	
	private int buttonSize = 10;
	
	
	
	
		
	public SalePanel(){
		
		build();
		
		
		
	}
	public void build(){
		
		
		
		
		
		
		//tablePanel = MyTable.createStripedJScrollPane( saleTable );
		tablePanel = new JScrollPane(){
			
			public Dimension getPreferredSize(){
				
				return saleTable.getMyPreferredSize( SalePanel.this.getWidth() , SalePanel.this.getHeight() );
				
			}
		
		
		};
		tablePanel.setCorner( JScrollPane.UPPER_RIGHT_CORNER , MyTable.createCornerComponent( saleTable ) );
		tablePanel.setViewportView( saleTable );
		
		
		tablePanel.getViewport().setOpaque( false );
		tablePanel.setOpaque( false );
		//this.setOpaque( false );
		saleTable.setOpaque( false );
		
		
		saleTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		saleTable.setFillsViewportHeight( false );
		
		
		btnPanel.setOpaque( false );
		editBtn.setOpaque( false );
		deleteBtn.setOpaque( false );
		addToSaleBtn.setOpaque( false );
		viewProductsBtn.setOpaque( false );
		
		
		buildMonthBox();
		buildYearBox();
		
		int h = 27;
		//editBtn.setPreferredSize( new Dimension( editBtn.getPreferredSize().width , h+2 ) );
		//deleteBtn.setPreferredSize( new Dimension( deleteBtn.getPreferredSize().width , h+2 ) );
		//addToSaleBtn.setPreferredSize( new Dimension( addToSaleBtn.getPreferredSize().width , h+2 ) );
		//viewProductsBtn.setPreferredSize( new Dimension( viewProductsBtn.getPreferredSize().width , h+2 ) );
		
		leftArrowBtn.setMargin( new Insets(0,0,0,0) );
		rightArrowBtn.setMargin( new Insets(0,0,0,0) );
		
		leftArrowBtn.setBorder( null );
		rightArrowBtn.setBorder( null );
		leftArrowBtn.setPreferredSize( new Dimension( h+2 , h+2 ) );
		rightArrowBtn.setPreferredSize( new Dimension( h+2 , h+2 ) );
		leftArrowBtn.setOpaque( false );
		rightArrowBtn.setOpaque( false );
		monthBox.setPreferredSize( new Dimension( monthBox.getPreferredSize().width , h ) );
		yearBox.setPreferredSize( new Dimension( monthBox.getPreferredSize().width , h ) );
		
		datePanel.setOpaque( false );
		datePanel.setBorder( BorderFactory.createEmptyBorder(0,20,0,20) );
		datePanel.setLayout( new FlowLayout( FlowLayout.CENTER , 1 , 2 ) );
		datePanel.add( leftArrowBtn );
		datePanel.add( monthBox );
		datePanel.add( yearBox );
		datePanel.add( rightArrowBtn );
		
		
		monthlyRevenueField.setEditable( false );
		//monthlyRevenueField.setHorizontalAlignment( SwingConstants.CENTER );
		monthlyRevenueField.setOpaque( false );
		monthlyRevenueField.setBorder( null );
		totalRevenueField.setEditable( false );
		//totalRevenueField.setHorizontalAlignment( SwingConstants.CENTER );
		totalRevenueField.setOpaque( false );
		totalRevenueField.setBorder( null );
		
		revenuePanel.setOpaque( false );
		revenuePanel.add( monthlyRevenueLabel );
		revenuePanel.add( monthlyRevenueField );
		revenuePanel.add( totalRevenueLabel );
		revenuePanel.add( totalRevenueField );
		
		btnPanel.add( editBtn );
		btnPanel.add( deleteBtn );
		btnPanel.add( addToSaleBtn );
		btnPanel.add( viewProductsBtn );
		btnPanel.add( datePanel );
		btnPanel.add( revenuePanel );
		
		
		
		
		
		
		tablePanel.setBorder( BorderFactory.createEmptyBorder() );
				
		
		//saleTable.getTableHeader().addMouseListener( new ColumnPopupListener( saleTable ) );
		
		
		setLayout( new BorderLayout() );
		add( btnPanel , BorderLayout.NORTH );
		add( tablePanel , BorderLayout.WEST );
		
		
		
	}
	
	public void setBoxes( GregorianCalendar date ){
		
		int month = date.get( Calendar.MONTH );
		int year = date.get( Calendar.YEAR );
		
		monthBox.setSelectedIndex( month );
		yearBox.setSelectedItem( year );
		
	}
	
	public void buildMonthBox(){

		String[] monthStrings = new String[12];
		for( int i=0;i<12;i++ )
		{
			monthStrings[i] = Methods.getMonthString( i );
		}

		monthBox = new JComboBox( monthStrings );
		monthBox.setOpaque( false );
		monthBox.setBorder( null );
		

	}
	public void buildYearBox(){
		
		Calendar current = GregorianCalendar.getInstance();
		int year = current.get( Calendar.YEAR );
		
		Vector years = new Vector();
		for( int i=0;i<60;i++ )
		{
			years.add( year-30+i );
		}
		
		yearBox = new JComboBox( years );
		yearBox.setOpaque( false );
		yearBox.setBorder( null );
		



	}
	
	
	public boolean isButton( int x , int y ){
		
		//System.out.println( "x >= " + (getWidth()-buttonSize) + " && y < " + buttonSize );
		
		return  x >= getWidth()-buttonSize && y < buttonSize ;
		
		
	}
	public boolean isHidden(){
		
		return hidden;
		
	}
	public void setHidden( boolean hidden ){
		
		this.hidden = hidden;
		
		int displayedC = saleTable.model.getDisplayedC( 0 );
		int width = saleTable.getColumnModel().getColumn( displayedC ).getWidth();
		
		
		saleTable.model.setActualColumnHidden( 1 , hidden );
		saleTable.model.setActualColumnHidden( 2 , hidden );
		saleTable.model.setActualColumnHidden( 3 , hidden );
		saleTable.model.setActualColumnHidden( 4 , hidden );
		saleTable.model.setActualColumnHidden( 5 , hidden );
		
		displayedC = saleTable.model.getDisplayedC( 0 );
		saleTable.getColumnModel().getColumn( displayedC ).setPreferredWidth( width );//products
		
		if( hidden )
		{
			btnPanel.remove( revenuePanel );
			
		}
		else
		{
			btnPanel.add( revenuePanel );
			
		}
		
		repaint();
		
	}
	public void toggleHidden(){
		
		setHidden( !hidden );
		
	}
	
	
	public void paintComponent( Graphics gfx ) {

		super.paintComponent( gfx );
		Graphics2D g = (Graphics2D) gfx;
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON );
		
		if( !hidden )
		{
			g.setColor( Color.BLACK );
			g.fillRect( this.getWidth()-buttonSize , 0 , buttonSize , buttonSize );
		}
		
		
	}
	
	
	
	
	
	
}




