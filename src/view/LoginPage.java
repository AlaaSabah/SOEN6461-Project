package view;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import model.StockMarketModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class LoginPage extends JFrame {

	private JPanel contentPane;
	private JTextField nameTxt;
	private JTextField passTxt;
	private LoginPage login = this ;
    private Controller controller;
    private StockMarketModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void addController(Controller c){
		controller = c;
	}
	
	public void addModel(StockMarketModel m){
		model = m;
	}

	/**
	 * Create the frame.
	 */
	public LoginPage() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(286, 247, 63, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(286, 289, 63, 14);
		contentPane.add(lblNewLabel_2);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(388, 245, 129, 20);
		contentPane.add(nameTxt);
		nameTxt.setColumns(10);
		
		passTxt = new JTextField();
		passTxt.setBounds(388, 287, 129, 20);
		contentPane.add(passTxt);
		passTxt.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\alaa\\Desktop\\Master\\EclipseProjects\\SOEN6461Project\\src\\images\\login 3.png"));
		lblNewLabel_3.setBounds(10, 74, 416, 252);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Log in");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean b = controller.authorizeUser(nameTxt.getText(), passTxt.getText());
				if(b){
					UserInterface user = new UserInterface();
					user.addControllerandModel(controller,model);
					
					controller.addUserInterface(user);
					user.setVisible(true);
					login.setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(null, "Error name or password !!","Unauthorized user", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBackground(new Color(240, 240, 240));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(411, 328, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel label = new JLabel("Stock Market Analyst");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(139, 0, 0));
		label.setFont(new Font("Castellar", Font.BOLD | Font.ITALIC, 30));
		label.setBounds(50, 11, 478, 62);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\Users\\alaa\\Desktop\\Master\\EclipseProjects\\SOEN6461Project\\src\\images\\logo 5.png"));
		label_1.setBounds(471, 97, 100, 100);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\alaa\\Desktop\\Master\\EclipseProjects\\SOEN6461Project\\src\\images\\bg 8.jpg"));
		lblNewLabel.setBounds(0, 0, 598, 389);
		contentPane.add(lblNewLabel);
	}
}
