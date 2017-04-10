package controller;

import java.awt.Color;
import java.util.ArrayList;

import model.MovingAverage;
import model.StockSubject;

public class SimpleMACalculator implements MACalculator{

	public MovingAverage calculate(StockSubject stock, int period , Color c){
		
		ArrayList<String[]> a = stock.getInfo();
		ArrayList<String[]> maData = new ArrayList<String[]>();
		double sum = 0.0;
		for(int i=1 ; i<a.size()-period+1 ; i++){
			sum=0.0;
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

	@Override
	public String findSBPoints(StockSubject s) {
		
		ArrayList<MovingAverage> ma = s.getMovingAverages();
		MovingAverage least = ma.get(0);
		MovingAverage most = ma.get(0);
		for(int i=1 ; i<ma.size() ; i++){
			if(ma.get(i).getPeriod() < least.getPeriod())
				least = ma.get(i);
			if(ma.get(i).getPeriod() > most.getPeriod())
				most = ma.get(i);
		}
		
		if(least.getPeriod() == most.getPeriod())
			return null;
		ArrayList<Integer> points = intersectionPoints(least, most, 5);
		if(points.size()>0)
			return predictionStatement(least, points);
		return "No intersection points between the moving averages !";
	}
	



	public ArrayList<Integer> intersectionPoints(MovingAverage least, MovingAverage most, int noOfPoints) {
		
		int size = Math.min(least.getData().size(), most.getData().size());
		ArrayList<Integer> p = new ArrayList<Integer>();
		String s="Sell/Buy predictions are as following in the previous period:\n";
		
		for(int i=2 ; i<size ; i++){
			if(p.size() == noOfPoints)
				break;
			if((int)(Double.parseDouble((least.getData().get(i))[1])+0.5) == (int)(Double.parseDouble((most.getData().get(i))[1])+0.5)){
				
				if(p.size() == 0)
				p.add(i);
				else{
					if(Math.abs(p.get(p.size()-1)-i) > 25)
						p.add(i);
				}
			}
		}
		
		return p;	
	}
	
	public String predictionStatement(MovingAverage least, ArrayList<Integer> points){
		
		String statement="Sell/Buy predictions are as following in the previous period:\n";
		statement+="In the period "+(least.getData().get(points.get(0)))[0]+" up to now, you should "+getPrediction(least, points.get(0))+"\n";
        for(int i=1 ; i<points.size() ; i++)
        	statement+="In the period "+(least.getData().get(points.get(i)))[0]+" to "+(least.getData().get(points.get(i-1)))[0]+", you should "+getPrediction(least, points.get(i))+"\n";
        
        return statement;

	}
	
	public String getPrediction(MovingAverage least, int index){
		int x = 5;
		if(index<5)
			x =index-1;
		if(index+5 > least.getData().size()-1)
			x = least.getData().size()-1-index;
		if(Double.parseDouble((least.getData().get(index+x))[1]) < Double.parseDouble((least.getData().get(index))[1]) ||
				Double.parseDouble((least.getData().get(index-x))[1]) > Double.parseDouble((least.getData().get(index))[1])){
			
				return "sell";
		
		}else if(Double.parseDouble((least.getData().get(index+x))[1]) > Double.parseDouble((least.getData().get(index))[1]) ||
				Double.parseDouble((least.getData().get(index-x))[1]) < Double.parseDouble((least.getData().get(index))[1])){
			
			return "buy";
		}else if(Double.parseDouble((least.getData().get(index-5))[1]) > Double.parseDouble((least.getData().get(index))[1])){
			return "sell";
		}else
			return "buy";
		
	}
	
}
