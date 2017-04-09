package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class StockMarketModelSubject extends Observable{
	
	private ArrayList<StockSubject> stocks;
	private ArrayList<Observer> observers;
	private String currentstock;
	private String[] stocksList;
	private int count = 30;
	
	public StockMarketModelSubject(){
		stocks = new ArrayList<StockSubject>();
		observers = new ArrayList<Observer>();
		stocksList = new String[]{"Dow 30 Stocks", "MMM", "AXP", "AAPL", "BA", "CAT", "CVX", "CSCO", "KO", "DD", "XOM",
				"GE", "GS", "HD", "IBM", "INTC", "JNJ", "JPM", "MCD", "MRK", "MSFT", "NKE", "PFE", "PG", "TRV", "UNH", "UTX",
				"VZ", "V", "WMT", "DIS", "CSV Files"};
	}

	public void addObservers(Observer o){
		observers.add(o);
	}
	
	public ArrayList<Observer> getObservers(){
		return observers;
	}
	
	public void addStock(StockSubject s, boolean updateList){
		if(!updateList){
			if(this.getStock(s.getName())==null){
				stocks.add(s);
			}
			else{
				this.getStock(s.getName()).setInfo(s.getInfo());
			}
		}
		else{
			stocks.add(s);
			updateStocksList(s);
		}
		
		setCurrentStock(s.getName());	
	}
	
	public String getCurrentStock(){
		return currentstock;
	}
	
	public void setCurrentStock(String name){
		currentstock = name;
		notifyView(this.getStock(name));
	}
	
	public StockSubject getStock(String StockName){
		for(int i=0 ; i<stocks.size() ; i++){
			if(stocks.get(i).getName().equals(StockName)){
				return stocks.get(i);
			}
		}
		return null;
	}
	
	public String[] getStocksNames(){
		String[] names = new String[stocks.size()];
		for(int i=0 ; i<stocks.size() ; i++){
			names[i] = stocks.get(i).getName();		
			}
		return names;
		}
	
	public int getSize(){
		return stocks.size();
	}
	
	private void notifyView(Observable o){
		for(int i=0 ; i<observers.size() ; i++){
			observers.get(i).update(o, null);
		}
	}

	public String[] getStocksList() {
		return stocksList;
	}
	
	public int getCount(){
		count++;
		return count;
	}

	public void updateStocksList(StockSubject s) {
		System.out.println(stocksList.length);
		String[] a = new String[stocksList.length+1];
		for(int i=0 ; i<stocksList.length ; i++){
			a[i] = stocksList[i];
		}
		a[a.length-1] = s.getName();	
		stocksList = a;
		notifyView(this);
	}
}
