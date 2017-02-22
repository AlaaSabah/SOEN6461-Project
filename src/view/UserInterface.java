package view;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.border.TitledBorder;

import controller.Controller;
import model.StockMarketModel;

import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

public class UserInterface extends JFrame implements Observer{

	private JPanel contentPane;
	private Controller controller;
	private StockMarketModel model;
	private JComboBox stockName;
	
	public void addControllerandModel(Controller c, StockMarketModel m){
		controller = c;
		model = m;
		model.addObservers(this);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface frame = new UserInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserInterface() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 973, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(230, 82, 727, 473);
		JPanel dataPanel = new JPanel();
		tabbedPane.addTab("Historical Data", dataPanel);
		contentPane.add(tabbedPane);
		final JPanel chartPanel = new JPanel();
		tabbedPane.addTab("Chart", chartPanel);
		
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
		
		JButton readbtn = new JButton("Get Data");
		readbtn.setBounds(53, 68, 81, 23);
		readbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.readData(csvFile.isSelected());
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
		
		JButton sellBuybtn = new JButton("Sell/Buy Data");
		sellBuybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		sellBuybtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sellBuybtn.setBounds(42, 284, 110, 23);
		settingpanel.add(sellBuybtn);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBackground(new Color(192, 192, 192));
		lblStock.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStock.setBounds(20, 23, 46, 17);
		settingpanel.add(lblStock);
		
		stockName = new JComboBox();
		stockName.setBounds(76, 22, 89, 20);
		settingpanel.add(stockName);
		
		JLabel lblNewLabel_1 = new JLabel("Data Range");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(20, 62, 81, 14);
		settingpanel.add(lblNewLabel_1);
		
		JComboBox range = new JComboBox();
		range.setModel(new DefaultComboBoxModel(new String[] {"3 months", "6 months", "1 year", "2 years"}));
		range.setBounds(96, 60, 69, 20);
		settingpanel.add(range);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(null, "Moving Average", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel.setBounds(20, 99, 147, 164);
		settingpanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblMethod = new JLabel("Method");
		lblMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMethod.setBounds(10, 29, 53, 14);
		panel.add(lblMethod);
		
		JComboBox maMethod = new JComboBox();
		maMethod.setModel(new DefaultComboBoxModel(new String[] {"Simple MA"}));
		maMethod.setBounds(64, 26, 73, 20);
		panel.add(maMethod);
		
		JLabel lblPeriod = new JLabel("Period");
		lblPeriod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPeriod.setBounds(10, 64, 46, 14);
		panel.add(lblPeriod);
		
		JComboBox maPeriod = new JComboBox();
		maPeriod.setModel(new DefaultComboBoxModel(new String[] {"20", "50", "100", "200"}));
		maPeriod.setBounds(74, 61, 63, 20);
		panel.add(maPeriod);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblColor.setBounds(10, 100, 46, 20);
		panel.add(lblColor);
		
		JLabel maColor = new JLabel("");
		maColor.setForeground(new Color(0, 0, 0));
		maColor.setOpaque(true);
		maColor.setBackground(Color.MAGENTA);
		maColor.setBounds(94, 100, 26, 20);
		panel.add(maColor);
		
		JButton addbtn = new JButton("Add");
		addbtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		addbtn.setBounds(10, 131, 53, 23);
		panel.add(addbtn);
		
		JButton editbtn = new JButton("Edit");
		editbtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		editbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		editbtn.setBounds(84, 131, 53, 23);
		panel.add(editbtn);
		
		JButton drawbtn = new JButton("Draw");
		drawbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                chartPanel.removeAll();
				chartPanel.add(controller.drawStock(model.getCurrentStock(),0), BorderLayout.CENTER);
				chartPanel.validate();
				
			}
		});
		drawbtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		drawbtn.setBounds(53, 547, 89, 38);
		contentPane.add(drawbtn);
		
		JLabel currentStock = new JLabel("Current Stock");
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
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\alaa\\Desktop\\Master\\EclipseProjects\\SOEN6461Project\\src\\images\\bg 8.jpg"));
		lblNewLabel.setBounds(0, 0, 977, 601);
		contentPane.add(lblNewLabel);
	}

	@Override
	public void update(Observable o, Object arg) {

		String[] stocksNames = model.getStocksNames();
		stockName.setModel(new DefaultComboBoxModel(stocksNames));
		
	}
}
