package controller;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartPanel;

import model.MovingAverage;
import model.Stock;
import model.StockMarketModel;
import view.LoginPage;
import view.UserInterface;

public class Controller {
	
	private LoginPage login;
	private StockMarketModel model;
	private DataReader reader;
	private ChartDrawer drawer;
	private MACalculator maCalculator;
	UserInterface user;
	
	public Controller(LoginPage login, StockMarketModel model){
		
		this.login = login;
		this.model = model;
		drawer = new ChartDrawer();
		
	}
	
	public void addUserInterface(UserInterface ui){
		user = ui;
	}
	
	public boolean authorizeUser(String name, String password){
		if(name.equals("team c") && password.equals("6461")){
			return true;
		}
		return false;
	}

	public void readData(boolean selected){
		if(selected){
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(user);
			
			if (result == JFileChooser.APPROVE_OPTION) {
				
			    File selectedFile = fileChooser.getSelectedFile();
			    
			    if(!selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().length()-4).equals(".csv")){
			    	JOptionPane.showMessageDialog(null, "Wrong file type. Please select csv file !!","ERROR", JOptionPane.ERROR_MESSAGE);
			    	return;
			    }
			    
				List a=null;
				try {
					reader = new CSVFileReader();
					a = reader.readAll(new FileReader(selectedFile.getAbsolutePath()));
					model.addStock(new Stock("Stock"+(model.getSize()+1),(ArrayList<String[]>)a, model.getObservers()));
					
					
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Failed to read the file. Please try again !","ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
				
				JOptionPane.showMessageDialog(null, "File was successfully read !.","Done", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}


	public ChartPanel drawStock(int selection){
	
		int range=0;
		switch(selection){
		case 0 : range = -1;break;
		case 1 : range = 3;break;
		case 2 : range = 6;break;
		case 3 : range = 1; break;
		case 4 : range = 2;break;
		}
		
		return drawer.draw(model.getStock(model.getCurrentStock()), range);
	}

	public void changeCurrentStock(String name){
		model.setCurrentStock(name);
	}

	public void addMA(int selection, int period , Color c){
		if(selection == 0){
			maCalculator = new SimpleMACalculator();
		}
		MovingAverage ma =  maCalculator.calculate(model.getStock(model.getCurrentStock()), period, c);
		model.getStock(model.getCurrentStock()).addMA(ma);
	}

	public void deletMA(int index){
		model.getStock(model.getCurrentStock()).removeMA(index);
	}
	
	public String findSellBuyPoints(){
		maCalculator = new SimpleMACalculator();
		String points = maCalculator.findSBPoints(model.getStock(model.getCurrentStock()));
		return points;
	}
}
