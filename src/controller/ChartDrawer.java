package controller;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import model.Stock;

public class ChartDrawer {
	
public static DefaultCategoryDataset createDataset(Stock s, int range){
		
		DefaultCategoryDataset dataset= new DefaultCategoryDataset();
		ArrayList<String[]> a = s.getInfo();
		
		for(int i=1 ; i<a.size() ; i++){
			
			dataset.addValue(Double.parseDouble(((String[])a.get(i))[4]), s.getName(), ((String[])a.get(i))[0]);
		}
		
		if(s.numOfCharts()>1){
			ArrayList<String[]> d;
			for(int i=0 ; i<s.numOfCharts()-1 ; i++){
				d = s.getMA(i).getData();
				for(int j=0 ; j<d.size() ; j++)
				dataset.addValue(Double.parseDouble(((String[])a.get(i))[1]), "MA#"+(i+1), ((String[])a.get(i))[0]);
			}
		}
		//System.out.println("done");
		return dataset;
		
	}


public ChartPanel draw(Stock stock , int range){
	DefaultCategoryDataset dataset = createDataset(stock , range);
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

}
