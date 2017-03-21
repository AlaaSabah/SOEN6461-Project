package controller;

import java.awt.Color;

import model.MovingAverage;
import model.StockSubject;

public interface MACalculator {

	public MovingAverage calculate(StockSubject stock, int period , Color c);
	public String findSBPoints(StockSubject s);
}
