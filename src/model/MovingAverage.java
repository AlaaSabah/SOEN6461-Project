package model;

import java.awt.Color;
import java.util.ArrayList;

public class MovingAverage {

	private Color color;
	private ArrayList<String[]> data;
	
	public MovingAverage(){
		data = new ArrayList<String[]>();
	}
	
	public Color getColor(){
		return color;
	}
	
	public ArrayList<String[]> getData(){
		return data;
	}
}
