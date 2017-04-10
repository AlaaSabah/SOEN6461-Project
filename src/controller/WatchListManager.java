package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.StockSubject;

public class WatchListManager {

	
	public String[] readWatchList(){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("src\\watchList.txt"));
			String line = br.readLine();
			if(line == null){
				br.close();
				return null;
			}
			int listSize = Integer.parseInt(line);
			String[] stocks = new String[listSize];
			for(int i = 0 ; i<listSize ; i++){
				stocks[i] = br.readLine();
			}
			br.close();
			return stocks;
		} catch (Exception e) {
			System.out.println("error while reading file !!");
			return null;
		}
	}
	
	public void writeWatchList(String[] stocks){
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter("src\\watchList.txt"));
			br.write(stocks.length+"");
			br.newLine();
			for(int i = 0 ; i<stocks.length ; i++){
				br.write(stocks[i]);
				br.newLine();
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Error while writing a file");
			e.printStackTrace();
		}
	}
	
	public StockSubject[] getStocksData(String[] stocksList){
		if(stocksList == null){
			return null;
		}
		StockSubject[] stocks = new StockSubject[stocksList.length];
		Dow30Reader reader;
		for(int i = 0 ; i<stocksList.length ; i++){
			reader = new Dow30Reader(stocksList[i], 1);
			List data = reader.readAll();
			stocks[i] = new StockSubject(stocksList[i], (ArrayList<String[]>)data, null);
		}
		return stocks;	
	}
	
	public String[][] calculatePredictions(StockSubject[] stocks){
		SimpleMACalculator maC = new SimpleMACalculator();
		String[][] predictions = new String[stocks.length][2];
		for(int i =0 ; i<stocks.length ; i++){
			stocks[i].addMA(maC.calculate(stocks[i], 20, null));
			stocks[i].addMA(maC.calculate(stocks[i], 100, null));
			ArrayList<Integer> points = maC.intersectionPoints(stocks[i].getMA(0), stocks[i].getMA(1), 1);
			if(points.size()>0){
				predictions[i][0] = stocks[i].getName();
				predictions[i][1] = maC.getPrediction(stocks[i].getMA(0), maC.intersectionPoints(stocks[i].getMA(0), stocks[i].getMA(1), 1).get(0));
			}else{
				predictions[i][0] = stocks[i].getName();
				predictions[i][1] = "None";
			}
		}
		
		return predictions;
	}
	
	public String[][] updateWatchList(String[] stocks){
		String[][] watchData = null;
		if(stocks == null){
			stocks = readWatchList();
		}
		if(stocks != null){
			writeWatchList(stocks);
			StockSubject[] stocksData = getStocksData(stocks);
			watchData = calculatePredictions(stocksData);
		}
		
		return watchData;
	}
}
