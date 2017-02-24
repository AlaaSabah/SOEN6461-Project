package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import view.UserInterface;

public class Stock extends Observable{
	
	private ArrayList<String[]> info;
	private ArrayList<MovingAverage> movingAverages;
	private String name;
	private Color color = Color.BLACK;
	private ArrayList<Observer> observers;
	
	
	public Stock(String name, ArrayList<String[]> data, ArrayList<Observer> o){
		this.name = name;
		info = data;
		movingAverages = new ArrayList<MovingAverage>();
		observers = o;
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
	
	public void addMA(MovingAverage ma){
		movingAverages.add(ma);
		notifyView(this);
	}
	
	public void removeMA(int index){
		movingAverages.remove(index);
		notifyView(this);
	}
	
	public void notifyView(Observable o){
		for(int i=0 ; i<observers.size() ; i++){
			observers.get(i).update(o, null);
		}
	}

	
}
