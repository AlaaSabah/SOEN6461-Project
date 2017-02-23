package controller;

import java.awt.Color;
import java.util.ArrayList;

import model.MovingAverage;
import model.Stock;

public class SimpleMACalculator implements MACalculator{

	public MovingAverage calculate(Stock stock, int period , Color c){
		
		ArrayList<String[]> a = stock.getInfo();
		ArrayList<String[]> maData = new ArrayList<String[]>();
		double sum = 0;
		for(int i=1 ; i<a.size()-period+1 ; i++){
			maData.add(new String[2]);
			(maData.get(i-1))[0] = (a.get(i))[0]; 
			for(int j=0 ; j<period ; j++){
				sum+= Double.parseDouble((a.get(i+j))[4]);
			}
			sum/=period;
			(maData.get(i-1))[1] = sum+"";
		}
		
		return new MovingAverage(c, period, maData);
	}
	
}
