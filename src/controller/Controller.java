package controller;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartPanel;

import model.MovingAverage;
import model.StockSubject;
import model.StockMarketModelSubject;
import view.LoginPage;
import view.UserInterfaceObserver;

public class Controller {
	
	//private LoginPage login;
	private StockMarketModelSubject model;
	private DataReader reader;
	private ChartDrawer drawer;
	private MACalculator maCalculator;
	UserInterfaceObserver user;
	
	public Controller(StockMarketModelSubject model){
		
		//this.login = login;
		this.model = model;
		drawer = new ChartDrawer();
		
	}
	
	public void addUserInterface(UserInterfaceObserver ui){
		user = ui;
	}
	
	public boolean authorizeUser(String name, String password){
		if(name.equals("team c") && password.equals("6461")){
			return true;
		}
		return false;
	}

	public void readData(String selected, int range){
		
		List a = null;
		if(selected.equals("CSV File")){
			
			reader = new CSVFileReader();
			a = reader.readAll();
			
			if(a != null){
				model.addStock(new StockSubject("Stock"+model.getCount(),(ArrayList<String[]>)a, model.getObservers()), true);
				
			}
		}else if(selected.equals("YahooFinance")){
			
			int r=0;
			switch(range){
			case 0 : r = -1;break;
			case 1 : r = 3;break;
			case 2 : r = 6;break;
			case 3 : r = 1; break;
			case 4 : r = 2;break;
			}
			reader = new Dow30Reader(model.getCurrentStock(), r);
			a = reader.readAll();
			if(a != null){
				model.addStock(new StockSubject(model.getCurrentStock(),(ArrayList<String[]>)a, model.getObservers()), false);
				
			}
			
		}
		
		if(a == null){
			JOptionPane.showMessageDialog(null, "Failed to read data !!.","Error", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(null, "File was successfully read !.","Done", JOptionPane.INFORMATION_MESSAGE);
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
		if(model.getCurrentStock() != null)
		return drawer.draw(model.getStock(model.getCurrentStock()), range);
		return null;
	}

	public void changeCurrentStock(String name){
		model.setCurrentStock(name);
	}

	public void addMA(int selection, int period , Color c){
		if(model.getCurrentStock() != null){
			if(selection == 0){
				maCalculator = new SimpleMACalculator();
			}
			MovingAverage ma =  maCalculator.calculate(model.getStock(model.getCurrentStock()), period, c);
			model.getStock(model.getCurrentStock()).addMA(ma);
		}
		
	}

	public void deletMA(int index){
		if(index>=0)
		model.getStock(model.getCurrentStock()).removeMA(index);
	}
	
	public String findSellBuyPoints(){
		if(model.getCurrentStock() != null){
			if(model.getStock(model.getCurrentStock()).getMovingAverages().size() < 2)
				return new String("Add MAs first. you should have two with different periods at least");
			maCalculator = new SimpleMACalculator();
			String points = maCalculator.findSBPoints(model.getStock(model.getCurrentStock()));
			return points;
		}
		return new String("Nothing to display");
	}
	
	public String[][] createWatchList(String[] list){
		WatchListManager manager = new WatchListManager();
		return manager.updateWatchList(list);
	}
}
