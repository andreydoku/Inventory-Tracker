
package panels;

import date.CalendarDialog;
import dialogs.PurchaseEditDialog;
import dialogs.SaleEditDialog;
import exceptions.*;
import filters.ProductFilter;
import filters.SaleFilter;
import gui.ButtonDialog;
import gui.GradientPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.*;

import myLibrary.ArrayToString;
import myLibrary.Methods;
import table.MyTableSelectionListener;
import basic.*;


public class EbayUtility extends JFrame {
	
	
	JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu( "File" );
			JMenuItem saveMenuItem = new JMenuItem( "Save" );
			JMenuItem closeMenuItem = new JMenuItem( "Exit" );
	
	GradientPanel mainPanel = new GradientPanel();
		JTabbedPane tabbedPane = new JTabbedPane();
			ProductPanel productPanel = new ProductPanel();
			PurchasePanel purchasePanel = new PurchasePanel();
			SalePanel salePanel = new SalePanel();
			CalcPanel calcPanel = new CalcPanel();
	
			
	Color themeColor = new Color( 130,150,230 );
	
	//Color tableBackgroundColor = new Color( 231,237,250 );
	//Color tableEvenRowColor = new Color( 241,245,250 );
	
	//Color tableBackgroundColor = Color.white;
	//Color tableEvenRowColor = new Color( 231,237,250 );
	
	Color tableBackgroundColor = new Color( 234,238,255 );
	Color tableEvenRowColor = new Color( 210,218,255 );
	
	Color tableGridColor = Color.lightGray;
	
	
	
	
	
	ProductFilter productFilter = new ProductFilter();
	SaleFilter saleFilter = new SaleFilter();
	
	private double version = 1.7;
	
	//ImageIcon logoIcon = new ImageIcon( "icons/logo 120.png" );
	BufferedImage logoImage;
	
	ImageIcon saveIcon = new ImageIcon( "icons/save 20.png" );
	ImageIcon exitIcon = new ImageIcon( "icons/exit 20.png" );
	
	ImageIcon inventoryIcon = new ImageIcon( "icons/products 32.png" );
	ImageIcon purchaseIcon = new ImageIcon( "icons/purchase 32.png" );
	ImageIcon saleIcon = new ImageIcon( "icons/sale 32.png" );
	ImageIcon calculatorIcon = new ImageIcon( "icons/calculator 32.png" );
	
	ImageIcon newSaleIcon = new ImageIcon( "icons/sale 20.png" );
	ImageIcon viewPurchaseIcon = new ImageIcon( "icons/view purchase 20.png" );
	ImageIcon viewSaleIcon = new ImageIcon( "icons/view sale 20.png" );
	ImageIcon groupIcon = new ImageIcon( "icons/group 20.png" );
	ImageIcon ungroupIcon = new ImageIcon( "icons/ungroup 20.png" );
	
	//ImageIcon addIcon = new ImageIcon( "icons/add 20.png" );
	ImageIcon addIcon = new ImageIcon( "icons/purchase 20.png" );
	ImageIcon editIcon = new ImageIcon( "icons/edit 20.png" );
	ImageIcon deleteIcon = new ImageIcon( "icons/delete 20.png" );
	ImageIcon receiveIcon = new ImageIcon( "icons/receive 20.png" );
	ImageIcon unreceiveIcon = new ImageIcon( "icons/unreceive 20.png" );
	ImageIcon viewProductsIcon = new ImageIcon( "icons/view products 20.png" );
	
	ImageIcon addToSaleIcon = new ImageIcon( "icons/add to sale 20.png" );
	ImageIcon leftIcon = new ImageIcon( "icons/left 20.png" );
	ImageIcon rightIcon = new ImageIcon( "icons/right 20.png" );
	
	public EbayUtility(){
		
		
		
		build();
		
		
		
		
		
		
		System.out.println( "started load" );
		load();
		System.out.println( "finished load" );
		
		applyAllFilters();
		
		
		
		
	}
	
	public final void build(){
		
		
		
		

		
		
		
		buildMenuBar();
		buildProductPanel();
		buildPurchasePanel();
		buildSalePanel();
		
		
		UIManager.put( "TabbedPane.contentBorderInsets" , new Insets( 0 , 0 , 0 , 0 ) );
		tabbedPane = new JTabbedPane();
		
		
		
		//
		
		tabbedPane.addTab( getHtmlForTab("Inventory") , inventoryIcon , productPanel );
		tabbedPane.addTab( getHtmlForTab("Purchases") , purchaseIcon , purchasePanel );
		tabbedPane.addTab( getHtmlForTab("Sales") , saleIcon , salePanel );
		tabbedPane.addTab( getHtmlForTab("Calculator") , calculatorIcon , calcPanel );
//		tabbedPane.addTab( "Inventory" , inventoryIcon , productPanel );
//		tabbedPane.addTab( "Purchases" , purchaseIcon , purchasePanel );
//		tabbedPane.addTab( "Sales" , saleIcon , salePanel );
//		tabbedPane.addTab( "Calculator" , calculatorIcon , calcPanel );
		
		
		
		tabbedPane.setBackgroundAt( 0 , themeColor );
		tabbedPane.setBackgroundAt( 1 , themeColor );
		tabbedPane.setBackgroundAt( 2 , themeColor );
		
		
		
		mainPanel.set( 1f,1f , themeColor.brighter() , 0f,0f , themeColor.darker() );
		mainPanel.setLayout( new BorderLayout() );
		mainPanel.add( tabbedPane , BorderLayout.CENTER );
		
		
		add( mainPanel );
		
		
		setSize( 926,594 );
		this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		this.setLocationRelativeTo( null );
		this.setTitle( "Ebay Utility by Andrey Dokuchayev     version " + version );
		
		try
		{
			logoImage = Methods.loadImage( new File( "icons/logo 120.png" ) );
			this.setIconImage( logoImage );
		}
		catch ( IOException ex )
		{
			System.out.println( "failed to load logo image" );
		}
		
		
		this.addWindowListener( new MyWindowListener() );
		
		
		
		
		
	}
	public final void buildMenuBar(){
		
		saveMenuItem.setIcon( saveIcon );
		closeMenuItem.setIcon( exitIcon );
		
		saveMenuItem.addActionListener( new BtnListener() );
		closeMenuItem.addActionListener( new BtnListener() );
		fileMenu.add( saveMenuItem );
		fileMenu.add( closeMenuItem );
		menuBar.add( fileMenu );
		this.setJMenuBar( menuBar );
		
	}
	public final void buildProductPanel(){
		
		
		productPanel.sellBtn.setIcon( newSaleIcon );
		productPanel.viewPurchaseBtn.setIcon( viewPurchaseIcon );
		productPanel.viewSaleBtn.setIcon( viewSaleIcon );
		
		productPanel.set( 1f,1f , themeColor.brighter() , 0f,0f , themeColor.darker() );
		
		productPanel.sellBtn.addActionListener( new BtnListener() );
		productPanel.viewPurchaseBtn.addActionListener( new BtnListener() );
		productPanel.viewSaleBtn.addActionListener( new BtnListener() );
		productPanel.statusFilterBox.addActionListener( new BtnListener() );
		productPanel.categoryFilterPanel.addActionListenerToButtons( new BtnListener() );
		
		
		
		productPanel.productTable.addMyTableSelectionListener( new TableSelectionListener() );
		productPanel.productTable.addMouseListener( new TableMouseListener() );
		
		
		updateAllProductBtns( productPanel.productTable.getSelectedRows() );
		
		productPanel.productTable.setGridColor( tableGridColor );
		productPanel.productTable.setBackground( tableBackgroundColor );
		productPanel.productTable.setEvenRowColor( tableEvenRowColor );
		
		
		productPanel.productTable.model.setRowFilter( productFilter );
		
	}
	public final void buildPurchasePanel(){
		
		purchasePanel.addBtn.setIcon( addIcon );
		purchasePanel.editBtn.setIcon( editIcon );
		purchasePanel.deleteBtn.setIcon( deleteIcon );
		purchasePanel.receiveBtn.setIcon( receiveIcon );
		purchasePanel.viewProductsBtn.setIcon( viewProductsIcon );
				
				
		purchasePanel.set( 1f,1f , themeColor.brighter() , 0,0 , themeColor.darker() );
		
		purchasePanel.addBtn.addActionListener( new BtnListener() );
		purchasePanel.editBtn.addActionListener( new BtnListener() );
		purchasePanel.deleteBtn.addActionListener( new BtnListener() );
		purchasePanel.receiveBtn.addActionListener( new BtnListener() );
		purchasePanel.viewProductsBtn.addActionListener( new BtnListener() );
		
		purchasePanel.purchaseTable.addMyTableSelectionListener( new TableSelectionListener() );
		purchasePanel.purchaseTable.addMouseListener( new TableMouseListener() );
		
		purchasePanel.editBtn.setEnabled( false );
		purchasePanel.deleteBtn.setEnabled( false );
		purchasePanel.receiveBtn.setEnabled( false );
		purchasePanel.viewProductsBtn.setEnabled( false );
			
		
		purchasePanel.purchaseTable.setGridColor( tableGridColor );
		purchasePanel.purchaseTable.setBackground( tableBackgroundColor );
		purchasePanel.purchaseTable.setEvenRowColor( tableEvenRowColor );
		
	}
	public final void buildSalePanel(){
		
		
		salePanel.editBtn.setIcon( editIcon );
		salePanel.deleteBtn.setIcon( deleteIcon );
		salePanel.addToSaleBtn.setIcon( addToSaleIcon );
		salePanel.viewProductsBtn.setIcon( viewProductsIcon );
		
		salePanel.leftArrowBtn.setIcon( leftIcon );
		salePanel.rightArrowBtn.setIcon( rightIcon );
		
		
		salePanel.set( 1,1 , themeColor.brighter() , 0,0 , themeColor.darker() );
		
		salePanel.saleTable.addMyTableSelectionListener( new TableSelectionListener() );
		salePanel.saleTable.addMouseListener( new TableMouseListener() );
		
		salePanel.editBtn.addActionListener( new BtnListener() );
		salePanel.deleteBtn.addActionListener( new BtnListener() );
		salePanel.addToSaleBtn.addActionListener( new BtnListener() );
		salePanel.viewProductsBtn.addActionListener( new BtnListener() );
		
		salePanel.leftArrowBtn.addActionListener( new SaleMonthListener() );
		salePanel.monthBox.addActionListener( new SaleMonthListener() );
		salePanel.yearBox.addActionListener( new SaleMonthListener() );
		salePanel.rightArrowBtn.addActionListener( new SaleMonthListener() );
		
		salePanel.saleTable.model.setRowFilter( saleFilter );
		
		GregorianCalendar currentDate = (GregorianCalendar) GregorianCalendar.getInstance();
		
		salePanel.setBoxes( currentDate );
		
		updateAllSaleBtns();
		
		salePanel.saleTable.setGridColor( tableGridColor );
		salePanel.saleTable.setBackground( tableBackgroundColor );
		salePanel.saleTable.setEvenRowColor( tableEvenRowColor );
		
		
		salePanel.addMouseListener( new HiddenMouseListener() );
		salePanel.addMouseMotionListener( new HiddenMouseListener() );
		
	}
	
	private String getHtmlForTab( String text ){
		
		int leftMargin = 10;
		int topMargin = 8;
		int marginWidth = 5;
		int marginHeight = 5;
		//return "<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>" + text + "</body></html>";
		//return "<html><body leftmargin=" + "15" + " topmargin=" + "8" + " marginwidth=" + "15" + " marginheight=" + "5" + ">" + text + "</body></html>";
		return "<html><body"
				+ " leftmargin=" + leftMargin 
				+ " topmargin=" + topMargin
				+ " marginwidth=" + marginWidth
				+ " marginheight=" + marginHeight
				+ ">" + text + 
				"</body></html>";
		
		
		
		
		
	}
	
	
	public boolean save(){
		
		boolean debug = false;
		
		
		String productsOutput = "";
		String purchasesOutput = "";
		String salesOutput = "";
		String divider = "-----------------------------------------" + "\n";
		
		ArrayList<Product> products = productPanel.productTable.getAllProducts();
		ArrayList<Purchase> purchases = purchasePanel.purchaseTable.getAllPurchases();
		ArrayList<Sale> sales = salePanel.saleTable.getAllSales();
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			String saveString = product.toSaveString( i );
			productsOutput += saveString;
			if( i<products.size()-1 )
			{
				productsOutput += divider;
			}
			
		}
		if( debug ) System.out.println( "products saved: " + products.size() );
		
		
		for( int i=0; i<purchases.size(); i++ )
		{
			Purchase purchase = purchases.get( i );
			String saveString = purchase.toSaveString( products );
			purchasesOutput += saveString;
			if( i < purchases.size()-1 )
			{
				purchasesOutput += divider;
			}
			
		}
		if( debug ) System.out.println( "purchases saved: " + purchases.size() );
		
		
		for( int i=0; i<sales.size(); i++ )
		{
			Sale sale = sales.get( i );
			String saveString = sale.toSaveString( products );
			salesOutput += saveString;
			if( i < sales.size()-1 )
			{
				salesOutput += divider;
			}
			
		}
		if( debug ) System.out.println( "sales saved: " + sales.size() );
		
		
		File productsFile = new File("products.txt");
		File purchasesFile = new File("purchases.txt");
		File salesFile = new File("sales.txt");
		try
		{
			Methods.writeTextFile( productsFile , productsOutput );
			Methods.writeTextFile( purchasesFile , purchasesOutput );
			Methods.writeTextFile( salesFile , salesOutput );
		} 
		catch ( IOException ex )
		{
			JOptionPane.showMessageDialog( null , "Failed to save" );
			return false;
		}
		
		return true;
		
		
		
	
	}
	public final void load(){
		
		
		boolean debug = false;
		
		
		File productsFile = new File("products.txt");
		File purchasesFile = new File("purchases.txt");
		File salesFile = new File("sales.txt");
		
		String productsFileText;
		String purchasesFileText;
		String salesFileText;
		
		String divider = "-----------------------------------------";
		
		try
		{
			if( !productsFile.exists() )
			{
				productsFile.createNewFile();
			}
			if( !purchasesFile.exists() )
			{
				purchasesFile.createNewFile();
			}
			if( !salesFile.exists() )
			{
				salesFile.createNewFile();
			}
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog( null , "Failed to load (1)");
			return;
		}
		
			
		
		
		try
		{
			productsFileText = Methods.readTextFile( productsFile );
			purchasesFileText = Methods.readTextFile( purchasesFile );
			salesFileText = Methods.readTextFile( salesFile );
			
		} 
		catch ( FileNotFoundException ex )
		{
			JOptionPane.showMessageDialog( null , "Data files not found");
			return;
		} 
		catch ( IOException ex )
		{
			JOptionPane.showMessageDialog( null , "Failed to open data files");
			return;
		}
		
		
		ArrayList<String> productTextList = Methods.split( productsFileText , divider , false, true , false );
		ArrayList<String> purchaseTextList = Methods.split( purchasesFileText , divider , false, true , false );
		ArrayList<String> saleTextList = Methods.split( salesFileText , divider , false, true , false );
		
		ArrayList<Product> products = new ArrayList<Product>();
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		ArrayList<Sale> sales = new ArrayList<Sale>();
		
		try
		{
			products = Product.getProducts( productTextList );
			purchases = Purchase.getPurchases( purchaseTextList , products );
			sales = Sale.getSales( saleTextList , products );
		} 
		catch ( FailedToLoadException ex )
		{
			JOptionPane.showMessageDialog( null , ex.getMessage() );
			return;
		}
		if( debug )
		{
			System.out.println( "products loaded: " + products.size() );
			ArrayToString.printArray( products , "\n" );
			System.out.println( "\n" );

			System.out.println( "purchases loaded: " + purchases.size() );
			ArrayToString.printArray( purchases , "---------------" + "\n" );
			System.out.println( "\n" );

			System.out.println( "sales loaded: " + sales.size() );
			ArrayToString.printArray( sales , "---------------" + "\n" );
			System.out.println( "\n" );
		}
			
		
		
		
		//add purchases and their products
		for( int i=0; i<purchases.size(); i++ )
		{
			addPurchase( purchases.get( i ) );
		}
		purchasePanel.purchaseTable.clearSelection();
		
		//add sales
		for( int i=0; i<sales.size(); i++ )
		{
			Sale newSale = sales.get( i );
			salePanel.saleTable.model.addRow( newSale );
			
			for( int j=0; j<newSale.getProducts().size(); j++ )
			{
				Product product = newSale.getProducts().get( j );
				int actualC = productPanel.productTable.model.getActualIndexOf( product );
				productPanel.productTable.model.setActualRowData( actualC , product );
				
			}
			
		}
		
		updateRevenueLabel();
		
		
	}
	
	public void checkPointers(){
		
		String n = "\n";
		String t = "    ";
		String output = "";
		boolean foundError = false;
		
		output += "testing every purchase in the purchase table" + n;
		ArrayList<Purchase> purchases = purchasePanel.purchaseTable.getAllPurchases();
		for( int i=0; i<purchases.size(); i++ )
		{
			Purchase purchase = purchases.get( i );
			for( int j=0; j<purchase.getProductCount(); j++ )
			{
				Product product = purchase.getProduct( j );
				int productTableIndex = productPanel.productTable.model.getActualIndexOf( product );
				if( productTableIndex == -1 )
				{
					output += t + "purchase points to a product which is not in the product table" + n;foundError = true;
				}
				
				if( product.getPurchase() == null ) 
				{
					output += t + "purchase points to a product, that product points to null purchase" + n;foundError = true;
				}
				else if( product.getPurchase() != purchase )
				{
					output += t + "purchase points to a product, that product points to a different purchase" + n;foundError = true;
					
				}
				
			}
		}
		
		output += "\n" + "testing every product in the product table" + n;
		ArrayList<Product> products = productPanel.productTable.getAllProducts();
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			Purchase purchase = product.getPurchase();
			if( purchase == null )
			{
				output += t + "product points to a null purchase" + n;foundError = true;
			}
			if( purchasePanel.purchaseTable.model.getActualIndexOf( purchase ) == -1 )
			{
				output += t + "product points to a purchase, that purchase is not in the purchase table" + n;foundError = true;
			}
			if( !purchase.contains( product ) )
			{
				output += t + "produt points to a purchase, that purchase does not point back" + n;foundError = true;
			}
			
			Sale sale = product.getSale();
			if( sale != null )
			{
				if( salePanel.saleTable.model.getActualIndexOf( sale ) == -1 )
				{
					output += t + "product points to a sale, that sale is not in the sale table" + n;foundError = true;
				}
				if( !sale.contains( product ) )
				{
					output += t + t + "product points to a sale, that sale does not point back" + n;foundError = true;
				}
			}
			
		}
		
		output += "\n" + "testing every sale in the sale table" + n;
		ArrayList<Sale> sales = salePanel.saleTable.getAllSales();
		for( int i=0; i<sales.size(); i++ )
		{
			Sale sale = sales.get( i );
			for( int j=0; j<sale.getProductCount(); j++ )
			{
				Product product = sale.getProducts().get( j );
				if( product == null )
				{
					output += t + "sale points to a product, that product is null" + n;foundError = true;
				}
				if( !productPanel.productTable.model.contains( product ) )
				{
					output += t + "sale points to a product, that product is not in the product table" + n;foundError = true;
				}
				if( product.getSale() == null )
				{
					output += t + "sale points to a product, that product points to null (is not sold)" + n;foundError = true;
				}
				if( product.getSale() != sale )
				{
					output += t + "sale points to a product, that product points to a differenct sale" + n;foundError = true;
				}
					
			}
		}
		
		
		if( foundError )
		{
			JOptionPane.showMessageDialog( null , output );
			System.out.println( output );
			System.out.println( "-----------------------------------------------------------" );
		}
		else
		{
			System.out.println( "all is good" );
		}
		
		
	}
	
	
	public void updateRevenueLabel(){
		
		double month = salePanel.saleTable.getMonthlyRevenue( this.saleFilter.getDate() );
		double total = salePanel.saleTable.getTotalRevenue();
		
		DecimalFormat f = new DecimalFormat("$#,##0.00");
		
		salePanel.monthlyRevenueField.setText( f.format( month ) );
		salePanel.totalRevenueField.setText( f.format( total ) );
		
		
	}
	
	public void applyAllFilters(){
		
		//purchasePanel.purchaseTable.model.applyFilter();
		ProductFilter pf = (ProductFilter) productPanel.productTable.model.getRowFilter();
		setProductStatusFilter( pf.getStatusType() , pf.getCategoryType() );
		
		
		
		
		salePanel.saleTable.model.applyFilter();
		
		
	}
	public void resetProductFilter(){
		
		productPanel.statusFilterBox.setSelectedIndex( 0 );
		productPanel.categoryFilterPanel.allBtn.setSelected( true );
		
		productStatusFilterBoxChanged();
		productCategoryFilterButtonsChanged( 0 );
		
	}
	public void resetPurhaseFilter(){
		
		
		
	}
	public void resetSaleFilter(){
		
		
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	//product panel actions
	public void addSale( Sale newSale , ArrayList<Product> oldProducts ){
		
		boolean debug = false;
		
		if( debug )
		{
			System.out.println( "-------------------------------------------------------" );
			System.out.println( "sale added" );
			System.out.println( "----------" );
			System.out.println( newSale );
			System.out.println( "-------------------------------------------------------" );
		}
			
		
		
		
		//ArrayList<Integer> actualRowsOld = productPanel.productTable.getActualRows( oldProducts );
		//ArrayList<Integer> actualRowsNew = productPanel.productTable.getActualRows( newSale.getProducts() );
		
		//System.out.println( "old: " + Methods.getString( actualRows1, "," ) );
		//System.out.println( "new: " + Methods.getString( actualRows2, "," ) );
		
		newSale.setProducts( oldProducts );
		if(debug) System.out.println( newSale );
		
		salePanel.saleTable.model.addRow( newSale );
		int[] selectedRows = productPanel.productTable.getSelectedRows();
		productPanel.productTable.setSelectedDisplayedRows( selectedRows );//clears first
		
		
		productPanel.productTable.uncheckAll();
		
		
		updateRevenueLabel();
		applyAllFilters();
		
	}
	public void viewPurchase( Purchase purchase ){
		
		
		
		tabbedPane.setSelectedIndex( 1 );
		purchasePanel.purchaseTable.clearSelection();
		
		int displayedR = purchasePanel.purchaseTable.model.getDisplayedIndexOf( purchase );
		if( displayedR == -1 )
		{
			resetPurhaseFilter();
			displayedR = purchasePanel.purchaseTable.model.getDisplayedIndexOf( purchase );
		}
		
		purchasePanel.purchaseTable.scrollRectToVisible( new Rectangle( purchasePanel.purchaseTable.getCellRect( displayedR , 0 , true )));
		purchasePanel.purchaseTable.setSelected( purchase , true );
		
		
		
	}
	public void viewSale( Sale sale ){
		
		tabbedPane.setSelectedIndex( 2 );
		
		salePanel.setBoxes( sale.getSaleDate() );
		
		salePanel.saleTable.clearSelection();
		int displayedR = salePanel.saleTable.model.getDisplayedIndexOf( sale );
		
		
		salePanel.saleTable.scrollRectToVisible( new Rectangle( salePanel.saleTable.getCellRect( displayedR , 0 , true )));
		salePanel.saleTable.setSelected( sale , true );
		
		
		
	}
	public void setProductStatusFilter( int statusType , int categoryType ){
		
		
		//System.out.println( "changed filter: " + ProductFilter.getStatusString( statusType ) + " , " + ProductFilter.getCategoryString( categoryType ) );
		
		ArrayList<Product> products = productPanel.productTable.getSelectedProducts();
		
		productFilter.setStatusType( statusType );
		productFilter.setCategoryType( categoryType );
		productPanel.productTable.model.applyFilter();
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			int displayedR = productPanel.productTable.model.getDisplayedIndexOf( product );
			if( displayedR != -1 )
			{
				productPanel.productTable.setSelected( product , true );
			}
		}
		
		
		
		
		
	}
	
	//purchase panel actions
	public void addPurchase( Purchase newPurchase ){
		
		purchasePanel.addPurchase( newPurchase );
		productPanel.addProducts( newPurchase.getProducts() );
		purchasePanel.purchaseTable.setSelected( newPurchase, true );
		
		int displayedR = purchasePanel.purchaseTable.model.getDisplayedIndexOf( newPurchase );
		if( displayedR != -1 )
		{
			purchasePanel.purchaseTable.scrollRectToVisible( new Rectangle( purchasePanel.purchaseTable.getCellRect( displayedR , 0 , true ) ) );
		}
		
		applyAllFilters();
		
	}
	public void editPurchase( Purchase oldPurchase , Purchase newPurchase ){
		
		int actualR = purchasePanel.purchaseTable.model.getActualIndexOf( oldPurchase );
		purchasePanel.purchaseTable.model.setActualRowData( actualR , newPurchase );
		purchasePanel.purchaseTable.setSelected( newPurchase , true );
		
		for( int i=0; i<oldPurchase.getProductCount(); i++ )
		{
			Product oldProduct = oldPurchase.getProduct( i );
			actualR = productPanel.productTable.model.getActualIndexOf( oldProduct );
			productPanel.productTable.model.removeActualRow( actualR );
			
			//make sale reference to new product
//			if( oldProduct.isSold() )
//			{
//				Sale sale = oldProduct.getSale();
//				sale.remove( oldProduct );
//			}
			
		}
		
		
		for( int i=0; i<newPurchase.getProductCount(); i++ )
		{
			Product newProduct = newPurchase.getProduct( i );
			productPanel.productTable.model.addRow( actualR , newProduct );
			
			if( newProduct.isSold() )
			{
				Sale sale = newProduct.getSale();
				
				
				//sale.add( newProduct );
				int actualR_sale = salePanel.saleTable.model.getActualIndexOf( sale );
				salePanel.saleTable.model.setActualRowData( actualR_sale , sale );
				
			}
			
		}
		
		updateRevenueLabel();
		applyAllFilters();
		
		
	}
	public void deletePurchase( Purchase purchase ){
		
		purchasePanel.purchaseTable.model.removeRow( purchase );
		productPanel.productTable.removeProducts( purchase.getProducts() );
		
		applyAllFilters();
		
	}
	public void receivePurchase( Purchase purchase ) {
		
				
		GregorianCalendar receivedDate = CalendarDialog.showDialog( null , "Received Date" );
		if( receivedDate != null )
		{
			try
			{
				int displayedR = purchasePanel.purchaseTable.getSelectedRow();
				
				purchase.setReceivedDate( receivedDate );
				purchasePanel.purchaseTable.model.fireTableRowsUpdated( displayedR , displayedR );
				updateReceiveBtn();
				this.updateAllProductBtns( productPanel.productTable.getSelectedRows() );
				
				
			} 
			catch( ReceivedAfterSoldException ex )
			{
				JOptionPane.showMessageDialog( null , "Received date cannot be after the sale date" );
				receivePurchase( purchase );
			}			
			catch( PurchaseAfterReceivedException ex )
			{
				JOptionPane.showMessageDialog( null , "Received date cannot be before the purchase date" );
				receivePurchase( purchase );
			}
			catch( NotReceivedButSoldException ex )
			{
				JOptionPane.showMessageDialog( null , "Cannot set a sold product to un-received" );
			}
			
		}
		
		applyAllFilters();
		
	}
	public void unreceivePurchase( Purchase purchase ){
		
		
		//System.out.println( "UN-receivePurchase( purchase )" );
		
		ArrayList<Product> products = purchase.getProducts();
		
		for( int i=0; i<products.size(); i++ )
		{
			if( products.get( i ).isSold() )
			{
				JOptionPane.showMessageDialog( null , "Cannot set sold products as not received" );
				return;
			}
			
		}
		try
		{
			int displayedR = purchasePanel.purchaseTable.getSelectedRow();
				
			purchase.setReceivedDate( null );
			purchasePanel.purchaseTable.model.fireTableRowsUpdated( displayedR , displayedR );
			//purchasePanel.purchaseTable.model.setDisplayedRowData( displayedR , purchase );
			updateReceiveBtn();
			updateAllProductBtns( productPanel.productTable.getSelectedRows() );
		} 
		catch ( PurchaseAfterReceivedException ex )
		{
			ex.printStackTrace();
		} 
		catch ( ReceivedAfterSoldException ex )
		{
			ex.printStackTrace();
		}
		catch ( NotReceivedButSoldException ex )
		{
			ex.printStackTrace();
		}
		
		applyAllFilters();
		
		
	}
	public void viewProducts( ArrayList<Product> products ){
		
		
		
		tabbedPane.setSelectedIndex( 0 );
		productPanel.productTable.clearSelection();
		
		boolean resetOnce = false;
		
		for( int i=0; i<products.size(); i++ )
		{
			Product product = products.get( i );
			Purchase purchase = product.getPurchase();
			Product representative = purchase.getRepresentative();
			
			Product productToBeSelected;
			
			if( purchase.isGrouped() && product != representative )
			{
				productToBeSelected = representative;
			}
			else
			{
				productToBeSelected = product;
			}
			
			int displayedR = productPanel.productTable.model.getDisplayedIndexOf( productToBeSelected );
			if( displayedR == -1 )
			{
				if( !resetOnce )
				{
					resetProductFilter();
					resetOnce = true;
				}

				displayedR = productPanel.productTable.model.getDisplayedIndexOf( productToBeSelected );
			}
			
						
			productPanel.productTable.setSelected( productToBeSelected , true );
			

			productPanel.productTable.scrollRectToVisible( new Rectangle( productPanel.productTable.getCellRect( displayedR , 0 , true )));
			
		}
		
		//in case selection didnt change
		updateAllProductBtns( productPanel.productTable.getSelectedRows() );
		
		
		
	}
	
	//sale panel actions
	public void editSale( Sale oldSale , Sale newSale ){
		
		
		//newSale.setProducts( oldSale.getProducts() );
		//
		//set edited sale reference back to old untouched copies of products
		//unfortunately, I cannot simply add them all since some of them may have been removed
		//for this reason, I must go through each one and ensure a copy exists under new sales
		//a copy will have same name and purchase object reference
		//	  note: same name and purchase object doesnt necessarily mean copies, it may be a copy of a different product which also belongs to the same purchase
		//		    In that case, it doesnt really matter if it is chosen instead of its peer. This is because they should still be in the same order, which means the first
		//          newProduct clone will be replaced by the first oldProduct clone, etc
		
		ArrayList<Product> oldProducts = oldSale.getProducts();
		ArrayList<Product> productsToAdd = new ArrayList<Product>(); //query all products which will be added at the end
		ArrayList<Integer> actualRowIndices = new ArrayList<Integer>();//keep track of the row index of the old product the new one will replace
		
//		for( int i=0; i<newProducts.size(); i++ )
//		{
//			Purchase newPurchase = newProducts.get( i ).getPurchase();
//			Purchase oldPurchase = oldProducts.get( i ).getPurchase();
//			System.out.println( newPurchase == oldPurchase );
//		}
		
		
		for( int i=0; i<oldProducts.size(); i++ )
		{
			Product oldProduct = oldProducts.get( i );
			//find the clone product, and if it exists(did not get removed), then replace it with oldProduct
			for( int j=0; j<newSale.getProductCount(); j++ )
			{
				Product newProduct = newSale.getProducts().get( j );
				//if name and purchase objects are the same, then it's the clone
				if( oldProduct.getName().equals( newProduct.getName() )  &&  oldProduct.getPurchase() == newProduct.getPurchase() )
				{
					//newSale.replace( i , newProduct );//may not be replaced now, as I dont want to add multiple copies in place of one
					productsToAdd.add( newProduct );
					
					//remember the row index where "newProduct" will go, replacing the oldProduct there
					actualRowIndices.add( productPanel.productTable.model.getActualIndexOf( oldProduct ) );
					
					
					//purchase of the old product - replace old product with new product, at the same index
					Purchase purchase = oldProduct.getPurchase();
					int indexOfProduct = purchase.indexOf( oldProduct );
					purchase.setProduct( indexOfProduct , newProduct );
					
					newSale.remove( newProduct );//removal means following clones will not be compared to this one anymore
					
					j = newSale.getProductCount();//exit this loop, since clone has been found
				}
				
			}
			
			
			//if a clone has not been found, then the current oldProduct has been removed
			//in this case, the product must no longer reference any sale, and the inventory tab must reflect these changes
			oldProduct.setSale( null );
			
			
			
		}
		
		//all cloned products have been removed from newSale
		//only products remaining under newSale are new products added in
		//they are clones, and the origionals are currently checked in the table
		ArrayList<Product> checkedProducts = productPanel.productTable.getCheckedProducts();
		for( int i=0; i<checkedProducts.size(); i++ )
		{
			Product checkedProduct = checkedProducts.get( i );
			
			for( int j=0; j<newSale.getProductCount(); j++ )
			{
				Product productAdded = newSale.getProducts().get( j );
				
				if( productAdded.getName().equals( checkedProduct.getName() )  &&  productAdded.getPurchase() == checkedProduct.getPurchase() )
				{
					productsToAdd.add( checkedProduct );
					actualRowIndices.add( productPanel.productTable.model.getActualIndexOf( checkedProduct ) );
					
					newSale.remove( productAdded );
					checkedProduct.setChecked( false );
					checkedProducts.remove( checkedProduct );i--;
					
					j = newSale.getProductCount() + 10;//the +10 is a just in case
				}
				
			}
			
		}
		
		
		newSale.setProducts( productsToAdd );
		
		//change sale table entry
		int actualR = salePanel.saleTable.model.getActualIndexOf( oldSale );
		salePanel.saleTable.model.setActualRowData( actualR , newSale );
		
		
		
		for( int i=0; i<productsToAdd.size(); i++ )
		{
			Product productToAdd = productsToAdd.get( i );
			actualR = actualRowIndices.get( i );
			productPanel.productTable.model.setActualRowData( actualR , productToAdd );
		}
		
		
		
		updateRevenueLabel();
		applyAllFilters();
		
		salePanel.saleTable.setSelected( newSale , true );
		
		
	}
	public void deleteSale( Sale sale ){
		
		for( int i=0; i<sale.getProducts().size(); i++ )
		{
			Product product = sale.getProducts().get( i );
			product.setSale( null );
		}
		
		salePanel.saleTable.model.removeRow( sale );
		
		updateRevenueLabel();
		applyAllFilters();
		
	}
	public void setSaleFilter( GregorianCalendar newViewDate ){
		
		saleFilter.setDate( newViewDate );
		salePanel.saleTable.model.applyFilter();
		updateRevenueLabel();
		
	}
	public void toggleHidden(){
		
		salePanel.toggleHidden();
		
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//update product buttons
	public void productTableSelectionChanged( int[] selectedRows ){
		
		updateAllProductBtns( selectedRows );
			
	}
	public void updateAllProductBtns( int[] selectedRows ){
		
		updateViewPurchaseBtn( selectedRows );
		updateViewSaleBtn( selectedRows );
		
	}
	public void updateViewPurchaseBtn( int[] selectedRows ){
		
		productPanel.viewPurchaseBtn.setEnabled( selectedRows.length == 1 );
		
	}
	public void updateViewSaleBtn( int[] selectedRows ){
		
		
		if( selectedRows.length == 1 )
		{
			Product selectedProduct = productPanel.productTable.getSelectedProducts().get( 0 );
			productPanel.viewSaleBtn.setEnabled( selectedProduct.isSold() && !selectedProduct.getPurchase().isGrouped() );
			
		}
		else
		{
			productPanel.viewSaleBtn.setEnabled( false );
		}
		
		
	}
	
	
	//update purchase buttons
	public void purchaseTableSelectionChanged( int newIndex ){
		
		
		purchasePanel.editBtn.setEnabled( newIndex != -1 );
		purchasePanel.deleteBtn.setEnabled( newIndex != -1 );
		purchasePanel.receiveBtn.setEnabled( newIndex != -1 );
		purchasePanel.viewProductsBtn.setEnabled( newIndex != -1 );
		
		updateReceiveBtn();
		
		
			
		
	}
	public void updateReceiveBtn(){
		
		Purchase selectedPurchase = purchasePanel.getSelectedPurchase();
		if( selectedPurchase != null )
		{
			if( selectedPurchase.isReceived() )
			{
				purchasePanel.receiveBtn.setIcon( unreceiveIcon );
				//purchasePanel.receiveBtn.setText( "UN-Receive");
			}
			else
			{
				purchasePanel.receiveBtn.setIcon( receiveIcon );
				//purchasePanel.receiveBtn.setText( "Receive");
			}
		}
		
	}
	
	//update sale buttons
	public void saleTableSelectionChanged( int newIndex ){
		
		updateAllSaleBtns();
				
	}
	public void updateAllSaleBtns(){
		
		salePanel.editBtn.setEnabled( salePanel.saleTable.getSelectedRow() != -1 );
		salePanel.deleteBtn.setEnabled( salePanel.saleTable.getSelectedRow() != -1 );
		salePanel.addToSaleBtn.setEnabled( salePanel.saleTable.getSelectedRow() != -1 );
		salePanel.viewProductsBtn.setEnabled( salePanel.saleTable.getSelectedRow() != -1 );
		
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//product panel butons
	public void sellPushed(){
		
		//Dimension headerSize = productPanel.productTable.getTableHeader().getSize();
		//Dimension tableSize = productPanel.productTable.getSize();
		//System.out.println( headerSize.width + " , " + tableSize.width );
		
		
		ArrayList<Product> chechedProducts = productPanel.productTable.getCheckedProducts();
		if( chechedProducts.isEmpty() )
		{
			JOptionPane.showMessageDialog( null , "Please check at least 1 product to sell" );
			return;
		}
		for( int i=0; i<chechedProducts.size(); i++ )
		{
			Product product = chechedProducts.get( i );
			if( product.isSold() )
			{
				JOptionPane.showMessageDialog( null , "The following product has already been sold: " + product.getName() );
				return;
			}
			if( !product.isReceived() )
			{
				JOptionPane.showMessageDialog( null , "The following product has not been received yet: " + product.getName() );
				return;
			}
		}
		
				
		
		
		Sale newSale = SaleEditDialog.showDialog( chechedProducts , "New Sale" );
		if( newSale != null )
		{
			addSale( newSale , chechedProducts );
			
		}
		
		checkPointers();
		
	}
	public void viewPurchasePushed(){
		
		int displayedR = productPanel.productTable.getSelectedRow();
		Product product = (Product) productPanel.productTable.model.getDisplayedRowData( displayedR );
		viewPurchase( product.getPurchase() );
		
		checkPointers();
		
	}
	public void viewSalePushed(){
		
		int displayedR = productPanel.productTable.getSelectedRow();
		Product product = (Product) productPanel.productTable.model.getDisplayedRowData( displayedR );
		viewSale( product.getSale() );
		
		checkPointers();
		
	}
	public void productStatusFilterBoxChanged(){
		
		int statusType = productPanel.statusFilterBox.getSelectedIndex();
		ProductFilter pf = (ProductFilter) productPanel.productTable.model.getRowFilter();
		setProductStatusFilter( statusType , pf.getCategoryType() );
		
		checkPointers();
	}
	public void productCategoryFilterButtonsChanged( int categoryType ){
		
		ProductFilter pf = (ProductFilter) productPanel.productTable.model.getRowFilter();
		setProductStatusFilter( pf.getStatusType() , categoryType );
		
		checkPointers();
	}
	
	//purchase panel buttons
	public void addPurchasePushed(){
		
		
		
		Purchase newPurchase = PurchaseEditDialog.showDialog( productPanel.productTable.getProductNameList() , purchasePanel.purchaseTable.getCategoryList() , "New Purchase" );
		if( newPurchase != null )
		{
			addPurchase( newPurchase );
		}
		
		checkPointers();
		
	}
	public void editPurchasePushed(){
		
		Purchase oldPurchase = purchasePanel.getSelectedPurchase();				
		//System.out.println( "before:" + oldPurchase.getProductCount() );
		
		Purchase newPurchase = PurchaseEditDialog.showDialog( oldPurchase , productPanel.productTable.getProductNameList() , purchasePanel.purchaseTable.getCategoryList() , "Edit Purchase" );	
		//System.out.println( "after:" + oldPurchase.getProductCount() );
		
		if( newPurchase != null )//newPurchase == null if hit cancel
		{
			this.editPurchase( oldPurchase , newPurchase );
		}
		else
		{
			//fix sale references
						
			for( int i=0; i<oldPurchase.getProductCount(); i++ )
			{
				Product oldProduct = oldPurchase.getProduct( i );
				if( oldProduct.isSold() )
				{
					Sale sale = oldProduct.getSale();
					
					//set reference to old product
					sale.add( oldProduct );
					
					
					//remove references from all new products
					//since i no longer have access to new products, i remove all products that are not in the product table
					for( int j=0; j<sale.getProducts().size(); j++ )
					{
						Product saleProduct = sale.getProducts().get( j );
						if( productPanel.productTable.model.getActualIndexOf( saleProduct) == -1 )
						{
							sale.remove( saleProduct );
							j--;
						}
					}
					
					
					
				}
					
				
					
			}
			
			
		}
		
		
		checkPointers();
		
				
	}
	public void deletePurchasePushed(){
		
		int reply = ButtonDialog.showDialog( "Are you sure you want to delete the selected purchase?" , "Yes" , "No" );
		if( reply == 0 )
		{
			Purchase purchase = purchasePanel.getSelectedPurchase();
			ArrayList<Product> products = purchase.getProducts();
			for( int i=0; i<products.size(); i++ )
			{
				Product p = products.get( i );
				if( p.isSold() )
				{
					JOptionPane.showMessageDialog( null , "Cannot delete a purchase containing products which have already been sold" );
					return;
				}
			}
			
			
			deletePurchase( purchase );
		}
		
			
		
		checkPointers();
		
	}
	public void receivePurchasePushed(){
		
		Purchase purchase = purchasePanel.getSelectedPurchase();
		if( !purchase.isReceived() )
		{
			receivePurchase( purchase );
		}
		else
		{
			unreceivePurchase( purchase );
		}
		
		
		
		checkPointers();
		
	}
	public void viewPurchaseProductsPushed(){
		
		Purchase purchase = purchasePanel.getSelectedPurchase();
		ArrayList<Product> products = purchase.getProducts();
		viewProducts( products );
		
		checkPointers();
	}
	
	//sale panel buttons
	public void editSalePushed(){
		
		Sale oldSale = salePanel.saleTable.getSelectedSale();
		if( oldSale != null )
		{
			Sale newSale = SaleEditDialog.showDialog( oldSale , "Edit Sale" );

			if( newSale != null )
			{
				editSale( oldSale , newSale );
			}
		}
		
		
		checkPointers();
		
	}
	public void deleteSalePushed(){
		
		int reply = ButtonDialog.showDialog( "Are you sure you want to delete the selected sale?" , "Yes" , "No" );
		if( reply == 0 )
		{
			Sale sale = salePanel.saleTable.getSelectedSale();
			deleteSale( sale );
		}
			
		checkPointers();
	}
	public void addToSalePushed(){
		
		ArrayList<Product> chechedProducts = productPanel.productTable.getCheckedProducts();
		if( chechedProducts.isEmpty() )
		{
			JOptionPane.showMessageDialog( null , "Please check at least 1 product to sell" );
			return;
		}
		for( int i=0; i<chechedProducts.size(); i++ )
		{
			Product product = chechedProducts.get( i );
			if( product.isSold() )
			{
				JOptionPane.showMessageDialog( null , "The following product has already been sold: " + product.getName() );
				return;
			}
			if( !product.isReceived() )
			{
				JOptionPane.showMessageDialog( null , "The following product has not been received yet: " + product.getName() );
				return;
			}
		}
		
		
		
		Sale oldSale = salePanel.saleTable.getSelectedSale();
		if( oldSale != null )
		{
			Sale newSale = SaleEditDialog.showDialog( oldSale , chechedProducts , "Add to Sale" );

			if( newSale != null )
			{
				editSale( oldSale , newSale );
			}
		}
		
		
		
		checkPointers();
		
	}
	public void viewSaleProductsPushed(){
		
		Sale sale = salePanel.saleTable.getSelectedSale();
		ArrayList<Product> products = sale.getProducts();
		viewProducts( products );
		
		
		
		checkPointers();
	}
	public void saleDateBoxChanged(){
		
		int year = (Integer) salePanel.yearBox.getSelectedItem();
		int month = salePanel.monthBox.getSelectedIndex();
		
		GregorianCalendar newViewDate = new GregorianCalendar( year , month , 1 );
		setSaleFilter( newViewDate );
		
		
		checkPointers();
		
	}
	public void doubleClickedOnHiddenSquare(){
		
		toggleHidden();
		
		checkPointers();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void rightClickedProductTable( Point point ){
		
		
		
		int displayedR = productPanel.productTable.rowAtPoint( point );
		if( displayedR != -1 )
		{
			productPanel.productTable.setRowSelectionInterval( displayedR , displayedR );
			Product product = (Product) productPanel.productTable.model.getDisplayedRowData( displayedR );
						
			JPopupMenu menu = createRightClickMenu( product );
			menu.show( productPanel.productTable , point.x , point.y );
			
		}
		
	}
	public void rightClickedPurchaseTable( Point point ){
		
		int displayedR = purchasePanel.purchaseTable.rowAtPoint( point );
		if( displayedR != -1 )
		{
			purchasePanel.purchaseTable.setRowSelectionInterval( displayedR , displayedR );
			Purchase purchase = (Purchase) purchasePanel.purchaseTable.model.getDisplayedRowData( displayedR );
						
			JPopupMenu menu = createRightClickMenu( purchase );
			menu.show( purchasePanel.purchaseTable , point.x , point.y );
		}
		
	}
	public void rightClickedSaleTable( Point point ){
		
		int displayedR = salePanel.saleTable.rowAtPoint( point );
		if( displayedR != -1 )
		{
			salePanel.saleTable.setRowSelectionInterval( displayedR , displayedR );
			Sale sale = (Sale) salePanel.saleTable.model.getDisplayedRowData( displayedR );
						
			JPopupMenu menu = createRightClickMenu( sale );
			menu.show( salePanel.saleTable , point.x , point.y );
		}
		
	}
	
	public JPopupMenu createRightClickMenu( Product product ){
		
		JPopupMenu popupMenu = new JPopupMenu();
		
		if( product.getPurchase().getProductCount() > 1 )
		{
			if( product.getPurchase().isGrouped() )
			{
				JMenuItem ungroupItem = new JMenuItem( "Ungroup" , ungroupIcon );
				ungroupItem.addActionListener( new ProductPopupListener( product ) );
				popupMenu.add( ungroupItem );
			}
			else
			{
				JMenuItem ungroupItem = new JMenuItem( "Group" , groupIcon );
				ungroupItem.addActionListener( new ProductPopupListener( product ) );
				popupMenu.add( ungroupItem );
			}
		}
			
		JMenuItem viewPurchaseItem = new JMenuItem( "View purchase" , viewPurchaseIcon );
		viewPurchaseItem.addActionListener( new ProductPopupListener( product ) );
		popupMenu.add( viewPurchaseItem );
		
		if( product.isSold() && !product.getPurchase().isGrouped() )
		{
			JMenuItem viewSaleItem = new JMenuItem( "View sale" , viewSaleIcon );
			viewSaleItem.addActionListener( new ProductPopupListener( product ) );
			popupMenu.add( viewSaleItem );
		}
		
		return popupMenu;
		
	}
	public JPopupMenu createRightClickMenu( Purchase purchase ){
		
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem editPurchaseItem = new JMenuItem( "Edit" , editIcon );
		editPurchaseItem.addActionListener( new PurchasePopupListener( purchase ) );
		popupMenu.add( editPurchaseItem );
		
		JMenuItem deletePurchaseItem = new JMenuItem( "Delete" , deleteIcon );
		deletePurchaseItem.addActionListener( new PurchasePopupListener( purchase ) );
		popupMenu.add( deletePurchaseItem );
		
		if( purchase.isReceived() )
		{
			JMenuItem unreceivePurchaseItem = new JMenuItem( "UN-Receive" , unreceiveIcon );
			unreceivePurchaseItem.addActionListener( new PurchasePopupListener( purchase ) );
			popupMenu.add( unreceivePurchaseItem );
		}
		else
		{
			JMenuItem receivePurchaseItem = new JMenuItem( "Receive" , receiveIcon );
			receivePurchaseItem.addActionListener( new PurchasePopupListener( purchase ) );
			popupMenu.add( receivePurchaseItem );
		}
		
		JMenuItem viewProductsItem = new JMenuItem( "View products" , viewProductsIcon );
		viewProductsItem.addActionListener( new PurchasePopupListener( purchase ) );
		popupMenu.add( viewProductsItem );
		
		return popupMenu;
		
	}
	public JPopupMenu createRightClickMenu( Sale sale ){
		
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem editItem = new JMenuItem( "Edit" );
		editItem.addActionListener( new SalePopupListener( sale ) );
		popupMenu.add( editItem );
		
		JMenuItem deleteItem = new JMenuItem( "Delete" );
		deleteItem.addActionListener( new SalePopupListener( sale ) );
		popupMenu.add( deleteItem );
		
		JMenuItem viewProductsItem = new JMenuItem( "View products" );
		viewProductsItem.addActionListener( new SalePopupListener( sale ) );
		popupMenu.add( viewProductsItem );
		
		return popupMenu;
		
	}
	
	//double click
	public void doubleClickedPurchaseTable( Point point ){
		
		int displayedR = purchasePanel.purchaseTable.rowAtPoint( point );
		if( displayedR != -1 )
		{
			editPurchasePushed();
		}
		
	}
	public void doubleClickedSaleTable( Point point ){
		
		int displayedR = salePanel.saleTable.rowAtPoint( point );
		if( displayedR != -1 )
		{
			editSalePushed();
		}
		
	}
	
	
	public void exitPushed(){
		
		System.out.println( getSize() );
		productPanel.productTable.printColumnWidths();
		purchasePanel.purchaseTable.printColumnWidths();
		salePanel.saleTable.printColumnWidths();
			
		int response = ButtonDialog.showDialog( null , "Save?" , "Yes" , "No" , "Cancel"  );
		if( response == 0 )//OK
		{
			save();
			System.exit( 0 );
		}
		else if( response == 1 )//No
		{
			System.exit( 0 );
		}
		else if( response == 2 )//Cancel
		{
			
		}
		else if( response == -1 )//x button
		{
			
		}
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//listeners
	private class BtnListener implements ActionListener {

		
		public void actionPerformed( ActionEvent ae ) {
			
			
			if( ae.getSource() == saveMenuItem )
			{
				boolean successful = save();
				if( successful )
				{
					JOptionPane.showMessageDialog( null , "Save successful" );
				}
			}
			else if( ae.getSource() == closeMenuItem )
			{
				exitPushed();
			}
			
			////////////////////////////////////////////////////////////////////
			
			
			else if( ae.getSource() == productPanel.sellBtn )
			{
				sellPushed();
			}
			else if( ae.getSource() == productPanel.viewPurchaseBtn )
			{
				viewPurchasePushed();
			}
			else if( ae.getSource() == productPanel.viewSaleBtn )
			{
				viewSalePushed();
			}
			else if( ae.getSource() == productPanel.statusFilterBox )
			{
				productStatusFilterBoxChanged();
			}
			
			
			
			else if( ae.getSource() == productPanel.categoryFilterPanel.allBtn )
			{
				productCategoryFilterButtonsChanged( 0 );
			}
			else if( ae.getSource() == productPanel.categoryFilterPanel.baseButton )
			{
				productCategoryFilterButtonsChanged( 1 );
			}
			else if( ae.getSource() == productPanel.categoryFilterPanel.modBtn )
			{
				productCategoryFilterButtonsChanged( 2 );
			}
			else if( ae.getSource() == productPanel.categoryFilterPanel.accessoryBtn )
			{
				productCategoryFilterButtonsChanged( 3 );
			}
			else if( ae.getSource() == productPanel.categoryFilterPanel.extraBtn )
			{
				productCategoryFilterButtonsChanged( 4 );
			}
			else if( ae.getSource() == productPanel.categoryFilterPanel.otherBtn )
			{
				productCategoryFilterButtonsChanged( 5 );
			}
			
			////////////////////////////////////////////////////////////////////
			
			
			else if( ae.getSource() == purchasePanel.addBtn )
			{
				addPurchasePushed();
			}
			else if( ae.getSource() == purchasePanel.editBtn )
			{
				editPurchasePushed();
			}
			else if( ae.getSource() == purchasePanel.deleteBtn )
			{
				deletePurchasePushed();
			}
			else if( ae.getSource() == purchasePanel.receiveBtn )
			{
				receivePurchasePushed();
			}
			else if( ae.getSource() == purchasePanel.viewProductsBtn )
			{
				viewPurchaseProductsPushed();
			}
			
			
			////////////////////////////////////////////////////////////////////
			
			
			else if( ae.getSource() == salePanel.editBtn )
			{
				editSalePushed();
			}
			else if( ae.getSource() == salePanel.deleteBtn )
			{
				deleteSalePushed();
			}
			else if( ae.getSource() == salePanel.addToSaleBtn )
			{
				addToSalePushed();
			}
			else if( ae.getSource() == salePanel.viewProductsBtn )
			{
				viewSaleProductsPushed();
			}
			
			
			////////////////////////////////////////////////////////////////////
			
			
			
			
			
		}
		
		
		
	}
	private class TableSelectionListener extends MyTableSelectionListener {
		
				
		public void selectionChanged(){
			
			
			
			if( this.getTable() == productPanel.productTable )
			{
				//printSelectionChange( "product" );
				productTableSelectionChanged( this.getNewRows() );
			}
			 else if( this.getTable() == purchasePanel.purchaseTable )
			{
				//printSelectionChange( "purchase" );
				purchaseTableSelectionChanged( purchasePanel.purchaseTable.getSelectedRow() );
			}
			
			else if( this.getTable() == salePanel.saleTable )
			{
				//printSelectionChange( "sale" );
				saleTableSelectionChanged( salePanel.saleTable.getSelectedRow() );
			}
			
			
			
		}



	}
	private class MyWindowListener implements WindowListener {

		
		public void windowOpened( WindowEvent we ){}
		public void windowClosing( WindowEvent we ){
			
			
			exitPushed();
			
			
		}
		public void windowClosed( WindowEvent we ){}
		public void windowIconified( WindowEvent we ){}
		public void windowDeiconified( WindowEvent we ){}
		public void windowActivated( WindowEvent we ){}
		public void windowDeactivated( WindowEvent we ){}
		
		
		
	}
	private class TableMouseListener implements MouseListener {

		
		public void mouseClicked( MouseEvent me ){
			
			
			if( me.getSource() == productPanel.productTable )
			{
				if( me.getButton() == MouseEvent.BUTTON3 )
				{
					rightClickedProductTable( me.getPoint() );
					
				}
				
				
			}
			else if( me.getSource() == purchasePanel.purchaseTable )
			{
				if( me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() == 2 )
				{
					doubleClickedPurchaseTable( me.getPoint() );
				}
				if( me.getButton() == MouseEvent.BUTTON3 )
				{
					rightClickedPurchaseTable( me.getPoint() );
				}
				
			}
			else if( me.getSource() == salePanel.saleTable )
			{
				if( me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() == 2 )
				{
					doubleClickedSaleTable( me.getPoint() );
				}
				if( me.getButton() == MouseEvent.BUTTON3 )
				{
					rightClickedSaleTable( me.getPoint() );
				}
				
			}
			
		}
		public void mousePressed( MouseEvent me ){}
		public void mouseReleased( MouseEvent me ){}
		public void mouseEntered( MouseEvent me ){}
		public void mouseExited( MouseEvent me ){}
		
		
		
		
	}	
	
	private class ProductPopupListener implements ActionListener{

		
		private Product product;
		
		public ProductPopupListener( Product product ){
			
			this.product = product;
			
		}
		
		public void actionPerformed( ActionEvent ae ) {
			
			
			JMenuItem menuItem = (JMenuItem) ae.getSource();
			if( menuItem.getText().equals( "Group" ) )
			{
				product.getPurchase().setGrouped( true );
				applyAllFilters();
				//productPanel.productTable.setSelected( selectedProduct.getPurchase().getRepresentative(), true );
				viewProducts( product.getPurchase().getProducts() );
			}
			else if( menuItem.getText().equals( "Ungroup" ) )
			{
				product.getPurchase().setGrouped( false );
				applyAllFilters();
				viewProducts( product.getPurchase().getProducts() );
			}
			else if( menuItem.getText().equals( "View purchase" ) )
			{
				viewPurchase( product.getPurchase() );
			}
			else if( menuItem.getText().equals( "View sale" ) )
			{
				viewSale( product.getSale() );
			}
			
			
		}
		
		
		
		
		
	}
	private class PurchasePopupListener implements ActionListener{

		
		private Purchase purchase;
		
		public PurchasePopupListener( Purchase purchase ){
			
			this.purchase = purchase;
			
		}
		
		public void actionPerformed( ActionEvent ae ) {
			
			
			
			JMenuItem menuItem = (JMenuItem) ae.getSource();
			if( menuItem.getText().equals( "Edit" ) )
			{
				editPurchasePushed();
			}
			else if( menuItem.getText().equals( "Delete" ) )
			{
				deletePurchasePushed();
			}
			else if( menuItem.getText().equals( "Receive" ) )
			{
				receivePurchasePushed();//handles both receive and unreceive
			}
			else if( menuItem.getText().equals( "UN-Receive" ) )
			{
				receivePurchasePushed();//handles both receive and unreceive
			}
			else if( menuItem.getText().equals( "View products" ) )
			{
				viewPurchaseProductsPushed();
			}
			
			
		}
		
		
		
		
		
	}
	private class SalePopupListener implements ActionListener{

		
		private Sale sale;
		
		public SalePopupListener( Sale sale ){
			
			this.sale = sale;
			
		}
		
		public void actionPerformed( ActionEvent ae ) {
			
			
			
			JMenuItem menuItem = (JMenuItem) ae.getSource();
			if( menuItem.getText().equals( "Edit" ) )
			{
				editSalePushed();
			}
			else if( menuItem.getText().equals( "Delete" ) )
			{
				deleteSalePushed();
			}
			else if( menuItem.getText().equals( "View products" ) )
			{
				viewSaleProductsPushed();
			}
			
			
		}
		
		
		
		
		
	}
	
	private class SaleMonthListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			
			if( e.getSource() == salePanel.monthBox || e.getSource() == salePanel.yearBox )
			{
				saleDateBoxChanged();
			}
			else if( e.getSource() == salePanel.leftArrowBtn )
			{
				int year = (Integer)salePanel.yearBox.getSelectedItem();
				int month = salePanel.monthBox.getSelectedIndex();
				
				

				if( month == 0 )
				{
					salePanel.monthBox.setSelectedIndex( 11 );
					salePanel.yearBox.setSelectedItem( year-1 );
				}
				else
				{
					salePanel.monthBox.setSelectedIndex( month-1 );
				}
				
			}
			else if( e.getSource() == salePanel.rightArrowBtn )
			{
				int year = (Integer)salePanel.yearBox.getSelectedItem();
				int month = salePanel.monthBox.getSelectedIndex();
				
				

				if( month == 11 )
				{
					salePanel.monthBox.setSelectedIndex( 0 );
					salePanel.yearBox.setSelectedItem( year+1 );
				}
				else
				{
					salePanel.monthBox.setSelectedIndex( month+1 );
				}
				
			}
			
			
		}
		
		
		
		
	}
	private class HiddenMouseListener implements MouseListener,MouseMotionListener {

		
		public void mouseClicked( MouseEvent me ) {
			
			
			
			
			
			if( salePanel.isButton( me.getX() , me.getY() )  &&  me.getClickCount() % 2 == 0 /*me.getClickCount() == 2*/ )
			{
				doubleClickedOnHiddenSquare();
			}
			
			
		}
		public void mousePressed( MouseEvent me ) {}
		public void mouseReleased( MouseEvent me ) {}
		public void mouseEntered( MouseEvent me ) {}
		public void mouseExited( MouseEvent me ) {}
		public void mouseDragged( MouseEvent me ) {}
		public void mouseMoved( MouseEvent me ) {}
		
		
		
		
	}
	
	
	
	public static void main( String[] args ) {
		
		try{ UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); } catch ( Exception ex ){}
		//try{ UIManager.setLookAndFeel( "javax.swing.plaf.nimbus.NimbusLookAndFeel"); } catch ( Exception ex ){}
		
		
		
		
		new EbayUtility().setVisible( true );
		
	}
	
	
	
	
	
}















