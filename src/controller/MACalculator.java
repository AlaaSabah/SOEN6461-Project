package controller;

import java.awt.Color;

import model.MovingAverage;
import model.Stock;

public interface MACalculator {

	public MovingAverage calculate(Stock stock, int period , Color c);
}
