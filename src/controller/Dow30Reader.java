package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class Dow30Reader implements DataReader{
	private String stockSymbol = "";
	private int range = -1;

	public Dow30Reader(String stock, int range){
		stockSymbol = stock;
		this.range = range;
	}
	
	
	public List readAll() {
		
		List<HistoricalQuote> hq = null;
		try {
			
			Stock s = null;
			Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			
			if(range == -1){
				from.add(Calendar.YEAR, -40);
				s = YahooFinance.get(stockSymbol, from, to, Interval.DAILY);
			}
			else if(range == 3 || range == 6){
				from.add(Calendar.MONTH, -range);
				s = YahooFinance.get(stockSymbol, from, to, Interval.DAILY);
			}
			else if(range == 1 || range == 2){
				from.add(Calendar.YEAR, -range);
				s = YahooFinance.get(stockSymbol, from, to, Interval.DAILY);
			}
			
			hq = s.getHistory();
				
		} catch (IOException e) {
			return null;
		}
		
		if(hq != null){
			return dataFormatAdapter(hq);
		}
		
		return null;
	}
	
	private List dataFormatAdapter(List<HistoricalQuote> hq){
		
		List<String[]> data = new ArrayList<String[]>();
		data.add(new String[]{"Date", "Open", "High", "Low", "Close", "Volume", "Adj Close"});
		
		for(int i = 0; i<hq.size(); i++){	
			String date = new SimpleDateFormat("yyyy-MM-dd").format(hq.get(i).getDate().getTime());
			data.add(new String[]{date, hq.get(i).getOpen()+"", hq.get(i).getHigh()+"", hq.get(i).getLow()+"", 
					hq.get(i).getClose()+"", hq.get(i).getVolume()+"", hq.get(i).getAdjClose()+""});
		}
		
		return data;
	}

}
