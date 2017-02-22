package model;

import java.util.ArrayList;

public class Stock {
	
	private ArrayList<String[]> info;
	private String name;
	
	public Stock(String name, ArrayList<String[]> data){
		this.name = name;
		info = data;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<String[]> getInfo(){
		return info;
	}

}
