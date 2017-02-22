package model;

import java.awt.Color;
import java.util.ArrayList;

public class Stock {
	
	private ArrayList<String[]> info;
	private ArrayList<MovingAverage> movingAverages;
	private String name;
	private Color color = Color.BLACK;
	
	public Stock(String name, ArrayList<String[]> data){
		this.name = name;
		info = data;
		movingAverages = new ArrayList<MovingAverage>();
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<String[]> getInfo(){
		return info;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int numOfCharts(){
		return 1+movingAverages.size();
	}
	
	public ArrayList<MovingAverage> getMovingAverages(){
		return movingAverages;
	}
	
	public MovingAverage getMA(int index){
		return movingAverages.get(index);
	}

}
