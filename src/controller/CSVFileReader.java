package controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class CSVFileReader implements DataReader{


	public CSVFileReader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List readAll(FileReader reader) {
		CSVReader r = new CSVReader(reader);
		try {
			return r.readAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
