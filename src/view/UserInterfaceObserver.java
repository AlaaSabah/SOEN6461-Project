package view;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartPanel;

import controller.Controller;
import model.MovingAverage;
import model.StockSubject;
import model.StockMarketModelSubject;

import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ScrollPaneConstants;

public class UserInterfaceObserver extends JFrame implements Observer{

	private JPanel contentPane;
	private Controller controller;
	private StockMarketModelSubject model;
	private JComboBox stockList;
	private JLabel currentStock;
	private JComboBox maBox;
	private JPanel dataPanel;
	private final JComboBox range;
	private final JPanel watchPanel;
	
	public void registerMe(Controller c, StockMarketModelSubject m){
		controller = c;
		model = m;
		model.addObservers(this);
		stockList = new JComboBox();
		stockList.setModel(new DefaultComboBoxModel(model.getStocksList()));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					UserInterfaceObserver frame = new UserInterfaceObserver();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserInterfaceObserver(Controller c, StockMarketModelSubject m) {
		registerMe(c, m);
		
		watchPanel = new JPanel();
		watchPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		watchPanel.setLayout(new BorderLayout());
		watchPanel.setOpaque(false);
		watchPanel.setBounds(967, 159, 177, 271);
		
		String[][] watchData = controller.createWatchList(null);
		updateWatchPanel(watchData);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1160, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(230, 82, 727, 473);
		dataPanel = new JPanel();
		tabbedPane.addTab("Historical Data", dataPanel);
		dataPanel.setLayout(new BorderLayout());
		contentPane.add(tabbedPane);
		final JPanel chartPanel = new JPanel();
		tabbedPane.addTab("Chart", chartPanel);
		JTable table = new JTable(30,7);
		dataPanel.add(table);
		
		JPanel readpanel = new JPanel();
		LineBorder border = new LineBorder ( Color.BLACK, 2, true );
		readpanel.setBorder(new TitledBorder(border, "Data Resource", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		readpanel.setBounds(10, 82, 188, 102);
		readpanel.setOpaque(false);
		contentPane.add(readpanel);
		
		final JRadioButton csvFile = new JRadioButton("CSV File");
		csvFile.setOpaque(false);
		csvFile.setSelected(true);
		csvFile.setBounds(6, 27, 75, 23);
		readpanel.add(csvFile);
		
		final JRadioButton yahooStock = new JRadioButton("YahooFinance");
		yahooStock.setSelected(false);
		yahooStock.setOpaque(false);
		yahooStock.setBounds(84, 27, 98, 23);
		readpanel.add(yahooStock);
		
		final ButtonGroup group = new ButtonGroup();
		group.add(csvFile);
		group.add(yahooStock);
		
		JButton readbtn = new JButton("Get Data");
		readbtn.setBounds(53, 68, 81, 23);
		readbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = "";
				if(csvFile.isSelected())
					text = csvFile.getText();
				else
					text = yahooStock.getText();
				controller.readData(text, range.getSelectedIndex());
			}
		});
		readpanel.setLayout(null);
		readpanel.add(readbtn);
		readbtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JPanel settingpanel = new JPanel();
		settingpanel.setBorder(new TitledBorder(border, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		settingpanel.setLayout(null);
		settingpanel.setOpaque(false);
		settingpanel.setBounds(10, 195, 188, 341);
		contentPane.add(settingpanel);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBackground(new Color(192, 192, 192));
		lblStock.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStock.setBounds(20, 23, 46, 17);
		settingpanel.add(lblStock);
		
		
		stockList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(stockList.getSelectedIndex() != 0 && stockList.getSelectedIndex() != 31){
					controller.changeCurrentStock(stockList.getSelectedItem().toString());
				}	
			}
		});
		stockList.setBounds(65, 22, 113, 20);
		settingpanel.add(stockList);
		
		JLabel lblNewLabel_1 = new JLabel("Data Range");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(20, 62, 81, 14);
		settingpanel.add(lblNewLabel_1);
		
		range = new JComboBox();
		range.setModel(new DefaultComboBoxModel(new String[] {"All", "3 months", "6 months", "1 year", "2 years"}));
		range.setBounds(96, 60, 69, 20);
		settingpanel.add(range);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(null, "Moving Average", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel.setBounds(20, 99, 147, 231);
		settingpanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblMethod = new JLabel("Method");
		lblMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMethod.setBounds(10, 29, 53, 14);
		panel.add(lblMethod);
		
		final JComboBox maMethod = new JComboBox();
		maMethod.setModel(new DefaultComboBoxModel(new String[] {"Simple MA"}));
		maMethod.setBounds(64, 26, 73, 20);
		panel.add(maMethod);
		
		JLabel lblPeriod = new JLabel("Period");
		lblPeriod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPeriod.setBounds(10, 64, 46, 14);
		panel.add(lblPeriod);
		
		final JComboBox maPeriod = new JComboBox();
		maPeriod.setModel(new DefaultComboBoxModel(new String[] {"20", "50", "100", "200"}));
		maPeriod.setBounds(74, 61, 63, 20);
		panel.add(maPeriod);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblColor.setBounds(10, 100, 46, 20);
		panel.add(lblColor);
		
		final JLabel maColor = new JLabel("");
		maColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", maColor.getBackground());
			    if (c != null)
			      maColor.setBackground(c);
			}
		});
		maColor.setForeground(new Color(0, 0, 0));
		maColor.setOpaque(true);
		maColor.setBackground(Color.MAGENTA);
		maColor.setBounds(97, 100, 26, 20);
		panel.add(maColor);
		
		final JButton addbtn = new JButton("Add");
		addbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.addMA(maMethod.getSelectedIndex(), Integer.parseInt(maPeriod.getSelectedItem().toString()), maColor.getBackground());
			}
		});
		addbtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		addbtn.setBounds(40, 129, 53, 23);
		panel.add(addbtn);
		
		JButton deletebtn = new JButton("Delete");
		deletebtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		
				controller.deletMA(maBox.getSelectedIndex());
				
			}
		});
		deletebtn.setBounds(74, 163, 63, 23);
		panel.add(deletebtn);
		
		JButton sellBuybtn = new JButton("Sell/Buy Data");
		sellBuybtn.setBounds(20, 197, 110, 23);
		panel.add(sellBuybtn);
		sellBuybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = controller.findSellBuyPoints();
				if(s == null){
					s = "No intersection points !!";
				}
				JFrame prediction = new JFrame("Prediction Points");
				prediction.setLocationRelativeTo(addbtn);
				prediction.setSize(350, 200);
				JTextArea txt = new JTextArea();
				txt.setText(s);
				JScrollPane sp = new JScrollPane(txt);
				sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				prediction.getContentPane().add(sp);
				prediction.setVisible(true);
			}
		});
		sellBuybtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		maBox = new JComboBox();
		maBox.setBounds(10, 163, 53, 23);
		panel.add(maBox);
		
		JButton drawbtn = new JButton("Draw");
		drawbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChartPanel c = controller.drawStock(range.getSelectedIndex());
				if(c != null){
					 chartPanel.removeAll();
					 chartPanel.add(controller.drawStock(range.getSelectedIndex()), BorderLayout.CENTER);
					 chartPanel.validate();
				}
               
				
			}
		});
		drawbtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		drawbtn.setBounds(53, 547, 89, 38);
		contentPane.add(drawbtn);
		
		currentStock = new JLabel("Current Stock");
		currentStock.setHorizontalAlignment(SwingConstants.CENTER);
		currentStock.setFont(new Font("Tahoma", Font.BOLD, 14));
		currentStock.setBounds(510, 558, 167, 43);
		contentPane.add(currentStock);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\alaa\\Desktop\\Master\\EclipseProjects\\SOEN6461Project\\src\\images\\logo 5.png"));
		label.setBounds(857, 0, 100, 100);
		contentPane.add(label);
		
		JLabel lblNewLabel_2 = new JLabel("Stock Market Analyst");
		lblNewLabel_2.setForeground(new Color(139, 0, 0));
		lblNewLabel_2.setFont(new Font("Castellar", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(357, 23, 478, 62);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblWatchList = new JLabel("Watch List");
		lblWatchList.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWatchList.setBounds(1000, 113, 112, 27);
		contentPane.add(lblWatchList);
		
		contentPane.add(watchPanel);
		
		final JComboBox dowList = new JComboBox();
		dowList.setBounds(980, 457, 89, 20);
		String [] list = new String[]{"MMM", "AXP", "AAPL", "BA", "CAT", "CVX", "CSCO", "KO", "DD", "XOM",
				"GE", "GS", "HD", "IBM", "INTC", "JNJ", "JPM", "MCD", "MRK", "MSFT", "NKE", "PFE", "PG", "TRV", "UNH", "UTX",
				"VZ", "V", "WMT", "DIS"};
		dowList.setModel(new DefaultComboBoxModel(list));
		contentPane.add(dowList);
	
		final JComboBox watchList = new JComboBox();
		watchList.setBounds(980, 491, 89, 20);
		contentPane.add(watchList);
		
		JButton addToWatch = new JButton("Add");
		addToWatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				watchList.addItem(dowList.getSelectedItem());
			}
		});
		addToWatch.setBounds(1079, 456, 65, 23);
		contentPane.add(addToWatch);
		
		
		JButton removeFromWatch = new JButton("Delete");
		removeFromWatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(watchList.getSelectedIndex()>=0)
				watchList.removeItemAt(watchList.getSelectedIndex());
			}
		});
		removeFromWatch.setBounds(1079, 488, 65, 23);
		contentPane.add(removeFromWatch);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(watchList.getSelectedIndex()>=0){
					ComboBoxModel model = watchList.getModel();
					int size = model.getSize();
					String[] list = new String[size];
					for(int i=0 ; i<size ; i++){
						list[i] = (String)model.getElementAt(i);
					}
					String[][] watchData = controller.createWatchList(list);
					updateWatchPanel(watchData);
				}
			}
		});
		btnCreate.setBounds(1023, 532, 89, 23);
		contentPane.add(btnCreate);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(0, 0, 1173, 624);
		contentPane.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\alaa\\Desktop\\Master\\EclipseProjects\\SOEN6461Project\\src\\images\\bg 8.jpg"));
	}
	
	private void updateWatchPanel(String[][] data){
		if(data == null){
			return;
		}
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,1,3,3));
		JLabel[] labels = new JLabel[data.length];
		
		for(int i= 0 ; i<labels.length ; i++){
			if(data[i][1].equals("sell")){
				labels[i] = new JLabel(data[i][0]+"          ", new ImageIcon("src\\images\\up.png"), JLabel.RIGHT);
			}else {
				labels[i] = new JLabel(data[i][0]+"          ", new ImageIcon("src\\images\\down.png"), JLabel.RIGHT);	
			}
			labels[i].setFont(new Font("Tahoma", Font.BOLD, 18));
			labels[i].setHorizontalTextPosition(SwingConstants.LEFT);
			p.add(labels[i]);
		}
		
		JScrollPane scrollPane = new JScrollPane(p);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		watchPanel.removeAll();
		watchPanel.add(scrollPane, BorderLayout.CENTER);
		watchPanel.revalidate();
		
	}

	@Override
	public void update(Observable o, Object arg) {

		if(o instanceof StockMarketModelSubject){ //update stocks list
			String[] stocksNames = model.getStocksList();
			stockList.setModel(new DefaultComboBoxModel(stocksNames));
			stockList.setSelectedIndex(stocksNames.length-1);
			
		}
		else if(o instanceof StockSubject){ // set current stock
			
			currentStock.setText(((StockSubject) o).getName());// name label
			ArrayList<MovingAverage> m = ((StockSubject) o).getMovingAverages();// MA list
			String[] maList = new String[m.size()];
			for(int i=0 ; i<maList.length ; i++){
				maList[i] = "MA/"+m.get(i).getPeriod();
			}
			maBox.setModel(new DefaultComboBoxModel(maList));
			
			String[][] data = new String[((StockSubject) o).getInfo().size()-1][7];// data table
			
			for(int i=1 ; i<model.getStock(model.getCurrentStock()).getInfo().size() ; i++){
				data[i-1] = model.getStock(model.getCurrentStock()).getInfo().get(i); 
			}
			
			JTable table = new JTable(data, ((StockSubject) o).getInfo().get(0));
			dataPanel.removeAll();
			dataPanel.add(new JScrollPane(table));
			dataPanel.revalidate();
		}
		
	}
}
