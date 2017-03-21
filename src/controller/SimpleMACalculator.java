package controller;

import java.awt.Color;
import java.util.ArrayList;

import model.MovingAverage;
import model.StockSubject;

public class SimpleMACalculator implements MACalculator{

	public MovingAverage calculate(StockSubject stock, int period , Color c){
		
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
		String ss = intersectionPoints(least, most);
		//print(least,most);
		return ss;
	}
	
//	public void print(MovingAverage least, MovingAverage most){
//		int size = Math.min(least.getData().size(), most.getData().size());
//		
//		for(int i=0 ; i<700 ; i++){
//			System.out.println((least.getData().get(i))[0]+">>>"+(least.getData().get(i))[1]+"		"+(most.getData().get(i))[0]+">>>"+(most.getData().get(i))[1]);
//		}
//	}


	private String intersectionPoints(MovingAverage least, MovingAverage most) {
		
		int size = Math.min(least.getData().size(), most.getData().size());
		ArrayList<Integer> p = new ArrayList<Integer>();
		String s="Sell/Buy predictions are as following in the previous period:\n";
		
		for(int i=1 ; i<size ; i++){
			if((int)(Double.parseDouble((least.getData().get(i))[1])+0.5) == (int)(Double.parseDouble((most.getData().get(i))[1])+0.5)){
				if(p.size() == 0)
				p.add(i);
				else{
					if(Math.abs(p.get(p.size()-1)-i) > 30)
						p.add(i);
				}
			}
		}
		
		int numP=p.size();
		if(numP > 5)
			numP = 5;
		for(int i=0 ; i<numP ; i++){
			
			if(Double.parseDouble((least.getData().get(p.get(i)+1))[1]) < Double.parseDouble((least.getData().get(p.get(i)))[1]) &&
					Double.parseDouble((least.getData().get(p.get(i)-1))[1]) > Double.parseDouble((least.getData().get(p.get(i)))[1])){
				
				if(i == 0){
					s+="In the period "+(least.getData().get(p.get(i)))[0]+" up to now, you should sell\n";
				}else
					s+="In the period "+(least.getData().get(p.get(i)))[0]+" to "+(least.getData().get(p.get(i-1)))[0]+", you should sell\n";
			
			}else if(Double.parseDouble((least.getData().get(p.get(i)+1))[1]) > Double.parseDouble((least.getData().get(p.get(i)))[1]) &&
					Double.parseDouble((least.getData().get(p.get(i)-1))[1]) < Double.parseDouble((least.getData().get(p.get(i)))[1])){
				
				if(i == 0){
					s+="In the period "+(least.getData().get(p.get(i)))[0]+" up to now, you should buy\n";
				}else
					s+="In the period "+(least.getData().get(p.get(i)))[0]+" to "+(least.getData().get(p.get(i-1)))[0]+", you should buy\n";
			}
			
		}
		return s;
	}
	
}
