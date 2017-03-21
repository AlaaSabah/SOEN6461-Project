package controller;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import model.StockSubject;

public class ChartDrawer {
	
private DefaultCategoryDataset createDataset(StockSubject s, int range){
		
		DefaultCategoryDataset dataset= new DefaultCategoryDataset();
		ArrayList<String[]> a = s.getInfo();
		String base = calculateDateRange(range , ((String[])a.get(1))[0]);
		int index=0;
		for(int i=1 ; i<a.size() ; i++){
			if(compareDates(base, ((String[])a.get(i))[0]))
				index = i;
			else
				break;
		}
		
		for(int i=index ; i>0 ; i--){
			dataset.addValue(Double.parseDouble(((String[])a.get(i))[4]), s.getName(), ((String[])a.get(i))[0]);
		}
		
		
		if(s.numOfCharts()>1){
			ArrayList<String[]> d;
			for(int i=0 ; i<s.numOfCharts()-1 ; i++){
				d = s.getMA(i).getData();
				index=0;
				for(int j=0 ; j<d.size() ; j++)
					if(compareDates(base, ((String[])d.get(j))[0]))
						index=j;
					else
						break;
				
				for(int j=index ; j>=0 ; j--){
					dataset.addValue(Double.parseDouble(((String[])d.get(j))[1]), "MA#"+(i+1), ((String[])d.get(j))[0]);
				}
			}
		}
		return dataset;
	}

private DefaultCategoryDataset createDataset(StockSubject s){
	
	DefaultCategoryDataset dataset= new DefaultCategoryDataset();
	ArrayList<String[]> a = s.getInfo();
	
	for(int i=a.size()-1 ; i>0 ; i--){
		dataset.addValue(Double.parseDouble(((String[])a.get(i))[4]), s.getName(), ((String[])a.get(i))[0]);
	}
	
	if(s.numOfCharts()>1){
		ArrayList<String[]> d;
		for(int i=0 ; i<s.numOfCharts()-1 ; i++){
			d = s.getMA(i).getData();
			for(int j=d.size()-1 ; j>=0 ; j--)
			dataset.addValue(Double.parseDouble(((String[])d.get(j))[1]), "MA#"+(i+1), ((String[])d.get(j))[0]);
		}
	}
	return dataset;
}


private String calculateDateRange(int range, String last) {
	String first="";
	String[] date = last.split("-");
	int y = Integer.parseInt(date[0]);
	int m = Integer.parseInt(date[1]);
	int newY=y;
	int newM=m;
	
	if(range == 3){
		newM = m-3;
		newY =y;
		if(newM<=0){
			newM+=12;
			newY-=1;
		}
	}else if(range == 6){
		newM = m-6;
		newY = y;
		if(newM<=0){
			newM+=12;
			newY-=1;
		}
	}else if(range ==1){
		newY = y-1;
	}else if(range == 2){
		newY = y-2;
	}
	
	if(newM<10){
		first+=newY+"-0"+newM+"-"+date[2];
	}else{
		first+=newY+"-"+newM+"-"+date[2];
	}

	return first;
}


public ChartPanel draw(StockSubject stock , int range){
	DefaultCategoryDataset dataset;
	if(range == -1){
		dataset = createDataset(stock);
	}else
	    dataset = createDataset(stock , range);
	JFreeChart chart = ChartFactory.createLineChart("Chart", "date", "price", dataset, PlotOrientation.VERTICAL, true, true, true);
	
	CategoryPlot catPlot = chart.getCategoryPlot();
	for(int i=0; i<stock.numOfCharts() ; i++){
		if(i==0)
		catPlot.getRenderer().setSeriesPaint(0, stock.getColor());
		else
		catPlot.getRenderer().setSeriesPaint(i, stock.getMA(i-1).getColor());
	}
	
	catPlot.setRangeGridlinePaint(Color.BLACK);
	ChartPanel chartPanel = new ChartPanel(chart);
	return chartPanel;
}

private boolean compareDates(String base, String d2){
	try {
		Date baseDate = new SimpleDateFormat("yyyy-MM-dd").parse(base);
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(d2);
		if(date2.after(baseDate)){
			return true;
		}
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return false;
}

}
