package model;

import java.awt.Color;
import java.util.ArrayList;

public class MovingAverage {

	private Color color;
	private ArrayList<String[]> data;
	private int period;
	
	public MovingAverage(Color c, int period, ArrayList<String[]> a){
		data = a;
		color = c;
		this.period = period;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public ArrayList<String[]> getData(){
		return data;
	}
	
	public void getData(ArrayList<String[]> a){
		data = a;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
}
